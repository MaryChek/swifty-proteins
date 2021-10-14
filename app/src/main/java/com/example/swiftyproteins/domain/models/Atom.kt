package com.example.swiftyproteins.domain.models

class Atom(
    val id: String,
    val name: String,
    val coordinate: Coordinate,
    val isHAtom: Boolean,
    val connectList: List<String>
) {
    class Coordinate(
        val x: Double,
        val y: Double,
        val z: Double
    )
}