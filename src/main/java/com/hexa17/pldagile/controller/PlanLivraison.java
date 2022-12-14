package com.hexa17.pldagile.controller;

import java.util.Observable;
import java.io.File;
import java.util.ArrayList;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.TransformerException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.hexa17.pldagile.model.Intersection;
import com.hexa17.pldagile.model.Livraison;
import com.hexa17.pldagile.model.Livreur;
import com.hexa17.pldagile.model.Plan;
import com.hexa17.pldagile.model.PlanUsine;
import com.hexa17.pldagile.model.Segment;
import com.hexa17.pldagile.model.Tournee;
import com.hexa17.pldagile.model.algo.Solveur;

/**
 * <p>
 * PlanLivraison class.
 * </p>
 *
 * @author Henri
 *         Voir diagramme des classes
 *         Classe avec un Plan et une liste de Livreur, permet de faire le lien
 *         entre le plan et les livraisons
 * @version $Id: $Id
 */
public class PlanLivraison extends Observable {

    private ArrayList<Livreur> listeLivreur;
    private Plan plan;

    /**
     * <p>
     * init.
     * </p>
     */
    public void init() {
        this.plan = null;
        this.listeLivreur = new ArrayList<Livreur>();
        this.listeLivreur.add(new Livreur(0, "Jean Titi"));
        this.listeLivreur.add(new Livreur(1, "Hervé Lapin"));
        this.listeLivreur.add(new Livreur(2, "Jeanne-Annick Al-Pohou"));
        this.listeLivreur.add(new Livreur(3, "Bertrand Turpin"));
    }

    /**
     * <p>
     * Constructor for PlanLivraison.
     * </p>
     */
    public PlanLivraison() {
        init();
    }

    /**
     * <p>
     * Constructor for PlanLivraison.
     * </p>
     *
     * @param cheminXml a {@link java.lang.String} object
     */
    public PlanLivraison(String cheminXml) {
        PlanUsine pf = new PlanUsine();
        pf.chargerXML(cheminXml, false);
        this.plan = pf.construirePlan();

        this.listeLivreur = new ArrayList<Livreur>();
        this.listeLivreur.add(new Livreur(0, "Jean Jean"));
        this.listeLivreur.add(new Livreur(1, "Hervé Lapin"));
        this.listeLivreur.add(new Livreur(2, "Jeanne-Annick Al-Pohou"));
        this.listeLivreur.add(new Livreur(3, "Bertrand Turpin"));

        this.setChanged();
        this.notifyObservers();
    }

    /**
     * <p>
     * initPlan.
     * </p>
     *
     * @param cheminXml a {@link java.lang.String} object
     */

    // Initialisation du plan lorsqu'un fichier est chargé
    public void initPlan(String cheminXml) {

        // Réinitialisation des livreurs
        init();

        // Récupération des données du fichier XML
        PlanUsine pf = new PlanUsine();
        pf.chargerXML(cheminXml, false);

        // Construction du plan
        this.plan = pf.construirePlan();

        this.setChanged();
        this.notifyObservers();
    }

    /**
     * <p>
     * obtenirListeLivreur.
     * </p>
     *
     * @return a {@link java.util.ArrayList} object
     */
    public ArrayList<Livreur> obtenirListeLivreur() {
        return this.listeLivreur;
    }

    /**
     * <p>
     * obtenirPlan.
     * </p>
     *
     * @return a {@link com.hexa17.pldagile.model.Plan} object
     */
    public Plan obtenirPlan() {
        return this.plan;
    }

    // AJOUT DUNE LIVRAISON, METHODE APPELEE PAR LE CONTROLLEUR
    /**
     * <p>
     * nouvelleLivraison.
     * </p>
     *
     * @param horaire      a {@link java.lang.String} object
     * @param intersection a {@link com.hexa17.pldagile.model.Intersection} object
     * @param numLivreur   a {@link java.lang.String} object
     */
    public void nouvelleLivraison(String horaire, Intersection intersection, String numLivreur) {

        // Création de la nouvelle livraison et attribution de celle-ci au livreur
        Livreur livreurActuel = this.listeLivreur.get(Integer.parseInt(numLivreur));
        Livraison nouvelleLivraison = new Livraison(Integer.parseInt(horaire), intersection, livreurActuel);
        livreurActuel.obtenirLivraisons().add(nouvelleLivraison);

        // Calcul de la nouvele tournée du livreur
        Tournee tournee = new Tournee();
        Solveur solveur = new Solveur(plan);
        tournee = solveur.calculerTournee(livreurActuel);
        livreurActuel.modifierTournee(tournee);

        this.setChanged();
        this.notifyObservers();
    }

    /**
     * <p>
     * supprimerLivraison.
     * </p>
     *
     * @param livraison a {@link com.hexa17.pldagile.model.Livraison} object
     */
    public void supprimerLivraison(Livraison livraison) {

        // Test si la livraison existe bien
        if (livraison == null)
            return;

        Livreur livreur = livraison.obtenirLivreur();
        Tournee tournee = new Tournee();

        // On retire la livraison de la liste de livraison du livreur
        livreur.obtenirLivraisons().remove(livraison);
        livraison.modifierLivreur(null);

        // Calcul de la tournée si la liste de livraison du livreur n'est pas vide
        if (!livreur.obtenirLivraisons().isEmpty()) {
            Solveur solveur = new Solveur(plan);
            tournee = solveur.calculerTournee(livreur);
        }
        livreur.modifierTournee(tournee);

        this.setChanged();
        this.notifyObservers();
    }

    /**
     * <p>
     * ajouterLivreur.
     * </p>
     *
     * @param l a {@link com.hexa17.pldagile.model.Livreur} object
     */
    public void ajouterLivreur(Livreur l) {
        this.listeLivreur.add(l);
        this.setChanged();
        this.notifyObservers();
    }

    /**
     * <p>
     * chargerLivraison.
     * </p>
     *
     * @param cheminXml a {@link java.lang.String} object
     */

    // Charger le plan/Livraison d'un fichier enregistré
    public void chargerLivraison(String cheminXml) {

        // Récupération des données
        PlanUsine pf = new PlanUsine();
        this.listeLivreur = pf.chargerXML(cheminXml, true);

        // Construction du plan
        this.plan = pf.construirePlan();

        this.setChanged();
        this.notifyObservers();
    }

    /**
     * <p>
     * sauvegarder.
     * </p>
     *
     * @param nomFichier a {@link java.lang.String} object
     */
    public void sauvegarder(String nomFichier) {

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
            for (Livreur livr : obtenirListeLivreur()) {
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
            for (Livreur livr : obtenirListeLivreur()) {

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
}
