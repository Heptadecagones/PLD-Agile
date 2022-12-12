package com.hexa17.pldagile.model;

import java.util.Observable;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.hexa17.pldagile.model.algo.Graphe;
import com.hexa17.pldagile.model.algo.Lien;
import com.hexa17.pldagile.model.algo.Solveur;
import com.hexa17.pldagile.model.algo.tabu.TabuSearch;

import java.util.Observable;
import java.util.WeakHashMap;
import java.util.ArrayList;
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

        Livreur livreurActuel = this.listeLivreur.get(Integer.parseInt(numLivreur));
        Livraison nouvelleLivraison = new Livraison(Integer.parseInt(horaire), intersection, livreurActuel);
        livreurActuel.obtenirLivraisons().add(nouvelleLivraison);
        
        Tournee tournee = new Tournee();
        Solveur solveur = new Solveur(plan);
        tournee = solveur.calculerTournee(livreurActuel);
        
        livreurActuel.modifierTournee(tournee);
        this.setChanged();
        this.notifyObservers();
    }

    public PlanLivraison() {
        init();
    }

    public void initPlan(String cheminXml) {
        PlanUsine pf = new PlanUsine();
        pf.chargerXML(cheminXml);
        this.plan = pf.construirePlan();

        this.setChanged();
        this.notifyObservers();
    }

    public PlanLivraison(String cheminXml) {
        PlanUsine pf = new PlanUsine();
        pf.chargerXML(cheminXml);
        this.plan = pf.construirePlan();
        
        
        this.listeLivreur = new ArrayList<Livreur>();
        this.listeLivreur.add(new Livreur(0,"Jean Jean"));
        this.listeLivreur.add(new Livreur(1,"Hervé Lapin"));
        this.listeLivreur.add(new Livreur(2,"Jeanne-Annick Al-Pohou"));
        this.listeLivreur.add(new Livreur(3,"Bertrand Turpin"));

        this.setChanged();
        this.notifyObservers();
    }

    public ArrayList<Livreur> obtenirListeLivreur() {
        return this.listeLivreur;
    }

    public Plan obtenirPlan() {
        return this.plan;
    }

    public void ajouterLivreur(Livreur l){
        this.listeLivreur.add(l);
        this.setChanged();
        this.notifyObservers();
    }

    public void init() {
        this.plan = null;
        this.listeLivreur = new ArrayList<Livreur>();
        this.listeLivreur.add(new Livreur(0,"Jean Titi"));
        this.listeLivreur.add(new Livreur(1,"Hervé Lapin"));
        this.listeLivreur.add(new Livreur(2,"Jeanne-Annick Al-Pohou"));
        this.listeLivreur.add(new Livreur(3,"Bertrand Turpin"));
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
                    // id
                    // doc.setTextContent("\n");
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
                //   doc.setTextContent("\n");    
                }
            }

            for(Livreur livr : obtenirListeLivreur()){
                
                for(Segment segment_livraison : livr.obtenirTournee().obtenirListeSegment()){
                

                
                    //segment
                    Element livraison = doc.createElement("livraison");
                    racine.appendChild(livraison);

                    ajout_parametre = String.valueOf(livr.obtenirId());
                    livraison.setAttribute("id_livreur", ajout_parametre);
    
                    ajout_parametre = String.valueOf(segment_livraison.obtenirDestination().obtenirId());
                    livraison.setAttribute("destination", ajout_parametre);
                
                    ajout_parametre = String.valueOf(segment_livraison.obtenirOrigine().obtenirId());
                    livraison.setAttribute("origin", ajout_parametre);

                }
            //   doc.setTextContent("\n");
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

    public void chargerLivraison(String cheminXml) {
        PlanUsine pf = new PlanUsine();
        pf.chargerXML(cheminXml);
        this.plan = pf.construirePlan();

        this.setChanged();
        this.notifyObservers();
    }
}
