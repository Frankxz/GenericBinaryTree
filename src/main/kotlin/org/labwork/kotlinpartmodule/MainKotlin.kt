package org.labwork.kotlin.part.kotlinpartmodule

import org.labwork.kotlin.part.kotlinpartmodule.Tree.KotlinTree
import org.labwork.java.part.view.GUI
import org.labwork.java.part.view.TreeActionListenerInterface
import org.labwork.kotlin.part.kotlinpartmodule.Tree.KotlinTreeInterface
import java.util.*

private fun testTree(count: Int) {
    val binaryTree: KotlinTree<Int> = KotlinTree(comparator_ = { o1, o2 -> o1 as Int - o2 as Int })

    val numbers = ArrayList<Int>()
    val r = Random()
    while (numbers.size < count) {
        val random = r.nextInt(count)
        if (!numbers.contains(random)) {
            numbers.add(random)
        }
    }
    val startInsertTime = System.nanoTime()
    for (number in numbers) {
        binaryTree.insertElement(number)
    }
    val stopInsertTime = System.nanoTime() - startInsertTime
    println("Total execution time for insert " + count + " random unique objects of type INTEGER in Tree: " + stopInsertTime / 1000000 + " ms")
    println(
        """
    SIZE AFTER INSERT:${binaryTree.getSize()}
    """.trimIndent()
    )
}

fun main() {
    testTree(500)
    val treeActionListener: TreeActionListenerInterface = TreeActionListener()
    GUI(treeActionListener)
}