package org.labwork;
import org.labwork.dataModel.builder.ObjectBuilder;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ObjectBuilderFactory {
    private final static List<ObjectBuilder> builders = new ArrayList<>();

    static {
        Reflections reflections = new Reflections("org.labwork.dataModel.builder");
        Set<Class<? extends ObjectBuilder>> buildersClasses = reflections.getSubTypesOf(ObjectBuilder.class);
        buildersClasses.forEach(bc -> {
            try {
                ObjectBuilder objectBuilder = bc.getDeclaredConstructor().newInstance();
                builders.add(objectBuilder);
            } catch (Exception ignored) {
                throw new RuntimeException("Something went wrong...");
            }
        });
        System.out.printf("%d builders were added\n", builders.size());
    }

    public static Set<String> getAllTypes() {
        return builders.stream().map(ObjectBuilder::typeName).collect(Collectors.toSet());
    }

    public static ObjectBuilder getBuilder(String name) {
        if (name == null) throw new NullPointerException();
        for (ObjectBuilder b : builders) {
            if (name.equals(b.typeName())) return b;
        }
        throw new IllegalArgumentException();
    }
}
