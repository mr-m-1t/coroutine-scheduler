package io.github.mrm1t.coroutineScheduler.graph

class TopologicalSorter {

    /**
     * Performs a topological sort of the given graph using Kuhn's algorithm.
     *
     * @param[vertices] the vertices of the graph to be sorted
     * @param[edges] the edges of the graph to be sorted
     *
     * @return The vertices in a topologically sorted list
     *
     * @throws[IllegalArgumentException] if the given graph has no vertices with in-degree of 0
     * @throws[IllegalStateException] if the given graph has a cycle
     */
    fun <T: Vertex>performTopologicalSort(
        vertices: List<T>,
        edges: List<DirectedEdge>,
    ): List<T> {
        val sortedVertices = mutableListOf<T>()
        val verticesWithNoIncoming = mutableListOf<T>()
        val edgesRemainingInGraph = edges.toMutableList()
        val verticesInGraph = vertices.toMutableList()

        verticesInGraph
            .filter { v -> edges.none { e -> e.destTag == v.tag } }
            .forEach {
                verticesWithNoIncoming.add(it)
                verticesInGraph.remove(it)
            }

        if (verticesWithNoIncoming.isEmpty()) {
            throw IllegalArgumentException("Graph is cyclic! No root vertices!")
        }

        while (verticesWithNoIncoming.isNotEmpty()) {
            val vertex = verticesWithNoIncoming.removeFirst()
            verticesInGraph.remove(vertex)
            sortedVertices.add(vertex)
            edgesRemainingInGraph.removeAll { it.sourceTag == vertex.tag }

            verticesInGraph
                .filter { v -> edgesRemainingInGraph.none { e -> e.destTag == v.tag } }
                .forEach {
                    verticesWithNoIncoming.add(it)
                    verticesInGraph.remove(it)
                }
        }

        if (edgesRemainingInGraph.isNotEmpty()) {
            throw IllegalStateException("Graph is cyclic! Remaining edges: $edges")
        }

        return sortedVertices
    }
}
