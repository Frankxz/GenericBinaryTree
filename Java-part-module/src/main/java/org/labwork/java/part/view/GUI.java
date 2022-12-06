package org.labwork.java.part.view;

import org.labwork.java.part.ObjectBuilderFactory;

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {
    private final JList<Object> jList;

    public GUI(TreeActionListenerInterface actionListener) throws HeadlessException {
        this.jList = new JList<>(actionListener.getListModel());
        jList.setLayoutOrientation(JList.VERTICAL);
        Container container = getContentPane();
        container.setLayout(new BorderLayout());

        JPanel listPanel = new JPanel();
        JLabel listLabel = new JLabel("");
        listPanel.add(new JScrollPane(jList));
        listPanel.add(listLabel);
        container.add(listPanel, BorderLayout.EAST);


        JPanel buttonsPanel = new JPanel();
        container.add(buttonsPanel, BorderLayout.WEST);
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));

        JPanel chooseType = new JPanel();
        buttonsPanel.add(chooseType);

        JComboBox<String> comboBox = new JComboBox<>(ObjectBuilderFactory.getAllTypes().toArray(new String[0]));
        chooseType.add(comboBox);

        comboBox.addActionListener(e -> {
            JComboBox source = (JComboBox) e.getSource();
            String selectedItem = (String) source.getSelectedItem();
            actionListener.onSelectType(selectedItem);

            buttonsPanel.add(new GUIComponent(actionListener::onAdd, "Insert"));
            buttonsPanel.add(new GUIComponent(() -> actionListener.onRemove(jList.getSelectedIndex()), "Remove"));
            buttonsPanel.add(new GUIComponent(actionListener::onSave, "Save"));
            buttonsPanel.add(new GUIComponent(actionListener::onLoad, "Load"));
            buttonsPanel.add(new GUIComponent(actionListener::print, "Print in console"));
            buttonsPanel.remove(chooseType);

            revalidate();
            repaint();
        });

        setTitle("Miller Magnushevsky LW # 2 SCALA");
        setPreferredSize(new Dimension(520, 220));
        setMaximumSize(new Dimension(520, 220));
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

}
