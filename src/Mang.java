import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Mang {
	public static char [] TahedInimene = new char [7];
	public static char [] TahedAI =  new char [7];
	public static String[][] MangulaudMassiiv = Mangulaud.LooMangulaud();
	public static int AISkoor = 0;
	public static int InimeseSkoor = 0;
	public static int punktid = 0;
	public static InfoInputist inputObjekt;
	public static ArrayList<Character> kasutatudTahed = new ArrayList<Character>();
	public static int sonaKordaja = 1;
	protected static int voor = 0;
	static ArrayList<Character> TaheKott;
	public static ArrayList<String> sonaraamat = new ArrayList<String>();
	public static ArrayList<char[]> charid = new ArrayList<char[]>();
	static GUI myGame;
	
	public static void main(String[] args) throws IOException{
		failistJarjendisse();
		Tahed2.setKott();
		TaheKott = Tahed2.getKott();
		for (int i = 0; i < TahedInimene.length; i++){
			TahedInimene[i] = Tahed2.kott(TaheKott);
		}
		for (int i = 0; i < TahedAI.length; i++){
			TahedAI[i] = Tahed2.kott(TaheKott);
		}
		myGame = new GUI();
			//1. m2ngija
			//kasutatudTahed.clear();
			myGame.setTähed(TahedInimene);
	}
	
	public static char[] removeElements(char[] input, char deleteMe) {
		if (input.length==1) {
			return new char[0];
		}
	    char [] result = new char[input.length-1];
	    int i = 0;
	    for(char item : input){
	    	if(deleteMe!=item && item!='\0'){
	            result[i]=item;
	            System.out.println("alles veel täht " + item);
	            i+=1;
	        }
	        else break;
	    }
	    System.out.println("tsükkel läbi ");
	    for (;i<result.length; i++){
	    	 result[i]=input[i+1];
	    }
	    return result;
	}
	
	static boolean mangKestab() {
		// TODO Auto-generated method stub
		if (TahedInimene.length == 0 || TahedAI.length == 0) return false;
		else return true;
	}

	public static void TahtedeHaldamine(String input){
		System.out.println("Tahtede haldamine input " + input);
		inputObjekt = new InfoInputist(input);
		System.out.println("sõne ise " + inputObjekt.sõne_ise);
		//nyyd tuleb reaalne tabelisse kirjutamine 
		punktid = 0;
		sonaKordaja = 1;
		kasutatudTahed.clear();
		if (inputObjekt.p_v_a.equals("a")){
			for (int i = inputObjekt.rida, indeks = 0; indeks<inputObjekt.sõne_ise.length(); i++, indeks++){
				if (MangulaudMassiiv[i][inputObjekt.tulp].equals("3xt")){
					punktid = punktid + 3*Tahed.vaartus(inputObjekt.sõne_ise.charAt(indeks));
					kasutatudTahed.add(inputObjekt.sõne_ise.charAt(indeks));
					myGame.textArea1.append("lisati " + inputObjekt.sõne_ise.charAt(indeks) + " punktid" + punktid);
					myGame.textArea1.append("tähe väärtus on "+ Tahed.vaartus(inputObjekt.sõne_ise.charAt(indeks)));

				}
				else if (MangulaudMassiiv[i][inputObjekt.tulp].equals("2xt")){
					punktid = punktid + 2*Tahed.vaartus(inputObjekt.sõne_ise.charAt(indeks));
					kasutatudTahed.add(inputObjekt.sõne_ise.charAt(indeks));
					myGame.textArea1.append("lisati " + inputObjekt.sõne_ise.charAt(indeks)+ " punktid" + punktid);
					myGame.textArea1.append("tähe väärtus on "+ Tahed.vaartus(inputObjekt.sõne_ise.charAt(indeks)));


				}
				else if (MangulaudMassiiv[i][inputObjekt.tulp].equals("3xs")){
					sonaKordaja*=3;
					punktid = punktid + Tahed.vaartus(inputObjekt.sõne_ise.charAt(indeks));
					kasutatudTahed.add(inputObjekt.sõne_ise.charAt(indeks));
					myGame.textArea1.append("lisati " + inputObjekt.sõne_ise.charAt(indeks)+ " punktid" + punktid);
					myGame.textArea1.append("tähe väärtus on "+ Tahed.vaartus(inputObjekt.sõne_ise.charAt(indeks)));


				}
				else if (MangulaudMassiiv[i][inputObjekt.tulp].equals("2xs")){
					sonaKordaja*=2;
					punktid = punktid + Tahed.vaartus(inputObjekt.sõne_ise.charAt(indeks));
					kasutatudTahed.add(inputObjekt.sõne_ise.charAt(indeks));
					myGame.textArea1.append("lisati " + inputObjekt.sõne_ise.charAt(indeks)+ " punktid" + punktid);
					myGame.textArea1.append("tähe väärtus on "+ Tahed.vaartus(inputObjekt.sõne_ise.charAt(indeks)));


				}
				else if (MangulaudMassiiv[i][inputObjekt.tulp].equalsIgnoreCase(Character.toString(inputObjekt.sõne_ise.charAt(indeks)))){
					punktid = punktid + Tahed.vaartus(inputObjekt.sõne_ise.charAt(indeks));
					myGame.textArea1.append("haldamine: " +inputObjekt.sõne_ise.charAt(indeks)+ " ühtib " + MangulaudMassiiv[i][inputObjekt.tulp]+ " punktid" + punktid);
					myGame.textArea1.append("tähe väärtus on "+ Tahed.vaartus(inputObjekt.sõne_ise.charAt(indeks)));

				}
				else{ 
					punktid = punktid+ Tahed.vaartus(inputObjekt.sõne_ise.charAt(indeks));
					kasutatudTahed.add(inputObjekt.sõne_ise.charAt(indeks));
					myGame.textArea1.append("lisati " + inputObjekt.sõne_ise.charAt(indeks)+ " punktid" + punktid);
					myGame.textArea1.append("tähe väärtus on "+ Tahed.vaartus(inputObjekt.sõne_ise.charAt(indeks)));

				}
			}
		}
		else if (inputObjekt.p_v_a.equals("p")){
			for (int j = inputObjekt.tulp, indeks = 0; indeks<inputObjekt.sõne_ise.length(); j++, indeks++){
				if (MangulaudMassiiv[inputObjekt.rida][j].equals("3xt")){
					punktid = punktid+ 3*Tahed.vaartus(inputObjekt.sõne_ise.charAt(indeks));
					kasutatudTahed.add(inputObjekt.sõne_ise.charAt(indeks));
					myGame.textArea1.append("lisati " + inputObjekt.sõne_ise.charAt(indeks)+ " punktid" + punktid);
					myGame.textArea1.append("tähe väärtus on "+ Tahed.vaartus(inputObjekt.sõne_ise.charAt(indeks)));
				}
				else if (MangulaudMassiiv[inputObjekt.rida][j].equals("2xt")){
					punktid = punktid + 2*Tahed.vaartus(inputObjekt.sõne_ise.charAt(indeks));
					kasutatudTahed.add(inputObjekt.sõne_ise.charAt(indeks));
					myGame.textArea1.append("lisati " + inputObjekt.sõne_ise.charAt(indeks)+ " punktid" + punktid);
					myGame.textArea1.append("tähe väärtus on "+ Tahed.vaartus(inputObjekt.sõne_ise.charAt(indeks)));
				}
				else if (MangulaudMassiiv[inputObjekt.rida][j].equals("3xs")){
					sonaKordaja*=3;
					punktid = punktid + Tahed.vaartus(inputObjekt.sõne_ise.charAt(indeks));
					kasutatudTahed.add(inputObjekt.sõne_ise.charAt(indeks));
					myGame.textArea1.append("lisati " + inputObjekt.sõne_ise.charAt(indeks)+ " punktid" + punktid);
					myGame.textArea1.append("tähe väärtus on "+ Tahed.vaartus(inputObjekt.sõne_ise.charAt(indeks)));

				}
				else if (MangulaudMassiiv[inputObjekt.rida][j].equals("2xs")){
					sonaKordaja*=2;
					punktid = punktid+ Tahed.vaartus(inputObjekt.sõne_ise.charAt(indeks));
					kasutatudTahed.add(inputObjekt.sõne_ise.charAt(indeks));
					myGame.textArea1.append("lisati " + inputObjekt.sõne_ise.charAt(indeks)+ " punktid" + punktid);
					myGame.textArea1.append("tähe väärtus on "+ Tahed.vaartus(inputObjekt.sõne_ise.charAt(indeks)));
				}
				else if (MangulaudMassiiv[inputObjekt.rida][j].equalsIgnoreCase(Character.toString(inputObjekt.sõne_ise.charAt(indeks)))){
					System.out.println("haldamine: ühtib " + MangulaudMassiiv[inputObjekt.rida][j]);
					punktid = punktid+ Tahed.vaartus(inputObjekt.sõne_ise.charAt(indeks));
					myGame.textArea1.append("haldamine: ühtib " + inputObjekt.sõne_ise.charAt(indeks)+ " punktid" + punktid);
					myGame.textArea1.append("tähe väärtus on "+ Tahed.vaartus(inputObjekt.sõne_ise.charAt(indeks)));
				}
				else{
					punktid = punktid + Tahed.vaartus(inputObjekt.sõne_ise.charAt(indeks));
					kasutatudTahed.add(inputObjekt.sõne_ise.charAt(indeks));
					myGame.textArea1.append("lisati " + inputObjekt.sõne_ise.charAt(indeks)+ " punktid" + punktid);
					myGame.textArea1.append("tähe väärtus on "+ Tahed.vaartus(inputObjekt.sõne_ise.charAt(indeks)));
				}
			}
		}
		punktid *= sonaKordaja;
		myGame.textArea1.append("punkte " + punktid);
	}
	
	/*public static Boolean tahedKlapivad(String elem, String sona, String tahed){
		kasutatudTahed.clear();
		int indeks = sona.indexOf(elem);
    	for (int k = 0; k < sona.length(); k++){
    		if (k < indeks || k > indeks-1+elem.length()){
    			kasutatudTahed.add(sona.toCharArray()[k]);
    		}
		}
    	for (char taht : kasutatudTahed){
    		if (tahed.indexOf(taht)<0){
    			kasutatudTahed.clear();
    			return false;
    		}
    		else tahed = tahed.replace(taht, '0');
    	}
    	kasutatudTahed.clear();
    	return true;
	}
	*/
	public static void failistJarjendisse() throws IOException{
		String pwd = System.getProperty("user.dir");
		File sonadFile = new File(pwd + "/src/sonad.txt");
		FileInputStream fstream = new FileInputStream(sonadFile);
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String rida;
		try {
			while ((rida = br.readLine()) != null) {
				sonaraamat.add(rida);
				charid.add(rida.toCharArray());
			}	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} br.close();
	}
}