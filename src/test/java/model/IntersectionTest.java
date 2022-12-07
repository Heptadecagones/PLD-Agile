package model;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
        assertEquals(trancheHoraire, 8);
        assertTrue(retourAjout);
    }
}
