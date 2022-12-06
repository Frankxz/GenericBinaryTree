package org.labwork.java.part.dataModel.binaryTree;

import org.labwork.java.part.service.Action;
import org.labwork.java.part.service.Comparator;

import java.util.*;

//comparator.compare(node.getData(), data) > 0
public class BinaryTree<T> implements BinaryTreeInterface {
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
        return size <= 0;
    }

    public boolean insertElement(Object data) {
        if (root == null) {
            root = new Node(data, 0, null);
            size++;
            return true;
        }

        Node curr = root;
        Node parent;

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
                return false;
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
        return true;
    }

    private void fixAfterInsertion(Node parent) {
        if (parent.getBalanceCoeffiecient() == 2) {
            leftBalance(parent);
        }

        if (parent.getBalanceCoeffiecient() == -2) {
            rightBalance(parent);
        }
    }

    private void rotateRight(Node parent) {

        if (parent != null) {
            Node left = parent.getLeft();
            left.setParent(parent.getParent());
            parent.setLeft(left.getRight());

            if (left.getRight() != null) {
                left.getRight().setParent(parent);
            }

            if (parent.getParent() == null) {
                root = left;
            } else if (parent.getParent().getRight() == parent) {
                parent.getParent().setRight(left);
            } else {
                parent.getParent().setLeft(left);
            }

            left.setRight(parent);
            parent.setParent(left);
        }

    }

    private void rotateLeft(Node parent) {
        if (parent != null) {
            Node right = parent.getRight();
            right.setParent(parent.getParent());
            parent.setRight(right.getLeft());

            if (right.getLeft() != null) {
                right.getLeft().setParent(parent);
            }
            if (parent.getParent() == null)
                root = right;
            else if (parent.getParent().getLeft() == parent)
                parent.getParent().setLeft(right);
            else
                parent.getParent().setRight(right);

            right.setLeft(parent);
            parent.setParent(right);
        }

    }

    private void leftBalance(Node temp) {
        Node left = temp.getLeft();
        switch (left.getBalanceCoeffiecient()) {
            case Node.LH -> {
                temp.setBalanceCoeffiecient(Node.EH);
                left.setBalanceCoeffiecient(Node.EH);
                rotateRight(temp);
            }
            case Node.RH -> {
                Node rd = left.getRight();
                switch (rd.getBalanceCoeffiecient()) {
                    case Node.LH -> {
                        temp.setBalanceCoeffiecient(Node.RH);
                        left.setBalanceCoeffiecient(Node.EH);
                    }
                    case Node.EH -> {
                        temp.setBalanceCoeffiecient(Node.EH);
                        left.setBalanceCoeffiecient(Node.EH);
                    }
                    case Node.RH -> {
                        temp.setBalanceCoeffiecient(Node.EH);
                        left.setBalanceCoeffiecient(Node.LH);
                    }
                }
                rd.setBalanceCoeffiecient(Node.EH);
                rotateLeft(temp.getLeft());
                rotateRight(temp);
            }
            case Node.EH -> {
                temp.setBalanceCoeffiecient(Node.LH);
                left.setBalanceCoeffiecient(Node.RH);
                rotateRight(temp);
            }
        }

    }

    private void rightBalance(Node temp) {
        Node right = temp.getRight();
        switch (right.getBalanceCoeffiecient()) {
            case Node.LH -> {
                Node ld = right.getLeft();
                switch (ld.getBalanceCoeffiecient()) {
                    case Node.LH -> {
                        temp.setBalanceCoeffiecient(Node.EH);
                        right.setBalanceCoeffiecient(Node.RH);
                    }
                    case Node.EH -> {
                        temp.setBalanceCoeffiecient(Node.EH);
                        right.setBalanceCoeffiecient(Node.EH);
                    }
                    case Node.RH -> {
                        temp.setBalanceCoeffiecient(Node.LH);
                        right.setBalanceCoeffiecient(Node.EH);
                    }
                }
                ld.setBalanceCoeffiecient(Node.EH);
                rotateRight(temp.getRight());
                rotateLeft(temp);
            }
            case Node.RH -> {
                temp.setBalanceCoeffiecient(Node.EH);
                right.setBalanceCoeffiecient(Node.EH);
                rotateLeft(temp);
            }
            case Node.EH -> {
                right.setBalanceCoeffiecient(Node.LH);
                temp.setBalanceCoeffiecient(Node.RH);
                rotateLeft(temp);
            }
        }
    }

    public void deleteElement(Object data) {
        Node currentElement = root;
        Node parentNode = root;
        boolean isLeft = true;

        while  (comparator.compare(data, currentElement.getData()) < 0) { // начинаем поиск узла
            parentNode = currentElement;
            if (comparator.compare(data, currentElement.getData()) < 0) { // Определяем, нужно ли движение влево?
                isLeft = true;
                currentElement = currentElement.getLeft();
            } else { // или движение вправо?
                isLeft = false;
                currentElement = currentElement.getRight();
            }
            if (currentElement == null)
                return; // yзел не найден
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
        Stack<Node> globalStack = new Stack<>(); // общий стек для значений дерева
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

    public void setComparator(Comparator<Object> comparator) {
        this.comparator = comparator;
    }
}

