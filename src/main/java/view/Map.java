/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

/**
 *
 * @author NGUYEN Danh Lan
 */
import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import model.Plan;

@SuppressWarnings("serial")
public class Map extends JPanel implements Observer{
    JTextArea mapText;
    
    public JTextArea getMapText() {
        return mapText;
    }

    public void setMapText(String text) {
        this.mapText.setText(text);
    }
    @Override
    public void update(Observable arg0,Object arg1){
        Plan p=(Plan) arg0;
        setMapText(p.toString());
    }
    public Map() {
            mapText = new JTextArea("Pas de contenu");
            setPreferredSize(new Dimension(600, 600));
            setBorder(new CompoundBorder(new TitledBorder("Map"), new EmptyBorder(0, 0, 0, 0)));
            this.add(mapText);
            // TO DO
    }
}

