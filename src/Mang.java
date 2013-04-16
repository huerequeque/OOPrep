import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

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
	public static String sisend = "0, 0 x x";

	public static void main(String[] args) throws FileNotFoundException{
		tahed2.setKott();
		ArrayList<Character> TaheKott = tahed2.getKott();
		for (int i = 0; i < TahedInimene.length; i++){
			TahedInimene[i] = tahed2.kott(TaheKott);
		}
		for (int i = 0; i < TahedAI.length; i++){
			TahedAI[i] = tahed2.kott(TaheKott);
		}
		int voor = 0;
		while (mangKestab()){
			//1. m2ngija
			kasutatudTahed.clear();
			Mangulaud.KuvaMangulaud(MangulaudMassiiv);
			Scanner scan = new Scanner(System.in);
			System.out.print("Sinu tähed on " + Character.toUpperCase(TahedInimene[0])+tahed.vaartus(TahedInimene[0]));
			for (int i=1; i<TahedInimene.length; i++){
				System.out.print(", " + Character.toUpperCase(TahedInimene[i])+tahed.vaartus(TahedInimene[i]));
			}
			System.out.println();
			System.out.println("Sisesta sõna");
			sisend = scan.nextLine();
			if (voor == 0){
				TahtedeHaldamine(sisend);
			}
			else if (Laud_input_kontroll.kontroll(sisend)){
				TahtedeHaldamine(sisend);
			}
			else {
				System.out.println("proovi midagi muud.");
				continue;
			}
			InimeseSkoor += punktid;
			System.out.println("Said " + punktid + " punkti! Kokku on sul " + InimeseSkoor + " punkti.");				
			if (tahed2.kott(TaheKott) !='\0'){
				for (char taht : kasutatudTahed){ 
					TahedInimene[new String(TahedInimene).indexOf(taht)] = tahed2.kott(TaheKott);
				}
			}						
			else {
				for (char taht : kasutatudTahed){ 
					TahedInimene = removeElements(TahedInimene, taht);
				}
			}
			Mangulaud.KuvaMangulaud(MangulaudMassiiv);
			System.out.println("Vastase kord.");
			//2. m2ngija
			punktid = 0;
			System.out.print("Vastase tähed on " + Character.toUpperCase(TahedAI[0])+tahed.vaartus(TahedAI[0]));
			for (int i=1; i<TahedAI.length; i++){
				System.out.print(", " + Character.toUpperCase(TahedAI[i])+tahed.vaartus(TahedAI[i]));
			}
			System.out.println();
			System.out.println("Vastane ragistab ajusid.");
			kasutatudTahed.clear();
			sisend = AI.SkaneeriLauda(MangulaudMassiiv);
			if (sisend.equals("jääb vahele")){
				System.out.println("Vastane jättis käigu vahele. Sinu kord.");
			}
			else {
				TahtedeHaldamine(sisend);
				System.out.println("Vastase sõna on " + inputObjekt.sõne_ise);
				AISkoor += punktid;
			}
			System.out.println("Vastane sai " + punktid + " punkti! Kokku on tal " + AISkoor + " punkti.");				
			if (tahed2.kott(TaheKott) !='\0'){
				if (kasutatudTahed.size()==0) TahedAI[(int) (Math.random()*TahedAI.length)] = tahed2.kott(TaheKott);
				else for (char taht : kasutatudTahed){
					TahedAI[new String(TahedAI).indexOf(taht)] = tahed2.kott(TaheKott);
				}
			}						
			else {
				for (char taht : kasutatudTahed){ 
					TahedAI = removeElements(TahedAI, taht);
				}
			}
			
		}
		System.out.println("Mäng läbi!");
	}
	
	public static char[] removeElements(char[] input, char deleteMe) {
	    char [] result = new char[input.length-1];
	    int i = 0;
	    for(char item : input){
	        if(deleteMe!=item){
	            result[i]=item;
	            i+=1;
	        }
	        else i +=1;
	    }
	    return result;
	}
	
	private static boolean mangKestab() {
		// TODO Auto-generated method stub
		if (TahedInimene.length == 0 | TahedAI.length == 0) return false;
		else return true;
	}

	public static void TahtedeHaldamine(String input){
		inputObjekt = new InfoInputist(input);
		//nyyd tuleb reaalne tabelisse kirjutamine 
		punktid = 0;
		sonaKordaja = 1;
		if (inputObjekt.p_v_a.equals("a")){
			for (int i = inputObjekt.rida, indeks = 0; indeks<inputObjekt.sõne_ise.length(); i++, indeks++){
				if (MangulaudMassiiv[i][inputObjekt.tulp].equals("3xt")){
					MangulaudMassiiv[i][inputObjekt.tulp]=Character.toString(inputObjekt.sõne_ise.charAt(indeks));
					punktid += 3*tahed.vaartus(inputObjekt.sõne_ise.charAt(indeks));
					kasutatudTahed.add(inputObjekt.sõne_ise.charAt(indeks));
				}
				else if (MangulaudMassiiv[i][inputObjekt.tulp].equals("2xt")){
					MangulaudMassiiv[i][inputObjekt.tulp]=Character.toString(inputObjekt.sõne_ise.charAt(indeks));
					punktid += 2*tahed.vaartus(inputObjekt.sõne_ise.charAt(indeks));
					kasutatudTahed.add(inputObjekt.sõne_ise.charAt(indeks));
				}
				else if (MangulaudMassiiv[i][inputObjekt.tulp].equals("3xs")){
					MangulaudMassiiv[i][inputObjekt.tulp]=Character.toString(inputObjekt.sõne_ise.charAt(indeks));
					sonaKordaja*=3;
					punktid += tahed.vaartus(inputObjekt.sõne_ise.charAt(indeks));
					kasutatudTahed.add(inputObjekt.sõne_ise.charAt(indeks));
				}
				else if (MangulaudMassiiv[i][inputObjekt.tulp].equals("2xs")){
					System.out.println(Character.toString(inputObjekt.sõne_ise.charAt(indeks)));
					MangulaudMassiiv[i][inputObjekt.tulp]=Character.toString(inputObjekt.sõne_ise.charAt(indeks));
					sonaKordaja*=2;
					punktid += tahed.vaartus(inputObjekt.sõne_ise.charAt(indeks));
					kasutatudTahed.add(inputObjekt.sõne_ise.charAt(indeks));
				}
				else if (!MangulaudMassiiv[i][inputObjekt.tulp].equals(Character.toString(inputObjekt.sõne_ise.charAt(indeks)))){
					MangulaudMassiiv[i][inputObjekt.tulp]=Character.toString(inputObjekt.sõne_ise.charAt(indeks));
					punktid += tahed.vaartus(inputObjekt.sõne_ise.charAt(indeks));
					kasutatudTahed.add(inputObjekt.sõne_ise.charAt(indeks));
				}
				else{ 
					punktid += tahed.vaartus(inputObjekt.sõne_ise.charAt(indeks));
					MangulaudMassiiv[i][inputObjekt.tulp]=Character.toString(inputObjekt.sõne_ise.charAt(indeks));
				}
			}
		}
		else if (inputObjekt.p_v_a.equals("p")){
			for (int j = inputObjekt.tulp, indeks = 0; indeks<inputObjekt.sõne_ise.length(); j++, indeks++){
				if (MangulaudMassiiv[inputObjekt.rida][j].equals("3xt")){
					MangulaudMassiiv[inputObjekt.rida][j]=Character.toString(inputObjekt.sõne_ise.charAt(indeks));
					punktid += 3*tahed.vaartus(inputObjekt.sõne_ise.charAt(indeks));
					kasutatudTahed.add(inputObjekt.sõne_ise.charAt(indeks));
				}
				else if (MangulaudMassiiv[inputObjekt.rida][j].equals("2xt")){
					MangulaudMassiiv[inputObjekt.rida][j]=Character.toString(inputObjekt.sõne_ise.charAt(indeks));
					punktid += 2*tahed.vaartus(inputObjekt.sõne_ise.charAt(indeks));
					kasutatudTahed.add(inputObjekt.sõne_ise.charAt(indeks));
				}
				else if (MangulaudMassiiv[inputObjekt.rida][j].equals("3xs")){
					MangulaudMassiiv[inputObjekt.rida][j]=Character.toString(inputObjekt.sõne_ise.charAt(indeks));
					sonaKordaja*=3;
					punktid += tahed.vaartus(inputObjekt.sõne_ise.charAt(indeks));
					kasutatudTahed.add(inputObjekt.sõne_ise.charAt(indeks));
				}
				else if (MangulaudMassiiv[inputObjekt.rida][j].equals("2xs")){
					MangulaudMassiiv[inputObjekt.rida][j]=Character.toString(inputObjekt.sõne_ise.charAt(indeks));
					sonaKordaja*=2;
					punktid += tahed.vaartus(inputObjekt.sõne_ise.charAt(indeks));
					kasutatudTahed.add(inputObjekt.sõne_ise.charAt(indeks));
				}
				else if (!MangulaudMassiiv[inputObjekt.rida][j].equals(Character.toString(inputObjekt.sõne_ise.charAt(indeks)))){
					MangulaudMassiiv[inputObjekt.rida][j]=Character.toString(inputObjekt.sõne_ise.charAt(indeks));
					punktid += tahed.vaartus(inputObjekt.sõne_ise.charAt(indeks));
					kasutatudTahed.add(inputObjekt.sõne_ise.charAt(indeks));
				}
				else{ 
					punktid += tahed.vaartus(inputObjekt.sõne_ise.charAt(indeks));
					MangulaudMassiiv[inputObjekt.rida][j]=Character.toString(inputObjekt.sõne_ise.charAt(indeks));
				}
			}
		}
		punktid *= sonaKordaja;
	}
	
	public static Boolean tahedKlapivad(String elem, String sona, String tahed){
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
}