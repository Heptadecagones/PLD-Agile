package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import model.Intersection;
import model.Plan;
import model.Segment;
import model.Tournee;

@SuppressWarnings("serial")
public class Carte extends JPanel implements Observer {
    private final int LONGUEUR = 900;
    private final int HAUTEUR = 650;
    private final int REMBOURRAGE = 20;
    private final double PI = 3.1415926535;

    private Intersection entrepot;
    private ArrayList<Intersection> listeIntersection = new ArrayList<>();
    private ArrayList<Segment> listeSegment = new ArrayList<>();
    private ArrayList<Tournee> listeTournee = new ArrayList<>();

    private int DIAMETRE_INTERSECTION = 4;
    private int DIAMETRE_ENTREPOT = 10;

    private Color couleurIntersection = Color.BLUE;
    private Color couleurEntrepot = Color.RED;
    private Creation fenetreCreation;
    
    public Carte() {
        setPreferredSize(new Dimension(LONGUEUR, HAUTEUR));
        setBorder(new TitledBorder("Carte"));
        fenetreCreation = new Creation();
		fenetreCreation.init();
        addMouseListener(new MouseAdapter() {
            @Override 
            public void mousePressed(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();
                System.out.println(mouseX + "," + mouseY);
                int minmX = 1000000;
                mouseCompare(listeIntersection, mouseX, mouseY, getGraphics(),minmX);
            }
          });
    }
    //UPDATE AU CHANGEMENT DES DONNEES DU MODELE
    @Override
    public void update(Observable arg0, Object arg1) {
        Plan plan = (Plan) arg0;
        listeIntersection = plan.obtenirListeIntersection();
        listeSegment = plan.obtenirListeSegment();
        entrepot = plan.obtenirEntrepot();
        repaint();
    }

    //TOUT CE QUI SE PASSE APRES UN CLIQUE SUR LA CARTE
    public void mouseCompare(ArrayList<Intersection> listeIntersection, int mouseX, int mouseY, Graphics g, int minmX){
        int showX = 0 ;
        int showY = 0 ;
        double minX = 1000.0, maxX = 0.0, minY = 1000.0, maxY = 0.0;
        
        int intersectionmaxX,intersectionmaxY,intersectionminX,intersectionminY; 
        Intersection choixIntersection = new Intersection();
    if (!listeIntersection.isEmpty()) {
        ArrayList<Point2D> points = new ArrayList<>();
        for (Intersection intersection : listeIntersection) {
            Point2D point = convertirLatLong(intersection);
            points.add(point);
            minX = Math.min(minX, point.getX());
            maxX = Math.max(maxX, point.getX());
            minY = Math.min(minY, point.getY());
            maxY = Math.max(maxY, point.getY());
        }
        double diffX = maxX - minX;
        double diffY = maxY - minY;
        
        for (int i = 0; i<points.size();i++) {
            int coordX = REMBOURRAGE + (int)((points.get(i).getX()-minX)/diffX*(LONGUEUR-2*REMBOURRAGE));
            int coordY = REMBOURRAGE + (int)((points.get(i).getY()-minY)/diffY*(HAUTEUR-2*REMBOURRAGE));

            if((Math.abs(mouseX - coordX)+Math.abs(mouseY-coordY)) < minmX){
                showX =  (int)(coordX-5);
                showY =  (int)(coordY-5);
                minmX =  (Math.abs(mouseX - coordX)+Math.abs(mouseY-coordY));   
                choixIntersection = listeIntersection.get(i);
            }
        }        
        System.out.println("this is x for mouse: "+ mouseX+ " and this is the one we found" + showX );

        g.setColor(new Color(0, 255, 0));
        g.fillOval(showX, showY, 10, 10);
        //OUVERTURE FENETRE LIVRAISON
        String message = choixIntersection.toString();
        System.out.println("nouvelleLivraison cliqué");
        fenetreCreation.setIntersection(choixIntersection);
		fenetreCreation.ouvrir();
        /*JOptionPane.showMessageDialog(new JFrame(),
            message,
            "Coordonnée", JOptionPane.PLAIN_MESSAGE);*/
        
        
        


    }


    }


/* 
    public void colorierTourne(ArrayList<Tournee> listeTournee, Graphics g){
        

        
    }
*/
public Creation obtenirFenetreCreation() {
    return fenetreCreation;
}
    //PAINT DE LA CARTE
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2d = (Graphics2D) g;

        RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        rh.put(RenderingHints.KEY_RENDERING,
               RenderingHints.VALUE_RENDER_QUALITY);

        g2d.setRenderingHints(rh);
        
        double minX = Double.MAX_VALUE, maxX = 0.0, minY = Double.MAX_VALUE, maxY = 0.0;
        double diffX = -1.0, diffY = -1.0; // = (max - min)

        if (entrepot != null) {
            Point2D cordEntrepot = convertirLatLong(entrepot); 
            minX = Math.min(minX, cordEntrepot.getX());
            maxX = Math.max(maxX, cordEntrepot.getX());
            minY = Math.min(minY, cordEntrepot.getY());
            maxY = Math.max(maxY, cordEntrepot.getY());
        }

        if (!listeIntersection.isEmpty()) {
            ArrayList<Point2D> points = new ArrayList<>();
            for (Intersection intersection : listeIntersection) {
                Point2D point = convertirLatLong(intersection);
                points.add(point);
                minX = Math.min(minX, point.getX());
                maxX = Math.max(maxX, point.getX());
                minY = Math.min(minY, point.getY());
                maxY = Math.max(maxY, point.getY());
            }
            
            diffX = maxX - minX;
            diffY = maxY - minY;
            
            g2d.setColor(couleurIntersection);

            for (Point2D point : points) {
                int coordX = REMBOURRAGE + (int)((point.getX()-minX)/diffX*(LONGUEUR-2*REMBOURRAGE));
                int coordY = REMBOURRAGE + (int)((point.getY()-minY)/diffY*(HAUTEUR-2*REMBOURRAGE));

                g2d.fillOval(coordX-DIAMETRE_INTERSECTION/2, coordY-DIAMETRE_INTERSECTION/2, DIAMETRE_INTERSECTION, DIAMETRE_INTERSECTION);
            }
        }

        if (entrepot != null) {
            Point2D cordEntrepot = convertirLatLong(entrepot); 
            int entrCordX = REMBOURRAGE + (int)((cordEntrepot.getX()-minX)/diffX*(LONGUEUR-2*REMBOURRAGE));
            int entrCordY = REMBOURRAGE + (int)((cordEntrepot.getY()-minY)/diffX*(HAUTEUR-2*REMBOURRAGE));

            g2d.setColor(couleurEntrepot);
            g2d.fillOval(entrCordX-DIAMETRE_ENTREPOT/2, entrCordY-DIAMETRE_ENTREPOT/2, DIAMETRE_ENTREPOT, DIAMETRE_ENTREPOT);
        }
        
        g2d.setColor(Color.BLACK);

        if (!listeSegment.isEmpty()) {
            ArrayList<Point2D> origines = new ArrayList<>();
            ArrayList<Point2D> destinations = new ArrayList<>();
            for (Segment segment : listeSegment) {
                Point2D origine = convertirLatLong(segment.obtenirOrigine());
                origines.add(origine);
                
                /*
                minX = Math.min(minX, origine.getX());
                maxX = Math.max(maxX, origine.getX());
                minY = Math.min(minY, origine.getY());
                maxY = Math.max(maxY, origine.getY());
                */
                
                Point2D destination = convertirLatLong(segment.obtenirDestination());
                destinations.add(destination);
                
                /*
                minX = Math.min(minX, destination.getX());
                maxX = Math.max(maxX, destination.getX());
                minY = Math.min(minY, destination.getY());
                maxY = Math.max(maxY, destination.getY());
                */
            }
            
            /*
            double diffX = maxX - minX;
            double diffY = maxY - minY;
            */

            for (int i = 0; i < origines.size(); ++i) {
                int origineCordX = REMBOURRAGE + (int)((origines.get(i).getX()-minX)/diffX*(LONGUEUR-2*REMBOURRAGE));
                int origineCordY = REMBOURRAGE + (int)((origines.get(i).getY()-minY)/diffY*(HAUTEUR-2*REMBOURRAGE));
                
                int destinationCordX = REMBOURRAGE + (int)((destinations.get(i).getX()-minX)/diffX*(LONGUEUR-2*REMBOURRAGE));
                int destinationCordY = REMBOURRAGE + (int)((destinations.get(i).getY()-minY)/diffY*(HAUTEUR-2*REMBOURRAGE));
                
                g2d.drawLine(origineCordX, origineCordY, destinationCordX, destinationCordY);
            }
        }
    }
    
    /**
     * Projection de Mercator
     * Convertir de latitude et longitude en coordonnée 2d
     * 
     * @param intersection
     * @return Point2D
     */
    //TRANSFORMATION POUR CLICK (COORDONNEES ECRAN -> COORDONNES CARTE)
    public Point2D convertirLatLong(Intersection intersection) {
        double longitude = intersection.obtenirLongitude();
        double latitude = intersection.obtenirLatitude();

        // obtenir x-coordonnée
        double x = (longitude + 180) * (LONGUEUR / 360);

        // convertir de degré en radian
        double latRad = latitude * PI / 180;

        // obtenir y-coordonnée
        double mercN = Math.log(Math.tan(PI/4 + latRad/2));
        double y = (HAUTEUR/2.0) - (LONGUEUR * mercN / (2.0*PI));

        return(new Point2D.Double(x, y));
    }

}
