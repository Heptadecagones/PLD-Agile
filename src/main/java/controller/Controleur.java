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
        p.addObserver((Observer) v.getMap());
        ActionListener c= new ActionListener(){    
            public void actionPerformed(ActionEvent arg0){
            String command = arg0.getActionCommand();
            //System.out.println(command);
            if ("Load".equals(command)) {
                p.chargerXML("src/main/java/smallMap.xml");}
            if ("Add".equals(command)) {
                //p.nouvelleLivraison(4)
                ;}
            if ("Create".equals(command)) {
                    p.nouvelleLivraison(v.getBar().getFenetreCreation().getTextHoraire().getText(),v.getBar().getFenetreCreation().getTextIntersection().getText(),v.getBar().getFenetreCreation().getTextLivreur().getText());
                    System.out.println("Create clicked");}
        }};

        v.getBar().getLoadRoute().addActionListener(c);
        v.getBar().getAddRoute().addActionListener(c);
        v.getBar().getFenetreCreation().getBtnCreerLivraison().addActionListener(c);
    }
    
}
