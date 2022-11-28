/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

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
    private Creation fenetreCreation;
    public void setOpenXML(JButton openXML) {
        this.openXML = openXML;
}

public void setAddRoute(JButton addRoute) {
        this.addRoute = addRoute;
}

public void setSaveRoute(JButton saveRoute) {
        this.saveRoute = saveRoute;
}

public void setLoadRoute(JButton loadRoute) {
        this.loadRoute = loadRoute;
}

public JButton getOpenXML() {
        return openXML;
}

public JButton getAddRoute() {
        return addRoute;
}

public JButton getSaveRoute() {
        return saveRoute;
}

public JButton getLoadRoute() {
        return loadRoute;
}

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
            Creation fenetreCreation=new Creation();
            fenetreCreation.setVisible(true);
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

