package controller;
import java.awt.event.*;

import javax.swing.JLabel;
import javax.swing.JTextField;

import model.Map;
public class Controller implements ActionListener {
    
    Map m;
    JTextField field;
    JLabel label;

    public Controller(Map m,JTextField field,JLabel label){
        this.m=m;
        this.field=field;
        this.label=label;
    }
    
    public void actionPerformed(ActionEvent arg0){
        m.setValeur(field.getText());
    }
}
