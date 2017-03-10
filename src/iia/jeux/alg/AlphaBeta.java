package iia.jeux.alg;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;

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
	private int nbnoeuds;

	/** Le nombre de feuilles évaluées par l'algorithme
	 */
	private int nbfeuilles;


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
		/* A vous de compléter le corps de ce fichier */

		nbnoeuds = 0;
		nbfeuilles = 0;


		ArrayList<CoupJeu> lesCoupsPossibles = p.coupsPossibles(joueurMax);
		CoupJeu coupMax = lesCoupsPossibles.get(0);
		int valMax = Integer.MIN_VALUE;
		
//		int incr = 0;
//		for (CoupJeu coupTemp : lesCoupsPossibles) {
//			nbnoeuds++;
//
//			PlateauJeu pNew = p.copy();
//			pNew.joue(joueurMax, coupTemp);
//			int valTemp = alphaBeta(pNew, valMax, Integer.MAX_VALUE, 1);
//			if (valTemp > valMax) {
//				valMax = valTemp;
//				coupMax = coupTemp;
//			}
//			incr++;
//			System.out.println( incr + " résultats sur " + lesCoupsPossibles.size());
//		}
		
		
		
		
		
		
		
		// Pool with 3 threads
		ExecutorService pool = Executors.newFixedThreadPool(3);
		CompletionService<Tuple<CoupJeu, Integer>> completion = new ExecutorCompletionService<Tuple<CoupJeu, Integer>>(pool);
		
		Semaphore semaphore = new Semaphore(1);
		IntegerPartage valMaxPartage = new IntegerPartage(Integer.MIN_VALUE);
		
		for (CoupJeu coupTemp : lesCoupsPossibles) {
			completion.submit( new lanceAlphaBeta(profMax, p, h, joueurMin, joueurMax, coupTemp, semaphore, valMaxPartage));
		}

		System.out.println("La file execution est maintenant remplis.");
		
		pool.shutdown();
		
		try {
			for (int i=0;i<lesCoupsPossibles.size();i++) {
				Future<Tuple<CoupJeu, Integer>> valTemp = completion.take();
				if (valTemp.get().y > valMax) {
					valMax = valTemp.get().y;
					coupMax = valTemp.get().x;
				}
				System.out.println( (i+1) + " résultats sur " + lesCoupsPossibles.size());
			}
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		
		
		
		
		
		

		System.out.println(nbfeuilles + " feuilles ont été visitées, ainsi que " + nbnoeuds + " noeuds.");
		
		if(valMax == Integer.MAX_VALUE){
			System.out.println("Je suis gagnant à coup sur");
		}

		return coupMax;
	}

	// -------------------------------------------
	// Méthodes publiques
	// -------------------------------------------
	public String toString() {
		return "AlphaBeta(ProfMax=" + profMax + ")";
	}


	// -------------------------------------------
	// Méthodes internes
	// -------------------------------------------

	// A vous de jouer pour implanter AlphaBeta

	// Min
	public int alphaBeta(PlateauJeu p, int Alpha, int Beta, int profondeur) {
		if (profondeur == profMax) {
			nbfeuilles++;
			return h.eval(p, joueurMax);
		}

		ArrayList<CoupJeu> coupsJouables = p.coupsPossibles(joueurMin);
		
		if(coupsJouables.size() == 0){
			return Integer.MAX_VALUE;
		} else {
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
	}

	// Max
	public int betaAlpha(PlateauJeu p, int Alpha, int Beta, int profondeur) {
		if (profondeur == profMax) {
			nbfeuilles++;
			return h.eval(p, joueurMin);
		}
		
		ArrayList<CoupJeu> coupsJouables = p.coupsPossibles(joueurMax);
		
		if(coupsJouables.size() == 0){
			return Integer.MIN_VALUE;
		} else {
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
}

class Tuple<X, Y> { 
	  public final X x; 
	  public final Y y; 
	  public Tuple(X x, Y y) { 
	    this.x = x; 
	    this.y = y; 
	  } 
	} 

class IntegerPartage {
	private int x;
	
	public IntegerPartage(int x){
		this.x = x;
	}
	
	public void set(int x){
		this.x = x;
	}
	
	public int get(){
		return  x;
	}
	
	public String toString(){
		return Integer.toString(x);
	}
}

class lanceAlphaBeta implements Callable<Tuple<CoupJeu, Integer>> {
	private int profMax;
	private Heuristique h;
	private Joueur joueurMin;
	private Joueur joueurMax;
	
	private CoupJeu coup;
	private Integer bestScore;
	private PlateauJeu p;

	private Semaphore semaphore;
	private IntegerPartage valMaxPartage;


	@Override
	public Tuple<CoupJeu, Integer> call() throws Exception {
		PlateauJeu pNew = p.copy();
		pNew.joue(joueurMax, coup);
		bestScore = alphaBeta(pNew, valMaxPartage.get(), Integer.MAX_VALUE, 1);
		
		
		semaphore.acquire();
		valMaxPartage.set(Math.max(valMaxPartage.get(), bestScore));
		semaphore.release();
		
		
		return new Tuple<CoupJeu, Integer>(coup, bestScore);
	}
	
	
	public lanceAlphaBeta(int profMax, PlateauJeu p, Heuristique h, Joueur joueurMin, Joueur joueurMax, CoupJeu coup, Semaphore semaphore, IntegerPartage valMaxPartage){
		this.profMax = profMax;
		this.h = h;
		this.joueurMin = joueurMin;
		this.joueurMax = joueurMax;
		this.coup = coup;
		this.bestScore = Integer.MIN_VALUE;
		this.p = p;
		
		this.semaphore = semaphore;
		this.valMaxPartage = valMaxPartage;
	}
    
	// Min
	public int alphaBeta(PlateauJeu p, int Alpha, int Beta, int profondeur) {
		if (profondeur == profMax) {
			return h.eval(p, joueurMax);
		}

		ArrayList<CoupJeu> coupsJouables = p.coupsPossibles(joueurMin);
		
		if(coupsJouables.size() == 0){
			return Integer.MAX_VALUE;
		} else {

			for (CoupJeu coupTemp : coupsJouables) {
				PlateauJeu pNew = p.copy();
				pNew.joue(joueurMin, coupTemp);
				Beta = Math.min(Beta, betaAlpha(pNew, Alpha, Beta, profondeur + 1));

				// Coupure Alpha
				if (Alpha >= Beta) { return Alpha; }
			}

			return Beta;
		}
	}

	// Max
	public int betaAlpha(PlateauJeu p, int Alpha, int Beta, int profondeur) {
		if (profondeur == profMax) {
			return h.eval(p, joueurMin);
		}
		
		ArrayList<CoupJeu> coupsJouables = p.coupsPossibles(joueurMax);
		
		if(coupsJouables.size() == 0){
			return Integer.MIN_VALUE;
		} else {

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
}
