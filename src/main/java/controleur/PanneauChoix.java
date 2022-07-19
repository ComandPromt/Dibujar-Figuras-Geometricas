package controleur;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

import modele.Carre;
import modele.Cercle;
import modele.DessinModele;
import modele.FigureColoree;
import modele.Heart;
import modele.Quadrilatere;
import modele.Rectangle;
import modele.Star;
import modele.Triangle;
import net.java.dev.colorchooser.demo.CopyColor;
import vue.VueDessin;

/**
 * Classe representant un Panneau Choix
 */
public class PanneauChoix extends JPanel {

	public static CopyColor couleur;
	/**
	 * La vue dessin principale
	 */
	private VueDessin vdessin;

	/**
	 * Le dessin Modele
	 */
	private DessinModele dmodele;

	/**
	 * Le figure en cours
	 */
	private FigureColoree figureEnCours;

	/**
	 * Tableau des string des formes
	 */
	private String[] tabForme;

	/**
	 * Combo box des formes
	 */
	private JComboBox<String> formes;

	/**
	 * Couleur en cours
	 */
	private Color colorSelected;

	/**
	 * Bouttons d'effacement
	 */
	private JMenuItem effacerTout, effacerSelection;

	/**
	 * Bouton de controle de sauvegarde
	 */
	private JMenuItem sauvegarder, charger;

	/**
	 * Manipulateur de Formes
	 */
	private ManipulateurFormes manipulateurFormes;

	private JMenuItem undo, redo;

	private JMenuBar menu;

	private ButtonImage gomme;

	@SuppressWarnings("deprecation")
	public PanneauChoix(final VueDessin vdessin) throws IOException {
		this.vdessin = vdessin;
		this.colorSelected = Color.BLACK;
		dmodele = new DessinModele();
		dmodele.addObserver(vdessin);
		tabForme = new String[] { "Rectangle", "Triangle", "Quadrilatere", "Cercle", "Carre", "Heart", "Start",
				"Star 360", "Star 4p" };
		JPanel j = new JPanel();

		JPanel j2 = new JPanel();

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		final ButtonImage newFig = new ButtonImage("Nouvelle figure", "ressources/images/figures.png",
				"ressources/images/figuresSelected.png");
		final ButtonImage mainLevee = new ButtonImage("Tracé à main levée", "ressources/images/pencil.png",
				"ressources/images/pencilSelected.png");
		final ButtonImage manip = new ButtonImage("Manipulations", "ressources/images/transform.png",
				"ressources/images/transformSelected.png");

		gomme = new ButtonImage("Gomme", "ressources/images/rubber.png", "ressources/images/rubberSelected.png");

		formes = new JComboBox<>(tabForme);

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

		sauvegarder = new JMenuItem("Enregister sous ...");
		sauvegarder.setEnabled(true);
		charger = new JMenuItem("Ouvrir ...");
		charger.setEnabled(true);

		charger.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					JFileChooser fc = new JFileChooser();
					fc.setFileFilter(new FileNameExtensionFilter("IHM extension", "ihm"));
					int a = fc.showDialog(vdessin, "Set File directory");
					if (a == 0) {
						String s = fc.getSelectedFile().getAbsolutePath();
						dmodele.chargerFigures(s.endsWith(".ihm") ? s : s + ".ihm");
					}
				} catch (IOException | ClassNotFoundException e1) {
					JOptionPane.showMessageDialog(vdessin, "Aucun fichier de sauvegarde disponible");
				}
			}
		});

		sauvegarder.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});

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
				newFig.setActivated();
				mainLevee.setDeactivated();
				manip.setDeactivated();
				gomme.setDeactivated();
				formes.setEnabled(true);
				couleur.setEnabled(true);
				supFigure();
				formes.setSelectedIndex(formes.getSelectedIndex());
				effacerSelection.setEnabled(false);
				effacerTout.setEnabled(false);
			}
		});

		mainLevee.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainLevee.setActivated();
				newFig.setDeactivated();
				manip.setDeactivated();
				gomme.setDeactivated();
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
				manip.setActivated();
				newFig.setDeactivated();
				mainLevee.setDeactivated();
				gomme.setDeactivated();
				formes.setEnabled(false);
				couleur.setEnabled(true);
				effacerSelection.setEnabled(true);
				effacerTout.setEnabled(true);
				supFigure();
				manipulateurFormes = new ManipulateurFormes(dmodele);
				vdessin.ajoutManip(manipulateurFormes);
			}
		});

		gomme.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				manip.setDeactivated();
				newFig.setDeactivated();
				mainLevee.setDeactivated();
				formes.setEnabled(false);
				couleur.setEnabled(false);
				effacerSelection.setEnabled(false);
				effacerTout.setEnabled(false);
				supFigure();
				Gommeur gommeur = new Gommeur(dmodele);
				vdessin.ajoutGommmeur(gommeur);
				gomme.setActivated();
			}
		});

		formes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
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
		j2.add(formes);
		j2.add(gomme);
		j2.add(sauvegarder);
		j2.add(charger);
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
	 * Permet de save la figure
	 */
	public void save() {
		try {
			JFileChooser fc = new JFileChooser();
			fc.setFileFilter(new FileNameExtensionFilter("IHM extension", "ihm"));
			int a = fc.showDialog(vdessin, "Get File directory");
			if (a == 0) {
				String s = fc.getSelectedFile().getAbsolutePath();
				dmodele.sauvegarderFigures(s.endsWith(".ihm") ? s : s + ".ihm", dmodele.getListFigureColore());
			}
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(vdessin, "Erreur lors de la sauvegarde");
		}
	}

	/**
	 * Initialise le menu
	 */
	private void initMenu() {
		menu = new JMenuBar();
		menu.setVisible(true);
		JMenu file = new JMenu("Fichier");
		file.add(sauvegarder);
		sauvegarder.setToolTipText("Pour sauvegarder");
		sauvegarder.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
		file.add(charger);
		charger.setToolTipText("Pour Ouvrir une sauvegarde");
		charger.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
		menu.add(file);

		JMenu edit = new JMenu("Edit");
		edit.add(effacerSelection);
		effacerSelection.setToolTipText("Pour supprimer la figure selectionner");
		effacerSelection.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
		edit.add(effacerTout);
		menu.add(edit);
		effacerTout.setToolTipText("Pour tout supprimer");
		effacerTout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, InputEvent.CTRL_DOWN_MASK));
		menu.add(edit);

		JMenu aide = new JMenu("Aide");
		JMenuItem aideBut = new JMenuItem("Aide");
		aideBut.setToolTipText("Pour avoir de l'aide");
		aideBut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_DOWN_MASK));
		aideBut.addActionListener(e -> vdessin.openPDF());
		aide.add(aideBut);
		menu.add(aide);

		JMenuItem image = new JMenuItem("Exporter en image");
		image.setToolTipText("Pour avoir l' image de ce qui est dessiné");
		image.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK));
		image.addActionListener(e -> {
			JFileChooser jFileChooser = new JFileChooser("Exporter en image");
			jFileChooser.setFileFilter(new FileNameExtensionFilter("Image (.png,.jpg,.jpeg)", "png", "jpg", "jpeg"));
			int i = jFileChooser.showDialog(vdessin, "Exporter");
			if (i == 0) {
				File f = jFileChooser.getSelectedFile();
				i = JOptionPane.showConfirmDialog(vdessin,
						"Cette opération peut prendre un certain temps, voulez vous contiuer ?");

				if (i == 0) {
					vdessin.toImage(f);
				}

			}
		});
		file.add(image);

		JMenuItem changTaile = new JMenuItem("Changer la taille ...");
		changTaile.setToolTipText("Pour changer l'immage ");
		changTaile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.ALT_DOWN_MASK));
		changTaile.addActionListener(e -> {
			String r = JOptionPane.showInputDialog("Taille du dessins : (width,height)",
					vdessin.getPreferredSize().width + "," + vdessin.getPreferredSize().height);

			int w, h;
			try {
				String[] spl = r.split(",");
				w = Integer.valueOf(spl[0]);
				h = Integer.valueOf(spl[1]);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(vdessin, "Valeurs invalide");
				return;
			}
			Dimension s = new Dimension(w, h);
			Dimension min = vdessin.getMinimumSize();
			if (s.height < min.height || s.width < min.height) {
				JOptionPane.showMessageDialog(vdessin, "Taille trop petite, min " + min.width + "," + min.height);
				return;
			}
			vdessin.setPreferredSize(s);
			JOptionPane.showMessageDialog(vdessin, "Taille changée");
			vdessin.getParent().revalidate();

		});
		file.addSeparator();
		file.add(changTaile);

		edit.addSeparator();
		undo = new JMenuItem("Annuler");
		undo.setToolTipText("Annule la dernière opération");
		undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK));
		undo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dmodele.setListFigureColore(Stockage.retrieveLast(dmodele.getListFigureColore()));
				look();
			}
		});
		edit.add(undo);

		redo = new JMenuItem("Revenir");
		redo.setToolTipText("Revenir a la dernière opération");
		redo.setAccelerator(KeyStroke.getKeyStroke("control alt Z"));
		redo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dmodele.setListFigureColore(Stockage.retriveBefore(dmodele.getListFigureColore()));
				look();
			}
		});
		edit.add(redo);
		look();
		vdessin.repaint();
	}

	/**
	 * Permet de suprimer la figure plus simplement
	 */
	private void supFigure() {
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

			return new Triangle();

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

			return new Star(1);

		case 7:

			return new Star(2);

		case 8:

			return new Star(3);

		}

	}

}