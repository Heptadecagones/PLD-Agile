package controller;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.*;

public class IHM {
    JFrame frame;
    JTextField field;
    JButton button;
    public JFrame getFrame() {
        return frame;
    }
    public JTextField getField() {
        return field;
    }
    public void setFrame(JFrame frame) {
        this.frame = frame;
    }
    public void setField(JTextField field) {
        this.field = field;
    }
    public void setButton(JButton button) {
        this.button = button;
    }
    public JButton getButton() {
        return button;
    }

    /**
     * 
     */
    public void init(){
        frame=new JFrame("Exemple MVC");
        field = new JTextField();
        field.setColumns(10);
        button=new JButton("OK");
        frame.setLayout(new FlowLayout());
        frame.getContentPane().add(field);
        frame.getContentPane().add(button);
        frame.setVisible(true);
        frame.pack();
    }
}
