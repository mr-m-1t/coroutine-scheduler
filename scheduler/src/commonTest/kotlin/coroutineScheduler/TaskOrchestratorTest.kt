package coroutineScheduler

import io.github.mrm1t.coroutineScheduler.Task
import io.github.mrm1t.coroutineScheduler.TaskOrchestrator
import io.github.mrm1t.coroutineScheduler.graph.DirectedEdge
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.time.Duration.Companion.milliseconds

@ExperimentalCoroutinesApi
class TaskOrchestratorTest {
    @Test
    fun sampleTestDemonstratingDirectUsageMultiThreaded() =
        runTest {
            // specifying a dispatcher since the default one is single-threaded
            launch(Dispatchers.Default) {
                demoDirectUsage()
            }.join()
        }

    @Test
    fun sampleTestDemonstratingDslUsageMultiThreaded() =
        runTest {
            // specifying a dispatcher since the default one is single-threaded
            launch(Dispatchers.Default) {
                demoDslUsage()
            }.join()
        }

    private suspend fun demoDirectUsage() {
        val vertices =
            listOf(
                Task().apply {
                    tag = "1"
                    block = { doWork(10) }
                },
                Task().apply {
                    tag = "2"
                    block = { doWork(40) }
                },
                Task().apply {
                    tag = "3"
                    block = { doWork(100) }
                },
                Task().apply {
                    tag = "4"
                    block = {
                        doWork(10)
                    }
                },
                Task().apply {
                    tag = "5"
                    block = { doWork(50) }
                },
            )
        val edges =
            listOf(
                DirectedEdge("1", "2"),
                DirectedEdge("1", "3"),
                DirectedEdge("2", "4"),
                DirectedEdge("3", "4"),
                DirectedEdge("2", "5"),
                DirectedEdge("4", "5"),
            )

        TaskOrchestrator(vertices, edges).start()
    }

    private suspend fun demoDslUsage() {
        TaskOrchestrator.taskOrchestrator {
            addTask {
                tag("1")
                block { doWork(10) }
            }
            addTask {
                tag("2")
                dependsOn("1")
                block { doWork(40) }
            }
            addTask {
                tag("3")
                dependsOn("1")
                block { doWork(100) }
            }
            addTask {
                tag("4")
                dependsOn("2", "3")
                block {
                    doWork(10)
                }
            }
            addTask {
                tag("5")
                dependsOn("2", "4")
                block { doWork(50) }
            }
        }.start()
    }

    private suspend fun doWork(num: Int) =
        coroutineScope {
            delay(num.milliseconds)
        }
}
