package com.braczkow.mvi.ui.main

import androidx.lifecycle.SavedStateHandle
import com.braczkow.mvi.feature.favs.test.GetFavsUseCaseTd
import com.braczkow.mvi.feature.favs.test.SaveFavUseCaseTd
import com.braczkow.mvi.feature.image_list.test.FetchImagesUseCaseResults
import com.braczkow.mvi.feature.image_list.test.FetchImagesUseCaseTd
import com.braczkow.mvi.ui.main.test.MainScreens
import io.reactivex.rxjava3.core.Single
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.io.IOException
import java.lang.RuntimeException

internal class MainScreenViewModelTest {

    lateinit var UT: MainScreenViewModel

    //doubles
    val getFavsUseCase = GetFavsUseCaseTd()
    val saveFavUseCase = SaveFavUseCaseTd()
    val fetchImagesUseCase = FetchImagesUseCaseTd()

    //real impl
    val savedStateHandle = SavedStateHandle()

    //typically SUT creation goes into setup method
    //but we are also testing init
    fun create() = MainScreenViewModel(savedStateHandle, fetchImagesUseCase, saveFavUseCase, getFavsUseCase)

    @Test
    fun initialize_noSavedState_imagesFetched() {
        //arrange
        UT = create()

        //act: no-op, init

        //assert
        assertThat(fetchImagesUseCase.executeCount).isEqualTo(1)
    }

    @Test
    fun initialize_fetchSuccess_imagesLoadedEmitted() {
        //arrange
        UT = create()

        //act
        val viewStates = UT.viewState.test().values()

        //assert
        assertThat(viewStates).last().matches {
            it.imagesState is MainScreen.ViewState.ImagesState.Fetched
        }
    }

    @Test
    fun initialize_fetchSuccessAndFavsSuccess_mergedCorrectly() {
        //arrange
        UT = create()

        //act
        val viewStates = UT.viewState.test().values()

        //assert
        assertThat(viewStates).last().matches {
            it.imagesState is MainScreen.ViewState.ImagesState.Fetched
        }

        val lastState = viewStates.last().imagesState as MainScreen.ViewState.ImagesState.Fetched

        assertThat(lastState.imageDescriptions).anyMatch {
            it.image == "image1" && it.isFav
        }

        assertThat(lastState.imageDescriptions).anyMatch {
            it.image == "image3" && it.isFav
        }

        assertThat(lastState.imageDescriptions).anyMatch {
            it.image == "image5" && it.isFav
        }
    }

    @Test
    fun initialize_fetchFailure_failureEmitted() {
        //arrange
        fetchImagesUseCase.result = Single.error(IOException())
        UT = create()

        //act

        //assert
        assertThat(UT.lastState.imagesState).isInstanceOf(MainScreen.ViewState.ImagesState.FetchFailure::class.java)
    }

    @Test
    fun initialize_getFavsFailure_failureEmitted() {
        //arrange
        getFavsUseCase.result = Single.error(RuntimeException("blah"))
        UT = create()

        //act

        //assert
        assertThat(UT.lastState.imagesState).isEqualTo(MainScreen.ViewState.ImagesState.FetchFailure)
    }

    @Test
    fun initialize_fetchSuccessButEmptyResponse_emptyResponseEmitted() {
        //arrange
        fetchImagesUseCase.result = Single.just(FetchImagesUseCaseResults.empty)
        UT = create()

        //act

        //assert
        assertThat(UT.lastState).matches {
            it.imagesState is MainScreen.ViewState.ImagesState.EmptyList
        }
    }

    @Test
    fun onEvent_itemClicked_showEmitted() {
        //arrange
        UT = create()
        val t = UT.viewActions.test()

        //act
        UT.onEvent(MainScreen.ViewEvent.ItemClicked(MainScreens.ViewStates.imageDescription1))

        //assert
        assertThat(t.values()).size().isEqualTo(1)
        assertThat(t.values().get(0)).matches {
            it is MainScreen.ViewAction.ShowItemClicked
        }
    }

    @Test
    fun onEvent_favClicked_favSaved() {
        //arrange
        UT = create()
        val t = UT.viewActions.test()

        //act
        UT.onEvent(MainScreen.ViewEvent.FavClicked(MainScreens.ViewStates.imageDescription3))

        //assert
        assertThat(saveFavUseCase.executeCount).isEqualTo(1)
        assertThat(saveFavUseCase.lastSavedData).isEqualTo(
                Pair(MainScreens.ViewStates.imageDescription3.image, !MainScreens.ViewStates.imageDescription3.isFav))
    }
}
