package view;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;

import model.Livraison;
import model.Livreur;
import model.PlanLivraison;

/**
 *
 * @author Equipe IHM
 */

@SuppressWarnings("serial")
public class Description extends JPanel implements Observer {
    /**
     * Tous les composants
     */
    private TitledBorder chargement = new TitledBorder("Chargement");
    JPanel panelbtnLivraison;
    ArrayList<JButton> btnLivraison = new ArrayList<JButton>();
    DefaultListModel btnName = new DefaultListModel();
    JList btnList;
    JScrollPane btnListScrollPane;
    private Carte carte;

    public void modifierCarte(Carte carte) {
        this.carte=carte;
    }

    public Description() {
    }

    public TitledBorder obtenirChargement() {
        return chargement;
    }

    public void modifierTitle(String title) {
        this.chargement.setTitle(title);
        setBorder(chargement);
        revalidate();
    }
    public void init() {
        setBorder(chargement);
        setLayout(new BorderLayout());
        btnList = new JList(btnName);
        btnList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        btnList.setSelectedIndex(0);
        //btnList.setVisibleRowCount(3);
  
        btnListScrollPane = new JScrollPane(btnList);
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
