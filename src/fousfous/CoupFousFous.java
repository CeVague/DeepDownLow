package fousfous;

import iia.jeux.modele.CoupJeu;

/*
 * Représente un coup avec sa position de départ et sa
 * position d'arrivée.
 * Fournit quelques accésseurs et fonctions de convertions
 */
public class CoupFousFous implements CoupJeu {

	public static final byte RIEN = 0;
	public static final byte GAGNANT = 1;
	public static final byte PERDANT = -1;

	/******** Attributs *********/

	private final byte avant, apres;
	
	public byte etat = RIEN;

	/****** Clonstructeur *******/

	public CoupFousFous() {
		this.avant = 0;
		this.apres = 0;
	}

	public CoupFousFous(String coord) {
		byte[] coordByte = StringToCoord(coord);
		avant = coordByte[0];
		apres = coordByte[1];
	}

	public CoupFousFous(byte avant, byte apres) {
		this.avant = avant;
		this.apres = apres;
	}

	public CoupFousFous(byte[] coord) {
		avant = coord[0];
		apres = coord[1];
	}

	/******** Accesseurs ********/

	public byte getAvant() {
		return avant;
	}

	public byte getApres() {
		return apres;
	}

	public String toString() {
		return CoordToString(new byte[]{avant, apres});
	}

	/****** Convertisseurs ******/

	public static byte[] StringToCoord(String coord) {
		byte[] coordFinal = new byte[2];

		coordFinal[0] = (byte) (63 - (coord.codePointAt(0) - 65) - 8 * (coord.codePointAt(1) - 49));

		coordFinal[1] = (byte) (63 - (coord.codePointAt(3) - 65) - 8 * (coord.codePointAt(4) - 49));

		return coordFinal;
	}

	public static byte PosStringToCoord(String coord) {
		return (byte) (63 - (coord.codePointAt(0) - 65) - 8 * (coord.codePointAt(1) - 49));
	}

	public static String CoordToString(byte[] coord) {
		String coordFinal = "";

		coordFinal += (char) (7 - coord[0] % 8 + 65);
		coordFinal += (char) (7 - coord[0] / 8 + 49);
		coordFinal += "-";
		coordFinal += (char) (7 - coord[1] % 8 + 65);
		coordFinal += (char) (7 - coord[1] / 8 + 49);

		return coordFinal;
	}

	public static String PosCoordToString(byte coord) {
		String coordFinal = "";

		coordFinal += (char) (7 - coord % 8 + 65);
		coordFinal += (char) (7 - coord / 8 + 49);

		return coordFinal;
	}

	/******* Vérificateur *******/

	/*
	 * Permet de vérifier si le coup actuel est possible
	 * (vérifie uniquement si Avant et Apres sont sur la même diagonale)
	 */
	public boolean coupValide() {
		return (Math.abs(avant % 8 - apres % 8) == Math.abs(avant / 8 - apres / 8)) && (avant != apres);
	}
	
	public boolean equals(Object obj) {
		CoupFousFous o = (CoupFousFous) obj;
		return (avant == o.getAvant()) && (apres == o.getApres());
	}
}

