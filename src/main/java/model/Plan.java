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

    // String valeur;

    // public String getValeur() {
    //     return valeur;
    // }

    // public void setValeur(String valeur) {
    //     this.valeur = valeur;
    //     System.out.println("nouvelle valeur ="+valeur);
    //     this.setChanged();
    //     this.notifyObservers();
    // }

    private String entrepot;
    private ArrayList<Intersection> listeIntersection;
    private ArrayList<Segment> listeSegment;
    private double nombreIntersection;
    private double nombreSegment;

    Plan(String nomFichier) {
        try {

            File file = new File(nomFichier);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(file);
            document.getDocumentElement().normalize();

            NodeList interList = document.getElementsByTagName("intersection");
            this.nombreIntersection = interList.getLength();
            this.intersectionList = new ArrayList();

            for (int i = 0; i < this.intersectionCount; i++) {
                Node nNode = interList.item(i);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String tempId = eElement.getAttribute("id");
                    double tempLong = Double.parseDouble(eElement.getAttribute("longitude"));
                    double tempLat = Double.parseDouble(eElement.getAttribute("latitude"));
                    Intersection tempInter = new Intersection(tempId,tempLong, tempLat);
                    this.intersectionList.add(tempInter);
                }
            }

            NodeList rList = document.getElementsByTagName("segment");
            this.roadCount = rList.getLength();
            this.roadList = new ArrayList();

            for (int i = 0; i < this.roadCount; i++) {
                Node nNode = rList.item(i);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String tempName = eElement.getAttribute("name");
                    String tempOrigin = eElement.getAttribute("origin");
                    String tempDest = eElement.getAttribute("destination");
                    double tempLength = Double.parseDouble(eElement.getAttribute("length"));
                    Road tempRoad = new Road(tempName, tempOrigin, tempDest, tempLength);
                    this.roadList.add(tempRoad);
                }
            }

            NodeList wList = document.getElementsByTagName("warehouse");
            Node nNode = wList.item(0);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                this.warehouse = eElement.getAttribute("address");
            }

        }
        catch(IOException e) {
            System.out.println(e);
        } 
    }
}
