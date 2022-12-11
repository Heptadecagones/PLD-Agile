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
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;

import model.Intersection;
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
    JList<Livraison> btnList;
    private Carte carte;
    private Intersection intersectionChoisie;
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
   
        btnList = new JList(btnName);
        btnList.setSelectedIndex(0);
        btnList.setVisibleRowCount(3);     
        btnList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
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
                    btnName.addElement(ls);
                }
            }
        
    }
    public void surlignerLivraison(Intersection intersection) {
        int[] tabIndices=new int[btnName.size()];
        int nbSelection=0;
        for(int k=0;k<btnName.size();k++){
            if(((Livraison)(btnName.get(k))).obtenirLieu()==intersection){
                tabIndices[nbSelection]=k;
                nbSelection++;
                this.carte.modifierLivraisonClickee((Livraison)(btnName.get(k)));
            }
        }
        int[] select=new int[nbSelection];
        for(int k=0;k<nbSelection;k++){
                select[k]=tabIndices[k];

        }
        for(int k=0;k<select.length;k++){
            System.out.println("\n:"+select[k]);
        }    
        btnList.setSelectedIndices(select);
    }
        
        
        
}
