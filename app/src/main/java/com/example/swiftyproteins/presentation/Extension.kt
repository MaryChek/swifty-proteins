package com.example.swiftyproteins.presentation

import android.util.Log
import androidx.annotation.ColorRes
import androidx.fragment.app.Fragment
import java.lang.Exception

object Extension {
    fun Fragment.getColor(@ColorRes colorResId: Int): Int =
        requireContext().getColor(colorResId)

    fun Any.logE(message: String?, e: Exception? = null) {
        val logTag = this::class.java.simpleName
        Log.e(logTag, message, e)
    }

    fun Any.logD(message: String) {
        val logTag = this::class.java.simpleName
        Log.d(logTag, message)
    }
}