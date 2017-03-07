package jeux.fousfousbitboard;

import java.util.concurrent.ThreadLocalRandom;

import iia.jeux.alg.Heuristique;
import iia.jeux.modele.PlateauJeu;
import iia.jeux.modele.joueur.Joueur;


public class HeuristiquesFFB {

	public static Heuristique hblanc = new Heuristique() {
		
		public int eval(PlateauJeu p, Joueur j) {
			PlateauFFB pNew = (PlateauFFB) p;
			
			int nbPionsMoi = pNew.heuristiqueNombrePions(j);
			int voisSimpleMoi = pNew.heuristiqueVoisinDirect(j, true);
			int voisMoi = pNew.heuristiqueVoisinDirect(j, false);

			
			Joueur jAdverse = pNew.retourneAdversaire(j);
			
			int nbPionsAdverse = pNew.heuristiqueNombrePions(jAdverse);
			int voisSimpleAdverse = pNew.heuristiqueVoisinDirect(jAdverse, true);
			int voisAdverse = pNew.heuristiqueVoisinDirect(jAdverse, false);

//			return ThreadLocalRandom.current().nextInt(-500, 500 + 1);
			return (nbPionsMoi - nbPionsAdverse) * 5 + (voisSimpleMoi - voisSimpleAdverse) * 4 + (voisMoi - voisAdverse) * 3;
		}
	};

	public static Heuristique hnoir = new Heuristique() {

		public int eval(PlateauJeu p, Joueur j) {
			PlateauFFB pNew = (PlateauFFB) p;
			
			int nbPionsMoi = pNew.heuristiqueNombrePions(j);
			int voisSimpleMoi = pNew.heuristiqueVoisinDirect(j, true);
			int voisMoi = pNew.heuristiqueVoisinDirect(j, false);

			
			Joueur jAdverse = pNew.retourneAdversaire(j);
			
			int nbPionsAdverse = pNew.heuristiqueNombrePions(jAdverse);
			int voisSimpleAdverse = pNew.heuristiqueVoisinDirect(jAdverse, true);
			int voisAdverse = pNew.heuristiqueVoisinDirect(jAdverse, false);

//			return ThreadLocalRandom.current().nextInt(-500, 500 + 1);
			return (nbPionsMoi - nbPionsAdverse) * 5 + (voisSimpleMoi - voisSimpleAdverse) * 4 + (voisMoi - voisAdverse) * 3;
		}
	};

}
