package org.labwork.view;

import javax.swing.*;
import java.util.Vector;

public interface TreeActionListenerInterface {
    void onAdd(String text);

    void onRemove(int index);

    void onSave();

    void onLoad();

    void onSelectType(String type);

    void print();

    DefaultListModel<Object> getListModel();
}
