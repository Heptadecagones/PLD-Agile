package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class Barre extends JPanel {
    private Font font = new Font("Arial",Font.PLAIN,14);
    private JButton ajouterLivraison, sauvegarder, charger;
    private Creation fenetreCreation;

	public Creation obtenirFenetreCreation() {
		return fenetreCreation;
	}

	public void modifierAjouterLivraison(JButton AjouterLivraison) {
			this.ajouterLivraison = AjouterLivraison;
	}

	public void modifierSauvegarder(JButton Sauvegarder) {
			this.sauvegarder = Sauvegarder;
	}

	public void modifierCharger(JButton Charger) {
			this.charger = Charger;
	}

	public JButton obtenirAjouterLivraison() {
			return ajouterLivraison;
	}

	public JButton obtenirSauvegarder() {
			return sauvegarder;
	}

	public JButton obtenirCharger() {
			return charger;
	}

	public Barre() {
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		setOpaque(false);
		setBorder(new CompoundBorder(new TitledBorder("Barre"), new EmptyBorder(0, 0, 0, 0)));
		
		// init les boutons
		charger = creerBouton("Charger");
		ajouterLivraison = creerBouton("Nouvelle livraison");
		sauvegarder = creerBouton("Sauvegarder");
		
		fenetreCreation = new Creation();
		fenetreCreation.init();
		

		// ajoute les composants
		JPanel panelGauche = new JPanel(), panelDroit = new JPanel();
		panelGauche.add(charger);
		panelGauche.add(ajouterLivraison);
		add(panelGauche);

		add(Box.createHorizontalGlue());
		panelDroit.add(sauvegarder);
		add(panelDroit);


		ActionListener action = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (evt.getSource() == charger) {
					System.out.println("Charger clicked");
				}

				if (evt.getSource() == ajouterLivraison) {
					System.out.println("AjouterLivraison clicked");
					fenetreCreation.ouvrir();
				}

				if (evt.getSource() == sauvegarder) {
					System.out.println("Sauvegarder clicked");
				}
			}
		};

		ajouterLivraison.addActionListener(action);
		sauvegarder.addActionListener(action);
		charger.addActionListener(action);
    }

    public JButton creerBouton(String nom) {
		JButton bouton = new JButton(nom);
		bouton.setFont(font);
		bouton.setFocusPainted(false);
		return bouton;
    }
}
