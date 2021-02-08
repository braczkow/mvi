package com.braczkow.mvi.feature.image_list.test

import com.braczkow.mvi.feature.image_list.FetchImagesUseCase
import io.reactivex.rxjava3.core.Single

class FetchImagesUseCaseTd: FetchImagesUseCase {

    var result: Single<FetchImagesUseCase.Result> = Single.just(FetchImagesUseCaseResults.default)
    var executeCount = 0

    override fun execute(): Single<FetchImagesUseCase.Result> {
        executeCount++
        return result
    }
}
