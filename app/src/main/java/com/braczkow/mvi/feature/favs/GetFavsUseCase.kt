package com.braczkow.mvi.feature.favs

import com.braczkow.mvi.feature.favs.common.Consts
import com.braczkow.mvi.feature.favs.common.Favourites
import com.braczkow.mvi.lib.Storage
import io.reactivex.rxjava3.core.Single
import timber.log.Timber
import javax.inject.Inject

interface GetFavsUseCase {
    fun execute(): Single<Favourites>
}

class GetFavsUseCaseImpl @Inject constructor(
        private val storage: Storage
) : GetFavsUseCase {
    override fun execute(): Single<Favourites> {
        return Single.fromCallable {
            val current = storage.load(Consts.FAVS_SET_KEY, Favourites::class.java) ?: Favourites()
            Timber.d("Loaded: ${current}")
            current
        }
    }

}
