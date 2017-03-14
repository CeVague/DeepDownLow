package fousfous;

import java.util.concurrent.ThreadLocalRandom;

import iia.jeux.alg.Heuristique;
import iia.jeux.modele.PlateauJeu;
import iia.jeux.modele.joueur.Joueur;


public class HeuristiquesFousFous {

	/****** Heuristiques pour tester les combinaisons *******/

	public static Heuristique htest1 = new Heuristique() {

		public int eval(PlateauJeu p, Joueur j) {
			PlateauFousFous pNew = (PlateauFousFous) p;

			int nbPionsMoi = pNew.heuristiqueNombrePions(j);
			int voisSimpleMoi = pNew.heuristiqueVoisinDirect(j, true);
			int voisMoi = pNew.heuristiqueVoisinDirect(j, false);


			Joueur jAdverse = pNew.retourneAdversaire(j);

			int nbPionsAdverse = pNew.heuristiqueNombrePions(jAdverse);
			int voisSimpleAdverse = pNew.heuristiqueVoisinDirect(jAdverse, true);
			int voisAdverse = pNew.heuristiqueVoisinDirect(jAdverse, false);

			return (nbPionsMoi - nbPionsAdverse) * 50
					+ (voisSimpleMoi - voisSimpleAdverse) * 20
					+ (voisMoi - voisAdverse) * -5;
		}
	};

	public static Heuristique htest2 = new Heuristique() {

		public int eval(PlateauJeu p, Joueur j) {
			PlateauFousFous pNew = (PlateauFousFous) p;

			int nbPionsMoi = pNew.heuristiqueNombrePions(j);
			int voisSimpleMoi = pNew.heuristiqueVoisinDirect(j, true);
			int voisMoi = pNew.heuristiqueVoisinDirect(j, false);

			Joueur jAdverse = pNew.retourneAdversaire(j);

			int nbPionsAdverse = pNew.heuristiqueNombrePions(jAdverse);
			int voisSimpleAdverse = pNew.heuristiqueVoisinDirect(jAdverse, true);
			int voisAdverse = pNew.heuristiqueVoisinDirect(jAdverse, false);

			return (nbPionsMoi - nbPionsAdverse) * 50
					+ (voisSimpleMoi - voisSimpleAdverse) * 20
					+ (voisMoi - voisAdverse) * -5;
		}
	};


	/****** Heuristiques d'exemple (avec tout) *******/
	
	/*
	 * Renvoie un mélange de toutes les heuristiques possibles
	 * (très lent)
	 */
	public static Heuristique htout = new Heuristique() {

		public int eval(PlateauJeu p, Joueur j) {
			PlateauFousFous pNew = (PlateauFousFous) p;

			int nbPionsMoi = pNew.heuristiqueNombrePions(j);
			int voisSimpleMoi = pNew.heuristiqueVoisinDirect(j, true);
			int voisMoi = pNew.heuristiqueVoisinDirect(j, false);
			int nbMangeMoi = pNew.heuristiqueMangeurs(j);
			int nbMenaceMoi = pNew.heuristiqueMenaceurs(j);


			Joueur jAdverse = pNew.retourneAdversaire(j);

			int nbPionsAdverse = pNew.heuristiqueNombrePions(jAdverse);
			int voisSimpleAdverse = pNew.heuristiqueVoisinDirect(jAdverse, true);
			int voisAdverse = pNew.heuristiqueVoisinDirect(jAdverse, false);
			int nbMangeAdverse = pNew.heuristiqueMangeurs(jAdverse);
			int nbMenaceAdverse = pNew.heuristiqueMenaceurs(jAdverse);

			return (nbPionsMoi - nbPionsAdverse) * 5
					+ (voisSimpleMoi - voisSimpleAdverse) * 4
					+ (voisMoi - voisAdverse) * 3
					+ (nbMangeMoi - nbMangeAdverse) * 2
					+ (nbMenaceMoi - nbMenaceAdverse) * -2;
		}
	};
	
	
	/****** Heuristiques génériques *******/

	/*
	 * Renvoie toujours zero
	 * (utile pour couper au maximum et ne remonter que les feuilles)
	 */
	public static Heuristique hzero = new Heuristique() {

		public int eval(PlateauJeu p, Joueur j) {
			return 0;
		}
	};

	/*
	 * Renvoie un nombre aléatoire
	 * (peut ralentire MinMax car la coupe est plus rare)
	 */
	public static Heuristique hrandom = new Heuristique() {

		public int eval(PlateauJeu p, Joueur j) {
			return ThreadLocalRandom.current().nextInt(-5000, 5000 + 1);
		}
	};

}
