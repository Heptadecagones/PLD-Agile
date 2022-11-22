

import java.util.Observer;

import controller.Controller;
import model.Map;
import view.IHM;

public class Main {
    public static void main(String[] args) {
        IHM view = new IHM();
        view.init();
        //model
        Map map=new Map();

        Controller c=new Controller(map,view.getField(),view.getLabel());
        view.getButton().addActionListener(c);

        map.addObserver((Observer) view.getLabel());
        map.addObserver((Observer) view.getLabel2());
        map.addObserver((Observer) view.getLabel3());

    }
}
