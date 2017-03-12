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

	/** Le joueur que joue "Blanc" **/
	private static Joueur joueurBlanc;

	/** Le joueur que joue "Noir" **/
	private static Joueur joueurNoir;

	/************ Attributs  ************/

	private int plateau[][];

	/************* Constructeurs ****************/

	public PlateauFousFous() {
		for(int i = 0; i<8; i +=2){
			for(int j = 1; j<8; j+= 2){
				this.plateau[i][j]= BLANC;
			}
		}
		
		for(int i = 1; i<8; i +=2){
			for(int j = 0; j<8; j+= 2){
				this.plateau[i][j]= NOIR;
			}
		}
		
		for(int i = 0; i<8; i +=2){
			for(int j = 0; j<8; j+= 2){
				this.plateau[i][j]= VIDE;
			}
		}
		
	}

	public PlateauFousFous(int depuis[][]) {
		this.plateau = depuis;
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

	/************* M�thodes de l'interface PlateauJeu ****************/
	
	public void affiche(){
		for(int i = 0; i<8; i ++){
			for(int j = 0; j<8; j++){
				System.out.print(getCase(i,j));
			}
		}
	}
	
	public int getCase(int i, int j){
		return this.plateau[i][j];
	}

	@Override
	public ArrayList<CoupJeu> coupsPossibles(Joueur jr) {
		ArrayList<CoupJeu> listeCoups = new ArrayList<CoupJeu>();
		if(isJoueurBlanc(jr)){
			for(int i = 0; i<8; i+=2){
				for(int j = 1; j < 8; j+=2){
					//A completer
				}
			} 
		} else {
			for(int i = 1; i<8; i +=2){
				for(int j = 0; j<8; j+= 2){
					//A completer
				}
			}
			
		}
		return listeCoups;
	}
	
	
	 

	@Override
	public void joue(Joueur j, CoupJeu c) {
		
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
		PlateauFousFous plateau = new PlateauFousFous();
		plateau.affiche();
	}

	/********************** Autres méthodes ******************/

	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private ArrayList<CoupJeu> listeCoupsMange(Joueur j) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private ArrayList<CoupJeu> listeCoupsMenace(Joueur j) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private boolean peutManger(Joueur j){
		// TODO Auto-generated method stub
		return false;
	}
	
	private boolean peutMenacer(Joueur j){
		// TODO Auto-generated method stub
		return false;
	}

}