

// import java.util.Observer;
// import view.IHM;
// import controller.Controleur;
import controller.Controleur;
import model.*;
import testIHM.IHM;


public class Main {
    public static void main(String[] args) {
        // IHM view = new IHM();
        // view.init();
        // //model
        // Plan map=new Plan();



        // map.addObserver((Observer) view.getLabel());
        // map.addObserver((Observer) view.getLabel2());
        // map.addObserver((Observer) view.getLabel3());

        Plan plan = new Plan("src/main/java/smallMap.xml");
        IHM view = new IHM();
        view.init();

        Controleur c=new Controleur(plan);
        view.getBar().getLoadRoute().addActionListener(c);
        
    }
}
