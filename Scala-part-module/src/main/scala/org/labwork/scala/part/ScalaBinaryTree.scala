package org.labwork.scala.part
import org.labwork.java.part.dataModel.binaryTree.BinaryTreeInterface
import org.labwork.java.part.service.Action
import org.labwork.java.part.service.Comparator

import scala.collection.mutable
import scala.collection.mutable.Stack
import scala.util.control.Breaks.break


//comparator.compare(node.getData(), data) > 0
class ScalaBinaryTree[T](private var comparator: Comparator[AnyRef]) extends BinaryTreeInterface {
  this.root = null
  private var root: ScalaNode = null
  private var size = 0

  // ---------------------------------------------------------------------- //
  // INSERTION AND BALANCING
  override def getSize: Int = size

  def isEmpty: Boolean = size <= 0

  override def insertElement(data: AnyRef): Boolean = {
    if (root == null) {
      root = new ScalaNode(data, 0, null)
      size += 1
      return true
    }
    var curr = root
    var parent: ScalaNode = null
    do {
      parent = curr
      if (comparator.compare(data, curr.data) > 0) {
        // перейти к левому поддереву
        curr = curr.getLeft
      }
      else if (comparator.compare(data, curr.data) < 0) {
        // смотреть на правильное поддерево
        curr = curr.getRight
      }
      else {
        // найдено, вставка не удалась
        return false
      }
    } while (curr != null)
    // Вставить узел
    if (comparator.compare(data, parent.data) > 0) {
      // Вставляем левого потомка
      parent.setLeft(new ScalaNode(data, ScalaNode.EH, parent))
      size += 1
    }
    else if (comparator.compare(data, parent.data) < 0) {
      // вставить правого ребенка
      parent.setRight(new ScalaNode(data, ScalaNode.EH, parent))
      size += 1
    }
    // назад снизу вверх, чтобы найти ближайший несбалансированный узел
    while (parent != null) {
      if (comparator.compare(data, parent.data) > 0) {
        // Вставляем узел в левое поддерево родителя
        parent.incrementBalanceCoeffiecient()
      }
      else if (comparator.compare(data, parent.data) < 0) {
        // Вставляем узел в правое поддерево родителя
        parent.decrementBalanceCoeffiecient()
      }
      if (parent.getBalanceCoeffiecient == ScalaNode.EH) {
        // Баланс этого узла равен 0, значение BF больше не корректируется вверх и вращение не требуется
        break //todo: break is not supported

      }
      if (Math.abs(parent.getBalanceCoeffiecient) == 2) {
        // Находим корневой узел наименьшего несбалансированного поддерева и поворачиваем его, чтобы исправить
        fixAfterInsertion(parent)
        break //todo: break is not supported

      }
      parent = parent.getParent
    }
    true
  }

  private def fixAfterInsertion(parent: ScalaNode): Unit = {
    if (parent.getBalanceCoeffiecient == 2) leftBalance(parent)
    if (parent.getBalanceCoeffiecient == -2) rightBalance(parent)
  }

  private def rotateRight(parent: ScalaNode): Unit = {
    if (parent != null) {
      val left = parent.getLeft
      left.setParent(parent.getParent)
      parent.setLeft(left.getRight)
      if (left.getRight != null) left.getRight.setParent(parent)
      if (parent.getParent == null) root = left
      else if (parent.getParent.getRight eq parent) parent.getParent.setRight(left)
      else parent.getParent.setLeft(left)
      left.setRight(parent)
      parent.setParent(left)
    }
  }

  private def rotateLeft(parent: ScalaNode): Unit = {
    if (parent != null) {
      val right = parent.getRight
      right.setParent(parent.getParent)
      parent.setRight(right.getLeft)
      if (right.getLeft != null) right.getLeft.setParent(parent)
      if (parent.getParent == null) root = right
      else if (parent.getParent.getLeft eq parent) parent.getParent.setLeft(right)
      else parent.getParent.setRight(right)
      right.setLeft(parent)
      parent.setParent(right)
    }
  }

  private def leftBalance(temp: ScalaNode): Unit = {
    val left = temp.getLeft
    left.getBalanceCoeffiecient match {
      case ScalaNode.LH => temp.setBalanceCoeffiecient(ScalaNode.EH)
        left.setBalanceCoeffiecient(ScalaNode.EH)
        rotateRight(temp)

      case ScalaNode.RH => val rd = left.getRight
        rd.getBalanceCoeffiecient match {
          case ScalaNode.LH => temp.setBalanceCoeffiecient(ScalaNode.RH)
            left.setBalanceCoeffiecient(ScalaNode.EH)

          case ScalaNode.EH => temp.setBalanceCoeffiecient(ScalaNode.EH)
            left.setBalanceCoeffiecient(ScalaNode.EH)

          case ScalaNode.RH => temp.setBalanceCoeffiecient(ScalaNode.EH)
            left.setBalanceCoeffiecient(ScalaNode.LH)

        }
        rd.setBalanceCoeffiecient(ScalaNode.EH)
        rotateLeft(temp.getLeft)
        rotateRight(temp)

      case ScalaNode.EH => temp.setBalanceCoeffiecient(ScalaNode.LH)
        left.setBalanceCoeffiecient(ScalaNode.RH)
        rotateRight(temp)

    }
  }

  private def rightBalance(temp: ScalaNode): Unit = {
    val right = temp.getRight
    right.getBalanceCoeffiecient match {
      case ScalaNode.LH => val ld = right.getLeft
        ld.getBalanceCoeffiecient match {
          case ScalaNode.LH => temp.setBalanceCoeffiecient(ScalaNode.EH)
            right.setBalanceCoeffiecient(ScalaNode.RH)

          case ScalaNode.EH => temp.setBalanceCoeffiecient(ScalaNode.EH)
            right.setBalanceCoeffiecient(ScalaNode.EH)

          case ScalaNode.RH => temp.setBalanceCoeffiecient(ScalaNode.LH)
            right.setBalanceCoeffiecient(ScalaNode.EH)

        }
        ld.setBalanceCoeffiecient(ScalaNode.EH)
        rotateRight(temp.getRight)
        rotateLeft(temp)

      case ScalaNode.RH => temp.setBalanceCoeffiecient(ScalaNode.EH)
        right.setBalanceCoeffiecient(ScalaNode.EH)
        rotateLeft(temp)

      case ScalaNode.EH => right.setBalanceCoeffiecient(ScalaNode.LH)
        temp.setBalanceCoeffiecient(ScalaNode.RH)
        rotateLeft(temp)

    }
  }

  override def deleteElement(data: AnyRef): Unit = {
    var currentElement = root
    var parentNode = root
    var isLeft = true
    while (comparator.compare(data, currentElement.getData) < 0) { // начинаем поиск узла
      parentNode = currentElement
      if (comparator.compare(data, currentElement.getData) < 0) { // Определяем, нужно ли движение влево?
        isLeft = true
        currentElement = currentElement.getLeft
      }
      else { // или движение вправо?
        isLeft = false
        currentElement = currentElement.getRight
      }
      if (currentElement == null) return // yзел не найден
    }
    if (currentElement.getLeft == null && currentElement.getRight == null) { // узел просто удаляется, если не имеет потомков
      if (currentElement eq root) // если узел - корень, то дерево очищается
        root = null
      else if (isLeft) parentNode.setLeft(null) // если нет - узел отсоединяется, от родителя
      else parentNode.setRight(null)
    }
    else if (currentElement.getRight == null) { // узел заменяется левым поддеревом, если правого потомка нет
      if (currentElement eq root) root = currentElement.getLeft
      else if (isLeft) parentNode.setLeft(currentElement.getLeft)
      else parentNode.setRight(currentElement.getLeft)
    }
    else if (currentElement.getLeft == null) { // узел заменяется правым поддеревом, если левого потомка нет
      if (currentElement eq root) root = currentElement.getRight
      else if (isLeft) parentNode.setLeft(currentElement.getRight)
      else parentNode.setRight(currentElement.getRight)
    }
    else { // если есть два потомка, узел заменяется преемником
      val heir = receiveHeir(currentElement) // поиск преемника для удаляемого узла

      if (currentElement eq root) root = heir
      else if (isLeft) parentNode.setLeft(heir)
      else parentNode.setRight(heir)
    }
    size -= 1
  }

  private def receiveHeir(elem: ScalaNode) = {
    var parentElement = elem
    var heirElement = elem
    var currentElement = elem.getRight // Переход к правому потомку

    while (currentElement != null) {
      parentElement = heirElement // потомка задаём как текущий узел

      heirElement = currentElement
      currentElement = currentElement.getLeft // переход к левому потомку

      // Пока остаются левые потомки
    }
    // Если преемник не является
    if (heirElement ne elem.getRight) { // создать связи между узлами
      heirElement.setLeft(elem.getLeft)
      parentElement.setLeft(heirElement.getRight)
      heirElement.setRight(elem.getRight)
    } // правым потомком,

    heirElement // возвращаем приемника

  }

  override def printTree(): Unit = { // метод для вывода дерева в консоль
    val globalStack = new mutable.Stack[ScalaNode] // общий стек для значений дерева

    globalStack.push(root)
    var spaceCount = 32 // начальное значение расстояния между элементами

    var isRowEmpty = false
    val separator = "-----------------------------------------------------------------"
    System.out.println(separator) // черта для указания начала нового дерева

    while (!isRowEmpty) {
      val localStack = new mutable.Stack[ScalaNode] // локальный стек для задания потомков элемента

      isRowEmpty = true
      for (j <- 0 until spaceCount) {
        System.out.print(' ')
      }
      while (globalStack.nonEmpty) { // покуда в общем стеке есть элементы
        val temp = globalStack.pop // берем следующий, при этом удаляя его из стека

        if (temp != null) {
          System.out.print(temp.getData) // выводим его значение в консоли

          localStack.push(temp.getLeft) // соохраняем в локальный стек, наследники текущего элемента

          localStack.push(temp.getRight)
          if (temp.getLeft != null || temp.getRight != null) isRowEmpty = false
        }
        else {
          System.out.print("__") // - если элемент пустой

          localStack.push(null)
          localStack.push(null)
        }
        for (j <- 0 until spaceCount * 2 - 2) {
          System.out.print(' ')
        }
      }
      System.out.println()
      spaceCount /= 2 // при переходе на следующий уровень расстояние между элементами каждый раз уменьшается

      while (!localStack.isEmpty) globalStack.push(localStack.pop) // перемещаем все элементы из локального стека в глобальный
    }
    System.out.println(separator) // подводим черту

  }

  override def forEach(a: Action[AnyRef]): Unit = {
    val globalStack = new mutable.Stack[ScalaNode] // общий стек для значений дерева

    globalStack.push(root)
    var isRowEmpty = false
    while (!isRowEmpty) {
      val localStack = new mutable.Stack[ScalaNode] // локальный стек для задания потомков элемента

      isRowEmpty = true
      while (!globalStack.isEmpty) { // покуда в общем стеке есть элементы
        val temp = globalStack.pop // берем следующий, при этом удаляя его из стека

        if (temp != null) {
          a.toDo(temp.getData)
          localStack.push(temp.getLeft) // соохраняем в локальный стек, наследники текущего элемента

          localStack.push(temp.getRight)
          if (temp.getLeft != null || temp.getRight != null) isRowEmpty = false
        }
        else {
          localStack.push(null)
          localStack.push(null)
        }
      }
      while (!localStack.isEmpty) globalStack.push(localStack.pop) // перемещаем все элементы из локального стека в глобальный
    }
  }

  override def setComparator(comparator: Comparator[AnyRef]): Unit = {
    this.comparator = comparator
  }
}

