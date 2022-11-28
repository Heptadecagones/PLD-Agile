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
public class Barre extends JPanel {
    private Font font = new Font("Arial",Font.PLAIN,20);
    private JButton ajouterLivraison, sauvegarder, charger;
    private Creation fenetreCreation;
/*   public void modifierOpenXML(JButton openXML) {
        this.openXML = openXML;
}*/

public void modifierAjouterLivraison(JButton AjouterLivraison) {
        this.ajouterLivraison = AjouterLivraison;
}

public Creation obtenirFenetreCreation() {
        return fenetreCreation;
}

public void modifierSauvegarder(JButton Sauvegarder) {
        this.sauvegarder = Sauvegarder;
}

public void modifierCharger(JButton Charger) {
        this.charger = Charger;
}

/*public JButton obtenirOpenXML() {
        return openXML;
}*/

public JButton obtenirAjouterLivraison() {
        return ajouterLivraison;
}

public JButton obtenirSauvegarder() {
        return sauvegarder;
}

public JButton obtenirCharger() {
        return charger;
}

public Barre() {
            
            setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
            setOpaque(false);
            setBorder(new CompoundBorder(new TitledBorder("Bar"), new EmptyBorder(0, 0, 0, 0)));
            // initialise the buttons
            //openXML = makeButton("Open");
            ajouterLivraison = makeButton("Ajouter_Livraison");
            sauvegarder = makeButton("Sauvegarder");
            charger = makeButton("Charger");
            fenetreCreation=new Creation();
            fenetreCreation.init();
            // add the components to the main panel
            JPanel leftPanel = new JPanel(), rightPanel = new JPanel();
            //leftPanel.add(openXML);
            leftPanel.add(ajouterLivraison);
            add(leftPanel);

            add(Box.createHorizontalGlue());
            rightPanel.add(sauvegarder);
            rightPanel.add(charger);
            add(rightPanel);

            ActionListener action = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                    /*if (evt.getSource() == openXML) {
                            // your action here
                            System.out.println("openXML clicked");
                    }*/

                    if (evt.getSource() == ajouterLivraison) {
                            // your action here
                            System.out.println("AjouterLivraison clicked");
                            fenetreCreation.ouvrir();
                    }

                    if (evt.getSource() == sauvegarder) {
                            // your action here
                            System.out.println("Sauvegarder clicked");
                    }

                    if (evt.getSource() == charger) {
                            // your action here
                            System.out.println("Charger clicked");
                    }
            }
            };

            //openXML.addActionListener(action);
            ajouterLivraison.addActionListener(action);
            sauvegarder.addActionListener(action);
            charger.addActionListener(action);

    }

    public JButton makeButton(String name) {
            JButton button = new JButton(name);
            button.setFont(font);
            button.setFocusPainted(false);
            return button;
    }
}

