package com.braczkow.mvi.feature.favs

import com.braczkow.mvi.feature.favs.common.Consts
import com.braczkow.mvi.feature.favs.test.Favouritess
import com.braczkow.mvi.lib.TestStorage
import org.junit.Before
import org.junit.Test

internal class GetFavsUseCaseImplTest {
    lateinit var UT: GetFavsUseCaseImpl

    //doubles
    val storage = TestStorage()

    @Before
    fun setup() {
        UT = GetFavsUseCaseImpl(storage)
    }

    @Test
    fun execute_emptyStorage_nonNullEmptyFavsReturned() {
        //arrange

        //act
        val result = UT.execute().test()

        //assert
        result.assertValueAt(0) {
            it != null && it.items.size == 0
        }
    }

    @Test
    fun execute_storedFavs_returned() {
        //arrange
        storage.save(Consts.FAVS_SET_KEY, Favouritess.default)

        //act
        val result = UT.execute().test()

        //assert
        result.assertValueAt(0) {
            it == Favouritess.default
        }

    }
}
