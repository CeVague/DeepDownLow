package fousfous;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

import iia.jeux.alg.AlgoJeu;
import iia.jeux.alg.AlphaBeta;
import iia.jeux.alg.Heuristique;
import iia.jeux.modele.CoupJeu;
import iia.jeux.modele.joueur.Joueur;

public class PartieFousFous {

	static Random rand = new Random();
	private static Scanner sc;

	public static int[][] randomPlateau() {
		int[][] temp = new int[8][8];

		float tauxRemplis = rand.nextFloat();

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (rand.nextFloat() > tauxRemplis && (i + j) % 2 != 0) {
					temp[i][j] = rand.nextInt(2) + 1;
				}
			}
		}

		return temp;
	}
	
	public static void afficheBas(String s, int sleep) throws InterruptedException{
		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
		System.out.println(s);
		System.out.flush();  
		Thread.sleep(sleep);
	}

	public static void main(String[] args) throws Exception {
		


		afficheBas("Perdu...", 1000);
		afficheBas("Perdu...\n(☉_☉) ┬─┬﻿", 1000);
		afficheBas("Perdu...\n(╯°Д°）╯﻿ ┻━┻", 1000);
		afficheBas("Perdu...\n(╥_╥)   ┻━┻", 1000);
		afficheBas("Perdu...\n(-_-)   ┻━┻", 1000);
		afficheBas("Perdu...\n(╥_╥)   ┻━┻", 1000);

		for(int i=0;i<50;i++){
			String s = "";
			for(int j=0;j<i;j++){
				s += "    ";
			}
			s += "ヽ༼ ಠ益ಠ ༽ﾉ";
			afficheBas("Perdu...\n" + s, 75); 
		}

		afficheBas("Gagné...\n", 1000);
		afficheBas("Gagné...\n( •_•)", 1500);
		afficheBas("Gagné...\n( •_•)>⌐■-■", 1000);
		afficheBas("Gagné...", 0);
		System.out.print("(⌐■_■) #");
		Thread.sleep(200);
		for(char c : "Yeeeeaaaaahhh".toCharArray()){
			Thread.sleep(100);
			System.out.print(c);
		}
		System.out.print("\n");
		Thread.sleep(750);
		System.out.print(".");
		Thread.sleep(750);
		System.out.print(".");
		Thread.sleep(750);
		System.out.print(".");
		Thread.sleep(750);
		for(int i=0;i<5;i++){
			afficheBas("Gagné...\n     ヽ(⌐■_■)ノ♪♬", 500);
			afficheBas("Gagné...\n♬♪ノ(⌐■_■)ヽ", 500);
		}
		
//		for(int i=20; i>0;i--)
//			creerStats(i);
//		creerStats(12);
//		exempleMemoize();
//		
//		joue(false, true);
//
//		Thread.sleep(10000);
//		
//		lanceCombat(HeuristiquesFousFous.htest1, HeuristiquesFousFous.htest1, 9, 6);
//		
//		PlateauFousFous temp = new PlateauFousFous();
//		Joueur jb = new Joueur("blanc");
//		Joueur jn = new Joueur("noir");
//		PlateauFousFous.setJoueurs(jb, jn);
//
//		verifIAValide(new AlphaBeta(HeuristiquesFousFous.htest1, jb, jn, 6), new NegAlphaBeta(HeuristiquesFousFous.htest1, jb, jn, 6), 20);
//		verifIAValide(new AlphaBeta(HeuristiquesFousFous.htest1, jn, jb, 6), new NegAlphaBeta(HeuristiquesFousFous.htest1, jn, jb, 6), 20);
//
//		
//		temp = new PlateauFousFous(new int[][]{
//			{0,0,0,0,0,0,0,0},
//			{0,0,0,0,0,0,0,0},
//			{0,1,0,0,0,1,0,0},
//			{1,0,0,0,2,0,0,0},
//			{0,0,0,2,0,0,0,0},
//			{0,0,0,0,0,0,0,0},
//			{0,0,0,0,0,0,0,0},
//			{0,0,2,0,0,0,0,0}});
//
//		temp = new PlateauFousFous(randomPlateau());
//
//		temp.print();
//
//		AlgoJeu alphabeta = new AlphaBeta(HeuristiquesFousFous.htest1, jb, jn, 7);
//		AlgoJeu minmax = new MinMax(HeuristiquesFousFous.htest1, jb, jn, 7);
//
//		System.out.println(verifIAValide(alphabeta, minmax));
//
//		 long startTime = System.currentTimeMillis();
//
//		 for(int i=0;i<300000000;i++){
//			 temp.listerMenacable(jb, new PionFousFous("H5"));
//			 temp.listerMenacable(jn, new PionFousFous("F1"));
//		 }
//		
//		 long stopTime = System.currentTimeMillis();
//		 long elapsedTime = stopTime - startTime;
//		 System.out.println(elapsedTime);
	}
	
	// En faire un à 12 Lundi !!!!!!!!!!!!!!
	public static void creerStats(int profondeur){
		int nbtests = 10;
		double coupsRandom = (profondeur + 2.55)/1.25;
//		int profondeur = 5;
//		double coupsRandom = 0;
		

		Joueur jBlanc = new Joueur("blanc");
		Joueur jNoir = new Joueur("noir");
		PlateauFousFous.setJoueurs(jBlanc, jNoir);

		Joueur[] lesJoueurs = new Joueur[]{jBlanc, jNoir};

		AlgoJeu AlgoJoueur[] = new AlgoJeu[2];
		AlgoJoueur[0] = new AlphaBeta(HeuristiquesFousFous.htest1, jBlanc, jNoir, profondeur);
		AlgoJoueur[1] = new AlphaBeta(HeuristiquesFousFous.htest1, jNoir, jBlanc, profondeur);

		CoupFousFous coup;

		for (int i = 0; i < nbtests; i++) {
			
			int coupsRandomRand = (int) (coupsRandom + rand.nextFloat()*4 - 2);
//			int coupsRandomRand = (int) (coupsRandom);
			
			int jnum = 0; // On commence par le joueur Blanc
			
			ArrayList<String> listeData = new ArrayList<String>();

			PlateauFousFous plateauCourant = new PlateauFousFous();

			for (int j = 0; j < coupsRandomRand; j++) {
				ArrayList<CoupJeu> listeCoups = plateauCourant.coupsPossibles(lesJoueurs[jnum]);
				coup = (CoupFousFous) listeCoups.get(rand.nextInt(listeCoups.size()));
				plateauCourant.joue(lesJoueurs[jnum], coup);
				jnum = (jnum + 1) % 2;
			}

			int coupsFait = coupsRandomRand;
			
			while (!plateauCourant.finDePartie()) {
				
				coupsFait++;
				
				long startTime = System.currentTimeMillis();
				coup = (CoupFousFous) AlgoJoueur[jnum].meilleurCoup(plateauCourant);
				long stopTime = System.currentTimeMillis();
				long elapsedTime = stopTime - startTime;
				
				listeData.add(elapsedTime
						+","+plateauCourant.coupsPossibles(lesJoueurs[jnum]).size()
						+","+((AlphaBeta) AlgoJoueur[jnum]).getFeuilles()
						+","+((AlphaBeta) AlgoJoueur[jnum]).getNoeuds()
						+","+((AlphaBeta) AlgoJoueur[jnum]).getEtat()
						+","+lesJoueurs[jnum]
						+","+coupsRandomRand
						+","+profondeur
						+","+coupsFait
						+","+Long.bitCount(plateauCourant.getPlateauNoir())
						+","+Long.bitCount(plateauCourant.getPlateauBlanc())
						+","+plateauCourant.heuristiqueMangeurs(jNoir)
						+","+plateauCourant.heuristiqueMangeurs(jBlanc)
						+","+plateauCourant.heuristiqueMenaceurs(jNoir)
						+","+plateauCourant.heuristiqueMenaceurs(jBlanc));
				
//				System.out.println(elapsedTime
//						+","+plateauCourant.coupsPossibles(lesJoueurs[jnum]).size()
//						+","+((AlphaBeta) AlgoJoueur[jnum]).getFeuilles()
//						+","+((AlphaBeta) AlgoJoueur[jnum]).getNoeuds()
//						+","+((AlphaBeta) AlgoJoueur[jnum]).getEtat()
//						+","+lesJoueurs[jnum]
//						+","+coupsRandomRand
//						+","+profondeur
//						+","+coupsFait
//						+","+Long.bitCount(plateauCourant.getPlateauNoir())
//						+","+Long.bitCount(plateauCourant.getPlateauBlanc())
//						+","+plateauCourant.heuristiqueMangeurs(jNoir)
//						+","+plateauCourant.heuristiqueMangeurs(jBlanc)
//						+","+plateauCourant.heuristiqueMenaceurs(jNoir)
//						+","+plateauCourant.heuristiqueMenaceurs(jBlanc));
				
				plateauCourant.joue(lesJoueurs[jnum], coup);
				jnum = (jnum + 1) % 2;
			}
			
			ArrayList<String> listeDataFinal = new ArrayList<String>();
			
			for(int j=0;j<listeData.size();j++){
				listeDataFinal.add(listeData.get(j) + "," + (coupsFait-j-1-coupsRandomRand));
			}
			
			for(String s : listeDataFinal){
				System.out.println(s);
			}
			
		}
	}

	/**
	 * Permet de vérifier que deux algo de recherche dans l'arbre
	 * donnent le même résultat
	 */
	public static void verifIAValide(AlgoJeu Algo1, AlgoJeu Algo2, int nb) {
		for(int i=0;i<nb;i++){
			PlateauFousFous temp = new PlateauFousFous(randomPlateau());
			
			while(temp.finDePartie()){
				temp = new PlateauFousFous(randomPlateau());
			}
	
	//		System.out.println("Calcul des coups :");
			CoupFousFous coupAB = (CoupFousFous) Algo1.meilleurCoup(temp);
	//		System.out.println("Premier coup calculé");
			CoupFousFous coupMM = (CoupFousFous) Algo2.meilleurCoup(temp);
	//		System.out.println("Deuxieme coup calculé");

			System.out.println(coupAB.getApres() == coupMM.getApres() && coupAB.getAvant() == coupMM.getAvant());
		}
	}
	
	private static void exempleMemoize(){
		PlateauMemoizeFousFous memo1 = new PlateauMemoizeFousFous();
		memo1.setScore(10);
		
		PlateauFousFous temp = new PlateauFousFous(randomPlateau());
		PlateauMemoizeFousFous memo2 = new PlateauMemoizeFousFous(temp);
		memo2.setScore(100);

		PlateauMemoizeFousFous memo2Bis = new PlateauMemoizeFousFous(
				PlateauFousFous.symetrieDiagDroite(temp.getPlateauBlanc()), 
				PlateauFousFous.symetrieDiagDroite(temp.getPlateauNoir()));
		memo2Bis.setScore(50);

		
		HashSet<PlateauMemoizeFousFous> hashset = new HashSet<PlateauMemoizeFousFous>();
		hashset.add(memo1);
		hashset.add(memo2);
		hashset.add(memo2Bis);

		System.out.println("Contenu du Set :");
		for(PlateauMemoizeFousFous truc : hashset){
			System.out.println(truc + " " + truc.getScore());
		}
		
		PlateauMemoizeFousFous[] listeTemp = hashset.toArray(new PlateauMemoizeFousFous[0]);
		Arrays.sort(listeTemp);
		
		System.out.println("Contenu trié :");
		for(PlateauMemoizeFousFous truc : listeTemp){
			System.out.println(truc + " " + truc.getScore());
		}
	}

	/**
	 * Permet de se jouer une partie
	 * @param jbHumain Le joueur blanc est-il humain ou IA
	 * @param jnHumain Le joueur noir est-il humain ou IA
	 * @throws Exception Si un coup interdit est joué
	 */
	public static void joue(boolean jbHumain, boolean jnHumain) throws Exception {

		Joueur jBlanc = new Joueur("blanc");
		Joueur jNoir = new Joueur("noir");

		Joueur[] lesJoueurs = new Joueur[]{jBlanc, jNoir};

		AlgoJeu AlgoJoueur[] = new AlgoJeu[2];
		AlgoJoueur[0] = new AlphaBeta(HeuristiquesFousFous.htest1, jBlanc, jNoir, 7);
		AlgoJoueur[1] = new AlphaBeta(HeuristiquesFousFous.htest1, jNoir, jBlanc, 7);

		PlateauFousFous plateauCourant = new PlateauFousFous();

//		plateauCourant = new PlateauFousFous(new int[][]{
//			 {0,2,0,2,0,0,0,0},
//			 {0,0,0,0,2,0,0,0},
//			 {0,1,0,0,0,2,0,0},
//			 {1,0,1,0,0,0,0,0},
//			 {0,0,0,1,0,2,0,0},
//			 {2,0,0,0,0,0,0,0},
//			 {0,0,0,2,0,0,0,0},
//			 {1,0,1,0,1,0,0,0}});


		PlateauFousFous.setJoueurs(jBlanc, jNoir);


		System.out.println("Démarage de la partie de Fous-Fous");
		System.out.println("Etat Initial du plateau de jeu:");
		plateauCourant.print();

		int tempIAtotal = 0;

		int jnum = 0; // On commence par le joueur Blanc

		while (!plateauCourant.finDePartie()) {
			System.out.println("C'est au joueur " + lesJoueurs[jnum] + " de jouer.");

			CoupFousFous coup = new CoupFousFous();

			// Si c'est une IA qui joue
			if ((jnum == 0 && !jbHumain) || (jnum == 1 && !jnHumain)) {
				long startTime = System.currentTimeMillis();

				coup = (CoupFousFous) AlgoJoueur[jnum].meilleurCoup(plateauCourant);

				long elapsedTime = System.currentTimeMillis() - startTime;
				tempIAtotal += elapsedTime;
				System.out.println("L'IA " + lesJoueurs[jnum] + " a joué le coup " + coup + " après "
						+ elapsedTime / 1000.0 + " secondes de réflexion.");
				// Si c'est un humain qui joue
			} else {
				System.out.println("Liste de vos pions :\n" + plateauCourant.listerPions(lesJoueurs[jnum]));

				while (!plateauCourant.coupValide(lesJoueurs[jnum], coup)) {
					sc = new Scanner(System.in);

					System.out.println("Veuillez saisir le coup joué ou le pion à observer :");

					String str = sc.nextLine();

					if (str.length() <= 2) {
						PionFousFous pion = new PionFousFous(str);
						System.out
								.println("Coups jouables :\n" + plateauCourant.coupsPossibles(lesJoueurs[jnum], pion));
					} else {
						coup = new CoupFousFous(str);
					}

				}
			}

			// Vérifie que le coup est valide
			if (!plateauCourant.coupValide(lesJoueurs[jnum],
					coup)) { throw new Exception("Coups joué invalide (test approfondi)"); }


			plateauCourant.joue(lesJoueurs[jnum], coup);
			plateauCourant.print();


			// Vérifie que le coup n'a pas fait de bétises
			if ((plateauCourant.getPlateauBlanc()
					& 0b1010101001010101101010100101010110101010010101011010101001010101L) != 0) { throw new Exception(
							"Un pion blanc n'a pas bien bougé"); }
			if ((plateauCourant.getPlateauNoir()
					& 0b1010101001010101101010100101010110101010010101011010101001010101L) != 0) { throw new Exception(
							"Un pion noir à pas bien bougé"); }
			if ((plateauCourant.getPlateauNoir() & plateauCourant.getPlateauBlanc()) != 0) { throw new Exception(
					"Un pion blanc et un noir sont supperposés"); }

			jnum = (jnum + 1) % 2;
		}
		System.out.println("C'est le joueur " + lesJoueurs[(jnum + 1) % 2] + " qui a gagné.");
		System.out.println("Les IA ont passées " + (tempIAtotal / 1000.0) + " secondes à réfléchir.");
	}


	/**
	 * Lance plusieurs combats entre deux IA avec deux heuristiques et affiche
	 * le nombre de parties gagnées seulon la couleur et seulon l'heuristique
	 * @param h1 Une heuristique
	 * @param h2 Une autre heuritique
	 * @param coupsRandom Le nombres de coups jouées aléatoirement en début de
	 * 			chaque parties (évite que toutes les parties se ressemblent)
	 * @param nbtests Le nombre d'affrontement entre IA à lancer
	 */
	public static void lanceCombat(Heuristique h1, Heuristique h2, int coupsRandom, int nbtests) {

		Joueur jBlanc = new Joueur("blanc");
		Joueur jNoir = new Joueur("noir");
		PlateauFousFous.setJoueurs(jBlanc, jNoir);

		Joueur[] lesJoueurs = new Joueur[]{jBlanc, jNoir};

		AlgoJeu AlgoJoueur[] = new AlgoJeu[2];

		CoupFousFous coup;

		// Composé de "Joueur;Heuristique"
		String[] gagnants = new String[nbtests * 2];

		int[] scoreCoul = new int[]{0, 0};
		int[] scoreH = new int[]{0, 0};

		for (int i = 0; i < nbtests * 2; i++) {
			int jnum = 0; // On commence par le joueur Blanc

			PlateauFousFous plateauCourant = new PlateauFousFous();

			if (i < nbtests) {
				AlgoJoueur[0] = new AlphaBeta(h1, jBlanc, jNoir, 7);
				AlgoJoueur[1] = new AlphaBeta(h2, jNoir, jBlanc, 7);
			} else {
				AlgoJoueur[0] = new AlphaBeta(h2, jBlanc, jNoir, 7);
				AlgoJoueur[1] = new AlphaBeta(h1, jNoir, jBlanc, 7);
			}


			for (int j = 0; j < coupsRandom; j++) {
				ArrayList<CoupJeu> listeCoups = plateauCourant.coupsPossibles(lesJoueurs[jnum]);
				coup = (CoupFousFous) listeCoups.get(rand.nextInt(listeCoups.size()));
				plateauCourant.joue(lesJoueurs[jnum], coup);
				jnum = (jnum + 1) % 2;
			}


			while (!plateauCourant.finDePartie()) {
				coup = (CoupFousFous) AlgoJoueur[jnum].meilleurCoup(plateauCourant);
				plateauCourant.joue(lesJoueurs[jnum], coup);
				jnum = (jnum + 1) % 2;
			}
			System.out.println((((i+1) * 50) / nbtests) + "% - C'est le joueur " + lesJoueurs[(jnum + 1) % 2] + " qui a gagné.");


			gagnants[i] = lesJoueurs[(jnum + 1) % 2].toString();
			scoreCoul[(jnum + 1) % 2]++;
			if (i < nbtests) {
				gagnants[i] += ";" + (2 - jnum);
				scoreH[(jnum + 1) % 2]++;
			} else {
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
