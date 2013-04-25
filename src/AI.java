import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;


public class AI {
	public static ArrayList<String> potentsiaalsed = new ArrayList<String>();

	public static String SkaneeriLauda(String Mangulaud[][]) throws FileNotFoundException{
		for (int i = 0; i < potentsiaalsed.size(); i ++){
			if (!AI_kontroll.kontroll(potentsiaalsed.get(i))){
				potentsiaalsed.remove(potentsiaalsed.get(i));
				i-=1;
			}
		}
		for (int i = 0; i < Mangulaud.length; i++){
			//skaneerin ridu, et pyyda t2hti ja s6naosasid, mida kasutades v6iks AI k2igu sooritafa
			String pyydja = "";
			for (int j = 0; j < Mangulaud.length; j++){
				while (!Mangulaud[i][j].equals(" ") && !Mangulaud[i][j].equals("2xs") && !Mangulaud[i][j].equals("3xs") && !Mangulaud[i][j].equals("3xt") && !Mangulaud[i][j].equals("2xt")){					
					pyydja+=Mangulaud[i][j];
					if (j < Mangulaud.length) j+=1;
				}
				//kui tulemus pole tyhi, saadetakse sonapakkuja meetodisse t2ht/s6naosa, m2ngija kasutada olevad t2hed, alguskoordinaat ja suund.
				if (!pyydja.equals("") && pyydja.length()<=6){
					if (!SonaPakkumine(pyydja, Mang.TahedAI, i, (j+1-pyydja.length()), "p").equals("0, 0 p xxxx")){
						//System.outprintln("pyydja on " + pyydja);
						potentsiaalsed.add(SonaPakkumine(pyydja, Mang.TahedAI, i, (j+1-pyydja.length()), "p"));
					}
				}
				pyydja = "";
			}
		}
		for (int j = 0; j < Mangulaud.length; j++){
			//skaneerin tulpi samal eesm2rgil
			String pyydja = "";
			for (int i = 0; i < Mangulaud.length; i++){
				while (!Mangulaud[i][j].equals(" ") && !Mangulaud[i][j].equals("2xs") && !Mangulaud[i][j].equals("3xs") && !Mangulaud[i][j].equals("3xt") && !Mangulaud[i][j].equals("2xt")){					
					pyydja+=Mangulaud[i][j];
					if (i < Mangulaud.length) i+=1;
				}
				if (!pyydja.equals("")){
					if (!SonaPakkumine(pyydja, Mang.TahedAI, (i+1-pyydja.length()), j, "a").equals("0, 0 p xxxx")){
						potentsiaalsed.add(SonaPakkumine(pyydja, Mang.TahedAI, (i+1-pyydja.length()), j, "a"));
					}
				}
				pyydja = "";
			}
		}
		if (potentsiaalsed.size() == 0) return ("jääb vahele");
		else return potentsiaalsed.get((int)(Math.random()*potentsiaalsed.size()));
	}
	
	public static String SonaPakkumine(String elem, char Tahed [], int i, int j, String p_v_a) throws FileNotFoundException {
		//teen regexi huvides t2hed s6neks
		elem = elem.toLowerCase();
		String TahedString = new String(Tahed);
		char [] tahedJaElement = new char[Tahed.length+elem.length()];
		int loendur = 0;
		for (char element : elem.toCharArray()){
			tahedJaElement[loendur] = element;
			loendur++;
		}
		for (char element : Tahed){
			tahedJaElement[loendur] = element;
			loendur++;
		}
		
		//System.outprintln("element" + elem);
		//arraylist klappivate s6nade jaoks
		ArrayList<String> matches = new ArrayList<String>();
		ArrayList<Integer> indeksid = new ArrayList<Integer>();

		//System.outprintln("kasutatavad tahed" +TahedString);

		Mang.kasutatudTahed.clear();
		for (loendur=0; loendur < Mang.charid.size(); loendur++){
			char [] sonaCharideks = Mang.charid.get(loendur);
			boolean sisaldabKoiki = false; 
			for (char charElem: sonaCharideks){
				//System.out.println(""+charElem);
				for (char charElem2: tahedJaElement){
					if (charElem2 == charElem){
						sisaldabKoiki = true;
						break;
					}
					sisaldabKoiki = false;
				}
				if (!sisaldabKoiki) break;
			}
			if (sisaldabKoiki) {
				indeksid.add(loendur);
				//System.out.println("lisasin indeksi " + loendur);
			}
		}
		
	//	for (loendur=0; loendur < Mang.sonaraamat.size(); loendur++){
		for (int indeks : indeksid){
			String sona = Mang.sonaraamat.get(indeks);
			Pattern p = Pattern.compile(StringUtils.repeat(("[" + TahedString + "]{0,1}"), 7) + elem + StringUtils.repeat(("[" + TahedString + "]{0,1}"), 7));
		    Matcher m = p.matcher(sona);
		    //kasutan kontrollimeetodeid sobivate peal
		    if (m.matches() && sona.length() > 3 && sona.length() > elem.length()) {
		    	if (Mang.tahedKlapivad(elem, sona, TahedString)){
		    		//System.outprintln("tahed klapivad");
		    		//System.outprintln("konrollin seda" + i+ ", " + (j-1-sona.indexOf(elem)) + " " + p_v_a + " " + sona);
		    		if (p_v_a=="p" && AI_kontroll.kontroll(i+ ", " + (j-1-sona.indexOf(elem)) + " " + p_v_a + " " + sona)){
		    			//System.outprintln( + i+ ", " + (j-1-sona.indexOf(elem)) + " " + p_v_a + " " + sona + "sobis");
			    		if (!potentsiaalsed.contains((i+ ", " + (j-1-sona.indexOf(elem)) + " " + p_v_a + " " + sona)) && !matches.contains((i+ ", " + (j-1-sona.indexOf(elem)) + " " + p_v_a + " " + sona))){
			    			matches.add(i+ ", " + (j-1-sona.indexOf(elem)) + " " + p_v_a + " " + sona);
			    			//System.outprintln("sobib "+ i+ ", " + (j-1-sona.indexOf(elem)) + " " + p_v_a + " " + sona);
			    		}
			    	}
		    		//System.outprintln("kontrollin teist "+ (i-1-sona.indexOf(elem))+ ", " + j + " " + p_v_a + " " + sona);
			    	if (p_v_a=="a" && AI_kontroll.kontroll((i-1-sona.indexOf(elem))+ ", " + j + " " + p_v_a + " " + sona)){
			    		//System.outprintln(i-1-sona.indexOf(elem)+ ", " + j + " " + p_v_a + " " + sona + "sobis");
			    		if (!potentsiaalsed.contains((i-1-sona.indexOf(elem))+ ", " + j + " " + p_v_a + " " + sona) && !matches.contains((i-1-sona.indexOf(elem))+ ", " + j + " " + p_v_a + " " + sona)){
				    		matches.add((i-1-sona.indexOf(elem))+ ", " + j + " " + p_v_a + " " + sona);
				    		//System.outprintln("sobib");
			    		}
			    	}
		    	}  	
		    	//kui k6ik sobib, esitan vastuse n6utud inputi formaadis
		    }			    
		}
		
		//kui 3st tähest pikemaid sõnu ei leidnud, otsin kesisemat varianti
	    if (matches.size()==0){
	    	for (int indeks : indeksid){
				String sona = Mang.sonaraamat.get(indeks);
	    		//otsin s6nastikust regex mustri ja m2ngija t2htedega klappivaid s6nu 
			    Pattern p = Pattern.compile(StringUtils.repeat(("[" + TahedString + "]{0,1}"), 7) + elem + StringUtils.repeat(("[" + TahedString + "]{0,1}"), 7));
			    Matcher m = p.matcher(sona);
			    //kasutan kontrollimeetodeid sobivate peal
			    if (m.matches() && sona.length() > elem.length()) {
			    	if (Mang.tahedKlapivad(elem, sona, TahedString)){
			    		//System.outprintln("tahed klapivadsa");
			    		//System.outprintln("kontrollin seda " + i+ ", " + (j-1-sona.indexOf(elem)) + " " + p_v_a + " " + sona);
			    		if (p_v_a=="p" && AI_kontroll.kontroll(i+ ", " + (j-1-sona.indexOf(elem)) + " " + p_v_a + " " + sona)){
				    		if (!potentsiaalsed.contains((i+ ", " + (j-1-sona.indexOf(elem)) + " " + p_v_a + " " + sona)) && !matches.contains((i+ ", " + (j-1-sona.indexOf(elem)) + " " + p_v_a + " " + sona))){
				    			matches.add(i+ ", " + (j-1-sona.indexOf(elem)) + " " + p_v_a + " " + sona);
				    			//System.outprintln("sobis "+ i+ ", " + (j-1-sona.indexOf(elem)) + " " + p_v_a + " " + sona);
				    		}
				    	}
				    	if (p_v_a=="a" && AI_kontroll.kontroll((i-1-sona.indexOf(elem))+ ", " + j + " " + p_v_a + " " + sona)){
				    		if (!potentsiaalsed.contains((i-1-sona.indexOf(elem))+ ", " + j + " " + p_v_a + " " + sona) && !matches.contains((i-1-sona.indexOf(elem))+ ", " + j + " " + p_v_a + " " + sona)){
					    		matches.add((i-1-sona.indexOf(elem))+ ", " + j + " " + p_v_a + " " + sona);
					    		//System.outprintln("sobis " + (i-1-sona.indexOf(elem))+ ", " + j + " " + p_v_a + " " + sona);
				    		}
				    	}
			    	}  	
			    	//kui k6ik sobib, esitan vastuse n6utud inputi formaadis
			    }			    
			}
	    }
		//teen klappivatest suvalise valiku
		if (matches.size() == 0) return "0, 0 p xxxx";		
		else {
			String pakkumine = matches.get((int)(Math.random()*matches.size()));
			matches.remove(pakkumine);
			return pakkumine;
		}
	}

}
