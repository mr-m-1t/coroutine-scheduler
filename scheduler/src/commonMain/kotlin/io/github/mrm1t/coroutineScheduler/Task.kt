package io.github.mrm1t.coroutineScheduler

import io.github.mrm1t.coroutineScheduler.graph.Vertex
import kotlinx.coroutines.Deferred

class Task : Vertex {
    override lateinit var tag: String
    lateinit var block: suspend () -> Unit
    lateinit var jobWaitingForDependentTasks: Deferred<Unit>
}
