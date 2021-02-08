package com.braczkow.mvi.feature.favs.test

import com.braczkow.mvi.feature.favs.common.Favourites

object Favouritess {
    val default = Favourites(
            mutableSetOf(
                    "image1",
                    "image3",
                    "image5"
            )
    )
}
