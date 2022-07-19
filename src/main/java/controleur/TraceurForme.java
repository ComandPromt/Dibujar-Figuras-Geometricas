package controleur;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import main.Fenetre;
import modele.DessinModele;
import modele.Point;
import modele.Heart;
import vue.VueDessin;

public class TraceurForme implements MouseMotionListener, MouseListener {

	/**
	 * Dessin Modele du Traceur de Forme
	 */
	private DessinModele dsm;

	/**
	 * Panneau Choix du traceur de forme
	 */
	private PanneauChoix panneauChoix;

	/**
	 * Abscisse du dernier point enregistre
	 */
	private int lastX;

	/**
	 * Ordonnee du dernier point enregistre
	 */
	private int lastY;

	/**
	 * Liste de traits du traceur
	 */
	private List<Heart> lTrait;

	/**
	 * Constructeur de la classe TraceurForme
	 *
	 * @param dessinModele Dessin modele du traceur de forme
	 */
	public TraceurForme(DessinModele dessinModele) {
		this.dsm = dessinModele;
		this.lTrait = new ArrayList<>();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e)) {
			Heart trait = new Heart(panneauChoix.getCouleur(), new Point(0, 0), new Point(e.getX(), e.getY()), true);
			lTrait.add(trait);
			dsm.getListFigureColore().add(trait);
			lastX = e.getPoint().x;
			lastY = e.getPoint().y;
			dsm.update();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		lastX = e.getX();
		lastY = e.getY();
		if (panneauChoix == null) {
			panneauChoix = ((Fenetre) SwingUtilities.getWindowAncestor((VueDessin) e.getSource())).getChoix();
		}
		Stockage.addNewSauvegarde(dsm.getListFigureColore());
	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}
}
