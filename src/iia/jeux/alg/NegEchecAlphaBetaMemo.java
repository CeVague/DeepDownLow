package iia.jeux.alg;

import java.util.ArrayList;
import java.util.HashMap;

import fousfous.CoupFousFous;
import fousfous.PlateauFousFous;
import fousfous.PlateauMemoizeSimpleFousFous;
import iia.jeux.modele.CoupJeu;
import iia.jeux.modele.PlateauJeu;
import iia.jeux.modele.joueur.Joueur;

public class NegEchecAlphaBetaMemo implements AlgoJeuMemo {

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

	private HashMap<Long, PlateauMemoizeSimpleFousFous> memoriseTT;
	
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
	public NegEchecAlphaBetaMemo(Heuristique h, Joueur joueurMax, Joueur joueurMin) {
		this(h, joueurMax, joueurMin, PROFMAXDEFAUT);
	}

	public NegEchecAlphaBetaMemo(Heuristique h, Joueur joueurMax, Joueur joueurMin, int profMaxi) {
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

		memoriseTT = new HashMap<Long, PlateauMemoizeSimpleFousFous>();

		ArrayList<CoupJeu> lesCoupsPossibles = p.coupsPossibles(joueurMax);
		CoupJeu coupMax = lesCoupsPossibles.get(0);
		int valMax = -Integer.MAX_VALUE;
		

		if(dernierCoup != null){
			PlateauJeu pNew = p.copy();
			pNew.joue(joueurMax, dernierCoup);
			
			valMax = negEchecAlphaBetaMem(pNew, valMax, Integer.MAX_VALUE, profMax-1, 1);
			
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
			int valTemp = negEchecAlphaBetaMem(pNew, valMax, Integer.MAX_VALUE, profMax-1, 1);
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

	public Long plateauToCompact(PlateauFousFous p) {
		long plateauBlanc = p.getPlateauBlanc();
		
		plateauBlanc |= Long.reverseBytes(p.getPlateauNoir());

		return plateauBlanc;
	}
	
	public int negEchecAlphaBetaMem(PlateauJeu p, int Alpha, int Beta, int profondeur, int parite) {
		int AlphaInit = Alpha;
		
		PlateauMemoizeSimpleFousFous EntreT = memoriseTT.get(plateauToCompact((PlateauFousFous) p));
		
		
		if(EntreT != null && EntreT.Prof>=profondeur){
			if(EntreT.Flag == PlateauMemoizeSimpleFousFous.EXACT){
				return EntreT.Val;
			}else if(EntreT.Flag == PlateauMemoizeSimpleFousFous.BINF){
				Alpha = Math.max(Alpha, EntreT.Val);
			}else if(EntreT.Flag == PlateauMemoizeSimpleFousFous.BSUP){
				Beta = Math.min(Beta, EntreT.Val);
			}
			
			if(Alpha>=Beta){
				return EntreT.Val;
			}
		}
		
		
		
		if(p.finDePartie()){
			nbfeuilles++;
			return Integer.MAX_VALUE;
		}else if (profondeur == 0) {
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

		CoupJeu MeilleurCoup = coupsJouables.get(0);
		if(EntreT != null){
			MeilleurCoup = EntreT.MeilleurCoup;
		}
		
		for (CoupJeu coupTemp : coupsJouables) {
			PlateauJeu pNew = p.copy();
			pNew.joue(joueurActuel, coupTemp);
			
			Min = Math.min(Min, -negEchecAlphaBetaMem(pNew, -Beta, -Alpha, profondeur - 1, -parite));

			if(Min<Beta){
				Beta = Min;
				MeilleurCoup = coupTemp;
			}
	
			if (Alpha >= Beta) {
				break;
			}
		}
	
		
		if(EntreT == null){
			EntreT = new PlateauMemoizeSimpleFousFous();
			memoriseTT.put(plateauToCompact((PlateauFousFous) p), EntreT);
		}
		
		
		EntreT.Val = Min;
		EntreT.MeilleurCoup = MeilleurCoup;
		if(Min<=AlphaInit){
			EntreT.Flag = PlateauMemoizeSimpleFousFous.BSUP;
		}else if(Min>=Beta){
			EntreT.Flag = PlateauMemoizeSimpleFousFous.BINF;
		}else{
			EntreT.Flag = PlateauMemoizeSimpleFousFous.EXACT;
		}
		EntreT.Prof = profondeur;
		
		
		return Min;
	}
}
