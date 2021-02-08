package com.braczkow.mvi.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.braczkow.mvi.R
import com.braczkow.mvi.ui.common.view.BaseMVxView
import com.braczkow.mvi.ui.common.MVxView
import timber.log.Timber

interface MainScreenItemView : MVxView<MainScreen.ViewEvent, MainScreen.ViewState.ImagesState.Fetched.ImageDescription> {
}

class MainScreenItemViewImpl(
        inflater: LayoutInflater,
        parent: ViewGroup?
): BaseMVxView<MainScreen.ViewEvent, MainScreen.ViewState.ImagesState.Fetched.ImageDescription>(),
        MainScreenItemView {
    override val rootView: View = inflater.inflate(R.layout.item_image, parent, false)
    private val image: ImageView = rootView.findViewById(R.id.item_image_image)
    private val favFrame: View = rootView.findViewById(R.id.item_image_fav_frame)
    private val favGrey: View = rootView.findViewById(R.id.item_image_fav_grey)
    private val favYellow: View = rootView.findViewById(R.id.item_image_fav_yellow)

    override fun render(viewState: MainScreen.ViewState.ImagesState.Fetched.ImageDescription) {
        val url = viewState.image

        Glide
                .with(rootView.context)
                .load(url)
                .centerCrop()
                .placeholder(android.R.drawable.ic_menu_camera)
                .into(image)

        rootView.setOnClickListener {
            publishEvent(MainScreen.ViewEvent.ItemClicked(viewState))
        }

        favFrame.setOnClickListener {
            publishEvent(MainScreen.ViewEvent.FavClicked(viewState))
        }

        Timber.d("ItemView item: ${viewState}")

        if (viewState.isFav) {
            favYellow.visibility = View.VISIBLE
            favGrey.visibility = View.GONE
        } else {
            favYellow.visibility = View.GONE
            favGrey.visibility = View.VISIBLE
        }


    }

}
