package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import model.Intersection;
import model.Livraison;
import model.Livreur;
import model.PlanLivraison;
import model.Segment;

import java.awt.*;
@SuppressWarnings("serial")
public class Carte extends JPanel implements Observer {
    /**
     * Tous les composants
     */
    private Creation fenetreCreation;

    /**
     * La taille du panneau
     */
    private int LONGUEUR = 900;
    private int HAUTEUR = 650;

    private final int REMBOURRAGE = 10;

    /**
     * Toutes les données de la carte
     */
    private Intersection entrepot;
    private ArrayList<Intersection> listeIntersection = new ArrayList<>();
    private ArrayList<Segment> listeSegment = new ArrayList<>();
    private ArrayList<Livreur> listeLivreur = new ArrayList<>();

    // La coordonnée du 4 coins de la carte
    private double minX = Double.MAX_VALUE, maxX = 0.0, minY = Double.MAX_VALUE, maxY = 0.0;
    // La différence entre les coordonnées max et min
    private double diffX = -1.0, diffY = -1.0;

    private final int DIAMETRE_INTERSECTION = 2;
    private final int DIAMETRE_ENTREPOT = 10;
  
    
    private int un;
    private int deux;
    private int trois;

    private int max_livreur = 10000;
    private Color[] tabColor2=new Color[max_livreur];

    private final Color couleurEntrepot = Color.RED;
    private final Color couleurIntersection = Color.BLUE;
    
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
                mouseCompare(listeIntersection, mouseX, mouseY, getGraphics(), minmX);
           
            }
        });

        for (int i = 0; i < max_livreur; ++i) {
            un= Math.abs((int)(Math.random()*255));
            deux =Math.abs((int)(Math.random()*255));
            trois=Math.abs((int)(Math.random()*255));
            tabColor2[i] = new Color(un, deux, trois);
        }
    }
       
   

    // MISE À JOUR AU CHANGEMENT DES DONNÉES DU MODÈLE
    @Override
    public void update(Observable arg0, Object arg1) {
        PlanLivraison planLivraison = (PlanLivraison) arg0;
        listeIntersection = planLivraison.obtenirPlan().obtenirListeIntersection();
        listeSegment = planLivraison.obtenirPlan().obtenirListeSegment();
        entrepot = planLivraison.obtenirPlan().obtenirEntrepot();
        listeLivreur=planLivraison.obtenirListeLivreur();
        repaint();
    }

    // TOUT CE QUI SE PASSE APRES UN CLIQUE SUR LA CARTE
    public void mouseCompare(ArrayList<Intersection> listeIntersection, int mouseX, int mouseY, Graphics g, int minmX) {
        int showX = 0;
        int showY = 0;
        double minX = Double.MAX_VALUE, maxX = 0.0, minY = Double.MAX_VALUE, maxY = 0.0;

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

            for (int i = 0; i < points.size(); i++) {
                int coordX = REMBOURRAGE + (int) ((points.get(i).getX() - minX) / diffX * (LONGUEUR - 2 * REMBOURRAGE));
                int coordY = REMBOURRAGE + (int) ((points.get(i).getY() - minY) / diffY * (HAUTEUR - 2 * REMBOURRAGE));

                if ((Math.abs(mouseX - coordX) + Math.abs(mouseY - coordY)) < minmX) {
                    showX = (int) (coordX - 5);
                    showY = (int) (coordY - 5);
                    minmX = (Math.abs(mouseX - coordX) + Math.abs(mouseY - coordY));
                    choixIntersection = listeIntersection.get(i);
                }
            }
            System.out.println("this is x for mouse: " + mouseX + " and this is the one we found: " + showX);

            g.setColor(new Color(0, 255, 0));
            g.fillOval(showX, showY, 10, 10);
            // OUVERTURE FENETRE LIVRAISON
            String message = choixIntersection.toString();
            System.out.println("nouvelleLivraison cliqué");
            fenetreCreation.setIntersection(choixIntersection);
            fenetreCreation.ouvrir();
            /*
             * JOptionPane.showMessageDialog(new JFrame(),
             * message,
             * "Coordonnée", JOptionPane.PLAIN_MESSAGE);
             */

        }

    }

    /*
     * public void colorierTourne(ArrayList<Tournee> listeTournee, Graphics g){
     * 
     * 
     * 
     * }
     */
    public Creation obtenirFenetreCreation() {
        return fenetreCreation;
    }

    // PAINT DE LA CARTE
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

        // Calculer les coins de la carte
        if (entrepot != null) {
            Point2D cordEntrepot = convertirLatLong(entrepot);
            minX = Math.min(minX, cordEntrepot.getX());
            maxX = Math.max(maxX, cordEntrepot.getX());
            minY = Math.min(minY, cordEntrepot.getY());
            maxY = Math.max(maxY, cordEntrepot.getY());
        }
        if (!listeIntersection.isEmpty()) {
            for (Intersection intersection : listeIntersection) {
                Point2D point = convertirLatLong(intersection);
                minX = Math.min(minX, point.getX());
                maxX = Math.max(maxX, point.getX());
                minY = Math.min(minY, point.getY());
                maxY = Math.max(maxY, point.getY());
            }
        }

        diffX = maxX - minX;
        diffY = maxY - minY;
        
     
        // PEINTURE
        if (!listeSegment.isEmpty()) { 
            int i ;
            for (Segment segment : listeSegment) {

                Point2D origine = convertirLatLong(segment.obtenirOrigine());
                //origines.add(origine);

                Point2D destination = convertirLatLong(segment.obtenirDestination());
                //destinations.add(destination);

                /*
                 * minX = Math.min(minX, destination.getX());
                 * maxX = Math.max(maxX, destination.getX());
                 * minY = Math.min(minY, destination.getY());
                 * maxY = Math.max(maxY, destination.getY());
                 */
                int origineCordX = REMBOURRAGE
                + (int) ((origine.getX() - minX) / diffX * (LONGUEUR - 2 * REMBOURRAGE));
                int origineCordY = REMBOURRAGE
                + (int) ((origine.getY() - minY) / diffY * (HAUTEUR - 2 * REMBOURRAGE));

                int destinationCordX = REMBOURRAGE
                + (int) ((destination.getX() - minX) / diffX * (LONGUEUR - 2 * REMBOURRAGE));
                int destinationCordY = REMBOURRAGE
                + (int) ((destination.getY() - minY) / diffY * (HAUTEUR - 2 * REMBOURRAGE));
                g2d.setColor(Color.BLACK);
                g2d.setStroke(new BasicStroke(1));
                i=0;
                for(Livreur livr : listeLivreur){
                    i++;
                    for(Segment s : livr.obtenirTournee().obtenirListeSegment()){
                        if(segment==s){
                            //System.out.println(t.toString()+i+tabColor[i]);
                            g2d.setColor(tabColor2[i]);
                            g2d.setStroke(new BasicStroke(3));
                        }
                    }
                }
                
                g2d.drawLine(origineCordX, origineCordY, destinationCordX, destinationCordY);
                
                
            }

            /*
             * double diffX = maxX - minX;
             * double diffY = maxY - minY;
             */

            /*for (int i = 0; i < origines.size(); ++i) {
                int origineCordX = REMBOURRAGE
                        + (int) ((origines.get(i).getX() - minX) / diffX * (LONGUEUR - 2 * REMBOURRAGE));
                int origineCordY = REMBOURRAGE
                        + (int) ((origines.get(i).getY() - minY) / diffY * (HAUTEUR - 2 * REMBOURRAGE));

                int destinationCordX = REMBOURRAGE
                        + (int) ((destinations.get(i).getX() - minX) / diffX * (LONGUEUR - 2 * REMBOURRAGE));
                int destinationCordY = REMBOURRAGE
                        + (int) ((destinations.get(i).getY() - minY) / diffY * (HAUTEUR - 2 * REMBOURRAGE));

                g2d.drawLine(origineCordX, origineCordY, destinationCordX, destinationCordY);
            }*/
        }

        if (!listeIntersection.isEmpty()) {
            ArrayList<Point2D> points = new ArrayList<>();
            for (Intersection intersection : listeIntersection) {
                Point2D point = convertirLatLong(intersection);
                points.add(point);
            }

            g2d.setColor(couleurIntersection);

            for (Point2D point : points) {
                int coordX = REMBOURRAGE + (int) ((point.getX() - minX) / diffX * (LONGUEUR - 2 * REMBOURRAGE));
                int coordY = REMBOURRAGE + (int) ((point.getY() - minY) / diffY * (HAUTEUR - 2 * REMBOURRAGE));

                g2d.fillOval(coordX - DIAMETRE_INTERSECTION / 2, coordY - DIAMETRE_INTERSECTION / 2,
                        DIAMETRE_INTERSECTION, DIAMETRE_INTERSECTION);
            }
        }


        if (entrepot != null) {
            Point2D cordEntrepot = convertirLatLong(entrepot);
            int entrCordX = REMBOURRAGE + (int) ((cordEntrepot.getX() - minX) / diffX * (LONGUEUR - 2 * REMBOURRAGE));
            int entrCordY = REMBOURRAGE + (int) ((cordEntrepot.getY() - minY) / diffY * (HAUTEUR - 2 * REMBOURRAGE));

            g2d.setColor(couleurEntrepot);
            g2d.fillOval(entrCordX - DIAMETRE_ENTREPOT / 2, entrCordY - DIAMETRE_ENTREPOT / 2,
                    DIAMETRE_ENTREPOT, DIAMETRE_ENTREPOT);
        }
    


    for(Livreur livr : listeLivreur){
        for(Livraison s : livr.obtenirLivraisons()){
            Point2D cordLivr = convertirLatLong(s.obtenirLieu());

            int livrCordX = REMBOURRAGE + (int) ((cordLivr.getX() - minX) / diffX * (LONGUEUR - 2 * REMBOURRAGE));
            int livrCordY = REMBOURRAGE + (int) ((cordLivr.getY() - minY) / diffY * (HAUTEUR - 2 * REMBOURRAGE));

            g2d.setColor(couleurIntersection);
            g2d.fillOval(livrCordX - DIAMETRE_ENTREPOT / 2, livrCordY - DIAMETRE_ENTREPOT / 2,
                    DIAMETRE_ENTREPOT, DIAMETRE_ENTREPOT);
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
    // TRANSFORMATION POUR CLICK (COORDONNEES ECRAN -> COORDONNES CARTE)
    public Point2D convertirLatLong(Intersection intersection) {
        double longitude = intersection.obtenirLongitude();
        double latitude = intersection.obtenirLatitude();

        // obtenir x-coordonnée
        double x = (longitude + 180) * (LONGUEUR / 360);

        // convertir de degré en radian
        double latRad = latitude * Math.PI / 180;

        // obtenir y-coordonnée
        double mercN = Math.log(Math.tan(Math.PI / 4 + latRad / 2));
        double y = (HAUTEUR / 2.0) - (LONGUEUR * mercN / (2.0 * Math.PI));

        return (new Point2D.Double(x, y));
    }

}
