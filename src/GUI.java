import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.Border;

public class GUI extends JFrame {
	JTextField dialoogiPaneel = new JTextField();
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Nupp[][] mangulauaNupud = new Nupp[15][15];

	static Nupp nupp;
	private static String selectedNupp;
	Nupp[] kasutajaChar = new Nupp[7];
	Nupp button1 = null;
	int InimeseSkoor = 0;
	int VastaseSkoor = 0;
	protected Color v2rv;

	public GUI(final String[][] game) {
		super("Eestikeelne Scrabble");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Loon vajalikud paneelid
		JPanel raamiPaneel = new JPanel();
		this.setMinimumSize(new Dimension(750, 525));
		raamiPaneel.setLayout(new BorderLayout());
		JPanel lauaPaneel = new JPanel();
		lauaPaneel.setLayout(new GridLayout(15, 15));
		JPanel kontrollPaneel = new JPanel();
		kontrollPaneel.setLayout(new FlowLayout(FlowLayout.LEADING, 20, 20));
		lauaPaneel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		dialoogiPaneel.setEditable(false);
		Dimension d = new Dimension(220, 100);
		dialoogiPaneel.setPreferredSize(d);

		for (int i = 0; i < game.length; i++) {
			for (int j = 0; j < game.length; j++) {
				if (game[i][j].length() == 1 && !game[i][j].equals(" ")) {
					nupp = new Nupp("<html>" + game[i][j].toUpperCase()
							+ "<sub>" + Tahed.vaartus(game[i][j].charAt(0))
							+ "</sub></html>", i, j);
					nupp.setBackground(Color.WHITE);
				} else if (game[i][j].equals("3xs")) {
					nupp = new Nupp("<html><i>3*s</i></html>", i, j);
					nupp.setBackground(Color.RED);
				} else if (game[i][j].equals("2xs")) {
					nupp = new Nupp("<html><i>2*s</i></html>", i, j);
					nupp.setBackground(Color.ORANGE);
				} else if (game[i][j].equals("3xt")) {
					nupp = new Nupp("<html><i>3*t</i></html>", i, j);
					Color minusinine = new Color(50, 150, 250);
					nupp.setBackground(minusinine);
				} else if (game[i][j].equals("2xt")) {
					nupp = new Nupp("<html><i>2*t</i></html>", i, j);
					nupp.setBackground(Color.CYAN);
				} else if (game[i][j].equals(" ")) {
					nupp = new Nupp("", i, j);
					Color minuroheline = new Color(20, 200, 140);
					nupp.setBackground(minuroheline);
				} 
				// nupp.setPreferredSize(new Dimension(30, 30));
				nupp.setMargin(new Insets(0, 0, 0, 0));
				nupp.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						Nupp button = (Nupp) e.getSource();

						if (button1 != null) {
							button1.setBackground(v2rv);
						}
						v2rv = button.getBackground();

						Color aktiivne = new Color(30, 100, 60);
						button.setBackground(aktiivne);

						button1 = button;

						selectedNupp = button.xKoordinaat + " "
								+ button.yKoordinaat;
					}
				});
				mangulauaNupud[i][j] = nupp;
				lauaPaneel.add(nupp);
			}
		}

		// Loon komponente, mis saavad olema kontrollpaneelis mängulaua kõrval
		JLabel tahedLeibel = new JLabel("Sinu tähed on:");
		JPanel tahedPaneel = new JPanel();
		tahedPaneel.setLayout(new GridLayout(1, 7));
		// nii peaks hiljem olema: for (int i = 0; i < Mang.TahedInimene.length;
		// i++){
		for (int i = 0; i < Mang.TahedInimene.length; i++) {
			nupp = new Nupp("<html>"
					+ Character.toUpperCase(Mang.TahedInimene[i]) + "<sub>"
					+ Tahed.vaartus(Mang.TahedInimene[i]) + "</sub></html>",
					-1, -1);
			nupp.setBackground(Color.WHITE);
			nupp.setPreferredSize(new Dimension(30, 30));
			nupp.setMargin(new Insets(0, 0, 0, 0));

			kasutajaChar[i] = nupp;
			tahedPaneel.add(nupp);
		}

		final JLabel skoorileibel = new JLabel("<html>Sinu skoor: "
				+ InimeseSkoor + "<br>Vastase skoor: " + VastaseSkoor
				+ "</html>");
		final JRadioButton paremale = new JRadioButton("Paremale");
		final JRadioButton alla = new JRadioButton("Alla");
		ButtonGroup suund = new ButtonGroup();
		suund.add(paremale);
		suund.add(alla);
		JPanel suunaPaneel = new JPanel();
		Border suunaBorder = BorderFactory.createTitledBorder("Suund");
		suunaPaneel.setBorder(suunaBorder);
		suunaPaneel.add(paremale);
		suunaPaneel.add(alla);
		paremale.setSelected(true);
		final JTextField sisestatudSõna = new JTextField("Sisesta sõna siia",
				15);
		JLabel juhendLeibel = new JLabel(
				"<html>Vali sõna algusruut mängu-<br>väljal, sisesta sõna ja vali, <br>kas see kulgeb suunaga <br>paremale või alla</html>");
		JButton pakkumiseNupp = new JButton("Sõna lauale!");

		// panen komponendid paneelidele ja alampaneelid ülempaneelidele
		raamiPaneel.add(lauaPaneel);
		kontrollPaneel.add(skoorileibel);
		kontrollPaneel.add(tahedLeibel);
		kontrollPaneel.add(tahedPaneel);
		kontrollPaneel.add(juhendLeibel);
		kontrollPaneel.add(sisestatudSõna);
		kontrollPaneel.add(suunaPaneel);
		kontrollPaneel.add(pakkumiseNupp);
		kontrollPaneel.add(dialoogiPaneel);

		pakkumiseNupp.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String query = "";
				if (selectedNupp != null) {
					query += selectedNupp;
					selectedNupp = null;
					if (paremale.isSelected()) {
						query += " p ";
					} else if (alla.isSelected()) {
						query += " a ";
					}
					String enteredWord = sisestatudSõna.getText();
					if (!enteredWord.equals("Sisesta sõna siia")
							&& enteredWord != "") {
						query += enteredWord;
						dialoogiPaneel.setText(query);
						if (Laud_input_kontroll.kontroll(query)) {
							Mang.TahtedeHaldamine(query);
							InimeseSkoor += Mang.punktid;
							Mang.InimeseSkoor += Mang.punktid;
							uuendaTabelit(
									Integer.parseInt(query.split(" ")[0]),
									Integer.parseInt(query.split(" ")[1]),
									query.split(" ")[2], enteredWord);
							if (Tahed2.kott(Mang.TaheKott) != '\0') {
								for (char taht : Mang.kasutatudTahed) {
									try {
										Mang.TahedInimene[new String(
												Mang.TahedInimene)
												.indexOf(taht)] = Tahed2
												.kott(Mang.TaheKott);
									} catch (Exception e1) {
										// täht laual olemas
									}
								}
								Mang.punktid = 0;

								dialoogiPaneel.setText("Vastase tähed on "
										+ Character
												.toUpperCase(Mang.TahedAI[0])
										+ Tahed.vaartus(Mang.TahedAI[0]));
								for (int i = 1; i < Mang.TahedAI.length; i++) {
									dialoogiPaneel.setText(", "
											+ Character
													.toUpperCase(Mang.TahedAI[i])
											+ Tahed.vaartus(Mang.TahedAI[i]));
								}
								Mang.kasutatudTahed.clear();
								try {
									String sisend = AI
											.SkaneeriLauda(Mang.MangulaudMassiiv);
									System.out.println(sisend);
									if (sisend.equals("jääb vahele")) {
										dialoogiPaneel
												.setText("Vastane jättis käigu vahele. Sinu kord.");
									} else {
										Mang.TahtedeHaldamine(sisend);
										dialoogiPaneel
												.setText("Vastase sõna on "
														+ Mang.inputObjekt.sõne_ise);
										System.out.println("Vastase sõna on "
												+ Mang.inputObjekt.sõne_ise);
										uuendaTabelit(Mang.inputObjekt.tulp,
												Mang.inputObjekt.rida,
												Mang.inputObjekt.p_v_a,
												Mang.inputObjekt.sõne_ise);
										Mang.AISkoor += Mang.punktid;
										VastaseSkoor += Mang.punktid;
									}

									dialoogiPaneel.setText("Vastane sai "
											+ Mang.punktid + " punkti!");
									if (Tahed2.kott(Mang.TaheKott) != '\0') {
										if (Mang.kasutatudTahed.size() == 0)
											Mang.TahedAI[(int) (Math.random() * Mang.TahedAI.length)] = Tahed2
													.kott(Mang.TaheKott);
										else
											for (char taht : Mang.kasutatudTahed) {
												try {
													Mang.TahedAI[new String(
															Mang.TahedAI)
															.indexOf(taht)] = Tahed2
															.kott(Mang.TaheKott);
												} catch (Exception e1) {
													// täht laual olemas
												}
											}
									} else {
										for (char taht : Mang.kasutatudTahed) {
											Mang.TahedAI = Mang.removeElements(
													Mang.TahedAI, taht);
										}
									}

									skoorileibel.setText("<html>Sinu skoor: "
											+ InimeseSkoor
											+ "<br>Vastase skoor: "
											+ VastaseSkoor + "</html>");
									if (Mang.mangKestab() == false) {
										if (InimeseSkoor < VastaseSkoor) {
											dialoogiPaneel
													.setText("Mäng läbi! Arvuti võitis!");
										} else {
											dialoogiPaneel
													.setText("Mäng läbi! Võitsid!!");
										}
									}
								} catch (FileNotFoundException e1) {
									e1.printStackTrace();
								}

							} else {
								for (char taht : Mang.kasutatudTahed) {
									Mang.TahedInimene = Mang.removeElements(
											Mang.TahedInimene, taht);
								}
							}
							Mang.kasutatudTahed.clear();
							setTähed(Mang.TahedInimene);
							Mang.voor++;
						} else {
							dialoogiPaneel.setText("Proovi midagi muud.");
							return;
						}
					} else {
						dialoogiPaneel.setText("Sisesta palun sõna!");
					}
				} else {
					dialoogiPaneel.setText("Vali paremalt sõne algus!");
				}
			}
		});

		kontrollPaneel.setPreferredSize(new Dimension(250, 0));
		raamiPaneel.add(kontrollPaneel, BorderLayout.WEST);
		this.add(raamiPaneel);
		this.pack();
		this.setVisible(true);

		sisestatudSõna.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				sisestatudSõna.setText("");
			}

			@Override
			public void focusLost(FocusEvent e) {
			}
		});

	}

	void setTähed(char[] tahed) {
		Mang.TahedInimene = tahed;
		for (int i = 0; i < tahed.length; i++) {
			char c = tahed[i];
			kasutajaChar[i].setText("<html>"
					+ String.valueOf(tahed[i]).toUpperCase() + "<sub>"
					+ Tahed.vaartus(c) + "</sub></html>");
		}
	}

	void uuendaTabelit(int x, int y, String suund, String word) {
		if (suund.equals("p")) {
			for (int i = x, c = 0; i < x + word.length(); i++, c++) {
				Mang.MangulaudMassiiv[y][i] = String.valueOf(word.charAt(c))
						.toUpperCase();
				mangulauaNupud[y][i].setText("<html>"
						+ String.valueOf(word.charAt(c)).toUpperCase()
						+ "<sub>" + Tahed.vaartus(word.charAt(c))
						+ "</sub></html>");
				mangulauaNupud[y][i].setBackground(Color.white);
			}
		} else {
			for (int i = y, c = 0; i < y + word.length(); i++, c++) {
				Mang.MangulaudMassiiv[i][x] = String.valueOf(word.charAt(c))
						.toUpperCase();
				mangulauaNupud[i][x].setText("<html>"
						+ String.valueOf(word.charAt(c)).toUpperCase()
						+ "<sub>" + Tahed.vaartus(word.charAt(c))
						+ "</sub></html>");
				mangulauaNupud[i][x].setBackground(Color.white);
			}
		}
	}
}
