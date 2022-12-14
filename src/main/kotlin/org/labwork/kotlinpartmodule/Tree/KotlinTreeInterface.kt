package org.labwork.kotlin.part.kotlinpartmodule.Tree

import org.labwork.java.part.dataModel.binaryTree.BinaryTreeInterface
import org.labwork.java.part.service.Action
import org.labwork.java.part.service.Comparator

interface KotlinTreeInterface : BinaryTreeInterface {
    override fun getSize(): Int

    override fun printTree()

    override fun insertElement(data: Any): Boolean

    override fun deleteElement(data: Any)

    override fun forEach(a: Action<Any>)

    override fun setComparator(comparator: Comparator<Any>)

}