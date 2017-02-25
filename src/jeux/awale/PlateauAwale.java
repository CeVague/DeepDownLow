package jeux.awale;

import iia.jeux.modele.CoupJeu;
import iia.jeux.modele.PlateauJeu;
import iia.jeux.modele.joueur.Joueur;

import java.io.PrintStream;
import java.util.ArrayList;

public class PlateauAwale implements PlateauJeu {

	/* Pour coder un nouveau jeu... il faut au minimum coder
	 * - Une classe PlateauX pour représenter l'état du "plateau"
	 *  de ce jeu.
	 *  Cette classe doit fournir le code des méthodes de l'interface PlateauJeu
	 *  qui permettent de caractériser les règles du jeu
	 *  Une classe CoupX qui
	 */
	
	private static Joueur joueurBas;
	private int pointsBas;
	
	private static Joueur joueurHaut;
	private int pointsHaut;

	private int[] plateau; //0-5 pour Bas, 6-11 pour Haut
	
	public PlateauAwale() {
		this.plateau = new int[12];
		for(int i=0;i<12;i++){
			this.plateau[i] = 4;
		}
		
		this.pointsBas = 0;
		this.pointsHaut = 0;
	}
	
	public PlateauAwale(int[] depuis, int pb, int ph) {
		this.plateau = new int[12];
		for(int i=0;i<12;i++){
			this.plateau[i] = depuis[i];
		}
		
		this.pointsBas = pb;
		this.pointsHaut = ph;
	}

	/************* Gestion des paramètres de classe** ****************/

	public static void setJoueurs(Joueur jb, Joueur jh) {
		joueurBas = jb;
		joueurHaut = jh;
	}

	public boolean isJoueurBas(Joueur jb) {
		return joueurBas.equals(jb);
	}

	public boolean isJoueurHaut(Joueur jh) {
		return joueurHaut.equals(jh);
	}
	
	public int nbPoints(Joueur j){
		if(j.equals(joueurBas)){
			return pointsBas;
		}else{
			return pointsHaut;
		}
	}
	
	/************* Méthodes de l'interface PlateauJeu ****************/
	/* A Faire */

	public ArrayList<CoupJeu> coupsPossibles(Joueur j) {
		ArrayList<CoupJeu> listeCoups = new ArrayList<CoupJeu>();
		
		// Si on est en fin de partie on ne peut plus jouer
		if(finDePartie()){
			return listeCoups;
		}
		
		// bornes les cases potentiellement jouables celon le joueur
		int debut, fin;
		if(j.equals(joueurBas)){
			debut = 0;
			fin = 5;
		}else{
			debut = 6;
			fin = 11;			
		}
		
		// Regarde chaque case de ce joueur
		// et ajoute les cases jouables à la liste
		for(int i=debut; i<=fin; i++){
			CoupAwale coupTemp = new CoupAwale(i);
			if(coupValide(j, coupTemp)){
				listeCoups.add(new CoupAwale(i));
			}
		}
		
		return listeCoups;
	}

	public void joue(Joueur j, CoupJeu c) {
		// Petit cast
		CoupAwale cNew = (CoupAwale) c;
		
		int i = cNew.getCase();
		
		// On prend toutes les graines de la case jouée
		int nbGraine = plateau[i];
		plateau[i] = 0;
		
		// On egraine tant qu'on en a
		while(nbGraine>0){
			// en sautant la case où on a joué
			if(i != cNew.getCase()){
				plateau[i]++;
				nbGraine--;
			}
			i = (i+1)%12;
		}
		
		// On mange à partir de la case où
		// on a fini d'égrainner
		int nbPris = prendre(j, (i+11)%12);
		
		// On donne les points (selon le nombre
		// de grainnes mangées) au joueur
		if(j.equals(joueurBas)){
			pointsBas += nbPris;
		}else{
			pointsHaut += nbPris;		
		}
	}

	public boolean finDePartie() {
		// On arrete si quelqu'un a plus de 25 points
		if(pointsBas>=25 || pointsHaut>=25){
			return true;
		}else{
			// Ou s'il ne reste que 6 graines en jeux
			int somme = somme(0, 11);
			return somme<=6;
		}
	}

	public PlateauJeu copy() {
		return new PlateauAwale(this.plateau, this.pointsBas, this.pointsHaut);
	}
	
	public boolean coupValide(Joueur j, CoupJeu c) {
		CoupAwale cNew = (CoupAwale) c;
		
		// vérifie si le joueur adverse a des graines
		boolean nourir;
		if(j.equals(joueurBas)){
			nourir = queDesZeros(6, 11);
		}else{
			nourir = queDesZeros(0, 5);
		}
		
		// Si il faut le nourir
		if(nourir){
			// Le coup doit lui donner à manger
			if(this.isJoueurBas(j)){
				return (plateau[cNew.getCase()] + cNew.getCase()) >=6;
			}else{
				return (plateau[cNew.getCase()] + cNew.getCase()) >=12;
			}
		// Sinon il faut juste qu'il y ai des grainnes
		}else{
			return plateau[cNew.getCase()]>0;
		}
	}

	/********************** Autres méthodes ******************/
	
	
	
	public int somme(int debut, int fin){
		int somme = 0;
		for(int i=debut; i<=fin; i++){
			somme += plateau[i];
		}
		return somme;
	}
	
	private boolean queDesZeros(int debut, int fin){
		for(int i=debut; i<=fin; i++){
			if( plateau[i] != 0){
				return false;
			}
		}
		return true;
	}
	
	// Fonction pour manger
	private int prendre(Joueur j, int i){
		int totalPris = 0;
		
		int fin = 0;
		int debut = 5;
		if(i>5){
			fin = 6;
			debut = 11;
		}
		
		if(j.equals(joueurBas) && i<6){
			return 0;
		}else if(j.equals(joueurHaut) && i>=6){
			return 0;
		}
		
		// Détecte si on affame l'adversaire
		// pour éviter de l'affamer
		boolean affame = queDesZeros(i+1, debut);
		int recule = i;
		while(affame && recule>=fin){
			if(plateau[i] != 2 && plateau[i] != 3){
				affame = false;
			}
			recule--;
		}
		if(affame){
			return 0;
		}
		
		// Si on affamera pas, on mange
		while(i>=fin && (plateau[i] == 2 || plateau[i] == 3) ){
			totalPris += plateau[i];
			plateau[i] = 0;
			i--;
		}
		
		return totalPris;
	}
	
	public void print() {
		System.out.println("Points joueur Haut : " + pointsHaut);
		System.out.println("Points joueur Bas : " + pointsBas);
		
		for(int i=11; i>5; i--){
			System.out.print(plateau[i] + "\t");
		}
		
		System.out.print("\n");
		
		for(int i=0; i<6; i++){
			System.out.print(plateau[i] + "\t");
		}
		System.out.print("\n");
	}


}
