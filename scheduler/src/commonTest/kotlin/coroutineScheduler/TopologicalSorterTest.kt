package coroutineScheduler

import io.github.mrm1t.coroutineScheduler.DirectedEdge
import io.github.mrm1t.coroutineScheduler.TopologicalSorter
import io.github.mrm1t.coroutineScheduler.Vertex
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class TopologicalSorterTest {
    private val sut = TopologicalSorter<Int>()

    @Test
    fun givenCyclicGraphWithNoRootNodesWhenPerformTopologicalSortThenThrowException() {
        val vertices = listOf(Vertex("foo", 1), Vertex("bar", 1))
        val edges = listOf(DirectedEdge("foo", "bar"), DirectedEdge("bar", "foo"))

        assertFailsWith<IllegalArgumentException> {
            sut.performTopologicalSort(vertices, edges)
        }
    }

    @Test
    fun givenCyclicGraphWithRootNodeWhenPerformTopologicalSortThenThrowException() {
        val vertices = listOf(
            Vertex("foo", 1),
            Vertex("bar", 1),
            Vertex("baz", 3)
        )
        val edges = listOf(
            DirectedEdge("foo", "bar"),
            DirectedEdge("bar", "baz"),
            DirectedEdge("baz", "bar")
        )

        assertFailsWith<IllegalStateException> {
            sut.performTopologicalSort(vertices, edges)
        }
    }

    @Test
    fun givenAcyclicGraphWhenPerformTopologicalSortThenReturnVerticesInSortedOrder() {
        // example from https://en.wikipedia.org/wiki/Topological_sorting
        val vertices = listOf(
            Vertex("5", 5),
            Vertex("7", 7),
            Vertex("3", 3),
            Vertex("11", 11),
            Vertex("8", 8),
            Vertex("2", 2),
            Vertex("9", 9),
            Vertex("10", 10)
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

        val sortedVertices = sut.performTopologicalSort(vertices, edges)
        edges.forEach { edge ->
            assertTrue("item with tag `${edge.sourceTag}` should be before item with tag `${edge.destTag}`!") {
                sortedVertices.indexOfFirst { it.tag == edge.sourceTag } <
                    sortedVertices.indexOfFirst { it.tag == edge.destTag }
            }
        }
    }
}
