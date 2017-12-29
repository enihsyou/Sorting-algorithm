import java.util.*

data class Vertex(val name: String)
data class Edge(val from: Vertex, val to: Vertex, val name: String = "$from -> $to")

class Graph(val vertexes: List<Vertex>, val edges: List<Edge>) {//todo 应该使用Set

    val adjacencyMatrix = Array(vertexes.size) { row ->
        BooleanArray(vertexes.size) { col ->
            edges.filter { it.from == vertexes[row] && it.to == vertexes[col] }.any()
        }
    }
    val adjacencyList = List(vertexes.size) { vert ->
        val list = mutableListOf<Vertex>()
        edges.filter { it.from == vertexes[vert] }.forEach { list.add(it.to) }
        list.toList()
    }

    /*记录已访问的节点*/
    private val matrixVisited = mutableSetOf<Int>()
    private val listVisited = mutableSetOf<Vertex>()

    /*滤出接下来要访问的节点*/
    private val matrixMapToValue = { i: BooleanArray ->
        i
            .mapIndexed { index, b -> if (b) index else -1 }
            .filter { it >= 0 }
            .filter { !matrixVisited.contains(it) }
    }
    private val listMapToValue = { i: List<Vertex> ->
        i
            .filter { !listVisited.contains(it) }
//            .map { vertexes.indexOf(it) }
    }

    fun dfsMatrix(source: Vertex): List<Vertex> {
        matrixVisited.clear()

        fun dfs(which: Int) {
            if (matrixVisited.contains(which))
                return
            matrixVisited += which
            adjacencyMatrix[which]
                .run(matrixMapToValue)
                .forEach(::dfs)
        }

        assert(source in vertexes) { "$source 应该在顶点列表里" }

        /*初始调用*/
        dfs(vertexes.indexOf(source))

        return matrixVisited.map { vertexes[it] }
    }

    fun bfsMatrix(source: Vertex): List<Vertex> {
        matrixVisited.clear()

        val next: Queue<Int> = LinkedList<Int>()

        fun bfs(which: Int) {
            next.add(which)
            while (next.isNotEmpty()) {
                val next_vertex = next.poll()
                if (matrixVisited.contains(next_vertex))
                    continue
                matrixVisited += next_vertex
                adjacencyMatrix[next_vertex]
                    .run(matrixMapToValue)
                    .forEach { next.add(it) }
            }
        }

        assert(source in vertexes) { "$source 应该在顶点列表里" }

        /*初始调用*/
        bfs(vertexes.indexOf(source))

        return matrixVisited.map { vertexes[it] }
    }

    fun dfsList(source: Vertex): List<Vertex> {
        listVisited.clear()

        fun dfs(which: Vertex) {
            if (listVisited.contains(which))
                return
            listVisited += which
            adjacencyList[vertexes.indexOf(which)]
                .run(listMapToValue)
                .forEach(::dfs)
        }

        assert(source in vertexes) { "$source 应该在顶点列表里" }

        /*初始调用*/
        dfs(source)

        return listVisited.toList()
    }

    fun bfsList(source: Vertex): List<Vertex> {
        listVisited.clear()

        val next: Queue<Vertex> = LinkedList<Vertex>()

        fun bfs(which: Vertex) {
            next.add(which)
            while (next.isNotEmpty()) {
                val next_vertex = next.poll()
                if (listVisited.contains(next_vertex))
                    continue

                listVisited += next_vertex
                adjacencyList[vertexes.indexOf(next_vertex)]
                    .run(listMapToValue)
                    .forEach { next.add(it) }
            }
        }

        assert(source in vertexes) { "$source 应该在顶点列表里" }
        /*初始调用*/
        bfs(source)

        return listVisited.toList()
    }
}

fun main(args: Array<String>) {
    val a = Vertex("a")
    val b = Vertex("b")
    val c = Vertex("c")
    val d = Vertex("d")
    val e = Vertex("e")
    val graph = Graph(listOf(
        a, b, c, d, e
    ), listOf(
        Edge(a, b),
        Edge(a, c),
        Edge(b, d),
        Edge(b, e)
    ))
    println("先输入点的名字，以一个空行分隔")
    println("接下来输入<a, b>形式的二元组，代表从a到b有条有向边。最后以空行结束")
    println("例如：")
    println("a b c d e")
    println()
    println("a b")
    println("a c")
    println("b d")
    println("b e")
    println()
    println("结果如下：")
    println("DFS邻接矩阵: ${graph.dfsMatrix(a).joinToString { it.name }}")
    println("DFS邻接表: ${graph.dfsList(a).joinToString { it.name }}")
    println("BFS邻接矩阵: ${graph.bfsMatrix(a).joinToString { it.name }}")
    println("BFS邻接表: ${graph.bfsList(a).joinToString { it.name }}")

    println()
    println("接下来试试：")
    userInput()
}

fun userInput() {
    val s = Scanner(System.`in`)
    with(s) {
        val vertexList = nextLine()
            .split("\\s+".toRegex())
            .map(::Vertex)

        nextLine()

        val edgeList = mutableListOf<Edge>()
        while (hasNextLine()) {
            val nextLine = nextLine()
            if (nextLine.isEmpty())
                break
            val split = nextLine.split("\\s+".toRegex())
                .map(::Vertex)
                .filter { vertexList.contains(it) }
            if (split.size != 2) error("一行应该只有有效的两个顶点名字")
            edgeList += Edge(split[0], split[1])
        }
        val graph = Graph(
            vertexList, edgeList
        )

        println("输入完成")
        println("邻接矩阵：")
        graph.adjacencyMatrix
            .map { it.joinToString { if (it) "1" else "0"} }
            .forEach(::println)

        println("邻接表：")
        graph.adjacencyList
            .map { it.joinToString() }
            .forEachIndexed{index, s -> println("${vertexList[index]} -> $s") }
        println("接下来输入搜索起始顶点: ")
        val from = Vertex(nextLine())
        println("结果如下：")


        println("DFS邻接矩阵: ${graph.dfsMatrix(from).joinToString { it.name }}")
        println("DFS邻接表　: ${graph.dfsList(from).joinToString { it.name }}")
        println("BFS邻接矩阵: ${graph.bfsMatrix(from).joinToString { it.name }}")
        println("BFS邻接表　: ${graph.bfsList(from).joinToString { it.name }}")
    }
}
