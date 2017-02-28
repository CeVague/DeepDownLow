package jeux.fousfousbitboard;

import iia.jeux.modele.CoupJeu;

public class PionFFB implements CoupJeu {

	/******** Attributs *********/

	private byte pion;

	/****** Clonstructeur *******/

	public PionFFB() {
		this.pion = 0;
	}

	public PionFFB(String coord) {
		this.pion = StringToCoord(coord);
	}

	public PionFFB(int pion) {
		this.pion = (byte) pion;
	}

	public PionFFB(byte pion) {
		this.pion = pion;
	}

	/******** Accesseurs ********/

	public byte getPion() {
		return pion;
	}

	public String toString() {
		return CoordToString(pion);
	}

	/****** Convertisseurs ******/

	public static byte StringToCoord(String coord) {
		// coordFinal[0] = (byte) (520 - coord.codePointAt(0) -
		// (coord.codePointAt(1)<<3));
		return (byte) (63 - (coord.codePointAt(0) - 65) - 8 * (coord.codePointAt(1) - 49));
	}

	public static String CoordToString(byte coord) {
		String coordFinal = "";

		// coordFinal += (char) (72 - (coord & 7));
		// coordFinal += (char) (56 - (coord>>>3));
		coordFinal += (char) (7 - coord % 8 + 65);
		coordFinal += (char) (7 - coord / 8 + 49);

		return coordFinal;
	}

}

