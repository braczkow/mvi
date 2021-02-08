package com.braczkow.mvi.lib.di

import com.braczkow.mvi.lib.Storage
import com.braczkow.mvi.lib.StorageImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class Module {
    @Binds
    abstract fun bindStorage(impl: StorageImpl): Storage
}
