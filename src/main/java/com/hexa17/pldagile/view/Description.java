package com.hexa17.pldagile.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.awt.Color;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;

import com.hexa17.pldagile.model.Intersection;
import com.hexa17.pldagile.model.Livraison;
import com.hexa17.pldagile.model.Livreur;
import com.hexa17.pldagile.model.PlanLivraison;

/**
 * <p>
 * Description class.
 * </p>
 *
 * @author Equipe IHM
 * @version $Id: $Id
 */

@SuppressWarnings("serial")
public class Description extends JPanel implements Observer {

    private TitledBorder chargement = new TitledBorder("Chargement");

    // Composants pour la liste des Livraisons
    JPanel panelbtnLivraison;
    ArrayList<JButton> btnLivraison = new ArrayList<JButton>();
    DefaultListModel btnName = new DefaultListModel();
    JScrollPane btnListScrollPane;
    JList<Livraison> btnList;

    /**
     * <p>
     * obtenirBtnList.
     * </p>
     *
     * @return a {@link javax.swing.JList} object
     */
    JButton supprimerLivraison;
    Livraison choixLivraison = new Livraison();

    /**
     * <p>obtenirChoixLivraison.</p>
     *
     * @return a {@link com.hexa17.pldagile.model.Livraison} object
     */
    public Livraison obtenirChoixLivraison() {
        return choixLivraison;
    }

    /**
     * <p>obtenirSupprimerLivraison.</p>
     *
     * @return a {@link javax.swing.JButton} object
     */
    public JButton obtenirSupprimerLivraison() {
        return supprimerLivraison;
    }

    /**
     * <p>modifierSupprimerLivraison.</p>
     *
     * @param jb a {@link javax.swing.JButton} object
     */
    public void modifierSupprimerLivraison(JButton jb) {
        supprimerLivraison = jb;
    }

    /**
     * <p>obtenirBtnList.</p>
     *
     * @return a {@link javax.swing.JList} object
     */
    public JList<Livraison> obtenirBtnList() {
        return btnList;
    }

    /**
     * <p>
     * obtenirChargement.
     * </p>
     *
     * @return a {@link javax.swing.border.TitledBorder} object
     */
    public TitledBorder obtenirChargement() {
        return chargement;
    }

    /**
     * <p>
     * modifierTitle.
     * </p>
     *
     * @param title a {@link java.lang.String} object
     */
    public void modifierTitle(String title) {
        this.chargement.setTitle(title);
        setBorder(chargement);
        revalidate();
    }

    /**
     * <p>
     * Constructor for Description.
     * </p>
     */
    public Description() {
    }

    /**
     * <p>
     * init.
     * </p>
     */
    public void init() {

        setBorder(chargement);
        setLayout(new BorderLayout());
        supprimerLivraison = new JButton("Supprimer");
        // Initialisation de la JList des livraisons
        btnListScrollPane = new JScrollPane(btnList);
        add(btnListScrollPane);
        btnList = new JList(btnName);
        btnList.setSelectedIndex(0);
        btnList.setVisibleRowCount(3);
        btnList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        JScrollPane btnListScrollPane = new JScrollPane(btnList);
        add(btnListScrollPane);
        supprimerLivraison.setBackground(Color.RED);
        supprimerLivraison.setForeground(Color.WHITE);
        add(supprimerLivraison, BorderLayout.SOUTH);
        supprimerLivraison.setVisible(false);
    }

    // Mise à jour des données : Ecriture de la liste des livraisons
    /** {@inheritDoc} */
    @Override
    public void update(Observable arg0, Object arg1) {
        PlanLivraison p = (PlanLivraison) arg0;
        btnName.clear();
        for (Livreur li : p.obtenirListeLivreur()) {
            for (Livraison ls : li.obtenirTournee().obtenirListeLivraison()) {
                btnName.addElement(ls);
            }
        }
    }

    /**
     * <p>
     * surlignerLivraison.
     * </p>
     *
     * @autor Henri
     * @return Livraison : Livraison qui correspond à l'intersection
     * @description méthode appelée par le contrôleur après un click sur une
     *              intersection
     *              on surligne dans la description la livraison qui correspond et
     *              on la renvoie au contrôleur
     * @param intersection a {@link com.hexa17.pldagile.model.Intersection} object
     */
    public Livraison surlignerLivraison(Intersection intersection) {

        int[] tabIndices = new int[btnName.size()];
        int nbSelection = 0;
        Livraison retour = new Livraison();

        // recherche des livraisons qui correspondent à l'intersection
        for (int k = 0; k < btnName.size(); k++) {
            if (((Livraison) (btnName.get(k))).obtenirLieu() == intersection) {
                tabIndices[nbSelection] = k;
                nbSelection++;
                retour = (Livraison) (btnName.get(k));
            }
        }

        // surlignage (selection) dans la JList
        int[] select = new int[nbSelection];
        for (int k = 0; k < nbSelection; k++) {
            select[k] = tabIndices[k];
        }
        btnList.setSelectedIndices(select);
        choixLivraison = retour;
        return retour;
    }

    /**
     * <p>modifierChoixLivraison.</p>
     *
     * @param l a {@link com.hexa17.pldagile.model.Livraison} object
     */
    public void modifierChoixLivraison(Livraison l) {
        choixLivraison = l;
    }

}
