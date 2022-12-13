package com.hexa17.pldagile.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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

import com.hexa17.pldagile.model.algo.Noeud;

/**
 * <p>PlanUsine class.</p>
 *
 * @author omi
 * @version $Id: $Id
 */
public class PlanUsine {

    private ArrayList<Intersection> listeIntersection;
    private ArrayList<Segment> listeSegment;
    private Map<Noeud, ArrayList<Segment>> liensEntreNoeuds;
    private Intersection entrepot;

    /**
     * <p>modifierEntrepot.</p>
     *
     * @param entrepot a {@link com.hexa17.pldagile.model.Intersection} object
     */
    public void modifierEntrepot(Intersection entrepot) {
        this.entrepot = entrepot;
    }

    /**
     * <p>modifierListeIntersection.</p>
     *
     * @param listeIntersection a {@link java.util.ArrayList} object
     */
    public void modifierListeIntersection(ArrayList<Intersection> listeIntersection) {
        this.listeIntersection = listeIntersection;
    }

    /**
     * <p>construirePlan.</p>
     *
     * @return a {@link com.hexa17.pldagile.model.Plan} object
     */
    public Plan construirePlan() {
        return new Plan(listeIntersection, listeSegment, entrepot, liensEntreNoeuds);
    }

    /**
     * <p>modifierListeSegment.</p>
     *
     * @param listeSegment a {@link java.util.ArrayList} object
     */
    public void modifierListeSegment(ArrayList<Segment> listeSegment) {
        this.listeSegment = listeSegment;
    }

    // FIXME je sais pas ce que ça fout là
    /**
     * Init les données / Reinit les données de l'ancienne carte
     * Utilisée quand l'utilisateur charge une nouvelle carte
     */
    public void init() {
        entrepot = new Intersection();
        listeIntersection = new ArrayList<Intersection>();
        listeSegment = new ArrayList<Segment>();
        liensEntreNoeuds = new HashMap<Noeud, ArrayList<Segment>>();
    }

    // chargement d'un plan( avec segments,intersections,entrepot) à partir d'un
    // fichier XML
    /**
     * <p>chargerXML.</p>
     *
     * @param nomFichier a {@link java.lang.String} object
     */
    public void chargerXML(String nomFichier) {
        try {

            File file = new File(nomFichier);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(file);
            document.getDocumentElement().normalize();

            NodeList listeInter = document.getElementsByTagName("intersection");
            this.listeIntersection = new ArrayList<Intersection>();

            for (int i = 0; i < listeInter.getLength(); i++) {
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
            this.listeSegment = new ArrayList<Segment>();
            this.liensEntreNoeuds = new HashMap<Noeud, ArrayList<Segment>>();

            for (int i = 0; i < listeSeg.getLength(); i++) {
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
                    //tempOrigine.obtenirListeSegmentOrigine().add(tempSegment);
                    this.listeSegment.add(tempSegment);

                    if (!liensEntreNoeuds.containsKey(tempOrigine)) {
                        liensEntreNoeuds.put(tempOrigine, new ArrayList<Segment>());
                    }
                    liensEntreNoeuds.get(tempOrigine).add(tempSegment);
                }
            }

            NodeList listeEntrepot = document.getElementsByTagName("warehouse");
            Node nNode = listeEntrepot.item(0);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                String tempEntrepotId = eElement.getAttribute("address");

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

    /**
     * <p>chargerLivraisonXML.</p>
     *
     * @param nomFichier a {@link java.lang.String} object
     * @return a {@link java.util.ArrayList} object
     */
    public ArrayList<Livreur> chargerLivraisonXML(String nomFichier) {

        ArrayList<Livreur> listeLivreur = new ArrayList<Livreur>();
        try {

            File file = new File(nomFichier);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(file);
            document.getDocumentElement().normalize();

            NodeList listeInter = document.getElementsByTagName("intersection");
            this.listeIntersection = new ArrayList<Intersection>();

            for (int i = 0; i < listeInter.getLength(); i++) {
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
            this.listeSegment = new ArrayList<Segment>();
            this.liensEntreNoeuds = new HashMap<Noeud, ArrayList<Segment>>();

            for (int i = 0; i < listeSeg.getLength(); i++) {
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
                    //tempOrigine.obtenirListeSegmentOrigine().add(tempSegment);
                    this.listeSegment.add(tempSegment);

                    if (!liensEntreNoeuds.containsKey(tempOrigine)) {
                        liensEntreNoeuds.put(tempOrigine, new ArrayList<Segment>());
                    }
                    liensEntreNoeuds.get(tempOrigine).add(tempSegment);
                }
            }

            NodeList listeEntrepot = document.getElementsByTagName("warehouse");
            Node nNode = listeEntrepot.item(0);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                String tempEntrepotId = eElement.getAttribute("address");

                for (int j = 0; j < this.listeIntersection.size(); j++) {
                    if (this.listeIntersection.get(j).obtenirId().equals(tempEntrepotId)) {
                        this.entrepot = this.listeIntersection.get(j);
                        break;
                    }
                }
            }

            NodeList nodeListLivreurs = document.getElementsByTagName("livreur");
            int nombreLivreur = nodeListLivreurs.getLength();
            for (int i = 0; i < nombreLivreur; ++i) {
                Node livreurNode = nodeListLivreurs.item(i);

                Element elementLivreur = (Element) livreurNode;

                int id_livreur = Integer.valueOf(elementLivreur.getAttribute("id"));
                String nom = elementLivreur.getAttribute("nom");
                boolean disponibilite = Boolean.valueOf(elementLivreur.getAttribute("disponibilite"));
                
                listeLivreur.add(new Livreur(id_livreur, nom, disponibilite));
            }

            // Ajoute les livraisons
            NodeList listeliv = document.getElementsByTagName("listelivraison");
            int nombreLivraison = listeliv.getLength();
            ArrayList<Livraison>[] listeLivraison = new ArrayList[nombreLivreur];
            for (int i = 0; i < nombreLivreur; ++i) {
                listeLivraison[i] = new ArrayList<Livraison>();
            }

            for (int i = 0; i < nombreLivraison; i++) {
                Node livNode = listeliv.item(i);

                if (livNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) livNode;
                    String id_intersection = eElement.getAttribute("id_intersection");
                    int id_livreur = Integer.valueOf(eElement.getAttribute("id_livreur"));
                    int plage_horaire = Integer.valueOf(eElement.getAttribute("plage_horaire"));
                    double heure = Double.valueOf(eElement.getAttribute("heure_livraison"));
                    for (Intersection intersection : listeIntersection) {
                        if (intersection.obtenirId().equals(id_intersection)){
                            listeLivraison[id_livreur].add(new Livraison(heure,plage_horaire, intersection, listeLivreur.get(id_livreur)));
                            break;
                        }
                    }
                }
            }
            for (int i = 0; i < nombreLivreur; ++i) {
                listeLivreur.get(i).modifierLivraisons(listeLivraison[i]);
                listeLivreur.get(i).obtenirTournee().modifierListeLivraison(listeLivraison[i]);
            }

            // Ajoute les tournées
            NodeList segmentTournee = document.getElementsByTagName("segment_tournee");
            int nombreSegmentTournee = segmentTournee.getLength();
            ArrayList<Segment>[] listeSegmentTournee = new ArrayList[nombreLivreur];
            for (int i = 0; i < nombreLivreur; ++i) {
                listeSegmentTournee[i] = new ArrayList<Segment>();
            }

            for (int i = 0; i < nombreSegmentTournee; i++) {
                Node livNode = segmentTournee.item(i);
                if (livNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) livNode;
                    String id_destination = (eElement.getAttribute("destination"));
                    int id_livreur = Integer.valueOf(eElement.getAttribute("id_livreur"));
                    String id_origin = (eElement.getAttribute("origin"));

                    for (Segment segment : listeSegment) {
                        if (segment.obtenirDestination().obtenirId().equals(id_destination) && segment.obtenirOrigine().obtenirId().equals(id_origin)) {
                            listeSegmentTournee[id_livreur].add(segment);
                        }
                    }
                }
            }
            for (int i = 0; i < nombreLivreur; ++i) {
                listeLivreur.get(i).obtenirTournee().modifierListeSegment(listeSegmentTournee[i]);
            }

        } catch (IOException e) {
            System.out.println(e);
        } catch (SAXException e) {
            System.out.println(e);
        } catch (ParserConfigurationException e) {
            System.out.println(e);
        }
        return listeLivreur;
    }

    /**
     * <p>obtenirEntrepot.</p>
     *
     * @return a {@link com.hexa17.pldagile.model.Intersection} object
     */
    public Intersection obtenirEntrepot() {
        return entrepot;
    }

    /**
     * <p>obtenirListeIntersection.</p>
     *
     * @return a {@link java.util.ArrayList} object
     */
    public ArrayList<Intersection> obtenirListeIntersection() {
        return listeIntersection;
    }

    /**
     * <p>obtenirListeSegment.</p>
     *
     * @return a {@link java.util.ArrayList} object
     */
    public ArrayList<Segment> obtenirListeSegment() {
        return listeSegment;
    }
}
