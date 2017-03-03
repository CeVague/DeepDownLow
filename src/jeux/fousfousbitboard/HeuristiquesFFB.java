package jeux.fousfousbitboard;

import java.util.concurrent.ThreadLocalRandom;

import iia.jeux.alg.Heuristique;
import iia.jeux.modele.PlateauJeu;
import iia.jeux.modele.joueur.Joueur;


public class HeuristiquesFFB {

	public static Heuristique hblanc = new Heuristique() {
		
		public int eval(PlateauJeu p, Joueur j) {
			// TODO Auto-generated method stub
			return ThreadLocalRandom.current().nextInt(-500, 500 + 1);
		}
	};

	public static Heuristique hnoir = new Heuristique() {

		public int eval(PlateauJeu p, Joueur j) {
			// TODO Auto-generated method stub
			return ThreadLocalRandom.current().nextInt(-500, 500 + 1);
		}
	};

}
