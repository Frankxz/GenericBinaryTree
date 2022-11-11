package org.labwork.dataModel.binaryTree;

import org.labwork.service.Action;
import org.labwork.service.Comparator;

public interface BinaryTreeInterface<T> {
    int getSize();

    void printTree();

    boolean isEmpty();

    boolean insertElement(Object data);

    boolean deleteElement(Object data);

    void forEach(Action<Object> a);

    void setComparator(Comparator<Object> comparator);
}
