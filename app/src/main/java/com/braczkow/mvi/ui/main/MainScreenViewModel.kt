package com.braczkow.mvi.ui.main

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import com.braczkow.mvi.feature.favs.GetFavsUseCase
import com.braczkow.mvi.feature.favs.SaveFavUseCase
import com.braczkow.mvi.feature.favs.common.Favourites
import com.braczkow.mvi.feature.image_list.FetchImagesUseCase
import com.braczkow.mvi.ui.common.BaseViewModel
import timber.log.Timber

class MainScreenViewModel @ViewModelInject constructor(
        @Assisted private val savedStateHandle: SavedStateHandle,
        private val fetchImagesUseCase: FetchImagesUseCase,
        private val saveFavUseCase: SaveFavUseCase,
        private val getFavsUseCase: GetFavsUseCase
) : BaseViewModel<MainScreen.ViewState, MainScreen.ViewAction>(
        savedStateHandle.get<MainScreen.ViewState>(TAG) ?: MainScreen.ViewState.default(),
        savedStateHandle,
        TAG
) {

    companion object {
        val TAG = MainScreenViewModel::class.java.simpleName
    }

    init {
        val hasSavedState = savedStateHandle.contains(TAG)
        if (!hasSavedState) {
            Timber.d("no savedState..")
            fetchImages()
        }
    }

    private fun fetchImages() {
        var favs = Favourites()
        getFavsUseCase
                .execute()
                .flatMap {
                    favs = it
                    fetchImagesUseCase.execute()
                }
                .subscribe { images, ex ->
                    if (ex != null) {
                        handleFetchException(ex)
                    } else {
                        handleFetchResult(images, favs.items)
                    }
                }
                .apply { disposables.add(this) }
    }

    private fun handleFetchException(it: Throwable) {
        Timber.e("handleFetchException: $it")
        handleFailedFetchResponse()
    }

    private fun handleFetchResult(result: FetchImagesUseCase.Result, favs: Set<String>) {
        Timber.d("handleFetchResult")
        when (result) {
            is FetchImagesUseCase.Result.Success -> {
                if (result.items.isNotEmpty()) {
                    handleNonEmptyFetchResponse(result, favs)
                } else {
                    handleEmptyFetchResponse()
                }
            }
            is FetchImagesUseCase.Result.Failure -> {
                handleFailedFetchResponse()
            }
        }
    }

    private fun handleEmptyFetchResponse() {
        publishPartialState {
            it.copy(
                    imagesState = MainScreen.ViewState.ImagesState.EmptyList
            )
        }
    }

    private fun handleFailedFetchResponse() {
        publishPartialState {
            it.copy(
                    imagesState = MainScreen.ViewState.ImagesState.FetchFailure
            )
        }
    }

    private fun handleNonEmptyFetchResponse(result: FetchImagesUseCase.Result.Success, favs: Set<String>) {
        publishPartialState {
            it.copy(
                    imagesState = MainScreen.ViewState.ImagesState.Fetched(
                            result.items.map {
                                val isFav = favs.contains(it.image)
                                MainScreen.ViewState.ImagesState.Fetched.ImageDescription(it.image, it.title, isFav)
                            }
                    )
            )
        }
    }

    fun onEvent(event: MainScreen.ViewEvent) {
        when (event) {
            is MainScreen.ViewEvent.ItemClicked -> {
                publishAction(MainScreen.ViewAction.ShowItemClicked(event.item.title))
            }

            is MainScreen.ViewEvent.FavClicked -> {
                toggleFav(event.item)
            }

            is MainScreen.ViewEvent.Refresh -> {
                fetchImages()
            }
        }
    }

    private fun toggleFav(item: MainScreen.ViewState.ImagesState.Fetched.ImageDescription) {
        saveFavUseCase
                .execute(item.image, !item.isFav)
                .subscribe { favs, ex ->
                    if (ex != null) {
                        Timber.e("saveFavUseCase.execute failed!")
                    } else {
                        Timber.d("saveFavUseCase.execute success: $favs")
                        updateFavState(favs.items)
                    }
                }
    }

    private fun updateFavState(favs: Set<String>) {
        publishPartialState { currentState ->
            if (currentState.imagesState is MainScreen.ViewState.ImagesState.Fetched) {
                currentState.copy(imagesState = MainScreen.ViewState.ImagesState.Fetched(currentState.imagesState.imageDescriptions.map {
                    val isFav = favs.contains(it.image)
                    MainScreen.ViewState.ImagesState.Fetched.ImageDescription(
                            it.image,
                            it.title,
                            isFav
                    )
                }))
            } else {
                currentState
            }
        }
    }

}
