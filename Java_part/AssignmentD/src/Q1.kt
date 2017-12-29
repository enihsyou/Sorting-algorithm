data class Vertex(val name: String)
data class Edge(val from: Vertex, val to: Vertex, val name: String = "")

class Graph(val vertexs: List<Vertex>, val edges: List<Edge>) {

    val adjacencyMatrix = Array(vertexs.size) { row ->
        BooleanArray(vertexs.size) { col ->
            edges.filter { it.from == vertexs[row] && it.to == vertexs[col] }.any()
        }
    }
    val adjacencyList = List(vertexs.size) { vert ->
        val list = mutableListOf<Vertex>()
        edges.filter { it.from == vertexs[vert] }.forEach { list.add(it.to) }
        list.toList()
    }

    fun dfsMatrix() {
        val visited = mutableSetOf<Vertex>()
        val dfs = ():Unit->
        adjacencyMatrix.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { colIndex, b ->

            }
        }
    }

    fun bfsMatrix() {
        val action: (Int, BooleanArray) -> Unit = { rowIndex, row ->
            row.forEachIndexed { colIndex, b ->

            }
        }
        adjacencyMatrix.forEachIndexed(action)
    }

    fun dfsList() {
    }

    fun bfxList() {
    }
}

fun main(args: Array<String>) {
    val a = Vertex("a")
    val b = Vertex("b")
    val c = Vertex("c")
    val d = Vertex("d")
    val graph = Graph(listOf(
        a, b, c, d
    ), listOf(
        Edge(a, b),
        Edge(a, c),
        Edge(c, d)
    ))
    println(graph)
}
