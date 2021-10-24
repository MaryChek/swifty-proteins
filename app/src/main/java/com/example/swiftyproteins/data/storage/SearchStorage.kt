package com.example.swiftyproteins.data.storage

import android.content.Context
import androidx.core.content.edit

class SearchStorage(context: Context) {
    private val sp = context.getSharedPreferences(NAME_STORAGE, Context.MODE_PRIVATE)

    fun putSearchString(value: String?) =
        sp.edit(true) {
            putString(SEARCH_KEY, value)
        }

    fun getSearchString(default: String? = null) =
        sp.getString(SEARCH_KEY, default)

    companion object {
        private const val NAME_STORAGE = "prefs"
        private const val SEARCH_KEY = "search"
    }
}