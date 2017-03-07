package jeux.fousfousbitboard;

/**
 * Classe utilisée pour savoir très rapidement si ce plateau
 * est equivalent à un autre (aux symetries près)
 */
public class PlateauFFBforSearch {

	/***************** Constantes *****************/

	private final static long masquePlateau = 0b1010101001010101101010100101010110101010010101011010101001010101L;
	private final static long masqueDiagGauche = 0b0000000100000010000001000000100000010000001000000100000010000001L;
	private final static long masqueVert = 0b0000000100000001000000010000000100000001000000010000000100000001L;
	private final static long masqueHaut = 0b1111111111111111111111111111111100000000000000000000000000000000L;
	private final static long masqueBas = 0b0000000000000000000000000000000011111111111111111111111111111111L;
	
	/************ Attributs  ************/

	private long plateauSimple;
	private long plateauSymetrie;

	/************* Constructeurs ****************/

	public PlateauFFBforSearch() {
		plateauSimple = 0;
		plateauSymetrie = 0;
	}

	public PlateauFFBforSearch(long plateauBlanc, long plateauNoir) {
		
		plateauSimple = plateauToCompact(plateauBlanc, plateauNoir);
		
		plateauBlanc = symetrieDiagDroite(plateauBlanc);
		plateauNoir = symetrieDiagDroite(plateauNoir);
		
		plateauSymetrie = plateauToCompact(plateauBlanc, plateauNoir);
	}

	public void setPlateauSimple(long plateauSimple) {
		this.plateauSimple = plateauSimple;
	}

	public void setPlateauSymetrie(long plateauSymetrie) {
		this.plateauSymetrie = plateauSymetrie;
	}

	/****************** Accesseurs **********************/

	public long getPlateauSimple() {
		return plateauSimple;
	}

	public long getPlateauSymetrie() {
		return plateauSymetrie;
	}

	public long getPlateauBlanc() {
		return plateauSimple & ~masquePlateau;
	}

	public long getPlateauNoir() {
		long plateauNoir = plateauSimple & masquePlateau;
		
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
	
	/********************** Verifier equivalence ******************/

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
	public int equivalent(PlateauFFBforSearch autreCompactPlateau){
		
		if(plateauSimple == autreCompactPlateau.getPlateauSimple()){
			return 0;
		}else if(plateauSimple == symetrieTour180(autreCompactPlateau.getPlateauSimple())){
			return 1;
		}else if(plateauSimple == autreCompactPlateau.getPlateauSymetrie()){
			return 2;
		}else if(plateauSimple == symetrieTour180(autreCompactPlateau.getPlateauSymetrie())){
			return 3;
		}
		
		return -1;
	}

    @Override
    public int hashCode() {
        return (int) (plateauSimple ^ Long.reverseBytes(plateauSimple));
    }

    @Override
    public boolean equals(Object obj) {
       if (!(obj instanceof PlateauFFBforSearch))
            return false;

       return equivalent((PlateauFFBforSearch) obj) != -1;
    }


	/********************** Aides et compactage ******************/
	
	public static long plateauToCompact(long plateauBlanc, long plateauNoir){
		
		plateauBlanc |= (plateauNoir & masqueHaut) << 1;
		plateauBlanc |= (plateauNoir & masqueBas) >>> 1;
		
		return plateauBlanc;
	}

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

			if (((plateauSimple >>> i) & 1L) != 0) {
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

			if (((plateauSimple >>> i) & 1L) != 0) {
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
