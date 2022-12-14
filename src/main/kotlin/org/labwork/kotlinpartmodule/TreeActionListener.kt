package org.labwork.kotlin.part.kotlinpartmodule

import org.labwork.java.part.ObjectBuilderFactory
import org.labwork.kotlin.part.kotlinpartmodule.Tree.KotlinTree
import org.labwork.kotlin.part.kotlinpartmodule.Tree.KotlinTreeInterface
import org.labwork.java.part.service.TreeFileLoader
import org.labwork.java.part.view.AbstractTreeActionListener
import java.io.FileNotFoundException

open class TreeActionListener : AbstractTreeActionListener() {
    private var items: KotlinTreeInterface = KotlinTree<Any>(null)
    override fun onAdd(text: String) {
        if (text.isEmpty()) return
        val data = builder.createFromString(text)
        if (items.insertElement(data)) {
            listModel.addElement(data)
        }
    }

    override fun onRemove(index: Int) {
        val data = listModel[index]
        items.deleteElement(data)
        listModel.remove(index)
    }

    override fun onSave() {
        try {
            TreeFileLoader.saveToFile(filename, items, builder)
        } catch (e: FileNotFoundException) {
            System.err.println("Unable to write list to a file")
            e.printStackTrace()
        }
    }

    override fun onLoad() {
        try {
            items = TreeFileLoader.loadFromFile(filename, builder, KotlinTree<Any>(builder.comparator)) as KotlinTreeInterface
            listModel.clear()
            items.forEach { element: Any? -> listModel.addElement(element) }
        } catch (e: Exception) {
            System.err.println("Unable to read list from a file")
            e.printStackTrace()
        }
    }

    override fun onSelectType(type: String) {
        try {
            builder = ObjectBuilderFactory.getBuilder(type)
            items.setComparator(builder.comparator)
        } catch (ignored: Exception) {
        }
    }

    override fun print() {
        items.printTree()
    }
}