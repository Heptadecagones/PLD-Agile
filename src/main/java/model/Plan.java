package model;

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

public class Plan {
    private Intersection entrepot;

    private ArrayList<Intersection> listeIntersection;
    private ArrayList<Segment> listeSegment;
    private long nombreIntersection;
    private long nombreSegment;

    /**
     * Init les données / Reinit les données de l'ancienne carte
     * Utilisée quand l'utilisateur charge une nouvelle carte
     */
    public void init() {
        entrepot = new Intersection();
        listeIntersection = new ArrayList<Intersection>();
        listeSegment = new ArrayList<Segment>();
    }
    
    //chargement d'un plan( avec segments,intersections,entrepot) à partir d'un fichier XML
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

}
