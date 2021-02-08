package com.braczkow.mvi.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.braczkow.mvi.ui.common.BaseFragment
import com.braczkow.mvi.ui.common.MVxEventer
import com.braczkow.mvi.ui.common.snackbar.SnackbarHelper
import dagger.hilt.android.AndroidEntryPoint


class ViewFactory(private val context: Context) {
    fun makeMainScreenView(parent: ViewGroup?): MainScreenView {
        return MainScreenViewImpl(LayoutInflater.from(context), parent, this)
    }

    fun makeMainScreenItemView(parent: ViewGroup?): MainScreenItemView {
        return MainScreenItemViewImpl(LayoutInflater.from(context), parent)
    }

    fun makeSnackbarHelper(rootView: View): SnackbarHelper {
        return SnackbarHelper(rootView)
    }
}


@AndroidEntryPoint
class MainFragment : BaseFragment<MainScreen.ViewState, MainScreen.ViewAction, MainScreenViewModel>(), MVxEventer.Listener<MainScreen.ViewEvent> {

    override val viewModel by viewModels<MainScreenViewModel>()

    lateinit var screen: MainScreenView
    lateinit var snackbarHelper: SnackbarHelper

    lateinit var viewFactory: ViewFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewFactory = ViewFactory(requireContext())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        screen = viewFactory.makeMainScreenView(container)
        snackbarHelper = viewFactory.makeSnackbarHelper(screen.rootView)


        return screen.rootView
    }

    override fun onStart() {
        super.onStart()
        screen.registerListener(this)

    }

    override fun onStop() {
        super.onStop()

        screen.unregisterListener(this)
    }

    override fun render(state: MainScreen.ViewState) {
        screen.render(state)
    }

    override fun handle(actionType: MainScreen.ViewAction) {
        if (actionType is MainScreen.ViewAction.ShowItemClicked) {
            snackbarHelper.showMessageWithHide(actionType.description)
        }
    }

    override fun onEvent(event: MainScreen.ViewEvent) {
        viewModel.onEvent(event)
    }



}
