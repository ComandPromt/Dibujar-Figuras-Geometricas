package main;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.filechooser.FileNameExtensionFilter;

import controleur.PanneauChoix;
import controleur.Stockage;
import radio_button.RadioButtonCustom;
import spinner.Spinner;
import vue.VueDessin;

@SuppressWarnings("all")

public class Fenetre extends javax.swing.JFrame {

	public static Spinner cm;

	VueDessin vdessin;

	PanneauChoix choix;

	private JMenuBar menuBar;

	private JMenuItem mntmNewMenuItem;

	public static Spinner r1;

	public static Spinner r2;

	public static Spinner tip;

	public static RadioButtonCustom defaultShape;

	public static Spinner numberOfSides;

	public Fenetre() throws IOException {

		setAlwaysOnTop(true);

		setTitle("Test");

		menuBar = new JMenuBar();

		setJMenuBar(menuBar);

		mntmNewMenuItem = new JMenuItem("Save");
		mntmNewMenuItem.setIcon(new ImageIcon(Fenetre.class.getResource("/images/save.png")));
		menuBar.add(mntmNewMenuItem);

		mntmNewMenuItem.addMouseListener(new MouseAdapter() {

			@Override

			public void mousePressed(MouseEvent e) {

				JFileChooser jFileChooser = new JFileChooser("Exporter en image");

				jFileChooser
						.setFileFilter(new FileNameExtensionFilter("Image (.png,.jpg,.jpeg)", "png", "jpg", "jpeg"));

				int i = jFileChooser.showDialog(vdessin, "Exporter");

				if (i == 0) {

					File f = jFileChooser.getSelectedFile();

					i = JOptionPane.showConfirmDialog(vdessin,
							"Cette opération peut prendre un certain temps, voulez vous contiuer ?");

					if (i == 0) {

						vdessin.toImage(f);

					}

				}

			}

		});

		initComponents();

		this.setVisible(true);

	}

	public static void main(String[] args) {

		try {

			new Fenetre().setVisible(true);

		}

		catch (Exception e) {

		}

	}

	public PanneauChoix getChoix() {

		return choix;

	}

	public void initComponents() throws IOException {

		vdessin = new VueDessin();

		choix = new PanneauChoix(vdessin);

		choix.thin.setSelected(true);

		vdessin.addKeyListener(new KeyAdapter() {

			@Override

			public void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_Z) {

					choix.dmodele.setListFigureColore(Stockage.retriveBefore(choix.dmodele.getListFigureColore()));

					choix.look();

				}

				if (e.getKeyCode() == KeyEvent.VK_Y) {

					choix.dmodele.setListFigureColore(Stockage.retrieveLast(choix.dmodele.getListFigureColore()));

					choix.look();

				}

			}

		});

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

		setResizable(false);

		JScrollPane scrollPane = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

		cm = new Spinner();

		cm.setMinValor(1);

		cm.setLabelText("Cm");

		r1 = new Spinner();

		r1.setLabelText("Radius 1");

		r1.setMinValor(1);

		r2 = new Spinner();

		r2.setLabelText("Radius 2");

		r2.setMinValor(1);

		tip = new Spinner();

		tip.setLabelText("Number of star tips");

		tip.setMinValor(1);

		defaultShape = new RadioButtonCustom("Default");

		numberOfSides = new Spinner();
		numberOfSides.setMaxValor(20);

		numberOfSides.setMinValor(3);

		numberOfSides.setLabelText("Number of sides");

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());

		layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addGap(18)
						.addComponent(cm, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(r1, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(r2, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(tip, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(numberOfSides, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(defaultShape, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addGap(735))
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addComponent(choix, GroupLayout.DEFAULT_SIZE, 1308, Short.MAX_VALUE).addContainerGap())
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 1250, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(68, Short.MAX_VALUE)));

		layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup()
				.addContainerGap()
				.addComponent(choix, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				.addGap(18)
				.addGroup(layout.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(defaultShape, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(cm, GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
						.addComponent(r1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(r2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(tip, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(numberOfSides, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE))
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 724, GroupLayout.PREFERRED_SIZE).addGap(25))

		);

		scrollPane.setViewportView(vdessin);

		getContentPane().setLayout(layout);

		setSize(new Dimension(1379, 980));

		setLocationRelativeTo(null);

	}

	public void actionPerformed(ActionEvent arg0) {

	}

	public void stateChanged(ChangeEvent e) {

	}

}
