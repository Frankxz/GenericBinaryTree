package org.labwork.java.part.dataModel.binaryTree;

import static org.junit.jupiter.api.Assertions.*;
class BinaryTreeTest {

    @org.junit.jupiter.api.Test
    void checkSizeAfterInsertion() {
        BinaryTree<Object> binaryTree = new BinaryTree<>((o1, o2) -> (Integer) o1 - (Integer) o2);

        binaryTree.insertElement(1);
        binaryTree.insertElement(3);
        binaryTree.insertElement(5);

        int actualSize = binaryTree.getSize();
        int expectedSize = 3;

        assertEquals(actualSize, expectedSize);
    }

    @org.junit.jupiter.api.Test
    void checkEmptyOnCreation() {
        BinaryTree<Object> binaryTree = new BinaryTree<>((o1, o2) -> (Integer) o1 - (Integer) o2);
        assertTrue(binaryTree.isEmpty());
    }

    @org.junit.jupiter.api.Test
    void checkNotEmptyAfterInsertion() {
        BinaryTree<Object> binaryTree = new BinaryTree<>((o1, o2) -> (Integer) o1 - (Integer) o2);
        binaryTree.insertElement(1);
        assertFalse(binaryTree.isEmpty());
    }

    @org.junit.jupiter.api.Test
    void checkBSTCorrectAfterInsertion() {
        BinaryTree<Object> binaryTree = new BinaryTree<>((o1, o2) -> (Integer) o1 - (Integer) o2);

        binaryTree.insertElement(2);
        binaryTree.insertElement(3);
        binaryTree.insertElement(1);

        int actualRootValue = (int) binaryTree.getRoot().data;
        int actualLeftChildOfRootValue = (int) binaryTree.getRoot().getLeft().data;
        int actualRightChildOfRootValue = (int) binaryTree.getRoot().getRight().data;

        int expectedRootValue = 2;
        int expectedLeftChildOfRootValue = 3;
        int expectedRightChildOfRootValue = 1;

        binaryTree.printTree();

        assertEquals(actualRootValue, expectedRootValue);
        assertEquals(actualLeftChildOfRootValue, expectedLeftChildOfRootValue);
        assertEquals(actualRightChildOfRootValue, expectedRightChildOfRootValue);
    }

    @org.junit.jupiter.api.Test
    void checkBSTCorrectAfterDelete() {
        BinaryTree<Object> binaryTree = new BinaryTree<>((o1, o2) -> (Integer) o1 - (Integer) o2);
        binaryTree.insertElement(10);
        binaryTree.deleteElement(10);
        assertTrue(binaryTree.isEmpty());
    }

    @org.junit.jupiter.api.Test
    void checkLeftRotation() {
        BinaryTree<Object> binaryTree = new BinaryTree<>((o1, o2) -> (Integer) o1 - (Integer) o2);

        binaryTree.insertElementWithoutAVLBalancing(2);
        binaryTree.insertElementWithoutAVLBalancing(1);
        binaryTree.insertElementWithoutAVLBalancing(0);

        System.out.println("Non balanced: ");
        binaryTree.printTree();

        BinaryTree<Object> binaryTreeBalanced = new BinaryTree<>((o1, o2) -> (Integer) o1 - (Integer) o2);

        binaryTreeBalanced.insertElement(2);
        binaryTreeBalanced.insertElement(1);
        binaryTreeBalanced.insertElement(0);

        System.out.println("\nBalanced: ");
        binaryTreeBalanced.printTree();

        int actualRootValue = (int) binaryTreeBalanced.getRoot().data;
        int actualLeftChildOfRootValue = (int) binaryTreeBalanced.getRoot().getLeft().data;
        int actualRightChildOfRootValue = (int) binaryTreeBalanced.getRoot().getRight().data;

        int expectedRootValue = 1;
        int expectedLeftChildOfRootValue = 2;
        int expectedRightChildOfRootValue = 0;

        assertEquals(actualRootValue, expectedRootValue);
        assertEquals(actualLeftChildOfRootValue, expectedLeftChildOfRootValue);
        assertEquals(actualRightChildOfRootValue, expectedRightChildOfRootValue);
    }

    @org.junit.jupiter.api.Test
    void checkRightRotation() {
        BinaryTree<Object> binaryTree = new BinaryTree<>((o1, o2) -> (Integer) o1 - (Integer) o2);

        binaryTree.insertElementWithoutAVLBalancing(0);
        binaryTree.insertElementWithoutAVLBalancing(1);
        binaryTree.insertElementWithoutAVLBalancing(2);

        System.out.println("Non balanced: ");
        binaryTree.printTree();

        BinaryTree<Object> binaryTreeBalanced = new BinaryTree<>((o1, o2) -> (Integer) o1 - (Integer) o2);

        binaryTreeBalanced.insertElement(0);
        binaryTreeBalanced.insertElement(1);
        binaryTreeBalanced.insertElement(2);

        System.out.println("\nBalanced: ");
        binaryTreeBalanced.printTree();

        int actualRootValue = (int) binaryTreeBalanced.getRoot().data;
        int actualLeftChildOfRootValue = (int) binaryTreeBalanced.getRoot().getLeft().data;
        int actualRightChildOfRootValue = (int) binaryTreeBalanced.getRoot().getRight().data;

        int expectedRootValue = 1;
        int expectedLeftChildOfRootValue = 2;
        int expectedRightChildOfRootValue = 0;

        assertEquals(actualRootValue, expectedRootValue);
        assertEquals(actualLeftChildOfRootValue, expectedLeftChildOfRootValue);
        assertEquals(actualRightChildOfRootValue, expectedRightChildOfRootValue);
    }

    @org.junit.jupiter.api.Test
    void checkRightToLeftRotation() {
        BinaryTree<Object> binaryTree = new BinaryTree<>((o1, o2) -> (Integer) o1 - (Integer) o2);

        binaryTree.insertElementWithoutAVLBalancing(3);
        binaryTree.insertElementWithoutAVLBalancing(1);
        binaryTree.insertElementWithoutAVLBalancing(2);

        System.out.println("Non balanced: ");
        binaryTree.printTree();

        BinaryTree<Object> binaryTreeBalanced = new BinaryTree<>((o1, o2) -> (Integer) o1 - (Integer) o2);

        binaryTreeBalanced.insertElement(3);
        binaryTreeBalanced.insertElement(1);
        binaryTreeBalanced.insertElement(2);

        System.out.println("\nBalanced: ");
        binaryTreeBalanced.printTree();

        int actualRootValue = (int) binaryTreeBalanced.getRoot().data;
        int actualLeftChildOfRootValue = (int) binaryTreeBalanced.getRoot().getLeft().data;
        int actualRightChildOfRootValue = (int) binaryTreeBalanced.getRoot().getRight().data;

        int expectedRootValue = 2;
        int expectedLeftChildOfRootValue = 3;
        int expectedRightChildOfRootValue = 1;

        assertEquals(actualRootValue, expectedRootValue);
        assertEquals(actualLeftChildOfRootValue, expectedLeftChildOfRootValue);
        assertEquals(actualRightChildOfRootValue, expectedRightChildOfRootValue);
    }

    @org.junit.jupiter.api.Test
    void checkLeftToRightRotation() {
        BinaryTree<Object> binaryTree = new BinaryTree<>((o1, o2) -> (Integer) o1 - (Integer) o2);

        binaryTree.insertElementWithoutAVLBalancing(1);
        binaryTree.insertElementWithoutAVLBalancing(3);
        binaryTree.insertElementWithoutAVLBalancing(2);

        System.out.println("Non balanced: ");
        binaryTree.printTree();

        BinaryTree<Object> binaryTreeBalanced = new BinaryTree<>((o1, o2) -> (Integer) o1 - (Integer) o2);

        binaryTreeBalanced.insertElement(1);
        binaryTreeBalanced.insertElement(3);
        binaryTreeBalanced.insertElement(2);

        System.out.println("\nBalanced: ");
        binaryTreeBalanced.printTree();

        int actualRootValue = (int) binaryTreeBalanced.getRoot().data;
        int actualLeftChildOfRootValue = (int) binaryTreeBalanced.getRoot().getLeft().data;
        int actualRightChildOfRootValue = (int) binaryTreeBalanced.getRoot().getRight().data;

        int expectedRootValue = 2;
        int expectedLeftChildOfRootValue = 3;
        int expectedRightChildOfRootValue = 1;

        assertEquals(actualRootValue, expectedRootValue);
        assertEquals(actualLeftChildOfRootValue, expectedLeftChildOfRootValue);
        assertEquals(actualRightChildOfRootValue, expectedRightChildOfRootValue);
    }
}