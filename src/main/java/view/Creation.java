
package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Intersection;

@SuppressWarnings("serial")
public class Creation extends JPanel {
    private JButton btnCreerLivraison;
    private JLabel infoHoraire = new JLabel("Horaire :");
    private JLabel infoLivreur = new JLabel("Livreur :");

    private final Font font = new Font("Arial", Font.PLAIN, 16);

    private String horaires[] = { "8", "9", "10", "11" };

    private JFrame f = new JFrame("Ajout Livraison");
    private JComboBox textHoraire = new JComboBox(horaires);
    private JTextField textLivreur = new JTextField();
    private JLabel textIntersection = new JLabel("pas d'infos sur l'intersection");
    private Intersection intersection;

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
        btnCreerLivraison = creerBouton("Creer");

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(textIntersection);
        panel.add(infoHoraire);
        panel.add(textHoraire);
        panel.add(infoLivreur);
        panel.add(textLivreur);
        panel.add(btnCreerLivraison);
        f.add(panel);
        f.setLocation(200, 200);
        f.setResizable(false);
        f.setVisible(false);
        f.pack();
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
        return textLivreur.getText();
    }

    public String obtenirTextIntersection() {
        return textIntersection.getText();
    }

    public void ouvrir() {
        textIntersection.setText(this.intersection.toString());
        this.f.setVisible(true);
    }

    public void fermer() {
        this.f.setVisible(false);
    }

    public JButton creerBouton(String nom) {
        JButton bouton = new JButton(nom);
        bouton.setFont(font);
        bouton.setFocusPainted(false);
        return bouton;
    }
}
