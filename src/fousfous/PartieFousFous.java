package fousfous;

import java.util.ArrayList;
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

	public static void main(String[] args) throws Exception {

		joue(false, true);

//		lanceCombat(HeuristiquesFousFous.htest1, HeuristiquesFousFous.htest2, 7, 25);

//		PlateauFFB temp = new PlateauFFB();
//		Joueur jb = new Joueur("blanc");
//		Joueur jn = new Joueur("noir");
//		PlateauFFB.setJoueurs(jb, jn);

//		temp = new PlateauFFB(new int[][]{
//			{0,0,0,0,0,0,0,0},
//			{0,0,0,0,0,0,0,0},
//			{0,1,0,0,0,1,0,0},
//			{1,0,0,0,2,0,0,0},
//			{0,0,0,0,0,0,0,0},
//			{0,0,0,0,0,0,0,0},
//			{0,0,0,0,0,0,0,0},
//			{0,0,2,0,0,0,0,0}});

//		temp = new PlateauFFB(randomPlateau());

//		temp.print();

//		 long startTime = System.currentTimeMillis();
//		 PionFFB pion1 = new PionFFB("B1-D3");
//		 PionFFB pion2 = new PionFFB("F7-B3");
//		 CoupFFB coup1 = new CoupFFB("B1-D3");
//		 CoupFFB coup2 = new CoupFFB("F7-B3");
//
//		 for(int i=0;i<100000000;i++){
//			 temp.coupsPossibles(jb);
//			 temp.coupsPossibles(jn);
//		 }
//		
//		 long stopTime = System.currentTimeMillis();
//		 long elapsedTime = stopTime - startTime;
//		 System.out.println(elapsedTime);

//		 Icon icon = new ImageIcon("DeepDownLow.gif");
//		 icon = new ImageIcon("GnaGnaGna.gif");
//		 //icon = new ImageIcon("Gneeeeeeee.gif");
//		 //icon = new ImageIcon("Lol.gif");
//		 JLabel label = new JLabel(icon);
//		
//		 JFrame f = new JFrame("Animation");
//		 f.getContentPane().add(label);
//		 f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		 f.pack();
//		 f.setLocationRelativeTo(null);
//		 f.setVisible(true);
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

//		plateauCourant = new PlateauFFB(new int[][]{
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
			System.out.println(((i * 50) / nbtests) + "% - C'est le joueur " + lesJoueurs[(jnum + 1) % 2] + " qui a gagné.");


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