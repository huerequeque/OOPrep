import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
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
	ArrayList <Nupp> kasutajaChar = new ArrayList <Nupp>();
	Nupp button1 = null;
	Nupp button2;
	protected Color v2rv;
	AI AIisend = new AI();

	final JRadioButton paremale = new JRadioButton("Paremale");
	final JRadioButton alla = new JRadioButton("Alla");
	final JTextField sisestatudSõna = new JTextField("Sisesta sõna siia", 15);
	final JLabel skoorileibel = new JLabel("<html>Sinu skoor: "
			+ Mang.InimeseSkoor + "<br>Vastase skoor: " + Mang.AISkoor
			+ "</html>");
	private JPanel tahedPaneel;
	private JPanel kontrollPaneel;
	private JLabel tahedLeibel;

	public GUI() {
		super("Eestikeelne sõnamäng");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Loon vajalikud paneelid
		final JPanel raamiPaneel = new JPanel();
		this.setMinimumSize(new Dimension(750, 525));
		raamiPaneel.setLayout(new BorderLayout());
		lauaPaneel.setLayout(new GridLayout(15, 15));
		lauaPaneel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		kontrollPaneel = new JPanel();
		kontrollPaneel.setLayout(new FlowLayout(FlowLayout.LEADING, 15, 15));

		textArea1 = new JTextArea();
		// Set the default text for the text area
		textArea1.setEditable(false);
		textArea1.setText("Algas uus mäng!\n");
		// If text doesn't fit on a line, jump to the next
		textArea1.setLineWrap(true);
		// Makes sure that words stay intact if a line wrap occurs
		textArea1.setWrapStyleWord(true);
		// Adds scroll bars to the text area ----------
		JScrollPane scrollbar1 = new JScrollPane(textArea1,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		Dimension d = new Dimension(220, 110);
		scrollbar1.setPreferredSize(d);

		looTabel(Mang.MangulaudMassiiv, lauaPaneel);

		// Loon komponente, mis saavad olema kontrollpaneelis mängulaua kõrval
		tahedLeibel = new JLabel("Sinu tähed on:");
		tahedLeibel.setPreferredSize(new Dimension(220, 10));
		tahedPaneel = new JPanel();
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

			kasutajaChar.add(i, nupp);
			tahedPaneel.add(nupp);
		}



		ButtonGroup suund = new ButtonGroup();
		suund.add(paremale);
		suund.add(alla);
		JPanel suunaPaneel = new JPanel();
		Border suunaBorder = BorderFactory.createTitledBorder("Suund");
		suunaPaneel.setBorder(suunaBorder);
		suunaPaneel.add(paremale);
		suunaPaneel.add(alla);
		paremale.setSelected(true);

		JLabel juhendLeibel = new JLabel(
				"<html>Vali sõna algusruut mängu-<br>väljal, sisesta sõna ja vali, <br>kas see kulgeb suunaga <br>paremale või alla</html>");
		JButton pakkumiseNupp = new JButton("Sõna lauale!");
		pakkumiseNupp.setMargin(new Insets(2, 5, 2, 5));
		JButton vaheleNupp = new JButton("Jäta vahele!");
		vaheleNupp.setMargin(new Insets(2, 5, 2, 5));

		// panen komponendid paneelidele ja alampaneelid ülempaneelidele
		raamiPaneel.add(lauaPaneel);
		kontrollPaneel.add(skoorileibel);
		kontrollPaneel.add(tahedLeibel);
		kontrollPaneel.add(tahedPaneel);
		kontrollPaneel.add(juhendLeibel);
		kontrollPaneel.add(sisestatudSõna);
		kontrollPaneel.add(suunaPaneel);
		kontrollPaneel.add(pakkumiseNupp);
		kontrollPaneel.add(vaheleNupp);
		kontrollPaneel.add(scrollbar1);

		pakkumiseNupp.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {


				if (Mang.TahedInimene.length==0)
					AIKäik("jääb vahele");
				else if (Mang.TahedAI.length!=0)
					AIKäik(UserKäik());
				else if (Mang.mangKestab()){
					UserKäik();
				}

				/*System.out.println("Sinu tähed on " + Character.toUpperCase(Mang.TahedInimene[0]) + Tahed.vaartus(Mang.TahedInimene[0]));
for (int i = 1; i < Mang.TahedInimene.length; i++) {
System.out.println(", " + Character.toUpperCase(Mang.TahedInimene[i]) + Tahed.vaartus(Mang.TahedInimene[i]));
}*/
				System.out.println("kasutajaChar pikkus " + kasutajaChar.size());
				if (Mang.mangKestab() == false) {
					skoorileibel.setText("<html>Sinu skoor: " + Mang.InimeseSkoor
							+ "<br>Vastase skoor: " + Mang.AISkoor + "</html>");
					if (Mang.InimeseSkoor < Mang.AISkoor) {
						textArea1.append("Mäng läbi! Arvuti võitis!\n");
						textArea1.setCaretPosition(textArea1.getDocument().getLength());
					} else {
						textArea1.append("Mäng läbi! Võitsid!!\n");
						textArea1.setCaretPosition(textArea1.getDocument().getLength());
					}
					if (Mang.InimeseSkoor == Mang.AISkoor) {
						textArea1.append("Viik!\n");
						textArea1.setCaretPosition(textArea1.getDocument().getLength());
					}
				}


			}
		});

		vaheleNupp.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (Mang.voor==0){
					Mang.MangulaudMassiiv[7][7]=Mang.TahedAI[4]+"";
					AIKäik("7 7 p " + Mang.TahedAI[4]);
				}
				else if (Mang.TahedAI.length==0){
					textArea1.append("Vastasel rohkem tähti pole.\n");
					textArea1.setCaretPosition(textArea1.getDocument().getLength());
					if (Mang.InimeseSkoor < Mang.AISkoor) {
						textArea1.append("Mäng läbi! Arvuti võitis!\n");
						textArea1.setCaretPosition(textArea1.getDocument().getLength());
					} else {
						textArea1.append("Mäng läbi! Väitsid!!\n");
						textArea1.setCaretPosition(textArea1.getDocument().getLength());
					}
					if (Mang.InimeseSkoor == Mang.AISkoor) {
						textArea1.append("Viik!\n");
					}
				}
				else {
					int indeks = (int)(Math.random()*Mang.TahedInimene.length);
					Mang.TaheKott.add(Mang.TahedInimene[indeks]);
					Mang.TahedInimene[indeks] = Tahed2.kott(Mang.TaheKott);
					setTähed(Mang.TahedInimene);
					AIKäik("jääb vahele");
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
				Mang.voor = 0;
				Mang.InimeseSkoor = 0;
				Mang.AISkoor = 0;
				Tahed2.setKott();
				Mang.TaheKott = Tahed2.getKott();
				skoorileibel.setText("<html>Sinu skoor: " + Mang.InimeseSkoor
						+ "<br>Vastase skoor: " + Mang.AISkoor + "</html>");
				Mang.TahedInimene = new char[7];
				for (int i = 0; i < Mang.TahedInimene.length; i++) {
					Mang.TahedInimene[i] = Tahed2.kott(Mang.TaheKott);
					System.out.println("täht" + Mang.TahedInimene[i]);
				}
				setTähed(Mang.TahedInimene);
				Mang.TahedAI = new char[7];
				for (int i = 0; i < Mang.TahedAI.length; i++) {
					Mang.TahedAI[i] = Tahed2.kott(Mang.TaheKott);
				}
				JPanel uusLauaPaneel = new JPanel();
				uusLauaPaneel.setLayout(new GridLayout(15, 15));
				uusLauaPaneel.setBorder(BorderFactory.createEmptyBorder(5, 5,
						5, 5));
				raamiPaneel.remove(lauaPaneel);
				looTabel(Mang.MangulaudMassiiv, uusLauaPaneel);
				lauaPaneel = uusLauaPaneel;
				raamiPaneel.add(lauaPaneel);
				textArea1.setText("");
				textArea1.append("Algas uus mäng!\n");
				sisestatudSõna.setText("Sisesta sõna siia");
			}
		});
		/*newGame.addActionListener(new ActionListener() {
@Override
public void actionPerformed(ActionEvent e) {
Mang.MangulaudMassiiv = Mangulaud.LooMangulaud();
Mang.voor = 0;
Mang.InimeseSkoor = 0;
Mang.AISkoor = 0;
Tahed2.setKott();
Mang.TaheKott = Tahed2.getKott();
skoorileibel.setText("<html>Sinu skoor: " + Mang.InimeseSkoor
+ "<br>Vastase skoor: " + Mang.AISkoor + "</html>");
Mang.TahedInimene = new char[7];
for (int i = 0; i < Mang.TahedInimene.length; i++) {
Mang.TahedInimene[i] = Tahed2.kott(Mang.TaheKott);
System.out.println("täht" + Mang.TahedInimene[i]);
}
JPanel uusTahedPaneel = new JPanel();
uusTahedPaneel.setLayout(new GridLayout(1, 7));
kasutajaChar.clear();
for (int i = 0; i < Mang.TahedInimene.length; i++) {
System.out.println(Mang.TahedInimene[i]);
nupp = new Nupp("<html>"
+ Character.toUpperCase(Mang.TahedInimene[i]) + "<sub>"
+ Tahed.vaartus(Mang.TahedInimene[i]) + "</sub></html>",
-1, -1);
nupp.setBackground(Color.WHITE);
nupp.setPreferredSize(new Dimension(30, 30));
nupp.setMargin(new Insets(0, 0, 0, 0));
kasutajaChar.add(i, nupp);
uusTahedPaneel.add(nupp);
}
kontrollPaneel.remove(tahedPaneel);
tahedPaneel=uusTahedPaneel;
//kontrollPaneel.add(tahedPaneel, 2);
kontrollPaneel.repaint();
// kontrollPaneel.remove(tahedPaneel);
// tahedPaneel=uusTahedPaneel;
//raamipaneel.remove(
Mang.TahedAI = new char[7];
for (int i = 0; i < Mang.TahedAI.length; i++) {
Mang.TahedAI[i] = Tahed2.kott(Mang.TaheKott);
}
JPanel uusLauaPaneel = new JPanel();
uusLauaPaneel.setLayout(new GridLayout(15, 15));
uusLauaPaneel.setBorder(BorderFactory.createEmptyBorder(5, 5,
5, 5));
raamiPaneel.remove(lauaPaneel);
looTabel(game, uusLauaPaneel);
lauaPaneel = uusLauaPaneel;
raamiPaneel.add(lauaPaneel);
textArea1.setText("");
textArea1.append("Algas uus mäng!\n");
sisestatudSõna.setText("Sisesta sõna siia");
}
});
		 */
		saveGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					ObjectOutputStream oos = new ObjectOutputStream(
							new FileOutputStream("save.dat"));
					oos.writeObject(Mang.MangulaudMassiiv);
					oos.writeObject(Mang.TaheKott);
					oos.writeObject(Mang.InimeseSkoor);
					oos.writeObject(Mang.AISkoor);
					oos.writeObject(Mang.TahedInimene);
					oos.writeObject(Mang.TahedAI);
					oos.close();
				} catch (Exception e1) {// Catch exception if any
					JOptionPane.showMessageDialog(null,
							"Error: " + e1.getMessage());
				}
			}
		});

		loadGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					ObjectInputStream ois = new ObjectInputStream(
							new FileInputStream("save.dat"));
					Mang.MangulaudMassiiv = (String[][]) ois.readObject();
					Mang.TaheKott = (ArrayList<Character>) ois.readObject();
					Mang.InimeseSkoor = (Integer) ois.readObject();
					Mang.AISkoor = (Integer) ois.readObject();
					Mang.TahedInimene = (char[]) ois.readObject();
					Mang.TahedAI = (char[]) ois.readObject();
					ois.close();
				} catch (Exception e1) {// Catch exception if any
					JOptionPane.showMessageDialog(null,
							"Error: " + e1.getMessage());
				}
				Mang.voor = 1;
				setTähed(Mang.TahedInimene);
				skoorileibel.setText("<html>Sinu skoor: " + Mang.InimeseSkoor
						+ "<br>Vastase skoor: " + Mang.AISkoor + "</html>");
				JPanel uusLauaPaneel = new JPanel();
				uusLauaPaneel.setLayout(new GridLayout(15, 15));
				uusLauaPaneel.setBorder(BorderFactory.createEmptyBorder(5, 5,
						5, 5));
				raamiPaneel.remove(lauaPaneel);
				looTabel(Mang.MangulaudMassiiv, uusLauaPaneel);
				lauaPaneel = uusLauaPaneel;
				raamiPaneel.add(lauaPaneel);
				textArea1.append("Jätkub eelmine mäng!\n");
				textArea1.setCaretPosition(textArea1.getDocument().getLength());
				sisestatudSõna.setText("Sisesta sõna siia");
			}
		});

		kontrollPaneel.setPreferredSize(new Dimension(250, 0));
		raamiPaneel.add(kontrollPaneel, BorderLayout.WEST);
		this.add(raamiPaneel);
		this.pack();
		this.getRootPane().setDefaultButton(pakkumiseNupp);
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
			kasutajaChar.get(i).setText("<html>"
					+ String.valueOf(tahed[i]).toUpperCase() + "<sub>"
					+ Tahed.vaartus(c) + "</sub></html>");
		}
	}

	void looTabel(String[][] game, JPanel lauaPaneel) {
		for (int i = 0; i < game.length; i++) {
			for (int j = 0; j < game.length; j++) {
				if (Mang.MangulaudMassiiv[i][j].length() == 1
						&& !Mang.MangulaudMassiiv[i][j].equals(" ")) {
					nupp = new Nupp("<html>"
							+ Mang.MangulaudMassiiv[i][j].toUpperCase()
							+ "<sub>"
							+ Tahed.vaartus(Mang.MangulaudMassiiv[i][j]
									.charAt(0)) + "</sub></html>", i, j);
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

						button2 = button;

						if (button1 != null) {
							button1.setBackground(v2rv);
						}
						v2rv = button.getBackground();

						Color aktiivne = new Color(30, 100, 60);
						button.setBackground(aktiivne);

						button1 = button;

						selectedNupp = button.yKoordinaat + " "
								+ button.xKoordinaat;
						if (sisestatudSõna.getText().equals("Sisesta sõna siia")) sisestatudSõna.requestFocus();
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
				Mang.MangulaudMassiiv[rida][i] = String.valueOf(word.charAt(c))
						.toUpperCase();
				mangulauaNupud[rida][i].setText("<html>"
						+ String.valueOf(word.charAt(c)).toUpperCase()
						+ "<sub>" + Tahed.vaartus(word.charAt(c))
						+ "</sub></html>");
				mangulauaNupud[rida][i].setBackground(Color.white);
			}
		} else {
			for (int i = rida, c = 0; i < rida + word.length(); i++, c++) {
				Mang.MangulaudMassiiv[i][tulp] = String.valueOf(word.charAt(c))
						.toUpperCase();
				mangulauaNupud[i][tulp].setText("<html>"
						+ String.valueOf(word.charAt(c)).toUpperCase()
						+ "<sub>" + Tahed.vaartus(word.charAt(c))
						+ "</sub></html>");
				mangulauaNupud[i][tulp].setBackground(Color.white);
			}
		}
	}




	public String UserKäik(){
		String query = "";
		if (selectedNupp != null) {
			query += selectedNupp;
			if (paremale.isSelected()) {
				query += " p ";
			} else if (alla.isSelected()) {
				query += " a ";
			}
			String enteredWord = sisestatudSõna.getText();
			if (!enteredWord.equals("Sisesta sõna siia")
					&& !enteredWord.equals("")) {
				query += enteredWord + " USER";
				//textArea1.append(query + "\n");
				if (InputCheck.kontroll(query)) {
					selectedNupp = null;
					Mang.TahtedeHaldamine(query);
					Mang.InimeseSkoor += Mang.punktid;
					textArea1.append("Said " + Mang.punktid
							+ " punkti!\n");
					textArea1.setCaretPosition(textArea1.getDocument().getLength());
					uuendaTabelit(
							Integer.parseInt(query.split(" ")[0]),
							Integer.parseInt(query.split(" ")[1]),
							query.split(" ")[2], enteredWord);

					for (char taht : Mang.kasutatudTahed) {
						//textArea1.append("kasutasid " + taht);

						if (Tahed2.kott(Mang.TaheKott) != '\0') {
							Mang.TahedInimene[new String(Mang.TahedInimene).indexOf(taht)] = Tahed2.kott(Mang.TaheKott);
						}
						else if (taht!='\0' && Mang.TahedInimene.length>0) {
							Mang.TahedInimene = Mang.removeElements(Mang.TahedInimene, taht);
							JPanel uusTahedPaneel = new JPanel();
							uusTahedPaneel.setLayout(new GridLayout(1, Mang.TahedInimene.length));
							kasutajaChar.clear();
							int i = 0;
							for (; i < Mang.TahedInimene.length; i++) {
								nupp = new Nupp("<html>"
										+ Character.toUpperCase(Mang.TahedInimene[i]) + "<sub>"
										+ Tahed.vaartus(Mang.TahedInimene[i]) + "</sub></html>",
										-1, -1);
								nupp.setBackground(Color.WHITE);
								nupp.setPreferredSize(new Dimension(30, 30));
								nupp.setMargin(new Insets(0, 0, 0, 0));
								kasutajaChar.add(i, nupp);
								uusTahedPaneel.add(nupp);
							}
							for (; i < 7; i++){
								nupp = new Nupp("",-1, -1);
								nupp.setBackground(Color.WHITE);
								nupp.setPreferredSize(new Dimension(30, 30));
								nupp.setMargin(new Insets(0, 0, 0, 0));
								kasutajaChar.add(i, nupp);
								uusTahedPaneel.add(nupp);
							}
							kontrollPaneel.remove(tahedPaneel);
							tahedPaneel=uusTahedPaneel;
							kontrollPaneel.add(tahedPaneel, 2);
							//tahedPaneel=uusTahedPaneel;
							//kontrollPaneel.repaint();
						}

					}

					Mang.punktid = 0;
					Mang.kasutatudTahed.clear();
					setTähed(Mang.TahedInimene);
					Mang.voor++;
				} else {
					textArea1.append("Proovi midagi muud.\n");
					textArea1.setCaretPosition(textArea1.getDocument().getLength());
					return "viga";
				}
			} else {
				textArea1.append("Sisesta palun sõna!\n");
				textArea1.setCaretPosition(textArea1.getDocument().getLength());
				return "viga";
			}
		} else {
			textArea1.append("Vali paremalt sõne algus!\n");
			textArea1.setCaretPosition(textArea1.getDocument().getLength());
			return "viga";
		}

		button2.setBackground(Color.WHITE);
		button1 = null;
		return query;

	}





	public void AIKäik(String query){
		if (Mang.TahedInimene.length==0) {
			tahedLeibel.setText("Tähed on otsas!");	
		}
		if(query != "viga"){
			textArea1.append("Vastase tähed on "
					+ Character.toUpperCase(Mang.TahedAI[0])
					+ Tahed.vaartus(Mang.TahedAI[0]));
			for (int i = 1; i < Mang.TahedAI.length; i++) {
				textArea1.append(", "
						+ Character.toUpperCase(Mang.TahedAI[i])
						+ Tahed.vaartus(Mang.TahedAI[i]));
			}
			textArea1.append("\n");
			textArea1.setCaretPosition(textArea1.getDocument().getLength());
			// Mang.kasutatudTahed.clear();
			try {
				String sisend = AIisend.SkaneeriLauda(query);
				System.out.println(sisend);
				if (sisend.equals("jääb vahele")) {
					textArea1
					.append("Vastane jättis käigu vahele. Sinu kord.\n");
					textArea1.setCaretPosition(textArea1.getDocument().getLength());
				} else {
					Mang.TahtedeHaldamine(sisend);
					textArea1.append("Vastase sõna on "
							+ Mang.inputObjekt.rida + ", " + Mang.inputObjekt.tulp + " " + 
							Mang.inputObjekt.p_v_a + " " + Mang.inputObjekt.sõne_ise + "\n");
					textArea1.setCaretPosition(textArea1.getDocument().getLength());
					System.out.println("Vastase sõna on "
							+ Mang.inputObjekt.sõne_ise);
					uuendaTabelit(Mang.inputObjekt.rida,
							Mang.inputObjekt.tulp, Mang.inputObjekt.p_v_a,
							Mang.inputObjekt.sõne_ise);
					Mang.AISkoor += Mang.punktid;
					skoorileibel.setText("<html>Sinu skoor: "
							+ Mang.InimeseSkoor + "<br>Vastase skoor: "
							+ Mang.AISkoor + "</html>\n");
				}

				textArea1.append("Vastane sai " + Mang.punktid
						+ " punkti!\n");
				textArea1.setCaretPosition(textArea1.getDocument().getLength());
				for (char taht : Mang.kasutatudTahed) {
					try {
						if (Tahed2.kott(Mang.TaheKott) != '\0') {
							Mang.TahedAI[new String(Mang.TahedAI).indexOf(taht)] = Tahed2.kott(Mang.TaheKott);
						}
						else Mang.TahedAI = Mang.removeElements(Mang.TahedAI, taht);
					} catch (Exception e1) {
						// täht laual olemas
					}
				}
				Mang.kasutatudTahed.clear();

				skoorileibel.setText("<html>Sinu skoor: "
						+ Mang.InimeseSkoor + "<br>Vastase skoor: "
						+ Mang.AISkoor + "</html>");

			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			Thread t = new Thread(AIisend, "Lõim-1");
			t.start();
		}
	}


}
