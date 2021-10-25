package com.example.swiftyproteins.data.model

import com.google.gson.annotations.SerializedName

data class AtomsInfo(
    val elements: List<AtomInfo>,
) {
    class AtomInfo(
        val name: String,
        val appearance: String?,
        @SerializedName("atomic_mass")
        val atomicMass: String,
        val boil: String?,
        val category: String,
        val color: String?,
        val density: String?,
        @SerializedName("discovered_by")
        val discoveredBy: String?,
        val melt: String?,
        @SerializedName("molar_heat")
        val molarHeat: String?,
        @SerializedName("named_by")
        val namedBy: String?,
        val number: Int,
        val period: Int,
        val phase: String,
        val source: String,
        @SerializedName("spectral_img")
        val spectralImg: String?,
        val summary: String,
        val symbol: String,
        @SerializedName("x_pos")
        val xPos: Int,
        @SerializedName("y_pos")
        val yPos: Int,
        val shells: List<Int>
    )
}