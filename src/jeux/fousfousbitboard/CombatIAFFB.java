package jeux.fousfousbitboard;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import iia.jeux.alg.AlgoJeu;
import iia.jeux.alg.AlphaBeta;
import iia.jeux.alg.Heuristique;
import iia.jeux.modele.CoupJeu;
import iia.jeux.modele.PlateauJeu;
import iia.jeux.modele.joueur.Joueur;

public class CombatIAFFB {

	static Random rand = new Random();

	public static void main(String[] args) throws Exception {
		lanceCombat(HeuristiquesFFB.htest1, HeuristiquesFFB.htest2, 5,100);

	}

	public static void lanceCombat(Heuristique h1, Heuristique h2, int coupsRandom, int nbtests){

		Joueur jBlanc = new Joueur("blanc");
		Joueur jNoir = new Joueur("noir");
		PlateauFFB.setJoueurs(jBlanc, jNoir);

		Joueur[] lesJoueurs = new Joueur[]{jBlanc, jNoir};

		AlgoJeu AlgoJoueur[] = new AlgoJeu[2];
		
		CoupFFB coup;

		// Composé de "Joueur;Heuristique"
		String[] gagnants = new String[nbtests*2];
		
		int[] scoreCoul = new int[]{0, 0};
		int[] scoreH = new int[]{0, 0};
		
		for(int i=0; i<nbtests*2; i++){
			int jnum = 0; // On commence par le joueur Blanc
			
			PlateauFFB plateauCourant = new PlateauFFB();
			
			if(i<nbtests){
				AlgoJoueur[0] = new AlphaBeta(h1, jBlanc, jNoir, 7);
				AlgoJoueur[1] = new AlphaBeta(h2, jNoir, jBlanc, 7);
			}else{
				AlgoJoueur[0] = new AlphaBeta(h2, jBlanc, jNoir, 7);
				AlgoJoueur[1] = new AlphaBeta(h1, jNoir, jBlanc, 7);
			}
			
			
			for(int j=0;j<coupsRandom; j++){
				ArrayList<CoupJeu> listeCoups = plateauCourant.coupsPossibles(lesJoueurs[jnum]);
				coup = (CoupFFB) listeCoups.get(rand.nextInt(listeCoups.size()));
				plateauCourant.joue(lesJoueurs[jnum], coup);
				jnum = (jnum + 1) % 2;
			}
			
			
			while (!plateauCourant.finDePartie()) {
				coup = (CoupFFB) AlgoJoueur[jnum].meilleurCoup(plateauCourant);
				plateauCourant.joue(lesJoueurs[jnum], coup);
				jnum = (jnum + 1) % 2;
			}
			System.out.println( ((i*50)/nbtests) + "% - C'est le joueur " + lesJoueurs[(jnum + 1) % 2] + " qui a gagné.");
			
			
			
			gagnants[i] = lesJoueurs[(jnum + 1) % 2].toString();
			scoreCoul[(jnum + 1) % 2]++;
			if(i<nbtests){
				gagnants[i] += ";" + (2 - jnum);
				scoreH[(jnum + 1) % 2]++;
			}else{
				gagnants[i] += ";" + (jnum + 1);
				scoreH[(jnum + 2) % 2]++;
			}
		}
		

		for (String mot : gagnants)
			System.out.println(mot);

		System.out.println("Score heuristique 1 : " + scoreH[0]);
		System.out.println("Score heuristique 2 : " + scoreH[1]);

		System.out.println("Score Joueur blanc : " + scoreCoul[0]);
		System.out.println("Score Joueur noir : " + scoreCoul[1]);
	}
}
