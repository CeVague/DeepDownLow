package fousfous;

/**
 * Classe utilisée pour avoir une mémoïzation efficasse :
 * Le plateau est enregistré de manière compacte et les transformations précalculées
 * pour permettre de vérifier les égalitées en 4 calculs.
 * 
 * Dans PlateauMemoizeFousFous, equal et hashCode travaillent sur les plateaux,
 * compareTo classe selon les scores
 */
public class CouplePlateauScore implements Comparable<CouplePlateauScore> {

	/***************** Constantes *****************/
	
	private final static long masquePlateau = 0b1010101001010101101010100101010110101010010101011010101001010101L;

	/************ Attributs  ************/

	public long Plateau;
	public int Score;

	/************* Constructeurs ****************/

	public CouplePlateauScore() {
	}

	public CouplePlateauScore(long Plateau, int Score) {
		this.Plateau = Plateau;
		this.Score = Score;
	}

	/********************** Ordonancement ******************/

	@Override
	public int compareTo(CouplePlateauScore o) {
		return o.Score - Score;
	}


	/********************** Autre ******************/

	public long getPlateauBlanc() {
		return Plateau & ~masquePlateau;
	}

	public long getPlateauNoir() {
		return Long.reverseBytes(Plateau & masquePlateau);
	}

	public static long plateauToCompact(long plateauBlanc, long plateauNoir) {
		
		plateauBlanc |= Long.reverseBytes(plateauNoir);

		return plateauBlanc;
	}
	
	public PlateauFousFous getPlateau(){
		return new PlateauFousFous(getPlateauBlanc(), getPlateauNoir());
	}

	public String toString() {
		return "{" + Plateau + ";" + Score + "}";
	}
}
