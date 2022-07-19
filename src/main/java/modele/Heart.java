package modele;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.List;

import controleur.FabricantFigure;
import controleur.PanneauChoix;

public class Heart extends FigureColoree {

	private boolean mainLevee;

	public Heart() {

	}

	public Heart(Color couleur, Point p1, Point p2) {

		this.couleur = couleur;

		tab_mem.add(p1);

		tab_mem.add(p2);

	}

	public Heart(Color couleur, Point p1, Point p2, boolean mainLevee) {

		this.couleur = couleur;

		tab_mem.add(p1);

		tab_mem.add(p2);

		this.mainLevee = mainLevee;

	}

	@Override

	public int nbPoints() {

		return 1;

	}

	@Override

	public int nbClics() {

		return 2;

	}

	@Override

	public void modifierPoints(List<Point> points) {

		tab_mem = points;

	}

	public void affiche(Graphics g) {

		int width = 200;

		int height = 160;

		g.setColor(PanneauChoix.couleur.getColor());

		Point p2 = tab_mem.get(1);

		int x = p2.getX();

		int y = p2.getY();

		int posicion = x;

		int[] triangleX = { (x - 2 * width / 18) + 25, (x + width + 2 * width / 18) - 27,
				((x - 2 * width / 18 + x + width + 2 * width / 18) / 2) };

		int[] triangleY = { (y + height - 2 * height / 3) + 22, (y + height - 2 * height / 3) + 22, (y + height) + 2 };

		g.fillPolygon(triangleX, triangleY, triangleX.length);

		g.fillOval(x - 20, y - 32, 120, 120);

		g.fillOval(posicion + 90, y - 32, 120, 130);

		g.setColor(Color.WHITE);

		int[] tx = { (x - 2 * width / 18) + 30, (x + width + 2 * width / 18) - 30,
				((x - 2 * width / 18 + x + width + 2 * width / 18) / 2) };

		int[] ty = { (y + height - 2 * height / 3) + 22, (y + height - 2 * height / 3) + 22, (y + height) - 3 };

		g.fillPolygon(tx, ty, triangleX.length);

		g.fillOval(x + 40, y + 25, 100, 80);

		g.fillOval(x - 15, y - 25, 110, 110);

		g.fillOval(posicion + 95, y - 25, 110, 120);

		super.affiche(g);

	}

	/**
	 * Methode permettant de savoir si la souris (MouseEvent) se situe sur un Trait
	 * et retourne un booleen De plus, si le trait est une figure Trait et non pas
	 * un trait d'un trace a main levee, la figure sera desormais selectionnee
	 *
	 * @param e
	 * @return
	 */
	@Override
	public boolean isInSelection(MouseEvent e) {

		if (!tab_mem.isEmpty()) {

			selectionne();
			return true;

		}
		return false;
	}

	/**
	 * Methode permettant de retourner l'instance qui crée un Trait
	 *
	 * @param dessinModele
	 * @return
	 */
	@Override
	public FabricantFigure getConstructeur(DessinModele dessinModele) {
		return new FabricantFigure(this, dessinModele);
	}

	@Override
	public boolean isMainLevee() {
		return mainLevee;
	}
}
