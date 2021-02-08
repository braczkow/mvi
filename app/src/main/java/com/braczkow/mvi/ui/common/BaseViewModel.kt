package com.braczkow.mvi.ui.common

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject

abstract class BaseViewModel<ViewStateType, ViewActionType>(
        var lastState: ViewStateType,
        private val savedStateHandle: SavedStateHandle,
        private val tag: String
        ) : ViewModel() {
    val viewState: Observable<ViewStateType> = BehaviorSubject.createDefault(lastState)

    val viewActions: Observable<ViewActionType> = PublishSubject.create()

    protected val disposables = CompositeDisposable()
    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

    @Synchronized
    protected fun publishPartialState(reducer: (ViewStateType) -> ViewStateType) {
        lastState = reducer(lastState)
        savedStateHandle.set(tag, lastState)
        (viewState as BehaviorSubject).onNext(lastState)
    }

    protected fun publishAction(actionType: ViewActionType) {
        (viewActions as PublishSubject).onNext(actionType)
    }


}
