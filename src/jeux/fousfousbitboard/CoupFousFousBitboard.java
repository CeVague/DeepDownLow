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
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		int[] coordFinal = new int[2];
		
		coordFinal[0] = coord.codePointAt(0) - 65;
		coordFinal[0] = coord.codePointAt(1) - 49;
		
		coordFinal[1] = coord.codePointAt(3) - 65;
		coordFinal[1] = coord.codePointAt(4) - 49;
		
		return coordFinal;
	}
	
	public static String CoordToString(int[] coord){
		// TODO Auto-generated method stub
		String coordFinal = "";
		
		coordFinal += (char) (coord[0] + 65);
		coordFinal += (char) (coord[0] + 49);
		coordFinal += "-";
		coordFinal += (char) (coord[1] + 65);
		coordFinal += (char) (coord[1] + 49);
		
		return coordFinal;
	}

}

