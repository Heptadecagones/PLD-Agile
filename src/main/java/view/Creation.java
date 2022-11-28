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
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import model.Plan;

@SuppressWarnings("serial")
public class Creation extends JPanel{
    JButton btnCreerLivraison;
    private Font font = new Font("Arial",Font.PLAIN,20);
    private JFrame f = new JFrame("Ajout Livraison");
    public Creation() {

    }
    public void init(){
        btnCreerLivraison = makeButton("Create");
        f.add(btnCreerLivraison);
        /*JPanel southPanel = new JPanel();
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.LINE_AXIS));
        southPanel.add(description);
        southPanel.add(map);
        f.add(southPanel, BorderLayout.SOUTH);*/
                f.pack();
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.setResizable(false);
                f.setVisible(false);
                ActionListener action = new ActionListener(){
                        public void actionPerformed(ActionEvent evt) {
                                if (evt.getSource() == btnCreerLivraison) {
                                        // your action here
                                        System.out.println("Create clicked");
                                        fermer();
                                }
                        }
                };
                btnCreerLivraison.addActionListener(action);
}

    public void ouvrir(){
        this.f.setVisible(true);
    }
    public void fermer(){
        this.f.setVisible(false);
    }

    public JButton makeButton(String name) {
        JButton button = new JButton(name);
        button.setFont(font);
        button.setFocusPainted(false);
        return button;
        }
}

