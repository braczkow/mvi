package com.braczkow.mvi.ui.main.test

import com.braczkow.mvi.ui.main.MainScreen

object MainScreens {
    object ViewStates {
        val imageDescription1 = MainScreen.ViewState.ImagesState.Fetched.ImageDescription(
                "image1", "title1", true
        )

        val imageDescription2 = MainScreen.ViewState.ImagesState.Fetched.ImageDescription(
                "image2", "title2", true
        )

        val imageDescription3 = MainScreen.ViewState.ImagesState.Fetched.ImageDescription(
                "image3", "title3", false
        )

        val default = MainScreen.ViewState(imagesState = MainScreen.ViewState.ImagesState.Fetched(
                imageDescriptions = listOf(
                        imageDescription1, imageDescription2, imageDescription3)
        ))
    }
}
