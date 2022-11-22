package view;
import model.Map;
import javax.swing.JLabel;
import java.util.*;
public class JLabelObserver extends JLabel implements Observer{
    public JLabelObserver(String l){
        super(l);
    }
    public void update(Observable arg0,Object arg1){
        Map m=(Map) arg0;
        setText(m.getValeur());
    }
}
