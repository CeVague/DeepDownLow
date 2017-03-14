package fousfous;

import iia.jeux.modele.CoupJeu;

public class PionFousFous implements CoupJeu {

	/******** Attributs *********/

	private byte pion;

	/****** Clonstructeur *******/

	public PionFousFous() {
		this.pion = 0;
	}

	public PionFousFous(String coord) {
		this.pion = StringToCoord(coord);
	}

	public PionFousFous(int pion) {
		this.pion = (byte) pion;
	}

	public PionFousFous(byte pion) {
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
		return (byte) (63 - (coord.codePointAt(0) - 65) - 8 * (coord.codePointAt(1) - 49));
	}

	public static String CoordToString(byte coord) {
		String coordFinal = "";

		coordFinal += (char) (7 - coord % 8 + 65);
		coordFinal += (char) (7 - coord / 8 + 49);

		return coordFinal;
	}

}

