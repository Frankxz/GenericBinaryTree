package org.labwork;

import org.labwork.dataModel.binaryTree.BinaryTree;
import org.labwork.dataModel.builder.ObjectBuilder;
import org.labwork.view.GUI;
import org.labwork.view.TreeActionListener;
import org.labwork.view.TreeActionListenerInterface;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws Exception {

        ObjectBuilder builder = ObjectBuilderFactory.getBuilder("Integer");
        BinaryTree<Object> binaryTree = new BinaryTree<>((o1, o2) -> (Integer) o1 - (Integer) o2);
        TreeActionListenerInterface treeActionListener = new TreeActionListener();
        ArrayList<Object> values = new ArrayList<>();
        new GUI(treeActionListener);

        System.out.println("Test values: ");
        addTestValues(builder, values);
        values.forEach(System.out::println);

        System.out.println("Test values in Tree");
        insertTestValuesInTree(binaryTree, values);

        System.out.println("Size: " + binaryTree.getSize());
        binaryTree.printTree();

    }

    public static void insertTestValuesInTree(BinaryTree<Object> binaryTree, ArrayList<Object> values) {
        for (Object value : values) {
            binaryTree.insertElement(value);
            System.out.println(binaryTree.getSize());
        }
    }

    public static void addTestValues(ObjectBuilder builder, ArrayList<Object> values) {
        for (int i = 0; i < 10; i++) {
            values.add(builder.create());
        }
    }
}
