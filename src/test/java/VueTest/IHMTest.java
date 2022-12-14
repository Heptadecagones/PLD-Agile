package VueTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.io.IOException;
import java.awt.event.KeyEvent;
import org.junit.jupiter.api.Test;

import com.hexa17.pldagile.controller.Controleur;

public class IHMTest {
    @Test
    public void nouvelleLivraison() throws IOException,
            AWTException, InterruptedException {
        System.out.println("testIHM");
        Controleur c = new Controleur();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        // c.view.obtenirBarre().obtenirCharger().doClick();
        c.view.obtenirCarte().initDonnee();
        c.planLivraison.initPlan("data/largeMap.xml");
        
        Robot robot = new Robot();         
        robot.delay(1000);
        robot.mouseMove(screenSize.width * 1 / 2, screenSize.height * 1 / 2);
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.delay(100);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
        robot.delay(1000);
        c.view.obtenirFenetreCreation().obtenirBtnCreerLivraison().doClick();
        robot.delay(1000);
        robot.mouseWheel(-25);
        robot.delay(100);
        robot.delay(1000);
        robot.mouseMove(screenSize.width * 1 / 2, screenSize.height * 1 / 2);
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.delay(100);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
        robot.delay(500);
        c.view.obtenirFenetreCreation().obtenirBtnCreerLivraison().doClick();
        robot.delay(500);
        assertTrue(c.planLivraison.obtenirListeLivreur().get(0).obtenirLivraisons().size() == 2);
        robot.delay(500);
        c.view.obtenirDescription().surlignerLivraison(c.planLivraison.obtenirListeLivreur().get(0).obtenirLivraisons().get(0).obtenirLieu());
        robot.delay(1000);
        c.view.obtenirDescription().obtenirSupprimerLivraison().doClick();
        assertTrue(c.planLivraison.obtenirListeLivreur().get(0).obtenirLivraisons().size() == 1);
    }
}

