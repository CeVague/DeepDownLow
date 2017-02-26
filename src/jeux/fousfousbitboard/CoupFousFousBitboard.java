package jeux.fousfousbitboard;

import iia.jeux.modele.CoupJeu;

public class CoupFousFousBitboard implements CoupJeu {

	/******** Attributs *********/
	
	private int avant, apres;

	/****** Clonstructeur *******/
	
	public CoupFousFousBitboard(String coord) {
		setCoord(StringToCoord(coord));
	}
	
	public CoupFousFousBitboard(int[] coord) {
		setCoord(coord);
	}
	
	private void setCoord(int[] coord){
		avant = coord[0];
		apres = coord[1];
	}

	/******** Accesseurs ********/
	
	public int getAvant() {
		return avant;
	}
	
	public int getApres() {
		return apres;
	}
	
	public String toString() {
		return CoordToString(new int[]{avant, apres});
	}

	/****** Convertisseurs ******/
	
	public static int[] StringToCoord(String coord){
		int[] coordFinal = new int[2];
		
		coordFinal[0] = (coord.codePointAt(0) - 65) + 8*(coord.codePointAt(1) - 49);
		
		coordFinal[1] = (coord.codePointAt(3) - 65) + 8*(coord.codePointAt(4) - 49);
		
		return coordFinal;
	}
	
	public static String CoordToString(int[] coord){
		String coordFinal = "";
		
		coordFinal += (char) (coord[0]%8 + 65);
		coordFinal += (char) (coord[0]/8 + 49);
		coordFinal += "-";
		coordFinal += (char) (coord[1]%8 + 65);
		coordFinal += (char) (coord[1]/8 + 49);
		
		return coordFinal;
	}

}

