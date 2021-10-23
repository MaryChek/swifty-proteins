package com.example.swiftyproteins.domain.interactor

import android.content.Context
import androidx.annotation.RawRes
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class FileInteractor(private val context: Context) {

    fun getFileListString(@RawRes fileResId: Int): List<String> {
        val inputStream: InputStream = context.resources.openRawResource(fileResId)
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        val fileBuilder = mutableListOf<String>()
        var newLine: String?
        while (bufferedReader.readLine().also { newLine = it } != null) {
            newLine?.let { line ->
                fileBuilder.add(line)
            }
        }
        return fileBuilder
    }
}