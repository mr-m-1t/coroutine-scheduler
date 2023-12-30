package io.github.mrm1t.coroutineScheduler

import io.github.mrm1t.coroutineScheduler.graph.Vertex
import kotlinx.coroutines.Deferred

class Task<T: Any> : Vertex<T> {
    override lateinit var tag: T
    lateinit var block: suspend () -> Unit
    lateinit var jobWaitingForDependentTasks: Deferred<Unit>

    var dependsOn: List<T> = emptyList()

    fun tag(tag: T): Task<T> {
        this.tag = tag
        return this
    }

    fun dependsOn(vararg tags: T): Task<T> {
        dependsOn += tags
        return this
    }

    fun block(blk: suspend () -> Unit): Task<T> {
        this.block = blk
        return this
    }
}
