package org.labwork.scala.part


import org.labwork.java.part.ObjectBuilderFactory
import org.labwork.java.part.dataModel.binaryTree.BinaryTree
import org.labwork.java.part.dataModel.binaryTree.BinaryTreeInterface
import org.labwork.java.part.view.AbstractTreeActionListener
import org.labwork.java.part.service.TreeFileLoader

import java.io.FileNotFoundException


class TreeActionListener extends AbstractTreeActionListener {
   private var items = new ScalaBinaryTree[Object](null)

  override def onAdd(text: String): Unit = {
    if (text.isEmpty) return
    val data = builder.createFromString(text)
    if (items.insertElement(data)) listModel.addElement(data)
  }

  override def onRemove(index: Int): Unit = {
    val data = listModel.get(index)
    items.deleteElement(data)
    listModel.remove(index)
  }

  override def onSave(): Unit = {
    try TreeFileLoader.saveToFile(filename, items, builder)
    catch {
      case e: FileNotFoundException =>
        System.err.println("Unable to write list to a file")
        e.printStackTrace()
    }
  }

  override def onLoad(): Unit = {
    try {
      val temp = TreeFileLoader.loadFromFile(filename, builder, new ScalaBinaryTree[AnyRef](comparator = builder.getComparator))
      items = temp.asInstanceOf[ScalaBinaryTree[Object]]
      listModel.clear()
      items.forEach(el => listModel.addElement(el))
    } catch {
      case e: Exception =>
        System.err.println("Unable to read list from a file")
        e.printStackTrace()
    }
  }

  override def onSelectType(`type`: String): Unit = {
    try {
      builder = ObjectBuilderFactory.getBuilder(`type`)
      items.setComparator(builder.getComparator)
    } catch {
      case ignored: Exception =>
    }
  }

  override def print(): Unit = {
    items.printTree()
  }
}
