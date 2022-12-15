package com.hexa17.pldagile.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.hexa17.pldagile.model.algo.Noeud;

/**
 * <p>PlanUsine classe.</p>
 *
 * @author omi
 * @version $Id: $Id
 */
public class PlanUsine {

    private ArrayList<Intersection> listeIntersection; //Liste d'intersection générée par le fichier XML
    private ArrayList<Segment> listeSegment; //Liste de segments générée par le fichier XML
    private Map<Noeud, ArrayList<Segment>> liensEntreNoeuds; //Map regroupant les segments par Intersection d'origine
    private Intersection entrepot; //Entrepôt généré par le fichier XML

    /**
     * <p>Modificateur Entrepot.</p>
     *
     * @param entrepot un {@link com.hexa17.pldagile.model.Intersection} objet
     */
    public void modifierEntrepot(Intersection entrepot) {
        this.entrepot = entrepot;
    }

    /**
     * <p>Modificateur ListeIntersection.</p>
     *
     * @param listeIntersection un {@link java.util.ArrayList} objet
     */
    public void modifierListeIntersection(ArrayList<Intersection> listeIntersection) {
        this.listeIntersection = listeIntersection;
    }

    /**
     * <p>Construction du plan.</p>
     *
     * @return un {@link com.hexa17.pldagile.model.Plan} objet
     */
    public Plan construirePlan() {
        return new Plan(listeIntersection, listeSegment, entrepot, liensEntreNoeuds);
    }

    /**
     * <p>Modificateur ListeSegment.</p>
     *
     * @param listeSegment un {@link java.util.ArrayList} objet
     */
    public void modifierListeSegment(ArrayList<Segment> listeSegment) {
        this.listeSegment = listeSegment;
    }

    /**
     * Init les données / Reinit les données de l'ancienne carte
     * Utilisée quand l'utilisateur charge une nouvelle carte
     */
    void init() {
        entrepot = new Intersection();
        listeIntersection = new ArrayList<Intersection>();
        listeSegment = new ArrayList<Segment>();
        liensEntreNoeuds = new HashMap<Noeud, ArrayList<Segment>>();
    }

    /**
     * <p>chargerXML.</p>
     *
     * @param nomFichier un {@link java.lang.String} objet
     * @return un {@link java.util.ArrayList} objet
     */
    //Retour d'une liste de livreur dans le cas où c'est un fichier sauvegardé,
    //la liste de livreur du planLivraison doit être celle du fichier.
    public ArrayList<Livreur> chargerXML(String nomFichier, boolean fichierEnregistre) {

        ArrayList<Livreur> listeLivreur = new ArrayList<Livreur>();
        try {

            //Ouverture et lecture du fichier
            File file = new File(nomFichier);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(file);
            document.getDocumentElement().normalize();

            //Récupérer toutes les intersections du fichier XML
            NodeList listeInter = document.getElementsByTagName("intersection");
            this.listeIntersection = new ArrayList<Intersection>();

            //Création d'un objet Intersection pour chaque intersection du fichier XML
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

            //Récupérer tous les segments du fichier XML
            NodeList listeSeg = document.getElementsByTagName("segment");
            this.listeSegment = new ArrayList<Segment>();
            this.liensEntreNoeuds = new HashMap<Noeud, ArrayList<Segment>>();

            //Création d'un objet Segment pour chaque segment du fichier XML
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
                    this.listeSegment.add(tempSegment);

                    //On ajoute le segment dans le map avec la clé de son Intersection d'origine
                    if (!liensEntreNoeuds.containsKey(tempOrigine)) {
                        liensEntreNoeuds.put(tempOrigine, new ArrayList<Segment>());
                    }
                    liensEntreNoeuds.get(tempOrigine).add(tempSegment);
                }
            }

            //Création de l'entrepôt
            NodeList listeEntrepot = document.getElementsByTagName("warehouse");
            Node nNode = listeEntrepot.item(0);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                String tempEntrepotId = eElement.getAttribute("address");

                //Lien entre l'entrpôt et son intersection associée
                for (int j = 0; j < this.listeIntersection.size(); j++) {
                    if (this.listeIntersection.get(j).obtenirId().equals(tempEntrepotId)) {
                        this.entrepot = this.listeIntersection.get(j);
                        break;
                    }
                }
            }
            //Dans le cas où il s'agit dun fichier enregistré
            if (fichierEnregistre) {
                //Récupération de tous les livreurs du fichier
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

                //Récupération de toutes les livraisons du fichier
                NodeList listeliv = document.getElementsByTagName("listelivraison");
                int nombreLivraison = listeliv.getLength();
                ArrayList<Livraison>[] listeLivraison = new ArrayList[nombreLivreur];
                for (int i = 0; i < nombreLivreur; ++i) {
                    listeLivraison[i] = new ArrayList<Livraison>();
                }

                //Affecter chaque livraison au bon livreur
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

                //Récupérer la tournée de chaque livreur
                //Récupérer chaque segments issus d'une même tournée et l'associer à la tourner du livreur correspondant
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
     * <p>sauvegarder dans un fichier l'état du plan.</p>
     * 
     * @param nomFichier
     * @param listeLivreur
     * @param plan
     */
    public void sauvegarderPlan(String nomFichier, ArrayList<Livreur> listeLivreur, Plan plan) {

        String ajout_parametre;

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();

            // élément de racine
            Document doc = docBuilder.newDocument();
            Element racine = doc.createElement("map");

            // l'élément contact
            String help;

            // Ecriture des attributs des livreurs dans le fichier
            for (Livreur livr : listeLivreur) {
                Element livreur = doc.createElement("livreur");
                racine.appendChild(livreur);

                help = String.valueOf(livr.obtenirId());
                livreur.setAttribute("id", help);

                help = String.valueOf(livr.estDisponible());
                livreur.setAttribute("disponibilite", help);

                help = String.valueOf(livr.obtenirNom());
                livreur.setAttribute("nom", help);
            }

            // Ecriture de l'id de l'entrepôt dans le fichier
            Element warehouse = doc.createElement("warehouse");
            warehouse.setAttribute("address", plan.obtenirEntrepot().obtenirId());
            racine.appendChild(warehouse);

            // Ecriture des valeurs des attributs des interceptions dans le fichier
            if (!plan.obtenirListeIntersection().isEmpty()) {
                for (Intersection aff_intersection : plan.obtenirListeIntersection()) {

                    Element intersection = doc.createElement("intersection");

                    racine.appendChild(intersection);
                    ajout_parametre = aff_intersection.obtenirId();

                    intersection.setAttribute("id", ajout_parametre);
                    ajout_parametre = String.valueOf(aff_intersection.obtenirLatitude());
                    intersection.setAttribute("latitude", ajout_parametre);
                    ajout_parametre = String.valueOf(aff_intersection.obtenirLongitude());
                    intersection.setAttribute("longitude", ajout_parametre);
                }
            }

            // Ecriture des valeurs des attributs des segments dans le fichier
            if (!plan.obtenirListeSegment().isEmpty()) {
                for (Segment aff_segment : plan.obtenirListeSegment()) {

                    Element segment = doc.createElement("segment");
                    racine.appendChild(segment);

                    ajout_parametre = String.valueOf(aff_segment.obtenirDestination().obtenirId());
                    segment.setAttribute("destination", ajout_parametre);

                    ajout_parametre = String.valueOf(aff_segment.obtenirLongueur());
                    segment.setAttribute("length", ajout_parametre);

                    ajout_parametre = String.valueOf(aff_segment.obtenirNom());
                    segment.setAttribute("name", ajout_parametre);

                    ajout_parametre = String.valueOf(aff_segment.obtenirOrigine().obtenirId());
                    segment.setAttribute("origin", ajout_parametre);
                }
            }

            // Ecriture des tournées de chaque livreur dans le fichier
            for (Livreur livr : listeLivreur) {

                for (Segment segment_livraison : livr.obtenirTournee().obtenirListeSegment()) {

                    // Ecriture d'un segment de la tournée
                    Element segment_tournee = doc.createElement("segment_tournee");
                    racine.appendChild(segment_tournee);

                    ajout_parametre = String.valueOf(livr.obtenirId());
                    segment_tournee.setAttribute("id_livreur", ajout_parametre);

                    ajout_parametre = String.valueOf(segment_livraison.obtenirDestination().obtenirId());
                    segment_tournee.setAttribute("destination", ajout_parametre);

                    ajout_parametre = String.valueOf(segment_livraison.obtenirOrigine().obtenirId());
                    segment_tournee.setAttribute("origin", ajout_parametre);
                }

                // Ecriture de la liste de livraison pour chaque livreur dans le fichier
                for (Livraison liv_livraison : livr.obtenirLivraisons()) {

                    Element listelivraison = doc.createElement("listelivraison");
                    racine.appendChild(listelivraison);

                    ajout_parametre = String.valueOf(livr.obtenirId());
                    listelivraison.setAttribute("id_livreur", ajout_parametre);

                    ajout_parametre = String.valueOf(liv_livraison.obtenirHoraireLivraison());
                    listelivraison.setAttribute("plage_horaire", ajout_parametre);

                    ajout_parametre = String.valueOf(liv_livraison.obtenirHeureLivraison());
                    listelivraison.setAttribute("heure_livraison", ajout_parametre);

                    ajout_parametre = String.valueOf(liv_livraison.obtenirLieu().obtenirId());
                    listelivraison.setAttribute("id_intersection", ajout_parametre);
                }
            }

            doc.appendChild(racine);

            // Ecriture du contenu dans le fichier XML

            // Création du chemin où stocker le fichier
            String cheminXML = File.separator + "src" + File.separator + "main" + File.separator + "java";

            // Création du fichier
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult resultat = new StreamResult(
                    new File((System.getProperty("user.dir") + cheminXML + File.separator + nomFichier + ".xml")));

            transformer.transform(source, resultat);

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }

    /**
     * <p>Accesseur Entrepot.</p>
     *
     * @return un {@link com.hexa17.pldagile.model.Intersection} objet
     */
    public Intersection obtenirEntrepot() {
        return entrepot;
    }

    /**
     * <p>Accesseur ListeIntersection.</p>
     *
     * @return un {@link java.util.ArrayList} objet
     */
    public ArrayList<Intersection> obtenirListeIntersection() {
        return listeIntersection;
    }

    /**
     * <p>Accesseur ListeSegment.</p>
     *
     * @return un {@link java.util.ArrayList} objet
     */
    public ArrayList<Segment> obtenirListeSegment() {
        return listeSegment;
    }
}
