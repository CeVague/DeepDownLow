package jeux.fousfousbitboard;

import iia.jeux.modele.*;
import iia.jeux.modele.joueur.Joueur;

import java.util.ArrayList;

public class PlateauFFB implements PlateauJeu, Partie1 {


	/***************** Constantes *****************/

	private final static int VIDE = 0;
	private final static int BLANC = 1;
	private final static int NOIR = 2;

	private final static long masquePlateau = 0b1010101001010101101010100101010110101010010101011010101001010101L;
	private final static long masqueDiagGauche = 0b0000000100000010000001000000100000010000001000000100000010000001L;
	private final static long masqueDiagGaucheHaute = 0b1000000100000010000001000000100000010000001000000100000010000000L;
	private final static long masqueDiagDroit = 0b1000000001000000001000000001000000001000000001000000001000000001L;

	// private final static long un =
	// 0b1000000000000000000000000000000000000000000000000000000000000000L;

	/*********** Paramètres de classe ************/

	/** Le joueur que joue "Blanc" **/
	private static Joueur joueurBlanc;

	/** Le joueur que joue "Noir" **/
	private static Joueur joueurNoir;

	/************ Attributs  ************/

	private long plateauBlanc;
	private long plateauNoir;

	/************* Constructeurs ****************/

	public PlateauFFB() {
		plateauBlanc = 0b0101010100000000010101010000000001010101000000000101010100000000L;
		plateauNoir = 0b0000000010101010000000001010101000000000101010100000000010101010L;
	}

	public PlateauFFB(long plateauBlanc, long plateauNoir) {
		this.plateauBlanc = plateauBlanc;
		this.plateauNoir = plateauNoir;
	}

	public PlateauFFB(int depuis[][]) {
		plateauBlanc = 0L;
		plateauNoir = 0L;

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				switch (depuis[i][j]) {
					case BLANC:
						plateauBlanc |= 1L << (8 * i + j);
						break;
					case NOIR:
						plateauNoir |= 1L << (8 * i + j);
						break;
				}
			}
		}
	}

	/************* Gestion des paramètres de classe ******************/

	public static void setJoueurs(Joueur jb, Joueur jn) {
		joueurBlanc = jb;
		joueurNoir = jn;
	}

	public boolean isJoueurBlanc(Joueur jb) {
		return joueurBlanc.equals(jb);
	}

	public boolean isJoueurNoir(Joueur jn) {
		return joueurNoir.equals(jn);
	}

	/************* Méthodes de l'interface PlateauJeu ****************/

	@Override
	public ArrayList<CoupJeu> coupsPossibles(Joueur j) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void joue(Joueur j, CoupJeu c) {
		CoupFFB cNew = (CoupFFB) c;

		if (j.equals(joueurBlanc)) {
			plateauBlanc ^= 1L << cNew.getAvant();
			plateauBlanc ^= 1L << cNew.getApres();
			plateauNoir &= ~(1L << cNew.getApres());
		} else {
			plateauNoir ^= 1L << cNew.getAvant();
			plateauNoir ^= 1L << cNew.getApres();
			plateauBlanc &= ~(1L << cNew.getApres());
		}
	}

	@Override
	public boolean finDePartie() {
		return (plateauBlanc == 0 || plateauBlanc == 0);
	}

	@Override
	public PlateauJeu copy() {
		return new PlateauFFB(this.plateauBlanc, this.plateauNoir);
	}

	@Override
	public boolean coupValide(Joueur j, CoupJeu c) {
		// TODO Auto-generated method stub
		return false;
	}

	/************* Méthodes de l'interface Partie1 ****************/

	@Override
	public void setFromFile(String fileName) {
		// TODO Auto-generated method stub
	}

	@Override
	public void saveToFile(String fileName) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean estValide(String move, String player) {
		return coupValide(retourneJoueur(player), new CoupFFB(move));
		// TODO Auto-generated method stub
	}

	@Override
	public String[] mouvementsPossibles(String player) {
		ArrayList<CoupJeu> temp = coupsPossibles(retourneJoueur(player));
		// TODO Auto-generated method stub

		String[] liste = new String[temp.size()];
		for (int i = 0; i < temp.size(); i++) {
			liste[i] = temp.get(i).toString();
		}
		return liste;
	}

	@Override
	public void play(String move, String player) {
		joue(retourneJoueur(player), new CoupFFB(move));
	}

	/************* Méthodes demandée pour la 2eme partie ****************/

	/** Ilustration de l'utilisation de nos méthodes de manière convaincante **/
	// public static void main(String[] args) {
	// TODO Auto-generated method stub
	// }

	/****************** Accesseurs **********************/

	public long getPlateauBlanc() {
		return plateauBlanc;
	}

	public long getPlateauNoir() {
		return plateauNoir;
	}

	/********************** Autres méthodes ******************/

	/**
	 * Permet de savoir si le chemin de Avant à Apres
	 * est libre de tout obstacles
	 * @param Avant la position du pion au debut
	 * @param Apres la position devant être atteinte
	 * 
	 * Notes : Il faut que Avant et Après soint sur la même diagonale
	 * 
	 * Vitesse : trop rapide
	 */
	public boolean cheminLibre(byte Avant, byte Apres) {
		// Il faut que Avant>Apres
		if (Avant < Apres) {
			byte temp = Avant;
			Avant = Apres;
			Apres = temp;
		}

		// On choisi le masque correspondant à
		// la diagonale entre ces deux points
		long masque;
		if (Avant % 8 > Apres % 8) {
			masque = masqueDiagDroit;
		} else {
			masque = masqueDiagGauche;
		}
		// On place le masque au niveau du pion le plus bas (Apres)
		masque = masque << Apres;

		// On évite que Apres ne soit compté dans le calcul
		Apres++;

		long A = 1L << Avant;
		long B = 1L << Apres;

		return ((A - B) & masque & (plateauNoir | plateauBlanc)) == 0;
	}

	/**
	 * Permet de savoir si le chemin suivit durant le coup c
	 * est libre de tout obstacles
	 * @param c un coup (on le suppose valide)
	 * 
	 * Vitesse : trop rapide
	 */
	public boolean cheminLibre(CoupFFB c) {
		return cheminLibre(c.getAvant(), c.getApres());
	}


	/**
	 * Permet de savoir si le pion d'un joueur est en position
	 * d'en manger au moins un autre
	 * @param j le joueur concerné
	 * @param p le pion concerné
	 * 
	 * Vitesse : trop rapide
	 */
	public boolean peutManger(Joueur j, PionFFB p) {
		long plateauNous = retournePlateau(j);
		long plateauEux = retournePlateauEnnemi(j);


		byte pion = p.getPion();
		byte antepion = (byte) (63 - p.getPion());


		plateauNous &= ~(1L << pion);

		plateauEux &= ~(1L << pion); // A enlever une fois les tests fait

		long diagGaucheHautNous = plateauNous & (masqueDiagGauche << pion);
		long diagGaucheHautEux = plateauEux & (masqueDiagGauche << pion);

		long diagGaucheBasNous = plateauNous & (masqueDiagGaucheHaute >>> antepion);
		long diagGaucheBasEux = plateauEux & (masqueDiagGaucheHaute >>> antepion);

		long diagDroiteHautNous = plateauNous & (masqueDiagDroit << pion);
		long diagDroiteHautEux = plateauEux & (masqueDiagDroit << pion);

		long diagDroiteBasNous = plateauNous & (masqueDiagDroit >>> antepion);
		long diagDroiteBasEux = plateauEux & (masqueDiagDroit >>> antepion);

		if (diagGaucheBasNous < diagGaucheBasEux || diagDroiteBasNous < diagDroiteBasEux
				|| Long.reverse(diagGaucheHautNous) < Long.reverse(diagGaucheHautEux)
				|| Long.reverse(diagDroiteHautNous) < Long.reverse(diagDroiteHautEux)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Permet de savoir si le pion d'un joueur est en position
	 * d'en menacer au moins un autre
	 * @param j le joueur concerné
	 * @param p le pion concerné
	 */
	public boolean peutMenacer(Joueur j, PionFFB p) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Liste tous les pions que possede un joueur
	 * @param j le joueur concerné
	 * 
	 * Vitesse : 11.000.000 fois par secondes
	 */
	public ArrayList<PionFFB> listerPions(Joueur j) {
		long plateau = retournePlateau(j);
		ArrayList<PionFFB> listeCoups = new ArrayList<PionFFB>(comptePions(j));

		long pion = Long.lowestOneBit(plateau);
		while (pion != 0) {
			listeCoups.add(new PionFFB(Long.numberOfTrailingZeros(plateau)));

			plateau &= ~pion;
			pion = Long.lowestOneBit(plateau);
		}
		return listeCoups;
	}

	/**
	 * Liste tous les coups qui peuvent être joués avec un des pions
	 * @param j le joueur qui veux joueur
	 * @param p le pion qui doit être joué
	 */
	public ArrayList<CoupFFB> coupsPossibles(Joueur j, PionFFB p) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Liste tous les coups qui permettent à un pion d'en manger un autre
	 * @param j le joueur qui veux joueur
	 * @param p le pion qui veux manger les autres
	 * 
	 * Vitesse : 160.000.000 fois par secondes
	 */
	public ArrayList<CoupFFB> listerMangeable(Joueur j, PionFFB p) {
		long plateauNous = retournePlateau(j);
		long plateauEux = retournePlateauEnnemi(j);

		byte pion = p.getPion();
		byte nonpion = (byte) (63 - pion);

		plateauNous &= ~(1L << pion);

		plateauEux &= ~(1L << pion); // A enlever une fois les tests fait


		ArrayList<CoupFFB> listeDesCoups = new ArrayList<CoupFFB>(4);

		long Nous = plateauNous & (masqueDiagGauche << pion);
		long Eux = plateauEux & (masqueDiagGauche << pion);
		if (Long.reverse(Nous) < Long.reverse(Eux)) {
			// System.out.println(PionFFB.CoordToString((byte)
			// Long.numberOfTrailingZeros(diagGaucheHautEux)));
			listeDesCoups.add(new CoupFFB(pion, (byte) Long.numberOfTrailingZeros(Eux)));
		}

		Nous = plateauNous & (masqueDiagDroit << pion);
		Eux = plateauEux & (masqueDiagDroit << pion);
		if (Long.reverse(Nous) < Long.reverse(Eux)) {
			// System.out.println(PionFFB.CoordToString((byte)
			// Long.numberOfTrailingZeros(diagDroiteHautEux)));
			listeDesCoups.add(new CoupFFB(pion, (byte) Long.numberOfTrailingZeros(Eux)));
		}

		Nous = plateauNous & (masqueDiagGaucheHaute >>> nonpion);
		Eux = plateauEux & (masqueDiagGaucheHaute >>> nonpion);
		if (Nous < Eux) {
			// System.out.println(PionFFB.CoordToString((byte)
			// (63-Long.numberOfLeadingZeros(diagGaucheBasEux))));
			listeDesCoups.add(new CoupFFB(pion, (byte) (63 - Long.numberOfLeadingZeros(Eux))));
		}

		Nous = plateauNous & (masqueDiagDroit >>> nonpion);
		Eux = plateauEux & (masqueDiagDroit >>> nonpion);
		if (Nous < Eux) {
			// System.out.println(PionFFB.CoordToString((byte)
			// (63-Long.numberOfLeadingZeros(diagDroiteBasEux))));
			listeDesCoups.add(new CoupFFB(pion, (byte) (63 - Long.numberOfLeadingZeros(Eux))));
		}

		return listeDesCoups;
	}

	/**
	 * Liste tous les pions d'un joueur pouvant manger
	 * @param j le joueur qui veux joueur
	 */
	public ArrayList<PionFFB> listerMangeur(Joueur j) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Liste tous les pions qu'un pion peut menacer
	 * @param j le joueur qui veux joueur
	 * @param p le pion qui veux menacer (on suppose qu'il ne peut pas manger)
	 */
	public ArrayList<CoupFFB> listerMenacable(Joueur j, PionFFB p) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Liste tous les pions d'un joueur pouvant menacer les autres
	 * @param j le joueur qui veux joueur
	 */
	public ArrayList<PionFFB> listerMenaceur(Joueur j) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Retourne ne nombre de pions encore en jeu d'un joueur
	 * @param j le joueur concerné
	 */
	public int comptePions(Joueur j) {
		return Long.bitCount(retournePlateau(j));
	}


	/********************** Aides et compactage ******************/

	public long retournePlateau(Joueur j) {
		if (j.equals(joueurNoir)) {
			return plateauNoir;
		} else {
			return plateauBlanc;
		}
	}

	public long retournePlateau(String j) {
		if (j.compareTo("noir") == 0) {
			return plateauNoir;
		} else {
			return plateauBlanc;
		}
	}

	public long retournePlateauEnnemi(Joueur j) {
		if (j.equals(joueurNoir)) {
			return plateauBlanc;
		} else {
			return plateauNoir;
		}
	}

	public long retournePlateauEnnemi(String j) {
		if (j.compareTo("noir") == 0) {
			return plateauBlanc;
		} else {
			return plateauNoir;
		}
	}

	public Joueur retourneJoueur(String player) {
		if (player.compareTo("noir") == 0) {
			return joueurNoir;
		} else {
			return joueurBlanc;
		}
	}

	public Joueur retourneJoueurEnnemi(String player) {
		if (player.compareTo("noir") == 0) {
			return joueurBlanc;
		} else {
			return joueurNoir;
		}
	}

	public String toString() {
		String represente = "";

		for (int i = 63; i >= 0; i--) {

			if (((plateauBlanc >>> i) & 1L) != 0) {
				represente += "b";
			} else if (((plateauNoir >>> i) & 1L) != 0) {
				represente += "n";
			} else {
				represente += " ";
			}

			if (i % 8 == 0) {
				represente += "\n";
			}
		}
		return represente;
	}

	public String toString(long tableau) {
		String represente = "";

		for (int i = 63; i >= 0; i--) {

			if (((tableau >>> i) & 1L) != 0) {
				represente += "0";
			} else {
				represente += "O";
			}

			if (i % 8 == 0) {
				represente += "\n";
			}
		}
		return represente;
	}


	public void print() {
		System.out.println("  A B C D E F G H");
		System.out.println(" ┌─┬─┬─┬─┬─┬─┬─┬─┐");
		for (int i = 63; i >= 0; i--) {

			if (i % 8 == 7) {
				System.out.print((8 - i / 8) + "│");
			}

			if (((plateauBlanc >>> i) & 1L) != 0) {
				System.out.print("b│");
			} else if (((plateauNoir >>> i) & 1L) != 0) {
				System.out.print("n│");
			} else {
				System.out.print(" │");
			}

			if (i % 8 == 0 && i != 0) {
				System.out.print("\n ├─┼─┼─┼─┼─┼─┼─┼─┤\n");
			}
		}
		System.out.print("\n └─┴─┴─┴─┴─┴─┴─┴─┘\n");
	}
}
