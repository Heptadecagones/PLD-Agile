package VueTest;

// Java program to demonstrate working of Robot
// class. This program is for Windows. It opens
// notepad and types a message.
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.*;
import java.awt.Dimension;
import java.awt.Toolkit;
import controller.Controleur;
import java.awt.*;   
import java.awt.event.*;   
import javax.swing.*;   
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class IHMTest
{
    @Test
    public void nouvelleLivraison() throws IOException,
                           AWTException, InterruptedException
    {
        System.out.println("testIHM");
        Controleur c=new Controleur();

        
        int keyInput[] = { KeyEvent.VK_J, KeyEvent.VK_A, KeyEvent.VK_V,
            KeyEvent.VK_A, KeyEvent.VK_SPACE };
            
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        //c.view.obtenirBarre().obtenirCharger().doClick();
        c.planLivraison.ouvrirPlan("src/main/java/largeMap.xml");
        
        Robot robot = new Robot();
        robot.delay(1000);
        robot.mouseMove(screenSize.width*1/2, screenSize.height*1/2);
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.delay(100);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
        robot.delay(1000);
        c.view.obtenirCarte().obtenirFenetreCreation().obtenirBtnCreerLivraison().doClick();

        assertTrue(c.planLivraison.obtenirListeLivreur().get(0).obtenirLivraisons().size()==1);

    }
}                                                                          