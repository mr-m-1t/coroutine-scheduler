package io.github.mrm1t.coroutineScheduler.graph

data class DirectedEdge<T: Any>(
    val sourceTag: T,
    val destTag: T,
)
