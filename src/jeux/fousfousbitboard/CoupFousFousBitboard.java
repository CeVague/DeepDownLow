package jeux.fousfousbitboard;

import iia.jeux.modele.CoupJeu;

public class CoupFousFousBitboard implements CoupJeu {

	/******** Attributs *********/
	
	private byte avant, apres;

	/****** Clonstructeur *******/
	
	public CoupFousFousBitboard(String coord) {
		setCoord(StringToCoord(coord));
	}
	
	public CoupFousFousBitboard(byte avant, byte apres) {
		this.avant = avant;
		this.apres = apres;
	}
	
	public CoupFousFousBitboard(byte[] coord) {
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
		
		coordFinal[0] = (byte) (63 - (coord.codePointAt(0) - 65) - 8*(coord.codePointAt(1) - 49));
		
		coordFinal[1] = (byte) (63 - (coord.codePointAt(3) - 65) - 8*(coord.codePointAt(4) - 49));
		
		return coordFinal;
	}
	
	public static byte PosStringToCoord(String coord){
		return (byte) (63 - (coord.codePointAt(0) - 65) - 8*(coord.codePointAt(1) - 49));
	}
	
	public static String CoordToString(byte[] coord){
		String coordFinal = "";
		
		coordFinal += (char) (7 - coord[0]%8 + 65);
		coordFinal += (char) (7 - coord[0]/8 + 49);
		coordFinal += "-";
		coordFinal += (char) (7 - coord[1]%8 + 65);
		coordFinal += (char) (7 - coord[1]/8 + 49);
		
		return coordFinal;
	}
	
	public static String PosCoordToString(byte coord){
		String coordFinal = "";
		
		coordFinal += (char) (7 - coord%8 + 65);
		coordFinal += (char) (7 - coord/8 + 49);
		
		return coordFinal;
	}

}

