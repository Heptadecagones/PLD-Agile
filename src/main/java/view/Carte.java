package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import model.Intersection;
import model.Livraison;
import model.Livreur;
import model.PlanLivraison;
import model.Segment;

@SuppressWarnings("serial")
public class Carte extends JPanel implements Observer, MouseWheelListener, MouseListener, MouseMotionListener {
    /**
     * Toutes les données de la carte pour l'affichage du plan
     */

    private int largeur;
    private int hauteur;

    private final int REMBOURRAGE = 5;

    // La coordonnée du 4 coins de la carte
    private double minX = Double.MAX_VALUE, maxX = 0.0, minY = Double.MAX_VALUE, maxY = 0.0;
    // La différence entre les coordonnées max et min
    private double diffX = -1.0, diffY = -1.0;

    private final int DIAMETRE_INTERSECTION_DEFAULT = 2; // diamètre par default
    private final int DIAMETRE_INTERSECTION = 10; // diamètre dans tous les scénarios sauf default
    private final int DIAMETRE_ENTREPOT = 12;
    private final int MAX_LIVREUR = 30; // OBTIENT CE NOMBRE DEPUIS LE MODELE
    // La liste de couleur pour les tournées
    private Color[] tabCouleurLivreur = new Color[MAX_LIVREUR];

    private final Color couleurEntrepot = Color.RED;
    private final Color couleurIntersection = Color.BLUE;
    private final Color couleurChoixIntersection = Color.GREEN;
    private final Color couleurRueSurvole = Color.MAGENTA;
    // Attribut pour zoomer et glisser la carte
    AffineTransform atCarte = new AffineTransform();
    private final double maxZoomFacteur = 5.0;
    private double zoomFacteur = 1.0;
    private double prevZoomFacteur = 1.0;
    private boolean zoomer = false;
    private boolean dragger = false;
    private boolean released = false;
    private double xOffset = 0;
    private double yOffset = 0;
    private int xDiff = 0;
    private int yDiff = 0;
    private Point startPoint;

    /**
     * Toutes les données de la carte pour l'application
     */
    private Intersection entrepot;
    private ArrayList<Intersection> listeIntersection = new ArrayList<>();
    private ArrayList<Segment> listeSegment = new ArrayList<>();
    private ArrayList<Livreur> listeLivreur = new ArrayList<>();

    // L'intersection la plus proche cliqué par la souris
    private Intersection choixIntersection = new Intersection();
    // La rue la plus proche survole par la souris
    private Segment rueSurvole = new Segment();
    // La livraison sur la carte selectionné par l'utilisateur
    private Livraison livraisonClickee;


    @Override
    // Garantie d'une carte carrée
    public Dimension getPreferredSize() {
        Dimension d = getParent().getSize();
        int min = Math.min((int)d.getWidth(), (int)d.getHeight());
        this.largeur = min;
        this.hauteur = min;

        return new Dimension(min, min);
    }
    public void modifierLivraisonClickee(Livraison l){
        livraisonClickee=l;
    }

    public Carte() {
        initDonnee();
        addMouseWheelListener(this);
        addMouseMotionListener(this);
        addMouseListener(this);
    }

    /**
     * (Re)Initialise les donnees de la carte 
     * Utilisée quand l'utilisateur charge une nouvelle carte
     */
    public void initDonnee() {
        minX = Double.MAX_VALUE;
        maxX = 0.0;
        minY = Double.MAX_VALUE;
        maxY = 0.0;
        
        diffX = -1.0;
        diffY = -1.0;

        entrepot = new Intersection();
        listeIntersection = new ArrayList<>();
        listeSegment = new ArrayList<>();
        listeLivreur = new ArrayList<>();

        choixIntersection = new Intersection();

        // Init les couleurs de la route pour chaque livreur
        for (int i = 0; i < MAX_LIVREUR; ++i) {
            double total_coulour = 0 ;
            int check_white;
            int rouge ;
            int vert ;
            int bleu ;
           
            do{
            rouge = Math.abs((int)(Math.random()*255));
            vert = Math.abs((int)(Math.random()*255));
            bleu = Math.abs((int)(Math.random()*255));
            check_white = rouge + vert + bleu;
            total_coulour = 0.3*rouge + 0.59*vert + 0.11*bleu;}
            while(total_coulour < 128.0 && check_white >= 763 );

            tabCouleurLivreur[i] = new Color(rouge, vert, bleu);
        }

        // Init les donnees pour le zoom
        atCarte = new AffineTransform();
        zoomFacteur = 1;
        prevZoomFacteur = 1;
        zoomer = false;
        dragger = false;
        released = false;
        xOffset = 0;
        yOffset = 0;
        xDiff = 0;
        yDiff = 0;
        startPoint = new Point();
    }

    // MISE À JOUR AU CHANGEMENT DES DONNÉES DU MODÈLE
    @Override
    public void update(Observable arg0, Object arg1) {
        PlanLivraison planLivraison = (PlanLivraison) arg0;
        listeIntersection = planLivraison.obtenirPlan().obtenirListeIntersection();
        listeSegment = planLivraison.obtenirPlan().obtenirListeSegment();
        entrepot = planLivraison.obtenirPlan().obtenirEntrepot();
        listeLivreur = planLivraison.obtenirListeLivreur();

        // Calculer les coins de la carte
        if (entrepot.obtenirId() != null) {
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

            diffX = maxX - minX;
            diffY = maxY - minY;
        }
        repaint();
        
    }

    
    /**
     * @param sourisX
     * @param sourisY
     * @param maxDistance : La distance Manhattan maximale acceptable
     * @return : L'intersection la plus proche de la souris
     */
    public Intersection chercherIntersectionProche(int sourisX, int sourisY, double maxDistance) {
        Intersection intersectionProche = new Intersection();
        if (!listeIntersection.isEmpty()) {
            for (Intersection intersection : listeIntersection) {
                Point2D point = convertirLatLong(intersection);

                int coordX = REMBOURRAGE + (int) ((point.getX() - minX) / diffX * (largeur - 2 * REMBOURRAGE));
                int coordY = REMBOURRAGE + (int) ((point.getY() - minY) / diffY * (hauteur - 2 * REMBOURRAGE));
                Point2D pointCarte = new Point2D.Double(coordX, coordY);

                // Prends en compte le zoom de la carte
                Point2D pointCarteZoom = new Point2D.Double();
                atCarte.transform(pointCarte, pointCarteZoom);

                if ( (Math.abs(sourisX - pointCarteZoom.getX()) + Math.abs(sourisY - pointCarteZoom.getY())) < maxDistance*zoomFacteur) {
                    maxDistance = (Math.abs(sourisX - pointCarteZoom.getX()) + Math.abs(sourisY - pointCarteZoom.getY())) / zoomFacteur;
                    intersectionProche = intersection;
                }
            }
        }
        
        return intersectionProche;
    }

    /**
     * @param sourisX
     * @param sourisY
     * @param maxDistance : La distance Manhattan maximale acceptable
     * @return : La rue le plus proche a la souris
     */
    public Segment recupererRue(int sourisX, int sourisY, double maxDistance) {
        Segment rue = new Segment();
        if (!listeSegment.isEmpty()) {
            for (Segment segment : listeSegment) {
                Point2D origine = convertirLatLong(segment.obtenirOrigine());
                Point2D destination = convertirLatLong(segment.obtenirDestination());

                int origineCordX = REMBOURRAGE
                    + (int) ((origine.getX() - minX) / diffX * (largeur - 2 * REMBOURRAGE));
                int origineCordY = REMBOURRAGE
                    + (int) ((origine.getY() - minY) / diffY * (hauteur - 2 * REMBOURRAGE));

                int destinationCordX = REMBOURRAGE
                    + (int) ((destination.getX() - minX) / diffX * (largeur - 2 * REMBOURRAGE));
                int destinationCordY = REMBOURRAGE
                    + (int) ((destination.getY() - minY) / diffY * (hauteur - 2 * REMBOURRAGE));

                Point2D origineCarte = new Point2D.Double(origineCordX, origineCordY);
                Point2D destinationCarte = new Point2D.Double(destinationCordX, destinationCordY);

                // Prends en compte le zoom de la carte
                Point2D origineCarteZoom = new Point2D.Double();
                Point2D destinationCarteZoom = new Point2D.Double();

                atCarte.transform(origineCarte, origineCarteZoom);
                atCarte.transform(destinationCarte, destinationCarteZoom);

                if (distancePointSegment(sourisX, sourisY, origineCarteZoom.getX(), origineCarteZoom.getY(), destinationCarteZoom.getX(), destinationCarteZoom.getY()) < maxDistance*zoomFacteur) {
                    rue = segment;
                    maxDistance = (distancePointSegment(sourisX, sourisY, origineCarteZoom.getX(), origineCarteZoom.getY(), destinationCarteZoom.getX(), destinationCarteZoom.getY())) / zoomFacteur;
                }
            }
        }

        return rue;
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
        
        // traiter zoom et glissement
        if (zoomer) {
            AffineTransform at = new AffineTransform();

            double xRel = MouseInfo.getPointerInfo().getLocation().getX() - getLocationOnScreen().getX();
            double yRel = MouseInfo.getPointerInfo().getLocation().getY() - getLocationOnScreen().getY();

            double zoomDiv = zoomFacteur / prevZoomFacteur;

            xOffset = (zoomDiv) * (xOffset) + (1 - zoomDiv) * xRel;
            yOffset = (zoomDiv) * (yOffset) + (1 - zoomDiv) * yRel;

            at.translate(xOffset, yOffset);
            at.scale(zoomFacteur, zoomFacteur);

            prevZoomFacteur = zoomFacteur;
            g2d.transform(at);
            zoomer = false;
        } else if (dragger) {
            AffineTransform at = new AffineTransform();
            at.translate(xOffset + xDiff, yOffset + yDiff);
            at.scale(zoomFacteur, zoomFacteur);

            g2d.transform(at);

            if (released) {
                xOffset += xDiff;
                yOffset += yDiff;

                xDiff = 0;
                yDiff = 0;
                dragger = false;
            }
        } else {
            g2d.transform(atCarte);
        }

        atCarte = g2d.getTransform();

        // PEINTURE
        if (!listeSegment.isEmpty()) { 
            int i;
            for (Segment segment : listeSegment) {
                Point2D origine = convertirLatLong(segment.obtenirOrigine());
                Point2D destination = convertirLatLong(segment.obtenirDestination());

                int origineCordX = REMBOURRAGE
                    + (int) ((origine.getX() - minX) / diffX * (largeur - 2 * REMBOURRAGE));
                int origineCordY = REMBOURRAGE
                    + (int) ((origine.getY() - minY) / diffY * (hauteur - 2 * REMBOURRAGE));

                int destinationCordX = REMBOURRAGE
                    + (int) ((destination.getX() - minX) / diffX * (largeur - 2 * REMBOURRAGE));
                int destinationCordY = REMBOURRAGE
                    + (int) ((destination.getY() - minY) / diffY * (hauteur - 2 * REMBOURRAGE));
                
                g2d.setColor(Color.BLACK);
                g2d.setStroke(new BasicStroke(1));
                i = 0;
                for(Livreur livr : listeLivreur){
                    i++;
                    for (Segment s : livr.obtenirTournee().obtenirListeSegment()){
                        if (segment == s) {
                            //System.out.println(t.toString()+i+tabColor[i]);
                            g2d.setColor(tabCouleurLivreur[i]);
                            g2d.setStroke(new BasicStroke(3));
                            break;
                        }
                    }
                }
                if(livraisonClickee!=null){
                    for (Segment s : livraisonClickee.obtenirLivreur().obtenirTournee().obtenirListeSegment()){
                            if (segment == s) {
                                g2d.setColor(Color.RED);
                                g2d.setStroke(new BasicStroke(5));
                                break;
                            }
                    }
                }
                // rue survolee par la souris
                if (segment == rueSurvole) {
                    g2d.setColor(couleurRueSurvole);
                    g2d.setStroke(new BasicStroke(3));
                }
                g2d.drawLine(origineCordX, origineCordY, destinationCordX, destinationCordY);
            }
        }


        if (!listeIntersection.isEmpty()) {
            g2d.setColor(couleurIntersection);

            for (Intersection intersection : listeIntersection) {
                Point2D point = convertirLatLong(intersection);

                int coordX = REMBOURRAGE + (int) ((point.getX() - minX) / diffX * (largeur - 2 * REMBOURRAGE));
                int coordY = REMBOURRAGE + (int) ((point.getY() - minY) / diffY * (hauteur - 2 * REMBOURRAGE));

                g2d.fillOval(coordX - DIAMETRE_INTERSECTION_DEFAULT / 2, coordY - DIAMETRE_INTERSECTION_DEFAULT / 2,
                    DIAMETRE_INTERSECTION_DEFAULT, DIAMETRE_INTERSECTION_DEFAULT);
            }
        }

        if (entrepot.obtenirId() != null) {
            Point2D cordEntrepot = convertirLatLong(entrepot);
            int entrCordX = REMBOURRAGE + (int) ((cordEntrepot.getX() - minX) / diffX * (largeur - 2 * REMBOURRAGE));
            int entrCordY = REMBOURRAGE + (int) ((cordEntrepot.getY() - minY) / diffY * (hauteur - 2 * REMBOURRAGE));

            g2d.setColor(couleurEntrepot);
            g2d.fillOval(entrCordX - DIAMETRE_ENTREPOT / 2, entrCordY - DIAMETRE_ENTREPOT / 2,
                    DIAMETRE_ENTREPOT, DIAMETRE_ENTREPOT);
        }
    
        for (Livreur livr : listeLivreur) {
            for (Livraison s : livr.obtenirLivraisons()) {
                Point2D cordLivr = convertirLatLong(s.obtenirLieu());

                int livrCordX = REMBOURRAGE + (int) ((cordLivr.getX() - minX) / diffX * (largeur - 2 * REMBOURRAGE));
                int livrCordY = REMBOURRAGE + (int) ((cordLivr.getY() - minY) / diffY * (hauteur - 2 * REMBOURRAGE));
                g2d.setColor(couleurIntersection);
                g2d.fillOval(livrCordX - DIAMETRE_INTERSECTION / 2, livrCordY - DIAMETRE_INTERSECTION / 2,
                    DIAMETRE_INTERSECTION, DIAMETRE_INTERSECTION);
            }
        }

        //Colorier en jaune la livraison cliquée
        if (livraisonClickee != null) {
            Point2D cordChoixIntersection = convertirLatLong(livraisonClickee.obtenirLieu());
            g2d.setColor(Color.YELLOW);
            int cordChoixIntersectionX = REMBOURRAGE + (int) ((cordChoixIntersection.getX() - minX) / diffX * (largeur - 2 * REMBOURRAGE));
            int cordChoixIntersectionY = REMBOURRAGE + (int) ((cordChoixIntersection.getY() - minY) / diffY * (hauteur - 2 * REMBOURRAGE));
            g2d.fillOval(cordChoixIntersectionX-DIAMETRE_INTERSECTION/2, cordChoixIntersectionY-DIAMETRE_INTERSECTION/2, DIAMETRE_INTERSECTION, DIAMETRE_INTERSECTION);
        }
        //Colorier en vert l'intersection choisie
        if (choixIntersection.obtenirId() != null) {
            Point2D cordChoixIntersection = convertirLatLong(choixIntersection);

            int cordChoixIntersectionX = REMBOURRAGE + (int) ((cordChoixIntersection.getX() - minX) / diffX * (largeur - 2 * REMBOURRAGE));
            int cordChoixIntersectionY = REMBOURRAGE + (int) ((cordChoixIntersection.getY() - minY) / diffY * (hauteur - 2 * REMBOURRAGE));

            g2d.setColor(couleurChoixIntersection);
            g2d.fillOval(cordChoixIntersectionX-DIAMETRE_INTERSECTION/2, cordChoixIntersectionY-DIAMETRE_INTERSECTION/2, DIAMETRE_INTERSECTION, DIAMETRE_INTERSECTION);
        }
    }


    /**
     * Projection de Mercator
     * Convertir de latitude et longitude en coordonnée 2d
     * 
     * @param intersection
     * @return Point2D
     */
    public Point2D convertirLatLong(Intersection intersection) {
        double longitude = intersection.obtenirLongitude();
        double latitude = intersection.obtenirLatitude();

        // obtenir x-coordonnée
        double x = (longitude + 180) * (largeur / 360);

        // convertir de degré en radian
        double latRad = latitude * Math.PI / 180;

        // obtenir y-coordonnée
        double mercN = Math.log(Math.tan(Math.PI / 4 + latRad / 2));
        double y = (hauteur / 2.0) - (largeur * mercN / (2.0 * Math.PI));

        return (new Point2D.Double(x, y));
    }

    // La distance le plus courte d'un point à un point de droite (x0, y0) à un segment de droite composé de deux points (x1, y1), (x2, y2)
    private double distancePointSegment(double x0, double y0, double x1, double y1, double x2, double y2) {
        double space = 0;
        double a, b, c;
        a = lineSpace(x1, y1, x2, y2); // la longueur du segment
        b = lineSpace(x1, y1, x0, y0); // la distance de (x0,y0) et (x1, y1)
        c = lineSpace(x2, y2, x0, y0); // la distance de (x0,y0) et (x2, y2)
        if (c <= 0.000001 || b <= 0.000001) {
            space = 0;
            return space;
        }
        if (a <= 0.000001) {
            space = b;
            return space;
        }
        if (c * c >= a * a + b * b) {
            space = b;
            return space;
        }
        if (b * b >= a * a + c * c) {
            space = c;
            return space;
        }
        double p = (a + b + c) / 2; // demi-circonférence
        double s = Math.sqrt(p * (p-a) * (p-b) * (p-c)); // Formule de Héron pour calculer l'aire de triangle
        space = 2 * s / a; // la distance du point à la ligne
        return space;
    }

    // Calculate the distance between two points
    private double lineSpace(double x1, double y1, double x2, double y2) {
        double lineLength = 0;
        lineLength = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
        return lineLength;
    }


    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        final double cstZoom = 1.1;
        zoomer = true;

        //Zoom in
        if (e.getWheelRotation() < 0) {
            if (zoomFacteur * cstZoom <= maxZoomFacteur) {
                zoomFacteur *= cstZoom;
            }

            repaint();
        }
        //Zoom out
        if (e.getWheelRotation() > 0) {
            zoomFacteur /= cstZoom;
            repaint();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Point curPoint = e.getLocationOnScreen();
        xDiff = curPoint.x - startPoint.x;
        yDiff = curPoint.y - startPoint.y;

        dragger = true;
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // Afficher le nom de la rue plus proche a la souris sur la carte
        int sourisX = e.getX();
        int sourisY = e.getY();
        double maxDistance = 50.0;

        rueSurvole = recupererRue(sourisX, sourisY, maxDistance);
        if (rueSurvole.obtenirNom() != null) {
            //pour nom de rues, peut poser problèmes sur certains pc
            setToolTipText(rueSurvole.obtenirNom());
        }
        repaint();
    }
    public Intersection carteCliquee(MouseEvent e){
        int sourisX = e.getX();
        int sourisY = e.getY();

        double maxDistance = 20.0;
        choixIntersection=chercherIntersectionProche(sourisX, sourisY, maxDistance);
        return(choixIntersection);
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        int sourisX = e.getX();
        int sourisY = e.getY();

        double maxDistance = 20.0;
        
        choixIntersection = chercherIntersectionProche(sourisX, sourisY, maxDistance);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        released = false;
        startPoint = MouseInfo.getPointerInfo().getLocation();
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        released = true;
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}