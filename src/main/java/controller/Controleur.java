package controller;
import java.awt.event.*;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JTextField;

import model.Plan;
import view.IHM;
public class Controleur{
    
    Plan plan;
    IHM view;

    public Controleur(){
        plan = new Plan();
        view = new IHM();
        view.init();
        plan.addObserver((Observer) view.obtenirCarte());
        plan.addObserver((Observer) view.obtenirDescription());
        ActionListener c= new ActionListener(){    
            public void actionPerformed(ActionEvent arg0){
                String command = arg0.getActionCommand();
                //System.out.println(command);
                if ("Charger".equals(command)) {
                    plan.chargerXML("src/main/java/mediumMap.xml");}
                /*if ("Nouvelle livraison".equals(command)) {
                    //plan.nouvelleLivraison(4)
                    ;}*/
                if ("Creer".equals(command)) {
                    plan.nouvelleLivraison(view.obtenirCarte().obtenirFenetreCreation().obtenirTextHoraire(),view.obtenirCarte().obtenirFenetreCreation().obtenirIntersection(),view.obtenirCarte().obtenirFenetreCreation().obtenirTextLivreur());
                    System.out.println("Creer cliqué");}
        }};

        view.obtenirBarre().obtenirCharger().addActionListener(c);
        view.obtenirCarte().obtenirFenetreCreation().obtenirBtnCreerLivraison().addActionListener(c);
    }
    
}
