package view;

import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class IHM {
    // position of the top left corner of the application
    final int POSITION_X = 100;
    final int POSITION_Y = 20;

    private Barre barre = new Barre();
    private Description description = new Description();
    private Carte carte = new Carte();

    public void setBarre(Barre barre) {
        this.barre = barre;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public void setCarte(Carte carte) {
        this.carte = carte;
    }

    public Barre getBarre() {
        return barre;
    }

    public Description getDescription() {
        return description;
    }

    public Carte getCarte() {
        return carte;
    }

    public void init(){
        /*try {
                for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }*/

        JFrame frame = new JFrame("IHM");
        
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        
        frame.setVisible(true);
        

        frame.setLocation(POSITION_X, POSITION_Y);
        frame.add(barre, BorderLayout.NORTH);

        JPanel panelSud = new JPanel();
        panelSud.setLayout(new BoxLayout(panelSud, BoxLayout.LINE_AXIS));
        panelSud.add(description);
        panelSud.add(carte);
        frame.add(panelSud, BorderLayout.SOUTH);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }
    
}

