

// import java.util.Observer;
// import view.IHM;
// import controller.Controleur;
import java.util.Observer;

import controller.Controleur;
import model.*;
import view.IHM;


public class Main {
    public static void main(String[] args) {
        Plan plan = new Plan();
        IHM view = new IHM();
        view.init();
        plan.addObserver((Observer) view.getMap());

        Controleur c=new Controleur(plan);
        view.getBar().getLoadRoute().addActionListener(c);
        view.getBar().getAddRoute().addActionListener(c);
        view.getBar().getFenetreCreation().getBtnCreerLivraison().addActionListener(c);
        
    }
}
