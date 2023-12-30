package io.github.mrm1t.coroutineScheduler.graph

internal data class DirectedEdge<T: Any>(
    val sourceTag: T,
    val destinationTag: T,
)
