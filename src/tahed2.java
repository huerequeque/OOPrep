import java.util.*;

public class tahed2 {
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
		Collections.addAll(tahed,'a','a','a','a','a','a','a','a','a','a','i','i','i','i','i','i','i','i','i','i',
				'n','n','n','n','n','n','n','n','n','t','t','t','t','t','t','t','t','t','e','e','e','e','e','e','e','e',
				's','s','s','s','s','s','s','k','k','k','k','k','l','l','l','l','l','o','o','o','o','o',
				'ä','ä','ä','ä','ä','u','u','u','u','m','m','m','h','h','j','j','p','p','r','r','v','v','y','y',
				'd','ö','b','f','g','c');

	}

}
