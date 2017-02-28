package jeux.fousfousbitboard;

import iia.jeux.modele.CoupJeu;

public class CoupFFB implements CoupJeu {

	/******** Attributs *********/
	
	private byte avant, apres;

	/****** Clonstructeur *******/
	
	public CoupFFB() {
		this.avant = 0;
		this.apres = 0;
	}
	
	public CoupFFB(String coord) {
		setCoord(StringToCoord(coord));
	}
	
	public CoupFFB(byte avant, byte apres) {
		this.avant = avant;
		this.apres = apres;
	}
	
	public CoupFFB(byte[] coord) {
		setCoord(coord);
	}
	
	private void setCoord(byte[] coord){
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
	
	public static byte[] StringToCoord(String coord){
		byte[] coordFinal = new byte[2];
		
		// coordFinal[0] = (byte) (520 - coord.codePointAt(0) - (coord.codePointAt(1)<<3));
		coordFinal[0] = (byte) (63 - (coord.codePointAt(0) - 65) - 8*(coord.codePointAt(1) - 49));
		
		// coordFinal[1] = (byte) (520 - coord.codePointAt(3) - (coord.codePointAt(4)<<3));
		coordFinal[1] = (byte) (63 - (coord.codePointAt(3) - 65) - 8*(coord.codePointAt(4) - 49));
		
		return coordFinal;
	}
	
	public static byte PosStringToCoord(String coord){
		// return (byte) (520 - coord.codePointAt(0) - (coord.codePointAt(1)<<3));
		return (byte) (63 - (coord.codePointAt(0) - 65) - 8*(coord.codePointAt(1) - 49));
	}
	
	public static String CoordToString(byte[] coord){
		String coordFinal = "";
		
		/**
		coordFinal += (char) (72 - (coord[0] & 7));
		coordFinal += (char) (56 - (coord[0]>>>3));
		coordFinal += "-";
		coordFinal += (char) (72 - (coord[1] & 7));
		coordFinal += (char) (56 - (coord[1]>>>3));
		 */
		coordFinal += (char) (7 - coord[0]%8 + 65);
		coordFinal += (char) (7 - coord[0]/8 + 49);
		coordFinal += "-";
		coordFinal += (char) (7 - coord[1]%8 + 65);
		coordFinal += (char) (7 - coord[1]/8 + 49);
		
		return coordFinal;
	}
	
	public static String PosCoordToString(byte coord){
		String coordFinal = "";
		
		// coordFinal += (char) (72 - (coord & 7));
		// coordFinal += (char) (56 - (coord>>>3));
		coordFinal += (char) (7 - coord%8 + 65);
		coordFinal += (char) (7 - coord/8 + 49);
		
		return coordFinal;
	}

	/******* Vérificateur *******/

	/*
	 * Permet de vérifier si le coup actuel est possible
	 * (vérifie si Avant et Apres sont sur la même diagonale)
	 */
	public boolean coupValide(){
		return (Math.abs(avant%8 - apres%8) == Math.abs(avant/8 - apres/8)) && (avant != apres);
	}
}

