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
		Collections.addAll(tahed,'a','a',
				'i','i',
				'e','e',
				's','s',
				't','t',
				'k','k',
				'l','l');

	}

}
