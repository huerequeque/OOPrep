
public class InfoInputist {
	int rida = -1;
	int tulp = -1;
	String p_v_a = "";
	String sõne_ise;
	int [][] koordinaadid;
	
	public InfoInputist(String input){
		//jargnev on tegelt laenatud Laud_input_kontroll.java'st
		input = input.replace(",", "");
		String[] jupid = input.split(" ");
		rida = Integer.parseInt(jupid[0]);
		tulp = Integer.parseInt(jupid[1]);
		p_v_a = jupid[2];
		sõne_ise = jupid[3];
		koordinaadid = new int[sõne_ise.length()][2];
		if (p_v_a.equals("p")){
			for (int j = tulp, indeks = 0; indeks < sõne_ise.length(); j++, indeks++){
				koordinaadid[indeks][0] = rida;
				koordinaadid[indeks][1] = j;
			}
		}
		if (p_v_a.equals("a")){
			for (int i = rida, indeks = 0; indeks < sõne_ise.length(); i++, indeks++){
				koordinaadid[indeks][0] = i;
				koordinaadid[indeks][1] = tulp;
			}
		}
		
	}
}