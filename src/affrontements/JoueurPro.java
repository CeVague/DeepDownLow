package affrontements;

import fousfous.CoupFousFous;
import fousfous.HeuristiquesFousFous;
import fousfous.PlateauFousFous;
import iia.jeux.alg.AlgoJeu;
import iia.jeux.alg.AlphaBeta;
import iia.jeux.modele.joueur.Joueur;

public class JoueurPro implements IJoueur{

    static final int BLANC = -1;
    static final int NOIR = 1;
    
    static final Joueur jBlanc = new Joueur("blanc");
    static final Joueur jNoir = new Joueur("noir");
    
    static Joueur moiJoueur;
    static int moiInt;

    static Joueur luiJoueur;
    
    static PlateauFousFous plateau;
    
    static AlgoJeu algo;
	
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
		
		algo = new AlphaBeta(HeuristiquesFousFous.htest1, moiJoueur, luiJoueur, 6);
	}

	@Override
	public int getNumJoueur() {
		return moiInt;
	}

	@Override
	public String choixMouvement() {
		CoupFousFous coup = (CoupFousFous) algo.meilleurCoup(plateau);
		plateau.joue(moiJoueur, coup);
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
				System.out.print("(-■_■) #");
				Thread.sleep(200);
				for(char c : "Yeeeeaaaaahhh".toCharArray()){
					Thread.sleep(100);
					System.out.print(c);
				}
				System.out.print("\n");
				Thread.sleep(750);
				System.out.print(".");
				Thread.sleep(750);
				System.out.print(".");
				Thread.sleep(750);
				System.out.print(".");
				Thread.sleep(750);
				for(int i=0;i<5;i++){
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
			}
			catch (InterruptedException e) {
				System.out.println("J'ai perdu ('-_-)");
			}
		}
	}

	@Override
	public void mouvementEnnemi(String coup) {
		plateau.play(coup, luiJoueur.toString());
	}

	@Override
	public String binoName() {
		return "Kazi Vachaudez";
	}

}
