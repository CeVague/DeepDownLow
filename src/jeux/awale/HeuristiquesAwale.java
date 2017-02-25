package jeux.awale;

import java.util.Random;

import iia.jeux.alg.Heuristique;
import iia.jeux.modele.PlateauJeu;
import iia.jeux.modele.joueur.Joueur;


public class HeuristiquesAwale {
	// Heuristique simple consistant à garder le plus de graines de son côté
	public static Heuristique hbas = new Heuristique() {

		public int eval(PlateauJeu p, Joueur j) {
			PlateauAwale pNew = (PlateauAwale) p;
			return pNew.somme(6, 11) - pNew.somme(0, 5);
		}
	};

	public static Heuristique hhaut = new Heuristique() {

		public int eval(PlateauJeu p, Joueur j) {
			PlateauAwale pNew = (PlateauAwale) p;
			return pNew.somme(0, 5) - pNew.somme(6, 11);
		}
	};

}
