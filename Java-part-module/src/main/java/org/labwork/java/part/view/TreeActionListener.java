package org.labwork.java.part.view;

import org.labwork.java.part.ObjectBuilderFactory;
import org.labwork.java.part.dataModel.binaryTree.BinaryTree;
import org.labwork.java.part.dataModel.binaryTree.BinaryTreeInterface;
import org.labwork.java.part.service.TreeFileLoader;

import java.io.FileNotFoundException;

public class TreeActionListener extends AbstractTreeActionListener {

    protected BinaryTreeInterface items = new BinaryTree<>(null);

    @Override
    public void onAdd(String text) {
        if (text.isEmpty()) return;
        Object data = builder.createFromString(text);
        if (items.insertElement(data)) {
            listModel.addElement(data);
        }
    }

    @Override
    public void onRemove(int index) {
        Object data = listModel.get(index);
        items.deleteElement(data);
        listModel.remove(index);
    }

    @Override
    public void onSave() {
        try {
            TreeFileLoader.saveToFile(filename, items, builder);
        } catch (FileNotFoundException e) {
            System.err.println("Unable to write list to a file");
            e.printStackTrace();
        }
    }

    @Override
    public void onLoad() {
        try {
            items = TreeFileLoader.loadFromFile(filename, builder, new BinaryTree<>(builder.getComparator()));
            listModel.clear();
            items.forEach(listModel::addElement);
        } catch (Exception e) {
            System.err.println("Unable to read list from a file");
            e.printStackTrace();
        }
    }

    @Override
    public void onSelectType(String type) {
        try {
            builder = ObjectBuilderFactory.getBuilder(type);
            items.setComparator(builder.getComparator());

        } catch (Exception ignored) {

        }
    }

    @Override
    public void print() {
        items.printTree();
    }
}
