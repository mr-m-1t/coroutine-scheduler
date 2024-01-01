package io.github.mrm1t.coroutineScheduler

import io.github.mrm1t.coroutineScheduler.graph.Vertex
import kotlinx.coroutines.Deferred

internal class TaskImpl<T : Any>(
    override val tag: T,
) : Task<T>, Vertex<T> {
    lateinit var block: suspend () -> Unit
    lateinit var jobWaitingForDependentTasks: Deferred<Unit>

    var dependsOn: List<T> = emptyList()

    override fun dependsOn(vararg tags: T) {
        dependsOn += tags
    }

    override fun block(blk: suspend () -> Unit) {
        this.block = blk
    }
}
