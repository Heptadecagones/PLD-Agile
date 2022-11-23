/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package testIHM;

/**
 *
 * @author NGUYEN Danh Lan
 */

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class Description extends JPanel {
	
    public Description() {
            setPreferredSize(new Dimension(300, 600));
            setBorder(new CompoundBorder(new TitledBorder("Description"), new EmptyBorder(0, 0, 0, 0)));
            // TO DO
    }
}
