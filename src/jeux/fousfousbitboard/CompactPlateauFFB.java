package jeux.fousfousbitboard;

/**
 * Classe utilisée pour manipuler (et sauvegarder) une version
 * compacte du plateau de jeu et de savoir si ce plateau est 
 * equivalent à un autre (aux symetries près)
 * 
 * 
 * @warning : rien n'a encore été testé
 */
public class CompactPlateauFFB {

	/***************** Constantes *****************/

	private final static long masquePlateau = 0b1010101001010101101010100101010110101010010101011010101001010101L;
	private final static long masqueDiagGauche = 0b0000000100000010000001000000100000010000001000000100000010000001L;
	private final static long masqueVert = 0b0000000100000001000000010000000100000001000000010000000100000001L;
	private final static long masqueHaut = 0b1111111111111111111111111111111100000000000000000000000000000000L;
	private final static long masqueBas = 0b0000000000000000000000000000000011111111111111111111111111111111L;
	
	/************ Attributs  ************/

	private long plateau;

	/************* Constructeurs ****************/

	public CompactPlateauFFB() {
		plateau = 0;
	}

	public CompactPlateauFFB(long plateauBlanc, long plateauNoir) {
		plateau = plateauBlanc;
		
		plateau |= (plateauNoir & masqueHaut) << 1;
		plateau |= (plateauNoir & masqueBas) >>> 1;
	}

	/****************** Accesseurs **********************/

	public long getPlateau() {
		return plateau;
	}

	public long getPlateauBlanc() {
		return plateau & ~masquePlateau;
	}

	public long getPlateauNoir() {
		long plateauNoir = plateau & masquePlateau;
		
		long plateauNoirFinal = 0;
		plateauNoirFinal |= (plateauNoir & masqueHaut) >>> 1;
		plateauNoirFinal |= (plateauNoir & masqueBas) << 1;
		
		return plateauNoirFinal;
	}

	/*************** Transformations Symétriques **************/

	public static long symetrieTour180(long plateau){
		return Long.reverse(plateau);
	}
	
	public static long symetrieDiagDroite(long plateau){
		long masqueDiag = masqueDiagGauche & ~1;
		
		long plateauTmp = 0L;

		plateauTmp |= ( (plateau & masqueVert) * masqueDiag)>>>56;
		for(int i=1;i<8;i++){
			plateau = plateau>>>1;
			plateauTmp = plateauTmp<<8;
			plateauTmp |= ( (plateau & masqueVert) * masqueDiag)>>>56;
		}
		
		return Long.reverseBytes(plateauTmp);
	}
	
	public static long symetrieDiagGauche(long plateau){
		return symetrieTour180(symetrieDiagDroite(plateau));
	}
	
	/********************** Autres méthodes ******************/

	/**
	 * Permet de savoir si deux plateaux sont equivalents
	 * aux simetries près
	 * @param autreCompactPlateau
	 * @return :
	 * 		0 si les deux plateaux sont identiques
	 * 		1 si identique quand on retourne l'autre à 180
	 * 		2 si identique quand on fait une symetrie diag droite
	 * 		3 si identique quand on fait une symetrie diag gauche
	 * 		-1 si ils ne sont pas identiques
	 */
	public int equivalent(CompactPlateauFFB autreCompactPlateau){
		long autrePlateau = autreCompactPlateau.getPlateau();
		
		if(plateau == autrePlateau){
			return 0;
		}else if(plateau == symetrieTour180(autrePlateau)){
			return 1;
		}else{
			autrePlateau = symetrieDiagDroite(autrePlateau);
			
			if(plateau == autrePlateau){
				return 2;
			}else if(plateau == symetrieTour180(autrePlateau)){
				return 3;
			}
		}
		
		return -1;
	}


	/********************** Aides et compactage ******************/

	public static String toString(long plateau) {
		String represente = "";

		for (int i = 63; i >= 0; i--) {

			if (((plateau >>> i) & 1L) != 0) {
				represente += "●";
			} else {
				represente += "○";
			}

			if (i % 8 == 0) {
				represente += "\n";
			}
		}
		return represente;
	}

	public String toString() {
		String represente = "";

		for (int i = 63; i >= 0; i--) {

			if (((plateau >>> i) & 1L) != 0) {
				represente += "●";
			} else {
				represente += "○";
			}

			if (i % 8 == 0) {
				represente += "\n";
			}
		}
		return represente;
	}


	public void print() {
		for (int i = 63; i >= 0; i--) {

			if (((plateau >>> i) & 1L) != 0) {
				System.out.print("●");
			} else {
				System.out.print("○");
			}

			if (i % 8 == 0) {
				System.out.print("\n");
			}
		}
	}
	
}
