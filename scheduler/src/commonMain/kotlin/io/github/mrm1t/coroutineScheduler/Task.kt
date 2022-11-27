package io.github.mrm1t.coroutineScheduler

import io.github.mrm1t.coroutineScheduler.graph.Vertex
import kotlinx.coroutines.Deferred

class Task : Vertex {
    override lateinit var tag: String
    lateinit var block: suspend () -> Unit
    lateinit var jobWaitingForDependentTasks: Deferred<Unit>

    var dependsOn: List<String> = emptyList()

    fun tag(tag: String): Task {
        this.tag = tag
        return this
    }

    fun dependsOn(vararg tags: String): Task {
        dependsOn += tags
        return this
    }

    fun block(blk: suspend () -> Unit): Task {
        this.block = blk
        return this
    }
}
