package model;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Tests du chargement de fichiers XML
 * 
 * @author Hugo
 */
public class PlanTest {
    static Plan plan;

    /** Groupe de méthodes qui chargent les cartes.
     *  Pour simplifier la maintenance.
     * 
     *  Remarque : les segments et intersections sont remis à zéro au début du
     *  chargement
     * 
     * @return la longueur du plus long segment de la carte
     */

    private double chargerPetiteCarte() {
        return plan.chargerXML("src/main/java/smallMap.xml");
    }

    private double chargerMoyenneCarte() {
        return plan.chargerXML("src/main/java/mediumMap.xml");
    }

    private double chargerGrandeCarte() {
        return plan.chargerXML("src/main/java/largeMap.xml");
    }

    /** Méthode qui crée un instance de Plan pour pouvoir charger les documents
     *  XML
    */
    @BeforeAll
    public static void creerInstancePlan()
    {
        plan = new Plan();
    }

    @Test
    void testCoutsSontPositifsEtInferieursAuCoutMaximal() {
        double longueurMaxSegment = chargerGrandeCarte();
        for(Segment seg : plan.obtenirListeSegment()) {
            assertTrue(seg.obtenirLongueur() >= 0 && seg.obtenirLongueur() <= longueurMaxSegment);
        }
    }
}
