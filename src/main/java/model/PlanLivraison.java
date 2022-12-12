package model;

import java.util.Observable;
import java.util.WeakHashMap;
import java.util.ArrayList;
import model.algo.FacadeAlgoTournee;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.transform.*;

import java.io.IOException;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;



/**
 *
 * @author Henri
 *         Voir diagramme des classes
 *         Classe avec un Plan et une liste de Livreur, permet de faire le lien
 *         entre le plan et les livraisons
 */
public class PlanLivraison extends Observable {

    private ArrayList<Livreur> listeLivreur;
    private Plan plan;

    // AJOUT DUNE LIVRAISON, METHODE APPELEE PAR LE CONTROLLEUR
    public void nouvelleLivraison(String horaire, Intersection intersection, String numLivreur) {
        Livraison nouvelleLivraison = new Livraison(Integer.parseInt(horaire), intersection,
                this.listeLivreur.get(Integer.parseInt(numLivreur)));
        this.listeLivreur.get(Integer.parseInt(numLivreur)).obtenirLivraisons().add(nouvelleLivraison);
        System.out.println("Livreur:" + this.listeLivreur.get(Integer.parseInt(numLivreur)).toString());
        // TODO: remplacer l'appel de DijkstraAlgo par un appel de FacadeAlgoTournee
        // DijkstraAlgo dijal = new DijkstraAlgo(this,
        // this.listeLivreur.get(Integer.parseInt(numLivreur)));
        Tournee t = new Tournee();
        try {
            t = new Tournee(FacadeAlgoTournee.calculerTournee(
                    plan, this.listeLivreur.get(Integer.parseInt(numLivreur))));
            System.out.println("test\n:" + t.toString());
        } catch (CloneNotSupportedException cnse) {
            cnse.printStackTrace();
        }
        this.listeLivreur.get(Integer.parseInt(numLivreur)).obtenirTournee()
                .modifierListeSegment(t.obtenirListeSegment());
        this.setChanged();
        this.notifyObservers();
    }

    public void ouvrirPlan(String XML) {
        this.plan.chargerXML(XML);
        this.setChanged();
        this.notifyObservers();
    }

    public PlanLivraison() {
        init();
    }

    /**
     * Init les données / Reinit les données de l'ancienne carte
     * Utilisée quand l'utilisateur charge une nouvelle carte
     */
    public void init() {
        this.plan = new Plan();
        this.plan.init();
        this.listeLivreur = new ArrayList<Livreur>();
        this.listeLivreur.add(new Livreur(0,"Jean Jean"));
        this.listeLivreur.add(new Livreur(1,"Hervé Lapin"));
        this.listeLivreur.add(new Livreur(2,"Jeanne-Annick Al-Pohou"));
        this.listeLivreur.add(new Livreur(3,"Bertrand Turpin"));
    }

    public ArrayList<Livreur> obtenirListeLivreur() {
        return this.listeLivreur;
    }

    public Plan obtenirPlan() {
        return this.plan;
    }
    public void sauvegarder(String nomFichier){

        String ajout_parametre;
     
        try {
 
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();
            
            // élément de racine
            Document doc = docBuilder.newDocument();
            Element racine = doc.createElement("map");

            // l'élément contact
            String help;
            
            for(Livreur livr : obtenirListeLivreur()){
                Element livreur = doc.createElement("livreur");
                racine.appendChild(livreur);

                help = String.valueOf(livr.obtenirId());
                livreur.setAttribute("id", help);

                help = String.valueOf(livr.estDisponible());
                livreur.setAttribute("disponibilite", help);

                help = String.valueOf(livr.obtenirNom());
                livreur.setAttribute("nom", help);
            }

            Element warehouse = doc.createElement("warehouse");
            warehouse.setAttribute("address", plan.obtenirEntrepot().obtenirId());
            racine.appendChild(warehouse);
            
          //  doc.setTextContent("\n");
            if (!plan.obtenirListeIntersection().isEmpty()) {
                for(Intersection aff_intersection : plan.obtenirListeIntersection()){
             
                // intersection
                
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
       
            if (!plan.obtenirListeSegment().isEmpty()) {
                for(Segment aff_segment : plan.obtenirListeSegment()){
                    //segment
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

            for(Livreur livr : obtenirListeLivreur()){

                for(Segment segment_livraison : livr.obtenirTournee().obtenirListeSegment()){
                    //segment
                    Element segment_tournee = doc.createElement("segment_tournee");
                    racine.appendChild(segment_tournee);

                    ajout_parametre = String.valueOf(livr.obtenirId());
                    segment_tournee.setAttribute("id_livreur", ajout_parametre);

                    ajout_parametre = String.valueOf(segment_livraison.obtenirDestination().obtenirId());
                    segment_tournee.setAttribute("destination", ajout_parametre);
                
                    ajout_parametre = String.valueOf(segment_livraison.obtenirOrigine().obtenirId());
                    segment_tournee.setAttribute("origin", ajout_parametre);
                }
                
                for(Livraison liv_livraison : livr.obtenirLivraisons()){
                    //segment
                    Element listelivraison = doc.createElement("listelivraison");
                    racine.appendChild(listelivraison);

                    ajout_parametre = String.valueOf(livr.obtenirId());
                    listelivraison.setAttribute("id_livreur", ajout_parametre);

                    ajout_parametre = String.valueOf(liv_livraison.obtenirPlageHoraire());
                    listelivraison.setAttribute("plage_horaire", ajout_parametre);

                    ajout_parametre = String.valueOf(liv_livraison.obtenirLieu().obtenirId());
                    listelivraison.setAttribute("id_intersection", ajout_parametre);
                }
            }

            doc.appendChild(racine);
        
            
            // write the content into xml file
            String cheminXML = File.separator + "src" + File.separator + "main" + File.separator + "java";
            //File repertoireProjet = new File(System.getProperty("user.dir") + cheminXML);
           

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult resultat = new StreamResult(new File((System.getProperty("user.dir") + cheminXML + File.separator + nomFichier + ".xml")));
            
            transformer.transform(source, resultat);
            
            System.out.println("Fichier sauvegardé avec succès!");
            
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }

    public void chargerLivraison(String nomFichier) {
        try {

            File file = new File(nomFichier);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(file);
            document.getDocumentElement().normalize();

            NodeList listeInter = document.getElementsByTagName("intersection");
            plan.modifierNombreIntersection(listeInter.getLength()); 
            plan.modifierListeIntersection(new ArrayList<Intersection>());

            for (int i = 0; i < plan.obtenirNombreIntersection(); i++) {
                Node nNode = listeInter.item(i);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String tempId = eElement.getAttribute("id");
                    double tempLong = Double.parseDouble(eElement.getAttribute("longitude"));
                    double tempLat = Double.parseDouble(eElement.getAttribute("latitude"));
                    Intersection tempInter = new Intersection(tempId, tempLong, tempLat);
                    plan.obtenirListeIntersection().add(tempInter);
                }
            }
    
            NodeList listeSeg = document.getElementsByTagName("segment");
            plan.modifierNombreSegment( listeSeg.getLength());
            plan.modifierListeSegment(new ArrayList<Segment>());

            for (int i = 0; i < plan.obtenirNombreSegment(); i++) {
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
                    for (int j = 0; j < plan.obtenirListeIntersection().size(); j++) {
                        if (plan.obtenirListeIntersection().get(j).obtenirId().equals(tempOrigineId)) {
                            tempOrigine = plan.obtenirListeIntersection().get(j);
                            compte++;
                        }

                        if (plan.obtenirListeIntersection().get(j).obtenirId().equals(tempDestId)) {
                            tempDest = plan.obtenirListeIntersection().get(j);
                            compte++;
                        }

                        if (compte == 2)
                            break;
                    }
                    Segment tempSegment = new Segment(tempNom, tempOrigine, tempDest, tempLongueur);
                    tempOrigine.ajouterSegment(tempSegment);
                    plan.obtenirListeSegment().add(tempSegment);
                }
            }
    
            NodeList listeEntrepot = document.getElementsByTagName("warehouse");
            Node nNode = listeEntrepot.item(0);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                String tempEntrepotId = eElement.getAttribute("address");
                plan.modifierEntrepot(null) ;

                for (int j = 0; j < plan.obtenirListeIntersection().size(); j++) {
                    if (plan.obtenirListeIntersection().get(j).obtenirId().equals(tempEntrepotId)) {
                        plan.modifierEntrepot(plan.obtenirListeIntersection().get(j));
                        break;
                    }
                }
            }

            NodeList nodeListLivreurs = document.getElementsByTagName("livreur");
            int nombreLivreur = nodeListLivreurs.getLength();
            listeLivreur = new ArrayList<Livreur>();
            for (int i = 0; i < nombreLivreur; ++i) {
                Node livreurNode = nodeListLivreurs.item(i);

                Element elementLivreur = (Element) livreurNode;

                int id_livreur = Integer.valueOf(elementLivreur.getAttribute("id"));
                String nom = elementLivreur.getAttribute("nom");
                boolean disponibilite = Boolean.valueOf(elementLivreur.getAttribute("disponibilite"));
                
                listeLivreur.add(new Livreur(id_livreur, nom, disponibilite));
            }

            System.out.println(listeLivreur.size());

            // Ajoute les livraisons
            NodeList listeliv = document.getElementsByTagName("listelivraison");
            int nombreLivraison = listeliv.getLength();
            //ArrayList<Livraison> listeLivraison = new ArrayList<Livraison>();

            for (int i = 0; i < nombreLivraison; i++) {
                Node livNode = listeliv.item(i);

                if (livNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) livNode;
                    String id_intersection =   eElement.getAttribute("id_intersection");
                    int id_livreur = Integer.valueOf(eElement.getAttribute("id_livreur"));
                    int plage_horaire = Integer.valueOf(eElement.getAttribute("plage_horaire"));

                    for (Intersection intersection : plan.obtenirListeIntersection()) {
                        if (intersection.obtenirId() == id_intersection){
                            listeLivreur.get(id_livreur).obtenirTournee(). obtenirListeLivraison().add(new Livraison(plage_horaire, intersection, listeLivreur.get(i)));
                            listeLivreur.get(id_livreur).obtenirLivraisons().add(new Livraison(plage_horaire, intersection, listeLivreur.get(i)));
                        }
                    }
                }
            }

            // Ajoute les tournées
            NodeList segmentTournee = document.getElementsByTagName("segment_tournee");
            int nombreSegmentTournee = segmentTournee.getLength();

            for (int i = 0; i < nombreSegmentTournee; i++) {
                Node livNode = segmentTournee.item(i);
                if (livNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) livNode;
                    String id_destination = (eElement.getAttribute("destination"));
                    int id_livreur = Integer.valueOf(eElement.getAttribute("id_livreur"));
                    String id_origin = (eElement.getAttribute("origin"));

                    for (Segment segment : plan.obtenirListeSegment()) {
                        if (segment.obtenirDestination().obtenirId() == id_destination && segment.obtenirOrigine().obtenirId() == id_origin) {
                            listeLivreur.get(id_livreur).obtenirTournee().obtenirListeSegment().add(segment);
                        }
                    }
                }
            }

            for (Livreur liv : listeLivreur) {
                System.out.println(liv.obtenirLivraisons());
                System.out.println(liv.obtenirTournee());
            }

            this.setChanged();
            this.notifyObservers();
        } catch (IOException e) {
            System.out.println(e);
        } catch (SAXException e) {
            System.out.println(e);
        } catch (ParserConfigurationException e) {
            System.out.println(e);
        }
    }
}
