public class tahed {

	public static int vaartus(char t2ht){
		//Et ei oleks vahet, kas sisestatakse suur või väike täht
		char t2htUus = Character.toLowerCase(t2ht);
		
		//Väärtused
        switch (t2htUus) {
            case 'a': case 'e': case 'i': case 's': case 't': case 'k': case 'l': case 'o': case 'u':
            	return 1;
            
            case 'd': case 'm': case 'n': case 'r':
            	return 2;
            	
            case 'g': case 'v':
            	return 3;
            
            case 'b': case 'h': case 'j': case 'p': case 'õ':
            	return 4;
            	
            case 'ä': case 'ü':
            	return 5;
            	
            case 'ö':
            	return 6;
            	
            case 'f':
            	return 8;
            	
            case 'š': case 'z': case 'ž':
            	return 10;
                       
            default: 
            	return 0;
        }
	}

	
	
	public static void main(String[] args){
		char taht = 'Z';
		System.out.println(vaartus(taht));
	}
}