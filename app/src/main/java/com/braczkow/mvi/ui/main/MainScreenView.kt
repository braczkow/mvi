package com.braczkow.mvi.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.braczkow.mvi.R
import com.braczkow.mvi.ui.common.view.BaseMVxView
import com.braczkow.mvi.ui.common.MVxEventer
import com.braczkow.mvi.ui.common.MVxView
import com.braczkow.mvi.ui.common.util.gone
import com.braczkow.mvi.ui.common.util.visible
import com.braczkow.mvi.ui.main.item.ItemAdapter

interface MainScreenView : MVxView<MainScreen.ViewEvent, MainScreen.ViewState>

class MainScreenViewImpl(
        layoutInflater: LayoutInflater,
        parent: ViewGroup?,
        viewFactory: ViewFactory
) : MainScreenView, BaseMVxView<MainScreen.ViewEvent, MainScreen.ViewState>(), MVxEventer.Listener<MainScreen.ViewEvent> {
    override val rootView: View = layoutInflater.inflate(R.layout.fragment_main, parent, false)

    private val adapter = ItemAdapter(this, viewFactory)
    private val recyclerView: RecyclerView = rootView.findViewById(R.id.main_recycler_view)

    private val failFrame = rootView.findViewById<View>(R.id.main_fail_frame)
    private val emptyFrame = rootView.findViewById<View>(R.id.main_empty_frame)
    private val idleFrame = rootView.findViewById<View>(R.id.main_idle_frame)

    init {
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context, LinearLayoutManager.VERTICAL, false)

        adapter
                .viewEvents
                .subscribe {
                    publishEvent(it)
                }

        rootView.findViewById<View>(R.id.main_fail_refresh_btn).setOnClickListener {
            publishEvent(MainScreen.ViewEvent.Refresh)
        }
    }

    override fun onEvent(event: MainScreen.ViewEvent) {
        publishEvent(event)
    }

    override fun render(viewState: MainScreen.ViewState) {
        when (viewState.imagesState) {
            is MainScreen.ViewState.ImagesState.Fetched -> {
                handleImagesFetched(viewState.imagesState.imageDescriptions)
            }
            is MainScreen.ViewState.ImagesState.EmptyList -> {
                handleEmptyList()
            }
            is MainScreen.ViewState.ImagesState.FetchFailure -> {
                handleFetchFailure()
            }
            is MainScreen.ViewState.ImagesState.Idle -> {
                handleIdle()
            }
        }
    }

    private fun handleImagesFetched(imageDescriptions: List<MainScreen.ViewState.ImagesState.Fetched.ImageDescription>) {
        adapter.render(imageDescriptions)

        recyclerView.visible()
        idleFrame.gone()
        failFrame.gone()
        emptyFrame.gone()
    }

    private fun handleIdle() {
        idleFrame.visible()
        failFrame.gone()
        emptyFrame.gone()
        recyclerView.gone()
    }

    private fun handleFetchFailure() {
        failFrame.visible()
        emptyFrame.gone()
        recyclerView.gone()
        idleFrame.gone()
    }

    private fun handleEmptyList() {
        emptyFrame.visible()
        failFrame.gone()
        recyclerView.gone()
        idleFrame.gone()
    }

}
