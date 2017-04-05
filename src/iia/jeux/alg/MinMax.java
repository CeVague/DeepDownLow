package iia.jeux.alg;

import java.util.ArrayList;

import fousfous.CoupFousFous;
import iia.jeux.modele.CoupJeu;
import iia.jeux.modele.PlateauJeu;
import iia.jeux.modele.joueur.Joueur;

public class MinMax implements AlgoJeu {

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
	private long nbfeuilles;


	// -------------------------------------------
	// Constructeurs
	// -------------------------------------------
	public MinMax(Heuristique h, Joueur joueurMax, Joueur joueurMin) {
		this(h, joueurMax, joueurMin, PROFMAXDEFAUT);
	}

	public MinMax(Heuristique h, Joueur joueurMax, Joueur joueurMin, int profMaxi) {
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
		/* A vous de compléter le corps de ce fichier */

		nbnoeuds = 0;
		nbfeuilles = 0;


		ArrayList<CoupJeu> lesCoupsPossibles = p.coupsPossibles(joueurMax);
		CoupJeu coupMax = lesCoupsPossibles.get(0);
		int valMax = Integer.MIN_VALUE;

		for (CoupJeu coupTemp : lesCoupsPossibles) {
			nbnoeuds++;

			PlateauJeu pNew = p.copy();
			pNew.joue(joueurMax, coupTemp);
			int valTemp = minMax(pNew, 1);
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
		return "MiniMax(ProfMax=" + profMax + ")";
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

	// A vous de jouer pour implanter Minimax
	public int minMax(PlateauJeu p, int profondeur) {
		if (profondeur == profMax) {
			nbfeuilles++;
			return h.eval(p, joueurMax);
		} else {
			nbnoeuds++;

			int valMin = Integer.MAX_VALUE;

			for (CoupJeu coupTemp : p.coupsPossibles(joueurMin)) {
				PlateauJeu pNew = p.copy();
				pNew.joue(joueurMin, coupTemp);
				valMin = Math.min(valMin, maxMin(pNew, profondeur + 1));
			}

			return valMin;
		}
	}

	public int maxMin(PlateauJeu p, int profondeur) {
		if (profondeur == profMax) {
			nbfeuilles++;
			return h.eval(p, joueurMax);
		} else {
			nbnoeuds++;

			int valMax = Integer.MIN_VALUE;

			for (CoupJeu coupTemp : p.coupsPossibles(joueurMax)) {
				PlateauJeu pNew = p.copy();
				pNew.joue(joueurMax, coupTemp);
				valMax = Math.max(valMax, minMax(pNew, profondeur + 1));
			}

			return valMax;
		}
	}


}
