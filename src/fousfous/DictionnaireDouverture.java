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
	public DictionnaireDouverture(String fichier, Joueur joueur){
		this.joueur = joueur;
		
	    System.out.println("Chargement du dictionnaire d'ouverture de " + joueur + "...");
		
        try{
        	InputStream fis = new FileInputStream(fichier + joueur + ".ser");
           ObjectInputStream ois = new ObjectInputStream(fis);
           dictionnaire = (HashMap<PlateauMemoizeSymetriesFousFous, Integer>) ois.readObject();
           ois.close();
           fis.close();
        }catch(IOException | ClassNotFoundException ioe){
        }
        
        
        if(dictionnaire == null){
    		
    	    System.out.println("Recherche dans le .jar ...");
    	    
            try{
               InputStream fis = getClass().getResourceAsStream(fichier + joueur + ".ser"); 
               ObjectInputStream ois = new ObjectInputStream(fis);
               dictionnaire = (HashMap<PlateauMemoizeSymetriesFousFous, Integer>) ois.readObject();
               ois.close();
               fis.close();
            }catch(IOException | ClassNotFoundException ioe){
        	    System.out.println("Echec lors du chargement du dictionnaire.");
            }
        }
        
        if(dictionnaire != null){
            System.out.println("Dictionnaire chargé. " + dictionnaire.size() + " plateaux pré-calculés.");
        }
	}
	
	public boolean estCharge(){
		return dictionnaire != null;
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
			}else if(valTemp == valMax && Math.random()<0.2){
				coupMax = coupTemp;
			}
		}
		
		if(valMax == -Integer.MAX_VALUE){
			System.out.println(p);
		}

		return coupMax;
	}
}