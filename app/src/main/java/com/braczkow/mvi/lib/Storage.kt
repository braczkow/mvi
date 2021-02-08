package com.braczkow.mvi.lib

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface Storage {
    fun save(key: String, obj: Any)
    fun <T> load(key: String, type: Class<T>): T?
}

class StorageImpl @Inject constructor(
        @ApplicationContext context: Context
) : Storage {

    val preferences: SharedPreferences =
            context.getSharedPreferences("app.prefs", Context.MODE_PRIVATE)

    val gson = Gson()

    override fun save(key: String, obj: Any) {
        val json = gson.toJson(obj)
        preferences.edit().putString(key, json).apply()
    }

    override fun <T> load(key: String, type: Class<T>): T? {
        val json: String? = preferences.getString(key, null)

        return json?.run { gson.fromJson(this, type) }
    }
}
