package com.braczkow.mvi.feature.favs

import com.braczkow.mvi.feature.common.UseCase
import com.braczkow.mvi.feature.favs.common.Consts
import com.braczkow.mvi.feature.favs.common.Favourites
import com.braczkow.mvi.lib.Storage
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

interface SaveFavUseCase : UseCase {
    fun execute(favKey: String, favState: Boolean): Single<Favourites>
}

typealias FavsSet = Set<String>

class SaveFavUseCaseImpl @Inject constructor(
        private val storage: Storage
) : SaveFavUseCase {
    override fun execute(favKey: String, favState: Boolean): Single<Favourites> {
        return Single.fromCallable {
            var current = storage.load(Consts.FAVS_SET_KEY, Favourites::class.java)

            if (current == null) {
                current = Favourites(mutableSetOf())
            }

            if (!favState) {
                current.items.remove(favKey)
            } else {
                current.items.add(favKey)
            }

            storage.save(Consts.FAVS_SET_KEY, current)

            current
        }
    }

}
