package org.labwork.java.part.dataModel.builder;

import java.util.Random;
import org.labwork.java.part.service.Comparator;

public class IntegerObjectBuilder implements ObjectBuilder {

    @Override
    public String typeName() {
        return "Integer";
    }

    @Override
    public Integer create() {
        return new Random().nextInt(100);
    }

    @Override
    public Integer createFromString(String s) {
        return Integer.parseInt(s);
    }

    @Override
    public String toString(Object object) {
        return object.toString();
    }

    @Override
    public Comparator<Object> getComparator() {
        return (o1, o2) -> (Integer) o1 - (Integer) o2;
    }

}
