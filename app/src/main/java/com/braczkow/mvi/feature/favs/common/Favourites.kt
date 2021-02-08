package com.braczkow.mvi.feature.favs.common

data class Favourites(val items: MutableSet<String> = mutableSetOf())
