package com.example.marvelapp.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.example.marvelapp.databinding.FragmentDetailBinding
import com.example.marvelapp.framework.imageloader.ImageLoader
import com.example.marvelapp.presentation.detail.livedata.FavoriteUiActionStateLiveData
import com.example.marvelapp.presentation.detail.livedata.UiActionStateLiveData
import com.example.marvelapp.presentation.extensions.showShortToast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding: FragmentDetailBinding get() = _binding!!

    private val viewModel: DetailViewModel by viewModels()

    @Inject
    lateinit var imageLoader: ImageLoader

    private val args by navArgs<DetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = FragmentDetailBinding.inflate(inflater, container, false).apply {
        _binding = this
    }.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val detailViewArgs = args.detailViewArg
        binding.imageViewCharacter.run {
            transitionName = detailViewArgs.name

            imageLoader.load(this, detailViewArgs.imageUrl)
        }

        setSharedElementTransitionOnEnter()

        loadCategoriesAndObserveUiState(detailViewArgs)
        setAndObserveFavoriteUiState(detailViewArgs)

    }

    private fun setAndObserveFavoriteUiState(detailViewArgs: DetailViewArg) {
        viewModel.favorite.run {
            checkFavorite(detailViewArgs.characterId)

            binding.imageFavoriteIcon.setOnClickListener {
                update(detailViewArgs)
            }

            state.observe(viewLifecycleOwner) { uiState ->
                binding.flipperFavorite.displayedChild = when (uiState) {
                    FavoriteUiActionStateLiveData.UiState.Loading -> FLIPPER_FAVORITE_CHILD_POSITION_LOADING
                    is FavoriteUiActionStateLiveData.UiState.Icon -> {
                        binding.imageFavoriteIcon.setImageResource(uiState.icon)
                        FLIPPER_FAVORITE_CHILD_POSITION_IMAGE
                    }
                    is FavoriteUiActionStateLiveData.UiState.Error -> {
                        showShortToast(uiState.message)
                        FLIPPER_FAVORITE_CHILD_POSITION_IMAGE
                    }
                }
            }
        }
    }

    private fun loadCategoriesAndObserveUiState(detailViewArgs: DetailViewArg) {
        viewModel.categories.load(detailViewArgs.characterId)
        viewModel.categories.state.observe(viewLifecycleOwner) { uiState ->
            binding.viewFlipperDatail.displayedChild = when (uiState) {
                UiActionStateLiveData.UiState.Loading -> {
                    FLIPPER_CHILD_LOADING
                }
                is UiActionStateLiveData.UiState.Success -> {
                    binding.recyclerViewParentDetail.run {
                        setHasFixedSize(true)
                        adapter = DetailParentAdapter(uiState.detailParentList, imageLoader)
                    }
                    FLIPPER_CHILD_DETAIL
                }
                UiActionStateLiveData.UiState.Error -> {
                    binding.includeErrorView.buttonRetry.setOnClickListener {
                        viewModel.categories.load(detailViewArgs.characterId)
                    }
                    FLIPPER_CHILD_ERROR
                }
                UiActionStateLiveData.UiState.Empty -> {
                    FLIPPER_CHILD_EMPTY
                }
            }

        }
    }

    // Define a animação da transição como "move"
    private fun setSharedElementTransitionOnEnter() {
        TransitionInflater.from(requireContext())
            .inflateTransition(android.R.transition.move).apply {
                sharedElementEnterTransition = this
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val FLIPPER_CHILD_LOADING = 0
        private const val FLIPPER_CHILD_DETAIL = 1
        private const val FLIPPER_CHILD_ERROR = 2
        private const val FLIPPER_CHILD_EMPTY = 3
        private const val FLIPPER_FAVORITE_CHILD_POSITION_IMAGE = 0
        private const val FLIPPER_FAVORITE_CHILD_POSITION_LOADING = 1
    }


}