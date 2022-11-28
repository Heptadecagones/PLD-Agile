package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.ColorUIResource;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import model.Intersection;

import model.Plan;
import model.Segment;

@SuppressWarnings("serial")
public class Carte extends JPanel implements Observer {
    private final int LONGUEUR = 900;
    private final int HAUTEUR = 700;
    private final int PADDING = 30;

    private final double PI = 3.1415926535;
    private Intersection entrepot;
    ArrayList<Intersection> listeIntersection = new ArrayList<>();
    ArrayList<Segment> listeSegment = new ArrayList<>();
    
    public Carte() {
        setPreferredSize(new Dimension(LONGUEUR, HAUTEUR));
        setBorder(new CompoundBorder(new TitledBorder("Carte"), new EmptyBorder(0, 0, 0, 0)));


            addMouseListener(new MouseAdapter() {
            @Override 

            public void mousePressed(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();
                System.out.println(mouseX + "," + mouseY);
            }
          });




    }

/* 

   public Intersection mouseCompare(ArrayList<Intersection> listeIntersection, int mouseX, int mouseY){
    if (!listeIntersection.isEmpty()) {
        ArrayList<Point2D> points = new ArrayList<>();
        double minX = 1000.0, maxX = 0.0, minY = 1000.0, maxY = 0.0;
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
        for (Point2D point : points) {
            int coordX = PADDING + (int)((point.getX()-minX)/diffX*(LONGUEUR-2*PADDING));
            int coordY = PADDING + (int)((point.getY()-minY)/diffY*(HAUTEUR-2*PADDING));



        }
    }


    }
   */ 
    @Override
    public void update(Observable arg0, Object arg1) {
        
        Plan plan = (Plan) arg0;
        listeIntersection = plan.obtenirListeIntersection();
        listeSegment = plan.obtenirListeSegment();
        entrepot = plan.obtenirEntrepot();
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {

       
        super.paintComponent(g);
        
        Graphics2D g2d = (Graphics2D) g;
       
        if (!listeIntersection.isEmpty()) {
            ArrayList<Point2D> points = new ArrayList<>();
            double minX = 1000.0, maxX = 0.0, minY = 1000.0, maxY = 0.0;
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
            for (Point2D point : points) {
                int coordX = PADDING + (int)((point.getX()-minX)/diffX*(LONGUEUR-2*PADDING));
                int coordY = PADDING + (int)((point.getY()-minY)/diffY*(HAUTEUR-2*PADDING));
                
            
            

                g.drawOval(coordX, coordY, 2, 2);
            }
        }
        
        if (!listeSegment.isEmpty()) {
            ArrayList<Point2D> origines = new ArrayList<>();
            ArrayList<Point2D> destinations = new ArrayList<>();
            
            double minX = 1000.0, maxX = 0.0, minY = 1000.0, maxY = 0.0;
            for (Segment segment : listeSegment) {
                Point2D origine = convertirLatLong(segment.obtenirOrigine());
                origines.add(origine);
                
                minX = Math.min(minX, origine.getX());
                maxX = Math.max(maxX, origine.getX());
                minY = Math.min(minY, origine.getY());
                maxY = Math.max(maxY, origine.getY());
                
                
                Point2D destination = convertirLatLong(segment.obtenirDestination());
                destinations.add(destination);
                
                minX = Math.min(minX, destination.getX());
                maxX = Math.max(maxX, destination.getX());
                minY = Math.min(minY, destination.getY());
                maxY = Math.max(maxY, destination.getY());
            }
            
            double diffX = maxX - minX;
            double diffY = maxY - minY;
            
            for (int i = 0; i < origines.size(); ++i) {
                int origineCoordX = PADDING + (int)((origines.get(i).getX()-minX)/diffX*(LONGUEUR-2*PADDING));
                int origineCoordY = PADDING + (int)((origines.get(i).getY()-minY)/diffY*(HAUTEUR-2*PADDING));
                
                int destinationCoordX = PADDING + (int)((destinations.get(i).getX()-minX)/diffX*(LONGUEUR-2*PADDING));
                int destinationCoordY = PADDING + (int)((destinations.get(i).getY()-minY)/diffY*(HAUTEUR-2*PADDING));
                
                g.drawLine(origineCoordX, origineCoordY, destinationCoordX, destinationCoordY);
            }  
            
            
        if (entrepot != null) {
            Point2D cordone_entrepot = convertirLatLong(entrepot); 
    
            int entr_cordX = PADDING + (int)((cordone_entrepot.getX()-minX)/diffX*(LONGUEUR-2*PADDING));
            int entr_cordY = PADDING + (int)((cordone_entrepot.getY()-minY)/diffX*(LONGUEUR-2*PADDING));
            g.setColor(new Color(255, 0, 0));
            g.fillOval(entr_cordX, entr_cordY, 10, 10);
        }
        }

       
    }
    
    // convertir latitude et longitude en 2d coordonnees
    public Point2D convertirLatLong(Intersection intersection) {
        double longitude = intersection.obtenirLongitude();
        double latitude = intersection.obtenirLatitude();

        // get x value
        double x = (longitude + 180) * (LONGUEUR / 360);

        // convert from degrees to radians
        double latRad = latitude * PI / 180;

        // get y value
        double mercN = Math.log(Math.tan(PI/4 + latRad/2));
        double y = (HAUTEUR/2.0) - (LONGUEUR * mercN / (2.0*PI));

        return(new Point2D.Double(x, y));
    }

  
}
