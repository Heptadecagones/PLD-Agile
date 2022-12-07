package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.Map;
import ch.qos.logback.core.joran.sanity.Pair;

import org.junit.jupiter.api.Test;

public class IntersectionTest {
    private final Integer premiereTrancheHoraire = 8;
    private final Integer derniereTrancheHoraire = 11; 
    
    @Test
    void testAjoutTrancheHoraireAcceptee() {
        Intersection intersection = new Intersection();
        Integer trancheHoraire = premiereTrancheHoraire + (int) (Math.random() *
                                    (derniereTrancheHoraire - premiereTrancheHoraire + 1)); 
        boolean retourAjout = intersection.modifierTrancheHoraire(trancheHoraire);
        System.out.println(trancheHoraire);
        assertTrue(retourAjout);
    }

    /**
     * Test vérifiant que pour n intersections 
     */
    @Test
    void testArborescenceContientToutesIntersectionsSaufElle() {
        Plan plan = new Plan();
        plan.chargerXML("src/main/java/largeMap.xml");

        ArrayList<Intersection> listeIntersectionsPlan = plan.obtenirListeIntersection();
        Integer indexAleatoire = (int) (Math.random() * listeIntersectionsPlan.size());

        Intersection intersectionTest = listeIntersectionsPlan.get(indexAleatoire);
        intersectionTest.calculerArborescenceChemins(listeIntersectionsPlan);

        Map<Intersection, Pair<ArrayList<Segment>, Double>> arborescence = intersectionTest.obtenirArborescence();
        
        assertEquals(listeIntersectionsPlan.size() - 1, arborescence.size());
        assertFalse(arborescence.containsKey(intersectionTest));
    }
}
