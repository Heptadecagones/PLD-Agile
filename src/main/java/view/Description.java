package view;

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;

import model.Livraison;
import model.Livreur;
import model.PlanLivraison;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
/**
 *
 * @author Equipe IHM
 */

@SuppressWarnings("serial")
public class Description extends JPanel implements Observer {
    /**
     * Tous les composants
     */
    JTextArea descText;
    JScrollPane scrollPane;
    private JLabel chargement=new JLabel("CHARGEMENT");
    JPanel panelbtnLivraison;
    ArrayList<JButton> btnLivraison = new ArrayList<JButton>();
    DefaultListModel btnName = new DefaultListModel();
    private Carte carte;
    /**
     * la taille du panneau
     */
    public void modifierCarte(Carte carte) {
        this.carte=carte;
    }

    public Description() {
    }
    public JLabel obtenirChargement() {
        return chargement;
    }
    public void init(){
   
        final JList btnList = new JList(btnName);
        btnList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        btnList.setSelectedIndex(0);
        btnList.setVisibleRowCount(3);     
  
        JScrollPane btnListScrollPane = new JScrollPane(btnList);  
        btnListScrollPane.setPreferredSize(new Dimension(200, 200));
        add(chargement);
        add(btnListScrollPane);       
        btnList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                JList list = (JList)evt.getSource();
                int index = list.locationToIndex(evt.getPoint());
                System.out.println(index);
                System.out.println("test+ "+btnList.getSelectedValue());
                carte.modifierLivraisonClickee((Livraison)btnList.getSelectedValue());
                carte.repaint();
            }
        }); 
    }

    // Mise à jour des données : Ecriture de la liste des livraisons
    @Override
    public void update(Observable arg0, Object arg1) {
            PlanLivraison p = (PlanLivraison) arg0;
            btnName.clear();
            for (Livreur li : p.obtenirListeLivreur()) {
                for(Livraison ls:li.obtenirLivraisons()){
                    System.out.println(ls.toString() + "\n");
                    btnName.addElement(ls);
                }
            }
        
    }

}
