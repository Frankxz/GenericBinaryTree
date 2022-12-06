package org.labwork.java.part.dataModel.builder;

import org.labwork.java.part.service.Comparator;

public interface Builder<T> {
    String typeName();

    T create();

    Comparator<T> getComparator();

    T createFromString(String s);

    String toString(T object);
}
