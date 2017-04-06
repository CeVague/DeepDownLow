package iia.jeux.alg;

import java.util.ArrayList;

import fousfous.CoupFousFous;
import iia.jeux.modele.CoupJeu;
import iia.jeux.modele.PlateauJeu;
import iia.jeux.modele.joueur.Joueur;

public class NegAlphaBeta implements AlgoJeuMemo {

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
	
	private CoupJeu dernierCoup = null;


	// -------------------------------------------
	// Constructeurs
	// -------------------------------------------
	public NegAlphaBeta(Heuristique h, Joueur joueurMax, Joueur joueurMin) {
		this(h, joueurMax, joueurMin, PROFMAXDEFAUT);
	}

	public NegAlphaBeta(Heuristique h, Joueur joueurMax, Joueur joueurMin, int profMaxi) {
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
		

		if(dernierCoup != null){
			PlateauJeu pNew = p.copy();
			pNew.joue(joueurMax, dernierCoup);
			
			valMax = negAlphaBeta(pNew, valMax, Integer.MAX_VALUE, 1, 1);
			
			if(coupMax instanceof CoupFousFous){
				coupMax = (CoupFousFous) dernierCoup;
			}else{
				coupMax = dernierCoup;
			}

			lesCoupsPossibles.remove(dernierCoup);
		}

		for (CoupJeu coupTemp : lesCoupsPossibles) {
			nbnoeuds++;
				
			PlateauJeu pNew = p.copy();
			pNew.joue(joueurMax, coupTemp);
			int valTemp = negAlphaBeta(pNew, valMax, Integer.MAX_VALUE, 1, 1);
			if (valTemp > valMax) {
				valMax = valTemp;
				coupMax = coupTemp;
				
				if(valTemp == Integer.MAX_VALUE){
					if(coupMax instanceof CoupFousFous){
						((CoupFousFous) coupMax).etat = CoupFousFous.GAGNANT;
					}
					dernierCoup = coupMax;
					return coupMax;
				}
			}
		}


//		System.out.println(nbfeuilles + " feuilles ont été visitées, ainsi que " + nbnoeuds + " noeuds.");

		if(coupMax instanceof CoupFousFous){
			
			if (valMax == Integer.MAX_VALUE) {
				((CoupFousFous) coupMax).etat = CoupFousFous.GAGNANT;
			}else if(valMax == -Integer.MAX_VALUE) {
				// On tentera de jouer un coup valide si on risque de perdre
				if(dernierCoup != null){
					coupMax = (CoupFousFous) dernierCoup;
				}
				((CoupFousFous) coupMax).etat = CoupFousFous.PERDANT;
			}else{
				((CoupFousFous) coupMax).etat = CoupFousFous.RIEN;
			}
			
		}
		
		dernierCoup = coupMax;
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
	
	public void setProfMax(int prof){
		profMax = prof;
	}


	// -------------------------------------------
	// Méthodes internes
	// -------------------------------------------

	public int negAlphaBeta(PlateauJeu p, int Alpha, int Beta, int profondeur, int parite) {
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
	
		for (CoupJeu coupTemp : coupsJouables) {
			
			PlateauJeu pNew = p.copy();
			pNew.joue(joueurActuel, coupTemp);
			
			Beta = Math.min(Beta, -negAlphaBeta(pNew, -Beta, -Alpha, profondeur + 1, -parite));
	
			if (Alpha >= Beta) { return Alpha; }
		}
	
		return Beta;
	}
}
