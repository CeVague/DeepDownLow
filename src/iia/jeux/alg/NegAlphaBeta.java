/**
 * 
 */

package iia.jeux.alg;

import java.util.ArrayList;

import iia.jeux.modele.CoupJeu;
import iia.jeux.modele.PlateauJeu;
import iia.jeux.modele.joueur.Joueur;

public class NegAlphaBeta implements AlgoJeu {

	/** La profondeur de recherche par défaut
	 */
	private final static int PROFMAXDEFAUT = 4;


	// -------------------------------------------
	// Attributs
	// -------------------------------------------

	/**  La profondeur de recherche utilisée pour l'algorithme
	 */
	private int profMax = PROFMAXDEFAUT;

	/**  L'heuristique utilisée par l'algorithme
	 */
	private Heuristique h;

	/** Le joueur Max
	 * (celui dont l'algorithme de recherche adopte le point de vue) */
	private Joueur joueurMax;

	/** Le joueur Min **/
	private Joueur joueurMin;

	/**  Le nombre de noeuds développé par l'algorithme
	 * (intéressant pour se faire une idée du nombre de noeuds développés) */
	private int nbnoeuds;

	/** Le nombre de feuilles évaluées par l'algorithme
	 */
	private int nbfeuilles;


	// -------------------------------------------
	// Constructeurs
	// -------------------------------------------
	public NegAlphaBeta(Heuristique h, Joueur joueurMax, Joueur joueurMin) {
		this(h, joueurMax, joueurMin, PROFMAXDEFAUT);
	}

	public NegAlphaBeta(Heuristique h, Joueur joueurMax, Joueur joueurMin, int profMaxi) {
		this.h = h;
		this.joueurMax = joueurMax;
		this.joueurMin = joueurMin;
		profMax = profMaxi;
		// System.out.println("Initialisation d'un MiniMax de profondeur " +
		// profMax);
	}

	// -------------------------------------------
	// Méthodes de l'interface AlgoJeu
	// -------------------------------------------
	public CoupJeu meilleurCoup(PlateauJeu p) {
		// Pour les stats
		nbnoeuds = 0;
		nbfeuilles = 0;


		ArrayList<CoupJeu> lesCoupsPossibles = p.coupsPossibles(joueurMax);
		CoupJeu coupMax = lesCoupsPossibles.get(0);
		int valMax = Integer.MIN_VALUE + 1; // (+1 pour éviter les débordements lors de changement de signe)

		for (CoupJeu coupTemp : lesCoupsPossibles) {
			nbnoeuds++;

			PlateauJeu pNew = p.copy();
			pNew.joue(joueurMax, coupTemp);
			int valTemp = negAlphaBeta(pNew, Integer.MIN_VALUE + 1, Integer.MAX_VALUE, 1, 1);
			if (valTemp > valMax) {
				valMax = valTemp;
				coupMax = coupTemp;
			}
		}

		System.out.println(nbfeuilles + " feuilles ont été visitées, ainsi que " + nbnoeuds + " noeuds.");

		return coupMax;
	}

	// -------------------------------------------
	// Méthodes publiques
	// -------------------------------------------
	public String toString() {
		return "negAlphaBeta(ProfMax=" + profMax + ")";
	}


	// -------------------------------------------
	// Méthodes internes
	// -------------------------------------------

	// A vous de jouer pour implanter NegAlphaBeta

	// Max
	public int negAlphaBeta(PlateauJeu p, int Alpha, int Beta, int profondeur, int parite) {
		Joueur joueurActuel;
		
		if(parite == 1){
			joueurActuel = joueurMax;
		}else{
			joueurActuel = joueurMin;
		}
		
		if (profondeur == profMax) {
			nbfeuilles++;
			return parite * h.eval(p, joueurActuel);
		}
		
		ArrayList<CoupJeu> coupsJouables = p.coupsPossibles(joueurActuel);
		
		if(coupsJouables.size() == 0){
			return parite * Integer.MAX_VALUE;
		} else {
			nbnoeuds++;

			for (CoupJeu coupTemp : coupsJouables) {
				PlateauJeu pNew = p.copy();
				pNew.joue(joueurActuel, coupTemp);
				Alpha = Math.max(Alpha, -negAlphaBeta(pNew, -Beta, -Alpha, profondeur + 1, -parite));

				// Coupure Beta
				if (Alpha >= Beta) { return Beta; }
			}

			return Alpha;
		}
	}


}
