package org.labwork.java.part.dataModel.binaryTree;

public class Node {
    // PARENT AND CHILDS
    private Node parent;
    private Node left;
    private Node right;

    // STORAGE DATA
    Object data;

    // BALANCE COEFFICIENTS
    private int balanceCoeffiecient;
    static final int LH = 1;
    static final int EH = 0;
    static final int RH = -1;

    // DEFAULT CONSTRUCTOR
    public Node(Object data, int bf, Node parent) {
        this.parent = parent;
        this.data = data;
        this.balanceCoeffiecient = bf;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public Object getData() {
        return data;
    }

    public int getBalanceCoeffiecient() {
        return balanceCoeffiecient;
    }

    public void setBalanceCoeffiecient(int balanceCoeffiecient) {
        this.balanceCoeffiecient = balanceCoeffiecient;
    }

    public void incrementBalanceCoeffiecient() {
        this.balanceCoeffiecient++;
    }

    public void decrementBalanceCoeffiecient() {
        this.balanceCoeffiecient--;
    }

}
