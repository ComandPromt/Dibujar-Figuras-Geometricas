package modele;

import java.awt.Graphics;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.geom.Path2D;
import java.util.List;

import controleur.FabricantFigure;
import controleur.PanneauChoix;
import main.Fenetre;

public class Cercle extends FigureColoree {

	private boolean mainLevee;

	public static Graphics dibujo;

	int tipo;

	Point p2;

	boolean star360 = false;

	private static Shape createStar(double centerX, double centerY, double innerRadius, double outerRadius, int numRays,
			double startAngleRad) {
		Path2D path = new Path2D.Double();
		try {
			double deltaAngleRad = Math.PI / numRays;
			for (int i = 0; i < numRays * 2; i++) {
				double angleRad = startAngleRad + i * deltaAngleRad;
				double ca = Math.cos(angleRad);
				double sa = Math.sin(angleRad);
				double relX = ca;
				double relY = sa;
				if ((i & 1) == 0) {
					relX *= outerRadius;
					relY *= outerRadius;
				} else {
					relX *= innerRadius;
					relY *= innerRadius;
				}
				if (i == 0) {
					path.moveTo(centerX + relX, centerY + relY);
				} else {
					path.lineTo(centerX + relX, centerY + relY);
				}
			}

			path.closePath();

		}

		catch (Exception e) {

		}

		return path;
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

		g.setColor(PanneauChoix.couleur.getColor());

		g.drawOval(p2.getX() - 2, p2.getY(), Fenetre.cm.getValor() * 168, Fenetre.cm.getValor() * 168);

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
