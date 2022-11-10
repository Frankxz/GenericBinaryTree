package org.labwork.dataModel.binaryTree;

import org.labwork.service.Comparator;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

import static java.lang.Math.max;

public class BinaryTree<T> implements BinaryTreeInterface<T>
{
    private Node<T> root;
    private int size;
    private Comparator<Object> comparator;

    public BinaryTree(Comparator<Object> comparator) {
        this.comparator = comparator;
    }

    private Node<T> root()
    {
        return root;
    }

    private void addRoot(T data) throws Exception
    {
        if(root != null) throw new Exception("Root exists is the tree.");
        root = new Node<>(data);
        size++;
    }

    public void add(T data) throws Exception
    {
        Node<T> node = find(data);
        if (node == null)
            addRoot(data);
        else if (comparator.compare(node.getData(), data) > 0)
            addLeft(node, data);
        else if (comparator.compare(node.getData(), data) < 0)
            addRight(node, data);
        else node.setData(data);
    }

    private void addLeft(Node<T> parent, T data)
    {
        Node<T> left = new Node<>(data);
        parent.setLeftChild(left);
        left.setParent(parent);
        size++;
    }

    private void addRight(Node<T> parent, T data)
    {
        Node<T> right = new Node<>(data);
        parent.setRightChild(right);
        right.setParent(parent);
        size++;
    }

    public void remove(T data)
    {
        Node<T> node = find(data);
        if(node == null || !node.getData().equals(data)) return;
        remove(node);
    }

    private Node<T> remove(Node<T> node)
    {
        if (isLeaf(node))
        {
            Node<T> parent = node.getParent();
            if (parent == null) root = null;
            else parent.removeChild(node);
            size--;
            return parent;
        }
        Node<T> descendant = descendant(node);
        promoteDescendant(node, descendant);
        return remove(descendant);
    }

    private void promoteDescendant(Node<T> parent, Node<T> descendant)
    {
        parent.setData(descendant.getData());
    }

    private Node<T> descendant(Node<T> parent)
    {
        Node<T> child = parent.getLeftChild();
        if (child != null)
        {
            while (child.getRightChild() != null) child = child.getRightChild();
            return child;
        }
        child = parent.getRightChild();
        if (child != null)
        {
            while (child.getLeftChild() != null) child = child.getLeftChild();
            return child;
        }
        return child;
    }

    public T get(T data)
    {
        Node<T> node = find(data);
        if(node == null || !node.getData().equals(data)) return null;
        return node.getData();
    }

    public boolean contains(T data)
    {
        Node<T> node = find(data);
        if(node == null || !node.getData().equals(data)) return false;
        return true;
    }

    private Node<T> find(T data)
    {
        if(root() == null) return null;
        return findRecursively(root(), data);
    }

    private Node<T> findRecursively(Node<T> parent, T data)
    {
        int comparison = comparator.compare(data, parent.getData());

        if(comparison == 0) return parent;
        else if(comparison < 0 && parent.getLeftChild() != null) return findRecursively(parent.getLeftChild(), data);
        else if(comparison > 0 && parent.getRightChild() != null) return findRecursively(parent.getRightChild(), data);
        return parent;
    }

    public boolean isEmpty()
    {
        return size() == 0;
    }

    public int size()
    {
        return size;
    }

    private Node<T> parent(Node<T> child)
    {
        return child.getParent();
    }

    private boolean isInternal(Node<T> node)
    {
        return node.children().hasNext();
    }

    private boolean isLeaf(Node<T> node)
    {
        return !isInternal(node);
    }

    private int depth(Node<T> node)
    {
        if(isLeaf(node)) return 0;
        return depth(node.getParent()) + 1;
    }

    private int height(Node<T> node)
    {
        if(isLeaf(node)) return 0;

        int maxHeight = 0;
        Iterator<Node> children = node.children();
        while (children.hasNext())
        {
            int height = height(children.next());
            if(height > maxHeight) maxHeight = height;
        }
        return maxHeight + 1;
    }

    public int height()
    {
        if(root == null) return -1;
        return height(root);
    }

    public List<T> preOrder()
    {
        List<T> list = new LinkedList<>();
        preOrder(root, list);
        return list;
    }

    private void preOrder(Node<T> node, List<T> list)
    {
        if(node == null) return;
        list.add(node.getData());

        Iterator<Node> children = node.children();
        while (children.hasNext())
        {
            preOrder(children.next(), list);
        }
    }

    public List<T> postOrder()
    {
        List<T> list = new LinkedList <>();
        postOrder(root(), list);
        return list;
    }

    private void postOrder(Node<T> node, List<T> list)
    {
        if(node == null) return;

        Iterator<Node> children = node.children();
        while (children.hasNext())
        {
            postOrder(children.next(), list);
        }
        list.add(node.getData());
    }

    public List<T> levelOrder()
    {
        List<T> nodeList = new LinkedList <>();

        if(root() == null) return nodeList;

        Queue<Node> nodeQueue = new ConcurrentLinkedQueue<>();

        try
        {
            nodeList.add(root().getData());
            nodeQueue.add(root());

            while (!nodeQueue.isEmpty())
            {
                Node<T> node = nodeQueue.poll();
                Iterator<Node> nodeItr = node.children();

                while (nodeItr.hasNext())
                {
                    Node<T> treeNode = nodeItr.next();
                    nodeQueue.add(treeNode);
                    nodeList.add(treeNode.getData());
                }
            }
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
        return nodeList;
    }

    public List<T> inOrder()
    {
        List<T> answer = new LinkedList <>();
        inOrder(root(), answer);
        return answer;
    }

    private void inOrder(Node<T> node, List<T> list)
    {
        if (node == null) return;
        inOrder(node.getLeftChild(), list);
        list.add(node.getData());
        inOrder(node.getRightChild(), list);
    }

    @Override
    public String toString()
    {
        return inOrder().toString();
    }

    public void printTree() { // метод для вывода дерева в консоль
        Stack globalStack = new Stack(); // общий стек для значений дерева
        globalStack.push(root);
        int gaps = 32; // начальное значение расстояния между элементами
        boolean isRowEmpty = false;
        String separator = "-----------------------------------------------------------------";
        System.out.println(separator);// черта для указания начала нового дерева
        while (isRowEmpty == false) {
            Stack localStack = new Stack(); // локальный стек для задания потомков элемента
            isRowEmpty = true;

            for (int j = 0; j < gaps; j++)
                System.out.print(' ');
            while (globalStack.isEmpty() == false) { // покуда в общем стеке есть элементы
                Node temp = (Node) globalStack.pop(); // берем следующий, при этом удаляя его из стека
                if (temp != null) {
                    System.out.print(temp.getData()); // выводим его значение в консоли
                    localStack.push(temp.getLeftChild()); // соохраняем в локальный стек, наследники текущего элемента
                    localStack.push(temp.getRightChild());
                    if (temp.getLeftChild() != null ||
                            temp.getRightChild() != null)
                        isRowEmpty = false;
                }
                else {
                    System.out.print("__");// - если элемент пустой
                    localStack.push(null);
                    localStack.push(null);
                }
                for (int j = 0; j < gaps * 2 - 2; j++)
                    System.out.print(' ');
            }
            System.out.println();
            gaps /= 2;// при переходе на следующий уровень расстояние между элементами каждый раз уменьшается
            while (localStack.isEmpty() == false)
                globalStack.push(localStack.pop()); // перемещаем все элементы из локального стека в глобальный
        }
        System.out.println(separator);// подводим черту
    }
}

