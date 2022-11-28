package controller;
import java.awt.event.*;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JTextField;

import model.Plan;
import view.IHM;
public class Controleur{
    
    Plan p;
    JTextField field;
    JLabel label;
    IHM v;

    public Controleur(){
        p = new Plan();
        v = new IHM();
        v.init();
        p.addObserver((Observer) v.obtenirCarte());
        p.addObserver((Observer) v.obtenirDescription());
        ActionListener c= new ActionListener(){    
            public void actionPerformed(ActionEvent arg0){
            String command = arg0.getActionCommand();
            //System.out.println(command);
            if ("Charger".equals(command)) {
                p.chargerXML("src/main/java/largeMap.xml");}
            if ("Ajouter_Livraison".equals(command)) {
                //p.nouvelleLivraison(4)
                ;}
            if ("Creer".equals(command)) {
                    p.nouvelleLivraison(v.obtenirBarre().obtenirFenetreCreation().getTextHoraire().getText(),v.obtenirBarre().obtenirFenetreCreation().getTextIntersection().getText(),v.obtenirBarre().obtenirFenetreCreation().getTextLivreur().getText());
                    System.out.println("Create clicked");}
        }};

        v.obtenirBarre().obtenirCharger().addActionListener(c);
        v.obtenirBarre().obtenirAjouterLivraison().addActionListener(c);
        v.obtenirBarre().obtenirFenetreCreation().obtenirBtnCreerLivraison().addActionListener(c);
    }
    
}
