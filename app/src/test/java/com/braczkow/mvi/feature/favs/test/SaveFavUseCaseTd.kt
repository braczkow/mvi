package com.braczkow.mvi.feature.favs.test

import com.braczkow.mvi.feature.favs.SaveFavUseCase
import com.braczkow.mvi.feature.favs.common.Favourites
import io.reactivex.rxjava3.core.Single

class SaveFavUseCaseTd: SaveFavUseCase {
    var result = Single.just(Favouritess.default)
    var executeCount = 0

    var lastSavedData: Pair<String, Boolean>? = null

    override fun execute(favKey: String, favState: Boolean): Single<Favourites> {
        executeCount++
        lastSavedData = Pair(favKey, favState)
        return result
    }
}
