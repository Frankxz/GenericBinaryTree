package org.labwork.java.part.service;

import org.labwork.java.part.dataModel.binaryTree.BinaryTreeInterface;
import org.labwork.java.part.dataModel.builder.Builder;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;

public class TreeFileLoader {

    public static <Object> void saveToFile(String filename, BinaryTreeInterface list, Builder<Object> builder) throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(filename)) {
            writer.println(builder.typeName());
            list.forEach(el -> writer.println(builder.toString((Object) el)));
        }
    }

    public static <Object> BinaryTreeInterface loadFromFile(String filename, Builder<Object> builder, BinaryTreeInterface tree) throws Exception {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;

            line = br.readLine();
            if (!builder.typeName().equals(line)) {
                throw new Exception("Wrong file structure");
            }

            while ((line = br.readLine()) != null) {
                tree.insertElement(builder.createFromString(line));
            }
            return tree;
        }
    }
}
