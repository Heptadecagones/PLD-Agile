

// import java.util.Observer;
// import view.IHM;
// import controller.Controleur;
import model.*;


public class Main {
    public static void main(String[] args) {
        // IHM view = new IHM();
        // view.init();
        // //model
        // Plan map=new Plan();

        // Controleur c=new Controleur(map,view.getField(),view.getLabel());
        // view.getButton().addActionListener(c);

        // map.addObserver((Observer) view.getLabel());
        // map.addObserver((Observer) view.getLabel2());
        // map.addObserver((Observer) view.getLabel3());

        Plan plan = new Plan("src/main/java/smallMap.xml");
        System.out.println(plan.toString());
        
    }
}
