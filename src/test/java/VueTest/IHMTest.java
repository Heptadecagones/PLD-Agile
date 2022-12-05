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
 
public class IHMTest
{
    public static void main(String[] args) throws IOException,
                           AWTException, InterruptedException
    {
        Controleur c=new Controleur();

        
        int keyInput[] = { KeyEvent.VK_J, KeyEvent.VK_A, KeyEvent.VK_V,
            KeyEvent.VK_A, KeyEvent.VK_SPACE };
            
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        /*Robot robot = new Robot();
        robot.mouseMove(screenSize.width*1/3, 0);
        robot.mousePress(0);
        robot.delay(100);
        robot.mouseRelease(0);
        robot.delay(1000);*/
        Robot robot2 = new Robot();
        for(int i=0;i<keyInput.length;i++){
            robot2.keyPress(keyInput[i]);
            robot2.delay(100);
            robot2.keyRelease(keyInput[i]);
        }
        c.view.obtenirBarre().obtenirCharger().doClick();
        Thread.sleep(1000);
  
        robot2.delay(2000);
        robot2.delay(1000);

    }
}                                                                          