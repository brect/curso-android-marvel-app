package com.example.marvelapp.core.usecase

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.core.data.repository.CharactersRepository
import com.example.core.data.repository.StorageRepository
import com.example.core.usecase.GetCharactersUseCase
import com.example.core.usecase.GetCharactersUseCaseImpl
import com.example.testing.MainCoroutineRule
import com.example.testing.model.CharacterFactory
import com.example.testing.pagingsource.PagingSourceFactory
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetCharactersUseCaseImplTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var getCharactersUseCase: GetCharactersUseCase

    @Mock
    lateinit var charactersRepository: CharactersRepository

    @Mock
    lateinit var storageRepository: StorageRepository

    private val hero = CharacterFactory().create(CharacterFactory.Hero.ABom)
    private val pagingData = PagingData.from(listOf(hero))

    @Before
    fun setup() {
        getCharactersUseCase = GetCharactersUseCaseImpl(charactersRepository, storageRepository)
    }

    @Test
    fun `should validate flow paging data creation when invoke from use case is called`() =
        runTest {
            val pagingConfig = PagingConfig(20)
            val orderBy = "xurupita"
            val query = "hulk"

            whenever(
                charactersRepository.getCachedCharacters(
                    query,
                    orderBy,
                    pagingConfig
                )
            ).thenReturn(flowOf(pagingData))

            whenever(storageRepository.sorting).thenReturn(flowOf(orderBy))

            val result = getCharactersUseCase.invoke(
                GetCharactersUseCase.GetCharactersParams(
                    query,
                    pagingConfig
                )
            )

            verify(charactersRepository, times(1)).getCachedCharacters(
                query,
                orderBy,
                pagingConfig
            )

            assertNotNull(result.first())
        }
}