package io.github.mrm1t.coroutineScheduler

import io.github.mrm1t.coroutineScheduler.graph.DirectedEdge
import io.github.mrm1t.coroutineScheduler.graph.TopologicalSorter
import io.github.mrm1t.coroutineScheduler.graph.Vertex
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.supervisorScope
import kotlinx.datetime.Clock

class TaskOrchestrator(
    private val tasks: List<Vertex<Task>>,
    private val taskDependencies: List<DirectedEdge>,
) {
    // TODO:
    //  since child coroutine failures do not cause scope to fail
    //  figure out how to communicate failures to consumer
    suspend fun start() = supervisorScope {
        val sortedTasks = TopologicalSorter<Task>().performTopologicalSort(vertices = tasks, edges = taskDependencies)
        sortedTasks.forEach { task ->
            val preReqTags = taskDependencies.filter { it.destTag == task.tag }.map { it.sourceTag }
            val preReqs = sortedTasks.filter { preReqTags.contains(it.tag) }.map { it.value.jobWaitingForDependentTasks }

            task.value.jobWaitingForDependentTasks = async(this.coroutineContext + CoroutineName(task.tag)) {
                println("${task.tag} is waiting for ${preReqs.size} jobs ($preReqTags)")
                try {
                    preReqs.awaitAll()
                    val now = Clock.System.now()
                    task.value.block()
                    println("${task.tag} completed in ${Clock.System.now() - now}")
                } catch (e: CancellationException) {
                    println("${task.tag} failed due to $e")
                    throw e
                }
            }
        }

        println("All ${sortedTasks.size} tasks started in $this")
        sortedTasks.map { it.value.jobWaitingForDependentTasks }.awaitAll()
        println("All ${sortedTasks.size} tasks started in $this have completed")
    }
}
