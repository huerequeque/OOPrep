import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
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
						System.out.println("pyydja on " + pyydja);
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
		System.out.println("element" + elem);
		//arraylist klappivate s6nade jaoks
		ArrayList<String> matches = new ArrayList<String>();
		String pwd = System.getProperty("user.dir");
		File sonadFile = new File(pwd + "/src/sonad.txt");
		FileInputStream fstream = new FileInputStream(sonadFile);
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String rida;
		System.out.println("kasutatavad tahed" +TahedString);

		Mang.kasutatudTahed.clear();
		try {
			while ((rida = br.readLine()) != null) {
				//otsin s6nastikust regex mustri ja m2ngija t2htedega klappivaid s6nu 
			    Pattern p = Pattern.compile(StringUtils.repeat(("[" + TahedString + "]{0,1}"), 7) + elem + StringUtils.repeat(("[" + TahedString + "]{0,1}"), 7));
			    Matcher m = p.matcher(rida);
			    //kasutan kontrollimeetodeid sobivate peal
			    if (m.matches() && rida.length() > 3 && rida.length() > elem.length()) {
			    	if (Mang.tahedKlapivad(elem, rida, TahedString)){
			    		System.out.println("tahed klapivad");
			    		System.out.println("konrollin seda" + i+ ", " + (j-1-rida.indexOf(elem)) + " " + p_v_a + " " + rida);
			    		if (p_v_a=="p" && AI_kontroll.kontroll(i+ ", " + (j-1-rida.indexOf(elem)) + " " + p_v_a + " " + rida)){
			    			System.out.println( + i+ ", " + (j-1-rida.indexOf(elem)) + " " + p_v_a + " " + rida + "sobis");
				    		if (!potentsiaalsed.contains((i+ ", " + (j-1-rida.indexOf(elem)) + " " + p_v_a + " " + rida)) && !matches.contains((i+ ", " + (j-1-rida.indexOf(elem)) + " " + p_v_a + " " + rida))){
				    			matches.add(i+ ", " + (j-1-rida.indexOf(elem)) + " " + p_v_a + " " + rida);
				    			System.out.println("sobib "+ i+ ", " + (j-1-rida.indexOf(elem)) + " " + p_v_a + " " + rida);
				    		}
				    	}
			    		System.out.println("kontrollin teist "+ (i-1-rida.indexOf(elem))+ ", " + j + " " + p_v_a + " " + rida);
				    	if (p_v_a=="a" && AI_kontroll.kontroll((i-1-rida.indexOf(elem))+ ", " + j + " " + p_v_a + " " + rida)){
				    		System.out.println(i-1-rida.indexOf(elem)+ ", " + j + " " + p_v_a + " " + rida + "sobis");
				    		if (!potentsiaalsed.contains((i-1-rida.indexOf(elem))+ ", " + j + " " + p_v_a + " " + rida) && !matches.contains((i-1-rida.indexOf(elem))+ ", " + j + " " + p_v_a + " " + rida)){
					    		matches.add((i-1-rida.indexOf(elem))+ ", " + j + " " + p_v_a + " " + rida);
					    		System.out.println("sobib");
				    		}
				    	}
			    	}  	
			    	//kui k6ik sobib, esitan vastuse n6utud inputi formaadis
			    }			    
			}	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	    if (matches.size()==0){
	    	try {
				while ((rida = br.readLine()) != null) {
					//otsin s6nastikust regex mustri ja m2ngija t2htedega klappivaid s6nu 
				    Pattern p = Pattern.compile(StringUtils.repeat(("[" + TahedString + "]{0,1}"), 7) + elem + StringUtils.repeat(("[" + TahedString + "]{0,1}"), 7));
				    Matcher m = p.matcher(rida);
				    //kasutan kontrollimeetodeid sobivate peal
				    if (m.matches() && rida.length() > elem.length()) {
				    	if (Mang.tahedKlapivad(elem, rida, TahedString)){
				    		System.out.println("tahed klapivadsa");
				    		System.out.println("kontrollin seda " + i+ ", " + (j-1-rida.indexOf(elem)) + " " + p_v_a + " " + rida);
				    		if (p_v_a=="p" && AI_kontroll.kontroll(i+ ", " + (j-1-rida.indexOf(elem)) + " " + p_v_a + " " + rida)){
					    		if (!potentsiaalsed.contains((i+ ", " + (j-1-rida.indexOf(elem)) + " " + p_v_a + " " + rida)) && !matches.contains((i+ ", " + (j-1-rida.indexOf(elem)) + " " + p_v_a + " " + rida))){
					    			matches.add(i+ ", " + (j-1-rida.indexOf(elem)) + " " + p_v_a + " " + rida);
					    			System.out.println("sobis "+ i+ ", " + (j-1-rida.indexOf(elem)) + " " + p_v_a + " " + rida);
					    		}
					    	}
					    	if (p_v_a=="a" && AI_kontroll.kontroll((i-1-rida.indexOf(elem))+ ", " + j + " " + p_v_a + " " + rida)){
					    		if (!potentsiaalsed.contains((i-1-rida.indexOf(elem))+ ", " + j + " " + p_v_a + " " + rida) && !matches.contains((i-1-rida.indexOf(elem))+ ", " + j + " " + p_v_a + " " + rida)){
						    		matches.add((i-1-rida.indexOf(elem))+ ", " + j + " " + p_v_a + " " + rida);
						    		System.out.println("sobis " + (i-1-rida.indexOf(elem))+ ", " + j + " " + p_v_a + " " + rida);
					    		}
					    	}
				    	}  	
				    	//kui k6ik sobib, esitan vastuse n6utud inputi formaadis
				    }			    
				}		br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
