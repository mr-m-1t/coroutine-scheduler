package io.github.mrm1t.coroutineScheduler.graph

internal interface Vertex<T: Any> {
    val tag: T
}
