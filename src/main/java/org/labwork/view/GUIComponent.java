package org.labwork.view;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class GUIComponent extends JPanel {
    public GUIComponent(EmptyConsumer action, String buttonText) {
        JButton button = new JButton(buttonText);
        button.setPreferredSize(new Dimension(200, 25));
        button.setMinimumSize(new Dimension(200, 25));
        button.addActionListener(e -> action.accept());

        switch (buttonText) {
            case "Remove" -> button.setBackground(Color.red.brighter());
            case "Save", "Load" -> button.setBackground(Color.orange);
            default -> button.setBackground(Color.white);
        }

        add(button);
    }


    public GUIComponent(Consumer<String> action, String buttonText) {
        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(100, 25));
        JButton button = new JButton(buttonText);
        button.setBackground(Color.green);
        button.addActionListener(e -> {
            action.accept(textField.getText());
            textField.setText("");

        });
        add(textField);
        add(button);
    }
}
