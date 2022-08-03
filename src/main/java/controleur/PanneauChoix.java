package controleur;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import combo_suggestion.ComboBoxSuggestion;
import modele.Carre;
import modele.Cercle;
import modele.DessinModele;
import modele.FigureColoree;
import modele.Heart;
import modele.Poligono;
import modele.Quadrilatere;
import modele.Rectangle;
import modele.Star;
import modele.Triangle;
import modele.Triangulo;
import net.java.dev.colorchooser.demo.CopyColor;
import radio_button.RadioButtonCustom;
import vue.VueDessin;

public class PanneauChoix extends JPanel {

	public static CopyColor couleur;

	private static VueDessin vdessin;

	public static DessinModele dmodele;

	private static FigureColoree figureEnCours;

	private String[] tabForme;

	public static ComboBoxSuggestion<String> formes;

	private Color colorSelected;

	private static JMenuItem effacerTout;

	private static JMenuItem effacerSelection;

	private static ManipulateurFormes manipulateurFormes;

	private JMenuItem undo, redo;

	private JMenuBar menu;

	private static RadioButtonCustom gomme;

	private ComboBoxSuggestion<String> formas;

	private JLabel form;

	public static RadioButtonCustom thin;

	public static RadioButtonCustom strong;

	private JLabel lblNewLabel;

	public static RadioButtonCustom newFig;

	public static RadioButtonCustom mainLevee;

	public static RadioButtonCustom manip;

	private void limpiar() {

		formas.removeAllItems();

	}

	@SuppressWarnings("deprecation")

	public static void modificar() {
		manip.setSelected(true);
		newFig.setSelected(false);
		mainLevee.setSelected(false);
		gomme.setSelected(false);
		formes.setEnabled(false);
		couleur.setEnabled(true);
		effacerSelection.setEnabled(true);
		effacerTout.setEnabled(true);
		supFigure();
		manipulateurFormes = new ManipulateurFormes(dmodele);
		vdessin.ajoutManip(manipulateurFormes);
	}

	public PanneauChoix(final VueDessin vdessin) throws IOException {

		newFig = new RadioButtonCustom("Nouvelle figure");

		mainLevee = new RadioButtonCustom("Tracé à main levée");

		manip = new RadioButtonCustom("Manipulations");

		this.vdessin = vdessin;

		this.colorSelected = Color.BLACK;

		dmodele = new DessinModele();

		dmodele.addObserver(vdessin);

		tabForme = new String[] { "Rectangle", "Triangle", "Quadrilatere", "Cercle", "Carre", "Heart", "Star",
				"Poligone" };

		JPanel j = new JPanel();
		j.setBackground(Color.WHITE);

		JPanel j2 = new JPanel();
		j2.setBackground(Color.WHITE);

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		gomme = new RadioButtonCustom("Gomme");

		formes = new ComboBoxSuggestion<>();

		formes.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {

				switch (formes.getSelectedItem().toString()) {

				case "Star":

					limpiar();

					formas.addItem("Star 4p");

					formas.addItem("Clasic 5p");

					formas.addItem("David's Star");

					formas.addItem("Star 360 º");

					formas.addItem("Custom Start");

					break;

				case "Triangle":

					limpiar();

					formas.addItem("Rectangle");

					formas.addItem("Equilatero");

					formas.addItem("Isosceles");

					formas.addItem("Free");

					break;

				}

			}

		});

		for (int i = 0; i < tabForme.length; i++) {

			formes.addItem(tabForme[i]);

		}

		couleur = new CopyColor(colorSelected, false);

		couleur.setBackground(this.colorSelected);

		couleur.setPreferredSize(new Dimension(25, 25));

		ButtonGroup b = new ButtonGroup();

		b.add(newFig);

		b.add(mainLevee);

		b.add(manip);

		b.add(gomme);

		effacerSelection = new JMenuItem("Effacer");

		effacerSelection.setEnabled(false);

		effacerSelection.addActionListener(new ActionListener() {

			@Override

			public void actionPerformed(ActionEvent e) {

				if (manipulateurFormes != null) {

					Stockage.addNewSauvegarde(dmodele.getListFigureColore());

					dmodele.delFigureColoree(manipulateurFormes.figureSelection());

					dmodele.update();

				}

			}

		});

		effacerTout = new JMenuItem("Effacer Tout");

		effacerTout.setEnabled(false);

		effacerTout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Stockage.addNewSauvegarde(dmodele.getListFigureColore());
				dmodele.getListFigureColore().clear();
				dmodele.update();
			}
		});

		newFig.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				newFig.setSelected(true);
				mainLevee.setSelected(false);
				manip.setSelected(false);
				gomme.setSelected(false);
				formes.setEnabled(true);
				couleur.setEnabled(true);
				supFigure();
				formes.setSelectedIndex(formes.getSelectedIndex());

			}
		});

		mainLevee.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainLevee.setSelected(true);
				newFig.setSelected(false);
				manip.setSelected(false);
				gomme.setSelected(false);
				formes.setEnabled(false);
				couleur.setEnabled(true);
				supFigure();
				effacerSelection.setEnabled(false);
				effacerTout.setEnabled(false);
				TraceurForme traceurForme = new TraceurForme(dmodele);
				vdessin.ajoutTraceur(traceurForme);
			}
		});

		manip.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				modificar();
			}

		});

		gomme.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				manip.setSelected(false);
				newFig.setSelected(false);
				mainLevee.setSelected(false);
				formes.setEnabled(false);
				couleur.setEnabled(false);
				effacerSelection.setEnabled(false);
				effacerTout.setEnabled(false);
				supFigure();
				Gommeur gommeur = new Gommeur(dmodele);
				vdessin.ajoutGommmeur(gommeur);
				gomme.setSelected(true);

			}
		});

		formes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {

					// contador++;

					vdessin.enleverListeners();
					figureEnCours = creeFigure(formes.getSelectedIndex());
					figureEnCours.changeCouleur(colorSelected);
					vdessin.createFigure(figureEnCours);
				} catch (Exception e1) {
				}

			}
		});
		j.add(newFig);
		j.add(mainLevee);
		j.add(manip);
		this.add(j);
		j2.add(effacerTout);
		j2.add(effacerSelection);
		j2.add(couleur);

		lblNewLabel = new JLabel("");
		// lblNewLabel.setIcon(new
		// ImageIcon(PanneauChoix.class.getResource("/images/star-1.png")));
		j2.add(lblNewLabel);

		form = new JLabel("");
		j2.add(form);
		j2.add(formes);

		formas = new ComboBoxSuggestion();

		j2.add(formas);

		thin = new RadioButtonCustom("Thin");
		j2.add(thin);

		strong = new RadioButtonCustom("Strong");
		j2.add(strong);
		j2.add(gomme);

		this.add(j2);

	}

	public JMenuBar getMenuBar() {
		if (menu == null) {
			initMenu();
		}
		return menu;
	}

	/**
	 * Permet de reselectioner l'objet a crée
	 */
	public void reCreateObject() {
		vdessin.enleverListeners();
		formes.setSelectedIndex(formes.getSelectedIndex());
	}

	/**
	 * @return la couleur selectoner
	 */
	public Color getCouleur() {
		return colorSelected;
	}

	/**
	 * Permet d'update les boutons
	 */
	public void look() {
		redo.setEnabled(!Stockage.redoEmpty());
		undo.setEnabled(!Stockage.undoEmpty());
	}

	/**
	 * Initialise le menu
	 */
	private void initMenu() {
		menu = new JMenuBar();
		menu.setVisible(true);
		look();
		vdessin.repaint();
	}

	/**
	 * Permet de suprimer la figure plus simplement
	 */
	private static void supFigure() {
		if (figureEnCours != null) {
			figureEnCours = null;
		}
		vdessin.enleverListeners();
		if (manipulateurFormes != null) {

			if (manipulateurFormes.figureSelection() != null) {
				manipulateurFormes.figureSelection().deSelectionne();
			}
			manipulateurFormes = null;
		}
		dmodele.update();
	}

	/**
	 * Permet de crée rapidement la figure
	 *
	 * @param nb la selection de la forme
	 * @return La figure corespondant a la figure
	 */
	private FigureColoree creeFigure(int nb) {

		switch (nb) {

		case 0:

			return new Rectangle();

		case 1:

			if (formas.getSelectedIndex() == 3) {

				return new Triangle();

			} else {
				return new Triangulo(formas.getSelectedIndex());
			}
		case 2:

			return new Quadrilatere();

		case 3:

			return new Cercle();

		default:

		case 4:

			return new Carre();

		case 5:

			return new Heart();

		case 6:

			return new Star(formas.getSelectedIndex());

		case 7:

			return new Poligono(1);

		}

	}

}