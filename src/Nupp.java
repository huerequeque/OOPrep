import javax.swing.JButton;


public class Nupp extends JButton {
	
	int yKoordinaat;
	int xKoordinaat;
	String nimi;
	
	public int getyKoordinaat() {
		return yKoordinaat;
	}

	public int getxKoordinaat() {
		return xKoordinaat;
	}
	
	public String getnimi() {
		return nimi;
	}



	public Nupp(String nimi, int yKoordinaat, int xKoordinaat) {
		super(nimi);
		this.nimi = nimi;
		this.yKoordinaat = yKoordinaat;
		this.xKoordinaat = xKoordinaat;
	}



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	

}
