package modele;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.List;

import controleur.FabricantFigure;
import controleur.PanneauChoix;

public class Triangulo extends FigureColoree {

	private boolean mainLevee;

	int tipo;

	Point p2;

	public Triangulo(int type) {

		this.tipo = type;

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

		Color color = PanneauChoix.couleur.getColor();

		p2 = tab_mem.get(1);

		int centerX = p2.getX();

		int centerY = p2.getY();

		int[] polygonX = new int[3];

		int[] polygonY = new int[3];

		int alturaX = 50;

		int alturaY = 100;

		g.translate(0, 0);

		switch (this.tipo) {

		case 0:

			centerX += 20;

			g.setColor(color);

			polygonX[0] = centerX;

			polygonX[1] = centerX;

			polygonX[2] = centerX - alturaX;

			polygonY[0] = centerY - alturaX;

			polygonY[1] = centerY + alturaX;

			polygonY[2] = centerY + alturaX;

			g.drawPolygon(polygonX, polygonY, 3);

			break;

		case 1:

			int[] x = { centerX, centerX + alturaY, centerX - alturaY };

			int[] y = { centerY - alturaY, centerY + alturaY, centerY + alturaY };

			g.drawPolygon(x, y, 3);

			break;

		case 2:

			int[] xx = { centerX + 10, (centerX + alturaY) + 20, (centerX - alturaY) + 30 };

			int[] yx = { (centerY - alturaY) + 100, (centerY + alturaY) + 20, (centerY + alturaY) + 100 };

			g.drawPolygon(xx, yx, 3);

			break;

		}

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
