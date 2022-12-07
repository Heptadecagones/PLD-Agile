package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
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
    private int largeur = 650;
    private int hauteur = 650;

    public int obtenirLargeur() {
        return largeur;
    }

    public void modifierLargeur(int largeur) {
        this.largeur = largeur;
    }

    public int obtenirHauteur() {
        return hauteur;
    }

    public void modifierHauteur(int hauteur) {
        this.hauteur = hauteur;
    }

    /**
     * Toutes les données de la carte
     */
    // Le rembourrage de la carte par rapport à le panneau
    private final int REMBOURRAGE = 10;

    // La coordonnée du 4 coins de la carte
    private double minX = Double.MAX_VALUE, maxX = 0.0, minY = Double.MAX_VALUE, maxY = 0.0;
    // La différence entre les coordonnées max et min
    private double diffX = -1.0, diffY = -1.0;

    private Intersection entrepot;
    private ArrayList<Intersection> listeIntersection = new ArrayList<>();
    private ArrayList<Segment> listeSegment = new ArrayList<>();
    private ArrayList<Livreur> listeLivreur = new ArrayList<>();

    // L'intersection la plus proche cliqué par la souris
    private Intersection choixIntersection = new Intersection();
    // Le segment le plus proche survole par la souris
    private Segment choixSegment = new Segment();

    private final int DIAMETRE_INTERSECTION = 2;
    private final int DIAMETRE_ENTREPOT = 12;
    private final int DIAMETRE_CHOIX_INTERSECTION = 10;
    private final int DIAMETRE_DEST_LIVRIAISON = 10;

    private final int MAX_LIVREUR = 1000; // OBTIENT CE NOMBRE DEPUIS LE MODELE
    // La liste de couleur pour la route de livreur
    private Color[] tabCouleurLivreur = new Color[MAX_LIVREUR];

    private final Color couleurEntrepot = Color.RED;
    private final Color couleurIntersection = Color.BLUE;
    private final Color couleurChoixIntersection = Color.GREEN;

    public Carte(int largeur, int hauteur) {
        this.largeur = largeur;
        this.hauteur = hauteur;
        setPreferredSize(new Dimension(largeur, hauteur));
        
        setBorder(new TitledBorder("Carte"));

        fenetreCreation = new Creation();
        fenetreCreation.init();

        initDonnee();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int sourisX = e.getX();
                int sourisY = e.getY();
                System.out.println(sourisX + "," + sourisY);
                int maxDistance = 100000;
                
                choixIntersection = chercherIntersectionProche(sourisX, sourisY, maxDistance);
                System.out.println("nouvelleLivraison cliqué");
                fenetreCreation.setIntersection(choixIntersection);
                fenetreCreation.ouvrir();
                repaint();
            }
        });

        // Afficher le nom de la rue plus proche a la souris sur la carte
        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                String rue;
                int mouseX = e.getX();
                int mouseY = e.getY();
                rue = recupererRue(mouseX, mouseY, 100).obtenirNom();
                System.out.println(rue);
                if (rue != null) {
                    setToolTipText(rue);
                }
            }
        });
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
        listeIntersection = new ArrayList<Intersection>();
        listeSegment = new ArrayList<Segment>();
        listeLivreur = new ArrayList<Livreur>();

        choixIntersection = new Intersection();
        choixSegment = new Segment();

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
    }

    // MISE À JOUR AU CHANGEMENT DES DONNÉES DU MODÈLE
    @Override
    public void update(Observable arg0, Object arg1) {
        PlanLivraison planLivraison = (PlanLivraison) arg0;
        listeIntersection = planLivraison.obtenirPlan().obtenirListeIntersection();
        listeSegment = planLivraison.obtenirPlan().obtenirListeSegment();
        entrepot = planLivraison.obtenirPlan().obtenirEntrepot();
        listeLivreur = planLivraison.obtenirListeLivreur();
        repaint();
    }

    
    /**
     * @param mouseX
     * @param mouseY
     * @param maxDistance : la distance maximale de l'intersection la plus proche
     * @return L'intersection la plus proche de la souris
     */
    public Intersection chercherIntersectionProche(int sourisX, int sourisY, int maxDistance) {
        Intersection intersectionProche = new Intersection();
        if (!listeIntersection.isEmpty()) {
            ArrayList<Point2D> points = new ArrayList<>();
            for (Intersection intersection : listeIntersection) {
                Point2D point = convertirLatLong(intersection);
                points.add(point);
            }

            for (int i = 0; i < points.size(); i++) {
                int coordX = REMBOURRAGE + (int) ((points.get(i).getX() - minX) / diffX * (largeur - 2 * REMBOURRAGE));
                int coordY = REMBOURRAGE + (int) ((points.get(i).getY() - minY) / diffY * (hauteur - 2 * REMBOURRAGE));

                if ((Math.abs(sourisX - coordX) + Math.abs(sourisY - coordY)) < maxDistance) {
                    maxDistance = (Math.abs(sourisX - coordX) + Math.abs(sourisY - coordY));
                    intersectionProche = listeIntersection.get(i);
                }
            }
        }
        
        return intersectionProche;
    }

     /**
     * @param X : 
     * @param Y
     * @param minmX : la distance maximale acceptable
     * @return la rue le plus proche a la souris
     */
    public Segment recupererRue(int X, int Y, int minmX) {
        Segment rue = new Segment();
        int minMxDest = minmX;
        Intersection choixIntersection = new Intersection();
        if (!listeIntersection.isEmpty()) {
            ArrayList<Point2D> points = new ArrayList<>();
            for (Intersection intersection : listeIntersection) {
                Point2D point = convertirLatLong(intersection);
                points.add(point);

            }

            for (int i = 0; i < points.size(); i++) {
                int coordX = REMBOURRAGE + (int) ((points.get(i).getX() - minX) / diffX * (largeur - 2 * REMBOURRAGE));
                int coordY = REMBOURRAGE + (int) ((points.get(i).getY() - minY) / diffY * (hauteur - 2 * REMBOURRAGE));

                if ((Math.abs(X - coordX) + Math.abs(Y - coordY)) < minmX) {
                    minmX = (Math.abs(X - coordX) + Math.abs(Y - coordY));
                    choixIntersection = listeIntersection.get(i);
                }
            }

            if (!listeIntersection.isEmpty()) {
                if (choixIntersection.obtenirListeSegmentOrigine() != null) {
                    for (Segment segment : choixIntersection.obtenirListeSegmentOrigine()) {
                        Intersection destination = segment.obtenirDestination();
                        Point2D point = convertirLatLong(destination);

                        int coordoX = REMBOURRAGE
                                + (int) ((point.getX() - minX) / diffX * (largeur - 2 * REMBOURRAGE));
                        int coordoY = REMBOURRAGE + (int) ((point.getY() - minY) / diffY * (hauteur - 2 * REMBOURRAGE));

                        if ((Math.abs(X - coordoX) + Math.abs(Y - coordoY)) < minMxDest) {
                            rue = segment;
                            minmX = (Math.abs(X - coordoX) + Math.abs(Y - coordoY));
                        }

                    }
                }
            }
        }

        return rue;
    }

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
     

        // PEINTURE
        if (!listeSegment.isEmpty()) { 
            int i;
            for (Segment segment : listeSegment) {
                Point2D origine = convertirLatLong(segment.obtenirOrigine());
                Point2D destination = convertirLatLong(segment.obtenirDestination());

                int origineCordX = REMBOURRAGE
                + (int) ((origine.getX() - minX) / diffX * (hauteur - 2 * REMBOURRAGE));
                int origineCordY = REMBOURRAGE
                + (int) ((origine.getY() - minY) / diffY * (largeur - 2 * REMBOURRAGE));

                int destinationCordX = REMBOURRAGE
                + (int) ((destination.getX() - minX) / diffX * (hauteur - 2 * REMBOURRAGE));
                int destinationCordY = REMBOURRAGE
                + (int) ((destination.getY() - minY) / diffY * (largeur - 2 * REMBOURRAGE));
                
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
                
                g2d.drawLine(origineCordX, origineCordY, destinationCordX, destinationCordY);   
            }
        }


        if (!listeIntersection.isEmpty()) {
            ArrayList<Point2D> points = new ArrayList<>();
            for (Intersection intersection : listeIntersection) {
                Point2D point = convertirLatLong(intersection);
                points.add(point);
            }

            g2d.setColor(couleurIntersection);

            for (Point2D point : points) {
                int coordX = REMBOURRAGE + (int) ((point.getX() - minX) / diffX * (hauteur - 2 * REMBOURRAGE));
                int coordY = REMBOURRAGE + (int) ((point.getY() - minY) / diffY * (largeur - 2 * REMBOURRAGE));

                g2d.fillOval(coordX - DIAMETRE_INTERSECTION / 2, coordY - DIAMETRE_INTERSECTION / 2,
                        DIAMETRE_INTERSECTION, DIAMETRE_INTERSECTION);
            }
        }


        if (entrepot.obtenirId() != null) {
            System.out.println(entrepot.toString());
            Point2D cordEntrepot = convertirLatLong(entrepot);
            int entrCordX = REMBOURRAGE + (int) ((cordEntrepot.getX() - minX) / diffX * (hauteur - 2 * REMBOURRAGE));
            int entrCordY = REMBOURRAGE + (int) ((cordEntrepot.getY() - minY) / diffY * (largeur - 2 * REMBOURRAGE));

            g2d.setColor(couleurEntrepot);
            g2d.fillOval(entrCordX - DIAMETRE_ENTREPOT / 2, entrCordY - DIAMETRE_ENTREPOT / 2,
                    DIAMETRE_ENTREPOT, DIAMETRE_ENTREPOT);
        }
    

        for (Livreur livr : listeLivreur) {
            for (Livraison s : livr.obtenirLivraisons()) {
                Point2D cordLivr = convertirLatLong(s.obtenirLieu());

                int livrCordX = REMBOURRAGE + (int) ((cordLivr.getX() - minX) / diffX * (hauteur - 2 * REMBOURRAGE));
                int livrCordY = REMBOURRAGE + (int) ((cordLivr.getY() - minY) / diffY * (largeur - 2 * REMBOURRAGE));
                System.out.println("heure de livaison: "+s.obtenirPlageHoraire() + "h");
                g2d.setColor(couleurIntersection);
                g2d.fillOval(livrCordX - DIAMETRE_DEST_LIVRIAISON / 2, livrCordY - DIAMETRE_DEST_LIVRIAISON / 2,
                        DIAMETRE_DEST_LIVRIAISON, DIAMETRE_DEST_LIVRIAISON);
            }
        }


        if (choixIntersection.obtenirId() != null) {
            Point2D cordChoixIntersection = convertirLatLong(choixIntersection);

            int cordChoixIntersectionX = REMBOURRAGE + (int) ((cordChoixIntersection.getX() - minX) / diffX * (hauteur - 2 * REMBOURRAGE));
            int cordChoixIntersectionY = REMBOURRAGE + (int) ((cordChoixIntersection.getY() - minY) / diffY * (largeur - 2 * REMBOURRAGE));

            System.out.println("cord = " + cordChoixIntersectionX + " " + cordChoixIntersectionY);
            //g2d.setColor(couleurChoixIntersection);
            g2d.fillOval(cordChoixIntersectionX-DIAMETRE_CHOIX_INTERSECTION/2, cordChoixIntersectionY-DIAMETRE_CHOIX_INTERSECTION/2, DIAMETRE_CHOIX_INTERSECTION, DIAMETRE_CHOIX_INTERSECTION);
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
        double x = (longitude + 180) * (largeur / 360);

        // convertir de degré en radian
        double latRad = latitude * Math.PI / 180;

        // obtenir y-coordonnée
        double mercN = Math.log(Math.tan(Math.PI / 4 + latRad / 2));
        double y = (largeur / 2.0) - (hauteur * mercN / (2.0 * Math.PI));

        return (new Point2D.Double(x, y));
    }
}
