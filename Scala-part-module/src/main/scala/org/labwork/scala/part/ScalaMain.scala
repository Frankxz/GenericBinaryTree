package org.labwork.scala.part

import org.labwork.java.part.ObjectBuilderFactory
import org.labwork.java.part.dataModel.binaryTree.BinaryTree
import org.labwork.java.part.dataModel.builder.ObjectBuilder
import org.labwork.java.part.view.{GUI, TreeActionListener, TreeActionListenerInterface}

import scala.collection
import java.util
import java.util.{ArrayList, Random}

object ScalaMain {
  def testTree(count: Integer): Unit = {
    val binaryTree = new BinaryTree[AnyRef]((o1: AnyRef, o2: AnyRef) => o1.asInstanceOf[Integer] - o2.asInstanceOf[Integer])
    val numbers = new util.ArrayList[Integer]
    val r = new Random
    val startRandomCreateTime = System.nanoTime
    while (numbers.size < count) {
      val random = r.nextInt(count)
      if (!numbers.contains(random)) numbers.add(random)
    }
    val stopRandomCreateTime = System.nanoTime - startRandomCreateTime
    System.out.println("Time for creating unique random values: " + stopRandomCreateTime / 1000000 + " ms")
    val startInsertTime = System.nanoTime

    for (number <- 0 until numbers.size()) {
      binaryTree.insertElement(number)
    }
    val stopInsertTime = System.nanoTime - startInsertTime
    System.out.println("Total execution time for insert " + count + " random unique objects of type INTEGER in Tree: " + stopInsertTime / 1000000 + " ms")
    System.out.println("SIZE AFTER INSERT:" + binaryTree.getSize + "\n")
  }

  def main(args: Array[String]): Unit = {
    val treeActionListener = new TreeActionListener
    new GUI(treeActionListener)

    /*
    testTree(10000);
    testTree(25000);
    testTree(50000);
    testTree(100000);
    testTree(200000);
    testTree(400000);
    testTree(800000);
  */

  }
}