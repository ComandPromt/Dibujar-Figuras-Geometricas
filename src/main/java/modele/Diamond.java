package modele;

import java.awt.geom.Path2D;

public class Diamond extends Path2D.Double {

	public Diamond(double width, double height) {

		moveTo(0, height / 2);
		lineTo(width / 2, 0);
		lineTo(width, height / 2);
		lineTo(width / 2, height);
		closePath();
	}

}