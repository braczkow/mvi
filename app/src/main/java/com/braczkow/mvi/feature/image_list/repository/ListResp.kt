package com.braczkow.mvi.feature.image_list.repository

class ListResp : ArrayList<ListResp.Item>() {
    data class Item(
            val id: String,
            val author: String,
            val width: Int,
            val height: Int,
            val url: String,
            val download_url: String
    )
}
