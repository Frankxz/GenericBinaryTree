package org.labwork.dataModel.binaryTree;

import org.labwork.service.Action;
import org.labwork.service.Comparator;

public interface BinaryTreeInterface {
    int getSize();

    void printTree();

    boolean insertElement(Object data);

    void deleteElement(Object data);

    void forEach(Action<Object> a);

    void setComparator(Comparator<Object> comparator);
}
