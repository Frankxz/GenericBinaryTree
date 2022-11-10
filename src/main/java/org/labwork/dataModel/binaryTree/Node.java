package org.labwork.dataModel.binaryTree;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Node<T> {
    private Node<T> parent;
    private Node<T> leftChild;
    private Node<T> rightChild;
    private T data;

    public Node(T data) {
        this(null, null, null, data);
    }

    public Node(Node<T> parent, Node<T> leftChild, Node<T> rightChild, T data) {
        this.parent = parent;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
        this.data = data;
    }

    public Node<T> getParent() {
        return parent;
    }

    public void setParent(Node<T> parent) {
        this.parent = parent;
    }

    public Node<T> getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(Node<T> leftChild) {
        this.leftChild = leftChild;
    }

    public Node<T> getRightChild() {
        return rightChild;
    }

    public void setRightChild(Node<T> rightChild) {
        this.rightChild = rightChild;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void removeChild(Node<T> child) {
        if (child == null) return;
        if (this.getRightChild() == child) {
            this.setRightChild(null);
            return;
        }
        if (this.getLeftChild() == child)
            this.setLeftChild(null);
    }

    public Iterator<Node> children() {
        List<Node> childList = new LinkedList<>();
        if (this.leftChild != null) childList.add(leftChild);
        if (this.rightChild != null) childList.add(rightChild);
        return childList.iterator();
    }
}
