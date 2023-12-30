package io.github.mrm1t.coroutineScheduler

import io.github.mrm1t.coroutineScheduler.graph.Vertex
import kotlinx.coroutines.Deferred

class TaskImpl<T: Any> : Task<T>, Vertex<T> {
    override lateinit var tag: T
    lateinit var block: suspend () -> Unit
    lateinit var jobWaitingForDependentTasks: Deferred<Unit>

    var dependsOn: List<T> = emptyList()

    override fun tag(tag: T) {
        this.tag = tag
    }

    override fun dependsOn(vararg tags: T) {
        dependsOn += tags
    }

    override fun block(blk: suspend () -> Unit) {
        this.block = blk
    }
}
