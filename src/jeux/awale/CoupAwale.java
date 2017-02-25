package jeux.awale;

import iia.jeux.modele.CoupJeu;

public class CoupAwale implements CoupJeu {

	/****** Attributs *******/
	int place;


	/****** Clonstructeur *******/

	public CoupAwale(int c) {
		this.place = c;
	}

	/****** Accesseurs *******/

	public int getCase() {
		return place;
	}

	/****** Accesseurs *******/

	public String toString() {
		return "("+place+")";
	}
}
