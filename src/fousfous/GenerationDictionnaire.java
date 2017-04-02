package fousfous;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import iia.jeux.alg.Heuristique;
import iia.jeux.modele.CoupJeu;
import iia.jeux.modele.PlateauJeu;
import iia.jeux.modele.joueur.Joueur;

public class GenerationDictionnaire {

	public static HashSet<PlateauMemoizeSymetriesFousFous> memorise;
	public static int profondeur_memorise = 3;
	public static int profondeur_recherche = 8;
	
	public static Joueur jb, jn;
	public static Joueur joueurMax, joueurMin;

	public static Heuristique h;
	
	public static void main(String[] args) throws InterruptedException {
		jb = new Joueur("blanc");
		jn = new Joueur("noir");

		joueurMax = jb;
		joueurMin = jn;
		if(profondeur_memorise%2==1){
			joueurMax = jn;
			joueurMin = jb;
		}
		
		Joueur[] joueurs = new Joueur[]{jb, jn};
		
		h = HeuristiquesFousFous.hdebut;
		
		PlateauFousFous.setJoueurs(jb, jn);
		
		PlateauFousFous plateauDebut = new PlateauFousFous();
		
		memorise = new HashSet<PlateauMemoizeSymetriesFousFous>();
		memorise.add(new PlateauMemoizeSymetriesFousFous(plateauDebut));

		ArrayList<PlateauFousFous> aEvaluer = new ArrayList<PlateauFousFous>();
		aEvaluer.add(plateauDebut);
		
		for(int i=0;i<=profondeur_memorise;i++){
			ArrayList<PlateauFousFous> aEvaluerNew = new ArrayList<PlateauFousFous>();
			
			for(PlateauFousFous plateauTemp : aEvaluer){
				
				for(CoupJeu c : plateauTemp.coupsPossibles(joueurs[i%2])){
					PlateauFousFous newPlateau = (PlateauFousFous) plateauTemp.copy();
					newPlateau.joue(joueurs[i%2], c);
					
					if(memorise.add(new PlateauMemoizeSymetriesFousFous(newPlateau))){
						aEvaluerNew.add(newPlateau);
					}
				}
				
			}
			
			aEvaluer = aEvaluerNew;
			
		}
		

		System.out.println(memorise.size());
		System.out.println(aEvaluer.size());

		System.out.println("Joueur concerné : " + joueurMax);
		

		int[] evaluation = new int[aEvaluer.size()];
		
		
		
		
		
		// Si on peut s'aider du dico
		if(profondeur_memorise<2){
			String profChoisi = "8";
			
	        HashMap<PlateauMemoizeSymetriesFousFous, Integer> map;
	        try{
	           FileInputStream fis = new FileInputStream("hashmap_" + profChoisi + "_" + joueurMax + ".ser");
	           ObjectInputStream ois = new ObjectInputStream(fis);
	           map = (HashMap<PlateauMemoizeSymetriesFousFous, Integer>) ois.readObject();
	           ois.close();
	           fis.close();
	        }catch(IOException | ClassNotFoundException ioe){
	           ioe.printStackTrace();
	           return;
	        }
	        System.out.println("Deserialized HashMap.." + map.size());
	        
			for(int i=0;i<aEvaluer.size();i++){
				evaluation[i] = negAlphaBetaMemo(aEvaluer.get(i), -Integer.MAX_VALUE, Integer.MAX_VALUE, 0, 1, map);
			}
			
			try {
				FileWriter ffw = new FileWriter("dictionnaire_tout_" + profChoisi + "_" + joueurMax + ".txt", false);

				for(int i=0;i<aEvaluer.size();i++){
					ffw.write(aEvaluer.get(i).getPlateauBlanc() + ";" + aEvaluer.get(i).getPlateauNoir() + ";" + evaluation[i] + "\n");
				}
				
		        Set set = map.entrySet();
		        Iterator iterator = set.iterator();
		        while(iterator.hasNext()) {
		           Map.Entry mentry = (Map.Entry)iterator.next();
		           PlateauMemoizeSymetriesFousFous temp = (PlateauMemoizeSymetriesFousFous) mentry.getKey();
					ffw.write(temp.getPlateauBlanc() + ";" + temp.getPlateauNoir() + ";" + mentry.getValue() + "\n");
		        }
	
				ffw.close();
			}
			catch (Exception e) {
				System.out.println(e.toString());
			}
			
			return ;
		}
		
		
		
		
		
		
		long startTime = System.currentTimeMillis();
		
		
		int realStart = 200000;
		
		if(args.length != 0){
			realStart = Integer.valueOf(args[0]);
		}
		
		for(int debut=realStart;debut<aEvaluer.size();debut+=1000){
			int fin = Math.min(aEvaluer.size(), debut + 1000);
			
			ExecutorService pool = Executors.newFixedThreadPool(3); 
			
			for(int i=debut;i<fin;i++){
				pool.submit(new lanceAlphaBeta(profondeur_recherche, aEvaluer.get(i), h, joueurMin, joueurMax, evaluation, i));
			}
			
		    pool.shutdown();
		    
		    pool.awaitTermination(Long.MAX_VALUE,  TimeUnit.DAYS);
			
			try {
				FileWriter ffw = new FileWriter("dictionnaire_" + profondeur_memorise + "_" + profondeur_recherche + ".txt", true);
	
				for(int i=debut;i<fin;i++){
					ffw.write(aEvaluer.get(i).getPlateauBlanc() + ";" + aEvaluer.get(i).getPlateauNoir() + ";" + evaluation[i] + "\n");
				}
	
				ffw.close();
			}
			catch (Exception e) {
				System.out.println(e.toString());
			}
		}

		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		System.out.println(elapsedTime);
	}
	
	public static int negAlphaBeta(PlateauJeu p, int Alpha, int Beta, int profondeur, int parite) {
		if (profondeur == profondeur_recherche) {
			return parite*h.eval(p, joueurMax);
		}else if(p.finDePartie()){
			return Integer.MAX_VALUE;
		}

		Joueur joueurActuel;
		if(parite==1){
			joueurActuel = joueurMin;
		}else{
			joueurActuel = joueurMax;
		}
		
		ArrayList<CoupJeu> coupsJouables = p.coupsPossibles(joueurActuel);
	
		for (CoupJeu coupTemp : coupsJouables) {
			PlateauJeu pNew = p.copy();
			pNew.joue(joueurActuel, coupTemp);
			
			Beta = Math.min(Beta, -negAlphaBeta(pNew, -Beta, -Alpha, profondeur + 1, -parite));
	
			if (Alpha >= Beta) { return Alpha; }
		}
	
		return Beta;
	}
	
	public static int negAlphaBetaMemo(PlateauJeu p, int Alpha, int Beta, int profondeur, int parite, HashMap<PlateauMemoizeSymetriesFousFous, Integer> map) {
		PlateauMemoizeSymetriesFousFous plateauMemo = new PlateauMemoizeSymetriesFousFous((PlateauFousFous) p);
		if(map.containsKey(plateauMemo)){
			return map.get(plateauMemo);
		}
		
		if (profondeur == profondeur_recherche) {
			return parite*h.eval(p, joueurMax);
		}else if(p.finDePartie()){
			return Integer.MAX_VALUE;
		}

		Joueur joueurActuel;
		if(parite==1){
			joueurActuel = joueurMin;
		}else{
			joueurActuel = joueurMax;
		}
		
		ArrayList<CoupJeu> coupsJouables = p.coupsPossibles(joueurActuel);
	
		for (CoupJeu coupTemp : coupsJouables) {
			PlateauJeu pNew = p.copy();
			pNew.joue(joueurActuel, coupTemp);
			
			Beta = Math.min(Beta, -negAlphaBetaMemo(pNew, -Beta, -Alpha, profondeur + 1, -parite, map));
	
			if (Alpha >= Beta) { return Alpha; }
		}
	
		return Beta;
	}
	
}



class lanceAlphaBeta implements Runnable {

	private int profMax;
	private Heuristique h;
	private Joueur joueurMin;
	private Joueur joueurMax;

	private PlateauFousFous p;

	private int[] evaluation;
	private int i;

	@Override
	public void run() {
		evaluation[i] = negAlphaBeta(p, -Integer.MAX_VALUE, Integer.MAX_VALUE, 0, 1);

//		System.out.println(p.getPlateauBlanc() + ";" + p.getPlateauNoir() + ";" + evaluation[i]);
		if(i%100==0){
			System.out.println(i + " calculés");
		}
	}


	public lanceAlphaBeta(int profMax, PlateauFousFous p, Heuristique h, Joueur joueurMin, Joueur joueurMax, int[] evaluation, int i) {
		this.profMax = profMax;
		this.h = h;
		this.joueurMin = joueurMin;
		this.joueurMax = joueurMax;
		this.p = p;
		this.evaluation = evaluation;
		this.i = i;
	}

	public int negAlphaBeta(PlateauJeu p, int Alpha, int Beta, int profondeur, int parite) {
		if (profondeur == profMax) {
			return parite*h.eval(p, joueurMax);
		}else if(p.finDePartie()){
			return Integer.MAX_VALUE;
		}

		Joueur joueurActuel;
		if(parite==1){
			joueurActuel = joueurMin;
		}else{
			joueurActuel = joueurMax;
		}
		
		ArrayList<CoupJeu> coupsJouables = p.coupsPossibles(joueurActuel);
	
		for (CoupJeu coupTemp : coupsJouables) {
			PlateauJeu pNew = p.copy();
			pNew.joue(joueurActuel, coupTemp);
			
			Beta = Math.min(Beta, -negAlphaBeta(pNew, -Beta, -Alpha, profondeur + 1, -parite));
	
			if (Alpha >= Beta) { return Alpha; }
		}
	
		return Beta;
	}
}


//System.out.println(i + "/" + aEvaluer.size());
//evaluation[i] = negAlphaBeta(aEvaluer.get(i), -Integer.MAX_VALUE, Integer.MAX_VALUE, 0, 1);
//
//System.out.print(aEvaluer.get(i).getPlateauBlanc() + ";" + aEvaluer.get(i).getPlateauNoir() + ";" + evaluation[i] +"\n");
//
//if(i%100==0){
//	long stopTime = System.currentTimeMillis();
//	 long elapsedTime = stopTime - startTime;
//	 System.out.println(elapsedTime);
//	 System.out.println("temps restant = " + (aEvaluer.size() * elapsedTime / (i+1)));
//}