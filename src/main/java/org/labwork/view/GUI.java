package org.labwork.view;

import org.labwork.ObjectBuilderFactory;

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {
    private final JList<Object> jList;

    public GUI(TreeActionListenerInterface actionListener) throws HeadlessException {
        this.jList = new JList<>(actionListener.getModel());
        Container container = getContentPane();
        container.setLayout(new BorderLayout());

        JPanel listPanel = new JPanel();
        listPanel.add(new JScrollPane(jList));
        container.add(listPanel, BorderLayout.CENTER);

        JPanel right = new JPanel();
        container.add(right, BorderLayout.EAST);

        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        JPanel chooseType = new JPanel();
        right.add(chooseType);
        JComboBox<String> comboBox = new JComboBox<>(ObjectBuilderFactory.getAllTypes().toArray(new String[0]));
        chooseType.add(comboBox);
        comboBox.addActionListener(e -> {
            JComboBox source = (JComboBox) e.getSource();
            String selectedItem = (String) source.getSelectedItem();
            actionListener.onSelectType(selectedItem);
            right.add(new GUIComponent(actionListener::onAdd, "Add element"));
            right.add(new GUIComponent(() -> actionListener.onRemove(jList.getSelectedIndex()), "Remove element"));
            right.add(new GUIComponent(actionListener::onSave, "Save"));
            right.add(new GUIComponent(actionListener::onLoad, "Load"));
            right.remove(chooseType);
            revalidate();
            repaint();
        });

        setTitle("Miller Magnushevsky LW # 1");
        setPreferredSize(new Dimension(640, 200));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

}
