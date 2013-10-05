import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;


public class InputCheck {

	public static boolean kontroll(String sisend) {
		System.out.println("-----------------------------\nsisend: " + sisend);
		sisend = sisend.replace(",", "");
		String[] jupid = sisend.split(" ");
		int rida = Integer.parseInt(jupid[0]); //index
		int tulp = Integer.parseInt(jupid[1]); //index
		String p_v_a = jupid[2];
		String sõne_ise = jupid[3];
		String kelleKäik = jupid[4];
		char[] charMassiv;
		if(kelleKäik.equalsIgnoreCase("USER")){
			charMassiv = Mang.TahedInimene.clone();
		}else{
			charMassiv = Mang.TahedAI.clone();
		}
		return checkData(rida, tulp, sõne_ise, p_v_a, charMassiv, kelleKäik);
	}

	private static boolean checkData(int rida, int tulp, String sõne_ise, String p_v_a, char[] charMassiv, String kelleKäik){
		System.out.println("checkData: " + rida + " " + tulp + " " + sõne_ise + " " + p_v_a );
		if(!checkIfFirstTurnAndNotMid(rida, tulp, sõne_ise.length(), p_v_a)){
			System.out.println("FirstTurnAndNotMid");
			if(kelleKäik.equalsIgnoreCase("USER")){
				Mang.myGame.textArea1.append("Mängu esimesel käigul laotud sõna peab läbima laua keskpunkti!\n");
				Mang.myGame.textArea1.setCaretPosition(Mang.myGame.textArea1.getDocument().getLength());
			}
			return false;
		}
		if(!checkIfFits(rida, tulp, sõne_ise.length(), p_v_a)){
			System.out.println("checkIfFits");
			return false;
		}
		if(!checkIfHasLettersAndMatchesWithCurrentBoard(rida, tulp, sõne_ise, p_v_a, charMassiv)){
			System.out.println("checkIfHasLettersAndMatchesWithCurrentBoard");
			return false;
		}
		if(!checkAllPerpendicularWords(rida, tulp, sõne_ise, p_v_a, kelleKäik)){
			System.out.println("checkAllPerpendicularWords");
			return false;
		}
		return true;
	}
	private static boolean checkIfFirstTurnAndNotMid(int rida, int tulp, int length, String p_v_a) {
		if(Mang.voor<1){
			if(p_v_a.equalsIgnoreCase("p")){
				if (rida==7 && tulp+length-1>7){
					return true;
				}
				else return false;
			}else if(p_v_a.equalsIgnoreCase("a")){
				if (tulp==7 && rida+length-1>7){
					return true;
				}
				else return false;
			}else{
				return false;
			}
		}
		return true;
	}

	private static boolean checkAllPerpendicularWords(int rida, int tulp, String sõne_ise, String p_v_a, String kelleKäik) {
		System.out.println("checkAllPerpendicularWords: " + rida + " " + tulp + " " + sõne_ise + " " + p_v_a );
		ArrayList<String> relevantRowsAndColumns = new ArrayList<String>();
		if(p_v_a.equalsIgnoreCase("p")){
			relevantRowsAndColumns.add(getPhantomWord(getRow(rida), tulp, sõne_ise));
			for (int i=0; i<sõne_ise.length(); i++){
				relevantRowsAndColumns.add(getWord(getColumn(tulp+i), rida, sõne_ise.charAt(i)));
			}
		}else if(p_v_a.equalsIgnoreCase("a")){
			relevantRowsAndColumns.add(getPhantomWord(getColumn(tulp), rida, sõne_ise));
			for (int i=0; i<sõne_ise.length(); i++){
				relevantRowsAndColumns.add(getWord(getRow(rida+i), tulp, sõne_ise.charAt(i)));
			}
		}else return false;
		for(String word : relevantRowsAndColumns){
			if(word.length()!=1 && !checkIfWord(word, kelleKäik)) {
				System.out.println(word + " - ei ole sõna! ");
				return false;
			}
		}
		return true;
	}

	private static String getPhantomWord(String[] märgid, int alguskoht, String sõne_ise) {
		System.out.println("getPhantomWord: " + alguskoht + " " + sõne_ise);
		String[] algus = Arrays.copyOfRange(märgid, 0, alguskoht);
		String[] lõpp = new String[0];
		if(alguskoht!=0 && alguskoht+sõne_ise.length()!=15){
			lõpp = Arrays.copyOfRange(märgid, alguskoht+sõne_ise.length(), 14);
		}
		String algusSõne = "";
		String lõppSõne = "";
		for (int j = algus.length - 1; j >= 0; j--) {
			String l = algus[j];
			if (l.equalsIgnoreCase("3xs") || l.equalsIgnoreCase("2xs") || l.equalsIgnoreCase(" ") || l.equalsIgnoreCase("2xt") || l.equalsIgnoreCase("3xt")) {
				break;
			} else algusSõne = l + algusSõne;
		}
		for (String l : lõpp) {
			if (l.equalsIgnoreCase("3xs") || l.equalsIgnoreCase("2xs") || l.equalsIgnoreCase(" ") || l.equalsIgnoreCase("2xt") || l.equalsIgnoreCase("3xt")) {
				break;
			} else lõppSõne += l;
		}
		System.out.println("algusSõne: " + algusSõne + " lõppSõne: " + lõppSõne);
		return (algusSõne + sõne_ise + lõppSõne).toLowerCase();
	}

	private static String getWord(String[] märgid, int keskkoht, char c) {
		System.out.println("getWord: " + keskkoht + " " + c);
		String[] algus = Arrays.copyOfRange(märgid, 0, keskkoht);
		String[] lõpp = new String[0];
		if(keskkoht!=0 && keskkoht+1!=16){
			lõpp = Arrays.copyOfRange(märgid, keskkoht+1, 15);
		}
		String algusSõne = "";
		String lõppSõne = "";
		for (int j = algus.length - 1; j >= 0; j--) {
			String l = algus[j];
			if (l.equalsIgnoreCase("3xs") || l.equalsIgnoreCase("2xs") || l.equalsIgnoreCase(" ") || l.equalsIgnoreCase("2xt") || l.equalsIgnoreCase("3xt")) {
				break;
			} else algusSõne = l + algusSõne;
		}
		for (String l : lõpp) {
			if (l.equalsIgnoreCase("3xs") || l.equalsIgnoreCase("2xs") || l.equalsIgnoreCase(" ") || l.equalsIgnoreCase("2xt") || l.equalsIgnoreCase("3xt")) {
				break;
			} else lõppSõne += l;
		}
		return (algusSõne + c + lõppSõne).toLowerCase();
	}

	private static String[] getRow(int rida) {
		return Mang.MangulaudMassiiv[rida];
	}

	private static String[] getColumn(int tulp) {
		String[] mängulauaTulp = new String[15];
		for(int i=0; i<=14; i++){
			mängulauaTulp[i]=Mang.MangulaudMassiiv[i][tulp];
		}
		return mängulauaTulp;
	}

	//reminder: Mang.Mängulaudmassiiv[rida][tulp]
	private static boolean checkIfHasLettersAndMatchesWithCurrentBoard(int rida, int tulp, String sõne_ise, String p_v_a, char[] charMassiv) {
		System.out.println("checkIfHasLettersAndMatchesWithCurrentBoard: " + rida + " " + tulp + " "+sõne_ise + " "+p_v_a);
		ArrayList<String> characters = new ArrayList<String>();
		for(char c:charMassiv){
			characters.add(String.valueOf(c).toLowerCase());
		}
		int emptySlots = 0;
		if(p_v_a.equalsIgnoreCase("p")){
			for(int i=0; i<sõne_ise.length(); i++){
				if(isEmpty(rida, tulp+i)){
					emptySlots++;
					if(!characters.remove(sõne_ise.substring(i, i+1).toLowerCase())){
						System.out.println("viga siin " + sõne_ise.substring(i, i+1));
						return false;
					}
				}else{
					System.out.println(tulp);
					String tähtVäljakul = Mang.MangulaudMassiiv[rida][tulp+i];
					if(!tähtVäljakul.equalsIgnoreCase(sõne_ise.substring(i, i+1))){
						System.out.println("viga siin " + sõne_ise.substring(i, i+1));
						return false;
					}
				}
			}
		}else if(p_v_a.equalsIgnoreCase("a")){
			for(int i=0; i<sõne_ise.length(); i++){
				if(isEmpty(rida+i, tulp)){
					emptySlots++;
					if(!characters.remove(sõne_ise.substring(i, i+1).toLowerCase())){
						System.out.println("viga siin " + sõne_ise.substring(i, i+1));
						return false;
					}
				}else{
					String tähtVäljakul = Mang.MangulaudMassiiv[rida+i][tulp];
					if(!tähtVäljakul.equalsIgnoreCase(sõne_ise.substring(i, i+1))) {
						System.out.println("viga siin " + sõne_ise.substring(i, i+1));
						return false;
					}
				}
			}
		}else{
			return false;
		}
		if(emptySlots==0) {
			System.out.println("Ühtegi vaba ruutu ei täidetud");
			return false;
		}

		return true;
	}

	private static boolean checkIfWord(String sõne_ise, String kelleKäik) {
		for (String sona : Mang.sonaraamat){
			if (sõne_ise.equalsIgnoreCase(sona))
				return true;
		}
		return false;
	}

	private static boolean checkIfFits(int rida, int tulp, int length, String p_v_a) {
		if(rida>14 || tulp>14) return false;

		if(p_v_a.equalsIgnoreCase("a")){
			if(rida+length-1>14){
				return false;
			}else{
				return true;
			}
		}else if(p_v_a.equalsIgnoreCase("p")){
			if(tulp+length-1>14){
				return false;
			}else{
				return true;
			}
		}else{
			return false;
		}
	}

	private static boolean isEmpty(int r, int t) {
		if (r < 15 && t < 15 && r >= 0 && t >= 0){
			String täht = Mang.MangulaudMassiiv[r][t];
			return täht.equalsIgnoreCase(" ") ||
					täht.equalsIgnoreCase("") ||
					täht.equalsIgnoreCase("3xs") ||
					täht.equalsIgnoreCase("2xs") ||
					täht.equalsIgnoreCase("3xt") ||
					täht.equalsIgnoreCase("2xt");
		} else return false;
	}
}
