package com.braczkow.mvi.feature.favs.di

import com.braczkow.mvi.feature.favs.GetFavsUseCase
import com.braczkow.mvi.feature.favs.GetFavsUseCaseImpl
import com.braczkow.mvi.feature.favs.SaveFavUseCase
import com.braczkow.mvi.feature.favs.SaveFavUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class Module {
    @Provides
    fun saveFavUseCase(impl: SaveFavUseCaseImpl): SaveFavUseCase = impl

    @Provides
    fun getFavsUseCase(impl: GetFavsUseCaseImpl): GetFavsUseCase = impl
}
