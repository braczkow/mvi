package com.braczkow.mvi.feature.favs.test

import com.braczkow.mvi.feature.favs.GetFavsUseCase
import com.braczkow.mvi.feature.favs.common.Favourites
import io.reactivex.rxjava3.core.Single

class GetFavsUseCaseTd: GetFavsUseCase {

    var result = Single.just(Favouritess.default)
    var executeCount = 0

    override fun execute(): Single<Favourites> {
        executeCount++
        return result
    }
}
