package io.github.mrm1t.coroutineScheduler

import io.github.mrm1t.coroutineScheduler.graph.DirectedEdge
import io.github.mrm1t.coroutineScheduler.graph.TopologicalSorter
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.supervisorScope

class TaskOrchestrator<T: Any>(
    private var tasks: List<Task<T>> = emptyList(),
    private var taskDependencies: List<DirectedEdge<T>> = emptyList(),
) {
    suspend fun start() =
        supervisorScope {
            val sortedTasks = TopologicalSorter().performTopologicalSort(vertices = tasks, edges = taskDependencies)
            sortedTasks.forEach { task ->
                val preReqTags = taskDependencies.filter { it.destTag == task.tag }.map { it.sourceTag }
                val preReqs = sortedTasks.filter { preReqTags.contains(it.tag) }.map { it.jobWaitingForDependentTasks }

                task.jobWaitingForDependentTasks =
                    async(this.coroutineContext + CoroutineName(task.tag.toString()), CoroutineStart.LAZY) {
                        try {
                            preReqs.awaitAll()
                            task.block()
                        } catch (e: CancellationException) {
                            println("${task.tag} failed due to $e")
                            throw e
                        }
                    }
            }

            sortedTasks.map { it.jobWaitingForDependentTasks }.awaitAll()
        }

    fun addTask(init: Task<T>.() -> Unit) {
        val job = Task<T>().apply(init)
        this.tasks += job
        this.taskDependencies += job.dependsOn.map { DirectedEdge(it, job.tag) }
    }

    companion object {
        fun <T: Any> taskOrchestrator(init: TaskOrchestrator<T>.() -> Unit): TaskOrchestrator<T> {
            return TaskOrchestrator<T>(emptyList(), emptyList()).apply(init)
        }
    }
}
