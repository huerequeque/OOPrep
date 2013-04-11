import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Laud_input_kontroll {

	private static int perpendicularCounter = 0;
	private static String[][] MangulaudMassiiv = Mang.MangulaudMassiiv;

	public static void main (String args[]){
		MangulaudMassiiv[7][7] = "t";
		MangulaudMassiiv[7][8] = "a";
		MangulaudMassiiv[7][9] = "a";
		MangulaudMassiiv[7][10] = "k";
		Mangulaud.KuvaMangulaud(MangulaudMassiiv);
		System.out.println(Laud_input_kontroll.kontroll("6, 10 a ka"));
	  }
	
	public static boolean kontroll(String sisend) {
		perpendicularCounter = 0;
		sisend = sisend.replace(",", "");
		String[] jupid = sisend.split(" ");
		int rida = Integer.parseInt(jupid[0]);
		int tulp = Integer.parseInt(jupid[1]);
		String p_v_a = jupid[2];
		String sõne_ise = jupid[3];
		char[] charMassiv = sõne_ise.toCharArray();
		return checkData(rida, tulp, sõne_ise, p_v_a, charMassiv);
	}

	private static boolean checkData(int rida, int tulp, String sõne_ise, String p_v_a, char[] charMassiv) {
		if (!checkWord(sõne_ise)) {
			return false;
		}
		if (p_v_a.equalsIgnoreCase("p")) {
			int charIndeksTulp = tulp;
			for (char a : charMassiv) {
				if (!kontrolliChar(a, rida, charIndeksTulp, p_v_a)) {
					return false;
				}
				charIndeksTulp++;
			}
		} else if (p_v_a.equalsIgnoreCase("a")) {
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
		if (perpendicularCounter == 0) {
			return false;
		}
		return true;
	}

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

		if (rida > 14 || rida < 0 || tulp < 0 || tulp > 14) {
			return false;
		}
		if (!isEmpty(rida, tulp)) {
			if (!MangulaudMassiiv[rida][tulp].equalsIgnoreCase(String.valueOf(a))) {
				return false;
			}
		}
		String perpendicularWord = getPerpendicularWord(a, rida, tulp, p_v_a);

//		System.out.println("risti moodustub sõne: " + perpendicularWord);
		if (perpendicularWord.length() > 1) {
			if (checkWord(perpendicularWord)) {
				perpendicularCounter++;
			} else {
				return false;
			}
		}
		return true;
	}

	private static String getPerpendicularWord(char täht, int ridaIndeks, int tulpIndeks, String suund) {
		String[] terveVeergRida = new String[15];
		if (suund.equalsIgnoreCase("p")) {
			for (int i = 0; i < 15; i++) {
				terveVeergRida[i] = MangulaudMassiiv[i][tulpIndeks];
			}
			String[] lõpp = Arrays.copyOfRange(terveVeergRida, ridaIndeks + 1, 15);
			String[] algus = Arrays.copyOfRange(terveVeergRida, 0, ridaIndeks);
			return getWordFromArray(lõpp, algus, täht);
		} else {
			for (int i = 0; i < 15; i++) {
				terveVeergRida[i] = MangulaudMassiiv[ridaIndeks][i];
			}
			String[] lõpp = Arrays.copyOfRange(terveVeergRida, tulpIndeks + 1, 14);
			String[] algus = Arrays.copyOfRange(terveVeergRida, 0, tulpIndeks);
			return getWordFromArray(lõpp, algus, täht);
		}
	}

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

	private static boolean isEmpty(int r, int t) {
		return MangulaudMassiiv[r][t] == " " || MangulaudMassiiv[r][t] == "3xs" || MangulaudMassiiv[r][t] == "2xs" || MangulaudMassiiv[r][t] == "3xt" || MangulaudMassiiv[r][t] == "2xt";
	}

}
// ><