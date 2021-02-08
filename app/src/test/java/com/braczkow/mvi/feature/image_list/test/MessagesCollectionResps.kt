package com.braczkow.mvi.feature.image_list.test

import com.braczkow.mvi.feature.image_list.repository.ListResp

object MessagesCollectionResps {
    val default = ListResp().apply {
        addAll(listOf(
                ListResp.Item("image1", "author1", 100, 100, "url", "download_url1"),
                ListResp.Item("image2", "author2", 100, 100, "url", "download_url2"),
                ListResp.Item("image3", "author3", 100, 100, "url", "download_url3")
        ))
    }

    val empty = ListResp()
}
