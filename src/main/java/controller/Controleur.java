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
        System.out.println(p.toString());
    }
}
