package affrontements;

import java.util.Scanner;

import fousfous.*;
import iia.jeux.modele.joueur.Joueur;

public class JoueurHumain implements IJoueur {

	static final int BLANC = -1;
	static final int NOIR = 1;

	static final Joueur jBlanc = new Joueur("blanc");
	static final Joueur jNoir = new Joueur("noir");

	int moiInt;
	Joueur moiJoueur;

	Joueur luiJoueur;

	PlateauFousFous plateau;

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
	}

	@Override
	public int getNumJoueur() {
		return moiInt;
	}

	@Override
	public String choixMouvement() {
		CoupFousFous coup = new CoupFousFous("-----");
		Scanner sc = null;

		System.out.println("Coups jouables :\n" + plateau.coupsPossibles(moiJoueur));

		while (!plateau.coupValide(moiJoueur, coup)) {
			sc = new Scanner(System.in);

			System.out.println("Veuillez saisir le coup joué ou le pion à observer :");

			String str = sc.nextLine();

			if (str.length() <= 2) {
				PionFousFous pion = new PionFousFous(str);
				System.out.println("Coups jouables :\n" + plateau.coupsPossibles(moiJoueur, pion));
			} else {
				coup = new CoupFousFous(str);
			}
		}

		plateau.joue(moiJoueur, coup);

		return coup.toString();
	}

	@Override
	public void declareLeVainqueur(int colour) {
		if (colour == moiInt) {
			System.out.println("Easy peasy lemon squeezy\n\\(-■_■)/");
		} else {
			System.out.println("J'ai échoué\n('-_-)");
		}
	}

	@Override
	public void mouvementEnnemi(String coup) {
		plateau.play(coup, luiJoueur.toString());
	}

	@Override
	public String binoName() {
		return "Kazi & Vachaudez";
	}

}
