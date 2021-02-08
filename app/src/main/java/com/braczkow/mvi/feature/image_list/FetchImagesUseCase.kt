package com.braczkow.mvi.feature.image_list

import com.braczkow.mvi.feature.common.UseCase
import com.braczkow.mvi.feature.image_list.repository.BackendApi
import com.braczkow.mvi.lib.SchedulersFactory
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

interface FetchImagesUseCase : UseCase {

    data class ImageDescription(
            val image: String,
            val title: String
    )

    sealed class Result {
        data class Success(val items: List<ImageDescription>) : Result()
        object Failure : Result()
    }

    fun execute(): Single<Result>
}

class FetchImagesUseCaseImpl @Inject constructor(
        private val backendApi: BackendApi
) : FetchImagesUseCase {
    override fun execute(): Single<FetchImagesUseCase.Result> {
        return backendApi
                .getImagesList()
                .subscribeOn(SchedulersFactory.io)
                .map { backendResult ->
                    FetchImagesUseCase.Result.Success(
                            backendResult.map {
                                FetchImagesUseCase.ImageDescription(
                                        it.download_url,
                                        it.id
                                )
                            }
                    ) as FetchImagesUseCase.Result
                }
                .onErrorReturn {
                    FetchImagesUseCase.Result.Failure
                }

    }
}
