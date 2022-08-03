package modele;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.List;

import controleur.FabricantFigure;
import controleur.PanneauChoix;
import main.Fenetre;

public class Star extends FigureColoree {

	private boolean mainLevee;

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

	public Star(int type) {

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

		int centerX;

		int centerY;

		p2 = tab_mem.get(1);

		Color color = PanneauChoix.couleur.getColor();

		centerX = p2.getX();

		centerY = p2.getY();

		Graphics2D g2d = (Graphics2D) g;

		g.translate(0, 0);

		g2d.translate(0, 0);

		switch (this.tipo) {

		case 1:

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

			g.setColor(color);

			g.drawPolygon(polygonX, polygonY, STAR_POINTS);

			break;

		case 3:
			if (!star360) {
				g2d.translate(centerX, centerY);

				int x = 56;

				int y = 0;

				int[] xPoints = { x, x + 12, x + 54, x + 18, x + 28, x, x - 28, x - 18, x - 54, x - 12 };
//
				int[] yPoints = { y, y + 36, y + 36, y + 54, y + 96, y + 72, y + 96, y + 54, y + 36, y + 36 };

//			int[] xPoints = { x, x + 12, x + 42, x - 36, x + 10, x - 28, x - 28, x + 10, x - 36, x + 42 };
//
//			int[] yPoints = { y, y + 36, y - 36, y + 18, y + 42, y - 24, y + 24, y - 42, y - 18, y - 36 };

//			int[] xPoints = { 55, 67, 109, 73, 83, 55, 27, 37, 1, 43 };
//
//			int[] yPoints = { 0, 36, 36, 54, 96, 72, 96, 54, 36, 36 };

				GeneralPath star = new GeneralPath();

				star.moveTo(xPoints[0], yPoints[0]);

				for (int count = 1; count < xPoints.length; count++)
					star.lineTo(xPoints[count], yPoints[count]);

				star.closePath();

				for (int count = 1; count <= 20; count++) {

					g2d.rotate(Math.PI / 10.0);

					g2d.setColor(PanneauChoix.couleur.getColor());

					g2d.draw(star);

				}

				g2d.translate(0, 0);

				star360 = true;

			}

			break;

		case 0:

			float witdh = (float) 75 * Fenetre.cm.getValor();

			float height = witdh - (float) 50 * Fenetre.cm.getValor();

			Point2D.Float point = new Point2D.Float(p2.getX(), p2.getY());

			GeneralPath p = new GeneralPath(GeneralPath.WIND_NON_ZERO);

			point.x = centerX - (100 * Fenetre.cm.getValor());

			p.moveTo(point.x, point.y);

			p.lineTo(point.x + witdh, point.y - height);

			point = (Point2D.Float) p.getCurrentPoint();

			p.lineTo(point.x + height, point.y - witdh);

			point = (Point2D.Float) p.getCurrentPoint();

			p.lineTo(point.x + height, point.y + witdh);

			point = (Point2D.Float) p.getCurrentPoint();

			p.lineTo(point.x + witdh, point.y + height);

			point = (Point2D.Float) p.getCurrentPoint();

			p.lineTo(point.x - witdh, point.y + height);

			point = (Point2D.Float) p.getCurrentPoint();

			p.lineTo(point.x - height, point.y + witdh);

			point = (Point2D.Float) p.getCurrentPoint();

			p.lineTo(point.x - height, point.y - witdh);

			p.closePath();

			g2d.draw(p);

			g2d.translate(0, 0);

			break;

		case 2:

			g.translate(centerX, centerY);

			int a = 100;

			int incremento = a / 2;

			int s = a + incremento;

			int d = s + incremento;

			int[] wx = { a, s, d };

			int[] wy = { a, d, a };

			int dividir = s / 2;

			a += dividir;

			int[] yPoints2 = { a, dividir, a };

			g.drawPolygon(wx, wy, 3);

//			int[] wxA = { centerX, 10, 10 };
//			int[] yPoints2A = { centerY, 10, 10 };

			g.drawPolygon(wx, yPoints2, 3);

			break;

		case 4:

			if (PanneauChoix.thin.isSelected()) {

				g2d.setPaint(new RadialGradientPaint(new Point2D.Double(400, 200), 60, new float[] { 0, 1 },
						new Color[] { color, color }));

				pintarEstrella(g2d, 20, 60, 8, 0);
			}

			else {

				g2d.setPaint(new RadialGradientPaint(new Point2D.Double(200, 400), 50, new float[] { 0, 0.3f, 1 },
						new Color[] { color, color, color }));

				pintarEstrella(g2d, 40, 50, 20, 0);

			}

			break;

		}

		super.affiche(g);

	}

	private void pintarEstrella(Graphics2D g, int i, int j, int k, int l) {

		if (!Fenetre.defaultShape.isSelected() && Fenetre.r1.getValor() > 1 && Fenetre.r2.getValor() > 1
				&& Fenetre.tip.getValor() > 2) {

			g.draw(createStar(p2.getX(), p2.getY(), Fenetre.r1.getValor(), Fenetre.r2.getValor(),
					Fenetre.tip.getValor(), 0));

		}

		else {

			Fenetre.r1.setValor(i);

			Fenetre.r2.setValor(j);

			Fenetre.tip.setValor(k);

			g.draw(createStar(p2.getX(), p2.getY(), i, j, k, l));

		}

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
