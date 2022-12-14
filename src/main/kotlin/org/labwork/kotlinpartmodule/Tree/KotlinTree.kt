package org.labwork.kotlin.part.kotlinpartmodule.Tree

import org.labwork.java.part.service.Action
import java.util.*
import org.labwork.java.part.service.Comparator
import org.labwork.kotlin.part.kotlinpartmodule.Tree.KotlinNode
import kotlin.math.abs

//comparator.compare(node.getData(), data) > 0
class KotlinTree<T>(comparator_: Comparator<Any>?) : KotlinTreeInterface {

    private var root: KotlinNode? = null
    private var size = 0
    private lateinit var comparator: Comparator<Any>

    init {
        if (comparator_ != null) {
            this.comparator = comparator_
        }
        this.root = null
    }

    // ---------------------------------------------------------------------- //
    // INSERTION AND BALANCING

    // ---------------------------------------------------------------------- //
    // INSERTION AND BALANCING
    override fun getSize(): Int {
        return size
    }

    fun isEmpty(): Boolean {
        return size <= 0
    }

    override fun insertElement(data: Any): Boolean {
        if (root == null) {
            root = KotlinNode(data, 0, null)
            size++
            return true
        }
        var curr = root
        var parent: KotlinNode?
        do {
            parent = curr
            curr = if (curr!!.getData()?.let { comparator.compare(data, it) }!! > 0) {
                // перейти к левому поддереву
                curr.getLeft()
            } else if (curr.getData()?.let { comparator.compare(data, it) }!! < 0) {
                // смотреть на правильное поддерево
                curr.getRight()
            } else {
                // найдено, вставка не удалась
                return false
            }
        } while (curr != null)
        // Вставить узел
        if (parent!!.getData()?.let { comparator.compare(data, it) }!! > 0) {
            // Вставляем левого потомка
            parent.setLeft(KotlinNode(data, KotlinNode.EH, parent))
            size++
        } else if (parent.getData()?.let { comparator.compare(data, it) }!! < 0) {
            // вставить правого ребенка
            parent.setRight(KotlinNode(data, KotlinNode.EH, parent))
            size++
        }

        // назад снизу вверх, чтобы найти ближайший несбалансированный узел
        while (parent != null) {
            if (parent.getData()?.let { comparator.compare(data, it) }!! > 0) {
                // Вставляем узел в левое поддерево родителя
                parent.incrementBalanceCoeffiecient()
            } else if (comparator.compare(data, parent.getData()!!) < 0) {
                // Вставляем узел в правое поддерево родителя
                parent.decrementBalanceCoeffiecient()
            }
            if (parent.getBalanceCoeffiecient() == KotlinNode.EH) {
                // Баланс этого узла равен 0, значение BF больше не корректируется вверх и вращение не требуется
                break
            }
            if (abs(parent.getBalanceCoeffiecient()) == 2) {
                // Находим корневой узел наименьшего несбалансированного поддерева и поворачиваем его, чтобы исправить
                fixAfterInsertion(parent)
                break
            }
            parent = parent.getParent()
        }
        return true
    }

    private fun fixAfterInsertion(parent: KotlinNode) {
        if (parent.getBalanceCoeffiecient() == 2) {
            leftBalance(parent)
        }
        if (parent.getBalanceCoeffiecient() == -2) {
            rightBalance(parent)
        }
    }

    private fun rotateRight(parent: KotlinNode?) {
        if (parent != null) {
            val left = parent.getLeft()
            left?.setParent(parent.getParent())
            if (left != null) {
                parent.setLeft(left.getRight())
            }
            if (left != null) {
                if (left.getRight() != null) {
                    left.getRight()!!.setParent(parent)
                }
            }
            if (parent.getParent() == null) {
                root = left
            } else if (parent.getParent()!!.getRight() === parent) {
                parent.getParent()!!.setRight(left)
            } else {
                parent.getParent()!!.setLeft(left)
            }
            left?.setRight(parent)
            parent.setParent(left)
        }
    }

    private fun rotateLeft(parent: KotlinNode?) {
        if (parent != null) {
            val right = parent.getRight()
            right?.setParent(parent.getParent())
            if (right != null) {
                parent.setRight(right.getLeft())
            }
            if (right != null) {
                if (right.getLeft() != null) {
                    right.getLeft()!!.setParent(parent)
                }
            }
            if (parent.getParent() == null) root = right
            else if (parent.getParent()!!.getLeft() === parent) parent.getParent()!!.setLeft(right)
            else parent.getParent()!!.setRight(right)
            right?.setLeft(parent)
            parent.setParent(right)
        }
    }

    private fun leftBalance(temp: KotlinNode) {
        val left = temp.getLeft()
        if (left != null) {
            when (left.getBalanceCoeffiecient()) {
                KotlinNode.LH -> {
                    temp.setBalanceCoeffiecient(KotlinNode.EH)
                    left.setBalanceCoeffiecient(KotlinNode.EH)
                    rotateRight(temp)
                }

                KotlinNode.RH -> {
                    val rd = left.getRight()
                    if (rd != null) {
                        when (rd.getBalanceCoeffiecient()) {
                            KotlinNode.LH -> {
                                temp.setBalanceCoeffiecient(KotlinNode.RH)
                                left.setBalanceCoeffiecient(KotlinNode.EH)
                            }

                            KotlinNode.EH -> {
                                temp.setBalanceCoeffiecient(KotlinNode.EH)
                                left.setBalanceCoeffiecient(KotlinNode.EH)
                            }

                            KotlinNode.RH -> {
                                temp.setBalanceCoeffiecient(KotlinNode.EH)
                                left.setBalanceCoeffiecient(KotlinNode.LH)
                            }
                        }
                    }
                    rd?.setBalanceCoeffiecient(KotlinNode.EH)
                    rotateLeft(temp.getLeft())
                    rotateRight(temp)
                }

                KotlinNode.EH -> {
                    temp.setBalanceCoeffiecient(KotlinNode.LH)
                    left.setBalanceCoeffiecient(KotlinNode.RH)
                    rotateRight(temp)
                }
            }
        }
    }

    private fun rightBalance(temp: KotlinNode) {
        val right = temp.getRight()
        if (right != null) {
            when (right.getBalanceCoeffiecient()) {
                KotlinNode.LH -> {
                    val ld = right.getLeft()
                    if (ld != null) {
                        when (ld.getBalanceCoeffiecient()) {
                            KotlinNode.LH -> {
                                temp.setBalanceCoeffiecient(KotlinNode.EH)
                                right.setBalanceCoeffiecient(KotlinNode.RH)
                            }

                            KotlinNode.EH -> {
                                temp.setBalanceCoeffiecient(KotlinNode.EH)
                                right.setBalanceCoeffiecient(KotlinNode.EH)
                            }

                            KotlinNode.RH -> {
                                temp.setBalanceCoeffiecient(KotlinNode.LH)
                                right.setBalanceCoeffiecient(KotlinNode.EH)
                            }
                        }
                    }
                    ld?.setBalanceCoeffiecient(KotlinNode.EH)
                    rotateRight(temp.getRight())
                    rotateLeft(temp)
                }

                KotlinNode.RH -> {
                    temp.setBalanceCoeffiecient(KotlinNode.EH)
                    right.setBalanceCoeffiecient(KotlinNode.EH)
                    rotateLeft(temp)
                }

                KotlinNode.EH -> {
                    right.setBalanceCoeffiecient(KotlinNode.LH)
                    temp.setBalanceCoeffiecient(KotlinNode.RH)
                    rotateLeft(temp)
                }
            }
        }
    }

    override fun deleteElement(data: Any) {
        var currentElement = root
        var parentNode = root
        var isLeft = true
        while (currentElement!!.getData()?.let { comparator.compare(data, it) }!! < 0) { // начинаем поиск узла
            parentNode = currentElement
            if (currentElement.getData()?.let { comparator.compare(data, it) }!! < 0) { // Определяем, нужно ли движение влево?
                isLeft = true
                currentElement = currentElement.getLeft()
            } else { // или движение вправо?
                isLeft = false
                currentElement = currentElement.getRight()
            }
            if (currentElement == null) return  // yзел не найден
        }
        if (currentElement.getLeft() == null && currentElement.getRight() == null) { // узел просто удаляется, если не имеет потомков
            if (currentElement === root) // если узел - корень, то дерево очищается
                root = null
            else if (isLeft) parentNode!!.setLeft(null) // если нет - узел отсоединяется, от родителя
            else parentNode!!.setRight(null)
        } else if (currentElement.getRight() == null) { // узел заменяется левым поддеревом, если правого потомка нет
            if (currentElement === root) root = currentElement.getLeft()
            else if (isLeft) parentNode!!.setLeft(currentElement.getLeft())
            else parentNode!!.setRight(currentElement.getLeft())
        } else if (currentElement.getLeft() == null) { // узел заменяется правым поддеревом, если левого потомка нет
            if (currentElement === root) root = currentElement.getRight()
            else if (isLeft) parentNode!!.setLeft(currentElement.getRight())
            else parentNode!!.setRight(currentElement.getRight())
        } else { // если есть два потомка, узел заменяется преемником
            val heir = receiveHeir(currentElement) // поиск преемника для удаляемого узла
            if (currentElement === root) root = heir
            else if (isLeft) parentNode!!.setLeft(heir)
            else parentNode!!.setRight(heir)
        }
        size--
    }

    private fun receiveHeir(elem: KotlinNode?): KotlinNode? {
        var parentElement = elem
        var heirElement = elem
        var currentElement = elem!!.getRight() // Переход к правому потомку
        while (currentElement != null) // Пока остаются левые потомки
        {
            parentElement = heirElement // потомка задаём как текущий узел
            heirElement = currentElement
            currentElement = currentElement.getLeft() // переход к левому потомку
        }
        // Если преемник не является
        if (heirElement !== elem.getRight()) // правым потомком,
        { // создать связи между узлами
            heirElement!!.setLeft(elem.getLeft())
            parentElement!!.setLeft(heirElement.getRight())
            heirElement.setRight(elem.getRight())
        }
        return heirElement // возвращаем приемника
    }

    override fun printTree() { // метод для вывода дерева в консоль
        val globalStack = Stack<KotlinNode?>() // общий стек для значений дерева
        globalStack.push(root)
        var spaceCount = 32 // начальное значение расстояния между элементами
        var isRowEmpty = false
        val separator = "-----------------------------------------------------------------"
        println(separator) // черта для указания начала нового дерева
        while (!isRowEmpty) {
            val localStack = Stack<KotlinNode?>() // локальный стек для задания потомков элемента
            isRowEmpty = true
            for (j in 0 until spaceCount) print(' ')
            while (!globalStack.isEmpty()) { // покуда в общем стеке есть элементы
                val temp = globalStack.pop() // берем следующий, при этом удаляя его из стека
                if (temp != null) {
                    print(temp.getData()) // выводим его значение в консоли
                    localStack.push(temp.getLeft()) // соохраняем в локальный стек, наследники текущего элемента
                    localStack.push(temp.getRight())
                    if (temp.getLeft() != null ||
                            temp.getRight() != null) isRowEmpty = false
                } else {
                    print("__") // - если элемент пустой
                    localStack.push(null)
                    localStack.push(null)
                }
                for (j in 0 until spaceCount * 2 - 2) print(' ')
            }
            println()
            spaceCount /= 2 // при переходе на следующий уровень расстояние между элементами каждый раз уменьшается
            while (!localStack.isEmpty()) globalStack.push(localStack.pop()) // перемещаем все элементы из локального стека в глобальный
        }
        println(separator) // подводим черту
    }

    override fun forEach(a: Action<Any>) {
        val globalStack = Stack<KotlinNode?>() // общий стек для значений дерева
        globalStack.push(root)
        var isRowEmpty = false
        while (!isRowEmpty) {
            val localStack = Stack<KotlinNode?>() // локальный стек для задания потомков элемента
            isRowEmpty = true
            while (!globalStack.isEmpty()) { // покуда в общем стеке есть элементы
                val temp = globalStack.pop() // берем следующий, при этом удаляя его из стека
                if (temp != null) {
                    temp.getData()?.let { a.toDo(it) }
                    localStack.push(temp.getLeft()) // соохраняем в локальный стек, наследники текущего элемента
                    localStack.push(temp.getRight())
                    if (temp.getLeft() != null ||
                            temp.getRight() != null) isRowEmpty = false
                } else {
                    localStack.push(null)
                    localStack.push(null)
                }
            }
            while (!localStack.isEmpty()) globalStack.push(localStack.pop()) // перемещаем все элементы из локального стека в глобальный
        }
    }

    override fun setComparator(comparator: Comparator<Any>) {
        this.comparator = comparator
    }
}
