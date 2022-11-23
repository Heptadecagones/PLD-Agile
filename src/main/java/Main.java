

import java.util.Observer;

import controller.Controleur;
import model.Plan;
import view.IHM;

public class Main {
    public static void main(String[] args) {
        IHM view = new IHM();
        view.init();
        //model
        Plan map=new Plan();

        Controleur c=new Controleur(map,view.getField(),view.getLabel());
        view.getButton().addActionListener(c);

        map.addObserver((Observer) view.getLabel());
        map.addObserver((Observer) view.getLabel2());
        map.addObserver((Observer) view.getLabel3());

    }
}
