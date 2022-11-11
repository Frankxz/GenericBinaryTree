package org.labwork.view;

import org.labwork.ObjectBuilderFactory;
import org.labwork.dataModel.builder.ObjectBuilder;

import javax.swing.*;
import java.util.Vector;

public abstract class AbstractTreeActionListener implements TreeActionListenerInterface {
    protected final String filename = "file.dat";

    protected final DefaultListModel<Object> listModel = new DefaultListModel<>(); // for JLIST

    protected ObjectBuilder builder;

    @Override
    public DefaultListModel<Object> getListModel() {
        return listModel;
    }
}
