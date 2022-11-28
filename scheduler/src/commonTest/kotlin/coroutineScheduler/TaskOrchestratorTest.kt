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
import kotlin.math.sqrt
import kotlin.test.Test
import kotlin.time.Duration.Companion.milliseconds

@ExperimentalCoroutinesApi
class TaskOrchestratorTest {

    @Test
    fun sampleTestDemonstratingDirectUsageMultiThreaded() = runTest {
        // specifying a dispatcher since the default one is single-threaded
        launch(Dispatchers.Default) {
            demoDirectUsage()
        }.join()
    }

    @Test
    fun sampleTestDemonstratingDslUsageMultiThreaded() = runTest {
        // specifying a dispatcher since the default one is single-threaded
        launch(Dispatchers.Default) {
            demoDslUsage()
        }.join()
    }

    private suspend fun demoDirectUsage() {
        val vertices = listOf<Task>(
            Task().apply {
                tag = "5"
                block = { doWork(10) }
            },
            Task().apply {
                tag = "7"
                block = { doWork(40) }
            },
            Task().apply {
                tag = "3"
                block = { doWork(100) }
            },
            Task().apply {
                tag = "11"
                block = { doWork(10); }
            },
            Task().apply {
                tag = "8"
                block = { doWork(50) }
            },
            Task().apply {
                tag = "2"
                block = { doWork(100) }
            },
            Task().apply {
                tag = "9"
                block = { doWork(10) }
            },
            Task().apply {
                tag = "10"
                block = { doWork(10) }
            }
        )
        val edges = listOf(
            DirectedEdge("5", "11"),
            DirectedEdge("7", "11"),
            DirectedEdge("7", "8"),
            DirectedEdge("3", "8"),
            DirectedEdge("3", "10"),
            DirectedEdge("11", "2"),
            DirectedEdge("11", "9"),
            DirectedEdge("11", "10"),
            DirectedEdge("8", "9")
        )

        TaskOrchestrator(vertices, edges).start()
    }

    private suspend fun demoDslUsage() {
        TaskOrchestrator.taskOrchestrator {
            addTask {
                tag("5")
                block { doWork(10) }
            }
            addTask {
                tag("7")
                block { doWork(40) }
            }
            addTask {
                tag("3")
                block { doWork(100) }
            }
            addTask {
                tag("11")
                dependsOn("5", "7")
                block { doWork(10); }
            }
            addTask {
                tag("8")
                dependsOn("3", "7")
                block { doWork(50) }
            }
            addTask {
                tag("2")
                dependsOn("11")
                block { doWork(100) }
            }
            addTask {
                tag("9")
                dependsOn("8", "11")
                block { doWork(10) }
            }
            addTask {
                tag("10")
                dependsOn("3", "11")
                block { doWork(10) }
            }
        }.start()
    }

    private suspend fun doWork(num: Int) = coroutineScope {
        delay(num.milliseconds)
    }
}
