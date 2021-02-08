package com.braczkow.mvi.ui.common.util

import android.view.View

fun View.gone() {
    this.visibility = View.GONE
}

fun View.isGone() = this.visibility == View.GONE

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.isVisible() = this.visibility == View.VISIBLE
