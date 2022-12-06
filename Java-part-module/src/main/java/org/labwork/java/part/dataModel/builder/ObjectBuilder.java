package org.labwork.java.part.dataModel.builder;

import org.labwork.java.part.service.Comparator;

public interface ObjectBuilder extends Builder<Object> {
    String typeName();

    Object create();

    Comparator<Object> getComparator();

    Object createFromString(String s);

    String toString(Object object);
}
