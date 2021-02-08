package com.braczkow.mvi.ui.common

import androidx.fragment.app.Fragment
import com.braczkow.mvi.lib.SchedulersFactory
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class BaseFragment<VIEW_STATE_TYPE, VIEW_ACTION_TYPE, VIEW_MODEL_TYPE: BaseViewModel<VIEW_STATE_TYPE, VIEW_ACTION_TYPE>>
    : Fragment() {

    abstract val viewModel: VIEW_MODEL_TYPE
    abstract fun render(state: VIEW_STATE_TYPE)
    abstract fun handle(actionType: VIEW_ACTION_TYPE)

    protected var disposables: CompositeDisposable = CompositeDisposable()

    override fun onStart() {
        super.onStart()

        viewModel
                .viewState
                .observeOn(SchedulersFactory.main)
                .subscribe {
                    render(it)
                }.apply {
                    disposables.add(this)
                }

        viewModel
                .viewActions
                .observeOn(SchedulersFactory.main)
                .subscribe {
                    handle(it)
                }.apply {
                    disposables.add(this)
                }
    }

    override fun onStop() {
        super.onStop()

        disposables.dispose()
        disposables = CompositeDisposable()
    }
}
