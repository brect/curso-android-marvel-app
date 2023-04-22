package com.example.marvelapp.presentation.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.marvelapp.databinding.ItemCharacterBinding
import com.example.marvelapp.framework.imageloader.ImageLoader
import com.example.marvelapp.presentation.common.CommonViewHolder

class FavoritesViewHolder(
    itemBinding: ItemCharacterBinding,
    private val imageLoader: ImageLoader
): CommonViewHolder<FavoriteItem>(itemBinding){

    private val textName: TextView = itemBinding.textViewCharacterName
    private val imageCharacter: ImageView = itemBinding.imageViewCharacter

    override fun bind(data: FavoriteItem) {
        textName.text = data.name
        imageLoader.load(imageCharacter, data.imageUrl)
    }

    companion object {
        fun create(parent: ViewGroup, imageLoader: ImageLoader): FavoritesViewHolder {
            val itemBinding = ItemCharacterBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)

            return FavoritesViewHolder(itemBinding, imageLoader)
        }
    }
}