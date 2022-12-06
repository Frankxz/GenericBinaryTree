package org.labwork.java.part.dataModel.binaryTree;

import org.labwork.java.part.service.Action;
import org.labwork.java.part.service.Comparator;

public interface BinaryTreeInterface {
    int getSize();

    void printTree();

    boolean insertElement(Object data);

    void deleteElement(Object data);

    void forEach(Action<Object> a);

    void setComparator(Comparator<Object> comparator);
}
