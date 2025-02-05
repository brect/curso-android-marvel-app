package com.example.marvelapp.presentation.characters

import androidx.paging.PagingData
import com.example.core.usecase.GetCharactersUseCase
import com.example.testing.MainCoroutineRule
import com.example.testing.model.CharacterFactory
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CharactersViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val charactersFactory = CharacterFactory()

    private val pagingDataCharacters = PagingData.from(
        listOf(
            charactersFactory.create(CharacterFactory.Hero.ThreeDMan),
            charactersFactory.create(CharacterFactory.Hero.ABom)
        )
    )

    @Mock
    lateinit var getCharactersUseCase: GetCharactersUseCase

    private lateinit var charactersViewModel: CharactersViewModel

    @Before
    fun setup() {
        charactersViewModel = CharactersViewModel(getCharactersUseCase)
    }

    @Test
    fun `should validate the paging data object values when calling charactersPagingData`() =
        runTest {

            whenever(
                getCharactersUseCase.invoke(
                    any()
                )
            ).thenReturn(
                flowOf(pagingDataCharacters)
            )

            var charactersPagingDataResult = charactersViewModel.charactersPagingData("")

            assertNotNull( charactersPagingDataResult.first())
        }

    @Test(expected = RuntimeException::class)
    fun `should throw an exception when the calling to use case returns an exception`() = runTest {
        whenever(getCharactersUseCase.invoke(any()))
            .thenThrow(RuntimeException())

        charactersViewModel.charactersPagingData("")
    }

}