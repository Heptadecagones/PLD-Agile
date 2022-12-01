package model;

import java.util.Observable;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import model.algo.Dijkstra.DijkstraAlgo;

public class Plan extends Observable {
    private Intersection entrepot;
    private ArrayList<Intersection> listeIntersection;
    private ArrayList<Segment> listeSegment;
    private long nombreIntersection;
    private long nombreSegment;
    private ArrayList<Livreur> listeLivreur;

    // AJOUT DUNE LIVRAISON, METHODE APPELEE PAR LE CONTROLLEUR
    public void nouvelleLivraison(String horaire, Intersection intersection, String numLivreur) {
        Livraison nouvelleLivraison=new Livraison(Integer.parseInt(horaire), intersection, this.listeLivreur.get(Integer.parseInt(numLivreur)));
        this.listeLivreur.get(Integer.parseInt(numLivreur)).obtenirLivraisons().add(nouvelleLivraison);
        System.out.println("Livreur:"+this.listeLivreur.get(Integer.parseInt(numLivreur)).toString());
        DijkstraAlgo dijal = new DijkstraAlgo(this, this.listeLivreur.get(Integer.parseInt(numLivreur)));
        Tournee t=new Tournee();
        try{
         t= new Tournee(dijal.calculerTournee());
         System.out.println("test\n:"+t.toString());
        }
        catch (CloneNotSupportedException cnse) {
            cnse.printStackTrace();
        }
        this.listeLivreur.get(Integer.parseInt(numLivreur)).obtenirTournee().modifierListeSegment(t.obtenirListeSegment());
        this.setChanged();
        this.notifyObservers();
    }

    public void chargerXML(String nomFichier) {
        try {

            File file = new File(nomFichier);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(file);
            document.getDocumentElement().normalize();

            NodeList listeInter = document.getElementsByTagName("intersection");
            this.nombreIntersection = listeInter.getLength();
            this.listeIntersection = new ArrayList<Intersection>();

            for (int i = 0; i < this.nombreIntersection; i++) {
                Node nNode = listeInter.item(i);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String tempId = eElement.getAttribute("id");
                    double tempLong = Double.parseDouble(eElement.getAttribute("longitude"));
                    double tempLat = Double.parseDouble(eElement.getAttribute("latitude"));
                    Intersection tempInter = new Intersection(tempId, tempLong, tempLat);
                    this.listeIntersection.add(tempInter);
                }
            }

            NodeList listeSeg = document.getElementsByTagName("segment");
            this.nombreSegment = listeSeg.getLength();
            this.listeSegment = new ArrayList<Segment>();

            for (int i = 0; i < this.nombreSegment; i++) {
                Node nNode = listeSeg.item(i);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String tempNom = eElement.getAttribute("name");
                    String tempOrigineId = eElement.getAttribute("origin");
                    String tempDestId = eElement.getAttribute("destination");
                    double tempLongueur = Double.parseDouble(eElement.getAttribute("length"));
                    Intersection tempOrigine = null;
                    Intersection tempDest = null;

                    int compte = 0;
                    for (int j = 0; j < this.listeIntersection.size(); j++) {
                        if (this.listeIntersection.get(j).obtenirId().equals(tempOrigineId)) {
                            tempOrigine = this.listeIntersection.get(j);
                            compte++;
                        }

                        if (this.listeIntersection.get(j).obtenirId().equals(tempDestId)) {
                            tempDest = this.listeIntersection.get(j);
                            compte++;
                        }

                        if (compte == 2)
                            break;
                    }
                    Segment tempSegment = new Segment(tempNom, tempOrigine, tempDest, tempLongueur);
                    tempOrigine.ajouterSegment(tempSegment);
                    this.listeSegment.add(tempSegment);
                }
            }

            NodeList listeEntrepot = document.getElementsByTagName("warehouse");
            Node nNode = listeEntrepot.item(0);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                String tempEntrepotId = eElement.getAttribute("address");
                this.entrepot = null;

                for (int j = 0; j < this.listeIntersection.size(); j++) {
                    if (this.listeIntersection.get(j).obtenirId().equals(tempEntrepotId)) {
                        this.entrepot = this.listeIntersection.get(j);
                        break;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        } catch (SAXException e) {
            System.out.println(e);
        } catch (ParserConfigurationException e) {
            System.out.println(e);
        }
        this.setChanged();
        this.notifyObservers();
        // System.out.println(this.toString());
    }

    public Plan() {
        this.listeLivreur = new ArrayList<Livreur>();
        this.listeLivreur.add(new Livreur(0));
        this.listeLivreur.add(new Livreur(1));
        this.listeLivreur.add(new Livreur(2));
        this.listeLivreur.add(new Livreur(3));
    }

    public Intersection obtenirEntrepot() {
        return entrepot;
    }

    public long obtenirNombreIntersection() {
        return nombreIntersection;
    }

    public long obtenirNombreSegment() {
        return nombreSegment;
    }

    public ArrayList<Intersection> obtenirListeIntersection() {
        return listeIntersection;
    }

    public ArrayList<Segment> obtenirListeSegment() {
        return listeSegment;
    }

    public String toString() {
        String description = "Entrepot\n";
        description += this.entrepot.toString();
        description += "\nListe des Livraisons :\n";
        description += "\nNombre d'intserctions : " + this.nombreIntersection;
        description += "\nNombre de segments : " + this.nombreSegment;
        description += "\nListe des intersections :\n";
        for (int i = 0; i < this.listeIntersection.size(); i++) {
            description += this.listeIntersection.get(i).toString();
            description += "\n";
        }
        description += "Liste des segments :\n";
        for (int i = 0; i < this.listeSegment.size(); i++) {
            description += this.listeSegment.get(i).toString();
            description += "\n";
        }
        return description;
    }

    public ArrayList<Livreur> obtenirListeLivreur() {
        return this.listeLivreur;
    }
}
