import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

public class GUI {
	static Nupp nupp;
	public static void main(String[] args) {
		JFrame raam =new JFrame("Eestikeelne Scrabble");
		raam.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Loon vajalikud paneelid
		JPanel lauaPaneel = new JPanel();
        lauaPaneel.setLayout(new GridLayout(15, 15));
		JPanel raamiPaneel = new JPanel();
		raamiPaneel.setLayout(new BorderLayout());
		JPanel kontrollPaneel = new JPanel();
		kontrollPaneel.setLayout(new FlowLayout(FlowLayout.LEADING, 20, 20));
		lauaPaneel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		String[][] mangulaud = Mangulaud.LooMangulaud();
		mangulaud[7][7]="k";
		mangulaud[7][8]="o";
		mangulaud[7][9]="e";
		mangulaud[7][10]="r";
		
		for (int i = 0; i < mangulaud.length; i++){
			for (int j = 0; j < mangulaud.length; j++){
				if (mangulaud[i][j].length() == 1 && !mangulaud[i][j].equals(" ")){
					nupp = new Nupp("<html>"+mangulaud[i][j].toUpperCase()+"<sub>"+tahed.vaartus(mangulaud[i][j].charAt(0))+"</sub></html>", i, j);
					nupp.setBackground(Color.WHITE);
				}
				else if (mangulaud[i][j].equals("3xs")){
           	 		nupp = new Nupp("<html><i>3*s</i></html>", i, j);
					nupp.setBackground(Color.RED);
				}
				else if (mangulaud[i][j].equals("2xs")){
           	 		nupp = new Nupp("<html><i>2*s</i></html>", i, j);
					nupp.setBackground(Color.ORANGE);
				}
				else if (mangulaud[i][j].equals("3xt")){
           	 		nupp = new Nupp("<html><i>3*t</i></html>", i, j);
           	 		Color minusinine = new Color (50, 150, 250);
					nupp.setBackground(minusinine);
				}
				else if (mangulaud[i][j].equals("2xt")){
           	 		nupp = new Nupp("<html><i>2*t</i></html>", i, j);
					nupp.setBackground(Color.CYAN);
				}
           	 	else {
           	 		nupp = new Nupp(mangulaud[i][j], i, j);
           	 		Color minuroheline = new Color (20, 200, 140);
					nupp.setBackground(minuroheline);
           	 	}
           	 //	nupp.setPreferredSize(new Dimension(30, 30));
		        nupp.setMargin(new Insets(0, 0, 0, 0));
				lauaPaneel.add(nupp);
			}
		}
		
		//Loon komponente, mis saavad olema kontrollpaneelis mängulaua kõrval
		JLabel tahedLeibel = new JLabel("Sinu tähed on:");
		char[] TahedInimene = {'a', 'n', 'o', 'k', 'l', 'e', 's'};
		JPanel tahedPaneel = new JPanel();
		tahedPaneel.setLayout(new GridLayout(1, 7));
//		nii peaks hiljem olema: for (int i = 0; i < Mang.TahedInimene.length; i++){
		for (int i = 0; i < TahedInimene.length; i++){
			nupp = new Nupp("<html>"+Character.toUpperCase(TahedInimene[i])+"<sub>"+tahed.vaartus(TahedInimene[i])+"</sub></html>", -1, -1);
			nupp.setBackground(Color.WHITE);
			nupp.setPreferredSize(new Dimension(30, 30));
	        nupp.setMargin(new Insets(0, 0, 0, 0));
			tahedPaneel.add(nupp);
		}
		
		int InimeseSkoor = 30;
		int VastaseSkoor = 20;
        JLabel skoorileibel = new JLabel("<html>Sinu skoor: " + InimeseSkoor + "<br>Vastase skoor: " + VastaseSkoor + "</html>");
		JRadioButton paremale = new JRadioButton("Paremale");
		JRadioButton alla = new JRadioButton("Alla");
		ButtonGroup suund = new ButtonGroup();
		suund.add(paremale);
		suund.add(alla);
		JPanel suunaPaneel = new JPanel();
		Border suunaBorder = BorderFactory.createTitledBorder("Suund");
		suunaPaneel.setBorder(suunaBorder);
		suunaPaneel.add(paremale);
		suunaPaneel.add(alla);
		paremale.setSelected(true);
        JTextField sistestatudSõna = new JTextField("Sisesta oma sõna siia", 15);
		JLabel juhendLeibel = new JLabel("<html>Vali sõna algusruut mänguväljal,<br> sisesta sõna ja vali, kas <br>see kulgeb suunaga <br>paremale või alla</html>");
        JButton pakkumiseNupp = new JButton("Sõna lauale!");
        
        //panen komponendid paneelidele ja alampaneelid ülempaneelidele
		raamiPaneel.add(lauaPaneel);
		kontrollPaneel.add(skoorileibel);
		kontrollPaneel.add(tahedLeibel);
		kontrollPaneel.add(tahedPaneel);
		kontrollPaneel.add(juhendLeibel);
		kontrollPaneel.add(sistestatudSõna);
		kontrollPaneel.add(suunaPaneel);
		kontrollPaneel.add(pakkumiseNupp);
        kontrollPaneel.setPreferredSize(new Dimension(250, 0));
		raamiPaneel.add(kontrollPaneel, BorderLayout.WEST);
		raam.add(raamiPaneel);
		raam.pack();
		raam.setVisible(true);
	}
}