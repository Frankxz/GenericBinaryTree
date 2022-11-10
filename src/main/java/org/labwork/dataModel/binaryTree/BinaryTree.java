package org.labwork.dataModel.binaryTree;

import org.labwork.service.Action;
import org.labwork.service.Comparator;

import java.util.*;

import static java.lang.Math.max;


//comparator.compare(node.getData(), data) > 0
public class BinaryTree<T> implements BinaryTreeInterface<T> {
    private Node root;
    private int size;
    private Comparator<Object> comparator;

    public BinaryTree(Comparator<Object> comparator) {
        this.comparator = comparator;
        this.root = null;
    }

    // ---------------------------------------------------------------------- //
    // INSERTION AND BALANCING

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        if (size <= 0) {
            return true;
        } else {
            return false;
        }
    }

    public void insertElement(Object data) {
        if (root == null) {
            root = new Node(data, 0, null);
            size++;
            return;
        }

        Node curr = root;
        Node parent = null;

        do {
            parent = curr;
            if (comparator.compare(data, curr.data) > 0) {
                // перейти к левому поддереву
                curr = curr.getLeft();
            } else if (comparator.compare(data, curr.data) < 0) {
                // смотреть на правильное поддерево
                curr = curr.getRight();
            } else {
                // найдено, вставка не удалась
                return;
            }
        } while (curr != null);
        // Вставить узел

        if (comparator.compare(data, parent.data) > 0) {
            // Вставляем левого потомка
            parent.setLeft(new Node(data, Node.EH, parent));
            size++;
        } else if (comparator.compare(data, parent.data) < 0) {
            // вставить правого ребенка
            parent.setRight(new Node(data, Node.EH, parent));
            size++;
        }

        // назад снизу вверх, чтобы найти ближайший несбалансированный узел
        while (parent != null) {
            if (comparator.compare(data, parent.data) > 0) {
                // Вставляем узел в левое поддерево родителя
                parent.incrementBalanceCoeffiecient();
            } else if (comparator.compare(data, parent.data) < 0) {
                // Вставляем узел в правое поддерево родителя
                parent.decrementBalanceCoeffiecient();
            }

            if (parent.getBalanceCoeffiecient() == Node.EH) {
                // Баланс этого узла равен 0, значение BF больше не корректируется вверх и вращение не требуется
                break;
            }

            if (Math.abs(parent.getBalanceCoeffiecient()) == 2) {
                // Находим корневой узел наименьшего несбалансированного поддерева и поворачиваем его, чтобы исправить
                fixAfterInsertion(parent);
                break;
            }
            parent = parent.getParent();
        }
    }

    private void fixAfterInsertion(Node parent) {
        // Глубокий уровень поддерева
        if (parent.getBalanceCoeffiecient() == 2) {
            leftBalance(parent);
        }

        // Глубина правого поддерева велика
        if (parent.getBalanceCoeffiecient() == -2) {
            rightBalance(parent);
        }
    }

    private void rotateRight(Node parent) {
        //  System.out.println (" вокруг " + parent.data + " правша ");
        if (parent != null) {
            Node left = parent.getLeft();

            // Родительский узел parent назначен родительскому узлу left
            left.setParent(parent.getParent());
            // правый узел LR как левый узел р
            parent.setLeft(left.getRight());

            if (left.getRight() != null) {
                left.getRight().setParent(parent);
            }

            if (parent.getParent() == null) {
                // parent - корневой узел, сбросить корневой узел
                root = left;
            } else if (parent.getParent().getRight() == parent) {
                // parent - корневой узел правого поддерева родительского потомка, сбрасываем корневой узел левого поддерева
                parent.getParent().setRight(left);
            } else {
                // parent - корневой узел левого поддерева родительского узла, сбрасываем корневой узел левого поддерева
                parent.getParent().setLeft(left);
            }

            // правое поддерево с parent как left
            left.setRight(parent);
            // Установить родительский узел parent в left
            parent.setParent(left);
        }

    }

    private void rotateLeft(Node parent) {
        //   System.out.println (" вокруг " + parent.data + " левша ");
        if (parent != null) {
            Node right = parent.getRight();
            // Родительский узел parent назначен родительскому узлу right
            right.setParent(parent.getParent());
            // левый узел L rR как правый узел р
            parent.setRight(right.getLeft());

            if (right.getLeft() != null) {
                right.getLeft().setParent(parent);
            }

            if (parent.getParent() == null)
                // parent является корневым узлом, right становится родительским узлом, то есть B является родительским узлом
                root = right;
            else if (parent.getParent().getLeft() == parent)
                // parent - левый дочерний узел, а левое дочернее дерево родительского узла - right
                parent.getParent().setLeft(right);
            else
                // Если parent - правильный дочерний узел
                parent.getParent().setRight(right);

            // parent - левое поддерево right
            right.setLeft(parent);
            // Установить родительский узел parent в right
            parent.setParent(right);
        }

    }

    private void leftBalance(Node temp) {
        // отметим, изменяется ли высота дерева
        boolean taller = true;

        Node left = temp.getLeft();
        switch (left.getBalanceCoeffiecient()) {

            // левая высота, правая регулировка, высота дерева уменьшается после поворота (левый-левый регистр, см. рисунок)
            case Node.LH:
                temp.setBalanceCoeffiecient(Node.EH);
                left.setBalanceCoeffiecient(Node.EH);
                rotateRight(temp);
                break;

            // Высота справа, отрегулированная для каждой ситуации (слева и справа, см. Рисунок)
            case Node.RH:
                Node rd = left.getRight();
                // Регулируем BF каждого узла
                switch (rd.getBalanceCoeffiecient()) {
                    // см. примечание метода case 1
                    case Node.LH:
                        temp.setBalanceCoeffiecient(Node.RH);
                        left.setBalanceCoeffiecient(Node.EH);
                        break;

                    // см. примечание метода case 2
                    case Node.EH:
                        temp.setBalanceCoeffiecient(Node.EH);
                        left.setBalanceCoeffiecient(Node.EH);
                        break;

                    // см. примечание метода 3
                    case Node.RH:
                        temp.setBalanceCoeffiecient(Node.EH);
                        left.setBalanceCoeffiecient(Node.LH);
                        break;
                }

                // левая, затем правая
                rd.setBalanceCoeffiecient(Node.EH);
                rotateLeft(temp.getLeft());
                rotateRight(temp);
                break;

            // Особый случай 4, эта ситуация не может возникнуть при добавлении,
            // возможно только при удалении, общая высота дерева не меняется после поворота
            // Удалить правого потомка root
            case Node.EH:
                temp.setBalanceCoeffiecient(Node.LH);
                left.setBalanceCoeffiecient(Node.RH);
                rotateRight(temp);
                taller = false;
                break;
        }

    }

    private void rightBalance(Node temp) {
        // Записать иерархические изменения дерева
        boolean heightLower = true;
        Node right = temp.getRight();

        switch (right.getBalanceCoeffiecient()) {
            // левый максимум, скорректированный для каждой ситуации (правый и левый случаи)
            case Node.LH:
                Node ld = right.getLeft();
                // Регулируем BF каждого узла
                switch (ld.getBalanceCoeffiecient()) {
                    // см. примечание метода case 1
                    case Node.LH:
                        temp.setBalanceCoeffiecient(Node.EH);
                        right.setBalanceCoeffiecient(Node.RH);
                        break;

                    // см. примечание метода case 2
                    case Node.EH:
                        temp.setBalanceCoeffiecient(Node.EH);
                        right.setBalanceCoeffiecient(Node.EH);
                        break;

                    // см. примечание метода 3
                    case Node.RH:
                        temp.setBalanceCoeffiecient(Node.LH);
                        right.setBalanceCoeffiecient(Node.EH);
                        break;
                }

                ld.setBalanceCoeffiecient(Node.EH);
                rotateRight(temp.getRight());
                rotateLeft(temp);
                break;

            // Высота справа, регулировка слева (справа-справа)
            case Node.RH:
                temp.setBalanceCoeffiecient(Node.EH);
                right.setBalanceCoeffiecient(Node.EH);
                rotateLeft(temp);
                break;

            // Особый случай 4
            case Node.EH:
                right.setBalanceCoeffiecient(Node.LH);
                temp.setBalanceCoeffiecient(Node.RH);
                rotateLeft(temp);
                heightLower = false;
                break;
        }
    }

    public boolean deleteElement(int data) {
        Node currentElement = root;
        Node parentNode = root;
        boolean isLeft = true;
        while (((Comparable) data).compareTo(currentElement.getData()) < 0) { // начинаем поиск узла
            parentNode = currentElement;
            if (((Comparable) data).compareTo(currentElement.getData()) < 0) { // Определяем, нужно ли движение влево?
                isLeft = true;
                currentElement = currentElement.getLeft();
            } else { // или движение вправо?
                isLeft = false;
                currentElement = currentElement.getRight();
            }
            if (currentElement == null)
                return false; // yзел не найден
        }

        if (currentElement.getLeft() == null && currentElement.getRight() == null) { // узел просто удаляется, если не имеет потомков
            if (currentElement == root) // если узел - корень, то дерево очищается
                root = null;
            else if (isLeft)
                parentNode.setLeft(null); // если нет - узел отсоединяется, от родителя
            else
                parentNode.setRight(null);
        } else if (currentElement.getRight() == null) { // узел заменяется левым поддеревом, если правого потомка нет
            if (currentElement == root)
                root = currentElement.getLeft();
            else if (isLeft)
                parentNode.setLeft(currentElement.getLeft());
            else
                parentNode.setRight(currentElement.getLeft());
        } else if (currentElement.getLeft() == null) { // узел заменяется правым поддеревом, если левого потомка нет
            if (currentElement == root)
                root = currentElement.getRight();
            else if (isLeft)
                parentNode.setLeft(currentElement.getRight());
            else
                parentNode.setRight(currentElement.getRight());
        } else { // если есть два потомка, узел заменяется преемником
            Node heir = receiveHeir(currentElement);// поиск преемника для удаляемого узла
            if (currentElement == root)
                root = heir;
            else if (isLeft)
                parentNode.setLeft(heir);
            else
                parentNode.setRight(heir);
        }
        size--;
        return true; // элемент успешно удалён
    }

    private Node receiveHeir(Node elem) {
        Node parentElement = elem;
        Node heirElement = elem;
        Node currentElement = elem.getRight(); // Переход к правому потомку
        while (currentElement != null) // Пока остаются левые потомки
        {
            parentElement = heirElement;// потомка задаём как текущий узел
            heirElement = currentElement;
            currentElement = currentElement.getLeft(); // переход к левому потомку
        }
        // Если преемник не является
        if (heirElement != elem.getRight()) // правым потомком,
        { // создать связи между узлами
            heirElement.setLeft(elem.getLeft());
            parentElement.setLeft(heirElement.getRight());
            heirElement.setRight(elem.getRight());
        }
        return heirElement;// возвращаем приемника
    }

    public void printTree() { // метод для вывода дерева в консоль
        Stack<Node> globalStack = new Stack<Node>(); // общий стек для значений дерева
        globalStack.push(root);
        int spaceCount = 32; // начальное значение расстояния между элементами
        boolean isRowEmpty = false;
        String separator = "-----------------------------------------------------------------";
        System.out.println(separator);// черта для указания начала нового дерева
        while (!isRowEmpty) {
            Stack<Node> localStack = new Stack<>(); // локальный стек для задания потомков элемента
            isRowEmpty = true;

            for (int j = 0; j < spaceCount; j++)
                System.out.print(' ');
            while (!globalStack.isEmpty()) { // покуда в общем стеке есть элементы
                Node temp = globalStack.pop(); // берем следующий, при этом удаляя его из стека
                if (temp != null) {
                    System.out.print(temp.getData()); // выводим его значение в консоли
                    localStack.push(temp.getLeft()); // соохраняем в локальный стек, наследники текущего элемента
                    localStack.push(temp.getRight());
                    if (temp.getLeft() != null ||
                            temp.getRight() != null)
                        isRowEmpty = false;
                } else {
                    System.out.print("__");// - если элемент пустой
                    localStack.push(null);
                    localStack.push(null);
                }
                for (int j = 0; j < spaceCount * 2 - 2; j++)
                    System.out.print(' ');
            }
            System.out.println();
            spaceCount /= 2;// при переходе на следующий уровень расстояние между элементами каждый раз уменьшается
            while (!localStack.isEmpty())
                globalStack.push(localStack.pop()); // перемещаем все элементы из локального стека в глобальный
        }
        System.out.println(separator);// подводим черту
    }

    public void forEach(Action<Object> a) {
        Stack<Node> globalStack = new Stack<>(); // общий стек для значений дерева
        globalStack.push(root);
        boolean isRowEmpty = false;
        while (!isRowEmpty) {
            Stack<Node> localStack = new Stack<>(); // локальный стек для задания потомков элемента
            isRowEmpty = true;

            while (!globalStack.isEmpty()) { // покуда в общем стеке есть элементы
                Node temp = globalStack.pop(); // берем следующий, при этом удаляя его из стека
                if (temp != null) {
                    a.toDo(temp.getData());
                    localStack.push(temp.getLeft()); // соохраняем в локальный стек, наследники текущего элемента
                    localStack.push(temp.getRight());
                    if (temp.getLeft() != null ||
                            temp.getRight() != null)
                        isRowEmpty = false;
                } else {
                    localStack.push(null);
                    localStack.push(null);
                }
            }
            while (!localStack.isEmpty())
                globalStack.push(localStack.pop()); // перемещаем все элементы из локального стека в глобальный
        }
    }
}

