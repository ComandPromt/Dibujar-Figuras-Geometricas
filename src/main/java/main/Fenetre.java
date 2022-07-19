package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import controleur.PanneauChoix;
import spinner.Spinner;
import vue.VueDessin;

public class Fenetre extends JFrame {

	private VueDessin vdessin;

	private PanneauChoix choix;

	private JTextField textField;

	private JPanel panel;

	public static Spinner cm;

	public Fenetre(String title, int longeur, int largeur) throws IOException {

		setTitle(title);

		setPreferredSize(new Dimension(longeur, largeur));

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		getContentPane().setLayout(new BorderLayout());

		setMinimumSize(new Dimension(500, 493));

		JScrollPane scrollPane = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

		addWindowListener(new WindowAdapter() {

			@Override

			public void windowClosing(WindowEvent e) {

				int confirmed = JOptionPane.showConfirmDialog(vdessin, "Voulez vous sauvegarder avant de quitter ?",
						"Voulez vous sauvegarder avant de quitter ?", JOptionPane.YES_NO_CANCEL_OPTION);

				if (confirmed == JOptionPane.YES_OPTION) {

					choix.save();

				}

				else if (confirmed == JOptionPane.CANCEL_OPTION || confirmed == JOptionPane.CLOSED_OPTION) {

					return;

				}

				dispose();

				System.exit(0);

			}

		});

		vdessin = new VueDessin();

		choix = new PanneauChoix(vdessin);

		vdessin.addMenuControler(choix);

		textField = new JTextField();

		getContentPane().add(textField, BorderLayout.SOUTH);

		textField.setColumns(10);

		scrollPane.setViewportView(vdessin);

		getContentPane().add(scrollPane, BorderLayout.CENTER);

		getContentPane().add(choix, BorderLayout.NORTH);

		panel = new JPanel();

		choix.add(panel);

		cm = new Spinner();

		panel.add(cm);

		cm.setMinValor(1);

		pack();

		setJMenuBar(choix.getMenuBar());

		setVisible(true);

	}

	public PanneauChoix getChoix() {

		return choix;

	}

	public static void main(String[] arguments) throws IOException {

		new Fenetre("Figures Geometriques", 1024, 500);

	}

}
