package controller;
import java.awt.event.*;

import javax.swing.JLabel;
import javax.swing.JTextField;

import model.Plan;
public class Controleur implements ActionListener {
    
    Plan m;
    JTextField field;
    JLabel label;

    public Controleur(Plan m,JTextField field,JLabel label){
        this.m=m;
        this.field=field;
        this.label=label;
    }
    
    public void actionPerformed(ActionEvent arg0){
        //m.setValeur(field.getText());
    }
}
