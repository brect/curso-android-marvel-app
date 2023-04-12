package com.example.marvelapp.framework.di

import com.example.core.usecase.GetCharacterCategoriesUseCase
import com.example.core.usecase.GetCharacterCategoriesUseCaseImpl
import com.example.core.usecase.GetCharactersUseCase
import com.example.core.usecase.GetCharactersUseCaseImpl
import com.example.core.usecase.AddFavoriteUseCase
import com.example.core.usecase.AddFavoriteUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseModule {

    @Binds
    fun bindGetCharactersUseCase(useCaseImpl: GetCharactersUseCaseImpl): GetCharactersUseCase

    @Binds
    fun bindGetComicsUseCase(
        useCaseImpl: GetCharacterCategoriesUseCaseImpl,
    ): GetCharacterCategoriesUseCase

    @Binds
    fun bindAddFavoriteUseCase(
        useCaseImpl: AddFavoriteUseCaseImpl,
    ): AddFavoriteUseCase

}