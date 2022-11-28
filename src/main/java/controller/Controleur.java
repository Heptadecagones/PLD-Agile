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
        p.addObserver((Observer) v.getCarte());
        p.addObserver((Observer) v.getDescription());
        ActionListener c= new ActionListener(){    
            public void actionPerformed(ActionEvent arg0){
            String command = arg0.getActionCommand();
            //System.out.println(command);
            if ("Load".equals(command)) {
                p.chargerXML("src/main/java/largeMap.xml");}
            if ("Add".equals(command)) {
                //p.nouvelleLivraison(4)
                ;}
            if ("Create".equals(command)) {
                    p.nouvelleLivraison(v.getBarre().getFenetreCreation().getTextHoraire().getText(),v.getBarre().getFenetreCreation().getTextIntersection().getText(),v.getBarre().getFenetreCreation().getTextLivreur().getText());
                    System.out.println("Create clicked");}
        }};

        v.getBarre().getLoadRoute().addActionListener(c);
        v.getBarre().getAddRoute().addActionListener(c);
        v.getBarre().getFenetreCreation().getBtnCreerLivraison().addActionListener(c);
    }
    
}
