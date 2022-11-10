package org.labwork.view;

import java.util.Vector;

public interface TreeActionListenerInterface {
    void onAdd(String text);

    void onRemove(int index);

    void onSave();

    void onLoad();

    void onSelectType(String type);

    Vector <Object> getModel();
}
