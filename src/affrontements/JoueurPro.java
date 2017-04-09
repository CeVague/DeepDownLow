package affrontements;

import fousfous.*;
import iia.jeux.alg.*;
import iia.jeux.modele.CoupJeu;
import iia.jeux.modele.joueur.Joueur;

public class JoueurPro implements IJoueur {

	static final int BLANC = -1;
	static final int NOIR = 1;

	static final Joueur jBlanc = new Joueur("blanc");
	static final Joueur jNoir = new Joueur("noir");

	int moiInt;
	Joueur moiJoueur;

	Joueur luiJoueur;

	PlateauFousFous plateau;

	AlgoJeuMemo algo;
	AlgoJeu algoDico;

	// Infos évolutives
	int coupJoues;
	long tempsRestant;
	
	// coups maximums avant de gagner (ou perdre si <0, et 0 si on ne sait pas)
	int gagnant; 

	// Infos fixes
	int nbCPU;
	int archi;
	float meMax;

	@Override
	public void initJoueur(int mycolour) {
		moiInt = mycolour;

		if (mycolour == BLANC) {
			moiJoueur = jBlanc;
			luiJoueur = jNoir;
		} else {
			moiJoueur = jNoir;
			luiJoueur = jBlanc;
		}

		plateau = new PlateauFousFous();
		PlateauFousFous.setJoueurs(jBlanc, jNoir);

		algoDico = new DictionnaireDouverture("/dictionnaires/hashmap_final_68_", moiJoueur);
		if (!((DictionnaireDouverture) algoDico).estCharge()) {
			System.out.println("Le dictionnaire n'a pas été chargé, on utilisera un AlphaBeta clasique");
			algoDico = new NegAlphaBeta(HeuristiquesFousFous.hdebut, moiJoueur, luiJoueur, 8);
		}

		coupJoues = 0;
		tempsRestant = 600000;

		gagnant = 0;

		nbCPU = Runtime.getRuntime().availableProcessors();
		archi = Integer.valueOf(System.getProperty("sun.arch.data.model"));
		meMax = ((int) ((Runtime.getRuntime().maxMemory() / 1073741824f) * 1000)) / 1000f;

		System.out.println("Système " + archi + "bit, avec " + nbCPU + " CPU (ou coeurs) et " + meMax + "Go de RAM dispo.\n\n\n\n");

		if (meMax < 3) {
			System.out.println("Pas assez de mémoire, on utilisera NegAlphaBeta plutot que NegEchecAlphaBetaMemo");
		}
	}

	@Override
	public int getNumJoueur() {
		return moiInt;
	}

	private AlgoJeuMemo choisiAlgo(Heuristique h, Joueur joueurMax, Joueur joueurMin, int profMaxi) {
		if (meMax < 2) { return new NegAlphaBeta(h, joueurMax, joueurMin, profMaxi); }

		if (coupJoues < 9) { return new NegAlphaBeta(h, joueurMax, joueurMin, profMaxi); }

		return new NegEchecAlphaBetaMemo(h, joueurMax, joueurMin, profMaxi);
	}

	private long estimeTemps(long tempsActuel, boolean premier) {
		double taux = 0;
		if (premier) {
			if (coupJoues < 10) {
				taux = 0.75;
			} else if (coupJoues < 13) {
				taux = 0.73;
			} else if (coupJoues < 18) {
				taux = 0.70;
			} else {
				taux = 0.67;
			}
		} else {
			if (coupJoues < 9) {
				taux = 0.75;
			} else if (coupJoues < 16) {
				taux = 0.72;
			} else {
				taux = 0.70;
			}
		}

		return (long) (-(tempsActuel * taux) / (taux - 1));
	}

	/**
	 * Permet de calculer un coup en prenant en compte les contraintes de temps.
	 * Il va faire une première recherche à profRapide, puis à une autre
	 * profondeur (selon estimations) et incrémentera ensuite tant qu'il reste du temps.
	 * 
	 * @param h l'heuristique à utiliser
	 * @param profRapide la profondeur où on est sûr d'avoir le temps (pour toujours avoir un résultat)
	 * @param tempsMax la limite de temps que l'on doit respecter
	 * @param estimation comment estimer la profondeur à aller :
	 * 						-2 pour commencer la recherche à une profondeur juste inférieur à celle idéal
	 * 						-1 pour commencer la recherche à la profondeur idéale
	 * 						>0 pour commencer à la valeur donnée
	 * @param forceArret permet (si false) de s'autoriser un petit dépassement du temps si on est juste au dessus
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private CoupFousFous lanceAlphaBeta(Heuristique h, int profRapide, long tempsMax, int estimation) {

		System.out.println("On a " + (tempsMax / 1000) + "s pour trouver un bon coup");
		long heureDebut = System.currentTimeMillis();

		// On initialise notre algorithme de recherche
		algo = choisiAlgo(h, moiJoueur, luiJoueur, profRapide);

		// Calcul du coup rapide
		CoupFousFous coup = (CoupFousFous) algo.meilleurCoup(plateau);

		long tempsPasseTotal = System.currentTimeMillis() - heureDebut;
		System.out.println("Coup rapide calculé en " + tempsPasseTotal + "ms pour un horizon de " + profRapide);


		int profIdeal = estimation;
		// Calcul de la profondeur idéal
		if (estimation < 0) {
			// En partant de la profondeur de base on va incrémenter
			profIdeal = profRapide;

			// Tant qu'on doit avoir le temps on regarde le coup suivant
			long tempsActuel = tempsPasseTotal;
			while (tempsActuel <= (tempsMax - tempsPasseTotal - 500) && profIdeal + coupJoues < 42) {
				profIdeal++;

				tempsActuel = estimeTemps(tempsActuel, true);
			}

			profIdeal += estimation;
		}
		// Pour forcer à aller plus loins que le coups rapide
		profIdeal = profIdeal <= profRapide ? profRapide + 1 : profIdeal;
		System.out.println("Profondeur idéal calculé : " + profIdeal);


		tempsPasseTotal = System.currentTimeMillis() - heureDebut;


		// Tant qu'on a le temps on tente d'aller plus loins
		// et qu'on a pas touché le fond
		while (tempsPasseTotal < tempsMax && profIdeal + coupJoues < 42 && coup.etat == CoupFousFous.RIEN) {
			System.out.println("Recherche à profondeur " + profIdeal);

			CoupJeu[] coupTrouve = new CoupJeu[]{null};

			algo.setProfMax(profIdeal);

			Runnable recherche = new Runnable() {

				public void run() {
					coupTrouve[0] = algo.meilleurCoup(plateau);
				}
			};


			long heureDebutAB = System.currentTimeMillis();


			// On lance la recherche 
			Thread thread = new Thread(recherche);
			thread.start();
			try {
				thread.join(Math.max(0, tempsMax - tempsPasseTotal + 500));
			}
			catch (InterruptedException e) {}
			thread.interrupt();
			thread.stop();


			tempsPasseTotal = System.currentTimeMillis() - heureDebut;
			long tempsPasseAB = System.currentTimeMillis() - heureDebutAB;


			// Si un coup a été trouvé on l'ajoute
			if (coupTrouve[0] != null) {
				coup = (CoupFousFous) coupTrouve[0];
				System.out.println("Coup valide trouvé");
			} else {
				profIdeal--;
				System.out.println("On a du arreter avant");
			}

			// On regarde si on a le temps d'aller plus loins
			// Vérifie si le temps minimum qu'il nous faudra est plus faible que temps restant
			if (estimeTemps(tempsPasseAB, false) < (tempsMax - tempsPasseTotal)) {
				// Si oui on va plus profond au prochain coup
				profIdeal++;
				tempsPasseTotal = System.currentTimeMillis() - heureDebut;
				System.out.println("On avance");
			} else {
				// Si on a pas le temps on sort
				tempsPasseTotal = tempsMax;
				System.out.println("Pas le temps d'aller plus loins");
			}
		}

		if (coup.etat == CoupFousFous.GAGNANT) {
			System.out.println("On a touché le fond, apparement on est sûr de gagner.");
			gagnant = profIdeal;
		} else if (coup.etat == CoupFousFous.PERDANT) {
			System.out.println("On a touché le fond, apparement on est sûr de perdre.");
			gagnant = -profIdeal;
		} else {
			gagnant = 0;
		}

		System.out.println("On est allé jusqu'à profondeur " + profIdeal);

		return coup;
	}

	@Override
	public String choixMouvement() {
		long startTime = System.currentTimeMillis();

		if (coupJoues >= 4) {
			algoDico = null;
		}

		System.out.println("\n\n\nChoix du mouvement pour mon " + (coupJoues + 1) + "ème coup");
		System.out.println("Il me reste " + tempsRestant + "ms à jouer");

		int coupsRestantEstime = (int) (1.445 * plateau.heuristiqueNombrePions(jNoir)
				+ 0.6907 * plateau.heuristiqueNombrePions(jBlanc) - 1.2358);

		coupsRestantEstime = (coupsRestantEstime * 2 + (33 - coupJoues)) / 3;
		System.out.println("On est à environ " + coupsRestantEstime + " coups de la fin\n");

		CoupFousFous coup;
		if (gagnant > 0) {
			gagnant = Math.max(gagnant - 2, 1);
			System.out.println("On est sûr de gagner, on avance vite");

			coup = lanceAlphaBeta(HeuristiquesFousFous.hzero, gagnant, tempsRestant, -1);
		} else if (gagnant < 0) {
			gagnant = Math.min(gagnant + 2, -1);
			System.out.println("On a perdu à coup sûr, mais on va tenter quelque chose");

			System.out.println("Est-on vraiment fichu ?");
			coup = lanceAlphaBeta(HeuristiquesFousFous.hlent, -gagnant + 1, 2 * tempsRestant / 3, -1);

			if (coup.etat == CoupFousFous.PERDANT) {
				System.out.println("Oui, mais on va jouer dignement");
				int nbCoupsMin = 2 * plateau.comptePions(moiJoueur) - 1;

				algo = choisiAlgo(HeuristiquesFousFous.hlent, moiJoueur, luiJoueur, nbCoupsMin);
				coup = (CoupFousFous) algo.meilleurCoup(plateau);
			} else {
				System.out.println("Non, tout vas bien, l'adversaire s'est trompé, on va gagner");
			}
		} else if (coupJoues < 4) {
			System.out.println("On utilise notre dictionnaire");
			coup = (CoupFousFous) algoDico.meilleurCoup(plateau);

		} else if (coupJoues < 9) {
			System.out.println("On utilise ID spécial début (pour aller plus vite)");

			coup = lanceAlphaBeta(HeuristiquesFousFous.hdebut,
					profRapide(coupJoues, plateau.listerPions(moiJoueur).size()),
					2 * tempsRestant / Math.max(3, coupsRestantEstime - 8), -2);

		} else if (coupJoues < 25) {
			System.out.println("On utilise ID normal");

			coup = lanceAlphaBeta(HeuristiquesFousFous.hlent,
					profRapide(coupJoues, plateau.listerPions(moiJoueur).size()),
					2 * tempsRestant / Math.max(3, coupsRestantEstime - 14), -2);
		} else {
			System.out.println("On touche forcément le fond");
			algo = choisiAlgo(HeuristiquesFousFous.hzero, moiJoueur, luiJoueur, 15);
			coup = (CoupFousFous) algo.meilleurCoup(plateau);
		}


		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;

		tempsRestant -= elapsedTime;

		System.out.println("Coup " + coup + " trouvé en " + (elapsedTime / 1000) + " secondes" + " (il reste "
				+ (tempsRestant / 1000) + " secondes à jouer)\n\n");


		plateau.joue(moiJoueur, coup);
		coupJoues++;
		return coup.toString();
	}

	/**
	 * Estime la profondeur permetant de jouer un coup rapide (en dessous de 2 secondes)
	 * selon le nombre de coups joués et le facteur de branchement.
	 */
	private int profRapide(int coupJoues, int facteurBranche) {

		int estimation = 0;

		if (coupJoues < 8) {
			estimation = 7;
		} else if (coupJoues < 12) {
			estimation = 8;
		} else if (coupJoues < 14) {
			estimation = 9;
		} else if (coupJoues < 16) {
			estimation = 10;
		} else if (coupJoues < 17) {
			estimation = 11;
		} else if (coupJoues < 20) {
			estimation = 12;
		} else {
			estimation = 15;
		}

		if (facteurBranche > 30) {
			estimation += 7;
		} else if (facteurBranche > 16) {
			estimation += 8;
		} else if (facteurBranche > 14) {
			estimation += 9;
		} else if (facteurBranche > 10) {
			estimation += 10;
		} else if (facteurBranche > 9) {
			estimation += 11;
		} else if (facteurBranche > 8) {
			estimation += 12;
		} else {
			estimation += 15;
		}

		if (archi < 64 && coupJoues > 12) {
			estimation -= 2;
		}

		return estimation / 2;
	}

	public static void afficheBas(String s, int sleep) throws InterruptedException {
		System.out.println(
				"\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
		System.out.println(s);
		System.out.flush();
		Thread.sleep(sleep);
	}

	@Override
	public void declareLeVainqueur(int colour) {
		if (colour == moiInt) {
			try {
				afficheBas("Gagné...\n", 1000);
				afficheBas("Gagné...\n( °_°)", 1500);
				afficheBas("Gagné...\n( °_°)>-■-■", 1000);
				afficheBas("Gagné...", 0);
				System.out.print("(-■_■)");
				Thread.sleep(1000);
				for (char c : " #Yeeeeaaaaahhh".toCharArray()) {
					Thread.sleep(100);
					System.out.print(c);
				}
				Thread.sleep(750);
				System.out.print("\n");
				Thread.sleep(750);
				System.out.print(".");
				Thread.sleep(750);
				System.out.print(".");
				Thread.sleep(750);
				System.out.print(".");
				Thread.sleep(750);
				for (int i = 0; i < 4; i++) {
					afficheBas("Gagné...\n/(-■_■)/", 250);
					afficheBas("Gagné...\n-(-■_■)/", 250);
					afficheBas("Gagné...\n-(-■_■)-", 250);
					afficheBas("Gagné...\n\\(-■_■)-", 250);
					afficheBas("Gagné...\n\\(-■_■)\\", 250);
					afficheBas("Gagné...\n\\(-■_■)-", 250);
					afficheBas("Gagné...\n-(-■_■)-", 250);
					afficheBas("Gagné...\n-(-■_■)/", 250);
					afficheBas("Gagné...\n/(-■_■)/", 250);
					afficheBas("Gagné...\n\\(-■_■)\\", 250);
					afficheBas("Gagné...\n/(-■_■)/", 250);
					afficheBas("Gagné...\n\\(-■_■)\\", 250);
				}
				afficheBas("Easy peasy lemon squeezy\n\\(-■_■)/", 250);
			}
			catch (InterruptedException e) {
				System.out.println("Yes we did it, c'est gagné \\(-■_■)/");
			}
		} else {
			try {
				afficheBas("Perdu...\n", 1000);
				afficheBas("Perdu...\n(°_°) ┬─┬", 1000);
				afficheBas("Perdu...\n(/°o°)/︵┴─┴", 1000);
				afficheBas("Perdu...\n(;_;)    ┴─┴", 1000);
				afficheBas("Perdu...\n(°_°)    ┴─┴", 1000);
				afficheBas("Perdu...\n(;_;)    ┴─┴", 1000);

				afficheBas("Perdu...\n( ;_;)", 1000);
				for (int i = 0; i < 40; i++) {
					String s = "";
					for (int j = 0; j < i; j++) {
						s += "    ";
					}
					s += "( ;_;)";
					afficheBas("Perdu...\n" + s, 100);
				}
				afficheBas("J'ai échoué\n('-_-)", 1000);
			}
			catch (InterruptedException e) {
				System.out.println("J'ai échoué ('-_-)");
			}
		}
	}

	@Override
	public void mouvementEnnemi(String coup) {
		plateau.play(coup, luiJoueur.toString());
		coupJoues++;
	}

	@Override
	public String binoName() {
		return "Kazi & Vachaudez";
	}

}
