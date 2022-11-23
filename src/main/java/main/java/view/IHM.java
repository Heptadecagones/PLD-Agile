package main.java.view;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.*;

public class IHM {
    JFrame frame;
    JTextField field;
    JButton button;
    JLabelObserver label;
    public void setLabel2(JLabelObserver label2) {
        this.label2 = label2;
    }
    public void setLabel3(JLabelObserver label3) {
        this.label3 = label3;
    }

    JLabelObserver label2;
    public JLabel getLabel2() {
        return label2;
    }
    public JLabel getLabel3() {
        return label3;
    }

    JLabelObserver label3;
    public void setLabel(JLabelObserver label) {
        this.label = label;
    }
    public JLabel getLabel() {
        return label;
    }
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
        label=new JLabelObserver("label 1");
        label2=new JLabelObserver("label 2");
        label3=new JLabelObserver("label 3");
        frame.setLayout(new FlowLayout());
        frame.getContentPane().add(field);
        frame.getContentPane().add(button);
        frame.getContentPane().add(label);
        frame.getContentPane().add(label2);
        frame.getContentPane().add(label3);
        frame.setVisible(true);
        frame.pack();
    }
}
