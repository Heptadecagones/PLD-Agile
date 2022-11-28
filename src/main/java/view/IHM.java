package view;

import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import model.Plan;

public class IHM {
    // position of the top left corner of the application
    final int LOCATION_X = 100;
    final int LOCATION_Y = 80;
    private Bar bar = new Bar();
    private Description description = new Description();
    private Map map = new Map();
    public IHM(){
    }
    public void setBar(Bar bar) {
        this.bar = bar;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public Bar getBar() {
        return bar;
    }

    public Description getDescription() {
        return description;
    }

    public Map getMap() {
        return map;
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
            JFrame f = new JFrame("IHM");
            f.setLocation(LOCATION_X, LOCATION_Y);
            f.add(bar, BorderLayout.NORTH);

            JPanel southPanel = new JPanel();
            southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.LINE_AXIS));
            southPanel.add(description);
            southPanel.add(map);
            f.add(southPanel, BorderLayout.SOUTH);
                    f.pack();
                    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    f.setResizable(false);
                    f.setVisible(true);
    }
    

}

