package org.labwork.view;

import org.labwork.ObjectBuilderFactory;
import org.labwork.dataModel.builder.ObjectBuilder;

import javax.swing.*;
import java.util.Vector;

public abstract class AbstractTreeActionListener implements TreeActionListenerInterface {
    protected final String filename = "file.dat";

    protected final Vector<Object> treeModel = new Vector<>();
    protected ObjectBuilder builder;

    @Override
    public Vector <Object> getModel() {
        return treeModel;
    }

    @Override
    public void onSelectType(String type) {
        try {
            builder = ObjectBuilderFactory.getBuilder(type);
        } catch (Exception ignored) {

        }
    }

}
