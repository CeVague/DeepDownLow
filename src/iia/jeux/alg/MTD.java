package iia.jeux.alg;

import java.util.ArrayList;

import iia.jeux.modele.CoupJeu;
import iia.jeux.modele.PlateauJeu;
import iia.jeux.modele.joueur.Joueur;

public class MTD implements AlgoJeu {

	/** La profondeur de recherche par défaut
	 */
	private final static int PROFMAXDEFAUT = 8;


	// -------------------------------------------
	// Attributs
	// -------------------------------------------

	/**  La profondeur de recherche utilisée pour l'algorithme
	 */
	private int profMax = PROFMAXDEFAUT;

	/**  L'heuristique utilisée par l'algorithme
	 */
	private Heuristique h;

	/** Le joueur Min
	 *  (l'adversaire) */
	private Joueur joueurMin;

	/** Le joueur Max
	 * (celui dont l'algorithme de recherche adopte le point de vue) */
	private Joueur joueurMax;

	/**  Le nombre de noeuds développé par l'algorithme
	 * (intéressant pour se faire une idée du nombre de noeuds développés) */
	private int nbnoeuds;

	/** Le nombre de feuilles évaluées par l'algorithme
	 */
	private int nbfeuilles;


	// -------------------------------------------
	// Constructeurs
	// -------------------------------------------
	public MTD(Heuristique h, Joueur joueurMax, Joueur joueurMin) {
		this(h, joueurMax, joueurMin, PROFMAXDEFAUT);
	}

	public MTD(Heuristique h, Joueur joueurMax, Joueur joueurMin, int profMaxi) {
		this.h = h;
		this.joueurMin = joueurMin;
		this.joueurMax = joueurMax;
		profMax = profMaxi;
	}

	// -------------------------------------------
	// Méthodes de l'interface AlgoJeu
	// -------------------------------------------
	public CoupJeu meilleurCoup(PlateauJeu p) {
		nbnoeuds = 0;
		nbfeuilles = 0;


		ArrayList<CoupJeu> lesCoupsPossibles = p.coupsPossibles(joueurMax);
		CoupJeu coupMax = lesCoupsPossibles.get(0);
		int valMax = -Integer.MAX_VALUE;
		double valMoyenne = 20;

		for (CoupJeu coupTemp : lesCoupsPossibles) {
			nbnoeuds++;

			PlateauJeu pNew = p.copy();
			pNew.joue(joueurMax, coupTemp);
			int valTemp = lanceMTD(pNew, (int) valMoyenne);
			valMoyenne = valMoyenne*0.3 + ((float) valTemp)*0.7;
			if (valTemp > valMax) {
				valMax = valTemp;
				coupMax = coupTemp;
				
				if(valTemp == Integer.MAX_VALUE){
					return coupMax;
				}
			}
		}

		return coupMax;
	}

	// -------------------------------------------
	// Méthodes publiques
	// -------------------------------------------
	public String toString() {
		return "AlphaBeta(ProfMax=" + profMax + ")";
	}
	
	public long getFeuilles(){
		return nbfeuilles;
	}
	
	public long getNoeuds(){
		return nbnoeuds;
	}


	// -------------------------------------------
	// Méthodes internes
	// -------------------------------------------

	public int lanceMTD(PlateauJeu p, int f){
		int g = f;
		int Beta = f;
		int Min = -Integer.MAX_VALUE;
		int Max = Integer.MAX_VALUE;
		
		while(Min<Max){
			if(g==Min){
				Beta = g+1;
			}else{
				Beta = g;
			}
			g = negEchecAlphaBeta(p, Beta-1, Beta, 1, 1);
			if(g<Beta){
				Max = g;
			}else{
				Min = g;
			}
		}
		return g;
	}
	
	
	public int negEchecAlphaBeta(PlateauJeu p, int Alpha, int Beta, int profondeur, int parite) {
		if(p.finDePartie()){
			nbfeuilles++;
			return Integer.MAX_VALUE;
		}else if (profondeur == profMax) {
			nbfeuilles++;
			return parite*h.eval(p, joueurMax);
		}

		Joueur joueurActuel;
		if(parite==1){
			joueurActuel = joueurMin;
		}else{
			joueurActuel = joueurMax;
		}
		
		ArrayList<CoupJeu> coupsJouables = p.coupsPossibles(joueurActuel);
		
		nbnoeuds++;
	
		int Min = Integer.MAX_VALUE;
		for (CoupJeu coupTemp : coupsJouables) {
			PlateauJeu pNew = p.copy();
			pNew.joue(joueurActuel, coupTemp);
			
			Min = Math.min(Min, -negEchecAlphaBeta(pNew, -Beta, -Alpha, profondeur + 1, -parite));
			Beta = Math.min(Beta, Min);
	
			if (Alpha >= Beta) { return Min; }
		}
	
		return Min;
	}
}
