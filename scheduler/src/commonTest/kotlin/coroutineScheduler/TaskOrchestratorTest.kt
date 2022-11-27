package coroutineScheduler

import io.github.mrm1t.coroutineScheduler.graph.DirectedEdge
import io.github.mrm1t.coroutineScheduler.Task
import io.github.mrm1t.coroutineScheduler.TaskOrchestrator
import io.github.mrm1t.coroutineScheduler.graph.Vertex
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
    fun sampleTestDemonstratingUsageMultiThreaded() = runTest {
        // specifying a dispatcher since the default one is single-threaded
        launch(Dispatchers.Default) {
            demoUsage()
        }.join()
    }

    private fun demoUsage() = runTest {
        val vertices = listOf(
            Vertex("5", Task { genPrime(1_000) }),
            Vertex("7", Task { genPrime(400) }),
            Vertex("3", Task { genPrime(1_000) }),
            Vertex("11", Task { genPrime(1_000); }),
            Vertex("8", Task { genPrime(500_000) }),
            Vertex("2", Task { genPrime(1_000) }),
            Vertex("9", Task { genPrime(1_000) }),
            Vertex("10", Task { genPrime(1_000) })
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
