package com.example.swiftyproteins.domain.interactor

import android.content.Context
import androidx.annotation.RawRes
import com.example.swiftyproteins.R
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class FileInteractor(private val context: Context) {

    @RawRes
    private val proteinsFileRes: Int = R.raw.ligands
    @RawRes
    private val atomsInfoFileRes: Int = R.raw.periodic_table

    fun getProteinsListString(): List<String> {
        val inputStream: InputStream = context.resources.openRawResource(proteinsFileRes)
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

    fun readAtomsInfo(): String {
        val inputStream: InputStream = context.resources.openRawResource(atomsInfoFileRes)
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        val jsonStringBuilder = StringBuilder()
        var newLine: String?
        while (bufferedReader.readLine().also { newLine = it } != null) {
            jsonStringBuilder.append(newLine)
            jsonStringBuilder.append("\n")
        }
        return jsonStringBuilder.toString()
    }
}