package view;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;

import java.awt.BorderLayout;
import java.awt.Dimension;

import model.Intersection;
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
    JScrollPane btnListScrollPane;

    JList<Livraison> btnList;
    /**
     * la taille du panneau
     */
    public JList<Livraison> obtenirBtnList(){
        return btnList;
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

        
    public void init(){
        setBorder(chargement);
        setLayout(new BorderLayout());
        //btnList.setVisibleRowCount(3);
  
        btnListScrollPane = new JScrollPane(btnList);
        add(btnListScrollPane);
        btnList = new JList(btnName);
        btnList.setSelectedIndex(0);
        btnList.setVisibleRowCount(3);     
        btnList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane btnListScrollPane = new JScrollPane(btnList);  
        btnListScrollPane.setPreferredSize(new Dimension(200, 200));
        add(btnListScrollPane);       
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
    public Livraison surlignerLivraison(Intersection intersection) {
        int[] tabIndices=new int[btnName.size()];
        int nbSelection=0;
        Livraison retour=new Livraison();
        for(int k=0;k<btnName.size();k++){
            if(((Livraison)(btnName.get(k))).obtenirLieu()==intersection){
                tabIndices[nbSelection]=k;
                nbSelection++;
                retour=(Livraison)(btnName.get(k));
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
        return retour;
    }
        
        
        
}
