package controller;
import java.awt.event.*;

import javax.swing.JTextField;

import model.Map;
public class Controller implements ActionListener {
    
    Map m;
    JTextField field;

    public Controller(Map m,JTextField field){
        this.m=m;
        this.field=field;
    }
    
    public void actionPerformed(ActionEvent arg0){
        m.setValeur(field.getText());
    }
}
