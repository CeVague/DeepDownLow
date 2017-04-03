package affrontements;

import fousfous.*;
import iia.jeux.alg.*;
import iia.jeux.modele.joueur.Joueur;

public class JoueurPro implements IJoueur{

    static final int BLANC = -1;
    static final int NOIR = 1;
    
    static final Joueur jBlanc = new Joueur("blanc");
    static final Joueur jNoir = new Joueur("noir");
    
    Joueur moiJoueur;
    int moiInt;

    Joueur luiJoueur;
    
    PlateauFousFous plateau;
    
    AlgoJeu algo;
    
    int coupJoues;
	
	@Override
	public void initJoueur(int mycolour) {
		moiInt = mycolour;
		
		if(mycolour == BLANC){
			moiJoueur = jBlanc;
			luiJoueur = jNoir;
		}else{
			moiJoueur = jNoir;
			luiJoueur = jBlanc;
		}
		
		plateau = new PlateauFousFous();
		PlateauFousFous.setJoueurs(jBlanc, jNoir);
		
		coupJoues = 0;
		
		algo = new NegAlphaBeta(HeuristiquesFousFous.hlent, moiJoueur, luiJoueur, 8);
	}

	@Override
	public int getNumJoueur() {
		return moiInt;
	}

	@Override
	public String choixMouvement() {
		if(coupJoues==8 || coupJoues==9){
			algo = new NegAlphaBeta(HeuristiquesFousFous.hlent, moiJoueur, luiJoueur, 10);
			System.out.println("Changement d'IA");
		}

		if(coupJoues==15 || coupJoues==16){
			algo = new NegAlphaBeta(HeuristiquesFousFous.hlent, moiJoueur, luiJoueur, 17);
			System.out.println("Changement d'IA");
		}
		
		
		CoupFousFous coup = (CoupFousFous) algo.meilleurCoup(plateau);
		plateau.joue(moiJoueur, coup);
		coupJoues++;
		return coup.toString();
	}

	public static void afficheBas(String s, int sleep) throws InterruptedException{
		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
		System.out.println(s);
		System.out.flush();  
		Thread.sleep(sleep);
	}
	
	@Override
	public void declareLeVainqueur(int colour) {
		if(colour == moiInt){
			try {
				afficheBas("Gagné...\n", 1000);
				afficheBas("Gagné...\n( °_°)", 1500);
				afficheBas("Gagné...\n( °_°)>-■-■", 1000);
				afficheBas("Gagné...", 0);
				System.out.print("(-■_■)");
				Thread.sleep(1000);
				for(char c : " #Yeeeeaaaaahhh".toCharArray()){
					Thread.sleep(100);
					System.out.print(c);
				}
				Thread.sleep(750);
				System.out.print("\n");
				Thread.sleep(750);
				System.out.print(".");
				Thread.sleep(750);
				System.out.print(".");
				Thread.sleep(750);
				System.out.print(".");
				Thread.sleep(750);
				for(int i=0;i<4;i++){
					afficheBas("Gagné...\n/(-■_■)/", 250);
					afficheBas("Gagné...\n-(-■_■)/", 250);
					afficheBas("Gagné...\n-(-■_■)-", 250);
					afficheBas("Gagné...\n\\(-■_■)-", 250);
					afficheBas("Gagné...\n\\(-■_■)\\", 250);
					afficheBas("Gagné...\n\\(-■_■)-", 250);
					afficheBas("Gagné...\n-(-■_■)-", 250);
					afficheBas("Gagné...\n-(-■_■)/", 250);
					afficheBas("Gagné...\n/(-■_■)/", 250);
					afficheBas("Gagné...\n\\(-■_■)\\", 250);
					afficheBas("Gagné...\n/(-■_■)/", 250);
					afficheBas("Gagné...\n\\(-■_■)\\", 250);
				}
				afficheBas("Easy peasy lemon squeezy\n\\(-■_■)/", 250);
			}
			catch (InterruptedException e) {
				System.out.println("\\(-■_■)/");
			}
		}else{
			try {
				afficheBas("Perdu...\n", 1000);
				afficheBas("Perdu...\n(°_°) ┬─┬", 1000);
				afficheBas("Perdu...\n(/°o°)/︵┴─┴", 1000);
				afficheBas("Perdu...\n(;_;)    ┴─┴", 1000);
				afficheBas("Perdu...\n(°_°)    ┴─┴", 1000);
				afficheBas("Perdu...\n(;_;)    ┴─┴", 1000);

				afficheBas("Perdu...\n( ;_;)", 1000); 
				for(int i=0;i<40;i++){
					String s = "";
					for(int j=0;j<i;j++){
						s += "    ";
					}
					s += "( ;_;)";
					afficheBas("Perdu...\n" + s, 100); 
				}
				afficheBas("J'ai échoué\n('-_-)", 1000); 
			}
			catch (InterruptedException e) {
				System.out.println("J'ai échoué ('-_-)");
			}
		}
	}

	@Override
	public void mouvementEnnemi(String coup) {
		plateau.play(coup, luiJoueur.toString());
		coupJoues++;
	}

	@Override
	public String binoName() {
		return "Kazi & Vachaudez";
	}
	
	public Joueur getJoueurBlanc(){
		return jBlanc;
	}
	
	public Joueur getJoueurNoir(){
		return jNoir;
	}
	
	public PlateauFousFous getPlateau(){
		return plateau;
	}
	
	public void testAnimationGagne(){
		moiInt = -1;
		declareLeVainqueur(-1);
	}
	
	public void testAnimationPerd(){
		moiInt = -1;
		declareLeVainqueur(1);
	}
	
	public static void main(String[] args) throws InterruptedException{
		new JoueurPro().testAnimationGagne();
		Thread.sleep(1000);
		new JoueurPro().testAnimationPerd();
	}

}
