package com.braczkow.mvi.feature.image_list.test

import com.braczkow.mvi.feature.image_list.FetchImagesUseCase

object FetchImagesUseCaseResults {
    val empty = FetchImagesUseCase.Result.Success(listOf())

    val default = FetchImagesUseCase.Result.Success(
            listOf(
                    FetchImagesUseCase.ImageDescription("image1", "title1"),
                    FetchImagesUseCase.ImageDescription("image2", "title2"),
                    FetchImagesUseCase.ImageDescription("image3", "title3"),
                    FetchImagesUseCase.ImageDescription("image4", "title4"),
                    FetchImagesUseCase.ImageDescription("image5", "title5")
            )
    )
}
