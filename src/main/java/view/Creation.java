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
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import model.Plan;

@SuppressWarnings("serial")
public class Creation extends JPanel{
    JButton btnCreerLivraison;
    private Font font = new Font("Arial",Font.PLAIN,20);

    public Creation() {
            setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
            setOpaque(false);
            setBorder(new CompoundBorder(new TitledBorder("Bar"), new EmptyBorder(0, 0, 0, 0)));
            setPreferredSize(new Dimension(600, 200));
            btnCreerLivraison = makeButton("Create");
            this.add(btnCreerLivraison);
            // TO DO
    }

    public JButton makeButton(String name) {
        JButton button = new JButton(name);
        button.setFont(font);
        button.setFocusPainted(false);
        return button;
}
}

