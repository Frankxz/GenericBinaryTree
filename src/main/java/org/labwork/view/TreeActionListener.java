package org.labwork.view;

import org.labwork.dataModel.binaryTree.BinaryTree;
import org.labwork.dataModel.binaryTree.BinaryTreeInterface;
import org.labwork.service.TreeFileLoader;

import java.io.FileNotFoundException;

public class TreeActionListener extends AbstractTreeActionListener {

    protected BinaryTreeInterface<Object> items = new BinaryTree<>(null);

    @Override
    public void onAdd(String text) {
        if (text.isEmpty()) return;
        Object data = builder.createFromString(text);
        items.insertElement(data);
        treeModel.addElement(data);
    }

    @Override
    public void onRemove(int index) {
        items.deleteElement(index);
        treeModel.remove(index);
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
            treeModel.clear();
            items.forEach(treeModel::addElement);
        } catch (Exception e) {
            System.err.println("Unable to read list from a file");
            e.printStackTrace();
        }
    }
}
