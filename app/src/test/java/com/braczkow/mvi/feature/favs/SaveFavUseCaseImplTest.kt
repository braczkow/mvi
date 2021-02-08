package com.braczkow.mvi.feature.favs

import com.braczkow.mvi.feature.favs.common.Consts
import com.braczkow.mvi.feature.favs.test.Favouritess
import com.braczkow.mvi.lib.TestStorage
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

internal class SaveFavUseCaseImplTest {

    lateinit var UT: SaveFavUseCaseImpl

    //doubles
    val storage = TestStorage()

    @Before
    fun setup() {
        UT = SaveFavUseCaseImpl(storage)
    }

    @Test
    fun execute_emptyStorage_singleFavReturned() {
        //arrange

        //act
        val result = UT.execute("fav1", true).test()

        //assert
        val r = result.values().last()
        assertThat(r.items.size).isEqualTo(1)
        assertThat(r.items.contains("fav1")).isTrue()
    }

    @Test
    fun executeAdd_existingFavs_mergedOk() {
        //arrange
        storage.save(Consts.FAVS_SET_KEY, Favouritess.default)

        //act
        val result = UT.execute("image2", true).test()

        //assert
        val r = result.values().last()
        assertThat(r.items.contains("image2")).isTrue()
        assertThat(r.items.contains("image1")).isTrue()
        assertThat(r.items.contains("image3")).isTrue()
        assertThat(r.items.contains("image5")).isTrue()
    }

    @Test
    fun executeRemove_existingFavs_mergedOk() {
        //arrange
        storage.save(Consts.FAVS_SET_KEY, Favouritess.default)

        //act
        val result = UT.execute("image1", false).test()

        //assert
        val r = result.values().last()
        assertThat(r.items.contains("image1")).isFalse()
        assertThat(r.items.contains("image3")).isTrue()
        assertThat(r.items.contains("image5")).isTrue()
    }

}
