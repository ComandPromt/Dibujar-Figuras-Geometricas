package controleur;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Iterator;

import modele.DessinModele;
import modele.FigureColoree;

public class Gommeur implements MouseListener, MouseMotionListener {

	private DessinModele dessinModele;

	public Gommeur(DessinModele dm) {

		this.dessinModele = dm;

	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {

		Stockage.addNewSauvegarde(dessinModele.getListFigureColore());

		Iterator<FigureColoree> fgI = this.dessinModele.getListFigureColore().iterator();

		int indice = 1;

		int hasta = this.dessinModele.getListFigureColore().size();

		hasta--;

		while (fgI.hasNext()) {

			FigureColoree figureColoree = fgI.next();

			if (indice == hasta) {

				if (!figureColoree.isMainLevee()) {
					Stockage.addNewSauvegarde(dessinModele.getListFigureColore());
				}

				fgI.remove();

				this.dessinModele.update();

			}

			indice++;

		}

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

	@Override
	public void mouseDragged(MouseEvent e) {

	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}
}
