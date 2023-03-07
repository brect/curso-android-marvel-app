package com.example.core.marvelapp.framework.paging

import androidx.paging.PagingSource
import com.example.core.data.repository.CharactersRemoteDataSource
import com.example.core.domain.model.Character
import com.example.core.marvelapp.factory.response.CharacterPagingFactory
import com.example.marvelapp.framework.paging.CharactersPagingSource
import com.example.testing.MainCoroutineRule
import com.example.testing.model.CharactorFactory
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CharactersPagingSourceTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val characterPagingFactory = CharacterPagingFactory()
    private val characterFactory = CharactorFactory()

    private lateinit var charactersPagingSource: CharactersPagingSource

    @Mock
    lateinit var remoteDataSource: CharactersRemoteDataSource

    @Before
    fun setup() {
        charactersPagingSource = CharactersPagingSource(remoteDataSource, "")
    }

    @Test
    fun `should return a success load result when load is called`() =
        runTest {
            // Arrange
            whenever(remoteDataSource.fetchCharacters(any())).thenReturn(characterPagingFactory.create())

            // Act
            val result = charactersPagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = 2,
                    false
                )
            )

            // Assert
            val expected = listOf(
                characterFactory.create(CharactorFactory.Hero.ThreeDMan),
                characterFactory.create(CharactorFactory.Hero.ABom)
            )

            assertEquals(
                PagingSource.LoadResult.Page(
                    data = expected,
                    prevKey = null,
                    nextKey = 20
                ),
                result
            )
        }

    @Test
    fun `should return a erro load result when load is called`() =
        runTest {
            // Arrange
            val exception = RuntimeException()
            whenever(remoteDataSource.fetchCharacters(any())).thenThrow(exception)

            // Act
            val result = charactersPagingSource.load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = 2,
                    false
                )
            )

            // Assert
            assertEquals(
                PagingSource.LoadResult.Error<Int, Character>(exception),
                result
            )
        }
}