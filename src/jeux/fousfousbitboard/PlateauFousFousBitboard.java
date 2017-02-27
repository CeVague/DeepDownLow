package jeux.fousfousbitboard;

import iia.jeux.modele.*;
import iia.jeux.modele.joueur.Joueur;

import java.util.ArrayList;

public class PlateauFousFousBitboard implements PlateauJeu, Partie1 {


	/***************** Constantes *****************/

	private final static int VIDE = 0;
	private final static int BLANC = 1;
	private final static int NOIR = 2;

	private final static long masquePlateau = 0b1010101001010101101010100101010110101010010101011010101001010101L;
	private final static long masqueDiagGauche = 0b1000000100000010000001000000100000010000001000000100000010000000L;
	private final static long masqueDiagDroite = 0b1000000001000000001000000001000000001000000001000000001000000001L;
	
	private final static long un = 0b1000000000000000000000000000000000000000000000000000000000000000L;

	/*********** Paramètres de classe ************/
	
	/** Le joueur que joue "Blanc" **/
	private static Joueur joueurBlanc;

	/** Le joueur que joue "Noir" **/
	private static Joueur joueurNoir;

	/************ Attributs  ************/

	public long plateauBlanc;
	public long plateauNoir;

	/************* Constructeurs ****************/

	public PlateauFousFousBitboard() {
		plateauBlanc = 0b0101010100000000010101010000000001010101000000000101010100000000L;
		plateauNoir = 0b0000000010101010000000001010101000000000101010100000000010101010L;
	}

	public PlateauFousFousBitboard(long plateauBlanc, long plateauNoir) {
		this.plateauBlanc = plateauBlanc;
		this.plateauNoir = plateauNoir;
	}

	public PlateauFousFousBitboard(int depuis[][]) {
		plateauBlanc = 0L;
		plateauNoir = 0L;
		
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				switch(depuis[i][j]){
					case BLANC : 
						plateauBlanc |= 1L<<(8*i+j);
						break;
					case NOIR :
						plateauNoir |= 1L<<(8*i+j);
						break;
				}
			}
		}
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
		CoupFousFousBitboard cNew = (CoupFousFousBitboard) c;
		
		if(j.equals(joueurBlanc)){
			plateauBlanc ^= 1L<<cNew.getAvant();
			plateauBlanc ^= 1L<<cNew.getApres();
			plateauNoir &= ~(1L<<cNew.getApres());
		}else{
			plateauNoir ^= 1L<<cNew.getAvant();
			plateauNoir ^= 1L<<cNew.getApres();
			plateauBlanc &= ~(1L<<cNew.getApres());
		}
	}

	@Override
	public boolean finDePartie() {
		return (plateauBlanc==0 || plateauBlanc==0);
	}

	@Override
	public PlateauJeu copy() {
		return new PlateauFousFousBitboard(this.plateauBlanc, this.plateauNoir);
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
		CoupFousFousBitboard c = new CoupFousFousBitboard(move);
		
		if(player.compareTo("blanc")==0){
			joue(joueurBlanc, c);
		}else{
			joue(joueurNoir, c);
		}
	}

	/************* Méthodes demandée pour la 2eme partie ****************/

	/** Ilustration de l'utilisation de nos méthodes de manière convaincante **/
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

	/********************** Autres méthodes ******************/
	
	public String toString() {
		String represente = "";
		
		for(int i=63;i>=0;i--){
			
			if(((plateauBlanc>>>i) & 1L) != 0){
				represente += "b";
			}else if(((plateauNoir>>>i) & 1L) != 0){
				represente += "n";
			} else{
				represente += " ";
			}
			
			if(i%8==0){
				represente += "\n";
			}
		}
		return represente;
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
