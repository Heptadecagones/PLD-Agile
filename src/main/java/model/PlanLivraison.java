package model;

import java.util.Observable;
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
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Henri
 *         Voir diagramme des classes
 *         Classe avec un Plan et une liste de Livreur, permet de faire le lien
 *         entre le plan et les livraisons
 */
public class PlanLivraison extends Observable {

    private Intersection entrepot;
    private ArrayList<Intersection> listeIntersection = new ArrayList<>();
    private ArrayList<Segment> listeSegment = new ArrayList<>();
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
    public void sauvegarder(){
/* 
        listeIntersection = Intersection.obtenirListeIntersection();
        listeSegment = planLivraison.obtenirPlan().obtenirListeSegment();
        entrepot = planLivraison.obtenirPlan().obtenirEntrepot();
        listeLivreur = planLivraison.obtenirListeLivreur();
*/


        try {
 
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();
            
            // élément de racine
            Document doc = docBuilder.newDocument();
            Element racine = doc.createElement("map");
            doc.appendChild(racine);

            

            // l'élément contact
            Element livreur = doc.createElement("livreur");
            racine.appendChild(livreur);


            Element warehouse = doc.createElement("warehouse");
            racine.appendChild(warehouse);
            

            

            // intersection

            Element intersection = doc.createElement("intersection");
            
            racine.appendChild(intersection);
            
            intersection.setAttribute("id", "99");
            intersection.setAttribute("latitude", "123");
            intersection.setAttribute("longitude", "123");
            // id
           


          



             //segment
             Element segment = doc.createElement("segment");
             racine.appendChild(segment);
             
             segment.setAttribute("destination", "456");
             segment.setAttribute("length", "456");
             segment.setAttribute("name", "chil3ba");
             segment.setAttribute("destination", "456");

         
           
            
            // write the content into xml file
            String cheminXML = File.separator + "src" + File.separator + "main" + File.separator + "java";
            //File repertoireProjet = new File(System.getProperty("user.dir") + cheminXML);
           

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult resultat = new StreamResult(new File((System.getProperty("user.dir") + cheminXML + File.separator + "monFichier.xml")));
            
            transformer.transform(source, resultat);
            
            System.out.println("Fichier sauvegardé avec succès!");
            
            } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
            } catch (TransformerException tfe) {
            tfe.printStackTrace();
            }



            }
        
}
