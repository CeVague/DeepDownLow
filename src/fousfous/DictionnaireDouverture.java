package fousfous;

import java.io.*;
import java.util.*;

import iia.jeux.alg.AlgoJeu;
import iia.jeux.modele.CoupJeu;
import iia.jeux.modele.PlateauJeu;
import iia.jeux.modele.joueur.Joueur;

public class DictionnaireDouverture implements AlgoJeu {

	private HashMap<PlateauMemoizeSymetriesFousFous, Integer> dictionnaire;
	private Joueur joueur;
	
	@SuppressWarnings("unchecked")
	public DictionnaireDouverture(Joueur joueur){
		this.joueur = joueur;
		
        try{
        	InputStream fis = new FileInputStream("hashmap_6_" + joueur + ".ser");
//           InputStream fis = getClass().getResourceAsStream("/file.txt"); 
           ObjectInputStream ois = new ObjectInputStream(fis);
           dictionnaire = (HashMap<PlateauMemoizeSymetriesFousFous, Integer>) ois.readObject();
           ois.close();
           fis.close();
        }catch(IOException | ClassNotFoundException ioe){
           ioe.printStackTrace();
        }
        
        
        if(dictionnaire == null){
            try{
               InputStream fis = getClass().getResourceAsStream("/hashmap_6_" + joueur + ".ser"); 
               ObjectInputStream ois = new ObjectInputStream(fis);
               dictionnaire = (HashMap<PlateauMemoizeSymetriesFousFous, Integer>) ois.readObject();
               ois.close();
               fis.close();
            }catch(IOException | ClassNotFoundException ioe){
               ioe.printStackTrace();
            }
        }
        
        System.out.println("Deserialized HashMap.." + dictionnaire.size());
	}
	


	@Override
	public CoupJeu meilleurCoup(PlateauJeu p) {
		ArrayList<CoupJeu> lesCoupsPossibles = p.coupsPossibles(joueur);
		
		CoupJeu coupMax = lesCoupsPossibles.get(0);
		int valMax = -Integer.MAX_VALUE;

		for (CoupJeu coupTemp : lesCoupsPossibles) {
			PlateauFousFous pNew = (PlateauFousFous) p.copy();
			pNew.joue(joueur, coupTemp);
			
			PlateauMemoizeSymetriesFousFous plateauMemo = new PlateauMemoizeSymetriesFousFous(pNew);
			
			
			int valTemp = -Integer.MAX_VALUE;
			
			if(dictionnaire.containsKey(plateauMemo)){
				valTemp = dictionnaire.get(plateauMemo);
			}else{
				System.out.println("0");
			}
			
			if (valTemp > valMax) {
				valMax = valTemp;
				coupMax = coupTemp;
			}else if(valTemp == valMax && Math.random()<0.5){
				coupMax = coupTemp;
			}
		}
		
		if(valMax == -Integer.MAX_VALUE){
			System.out.println(p);
		}

		return coupMax;
	}
}