package main.java.view;
import main.java.model.Map;
import javax.swing.JLabel;
import java.util.*;
@SuppressWarnings({ "serial", "deprecation" })
public class JLabelObserver extends JLabel implements Observer{
    public JLabelObserver(String l){
        super(l);
    }
    @Override
    public void update(Observable arg0,Object arg1){
        Map m=(Map) arg0;
        setText(m.getValeur());
   }
}
