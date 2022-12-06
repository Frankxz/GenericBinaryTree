package org.labwork.java.part.view;

import javax.swing.*;

public interface TreeActionListenerInterface {
    void onAdd(String text);

    void onRemove(int index);

    void onSave();

    void onLoad();

    void onSelectType(String type);

    void print();

    DefaultListModel<Object> getListModel();
}
