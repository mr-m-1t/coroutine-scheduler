package io.github.mrm1t.coroutineScheduler

import kotlinx.coroutines.Deferred

data class Task(val block: suspend () -> Unit) {
    lateinit var jobWaitingForDependentTasks: Deferred<Unit>
}
