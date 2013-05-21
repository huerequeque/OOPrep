import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.Border;

public class GUI extends JFrame {
	JTextArea textArea1 = new JTextArea();
	static JPanel lauaPaneel = new JPanel();

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
		final JPanel raamiPaneel = new JPanel();
		this.setMinimumSize(new Dimension(750, 525));
		raamiPaneel.setLayout(new BorderLayout());
		lauaPaneel.setLayout(new GridLayout(15, 15));
		lauaPaneel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		JPanel kontrollPaneel = new JPanel();
		kontrollPaneel.setLayout(new FlowLayout(FlowLayout.LEADING, 15, 15));
		
		textArea1 = new JTextArea();
		// Set the default text for the text area
		textArea1.setText("Algas uus mäng!\n");
		// If text doesn't fit on a line, jump to the next
		textArea1.setLineWrap(true);
		// Makes sure that words stay intact if a line wrap occurs
		textArea1.setWrapStyleWord(true);
		// Adds scroll bars to the text area ----------
		JScrollPane scrollbar1 = new JScrollPane(textArea1, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		Dimension d = new Dimension(220, 110);		
		scrollbar1.setPreferredSize(d);
		
		looTabel(game, lauaPaneel);

		// Loon komponente, mis saavad olema kontrollpaneelis mängulaua kõrval
		JLabel tahedLeibel = new JLabel("Sinu tähed on:             ");
		JPanel tahedPaneel = new JPanel();
		tahedPaneel.setLayout(new GridLayout(1, 7));
		// nii peaks hiljem olema: for (int i = 0; i < Mang.TahedInimene.length;
		// i++){
		for (int i = 0; i < Mang.TahedInimene.length; i++) {
			nupp = new Nupp("<html>" + Character.toUpperCase(Mang.TahedInimene[i]) + "<sub>" + Tahed.vaartus(Mang.TahedInimene[i]) + "</sub></html>", -1, -1);
			nupp.setBackground(Color.WHITE);
			nupp.setPreferredSize(new Dimension(30, 30));
			nupp.setMargin(new Insets(0, 0, 0, 0));

			kasutajaChar[i] = nupp;
			tahedPaneel.add(nupp);
		}

		final JLabel skoorileibel = new JLabel("<html>Sinu skoor: " + InimeseSkoor + "<br>Vastase skoor: " + VastaseSkoor + "</html>");
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
		final JTextField sisestatudSõna = new JTextField("Sisesta sõna siia", 15);
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
		kontrollPaneel.add(scrollbar1);

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
					if (!enteredWord.equals("Sisesta sõna siia") && !enteredWord.equals("")) {
						query += enteredWord + " USER";
						textArea1.append(query+"\n");
						if (InputCheck.kontroll(query)) {
							Mang.TahtedeHaldamine(query);
							InimeseSkoor += Mang.punktid;
							Mang.InimeseSkoor += Mang.punktid;
							uuendaTabelit(Integer.parseInt(query.split(" ")[0]), Integer.parseInt(query.split(" ")[1]), query.split(" ")[2], enteredWord);
							if (Tahed2.kott(Mang.TaheKott) != '\0') {
								for (char taht : Mang.kasutatudTahed) {
									try {
										Mang.TahedInimene[new String(Mang.TahedInimene).indexOf(taht)] = Tahed2.kott(Mang.TaheKott);
									} catch (Exception e1) {
										// täht laual olemas
									}
								}
								Mang.punktid = 0;

								textArea1.append("Vastase tähed on " + Character.toUpperCase(Mang.TahedAI[0]) + Tahed.vaartus(Mang.TahedAI[0]));
								for (int i = 1; i < Mang.TahedAI.length; i++) {
									textArea1.append(", " + Character.toUpperCase(Mang.TahedAI[i]) + Tahed.vaartus(Mang.TahedAI[i]));
								}
								textArea1.append("\n");
								Mang.kasutatudTahed.clear();
								try {
									String sisend = AI.SkaneeriLauda(Mang.MangulaudMassiiv);
									System.out.println(sisend);
									if (sisend.equals("jääb vahele")) {
										textArea1.append("Vastane jättis käigu vahele. Sinu kord.\n");
									} else {
										Mang.TahtedeHaldamine(sisend);
										textArea1.append("Vastase sõna on " + Mang.inputObjekt.sõne_ise+"\n");
										System.out.println("Vastase sõna on " + Mang.inputObjekt.sõne_ise);
										uuendaTabelit(Mang.inputObjekt.rida, Mang.inputObjekt.tulp, Mang.inputObjekt.p_v_a, Mang.inputObjekt.sõne_ise);
										Mang.AISkoor += Mang.punktid;
										VastaseSkoor += Mang.punktid;
										skoorileibel.setText("<html>Sinu skoor: " + InimeseSkoor + "<br>Vastase skoor: " + VastaseSkoor + "</html>\n");
									}

									textArea1.append("Vastane sai " + Mang.punktid + " punkti!\n");
									if (Tahed2.kott(Mang.TaheKott) != '\0') {
										if (Mang.kasutatudTahed.size() == 0)
											Mang.TahedAI[(int) (Math.random() * Mang.TahedAI.length)] = Tahed2.kott(Mang.TaheKott);
										else
											for (char taht : Mang.kasutatudTahed) {
												try {
													Mang.TahedAI[new String(Mang.TahedAI).indexOf(taht)] = Tahed2.kott(Mang.TaheKott);
												} catch (Exception e1) {
													// täht laual olemas
												}
											}
									} else {
										for (char taht : Mang.kasutatudTahed) {
											Mang.TahedAI = Mang.removeElements(Mang.TahedAI, taht);
										}
									}

									skoorileibel.setText("<html>Sinu skoor: " + InimeseSkoor + "<br>Vastase skoor: " + VastaseSkoor + "</html>");
									if (Mang.mangKestab() == false) {
										if (InimeseSkoor < VastaseSkoor) {
											textArea1.append("Mäng läbi! Arvuti võitis!\n");
										} else {
											textArea1.append("Mäng läbi! Võitsid!!\n");
										}
									}
								} catch (FileNotFoundException e1) {
									e1.printStackTrace();
								}

							} else {
								for (char taht : Mang.kasutatudTahed) {
									Mang.TahedInimene = Mang.removeElements(Mang.TahedInimene, taht);
								}
							}
							Mang.kasutatudTahed.clear();
							setTähed(Mang.TahedInimene);
							Mang.voor++;
						} else {
							textArea1.append("Proovi midagi muud.\n");
							return;
						}
					} else {
						textArea1.append("Sisesta palun sõna!\n");
					}
				} else {
					textArea1.append("Vali paremalt sõne algus!\n");
				}
			}
		});
		
		JMenuBar jmb = new JMenuBar();
		this.setJMenuBar(jmb);
		JMenu fileMenuButton = new JMenu("File");
		jmb.add(fileMenuButton);
		JMenuItem newGame = new JMenuItem("Uus mäng");
		fileMenuButton.add(newGame);
		JMenuItem saveGame = new JMenuItem("Salvesta mäng");
		fileMenuButton.add(saveGame);
		JMenuItem loadGame = new JMenuItem("Jätka eelmist");
		fileMenuButton.add(loadGame);
		newGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Mang.MangulaudMassiiv = Mangulaud.LooMangulaud();
				Mang.voor=0;
				Mang.InimeseSkoor=0;
				Mang.AISkoor=0;
				Tahed2.setKott();
				Mang.TaheKott = Tahed2.getKott();
				skoorileibel.setText("<html>Sinu skoor: " + Mang.InimeseSkoor + "<br>Vastase skoor: " + Mang.AISkoor + "</html>");
				for (int i = 0; i < Mang.TahedInimene.length; i++){
					Mang.TahedInimene[i] = Tahed2.kott(Mang.TaheKott);
				}
				setTähed(Mang.TahedInimene);
				for (int i = 0; i < Mang.TahedAI.length; i++){
					Mang.TahedAI[i] = Tahed2.kott(Mang.TaheKott);
				}
				JPanel uusLauaPaneel = new JPanel();
				uusLauaPaneel.setLayout(new GridLayout(15, 15));
				uusLauaPaneel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				raamiPaneel.remove(lauaPaneel);
				looTabel(game, uusLauaPaneel);
				lauaPaneel=uusLauaPaneel;
				raamiPaneel.add(lauaPaneel);
				textArea1.append("Algas uus mäng!\n");
				sisestatudSõna.setText("Sisesta sõna siia");
			}
		});
		
		saveGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try
				{
					ObjectOutputStream oos = new ObjectOutputStream (new FileOutputStream("save.dat"));
					oos.writeObject(Mang.MangulaudMassiiv);
					oos.writeObject(Mang.TaheKott);
					oos.writeObject(Mang.InimeseSkoor);
					oos.writeObject(Mang.AISkoor);
					oos.writeObject(Mang.TahedInimene);
					oos.writeObject(Mang.TahedAI);
					oos.close();
				}catch (Exception e1){//Catch exception if any
					JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage());
				}
			}
		});
		
		loadGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try
				{
					ObjectInputStream ois = new ObjectInputStream(new FileInputStream("save.dat"));
					Mang.MangulaudMassiiv = (String[][]) ois.readObject();
					Mang.TaheKott = (ArrayList<Character>) ois.readObject();
					Mang.InimeseSkoor = (Integer) ois.readObject();
					Mang.AISkoor = (Integer) ois.readObject();
					Mang.TahedInimene = (char[]) ois.readObject();
					Mang.TahedAI = (char[]) ois.readObject();
					ois.close();
				}catch (Exception e1){//Catch exception if any
					JOptionPane.showMessageDialog(null, "Error: " + e1.getMessage());
				}
				Mang.voor=1;
				setTähed(Mang.TahedInimene);
				skoorileibel.setText("<html>Sinu skoor: " + Mang.InimeseSkoor + "<br>Vastase skoor: " + Mang.AISkoor + "</html>");
				JPanel uusLauaPaneel = new JPanel();
				uusLauaPaneel.setLayout(new GridLayout(15, 15));
				uusLauaPaneel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				raamiPaneel.remove(lauaPaneel);
				looTabel(game, uusLauaPaneel);
				lauaPaneel=uusLauaPaneel;
				raamiPaneel.add(lauaPaneel);
				textArea1.append("Jätkub eelmine mäng!\n");
				sisestatudSõna.setText("Sisesta sõna siia");
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
		for (int i = 0; i < tahed.length; i++) {
			char c = tahed[i];
			kasutajaChar[i].setText("<html>" + String.valueOf(tahed[i]).toUpperCase() + "<sub>" + Tahed.vaartus(c) + "</sub></html>");
		}
	}
	
	void looTabel(String[][] game, JPanel lauaPaneel){
		for (int i = 0; i < game.length; i++) {
			for (int j = 0; j < game.length; j++) {
				if (Mang.MangulaudMassiiv[i][j].length() == 1 && !Mang.MangulaudMassiiv[i][j].equals(" ")) {
					nupp = new Nupp("<html>" + Mang.MangulaudMassiiv[i][j].toUpperCase() + "<sub>" + Tahed.vaartus(Mang.MangulaudMassiiv[i][j].charAt(0))
							+ "</sub></html>", i, j);
					nupp.setBackground(Color.WHITE);
				} else if (Mang.MangulaudMassiiv[i][j].equals("3xs")) {
					nupp = new Nupp("<html><i>3*s</i></html>", i, j);
					nupp.setBackground(Color.RED);
				} else if (Mang.MangulaudMassiiv[i][j].equals("2xs")) {
					nupp = new Nupp("<html><i>2*s</i></html>", i, j);
					nupp.setBackground(Color.ORANGE);
				} else if (Mang.MangulaudMassiiv[i][j].equals("3xt")) {
					nupp = new Nupp("<html><i>3*t</i></html>", i, j);
					Color minusinine = new Color(50, 150, 250);
					nupp.setBackground(minusinine);
				} else if (Mang.MangulaudMassiiv[i][j].equals("2xt")) {
					nupp = new Nupp("<html><i>2*t</i></html>", i, j);
					nupp.setBackground(Color.CYAN);
				} else {
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

						selectedNupp = button.yKoordinaat + " " + button.xKoordinaat;
					}
				});
				mangulauaNupud[i][j] = nupp;
				lauaPaneel.add(nupp);
			}
		}
	}

	void uuendaTabelit(int rida, int tulp, String suund, String word) {
		if (suund.equals("p")) {
			for (int i = tulp, c = 0; i < tulp + word.length(); i++, c++) {
				Mang.MangulaudMassiiv[rida][i] = String.valueOf(word.charAt(c)).toUpperCase();
				mangulauaNupud[rida][i].setText("<html>" + String.valueOf(word.charAt(c)).toUpperCase() + "<sub>" + Tahed.vaartus(word.charAt(c)) + "</sub></html>");
				mangulauaNupud[rida][i].setBackground(Color.white);
			}
		} else {
			for (int i = rida, c = 0; i < rida + word.length(); i++, c++) {
				Mang.MangulaudMassiiv[i][tulp] = String.valueOf(word.charAt(c)).toUpperCase();
				mangulauaNupud[i][tulp].setText("<html>" + String.valueOf(word.charAt(c)).toUpperCase() + "<sub>" + Tahed.vaartus(word.charAt(c)) + "</sub></html>");
				mangulauaNupud[i][tulp].setBackground(Color.white);
			}
		}
	}
	
}
