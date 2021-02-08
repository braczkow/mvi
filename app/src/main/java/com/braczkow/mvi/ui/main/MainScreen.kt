package com.braczkow.mvi.ui.main

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

class MainScreen {
    @Parcelize
    data class ViewState(
            val imagesState: ImagesState
    ): Parcelable {
        sealed class ImagesState: Parcelable {

            @Parcelize object Idle : ImagesState()
            @Parcelize data class Fetched(
                    val imageDescriptions: List<ImageDescription>
            ) : ImagesState() {
                @Parcelize data class ImageDescription(
                        val image: String,
                        val title: String,
                        val isFav: Boolean
                ): Parcelable
            }

            @Parcelize object EmptyList : ImagesState()

            @Parcelize object FetchFailure : ImagesState()
        }

        companion object {
            fun default() = ViewState(
                    imagesState = ImagesState.Idle
            )
        }
    }

    sealed class ViewAction {
        data class ShowItemClicked(val description: String) : ViewAction()
    }

    sealed class ViewEvent {
        data class ItemClicked(val item: ViewState.ImagesState.Fetched.ImageDescription) : ViewEvent()
        data class FavClicked(val item: ViewState.ImagesState.Fetched.ImageDescription) : ViewEvent()
        object Refresh : ViewEvent()
    }

}
