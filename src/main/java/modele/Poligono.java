
package modele;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.util.List;

import controleur.FabricantFigure;
import controleur.PanneauChoix;
import main.Fenetre;

public class Poligono extends FigureColoree {

	private boolean mainLevee;

	int tipo;

	Point p2;

	boolean star360 = false;

	public Poligono(int type) {

		this.tipo = type;

	}

	public Poligono(Color couleur, Point p1, Point p2) {

		this.couleur = couleur;

		tab_mem.add(p1);

		tab_mem.add(p2);

	}

	public Poligono(Color couleur, Point p1, Point p2, boolean mainLevee) {

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

	int conteo = 0;

	public void affiche(Graphics g) {

		p2 = tab_mem.get(1);

		Color color = PanneauChoix.couleur.getColor();

		Polygon p = new Polygon();

		int lados = Fenetre.numberOfSides.getValor();

		for (int i = 0; i < lados; i++) {

			p.addPoint((int) (p2.getX() + 50 * Math.cos(i * 2 * Math.PI / lados)),
					(int) (p2.getY() + 50 * Math.sin(i * 2 * Math.PI / lados)));

		}

		g.drawPolygon(p);

		g.setColor(color);

		super.affiche(g);

	}

	@Override

	public boolean isInSelection(MouseEvent e) {

		if (!tab_mem.isEmpty()) {

			selectionne();

			return true;

		}

		return false;

	}

	@Override

	public FabricantFigure getConstructeur(DessinModele dessinModele) {

		return new FabricantFigure(this, dessinModele);

	}

	@Override

	public boolean isMainLevee() {

		return mainLevee;

	}

}
