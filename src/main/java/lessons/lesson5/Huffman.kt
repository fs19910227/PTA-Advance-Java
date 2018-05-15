package lessons.lesson5

//import lessons.Utills
//import java.io.File
//import java.io.InputStream
import java.util.*
import kotlin.collections.HashMap
import kotlin.coroutines.experimental.buildSequence

object Main3 {
//    fun getLinesByFile(path: String): Sequence<List<String>> {
//        return buildSequence {
//            val inputStream: InputStream = File(Utills.BASE_PATH, path).inputStream()
//            val bufferedReader = inputStream.bufferedReader()
//            while (true) {
//                val readLine = bufferedReader.readLine() ?: break
//                yield(readLine.split(" "))
//            }
//        }
//    }

    fun getLines(): Sequence<List<String>> {
        return buildSequence {
            while (true) {
                yield(readLine()!!.split(" "))
            }
        }
    }

    fun getLinesDefault(): Sequence<List<String>> {
        return buildSequence {
            while (true) {
                yield("7".split(" "))
                yield("A 1 B 1 C 1 D 3 E 3 F 6 G 6".split(" "))
                yield("1000".split(" "))
                /**
                 * G 11
                F 11
                E 01
                D 001
                C 0001
                A 00000
                B 00001
                 */
                for (i in 1..1000) {
                    yield("G 10".split(" "))
                    yield("F 11".split(" "))
                    yield("E 01".split(" "))
                    yield("D 001".split(" "))
                    yield("C 0001".split(" "))
                    yield("A 00000".split(" "))
                    yield("B 00001".split(" "))
                }
            }
        }
    }
}

class TreeBuilder {
    var score: Int = 0

    class Node(var name: String? = null,
               var left: Node? = null,
               var right: Node? = null,
               var parent: Node? = null,
               var weight: Int? = null) {
        var code: String = ""
        fun isLeaf(): Boolean {
            return left == null && right == null
        }

        override fun toString(): String {
            return "Node(name=$name, weight=$weight)"
        }


    }

    var root: Node = Node()

    fun build(map: HashMap<String, Int>): TreeBuilder {
        val list = map.map { Node(name = it.key, weight = it.value) }.sortedBy { it.weight }.toMutableList()
        while (true) {
            val left = list.removeAt(0)
            left.code = "0"
            val right = list.removeAt(0)
            right.code = "1"
            val newWeight = left.weight!! + right.weight!!
            val newNode = Node(weight = newWeight)
            newNode.left = left
            newNode.right = right
            left.parent = newNode
            right.parent = newNode
            if (list.size == 0) {
                root = newNode
                break
            }
            insert(list, newNode)
        }
        travelAndCounts()
        return this
    }

    private fun insert(list: MutableList<Node>, node: Node) {
        list.forEachIndexed { index, n ->
            if (n.weight!! > node.weight!!) {
                list.add(index, node)
                return
            }
        }
        list.add(node)
    }

    fun add(name: String, code: String, weight: Int): Boolean {
        var indexNode = root
        for (i in 0 until code.length) {
            val c = code[i]
            when (c) {
                '0' -> {
                    if (indexNode.left == null) {
                        indexNode.left = Node()
                    }

                    indexNode = indexNode.left!!
                }
                '1' -> {
                    if (indexNode.right == null) {
                        indexNode.right = Node()
                    }
                    indexNode = indexNode.right!!
                }
            }
        }
        if (indexNode.name != null) {
            return false
        }
        indexNode.name = name
        indexNode.weight = weight
        score += code.length * weight
        return true
    }

    fun travelAndCounts() {
        fun genCode(leaf: Node): Int {
            var sum = -1
            var index: Node? = leaf
            while (index != null) {
                sum++
                index = index.parent
            }
            return sum
        }

        val queue = LinkedList<Node>()
        queue.add(root)
        while (!queue.isEmpty()) {
            val pop = queue.poll()
            if (!pop.isLeaf()) {
                if (pop.right != null) {
                    queue.add(pop.right!!)
                }
                if (pop.left != null) {
                    queue.add(pop.left!!)
                }
            } else {
                val message = genCode(pop)
                val wei = message * pop.weight!!
                score += wei
            }
        }
    }

    fun travelAndCheck(): Boolean {
        val queue = LinkedList<Node>()
        queue.add(root)
        while (!queue.isEmpty()) {
            val pop = queue.poll()
            if (!pop.isLeaf()) {
                if (pop.name != null)
                    return false
                if (pop.right != null) {
                    queue.add(pop.right!!)
                }
                if (pop.left != null) {
                    queue.add(pop.left!!)
                }
            }
        }
        return true
    }

}

fun check(map: HashMap<String, Int>, iterator: Iterator<List<String>>, minScore: Int): String {
    val result = "No"
    val builder = TreeBuilder()
    val list: LinkedList<Pair<String, String>> = LinkedList()
    for (j in 1..map.size) {
        val (name, code) = iterator.next()
        list.add(Pair(name, code))
    }
    list.forEach {
        val add = builder.add(it.first, it.second, map[it.first]!!)
        if (!add)
            return result
    }
    val message = builder.travelAndCheck()
    if (message && minScore == builder.score) {
        return "Yes"
    } else {
        return result
    }

}

fun main(args: Array<String>) {
//    val start=System.currentTimeMillis()
//    val lines = Main3.getLinesByFile("lesson5/code.txt")
    val lines = Main3.getLines()
//    val lines = Main3.getLinesDefault()
    val iterator = lines.iterator()
    val pointNums = iterator.next().first().toInt()
    val map = HashMap<String, Int>()
    val codeStrings = iterator.next()
    for (i in 0 until pointNums * 2 step 2) {
        val key = codeStrings[i]
        val value = codeStrings[i + 1].toInt()
        map[key] = value
    }
    val minScore = TreeBuilder().build(map).score



    val checkNums = iterator.next().first().toInt()
    for (i in 1..checkNums) {
        val check = check(map, iterator, minScore)
        println(check)
    }
//    println(System.currentTimeMillis()-start)
}