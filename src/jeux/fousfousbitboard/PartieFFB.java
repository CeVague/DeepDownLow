package jeux.fousfousbitboard;

import java.util.Scanner;

import iia.jeux.alg.AlgoJeu;
import iia.jeux.alg.AlphaBeta;
import iia.jeux.alg.Minimax;
import iia.jeux.alg.NegAlphaBeta;
import iia.jeux.modele.joueur.Joueur;

public class PartieFFB {

	public static void main(String[] args) {

		joue(true, false);

//		PlateauFFB temp = new PlateauFFB(new int[][] {
//			{ 0, 2, 0, 2, 0, 2, 0, 2 },
//			{ 1, 0, 1, 0, 1, 0, 1, 0 },
//			{ 0, 2, 0, 2, 0, 2, 0, 2 },
//			{ 1, 0, 1, 0, 1, 0, 1, 0 },
//			{ 0, 2, 0, 2, 0, 2, 0, 2 },
//			{ 1, 0, 1, 0, 1, 0, 1, 0 },
//			{ 0, 2, 0, 2, 0, 2, 0, 2 },
//			{ 1, 0, 1, 0, 1, 0, 1, 0 } });


		// temp = new PlateauFFB(new int[][]{{0,0,0,0,0,0,0,0},
		// {0,0,0,0,0,0,0,0},
		// {0,1,0,0,0,1,0,0},
		// {1,0,0,0,2,0,0,0},
		// {0,0,0,0,0,0,0,0},
		// {0,0,0,0,0,0,0,0},
		// {0,0,0,0,0,0,0,0},
		// {0,0,2,0,0,0,0,0}});

//		Joueur jb = new Joueur("blanc");
//		Joueur jn = new Joueur("noir");
//		PlateauFFB.setJoueurs(jb, jn);
//
//		temp.print();


		// System.out.println(PlateauFFB.toString(temp.getPlateauNoir()));
		// System.out.println(PlateauFFB.toString(PlateauFFB.symetrieTour360(temp.getPlateauNoir())));
		// System.out.println(PlateauFFB.toString(PlateauFFB.symetrieDiagDroite(temp.getPlateauNoir())));
		// System.out.println(PlateauFFB.toString(PlateauFFB.symetrieDiagGauche(temp.getPlateauNoir())));

		// // Joueur Noir
		// PionFFB F1 = new PionFFB("F1");
		// PionFFB D5 = new PionFFB("D5");
		//
		// // Joueur Blanc
		// PionFFB H5 = new PionFFB("H5");
		// PionFFB G6 = new PionFFB("G6");
		// PionFFB C6 = new PionFFB("C6");

		// System.out.println( temp.peutMenacer(jb, H5) );
		// System.out.println( temp.listerMenacable(jb, H5) );
		//
		// System.out.println( temp.coupsPossibles(jn) );
		// System.out.println( temp.coupsPossibles(jb) );
		//
		// temp.saveToFile("test.txt");
		// temp.setFromFile("ExempleSavePlateau.txt");
		// temp.print();

		// System.out.println(temp.getPlateauBlanc());
		// System.out.println(temp.getPlateauNoir());
		//
		//
		// long tempInt = temp.getPlateauNoir();
		//
		// tempInt = tempInt - ((tempInt >> 1) & 0x5555555555555555L);
		// tempInt = (tempInt & 0x3333333333333333L) + ((tempInt >> 2) &
		// 0x3333333333333333L);
		// System.out.println( (((tempInt + (tempInt >> 4)) &
		// 0x0F0F0F0F0F0F0F0FL) * 0x0101010101010101L) >> 56);
		// System.out.println( Long.bitCount(temp.getPlateauNoir()));
		//
		// System.out.println(temp.toString());
		// temp.print();
		// temp.joue(new Joueur("gfghf"), new CoupFFB("C2-B1"));
		// System.out.println(temp.toString());
		// temp.print();
		//
		// System.out.println(temp.cheminLibre(new CoupFFB("B1-D3")));
		//
		// System.out.println(new CoupFFB("C2-B1").getAvant());
		// System.out.println(new CoupFFB("C2-B1").getApres());
		// System.out.println(new CoupFFB("C2-B1").toString());
		//
		// temp = new PlateauFFB();
		// System.out.println(temp.cheminLibre(new CoupFFB("B1-D3")));
		//
		// System.out.println(temp.getPlateauBlanc());
		// System.out.println(temp.getPlateauNoir());


		// temp.play("C2-B1", "noir");
		// temp.play("A8-B7", "noir");

		// temp.print();


		// long startTime = System.currentTimeMillis();
		// PionFFB pion1 = new PionFFB("B1-D3");
		// PionFFB pion2 = new PionFFB("F7-B3");
		// CoupFFB coup1 = new CoupFFB("B1-D3");
		// CoupFFB coup2 = new CoupFFB("F7-B3");

		// for(int i=0;i<100000000;i++){
		// temp.coupsPossibles(jb);
		// temp.coupsPossibles(jn);
		// }
		//
		// long stopTime = System.currentTimeMillis();
		// long elapsedTime = stopTime - startTime;
		// System.out.println(elapsedTime);


		// temp.print();
		// temp.joue(jb, new CoupFFB("D1-C2"));
		// temp.print();
		//
		// System.out.println(temp.listerMangeable(jn, new PionFFB("B1")) + "
		// true false B1");
		// System.out.println(temp.listerMangeable(jn, new PionFFB("A2")) + "
		// true A2");
		// System.out.println(temp.listerMangeable(jn, new PionFFB("D3")) + "
		// true false D3");
		// System.out.println(temp.listerMangeable(jn, new PionFFB("E4")) + "
		// true E4");
		// System.out.println(temp.listerMangeable(jn, new PionFFB("D5")) + "
		// false D5");
		// System.out.println(temp.listerMangeable(jn, new PionFFB("G8")) + "
		// true G8");
		// System.out.println(temp.listerMangeable(jn, new PionFFB("H5")) + "
		// false H5");
		// System.out.println(temp.listerMangeable(jn, new PionFFB("A8")) + "
		// true A8");
		// temp.joue(jn, new CoupFFB("G6-H7"));
		// temp.joue(jn, new CoupFFB("E8-F7"));
		// temp.print();
		// System.out.println(temp.peutManger(jn, new PionFFB("G8")));

		// for(PionFFB t : temp.listerPions(jb)){
		// System.out.println(t.toString());
		// }


		// Icon icon = new ImageIcon("DeepDownLow.gif");
		// icon = new ImageIcon("GnaGnaGna.gif");
		// //icon = new ImageIcon("Gneeeeeeee.gif");
		// //icon = new ImageIcon("Lol.gif");
		// JLabel label = new JLabel(icon);
		//
		// JFrame f = new JFrame("Animation");
		// f.getContentPane().add(label);
		// f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// f.pack();
		// f.setLocationRelativeTo(null);
		// f.setVisible(true);
	}

	public static void joue(boolean jbHumain, boolean jnHumain) {

		Joueur jBlanc = new Joueur("blanc");
		Joueur jNoir = new Joueur("noir");

		Joueur[] lesJoueurs = new Joueur[] { jBlanc, jNoir };

		AlgoJeu AlgoJoueur[] = new AlgoJeu[2];
		AlgoJoueur[0] = new AlphaBeta(HeuristiquesFFB.hblanc, jBlanc, jNoir, 7);
		AlgoJoueur[1] = new AlphaBeta(HeuristiquesFFB.hnoir, jNoir, jBlanc, 7);

		PlateauFFB plateauCourant = new PlateauFFB();

//		plateauCourant = new PlateauFFB(new int[][]{
//			 {0,2,0,2,0,0,0,0},
//			 {0,0,0,0,2,0,0,0},
//			 {0,1,0,0,0,2,0,0},
//			 {1,0,1,0,0,0,0,0},
//			 {0,0,0,1,0,2,0,0},
//			 {2,0,0,0,0,0,0,0},
//			 {0,0,0,2,0,0,0,0},
//			 {1,0,1,0,1,0,0,0}});


		PlateauFFB.setJoueurs(jBlanc, jNoir);


		System.out.println("Démarage de la partie de Fous-Fous");
		System.out.println("Etat Initial du plateau de jeu:");
		plateauCourant.print();
		
		int tempIAtotal = 0;

		int jnum = 0; // On commence par le joueur Blanc

		while (!plateauCourant.finDePartie()) {
			System.out.println("C'est au joueur " + lesJoueurs[jnum] + " de jouer.");

			CoupFFB coup = new CoupFFB();
			
			if ((jnum == 0 && !jbHumain) || (jnum == 1 && !jnHumain)) {
				long startTime = System.currentTimeMillis();

				int profondeur = (int) (plateauCourant.comptePions(lesJoueurs[jnum]) * -0.5555 + 14.88889);
				
				if(jnum==0){
					AlgoJoueur[jnum] = new AlphaBeta(HeuristiquesFFB.hblanc, jBlanc, jNoir, profondeur);
				}else{
					AlgoJoueur[jnum] = new AlphaBeta(HeuristiquesFFB.hnoir, jNoir, jBlanc, profondeur);
				}
				
				coup = (CoupFFB) AlgoJoueur[jnum].meilleurCoup(plateauCourant);
				
				long stopTime = System.currentTimeMillis();
				long elapsedTime = stopTime - startTime;
				tempIAtotal += elapsedTime;
				System.out.println("L'IA " + lesJoueurs[jnum] + " a joué le coup " + coup + " après " + elapsedTime/1000.0 + " secondes de réflexion.");
				System.out.println("Et en se projetant avec " + profondeur + " coups d'avance.");
			} else {
				System.out.println("Liste de vos pions :\n" + plateauCourant.listerPions(lesJoueurs[jnum]));

				while (!plateauCourant.coupValide(lesJoueurs[jnum], coup)) {
					Scanner sc = new Scanner(System.in);

					System.out.println("Veuillez saisir le coup joué ou le pion à observer :");

					String str = sc.nextLine();

					if (str.length() <= 2) {
						PionFFB pion = new PionFFB(str);
						System.out
								.println("Coups jouables :\n" + plateauCourant.coupsPossibles(lesJoueurs[jnum], pion));
					} else {
						coup = new CoupFFB(str);
					}

				}
			}

			plateauCourant.joue(lesJoueurs[jnum], coup);
			plateauCourant.print();

			jnum = (jnum + 1) % 2;
		}
		System.out.println("C'est le joueur " + lesJoueurs[(jnum + 1) % 2] + " qui a gagné.");
		System.out.println("Les IA ont passées " + (tempIAtotal/1000.0) + " secondes à réfléchir.");
	}
}
