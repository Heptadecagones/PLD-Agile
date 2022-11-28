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
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import model.Livraison;
import model.Plan;

@SuppressWarnings("serial")
public class Creation extends JPanel{
    private JButton btnCreerLivraison;
    private Font font = new Font("Arial",Font.PLAIN,20);
    private JFrame f = new JFrame("Ajout Livraison");
    private JTextField textHoraire = new JTextField("horaire");
    private JTextField textLivreur = new JTextField("livreur");
    private JTextField textIntersection = new JTextField("intersection");
    

    public JButton getBtnCreerLivraison() {
        return btnCreerLivraison;
        }
    public Creation() {

    }
    public void init(){
        btnCreerLivraison = makeButton("Create");
        
        JPanel Panel = new JPanel();
        Panel.setLayout(new BoxLayout(Panel, BoxLayout.Y_AXIS));
        Panel.add(textIntersection);
        Panel.add(textHoraire);
        Panel.add(textLivreur);
        Panel.add(btnCreerLivraison);
        f.add(Panel);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setResizable(true);
        f.setVisible(false);
        f.pack();
        ActionListener action = new ActionListener(){
                public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource() == btnCreerLivraison) {
                                // your action here
                                fermer();
                        }
                }
        };
        btnCreerLivraison.addActionListener(action);
}

    public JTextField getTextHoraire() {
        return textHoraire;
}
public JTextField getTextLivreur() {
        return textLivreur;
}
public JTextField getTextIntersection() {
        return textIntersection;
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

