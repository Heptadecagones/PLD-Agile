/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package testIHM;

/**
 *
 * @author NGUYEN Danh Lan
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class Bar extends JPanel {
    private Font font = new Font("Arial",Font.PLAIN,20);

    private JButton openXML, addRoute, saveRoute, loadRoute;

    public Bar() {
            setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
            setOpaque(false);
            setBorder(new CompoundBorder(new TitledBorder("Bar"), new EmptyBorder(0, 0, 0, 0)));
            // initialise the buttons
            openXML = makeButton("Open");
            addRoute = makeButton("Add");
            saveRoute = makeButton("Save");
            loadRoute = makeButton("Load");

            // add the components to the main panel
            JPanel leftPanel = new JPanel(), rightPanel = new JPanel();
            leftPanel.add(openXML);
            leftPanel.add(addRoute);
            add(leftPanel);

            add(Box.createHorizontalGlue());
            rightPanel.add(saveRoute);
            rightPanel.add(loadRoute);
            add(rightPanel);

            ActionListener action = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                    if (evt.getSource() == openXML) {
                            // your action here
                            System.out.println("openXML clicked");
                    }

                    if (evt.getSource() == addRoute) {
                            // your action here
                            System.out.println("addRoute clicked");
                    }

                    if (evt.getSource() == saveRoute) {
                            // your action here
                            System.out.println("saveRoute clicked");
                    }

                    if (evt.getSource() == loadRoute) {
                            // your action here
                            System.out.println("loadRoute clicked");
                    }
            }
            };

            openXML.addActionListener(action);
            addRoute.addActionListener(action);
            saveRoute.addActionListener(action);
            loadRoute.addActionListener(action);
    }

    public JButton makeButton(String name) {
            JButton button = new JButton(name);
            button.setFont(font);
            button.setFocusPainted(false);
            return button;
    }
}

