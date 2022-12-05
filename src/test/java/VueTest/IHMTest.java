package VueTest;

// Java program to demonstrate working of Robot
// class. This program is for Windows. It opens
// notepad and types a message.
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.*;

import controller.Controleur;
 
public class IHMTest
{
    public static void main(String[] args) throws IOException,
                           AWTException, InterruptedException
    {
        Controleur c=new Controleur();
        try {
            Thread.sleep(2000);
        }
        catch (InterruptedException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Runtime.getRuntime().exec("WordPad");
        int keyInput[] = { KeyEvent.VK_J, KeyEvent.VK_A, KeyEvent.VK_V,
            KeyEvent.VK_A, KeyEvent.VK_SPACE };
        c.view.obtenirBarre().obtenirCharger().doClick();
        Robot robot = new Robot();
        for (int i = 0; i < keyInput.length; i++) {
          robot.keyPress(keyInput[i]);
          robot.delay(100);
        }

    }
}