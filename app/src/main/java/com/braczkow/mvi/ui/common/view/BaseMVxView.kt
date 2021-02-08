package com.braczkow.mvi.ui.common.view

import com.braczkow.mvi.ui.common.MVxEventer
import com.braczkow.mvi.ui.common.MVxView

abstract class BaseMVxView<ViewEventsType, ViewStateType> : MVxView<ViewEventsType, ViewStateType> {
    private val listeners: MutableSet<MVxEventer.Listener<ViewEventsType>> = mutableSetOf()

    override fun registerListener(listener: MVxEventer.Listener<ViewEventsType>) {
        listeners.add(listener)
    }

    override fun unregisterListener(listener: MVxEventer.Listener<ViewEventsType>) {
        listeners.remove(listener)
    }

    protected fun publishEvent(viewEvent: ViewEventsType) {
        listeners.forEach {
            it.onEvent(viewEvent)
        }
    }
}
