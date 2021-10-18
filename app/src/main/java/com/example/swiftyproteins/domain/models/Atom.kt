package com.example.swiftyproteins.domain.models

class Atom(
    val id: String,
    val name: String,
    val coordinate: Coordinate,
    val base: BaseAtom,
    val connectList: List<String>
) {
    class Coordinate(
        val x: Float,
        val y: Float,
        val z: Float
    )

    enum class BaseAtom {
        C, O, H, P, N, F, OTHER
    }
}