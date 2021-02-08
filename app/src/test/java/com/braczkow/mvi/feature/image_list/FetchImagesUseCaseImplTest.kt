package com.braczkow.mvi.feature.image_list

import com.braczkow.mvi.feature.image_list.repository.BackendApi
import com.braczkow.mvi.lib.SchedulersFactory
import com.braczkow.mvi.lib.TestSchedulersFactory
import com.braczkow.mvi.feature.image_list.test.MessagesCollectionResps
import io.mockk.every
import io.mockk.mockk
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import java.io.IOException

internal class FetchImagesUseCaseImplTest {

    //mocks
    val backendApi: BackendApi = mockk()

    lateinit var UT: FetchImagesUseCaseImpl

    @Before
    fun setup() {
        SchedulersFactory.set(TestSchedulersFactory())

        UT = FetchImagesUseCaseImpl(backendApi)
    }

    @Test
    fun execute_backendSuccess_successReturned() {
        //arrange
        every { backendApi.getImagesList() } returns Single.just(MessagesCollectionResps.default)

        //act
        val result = UT.execute().test()

        //assert
        result.assertValue {
            it is FetchImagesUseCase.Result.Success && it.items.size == MessagesCollectionResps.default.size
        }
    }

    @Test
    fun execute_backendFailure_failureReturned() {
        //arrange
        every { backendApi.getImagesList() } returns Single.error(IOException())

        //act
        val result = UT.execute().test()

        //assert
        result.assertValue {
            it is FetchImagesUseCase.Result.Failure
        }
    }

}
