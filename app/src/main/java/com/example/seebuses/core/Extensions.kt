package com.example.seebuses.core

import com.example.seebuses.core.entities.BlockElement

fun Array<BlockElement>.contains(whatToContain: Any) {
    this.any { x -> x == whatToContain }
}

fun List<Array<String>>.clear() {
    dropWhile { this.isNotEmpty() }
}
