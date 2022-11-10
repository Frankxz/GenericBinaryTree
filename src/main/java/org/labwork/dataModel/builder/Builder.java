package org.labwork.dataModel.builder;

import org.labwork.service.Comparator;

public interface Builder<T> {
    String typeName();

    T create();

    Comparator<T> getComparator();

    T createFromString(String s);

    String toString(T object);
}
