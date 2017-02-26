package jeux.fousfous;

import iia.jeux.modele.CoupJeu;

public class CoupFousFous implements CoupJeu {

	/******** Attributs *********/
	
	private int xAvant, yAvant;
	private int xApres, yApres;

	/****** Clonstructeur *******/
	
	public CoupFousFous(String coord) {
		setCoord(StringToCoord(coord));
	}
	
	public CoupFousFous(int[] coord) {
		setCoord(coord);
	}
	
	private void setCoord(int[] coord){
		xAvant = coord[0];
		yAvant = coord[1];
		
		xApres = coord[2];
		yApres = coord[3];
	}

	/******** Accesseurs ********/
	
	public int[] getAvant() {
		return new int[]{xAvant, yAvant};
	}
	
	public int[] getApres() {
		return new int[]{xApres, yApres};
	}
	
	public String toString() {
		return CoordToString(new int[]{xAvant, yAvant, xApres, yApres});
	}

	/****** Convertisseurs ******/
	
	public static int[] StringToCoord(String coord){
		int[] coordFinal = new int[4];
		
		coordFinal[0] = coord.codePointAt(0) - 65;
		coordFinal[1] = coord.codePointAt(1) - 49;
		
		coordFinal[2] = coord.codePointAt(3) - 65;
		coordFinal[3] = coord.codePointAt(4) - 49;
		
		return coordFinal;
	}
	
	public static String CoordToString(int[] coord){
		String coordFinal = "";
		
		coordFinal += (char) (coord[0] + 65);
		coordFinal += (char) (coord[1] + 49);
		coordFinal += "-";
		coordFinal += (char) (coord[2] + 65);
		coordFinal += (char) (coord[3] + 49);
		
		return coordFinal;
	}

}

