package io.github.mrm1t.coroutineScheduler.graph

data class Vertex<T>(
    val tag: String,
    val value: T,
)
