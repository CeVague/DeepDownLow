package jeux.fousfousbitboard;

import java.util.concurrent.ThreadLocalRandom;

import iia.jeux.alg.Heuristique;
import iia.jeux.modele.PlateauJeu;
import iia.jeux.modele.joueur.Joueur;


public class HeuristiquesFFB {

	public static Heuristique htest1 = new Heuristique() {

		public int eval(PlateauJeu p, Joueur j) {
			PlateauFFB pNew = (PlateauFFB) p;
			
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
			PlateauFFB pNew = (PlateauFFB) p;
			
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
	
	
	public static Heuristique hblanc = new Heuristique() {
		
		public int eval(PlateauJeu p, Joueur j) {
			PlateauFFB pNew = (PlateauFFB) p;
			
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

	public static Heuristique hnoir = new Heuristique() {

		public int eval(PlateauJeu p, Joueur j) {
			PlateauFFB pNew = (PlateauFFB) p;
			
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
	

	public static Heuristique hrandom = new Heuristique() {
		
		public int eval(PlateauJeu p, Joueur j) {
			return ThreadLocalRandom.current().nextInt(-5000, 5000 + 1);
		}
	};

}
