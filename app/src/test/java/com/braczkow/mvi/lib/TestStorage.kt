package com.braczkow.mvi.lib

import com.google.gson.Gson

class TestStorage: Storage {

    private val storage = mutableMapOf<String, String>()

    private val gson = Gson()

    override fun save(key: String, obj: Any) {
        val json = gson.toJson(obj)
        storage.put(key, json)
    }

    override fun <T> load(key: String, type: Class<T>): T? {
        val json: String? = storage.get(key)
        return json?.run { gson.fromJson(this, type) }
    }
}
