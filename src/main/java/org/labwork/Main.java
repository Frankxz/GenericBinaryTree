package org.labwork;

import org.labwork.dataModel.binaryTree.BinaryTree;
import org.labwork.dataModel.builder.ObjectBuilder;
import org.labwork.view.GUI;
import org.labwork.view.TreeActionListener;
import org.labwork.view.TreeActionListenerInterface;

import java.util.ArrayList;
import java.util.Random;

public class Main {

    public static void testTree(ObjectBuilder builder, Integer count) {
        BinaryTree<Object> binaryTree = new BinaryTree<>((o1, o2) -> (Integer) o1 - (Integer) o2);
        ArrayList<Integer> numbers = new ArrayList<Integer>();
        Random r = new Random();

        long startRandomCreateTime = System.nanoTime();
        while (numbers.size() < count) {
            int random = r.nextInt(count);
            if (!numbers.contains(random)) {
                numbers.add(random);
            }
        }
        long stopRandomCreateTime = System.nanoTime() - startRandomCreateTime;
        System.out.println("Time for creating unique random values: " + stopRandomCreateTime / 1000000 + " ms" );

        long startInsertTime = System.nanoTime();
        for (int number : numbers) {
            binaryTree.insertElement(number);
        }
        long stopInsertTime = System.nanoTime() - startInsertTime;

        System.out.println("Total execution time for insert " + count + " random unique objects of type INTEGER in Tree: "
                + stopInsertTime / 1000000 + " ms");
        System.out.println("SIZE AFTER INSERT:" + binaryTree.getSize() + "\n");
    }

    public static void main(String[] args) throws Exception {

        ObjectBuilder builder = ObjectBuilderFactory.getBuilder("Integer");
        BinaryTree<Object> binaryTree = new BinaryTree<>((o1, o2) -> (Integer) o1 - (Integer) o2);
        TreeActionListenerInterface treeActionListener = new TreeActionListener();
        ArrayList<Object> values = new ArrayList<>();
         new GUI(treeActionListener);

        // TESTING
        /*
        testTree(builder, 10000);
        testTree(builder, 25000);
        testTree(builder, 50000);
        testTree(builder, 100000);
        testTree(builder, 200000);
        testTree(builder, 400000);
        testTree(builder, 800000);
        */
    }
}
