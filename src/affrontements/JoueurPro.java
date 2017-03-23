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
		
		algo = new AlphaBeta(HeuristiquesFousFous.htest1, moiJoueur, luiJoueur, 7);
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

	@Override
	public void declareLeVainqueur(int colour) {
		if(colour == moiInt){
			System.out.println("Gagné :P");
		}else{
			System.out.println("Perdu :'(");
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
