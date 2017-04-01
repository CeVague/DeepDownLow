package fousfous;

import iia.jeux.modele.CoupJeu;

/**
 * Classe utilisée pour avoir une mémoïzation efficasse :
 * Le plateau est enregistré de manière compacte et les transformations précalculées
 * pour permettre de vérifier les égalitées en 4 calculs.
 * 
 * Dans PlateauMemoizeFousFous, equal et hashCode travaillent sur les plateaux,
 * compareTo classe selon les scores
 */
public class PlateauMemoizeSimpleFousFous implements Comparable<PlateauMemoizeSimpleFousFous> {

	/***************** Constantes *****************/

	public static final int EXACT = 0;
    public static final int BINF = -1;
    public static final int BSUP = 1;

	/************ Attributs  ************/

	public int Prof;
	public int Val;
	public int Flag;
	public CoupJeu MeilleurCoup;

	/************* Constructeurs ****************/

	public PlateauMemoizeSimpleFousFous() {
	}

	public PlateauMemoizeSimpleFousFous(int Prof, int Val, int Flag, CoupJeu MeilleurCoup) {
		this.Prof = Prof;
		this.Val = Val;
		this.Flag = Flag;
		this.MeilleurCoup = MeilleurCoup;
	}

	/********************** Ordonancement ******************/

	@Override
	public int compareTo(PlateauMemoizeSimpleFousFous o) {
		return Val - o.Val;
	}


	/********************** Autre ******************/

	public String toString() {
		return Prof + ";" + Val + ";" + Flag + ";" + MeilleurCoup;
	}
}
