package org.labwork.dataModel.binaryTree;

import javax.swing.*;
import java.util.List;

public interface BinaryTreeInterface<T> {
    boolean isEmpty();

    int size();

    int height();

    boolean contains(T data);

    T get(T data);

    void add(T data) throws Exception;

    void remove(T data);

    String toString();

    List<T> inOrder();

    List<T> preOrder();

    List<T> postOrder();

    List<T> levelOrder();
}
