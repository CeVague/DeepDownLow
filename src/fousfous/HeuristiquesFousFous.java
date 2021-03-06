package fousfous;

import java.util.concurrent.ThreadLocalRandom;

import iia.jeux.alg.Heuristique;
import iia.jeux.modele.PlateauJeu;
import iia.jeux.modele.joueur.Joueur;


public class HeuristiquesFousFous {

	/****** Heuristiques pour tester les combinaisons *******/

	public static Heuristique hdebut = new Heuristique() {

		public int eval(PlateauJeu p, Joueur j) {
			PlateauFousFous pNew = (PlateauFousFous) p;

			int voisSimpleMoi = pNew.heuristiqueVoisinDirect(j, true);

			return voisSimpleMoi;
		}
	};

	public static Heuristique hlent = new Heuristique() {

		public int eval(PlateauJeu p, Joueur j) {
			PlateauFousFous pNew = (PlateauFousFous) p;

			int nbPionsMoi = pNew.heuristiqueNombrePions(j);
			int voisSimpleMoi = pNew.heuristiqueVoisinDirect(j, true);
			int voisMoi = pNew.heuristiqueVoisinDirect(j, false);
			int alligneBloguantMoi = pNew.heuristiqueVoisinLoins(j, true);
			int alligneMoi = pNew.heuristiqueVoisinLoins(j, false);

			Joueur jAdverse = pNew.retourneAdversaire(j);

			int nbPionsAdverse = pNew.heuristiqueNombrePions(jAdverse);
			int voisSimpleAdverse = pNew.heuristiqueVoisinDirect(jAdverse, true);
			int voisAdverse = pNew.heuristiqueVoisinDirect(jAdverse, false);
			int alligneBloguantAdverse = pNew.heuristiqueVoisinLoins(jAdverse, true);
			int alligneAdverse = pNew.heuristiqueVoisinLoins(jAdverse, false);

			return (nbPionsMoi - nbPionsAdverse) * 50
					+ (voisSimpleMoi - voisSimpleAdverse) * 20
					+ (voisMoi - voisAdverse) * -5
					+ (alligneBloguantMoi - alligneBloguantAdverse) * 15
					+ (alligneMoi - alligneAdverse) * 20;
		}
	};
	
	public static Heuristique hmoyen = new Heuristique() {

		public int eval(PlateauJeu p, Joueur j) {
			PlateauFousFous pNew = (PlateauFousFous) p;

			int nbPionsMoi = pNew.heuristiqueNombrePions(j);
			int voisSimpleMoi = pNew.heuristiqueVoisinDirect(j, true);
			int alligneMoi = pNew.heuristiqueVoisinLoins(j, false);

			Joueur jAdverse = pNew.retourneAdversaire(j);

			int nbPionsAdverse = pNew.heuristiqueNombrePions(jAdverse);
			int voisSimpleAdverse = pNew.heuristiqueVoisinDirect(jAdverse, true);
			int alligneAdverse = pNew.heuristiqueVoisinLoins(jAdverse, false);

			return (nbPionsMoi - nbPionsAdverse) * 50
					+ (voisSimpleMoi - voisSimpleAdverse) * 20
					+ (alligneMoi - alligneAdverse) * 20;
		}
	};

	public static Heuristique hrapide = new Heuristique() {

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
			int alligneBloguantMoi = pNew.heuristiqueVoisinLoins(j, true);
			int alligneMoi = pNew.heuristiqueVoisinLoins(j, false);
			int nbMangeMoi = pNew.heuristiqueMangeurs(j);
			int nbMenaceMoi = pNew.heuristiqueMenaceurs(j);


			Joueur jAdverse = pNew.retourneAdversaire(j);

			int nbPionsAdverse = pNew.heuristiqueNombrePions(jAdverse);
			int voisSimpleAdverse = pNew.heuristiqueVoisinDirect(jAdverse, true);
			int voisAdverse = pNew.heuristiqueVoisinDirect(jAdverse, false);
			int alligneBloguantAdverse = pNew.heuristiqueVoisinLoins(jAdverse, true);
			int alligneAdverse = pNew.heuristiqueVoisinLoins(jAdverse, false);
			int nbMangeAdverse = pNew.heuristiqueMangeurs(jAdverse);
			int nbMenaceAdverse = pNew.heuristiqueMenaceurs(jAdverse);

			return (nbPionsMoi - nbPionsAdverse) * 5
					+ (voisSimpleMoi - voisSimpleAdverse) * 4
					+ (voisMoi - voisAdverse) * 3
					+ (alligneBloguantMoi - alligneBloguantAdverse) * 15
					+ (alligneMoi - alligneAdverse) * 20
					+ (nbMangeMoi - nbMangeAdverse) * 2
					+ (nbMenaceMoi - nbMenaceAdverse) * -2;
		}
	};
	

	public static Heuristique hcompte = new Heuristique() {

		public int eval(PlateauJeu p, Joueur j) {
			PlateauFousFous pNew = (PlateauFousFous) p;
			int nbPionsMoi = pNew.heuristiqueNombrePions(j);
			Joueur jAdverse = pNew.retourneAdversaire(j);
			int nbPionsAdverse = pNew.heuristiqueNombrePions(jAdverse);

			return (nbPionsMoi - nbPionsAdverse);
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
