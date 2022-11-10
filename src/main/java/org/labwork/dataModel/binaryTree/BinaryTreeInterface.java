package org.labwork.dataModel.binaryTree;

import org.labwork.service.Action;

public interface BinaryTreeInterface<T> {
    int getSize();

    void printTree();

    boolean isEmpty();

    void insertElement(Object data);

    boolean deleteElement(int data);

    void forEach(Action<Object> a);
}
