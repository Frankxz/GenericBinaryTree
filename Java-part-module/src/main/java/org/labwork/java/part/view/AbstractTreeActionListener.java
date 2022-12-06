package org.labwork.java.part.view;

import org.labwork.java.part.dataModel.builder.ObjectBuilder;

import javax.swing.*;

public abstract class AbstractTreeActionListener implements TreeActionListenerInterface {
    protected final String filename = "file.dat";

    protected final DefaultListModel<Object> listModel = new DefaultListModel<>(); // for JLIST

    protected ObjectBuilder builder;

    @Override
    public DefaultListModel<Object> getListModel() {
        return listModel;
    }
}
