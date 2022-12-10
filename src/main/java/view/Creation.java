
package view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import model.Intersection;
import model.Livreur;

/**
 *
 * @author Equipe IHM
 */

@SuppressWarnings("serial")
public class Creation {
    private JButton btnCreerLivraison;

    private final Font font = new Font("Arial", Font.PLAIN, 12);

    private String horaires[] = { "8", "9", "10", "11" };

    private String livreurs[];
    private JFrame f = new JFrame("Ajout Livraison");

    private JTextArea textIntersection = new JTextArea(10, 20);
    private JScrollPane defilerTextIntersection = new JScrollPane(textIntersection);
    private JComboBox textHoraire = new JComboBox(horaires);
    private JComboBox textLivreur = new JComboBox();

    private JPanel panelIntersection = new JPanel(new BorderLayout());
    private JPanel panelHoraire = new JPanel(new BorderLayout());
    private JPanel panelLivreur = new JPanel(new BorderLayout());

    private Intersection intersection;
    public void modifierlivreurs(ArrayList<Livreur> livreursListe) {
        livreurs = new String[livreursListe.size()];
        for(int i=0;i<livreursListe.size();i++){
            livreurs[i]=livreursListe.get(i).obtenirId()+";"+livreursListe.get(i).obtenirNom();
        }
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>( livreurs );
        textLivreur.setModel(model);
    }
    public Intersection obtenirIntersection() {
        return intersection;
    }

    public void setIntersection(Intersection intersection) {
        this.intersection = intersection;
    }

    public JButton obtenirBtnCreerLivraison() {
        return btnCreerLivraison;
    }

    public Creation() {

    }

    public void init() {
        for(int i=0;i<8;i++){
        
        }
        // Définir les propriétés des composants
        btnCreerLivraison = creerBouton("Creer");
        textIntersection.setFont(font);
        textIntersection.setLineWrap(true);
        textIntersection.setEditable(false);
        textHoraire.setFont(font);
        textLivreur.setFont(font);

        // Ajouter des composants
        JPanel panelMere = new JPanel();
        panelMere.setLayout(new BoxLayout(panelMere, BoxLayout.Y_AXIS));

        JPanel panelFil = new JPanel();
        panelFil.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelIntersection.setBorder(new TitledBorder("Intersection"));
        panelIntersection.add(defilerTextIntersection);
        panelFil.add(panelIntersection, gbc);

        gbc.gridy++;
        panelHoraire.setBorder(new TitledBorder("Horaire"));
        panelHoraire.add(textHoraire);
        panelFil.add(panelHoraire, gbc);

        gbc.gridy++;
        panelLivreur.setBorder(new TitledBorder("Livreur"));
        panelLivreur.add(textLivreur);
        panelFil.add(panelLivreur, gbc);

        panelMere.add(panelFil);
        panelMere.add(btnCreerLivraison);
        f.add(panelMere);

        // Définir les propriétés de la fenêtre
        //f.setLocation(200, 200);
        f.setResizable(false);
        f.pack();
        f.setVisible(false);

        ActionListener action = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (evt.getSource() == btnCreerLivraison) {
                    fermer();
                }
            }
        };
        btnCreerLivraison.addActionListener(action);
    }

    public String obtenirTextHoraire() {
        return (String) textHoraire.getSelectedItem();
    }

    public String obtenirTextLivreur() {
        return textLivreur.getSelectedItem().toString();
    }

    public String obtenirTextIntersection() {
        return textIntersection.getText();
    }

    /**
     * Ouvrir la fenêtre
     */
    public void ouvrir() {
        textIntersection.setText(this.intersection.toString());
        this.f.setVisible(true);
    }

    /**
     * Fermer la fenêtre
     */
    public void fermer() {
        this.f.setVisible(false);
    }

    /**
     * Renvoie un bouton avec des propriétés de base (police, etc)
     * 
     * @param nom
     * @return JButton
     */
    public JButton creerBouton(String nom) {
        JButton bouton = new JButton(nom);
        bouton.setFont(font);
        bouton.setFocusPainted(false);
        return bouton;
    }
}
