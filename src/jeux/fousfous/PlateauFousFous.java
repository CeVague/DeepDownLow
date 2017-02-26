package jeux.fousfous;

import iia.jeux.modele.*;
import iia.jeux.modele.joueur.Joueur;

import java.util.ArrayList;

public class PlateauFousFous implements PlateauJeu, Partie1 {


	/***************** Constantes *****************/

	private final static int VIDE = 0;
	private final static int BLANC = 1;
	private final static int NOIR = 2;

	/*********** Paramètres de classe ************/

	/** Le joueur que joue "Blanc" */
	private static Joueur joueurBlanc;

	/** Le joueur que joue "Noir" */
	private static Joueur joueurNoir;

	/************ Attributs  ************/

	private int plateau[][];

	/************* Constructeurs ****************/

	public PlateauFousFous() {
		// TODO Auto-generated method stub
	}

	public PlateauFousFous(int depuis[][]) {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
	}

	@Override
	public boolean finDePartie() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public PlateauJeu copy() {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String[] mouvementsPossibles(String player) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void play(String move, String player) {
		// TODO Auto-generated method stub
	}

	/************* Méthodes demandée pour la 2eme partie ****************/

	/** Ilustration de l'utilisation de nos méthodes de manière convaincante **/
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

	/********************** Autres méthodes ******************/


}
