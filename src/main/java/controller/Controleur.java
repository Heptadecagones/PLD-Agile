package controller;
import java.awt.event.*;

import javax.swing.JLabel;
import javax.swing.JTextField;

import model.Plan;
public class Controleur implements ActionListener {
    
    Plan p;
    JTextField field;
    JLabel label;

    public Controleur(Plan p,JTextField field,JLabel label){
        this.p=p;
        this.field=field;
        this.label=label;
    }
    public Controleur(Plan p){
        this.p=p;
    }
    
    public void actionPerformed(ActionEvent arg0){
        String command = arg0.getActionCommand();
        //System.out.println(command);
        if ("Load".equals(command)) {
            p.chargerXML("src/main/java/smallMap.xml");}
        if ("Add".equals(command)) {
            p.nouvelleLivraison(4);}
    }
}
