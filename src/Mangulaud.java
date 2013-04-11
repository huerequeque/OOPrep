import java.util.Arrays;

public class Mangulaud {
	/**
	 * @param args
	 */
	public static String[][] LooMangulaud() {
		//loon tyhja 15x15 m2ngulaua
		String[][] Mangulaud = new String[15][15];	
		//edasist saanuks muudmoodi teha, aga mina tegin nii. t2idan selle tyhikutega
		for (String [] row : Mangulaud){
			Arrays.fill(row, " ");}
		//selle asemel, et read t2ielikult sisse toksida, pyyan 2ra kasutada scrabble-i laua mustrit
		int[] a = {2, 6, 8, 12};
		int[] b = {0, 7, 14};
		int[] c = {1, 2, 3, 4, 7, 10, 11, 12, 13};
		int[] d = {5, 9};
		int[] e = {1, 13};
		int[] f = {1, 5, 9, 13};
		int[] g = {3, 11};
		int[] h = {6, 8};
		int[] i = {2, 12};
		
		for (int elemA : b){
			for (int elemB : b){
			Mangulaud[elemA][elemB]="3xs";
			}
		}
		for (int elemA : c){			
			Mangulaud[elemA][elemA]="2xs";			
		}
		for (int elemA = 0; elemA < c.length; elemA++){			
			Mangulaud[c[elemA]][c[c.length-1-elemA]]="2xs";			
		}
		for (int elemA : d){
			for (int elemB : f){
			Mangulaud[elemA][elemB]="3xt";
			}
		}
		for (int elemA : e){
			for (int elemB : d){
			Mangulaud[elemA][elemB]="3xt";
			}
		}
		for (int elemA : b){
			for (int elemB : g){
			Mangulaud[elemA][elemB]="2xt";
			}
		}
		for (int elemA : g){
			for (int elemB : b){
			Mangulaud[elemA][elemB]="2xt";
			}
		}
		for (int elemA : h){
			for (int elemB : a){
			Mangulaud[elemA][elemB]="2xt";
			}
		}
		for (int elemA : i){
			for (int elemB : h){
			Mangulaud[elemA][elemB]="2xt";
			}
		}
		return Mangulaud;
		}


	public static void KuvaMangulaud(String[][] Mangulaud){
        int k = 1;
        while(k <= 63){ System.out.print('-'); k++; 
        }
        System.out.println();
        //eelnevalt loodud kahem66tmeline j2rjend saab kuvatud ruudustikus
        //iga tulba peal on number, et aidata m2ngijal inputti s6nu panna
        System.out.print("   _");
        int l = 0;
        while(l <= 14){ 
        	if (l<10) System.out.print(l+"___");
        	else if (l<14) System.out.print(l+"__"); 
        	else System.out.print(l+"_");
        	l++; 
        }
        System.out.println();
        //iga rea ees on samuti number
        for (int i = 0; i < Mangulaud.length; i++){
        	if (i<10) System.out.print(" "+i);
        	else System.out.print(i);       	
             for(int j = 0; j < Mangulaud[i].length; j++){
            	 if (Mangulaud[i][j].length() == 1 && !Mangulaud[i][j].equals(" "))            	 
            		 System.out.print("|_" + Mangulaud[i][j].toUpperCase()+tahed.vaartus(Mangulaud[i][j].charAt(0)));
            	 else if (Mangulaud[i][j].length() == 1)            	 
                     System.out.print("|_" + Mangulaud[i][j] + "_");
            	 if (Mangulaud[i][j].length() == 3)            	 
                     System.out.print("|" + Mangulaud[i][j]);
             }
             System.out.print("|");
             System.out.println();
         }
         k = 1;
         while(k <= 63){ System.out.print('-'); k++; 
         }
         System.out.println();
     }

}
