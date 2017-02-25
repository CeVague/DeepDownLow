package jeux.awale;

import iia.jeux.alg.AlgoJeu;
import iia.jeux.alg.AlphaBeta;
import iia.jeux.alg.Minimax;
import iia.jeux.alg.NegAlphaBeta;
import iia.jeux.modele.CoupJeu;
import iia.jeux.modele.PlateauJeu;
import iia.jeux.modele.joueur.Joueur;
import java.util.ArrayList;

public class PartieAwale {

	public static void main(String[] args) {

		Joueur jHaut = new Joueur("Haut");
		Joueur jBas = new Joueur("Bas");

		Joueur[] lesJoueurs = new Joueur[2];

		lesJoueurs[0] = jBas;
		lesJoueurs[1] = jHaut;

		AlgoJeu AlgoJoueur[] = new AlgoJeu[2];
		AlgoJoueur[0] = new Minimax(HeuristiquesAwale.hbas, jBas, jHaut, 1); // Il faut remplir la méthode !!!
		AlgoJoueur[1] = new Minimax(HeuristiquesAwale.hhaut , jHaut, jBas, 1); // Il faut remplir la méthode !!!

		System.out.println("TD IIA n.3 - Algorithmes pour les Jeux (Awale)");
		System.out.println("Etat Initial du plateau de jeu:");

		boolean jeufini = false;
		CoupJeu meilleurCoup = null;
		int jnum;

		PlateauJeu plateauCourant = new PlateauAwale();
		PlateauAwale.setJoueurs(jBas, jHaut);

		// A chaque itération de la boucle, on fait jouer un des deux joueurs
		// tour a tour
		jnum = 0; // On commence par le joueur Bas (arbitraire)
		
		
		while (!jeufini) {
			System.out.println("" + plateauCourant);
			((PlateauAwale) plateauCourant).print();
			System.out.println("C'est au joueur " + lesJoueurs[jnum] + " de jouer.");
			// Vérifie qu'il y a bien des coups possibles
			// Ce n'est pas tres efficace, mais c'est plus rapide... a écrire...
			ArrayList<CoupJeu> lesCoupsPossibles = plateauCourant.coupsPossibles(lesJoueurs[jnum]);
			System.out.println("Coups possibles pour" + lesJoueurs[jnum] + " : " + lesCoupsPossibles);
			if (lesCoupsPossibles.size() > 0) {
				// On écrit le plateau

				// Lancement de l'algo de recherche du meilleur coup
				System.out.println("Recherche du meilleur coup avec l'algo " + AlgoJoueur[jnum]);
				meilleurCoup = AlgoJoueur[jnum].meilleurCoup(plateauCourant);
				System.out.println("Coup joué : " + meilleurCoup + " par le joueur " + lesJoueurs[jnum]);

				plateauCourant.joue(lesJoueurs[jnum], meilleurCoup);
				// Le coup est effectivement joué
				jnum = 1 - jnum;

			} else {
				if(((PlateauAwale) plateauCourant).nbPoints(lesJoueurs[0]) > ((PlateauAwale) plateauCourant).nbPoints(lesJoueurs[1])){
					System.out.println("Le joueur " + lesJoueurs[0] + " a gagné cette partie !");
				}else{
					System.out.println("Le joueur " + lesJoueurs[1] + " a gagné cette partie !");
				}
				jeufini = true;

			}
		}
	}
}
