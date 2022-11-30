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

public class Plan extends Observable
{
    private Intersection entrepot;
    private ArrayList<Intersection> listeIntersection;
    private ArrayList<Segment> listeSegment;
    private long nombreIntersection;
    private long nombreSegment;
    private ArrayList<Tournee> listeTournee;
    private ArrayList<Livraison> listeLivraison;
    private ArrayList<Livreur> listeLivreur;
    //AJOUT DUNE LIVRAISON, METHODE APPELEE PAR LE CONTROLLEUR
    public void nouvelleLivraison(String horaire,Intersection intersection,String numLivreur) {
        this.listeLivraison.add(new Livraison(Integer.parseInt(horaire),intersection,new Livreur(Integer.parseInt(numLivreur))));
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
                    Intersection tempInter = new Intersection(tempId,tempLong, tempLat);
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
                    for(int j = 0 ; j < this.listeIntersection.size(); j++) {
                        if(this.listeIntersection.get(j).obtenirId().equals(tempOrigineId)) {
                            tempOrigine = this.listeIntersection.get(j);
                            compte++;
                        }

                        if(this.listeIntersection.get(j).obtenirId().equals(tempDestId)) {
                            tempDest = this.listeIntersection.get(j);
                            compte++;
                        }

                        if(compte == 2) break;
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

                for(int j = 0 ; j < this.listeIntersection.size(); j++) {
                    if(this.listeIntersection.get(j).obtenirId().equals(tempEntrepotId)) {
                        this.entrepot = this.listeIntersection.get(j);
                        break;
                    }
                }
            }
            this.listeTournee = new ArrayList<Tournee>();
            this.listeLivraison = new ArrayList<Livraison>();
        }
        catch(IOException e) {
            System.out.println(e);
        }
        catch(SAXException e) {
            System.out.println(e);
        }
        catch(ParserConfigurationException e) {
            System.out.println(e);
        } 
        this.setChanged();
        this.notifyObservers();
        // System.out.println(this.toString());
    }

    public Plan()
    {
    }

    public Intersection obtenirEntrepot()
    {
        return entrepot;
    }

    public long obtenirNombreIntersection()
    {
        return nombreIntersection;
    }

    public long obtenirNombreSegment()
    {
        return nombreSegment;
    }

    public ArrayList<Intersection> obtenirListeIntersection() {
        return listeIntersection;
    }

    public ArrayList<Segment> obtenirListeSegment() {
        return listeSegment;
    }

    public ArrayList<Tournee> obtenirListeTournee() {
        return listeTournee;
    }

    public ArrayList<Livraison> obtenirListeLivraison() {
        return listeLivraison;
    }

    public void modifierListeTournee(ArrayList<Tournee> listeTournee) {
        this.listeTournee = listeTournee;
    }

    public void modifierListeLivraison(ArrayList<Livraison> listeLivraison) {
        this.listeLivraison = listeLivraison;
    }

    public void modifierEntrepot(Intersection entrepot) {
        this.entrepot = entrepot;
    }

    public void modifierListeIntersection(ArrayList<Intersection> listeIntersection) {
        this.listeIntersection = listeIntersection;
    }

    public void modifierListeSegment(ArrayList<Segment> listeSegment) {
        this.listeSegment = listeSegment;
    }

    public void modifierNombreIntersection(long nombreIntersection) {
        this.nombreIntersection = nombreIntersection;
    }

    public void modifierNombreSegment(long nombreSegment) {
        this.nombreSegment = nombreSegment;
    }

    public String toString() {
        String description = "Entrepot\n";
        description += this.entrepot.toString();
        description +="\nNombre de Livraisons : " + this.listeLivraison.size()+"\n";
        description+="\nListe des Livraisons :\n";
        for(int i = 0 ; i < this.listeLivraison.size(); i++) {
            description += this.listeLivraison.get(i).toString();
            description += "\n";
        }
        description +="\nNombre d'intserctions : " + this.nombreIntersection;
        description +="\nNombre de segments : " + this.nombreSegment;
        description +="\nListe des intersections :\n";
        for(int i = 0 ; i < this.listeIntersection.size(); i++) {
            description += this.listeIntersection.get(i).toString();
            description += "\n";
        }
        description +="Liste des segments :\n";
        for(int i = 0 ; i < this.listeSegment.size(); i++) {
            description += this.listeSegment.get(i).toString();
            description += "\n";
        }
        return description;
    }

    public void ajouterLivraison(Livraison liv)
    {
        this.listeLivraison.add(liv);
    }

    public void ajouterTournee(Livraison tournee)
    {
        this.listeLivraison.add(tournee);
    }
    public ArrayList<Livreur> obtenirListeLivreur() {
        return listeLivreur;
    }
    public void modifierListeLivreur(ArrayList<Livreur> listeLivreur) {
        this.listeLivreur = listeLivreur;
    }

    
}
