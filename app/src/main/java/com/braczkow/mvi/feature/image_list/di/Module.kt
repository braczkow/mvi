package com.braczkow.mvi.feature.image_list.di

import com.braczkow.mvi.feature.image_list.FetchImagesUseCase
import com.braczkow.mvi.feature.image_list.FetchImagesUseCaseImpl
import com.braczkow.mvi.feature.image_list.repository.BackendApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class Module {
    @Provides
    fun fetchImagesUseCase(impl: FetchImagesUseCaseImpl): FetchImagesUseCase = impl

    @Provides
    fun backendApi(): BackendApi {
        return BackendApi.create()
    }

}
