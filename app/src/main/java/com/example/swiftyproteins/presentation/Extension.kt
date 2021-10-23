package com.example.swiftyproteins.presentation

import android.app.Activity
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorRes
import androidx.fragment.app.Fragment
import com.example.swiftyproteins.domain.models.Atom
import com.google.ar.sceneform.math.Vector3
import java.lang.Exception

fun Fragment.getColor(@ColorRes colorResId: Int): Int =
    requireContext().getColor(colorResId)

fun Fragment.hideKeyboard() {
    val inputManager: InputMethodManager? =
        activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
    inputManager?.hideSoftInputFromWindow(activity?.currentFocus?.windowToken, 0)
}

fun Any.logE(message: String?, e: Exception? = null) {
    val logTag = this::class.java.simpleName
    Log.e(logTag, message, e)
}

fun Any.logD(message: String) {
    val logTag = this::class.java.simpleName
    Log.d(logTag, message)
}

fun Atom.Coordinate.toVec3() =
    Vector3(x, y, z)

fun String.splitOnlyWords(): List<String> =
    split(" ").filter { string ->
        string != ""
    }