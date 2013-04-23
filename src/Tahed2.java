import java.util.*;

public class Tahed2 {
	public static ArrayList<Character> tahed = new ArrayList<Character>();
	
	public static void setKott (){
		LooKott();
	}
	
	public static char kott(ArrayList<Character> kott){
		
		char tyhi = '\0';
		Random r = new Random();
		char taht;
		
		if(kott.size() == 0)
			return tyhi;
		
		else
			taht = kott.get(r.nextInt(kott.size()));
		
			int asukoht = kott.indexOf(taht);
			kott.remove(asukoht);
			
			tahed = kott;
			
			return taht;
	}
	
	public static ArrayList<Character> getKott(){
		return tahed;
	}
	
	
	public static void LooKott(){	
		Collections.addAll(tahed,'a','a','a','a','a','a','a','a','a','a',
				'i','i','i','i','i','i','i','i','i',
				'e','e','e','e','e','e','e','e','e',
				's','s','s','s','s','s','s','s',
				't','t','t','t','t','t','t',
				'k','k','k','k','k',
				'l','l','l','l','l',
				'o','o','o','o','o',
				'u','u','u','u','u',
				'n','n','n','n',
				'd','d','d','d',
				'm','m','m','m',
				'r','r',
				'v','v',
				'g','g',
				'h','h',
				'j','j',
				'p','p',
				'õ','õ',
				'ä','ä',
				'ö','ö',
				'ü','ü',
				'f',
				'š',
				'z',
				'ž',
				'b');

	}

}
