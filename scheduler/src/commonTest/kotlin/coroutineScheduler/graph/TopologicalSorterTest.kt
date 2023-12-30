package coroutineScheduler.graph

import io.github.mrm1t.coroutineScheduler.graph.DirectedEdge
import io.github.mrm1t.coroutineScheduler.graph.TopologicalSorter
import io.github.mrm1t.coroutineScheduler.graph.Vertex
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class TopologicalSorterTest {
    private val sut = TopologicalSorter()

    @Test
    fun givenCyclicGraphWithNoRootNodesWhenPerformTopologicalSortThenThrowException() {
        val vertices = listOf(IntVertex("foo", 1), IntVertex("bar", 1))
        val edges = listOf(DirectedEdge("foo", "bar"), DirectedEdge("bar", "foo"))

        assertFailsWith<IllegalArgumentException> {
            sut.performTopologicalSort(vertices, edges)
        }
    }

    @Test
    fun givenCyclicGraphWithRootNodeWhenPerformTopologicalSortThenThrowException() {
        val vertices =
            listOf(
                IntVertex("foo", 1),
                IntVertex("bar", 1),
                IntVertex("baz", 3),
            )
        val edges =
            listOf(
                DirectedEdge("foo", "bar"),
                DirectedEdge("bar", "baz"),
                DirectedEdge("baz", "bar"),
            )

        assertFailsWith<IllegalStateException> {
            sut.performTopologicalSort(vertices, edges)
        }
    }

    @Test
    fun givenAcyclicGraphWhenPerformTopologicalSortThenReturnVerticesInSortedOrder() {
        // example from https://en.wikipedia.org/wiki/Topological_sorting
        val vertices =
            listOf(
                IntVertex("5", 5),
                IntVertex("7", 7),
                IntVertex("3", 3),
                IntVertex("11", 11),
                IntVertex("8", 8),
                IntVertex("2", 2),
                IntVertex("9", 9),
                IntVertex("10", 10),
            )
        val edges =
            listOf(
                DirectedEdge("5", "11"),
                DirectedEdge("7", "11"),
                DirectedEdge("7", "8"),
                DirectedEdge("3", "8"),
                DirectedEdge("3", "10"),
                DirectedEdge("11", "2"),
                DirectedEdge("11", "9"),
                DirectedEdge("11", "10"),
                DirectedEdge("8", "9"),
            )

        val sortedVertices = sut.performTopologicalSort(vertices, edges)
        edges.forEach { edge ->
            assertTrue("item with tag `${edge.sourceTag}` should be before item with tag `${edge.destTag}`!") {
                sortedVertices.indexOfFirst { it.tag == edge.sourceTag } <
                    sortedVertices.indexOfFirst { it.tag == edge.destTag }
            }
        }
    }

    private inner class IntVertex(override val tag: String, val value: Int) : Vertex
}
