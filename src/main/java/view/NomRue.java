package view;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;

public class NomRue extends JLabel {
    public NomRue() {
    }
    public void init() {
        setVerticalAlignment(CENTER);
        setHorizontalAlignment(CENTER);
        setFont(new Font("Arial", Font.PLAIN, 12));
        setText("nom de la rue");
    }
}
