import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Laud_input_kontroll {

	private static int perpendicularCounter = 0; // enam ei ole kindel selle rea mõttekuses .... aga las ta olla

	public static boolean kontroll(String sisend) {
		perpendicularCounter = 0;
		sisend = sisend.replace(",", "");
		String[] jupid = sisend.split(" ");
		int tulp = Integer.parseInt(jupid[0]);
		int rida = Integer.parseInt(jupid[1]);
		String p_v_a = jupid[2];
		String sõne_ise = jupid[3];
		char[] charMassiv = sõne_ise.toCharArray();
		return checkData(rida, tulp, sõne_ise, p_v_a, charMassiv);
	}

	private static boolean checkData(int rida, int tulp, String sõne_ise, String p_v_a, char[] charMassiv) {
		if (!checkWord(sõne_ise)) {
			return false;
		}
		if (!tahedKlapivad(rida, tulp, p_v_a, sõne_ise)) return false;
		if (p_v_a.equalsIgnoreCase("p")) {
			//et ära mahuks
			if (tulp+sõne_ise.length()>14) return false;
			int charIndeksTulp = tulp;
			for (char a : charMassiv) {
				if (!kontrolliChar(a, rida, charIndeksTulp, p_v_a)) {
					return false;
				}
				charIndeksTulp++;
			}
		} else if (p_v_a.equalsIgnoreCase("a")) {
			//et ära mahuks
			if (rida+sõne_ise.length()>14) return false;
			int charIndeksRida = rida;
			for (char a : charMassiv) {
				if (!kontrolliChar(a, charIndeksRida, tulp, p_v_a)) {
					return false;
				}
				charIndeksRida++;
			}
		} else {
			System.err.println("Vigane sisend!");
			return false;
		}
		String paremale = getPerpendicularWord(sõne_ise.charAt(0), rida, tulp, "a");
		String alla = getPerpendicularWord(sõne_ise.charAt(0), rida, tulp, "p");
		if(p_v_a.equals("a")){
			alla+=sõne_ise.substring(1);
		}else{
			paremale+=sõne_ise.substring(1);
		}
		if (alla.length() > 1 && !checkWord(alla)) {
			return false;
		}
		if (paremale.length() > 1 && !checkWord(paremale)) {
			return false;
		}
		return true;
	}

	// kontrollin, kas sõna eksisteerib
	public static boolean checkWord(String sõne_ise) {
		try {
			String pwd = System.getProperty("user.dir");
			File sonadFile = new File(pwd + "/src/sonad.txt");
			FileInputStream fstream = new FileInputStream(sonadFile);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strRida;
			while ((strRida = br.readLine()) != null) {
				if (sõne_ise.equalsIgnoreCase(strRida)) {
					br.close();
					return true;
				}
			}
			br.close();
			return false;
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			return false;
		}
	}

	private static boolean kontrolliChar(char a, int rida, int tulp, String p_v_a) {

		// if (rida > 14 || rida < 0 || tulp < 0 || tulp > 14) {
		// return false;
		// }
		if (!isEmpty(rida, tulp)) {
			if (!Mang.MangulaudMassiiv[rida][tulp].equalsIgnoreCase(String.valueOf(a))) {
				return false;
			}
	
		}
		String perpendicularWord = getPerpendicularWord(a, rida, tulp, p_v_a);

		// System.out.println("risti moodustub sõne: " + perpendicularWord);
		if (perpendicularWord.length() > 1) {
			if (checkWord(perpendicularWord)) {
				perpendicularCounter++;
			} else {
				return false;
			}
		}
		return true;
	}

	// Ei leia kõiki risti sõnu... vasakule ei kontrolli vms
	private static String getPerpendicularWord(char täht, int ridaIndeks, int tulpIndeks, String suund) {
		String[] terveVeergRida = new String[15];
		if (suund.equalsIgnoreCase("p")) {
			for (int i = 0; i < 15; i++) {
				terveVeergRida[i] = Mang.MangulaudMassiiv[i][tulpIndeks];
			}
			String[] lõpp = Arrays.copyOfRange(terveVeergRida, ridaIndeks + 1, 15);
			String[] algus = Arrays.copyOfRange(terveVeergRida, 0, ridaIndeks);
			return getWordFromArray(lõpp, algus, täht);
		} else {
			for (int i = 0; i < 15; i++) {
				terveVeergRida[i] = Mang.MangulaudMassiiv[ridaIndeks][i];
			}
			String[] lõpp = Arrays.copyOfRange(terveVeergRida, tulpIndeks + 1, 14);
			String[] algus = Arrays.copyOfRange(terveVeergRida, 0, tulpIndeks);
			return getWordFromArray(lõpp, algus, täht);
		}

	}
	// annad ette terve rea / tulba, kuhu on sisestatud sinu sõne ja tagastab vastava sõne
	private static String getWordFromArray(String[] lõpp, String[] algus, char a) {
		String algusSõne = "";
		String lõppSõne = "";
		for (int j = algus.length - 1; j >= 0; j--) {
			String l = algus[j];
			if (l == "3xs" || l == "2xs" || l == " " || l == "2xt" || l == "3xt") {
				break;
			} else {
				algusSõne = l + algusSõne;
			}
		}
		for (String l : lõpp) {
			if (l == "3xs" || l == "2xs" || l == " " || l == "2xt" || l == "3xt") {
				break;
			} else {
				lõppSõne += l;
			}
		}
		return algusSõne + a + lõppSõne;
	}

	// kui midagi on kasutaja poolt on sees midagi siis on kogu see false; või
	// kui kehtivad need tingimused siis on väli tühi
	private static boolean isEmpty(int r, int t) {
		//siin tuleb miskit parandada
		if (r < 15 || t < 15){
			return Mang.MangulaudMassiiv[r][t].equalsIgnoreCase(" ") || Mang.MangulaudMassiiv[r][t].equalsIgnoreCase("") || Mang.MangulaudMassiiv[r][t].equalsIgnoreCase("3xs")
					|| Mang.MangulaudMassiiv[r][t].equalsIgnoreCase("2xs") || Mang.MangulaudMassiiv[r][t].equalsIgnoreCase("3xt") || Mang.MangulaudMassiiv[r][t].equalsIgnoreCase("2xt");
		}
		else return false;
	}
	
	public static boolean tahedKlapivad(int rida, int tulp, String p_v_a, String sona){
		String tahed = new String (Mang.TahedInimene);
		Mang.kasutatudTahed.clear();
		String elem="";
		if (p_v_a.equals("p")){
			for (int i = rida, j = tulp; j < tulp+sona.length(); j++){
				//skaneerin ridu, et pyyda t2hti ja s6naosasid, mida kasutades v6iks AI k2igu sooritafa
				elem = "";
				while (!Mang.MangulaudMassiiv[i][j].equals(" ") && !Mang.MangulaudMassiiv[i][j].equals("2xs") && !Mang.MangulaudMassiiv[i][j].equals("3xs") && !Mang.MangulaudMassiiv[i][j].equals("3xt") && !Mang.MangulaudMassiiv[i][j].equals("2xt")){					
					elem+=Mang.MangulaudMassiiv[i][j].toLowerCase();
					if (j < tulp+sona.length()) j+=1;
				}
			}
		}
		if (p_v_a.equals("a")){
			for (int i = rida, j = tulp; i < rida+sona.length(); i++){
				//skaneerin ridu, et pyyda t2hti ja s6naosasid, mida kasutades v6iks AI k2igu sooritafa
				elem = "";
				while (!Mang.MangulaudMassiiv[i][j].equals(" ") && !Mang.MangulaudMassiiv[i][j].equals("2xs") && !Mang.MangulaudMassiiv[i][j].equals("3xs") && !Mang.MangulaudMassiiv[i][j].equals("3xt") && !Mang.MangulaudMassiiv[i][j].equals("2xt")){					
					elem+=Mang.MangulaudMassiiv[i][j].toLowerCase();
					if (i < rida+sona.length()) i+=1;
				}
			}
		}
		int indeks = sona.indexOf(elem);
		for (int k = 0; k < sona.length(); k++){
    		if (k < indeks || k > indeks-1+elem.length()){
    			Mang.kasutatudTahed.add(sona.toCharArray()[k]);
    		}
		}
    	for (char taht : Mang.kasutatudTahed){
    		if (tahed.indexOf(taht)<0){
    			Mang.kasutatudTahed.clear();
    			return false;
    		}
    		else tahed = tahed.replace(taht, '0');
    	}
    	Mang.kasutatudTahed.clear();
    	return true;
	}
	

}