package iia.jeux.alg.fousfous;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import fousfous.CouplePlateauScore;
import fousfous.PlateauFousFous;
import iia.jeux.alg.AlgoJeu;
import iia.jeux.alg.Heuristique;
import iia.jeux.modele.CoupJeu;
import iia.jeux.modele.PlateauJeu;
import iia.jeux.modele.joueur.Joueur;

public class ID implements AlgoJeu {

	/** La profondeur de recherche par défaut
	 */
	private final static int PROFMAXDEFAUT = 8;


	// -------------------------------------------
	// Attributs
	// -------------------------------------------

	/**  La profondeur de recherche utilisée pour l'algorithme
	 */
	private int profMax = PROFMAXDEFAUT;
	
	/**  Le temps maximum que doit durer la recherche (en millisecondes)
	 */
	private long tempMax = 10;

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
	public ID(Heuristique h, Joueur joueurMax, Joueur joueurMin) {
		this(h, joueurMax, joueurMin, PROFMAXDEFAUT);
	}

	public ID(Heuristique h, Joueur joueurMax, Joueur joueurMin, int tempMax) {
		this.h = h;
		this.joueurMin = joueurMin;
		this.joueurMax = joueurMax;
		this.tempMax = tempMax;
	}

	// -------------------------------------------
	// Méthodes de l'interface AlgoJeu
	// -------------------------------------------
	public CoupJeu meilleurCoup(PlateauJeu p) {
		nbnoeuds = 0;
		nbfeuilles = 0;
		
		CoupJeu[] coupTrouve = new CoupJeu[]{null};
		boolean[] fichue = new boolean[]{false};

        
		
		Thread lance = new CalculID(h, joueurMin, joueurMax, (PlateauFousFous) p, coupTrouve, fichue);
		
		lance.start();
		try {
			lance.join(tempMax);
		}
		catch (InterruptedException e) {
		}
		lance.interrupt();
		

		return coupTrouve[0];
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
	
	public void setTempsMax(long tempMax){
		this.tempMax = tempMax;
	}

	// -------------------------------------------
	// Méthodes internes
	// -------------------------------------------


}


class CalculID extends Thread{
	private Heuristique h;
	private Joueur joueurMin;
	private Joueur joueurMax;
	
	private PlateauFousFous p;
	private CoupJeu[] coupTrouve;
	private boolean[] fichue;
	
	public CalculID(Heuristique h, Joueur joueurMin, Joueur joueurMax, PlateauFousFous p, CoupJeu[] coupTrouve, boolean[] fichue){
		this.h = h;
		this.joueurMin = joueurMin;
		this.joueurMax = joueurMax;
		
		this.p = p;
		this.coupTrouve = coupTrouve;
		this.fichue = fichue;
	}
	
	public void run() {
		lanceMeilleurCoup(p, coupTrouve, fichue);
	}
	
	public Long plateauToCompact(PlateauFousFous p) {
		long plateauBlanc = p.getPlateauBlanc();
		
		plateauBlanc |= Long.reverseBytes(p.getPlateauNoir());

		return plateauBlanc;
	}
	
	public void lanceMeilleurCoup(PlateauFousFous p, CoupJeu[] coupTrouve, boolean[] fichue){
		boolean trouve = false;

		ArrayList<CoupJeu> coupsJouables = p.coupsPossibles(joueurMax);
		
		coupTrouve[0] = coupsJouables.get(0);
		
		HashMap<Long, CoupJeu> lierPlateauCoup = new HashMap<Long, CoupJeu>(coupsJouables.size());
		CouplePlateauScore[] aTraiter = new CouplePlateauScore[coupsJouables.size()];
		
		// Remplissage des listes
		for(int i=0;i<coupsJouables.size();i++){
			PlateauFousFous pNew = (PlateauFousFous) p.copy();
			pNew.joue(joueurMax, coupsJouables.get(i));
			
			long pNewCompact = plateauToCompact(pNew);
			
			lierPlateauCoup.put(pNewCompact, coupsJouables.get(i));
			aTraiter[i] = new CouplePlateauScore(pNewCompact, h.eval(pNew, joueurMax));
		}
		
		int profondeur = 3;
		while(!trouve && profondeur<8  && !Thread.currentThread().isInterrupted()){
			int valMax = -Integer.MAX_VALUE;
			
			Arrays.sort(aTraiter);

			for(int i=0;i<aTraiter.length;i++){

				int score = negAlphaBetaMemo(aTraiter[i].getPlateau(), valMax, Integer.MAX_VALUE, profondeur, 1);
				
				aTraiter[i].Score = score;
				
				if (score > valMax) {
					valMax = score;
					coupTrouve[0] = lierPlateauCoup.get(aTraiter[i].Plateau);
					
					if(valMax == Integer.MAX_VALUE){
						trouve = true;
						break;
					}
				}
				
				
				if(Thread.currentThread().isInterrupted()){
					return ;
				}
			}
			
			if(valMax == -Integer.MAX_VALUE){
				trouve = true;
				fichue[0] = true;
				break;
			}
			
			profondeur++;
		}
	}

	public int negAlphaBetaMemo(PlateauFousFous p, int Alpha, int Beta, int profondeur, int parite) {
		
		// Ajout de feuilles au deja vue
		if(p.finDePartie()){
			return Integer.MAX_VALUE;
		}else if (profondeur == 0) {
			return parite*h.eval(p, joueurMax);
		}

		// Selection du joueur actuel
		Joueur joueurActuel;
		if(parite==1){
			joueurActuel = joueurMin;
		}else{
			joueurActuel = joueurMax;
		}
		
		ArrayList<CoupJeu> coupsJouables = p.coupsPossibles(joueurActuel);
		CouplePlateauScore[] aTraiter = new CouplePlateauScore[coupsJouables.size()];
		
		// Listage de tous les coups à traiter (avec évaluation si besoins)
		for(int i=0;i<coupsJouables.size();i++){
			PlateauFousFous pNew = (PlateauFousFous) p.copy();
			pNew.joue(joueurActuel, coupsJouables.get(i));
			
			long pNewCompact = plateauToCompact(pNew);
			
			aTraiter[i] = new CouplePlateauScore(pNewCompact, parite*h.eval(pNew, joueurMax));
		}
		
		// Rangement dans l'ordre
		Arrays.sort(aTraiter);
	
		// On applique le alpha beta dans l'ordre
		for (CouplePlateauScore couple : aTraiter) {

			Beta = Math.min(Beta, -negAlphaBetaMemo(couple.getPlateau(), -Beta, -Alpha, profondeur - 1, -parite));
	
			if (Alpha >= Beta) {
				return Alpha;
			}
		}
	
		return Beta;
	}
}
