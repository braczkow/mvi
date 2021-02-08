package com.braczkow.mvi.ui.common

import android.view.View

interface MVxRenderer<ViewStateType> {
    fun render(viewState: ViewStateType)
}

interface MVxEventer<ViewEventsType> {
    interface Listener<ViewEventsType> {
        fun onEvent(event: ViewEventsType)
    }

    fun registerListener(listener: Listener<ViewEventsType>)
    fun unregisterListener(listener: Listener<ViewEventsType>)
}

interface MVxView<ViewEventsType, ViewStateType>: MVxRenderer<ViewStateType>, MVxEventer<ViewEventsType> {
    val rootView: View
}
