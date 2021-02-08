package com.braczkow.mvi.ui.main.item

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.braczkow.mvi.ui.common.MVxEventer
import com.braczkow.mvi.ui.common.MVxRenderer
import com.braczkow.mvi.ui.main.MainScreen
import com.braczkow.mvi.ui.main.MainScreenItemView
import com.braczkow.mvi.ui.main.ViewFactory
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject

class ItemAdapter(
        private val eventListener: MVxEventer.Listener<MainScreen.ViewEvent>,
        private val viewFactory: ViewFactory
) : RecyclerView.Adapter<ItemAdapter.ViewHolder>(),
        MVxRenderer<List<MainScreen.ViewState.ImagesState.Fetched.ImageDescription>> {

    val viewEvents: Observable<MainScreen.ViewEvent> = PublishSubject.create()

    class ViewHolder(val view: MainScreenItemView) : RecyclerView.ViewHolder(view.rootView)

    private val imagesList = mutableListOf<MainScreen.ViewState.ImagesState.Fetched.ImageDescription>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemViewMVx = viewFactory.makeMainScreenItemView(parent)

        itemViewMVx
                .registerListener(eventListener)

        return ViewHolder(itemViewMVx)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.render(imagesList[position])
    }

    override fun getItemCount() = imagesList.size

    override fun render(viewState: List<MainScreen.ViewState.ImagesState.Fetched.ImageDescription>) {
        imagesList.clear()
        imagesList.addAll(viewState)

        notifyDataSetChanged()
    }
}
