package fousfous;

import java.io.*;
import java.util.*;

import iia.jeux.alg.*;
import affrontements.JoueurPro;
import affrontements.JoueurSemiPro;
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

		HashMap<PlateauMemoizeSymetriesFousFous, Integer> hmap = new HashMap<PlateauMemoizeSymetriesFousFous, Integer>();
		
		
		for(String fichier : new String[]{"dictionnaire_3_8.txt"}){
//		for(String fichier : new String[]{"dictionnaire_3_68.txt"}){
			try {
				InputStream ips = new FileInputStream(fichier);
				InputStreamReader ipsr = new InputStreamReader(ips);
				BufferedReader br = new BufferedReader(ipsr);
				String ligne;
				while ((ligne = br.readLine()) != null) {
//					System.out.println(ligne);
					String[] temp = ligne.split(";");
					
					long blanc = Long.valueOf(temp[0]);
					long noir = Long.valueOf(temp[1]);
					Integer score = Integer.valueOf(temp[2]);
					
					
					hmap.put(new PlateauMemoizeSymetriesFousFous(blanc, noir), score);
				}
				br.close();
			}
			catch (Exception e) {
				System.out.println(e.toString());
			}
		}
        try {
               FileOutputStream fos = new FileOutputStream("hashmap_8_noir.ser");
               ObjectOutputStream oos = new ObjectOutputStream(fos);
               oos.writeObject(hmap);
               oos.close();
               fos.close();
               System.out.printf("Serialized HashMap data is saved in hashmap.ser");
        }catch(IOException ioe) {
               ioe.printStackTrace();
        }
		
		
        
        
        
        
        
        
        
//			for(String fichier : new String[]{"dictionnaire_tout_8_blanc.txt", "dictionnaire_tout_8_noir.txt"}){
//				try {
//					InputStream ips = new FileInputStream(fichier);
//					InputStreamReader ipsr = new InputStreamReader(ips);
//					BufferedReader br = new BufferedReader(ipsr);
//					String ligne;
//					while ((ligne = br.readLine()) != null) {
////						System.out.println(ligne);
//						String[] temp = ligne.split(";");
//						
//						long blanc = Long.valueOf(temp[0]);
//						long noir = Long.valueOf(temp[1]);
//						Integer score = Integer.valueOf(temp[2]);
//						
//						
//						hmap.put(new PlateauMemoizeSymetriesFousFous(blanc, noir), score);
//					}
//					br.close();
//				}
//				catch (Exception e) {
//					System.out.println(e.toString());
//				}
//			}
//			
//	        try {
//	               FileOutputStream fos = new FileOutputStream("hashmap_final_8.ser");
//	               ObjectOutputStream oos = new ObjectOutputStream(fos);
//	               oos.writeObject(hmap);
//	               oos.close();
//	               fos.close();
//	               System.out.printf("Serialized HashMap data is saved in hashmap.ser");
//	        }catch(IOException ioe) {
//	               ioe.printStackTrace();
//	        }
        
        
        
        
        
        
		
		
		
//        HashMap<PlateauMemoizeSymetriesFousFous, Integer> map;
//        try
//        {
//           FileInputStream fis = new FileInputStream("hashmap.ser");
//           ObjectInputStream ois = new ObjectInputStream(fis);
//           map = (HashMap) ois.readObject();
//           ois.close();
//           fis.close();
//        }catch(IOException ioe)
//        {
//           ioe.printStackTrace();
//           return;
//        }catch(ClassNotFoundException c)
//        {
//           System.out.println("Class not found");
//           c.printStackTrace();
//           return;
//        }
//        System.out.println("Deserialized HashMap..");
//        // Display content using Iterator
//        Set set = map.entrySet();
//        Iterator iterator = set.iterator();
//        while(iterator.hasNext()) {
//           Map.Entry mentry = (Map.Entry)iterator.next();
//           PlateauMemoizeSymetriesFousFous temp = (PlateauMemoizeSymetriesFousFous) mentry.getKey();
//           System.out.println(temp.getPlateauBlanc() + ";" + temp.getPlateauNoir() + ";" + mentry.getValue());
//        }
		
		
		
		
		
		
		
		
		
		
		
		
		
//		creerStats();
		
//		for(int i=0;i<300000000;i++){
//			 System.out.println((int) ((rand.nextGaussian()*5)+23));
//			 Thread.sleep(50);
//		}
		
//		exempleMemoize();
//		
//		joue(true, false);
//
//		Thread.sleep(10000);
//		
//		long startTime = System.currentTimeMillis();
//		lanceCombat(HeuristiquesFousFous.htest1, HeuristiquesFousFous.htest2, 5, 20);
//		 long stopTime = System.currentTimeMillis();
//		 long elapsedTime = stopTime - startTime;
//		 System.out.println(elapsedTime);
//		
//		PlateauFousFous temp = new PlateauFousFous();
//		Joueur jb = new Joueur("blanc");
//		Joueur jn = new Joueur("noir");
//		PlateauFousFous.setJoueurs(jb, jn);
//
//		verifIAValide(new NegEchecAlphaBetaMemo(HeuristiquesFousFous.hmoyen, jb, jn, 6), new NegAlphaBeta(HeuristiquesFousFous.hmoyen, jb, jn, 6), 20);
//		verifIAValide(new NegEchecAlphaBetaMemo(HeuristiquesFousFous.hmoyen, jn, jb, 6), new NegAlphaBeta(HeuristiquesFousFous.hmoyen, jn, jb, 6), 20);
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
	
	public static void creerStats(){
		int nbtests = 100;
		double coupsRandom;
		int profondeur = 40;
//		double coupsRandom = 0;
		

		Joueur jBlanc = new Joueur("blanc");
		Joueur jNoir = new Joueur("noir");
		PlateauFousFous.setJoueurs(jBlanc, jNoir);

		Joueur[] lesJoueurs = new Joueur[]{jBlanc, jNoir};

		AlgoJeu AlgoJoueur[] = new AlgoJeu[2];
		AlgoJoueur[0] = new AlphaBeta(HeuristiquesFousFous.hzero, jBlanc, jNoir, profondeur);
		AlgoJoueur[1] = new AlphaBeta(HeuristiquesFousFous.hzero, jNoir, jBlanc, profondeur);
		


		AlgoJeu AlgoJoueurDebut[] = new AlgoJeu[2];
		AlgoJoueurDebut[0] = new AlphaBeta(HeuristiquesFousFous.hmoyen, jBlanc, jNoir, 6);
		AlgoJoueurDebut[1] = new AlphaBeta(HeuristiquesFousFous.hmoyen, jNoir, jBlanc, 6);

		CoupFousFous coup;

		for (int i = 0; i < nbtests; i++) {
			coupsRandom = ((rand.nextGaussian()*2)+15);
			coupsRandom = coupsRandom<12 ? 12 : coupsRandom;
			coupsRandom = coupsRandom>20 ? 20 : coupsRandom;
			
//			int coupsRandomRand = (int) (coupsRandom + rand.nextFloat()*4 - 2);
			int coupsRandomRand = (int) (coupsRandom);
			
			int jnum = 0; // On commence par le joueur Blanc
			
			ArrayList<String> listeData = new ArrayList<String>();

			PlateauFousFous plateauCourant = new PlateauFousFous();

			for (int j = 0; j < coupsRandomRand; j++) {
				if(j<4){
					ArrayList<CoupJeu> listeCoups = plateauCourant.coupsPossibles(lesJoueurs[jnum]);
					coup = (CoupFousFous) listeCoups.get(rand.nextInt(listeCoups.size()));
				}else{
					coup = (CoupFousFous) AlgoJoueurDebut[jnum].meilleurCoup(plateauCourant);
				}
				plateauCourant.joue(lesJoueurs[jnum], coup);
				jnum = (jnum + 1) % 2;
			}

			int coupsFait = coupsRandomRand;

			System.out.println(coupsRandomRand);
			
			while (!plateauCourant.finDePartie()) {
				
				coupsFait++;
				
//				long startTime = System.currentTimeMillis();
				coup = (CoupFousFous) AlgoJoueur[jnum].meilleurCoup(plateauCourant);
//				long stopTime = System.currentTimeMillis();
//				long elapsedTime = stopTime - startTime;
				
//				listeData.add(elapsedTime
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

				listeData.add(lesJoueurs[jnum]
						+","+coupsFait
						+","+coupsRandomRand
						+","+plateauCourant.getPlateauNoir()
						+","+plateauCourant.getPlateauBlanc()
						+","+plateauCourant.heuristiqueNombrePions(jNoir)
						+","+plateauCourant.heuristiqueNombrePions(jBlanc)
						+","+plateauCourant.heuristiqueVoisinDirect(jNoir, false)
						+","+plateauCourant.heuristiqueVoisinDirect(jBlanc, false)
						+","+plateauCourant.heuristiqueVoisinDirect(jNoir, true)
						+","+plateauCourant.heuristiqueVoisinDirect(jBlanc, true)
						+","+plateauCourant.heuristiqueMangeurs(jNoir)
						+","+plateauCourant.heuristiqueMangeurs(jBlanc)
						+","+plateauCourant.heuristiqueMenaceurs(jNoir)
						+","+plateauCourant.heuristiqueMenaceurs(jBlanc));
				
				plateauCourant.joue(lesJoueurs[jnum], coup);
				jnum = (jnum + 1) % 2;
			}
			
			ArrayList<String> listeDataFinal = new ArrayList<String>();
			
			for(int j=0;j<listeData.size();j++){
				// ......gagnant;perdant
				listeDataFinal.add(listeData.get(j) + "," + (coupsFait-j-1-coupsRandomRand) + "," + lesJoueurs[(jnum + 1) % 2] + "," + lesJoueurs[jnum]);
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
	
	@SuppressWarnings("unused")
	private static void exempleMemoize(){
		PlateauMemoizeSymetriesFousFous memo1 = new PlateauMemoizeSymetriesFousFous();
		memo1.setScore(10);
		
		PlateauFousFous temp = new PlateauFousFous(randomPlateau());
		PlateauMemoizeSymetriesFousFous memo2 = new PlateauMemoizeSymetriesFousFous(temp);
		memo2.setScore(100);

		PlateauMemoizeSymetriesFousFous memo2Bis = new PlateauMemoizeSymetriesFousFous(
				PlateauFousFous.symetrieDiagDroite(temp.getPlateauBlanc()), 
				PlateauFousFous.symetrieDiagDroite(temp.getPlateauNoir()));
		memo2Bis.setScore(50);

		
		HashSet<PlateauMemoizeSymetriesFousFous> hashset = new HashSet<PlateauMemoizeSymetriesFousFous>();
		hashset.add(memo1);
		hashset.add(memo2);
		hashset.add(memo2Bis);

		System.out.println("Contenu du Set :");
		for(PlateauMemoizeSymetriesFousFous truc : hashset){
			System.out.println(truc + " " + truc.getScore());
		}
		
		PlateauMemoizeSymetriesFousFous[] listeTemp = hashset.toArray(new PlateauMemoizeSymetriesFousFous[0]);
		Arrays.sort(listeTemp);
		
		System.out.println("Contenu trié :");
		for(PlateauMemoizeSymetriesFousFous truc : listeTemp){
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

		JoueurPro JoueurProBlanc = new JoueurPro();
		JoueurProBlanc.initJoueur(-1);
		
		Joueur jBlanc = JoueurProBlanc.getJoueurBlanc();
		Joueur jNoir = JoueurProBlanc.getJoueurNoir();

		JoueurSemiPro JoueurProNoir = new JoueurSemiPro();
		JoueurProNoir.setJoueurs(jBlanc, jNoir);
		JoueurProNoir.initJoueur(1);

		Joueur[] lesJoueurs = new Joueur[]{jBlanc, jNoir};

		PlateauFousFous plateauCourant = new PlateauFousFous();

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
			if ((jnum == 0 && !jbHumain)) {
				long startTime = System.currentTimeMillis();

				coup = new CoupFousFous(JoueurProBlanc.choixMouvement());
				JoueurProNoir.mouvementEnnemi(coup.toString());

				long elapsedTime = System.currentTimeMillis() - startTime;
				tempIAtotal += elapsedTime;
				System.out.println("L'IA " + lesJoueurs[jnum] + " a joué le coup " + coup + " après "
						+ elapsedTime / 1000.0 + " secondes de réflexion.");
			} else if ((jnum == 1 && !jnHumain)) {
				long startTime = System.currentTimeMillis();

				coup = new CoupFousFous(JoueurProNoir.choixMouvement());
				JoueurProBlanc.mouvementEnnemi(coup.toString());

				long elapsedTime = System.currentTimeMillis() - startTime;
				tempIAtotal += elapsedTime;
				System.out.println("L'IA " + lesJoueurs[jnum] + " a joué le coup " + coup + " après "
						+ elapsedTime / 1000.0 + " secondes de réflexion.");
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
				

				JoueurProNoir.mouvementEnnemi(coup.toString());
				JoueurProBlanc.mouvementEnnemi(coup.toString());
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
//	public static void joue(boolean jbHumain, boolean jnHumain) throws Exception {
//
//		Joueur jBlanc = new Joueur("blanc");
//		Joueur jNoir = new Joueur("noir");
//
//		Joueur[] lesJoueurs = new Joueur[]{jBlanc, jNoir};
//
//		AlgoJeu AlgoJoueur[] = new AlgoJeu[2];
//		AlgoJoueur[0] = new AlphaBeta(HeuristiquesFousFous.hmoyen, jBlanc, jNoir, 7);
//		AlgoJoueur[1] = new AlphaBeta(HeuristiquesFousFous.hmoyen, jNoir, jBlanc, 7);
//
//		PlateauFousFous plateauCourant = new PlateauFousFous();
//
////		plateauCourant = new PlateauFousFous(new int[][]{
////			 {0,2,0,2,0,0,0,0},
////			 {0,0,0,0,2,0,0,0},
////			 {0,1,0,0,0,2,0,0},
////			 {1,0,1,0,0,0,0,0},
////			 {0,0,0,1,0,2,0,0},
////			 {2,0,0,0,0,0,0,0},
////			 {0,0,0,2,0,0,0,0},
////			 {1,0,1,0,1,0,0,0}});
//
//
//		PlateauFousFous.setJoueurs(jBlanc, jNoir);
//
//
//		System.out.println("Démarage de la partie de Fous-Fous");
//		System.out.println("Etat Initial du plateau de jeu:");
//		plateauCourant.print();
//
//		int tempIAtotal = 0;
//
//		int jnum = 0; // On commence par le joueur Blanc
//
//		while (!plateauCourant.finDePartie()) {
//			System.out.println("C'est au joueur " + lesJoueurs[jnum] + " de jouer.");
//
//			CoupFousFous coup = new CoupFousFous();
//
//			// Si c'est une IA qui joue
//			if ((jnum == 0 && !jbHumain) || (jnum == 1 && !jnHumain)) {
//				long startTime = System.currentTimeMillis();
//
//				coup = (CoupFousFous) AlgoJoueur[jnum].meilleurCoup(plateauCourant);
//
//				long elapsedTime = System.currentTimeMillis() - startTime;
//				tempIAtotal += elapsedTime;
//				System.out.println("L'IA " + lesJoueurs[jnum] + " a joué le coup " + coup + " après "
//						+ elapsedTime / 1000.0 + " secondes de réflexion.");
//				// Si c'est un humain qui joue
//			} else {
//				System.out.println("Liste de vos pions :\n" + plateauCourant.listerPions(lesJoueurs[jnum]));
//
//				while (!plateauCourant.coupValide(lesJoueurs[jnum], coup)) {
//					sc = new Scanner(System.in);
//
//					System.out.println("Veuillez saisir le coup joué ou le pion à observer :");
//
//					String str = sc.nextLine();
//
//					if (str.length() <= 2) {
//						PionFousFous pion = new PionFousFous(str);
//						System.out
//								.println("Coups jouables :\n" + plateauCourant.coupsPossibles(lesJoueurs[jnum], pion));
//					} else {
//						coup = new CoupFousFous(str);
//					}
//
//				}
//			}
//
//			// Vérifie que le coup est valide
//			if (!plateauCourant.coupValide(lesJoueurs[jnum],
//					coup)) { throw new Exception("Coups joué invalide (test approfondi)"); }
//
//
//			plateauCourant.joue(lesJoueurs[jnum], coup);
//			plateauCourant.print();
//
//
//			// Vérifie que le coup n'a pas fait de bétises
//			if ((plateauCourant.getPlateauBlanc()
//					& 0b1010101001010101101010100101010110101010010101011010101001010101L) != 0) { throw new Exception(
//							"Un pion blanc n'a pas bien bougé"); }
//			if ((plateauCourant.getPlateauNoir()
//					& 0b1010101001010101101010100101010110101010010101011010101001010101L) != 0) { throw new Exception(
//							"Un pion noir à pas bien bougé"); }
//			if ((plateauCourant.getPlateauNoir() & plateauCourant.getPlateauBlanc()) != 0) { throw new Exception(
//					"Un pion blanc et un noir sont supperposés"); }
//
//			jnum = (jnum + 1) % 2;
//		}
//		System.out.println("C'est le joueur " + lesJoueurs[(jnum + 1) % 2] + " qui a gagné.");
//		System.out.println("Les IA ont passées " + (tempIAtotal / 1000.0) + " secondes à réfléchir.");
//	}


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

//			if (i < nbtests) {
//				AlgoJoueur[0] = new AlphaBeta(HeuristiquesFousFous.hdebut, jBlanc, jNoir, 6);
//				AlgoJoueur[1] = new AlphaBeta(HeuristiquesFousFous.hdebut, jNoir, jBlanc, 6);
//			} else {
//				AlgoJoueur[0] = new AlphaBeta(HeuristiquesFousFous.hdebut, jBlanc, jNoir, 6);
//				AlgoJoueur[1] = new AlphaBeta(HeuristiquesFousFous.hdebut, jNoir, jBlanc, 6);
//			}

			if (i < nbtests) {
				AlgoJoueur[0] = new NegEchecAlphaBeta(HeuristiquesFousFous.hmoyen, jBlanc, jNoir, 6);
				AlgoJoueur[1] = new NegEchecAlphaBeta(HeuristiquesFousFous.hmoyen, jNoir, jBlanc, 6);
			} else {
				AlgoJoueur[0] = new NegEchecAlphaBeta(HeuristiquesFousFous.hmoyen, jBlanc, jNoir, 6);
				AlgoJoueur[1] = new NegEchecAlphaBeta(HeuristiquesFousFous.hmoyen, jNoir, jBlanc, 6);
			}


			for (int j = 0; j < coupsRandom; j++) {
				ArrayList<CoupJeu> listeCoups = plateauCourant.coupsPossibles(lesJoueurs[jnum]);
				coup = (CoupFousFous) listeCoups.get(rand.nextInt(listeCoups.size()));
				plateauCourant.joue(lesJoueurs[jnum], coup);
				jnum = (jnum + 1) % 2;
			}

//			int duree = coupsRandom;

			while (!plateauCourant.finDePartie()) {
				

//				if(duree == 4){
//					if (i < nbtests) {
//						AlgoJoueur[1] = new AlphaBeta(h2, jNoir, jBlanc, 8);
//					} else {
//						AlgoJoueur[0] = new AlphaBeta(h2, jBlanc, jNoir, 8);
//					}
//				}
//				
//
//
//				if(duree == 4){
//					if (i < nbtests) {
//						AlgoJoueur[0] = new AlphaBeta(h1, jBlanc, jNoir, 7);
//					} else {
//						AlgoJoueur[1] = new AlphaBeta(h1, jNoir, jBlanc, 7);
//					}
//				}
				
				coup = (CoupFousFous) AlgoJoueur[jnum].meilleurCoup(plateauCourant);
				plateauCourant.joue(lesJoueurs[jnum], coup);
				jnum = (jnum + 1) % 2;
//				duree++;
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

		System.out.println("Score heuristique 1 : " + 50.0*scoreH[0]/nbtests);
		System.out.println("Score heuristique 2 : " + 50.0*scoreH[1]/nbtests);
		System.out.println("Différence h1 - h2 : " + (scoreH[0] - scoreH[1]));

		System.out.println("Score Joueur blanc : " + 50.0*scoreCoul[0]/nbtests);
		System.out.println("Score Joueur noir : " + 50.0*scoreCoul[1]/nbtests);
	}
}
