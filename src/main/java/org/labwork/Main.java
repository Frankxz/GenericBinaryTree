package org.labwork;

import org.labwork.dataModel.binaryTree.BinaryTree;
import org.labwork.dataModel.builder.ObjectBuilder;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws Exception {
        ObjectBuilder builder = ObjectBuilderFactory.getBuilder("Integer");

        BinaryTree<Object> binaryTree = new BinaryTree<>((o1, o2) -> (Integer) o1 - (Integer) o2);

        ArrayList<Object> values = new ArrayList<>();
        values.add(builder.create());
        values.add(builder.create());
        values.add(builder.create());
        values.add(builder.create());
        values.add(builder.create());
        values.add(builder.create());
        values.add(builder.create());
        values.add(builder.create());
        values.add(builder.create());
        values.add(builder.create());
        values.add(builder.create());
        values.add(builder.create());
        values.add(builder.create());
        values.add(builder.create());
        values.add(builder.create());

        values.forEach(System.out::println);

        binaryTree.add(values.get(1));
        binaryTree.add(values.get(1));
        binaryTree.add(values.get(2));
        binaryTree.add(values.get(3));
        binaryTree.add(values.get(4));
        binaryTree.add(values.get(5));
        binaryTree.add(values.get(6));
        binaryTree.add(values.get(7));
        binaryTree.add(values.get(8));
        binaryTree.add(values.get(9));
        binaryTree.add(values.get(10));
        binaryTree.add(values.get(11));
        binaryTree.add(values.get(12));
        binaryTree.add(values.get(13));
        binaryTree.add(values.get(14));


        System.out.println("Size: " + binaryTree.size());
        System.out.println("Height: " + binaryTree.height());
        binaryTree.printTree();

//        System.out.println("InOrder sort:");
//        binaryTree.traverseInOrder();
//
//        System.out.println("\nPreOrder sort:");
//        binaryTree.traversePreOrder();
//
//        System.out.println("\nLevelOrder sort: ");
//        binaryTree.traverseLevelOrder();
//
//        binaryTree.delete(values.get(3));
//        System.out.println("\nInOrder sort after removing:");
//        binaryTree.traverseInOrder();
//
//        binaryTree.print2D();

    }
}
