package org.labwork.scala.part

object ScalaNode {
  // Для балансировки
  val LH = 1
  val EH = 0
  val RH = -1
}

class ScalaNode(
                 var data: AnyRef,
                 var balanceCoeffiecient: Int,
                 var parent: ScalaNode
               )
{
  private var left: ScalaNode = _
  private var right: ScalaNode = _

  def getParent: ScalaNode = parent

  def setParent(parent: ScalaNode): Unit = {
    this.parent = parent
  }

  def getLeft: ScalaNode = left

  def setLeft(left: ScalaNode): Unit = {
    this.left = left
  }

  def getRight: ScalaNode = right

  def setRight(right: ScalaNode): Unit = {
    this.right = right
  }

  def getData: AnyRef = data

  def getBalanceCoeffiecient: Int = balanceCoeffiecient

  def setBalanceCoeffiecient(balanceCoeffiecient: Int): Unit = {
    this.balanceCoeffiecient = balanceCoeffiecient
  }

  def incrementBalanceCoeffiecient(): Unit = {
    this.balanceCoeffiecient += 1
  }

  def decrementBalanceCoeffiecient(): Unit = {
    this.balanceCoeffiecient -= 1
  }
}
