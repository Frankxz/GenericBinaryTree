package org.labwork.dataModel.builder;

import org.labwork.service.Comparator;

public interface Builder<T> {
    String typeName();

    Object create();

    Comparator<Object> getComparator();

    Object createFromString(String s);

    String toString(Object object);
}
