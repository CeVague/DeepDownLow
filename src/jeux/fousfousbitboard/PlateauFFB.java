package jeux.fousfousbitboard;

import iia.jeux.modele.*;
import iia.jeux.modele.joueur.Joueur;

import java.util.ArrayList;

public class PlateauFFB implements PlateauJeu, Partie1 {


	/***************** Constantes *****************/

	//private final static int VIDE = 0;
	private final static int BLANC = 1;
	private final static int NOIR = 2;

	private final static long masquePlateau = 0b1010101001010101101010100101010110101010010101011010101001010101L;
	private final static long masqueDiagGauche = 0b0000000100000010000001000000100000010000001000000100000010000001L;
	private final static long masqueDiagDroit = 0b1000000001000000001000000001000000001000000001000000001000000001L;
	
	//private final static long un = 0b1000000000000000000000000000000000000000000000000000000000000000L;

	/*********** Paramètres de classe ************/
	
	/** Le joueur que joue "Blanc" **/
	private static Joueur joueurBlanc;

	/** Le joueur que joue "Noir" **/
	private static Joueur joueurNoir;

	/************ Attributs  ************/

	private long plateauBlanc;
	private long plateauNoir;

	/************* Constructeurs ****************/

	public PlateauFFB() {
		plateauBlanc = 0b0101010100000000010101010000000001010101000000000101010100000000L;
		plateauNoir = 0b0000000010101010000000001010101000000000101010100000000010101010L;
	}

	public PlateauFFB(long plateauBlanc, long plateauNoir) {
		this.plateauBlanc = plateauBlanc;
		this.plateauNoir = plateauNoir;
	}

	public PlateauFFB(int depuis[][]) {
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
		CoupFFB cNew = (CoupFFB) c;
		
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
		return new PlateauFFB(this.plateauBlanc, this.plateauNoir);
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
		CoupFFB c = new CoupFFB(move);
		
		if(player.compareTo("blanc")==0){
			return coupValide(joueurBlanc, c);
		}else{
			return coupValide(joueurNoir, c);
		}
	}

	@Override
	public String[] mouvementsPossibles(String player) {
		ArrayList<CoupJeu> temp;
		
		if(player.compareTo("blanc")==0){
			temp = coupsPossibles(joueurBlanc);
		}else{
			temp = coupsPossibles(joueurBlanc);
		}
		
		String[] liste = new String[temp.size()];
		for(int i=0;i<temp.size();i++){
			liste[i] = temp.get(i).toString();
		}
		return liste;
	}

	@Override
	public void play(String move, String player) {
		CoupFFB c = new CoupFFB(move);
		
		if(player.compareTo("blanc")==0){
			joue(joueurBlanc, c);
		}else{
			joue(joueurNoir, c);
		}
	}

	/************* Méthodes demandée pour la 2eme partie ****************/

	/** Ilustration de l'utilisation de nos méthodes de manière convaincante **/
	//public static void main(String[] args) {
		// TODO Auto-generated method stub
	//}

	/********************** Autres méthodes ******************/
	
	public long getPlateauBlanc(){
		return plateauBlanc;
	}
	
	public long getPlateauNoir(){
		return plateauNoir;
	}
	
	public long retourneTableau(Joueur j){
		if(j.equals(joueurBlanc)){
			return plateauBlanc;
		}else{
			return plateauNoir;
		}
	}
	
	public long retourneTableau(String j){
		if(j.compareTo("blanc")==0){
			return plateauBlanc;
		}else{
			return plateauNoir;
		}
	}
	
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
	
	
	public void print() {
		System.out.println("  A B C D E F G H");
		System.out.println(" ┌─┬─┬─┬─┬─┬─┬─┬─┐");
		for(int i=63;i>=0;i--){
			
			if(i%8==7){
				System.out.print((8 - i/8) + "│");
			}
			
			if(((plateauBlanc>>>i) & 1L) != 0){
				System.out.print("b│");
			}else if(((plateauNoir>>>i) & 1L) != 0){
				System.out.print("n│");
			} else{
				System.out.print(" │");
			}
			
			if(i%8==0 && i!=0){
				System.out.print("\n ├─┼─┼─┼─┼─┼─┼─┼─┤\n");
			}
		}
		System.out.print("\n └─┴─┴─┴─┴─┴─┴─┴─┘\n");
	}
	
	// On considère qu'on nous donne bien un coup en diagonal valide
	// Temps d'execution négligeable
	public boolean cheminLibre(Joueur j, CoupFFB c){
		
		// On charge les endroits où on veut aller
		// Avec A>B
		long A = c.getAvant();
		long B = c.getApres();
		if(A<B){
			A = c.getApres();
			B = c.getAvant();
		}
		
		// Choisi le masque correspondant à
		// la diagonale entre les deux points
		long masque = masqueDiagGauche;
		if(A%8>B%8){
			masque = masqueDiagDroit;
		}
		// Place le masque au niveau de B
		masque = masque<<B;

		B++;
		
		A = 1L<<A;
		B = 1L<<B;

		return ((A-B) & masque & (plateauNoir | plateauBlanc))==0;
	}
	
	public boolean peutManger(Joueur j, CoupFFB c){
		if(cheminLibre(j, c)){
			// Emplacement du pion à manger
			long temp = 1L<<c.getApres();
			
			// Verifie si il y a un pion adverse
			// a cet endroit
			if(j.equals(joueurBlanc)){
				return (temp & plateauNoir) != 0;
			}else{
				return (temp & plateauBlanc) != 0;
			}
		}else{
			// Emplacement du pion à manger
			long temp = 1L<<c.getApres();
			
			// Verifie si il y a un pion adverse
			// a cet endroit
			if(j.equals(joueurBlanc)){
				return (temp & plateauNoir) != 0;
			}else{
				return (temp & plateauBlanc) != 0;
			}
		}
	}
	
	// 12000000 appels possibles par secondes
	public ArrayList<PionFFB> listerPions(Joueur j){
		long plateau = retourneTableau(j);
		ArrayList<PionFFB> listeCoups = new ArrayList<PionFFB>(comptePions(j));
		
		long pion = Long.lowestOneBit(plateau);
		while(pion != 0){
			listeCoups.add(new PionFFB(Long.numberOfTrailingZeros(plateau)));
			
			plateau &= ~pion;
			pion = Long.lowestOneBit(plateau);
		}
		return listeCoups;
	}

	public ArrayList<CoupFFB> coupsPossibles(Joueur j, PionFFB p) {
		// TODO Auto-generated method stub
		return null;
	}

	// ceux qui peuvent être mangés
	public ArrayList<CoupFFB> listerMangeable(Joueur j, PionFFB p) {
		// TODO Auto-generated method stub
		return null;
	}

	// ceux qui peuvent manger
	public ArrayList<CoupFFB> listerMangeur(Joueur j) {
		// TODO Auto-generated method stub
		return null;
	}

	// ceux qui peuvent être menacés
	public ArrayList<CoupFFB> listerMenacable(Joueur j, PionFFB p) {
		// TODO Auto-generated method stub
		return null;
	}

	// ceux qui peuvent menacer
	public ArrayList<CoupFFB> listerMenaceur(Joueur j) {
		// TODO Auto-generated method stub
		return null;
	}

	public int comptePions(Joueur j){
		// Tester avec deux var en plus pour compter en continue
		return Long.bitCount(retourneTableau(j));
	}
}
