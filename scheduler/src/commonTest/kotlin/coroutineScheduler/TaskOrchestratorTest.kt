package coroutineScheduler

import io.github.mrm1t.coroutineScheduler.Task
import io.github.mrm1t.coroutineScheduler.TaskOrchestrator
import io.github.mrm1t.coroutineScheduler.graph.DirectedEdge
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import kotlin.math.sqrt
import kotlin.test.Test

@ExperimentalCoroutinesApi
class TaskOrchestratorTest {

    @Test
    fun sampleTestDemonstratingDirectUsageMultiThreaded() = runTest {
        // specifying a dispatcher since the default one is single-threaded
        launch(Dispatchers.Default) {
            demoDirectUsage()
        }.join()
    }

    private fun demoDirectUsage() = runTest {
        val vertices = listOf<Task>(
            Task().apply {
                tag = "5"
                block = { genPrime(1_000) }
            },
            Task().apply {
                tag = "7"
                block = { genPrime(400) }
            },
            Task().apply {
                tag = "3"
                block = { genPrime(1_000) }
            },
            Task().apply {
                tag = "11"
                block = { genPrime(1_000); }
            },
            Task().apply {
                tag = "8"
                block = { genPrime(500_000) }
            },
            Task().apply {
                tag = "2"
                block = { genPrime(1_000) }
            },
            Task().apply {
                tag = "9"
                block = { genPrime(1_000) }
            },
            Task().apply {
                tag = "10"
                block = { genPrime(1_000) }
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

    private suspend fun genPrime(num: Int) = coroutineScope {
        require(num > 0) { "There is no zeroth prime." }
        generateSequence(1) { it + 1 }
            .filterNot { value -> (2..sqrt(value.toDouble()).toInt()).any { value % it == 0 } }
            .elementAt(num)
    }
}
