package modele;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.List;

import controleur.FabricantFigure;
import controleur.PanneauChoix;
import main.Fenetre;

public class Star extends FigureColoree {

	private boolean mainLevee;

	int tipo;

	public Star(int type) {

		this.tipo = type;

	}

	public Star(Color couleur, Point p1, Point p2) {

		this.couleur = couleur;

		tab_mem.add(p1);

		tab_mem.add(p2);

	}

	public Star(Color couleur, Point p1, Point p2, boolean mainLevee) {

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

		Point p2;

		int centerX;

		int centerY;

		switch (this.tipo) {

		default:

		case 1:

			p2 = tab_mem.get(1);

			centerX = p2.getX();

			centerY = p2.getY();

			double radius = 90 * Fenetre.cm.getValor();

			int STAR_POINTS = 10;

			int[] polygonX = new int[STAR_POINTS];

			int[] polygonY = new int[STAR_POINTS];

			double innerRadius = radius * Math.sin(Math.toRadians(18) / Math.sin(Math.toRadians(54)));

			for (int i = 18; i < 360; i += 72) {
				polygonX[(i - 18) / 36] = centerX + (int) (radius * Math.cos(Math.toRadians(i)));
				polygonY[(i - 18) / 36] = centerY - (int) (radius * Math.sin(Math.toRadians(i)));
			}

			for (int i = 54; i < 360; i += 72) {

				polygonX[(i - 18) / 36] = centerX + (int) (innerRadius * Math.cos(Math.toRadians(i)));

				polygonY[(i - 18) / 36] = centerY - (int) (innerRadius * Math.sin(Math.toRadians(i)));

			}

			g.setColor(PanneauChoix.couleur.getColor());

			g.drawPolygon(polygonX, polygonY, STAR_POINTS);

			break;

		case 2:

			p2 = tab_mem.get(1);

			centerX = p2.getX();

			centerY = p2.getY();

			int[] xPoints = { 55, 67, 109, 73, 83, 55, 27, 37, 1, 43 };

			int[] yPoints = { 0, 36, 36, 54, 96, 72, 96, 54, 36, 36 };

			Graphics2D g2d = (Graphics2D) g;

			GeneralPath star = new GeneralPath();

			star.moveTo(xPoints[0], yPoints[0]);

			for (int count = 1; count < xPoints.length; count++)
				star.lineTo(xPoints[count], yPoints[count]);

			star.closePath();

			g2d.translate(centerX, centerY);

			for (int count = 1; count <= 20; count++) {

				g2d.rotate(Math.PI / 10.0);

				g2d.setColor(PanneauChoix.couleur.getColor());

				g2d.draw(star);

			}

			break;
		case 3:
			Graphics2D g2D = (Graphics2D) g;
			Point2D.Float point = new Point2D.Float(100, 100); // store start point
			GeneralPath p = new GeneralPath(GeneralPath.WIND_NON_ZERO);
			p.moveTo(point.x, point.y);
			p.lineTo(point.x + 20.0f, point.y - 5.0f); // Line from start to A
			point = (Point2D.Float) p.getCurrentPoint();
			p.lineTo(point.x + 5.0f, point.y - 20.0f); // Line from A to B
			point = (Point2D.Float) p.getCurrentPoint();
			p.lineTo(point.x + 5.0f, point.y + 20.0f); // Line from B to C
			point = (Point2D.Float) p.getCurrentPoint();
			p.lineTo(point.x + 20.0f, point.y + 5.0f); // Line from C to D
			point = (Point2D.Float) p.getCurrentPoint();
			p.lineTo(point.x - 20.0f, point.y + 5.0f); // Line from D to E
			point = (Point2D.Float) p.getCurrentPoint();
			p.lineTo(point.x - 5.0f, point.y + 20.0f); // Line from E to F
			point = (Point2D.Float) p.getCurrentPoint();
			p.lineTo(point.x - 5.0f, point.y - 20.0f); // Line from F to g
			p.closePath(); // Line from G to start
			g2D.draw(p);
			break;
		}

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
