package iia.jeux.alg;

import java.util.ArrayList;

import fousfous.CoupFousFous;
import iia.jeux.modele.CoupJeu;
import iia.jeux.modele.PlateauJeu;
import iia.jeux.modele.joueur.Joueur;

public class AlphaBeta implements AlgoJeu {

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
	private long nbnoeuds;

	/** Le nombre de feuilles évaluées par l'algorithme
	 */
	private long nbfeuilles;
	
	private String etat;


	// -------------------------------------------
	// Constructeurs
	// -------------------------------------------
	public AlphaBeta(Heuristique h, Joueur joueurMax, Joueur joueurMin) {
		this(h, joueurMax, joueurMin, PROFMAXDEFAUT);
	}

	public AlphaBeta(Heuristique h, Joueur joueurMax, Joueur joueurMin, int profMaxi) {
		this.h = h;
		this.joueurMin = joueurMin;
		this.joueurMax = joueurMax;
		profMax = profMaxi;
		// System.out.println("Initialisation d'un MiniMax de profondeur " +
		// profMax);
	}

	// -------------------------------------------
	// Méthodes de l'interface AlgoJeu
	// -------------------------------------------
	public CoupJeu meilleurCoup(PlateauJeu p) {
		nbnoeuds = 0;
		nbfeuilles = 0;


		ArrayList<CoupJeu> lesCoupsPossibles = p.coupsPossibles(joueurMax);
		CoupJeu coupMax = lesCoupsPossibles.get(0);
		int valMax = Integer.MIN_VALUE;

		for (CoupJeu coupTemp : lesCoupsPossibles) {
			nbnoeuds++;

			PlateauJeu pNew = p.copy();
			pNew.joue(joueurMax, coupTemp);
			int valTemp = alphaBeta(pNew, valMax, Integer.MAX_VALUE, 1);
			if (valTemp > valMax) {
				valMax = valTemp;
				coupMax = coupTemp;
				
				if(valTemp == Integer.MAX_VALUE){
					if(coupMax instanceof CoupFousFous){
						((CoupFousFous) coupMax).etat = CoupFousFous.GAGNANT;
					}
					return coupMax;
				}
			}
		}


//		System.out.println(nbfeuilles + " feuilles ont été visitées, ainsi que " + nbnoeuds + " noeuds.");
		
		if(coupMax instanceof CoupFousFous){
			
			if (valMax == Integer.MAX_VALUE) {
				((CoupFousFous) coupMax).etat = CoupFousFous.GAGNANT;
			}else if(valMax == -Integer.MAX_VALUE) {
				((CoupFousFous) coupMax).etat = CoupFousFous.PERDANT;
			}else{
				((CoupFousFous) coupMax).etat = CoupFousFous.RIEN;
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
	
	public String getEtat(){
		return etat;
	}


	// -------------------------------------------
	// Méthodes internes
	// -------------------------------------------

	// Min
	public int alphaBeta(PlateauJeu p, int Alpha, int Beta, int profondeur) {
		if(p.finDePartie()){
			nbfeuilles++;
			return Integer.MAX_VALUE;
		}else if (profondeur == profMax) {
			nbfeuilles++;
			return h.eval(p, joueurMax);
		}

		ArrayList<CoupJeu> coupsJouables = p.coupsPossibles(joueurMin);

		nbnoeuds++;

		for (CoupJeu coupTemp : coupsJouables) {
			PlateauJeu pNew = p.copy();
			pNew.joue(joueurMin, coupTemp);
			Beta = Math.min(Beta, betaAlpha(pNew, Alpha, Beta, profondeur + 1));

			// Coupure Alpha
			if (Alpha >= Beta) { return Alpha; }
		}

		return Beta;
	}

	// Max
	public int betaAlpha(PlateauJeu p, int Alpha, int Beta, int profondeur) {
		if(p.finDePartie()){
			nbfeuilles++;
			return Integer.MIN_VALUE;
		}else if (profondeur == profMax) {
			nbfeuilles++;
			return h.eval(p, joueurMax);
		}

		ArrayList<CoupJeu> coupsJouables = p.coupsPossibles(joueurMax);

		nbnoeuds++;

		for (CoupJeu coupTemp : coupsJouables) {
			PlateauJeu pNew = p.copy();
			pNew.joue(joueurMax, coupTemp);
			Alpha = Math.max(Alpha, alphaBeta(pNew, Alpha, Beta, profondeur + 1));

			// Coupure Beta
			if (Alpha >= Beta) { return Beta; }
		}

		return Alpha;
	}
}
