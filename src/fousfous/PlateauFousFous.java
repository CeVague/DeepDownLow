package fousfous;

import iia.jeux.modele.*;
import iia.jeux.modele.joueur.Joueur;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Chaque objets prend 21 octets en mémoire (observé en le sérialisant) tandit
 * qu'un tableau de byte (seul) en prend 145 (le bitboard est donc très compact)
 */

public class PlateauFousFous implements PlateauJeu, Partie1 {


	/***************** Constantes *****************/

	private final static int BLANC = 1;
	private final static int NOIR = 2;

	private final static long masquePlateau = 0b1010101001010101101010100101010110101010010101011010101001010101L;
	private final static long masqueDiagGauche = 0b0000000100000010000001000000100000010000001000000100000010000001L;
	private final static long masqueDiagGaucheHaute = 0b1000000100000010000001000000100000010000001000000100000010000000L;
	private final static long masqueDiagDroit = 0b1000000001000000001000000001000000001000000001000000001000000001L;
	private final static long masqueVert = 0b0000000100000001000000010000000100000001000000010000000100000001L;

	/*********** Paramètres de classe ************/

	/** Le joueur que joue "Blanc" **/
	private static Joueur joueurBlanc;

	/** Le joueur que joue "Noir" **/
	private static Joueur joueurNoir;

	/************ Attributs  ************/

	private long plateauBlanc;
	private long plateauNoir;

	/************* Constructeurs ****************/

	public PlateauFousFous() {
		plateauBlanc = 0b0101010100000000010101010000000001010101000000000101010100000000L;
		plateauNoir = 0b0000000010101010000000001010101000000000101010100000000010101010L;
	}

	public PlateauFousFous(long plateauBlanc, long plateauNoir) {
		this.plateauBlanc = plateauBlanc;
		this.plateauNoir = plateauNoir;
	}

	public PlateauFousFous(int depuis[][]) {
		plateauBlanc = 0L;
		plateauNoir = 0L;

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				switch (depuis[i][j]) {
					case BLANC:
						plateauBlanc |= 1L << (8 * i + j);
						break;
					case NOIR:
						plateauNoir |= 1L << (8 * i + j);
						break;
				}
			}
		}
	}

	/************* Gestion des paramètres de classe ******************/

	public static void setJoueurs(Joueur jb, Joueur jn) {
		joueurBlanc = jb;
		joueurNoir = jn;
	}

	public boolean isJoueurBlanc(Joueur jb) {
		return joueurBlanc.equals(jb);
	}

	public boolean isJoueurNoir(Joueur jn) {
		return joueurNoir.equals(jn);
	}

	/************* Méthodes de l'interface PlateauJeu ****************/
	/**
	 * Vitesse : 1.600.000 fois par secondes
	 */
	@Override
	public ArrayList<CoupJeu> coupsPossibles(Joueur j) {
//		ArrayList<CoupJeu> listeCoups = new ArrayList<CoupJeu>();
//
//		for (PionFousFous pion : listerPions(j)) {
//			listeCoups.addAll(coupsPossibles(j, pion));
//		}
//
//		return listeCoups;
		
		
		// 60% plus rapide
		ArrayList<CoupJeu> listeCoups = new ArrayList<CoupJeu>();
		
		ArrayList<PionFousFous> listePionsMenaceurs = new ArrayList<PionFousFous>();

		for (PionFousFous pion : listerPions(j)) {
			ArrayList<CoupFousFous> tmp = listerMangeable(j, pion);
			
			if(tmp.isEmpty()){
				listePionsMenaceurs.add(pion);
			}else{
				listeCoups.addAll(tmp);
			}
		}
		
		for (PionFousFous pion : listePionsMenaceurs) {
			listeCoups.addAll(listerMenacable(j, pion));
		}

		return listeCoups;
	}

	@Override
	public void joue(Joueur j, CoupJeu c) {
		CoupFousFous cNew = (CoupFousFous) c;

		if (j.equals(joueurBlanc)) {
			plateauBlanc ^= 1L << cNew.getAvant();
			plateauBlanc ^= 1L << cNew.getApres();
			plateauNoir &= ~(1L << cNew.getApres());
		} else {
			plateauNoir ^= 1L << cNew.getAvant();
			plateauNoir ^= 1L << cNew.getApres();
			plateauBlanc &= ~(1L << cNew.getApres());
		}
	}

	@Override
	public PlateauJeu copy() {
		return new PlateauFousFous(this.plateauBlanc, this.plateauNoir);
	}

	@Override
	public boolean coupValide(Joueur j, CoupJeu c) {
		CoupFousFous cNew = (CoupFousFous) c;
		// Vérifie que le coup est bien une diagonale
		// et que le chemin est libre
		if (!cNew.coupValide() || !cheminLibre(cNew)) { return false; }

		// Vérifie si on part bien d'un pion existant
		if (((retournePlateau(j) >>> cNew.getAvant()) & 1) == 0) { return false; }

		// Si on mange un pion adverse le coup est valide
		if (((retournePlateauAdverse(j) >>> cNew.getApres()) & 1) != 0) {
			return true;
		} else { // Si non
						// Cette position ne doit pas nous permettre de manger
			if (peutManger(j, new PionFousFous(cNew.getAvant()))) { return false; }

			// Mais la nouvelle position doit nous le permettre
			if (peutManger(j, new PionFousFous(cNew.getApres()))) {
				return true;
			} else {
				return false;
			}
		}
	}

	/************* Méthodes de l'interface Partie1 ****************/

	@Override
	public void setFromFile(String fileName) {
		plateauBlanc = 0L;
		plateauNoir = 0L;

		try {
			InputStream ips = new FileInputStream(fileName);
			InputStreamReader ipsr = new InputStreamReader(ips);
			BufferedReader br = new BufferedReader(ipsr);
			String ligne;
			while ((ligne = br.readLine()) != null) {
				if (!ligne.startsWith("%")) {
					ligne = ligne.substring(2, 10);
					for (int i = 0; i < 8; i++) {
						if (ligne.charAt(i) == 'b' || ligne.charAt(i) == 'n' || ligne.charAt(i) == '-') {
							plateauBlanc = plateauBlanc << 1;
							plateauNoir = plateauNoir << 1;
						}

						switch (ligne.charAt(i)) {
							case 'b':
								plateauBlanc |= 1;
								break;
							case 'n':
								plateauNoir |= 1;
								break;
							case '-':
								break;
						}
					}
				}
			}
			br.close();
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	public void saveToFile(String fileName, String version, String notesDeVersion, String commentaire) {
		try {
			File ff = new File(fileName);
			ff.createNewFile();
			FileWriter ffw = new FileWriter(ff);
			ffw.write("% Version : " + version + "\n");
			if (!notesDeVersion.isEmpty()) {
				ffw.write("% Notes : " + notesDeVersion + "\n");
			}
			if (!commentaire.isEmpty()) {
				ffw.write("% " + commentaire + "\n");
			}

			ffw.write("% ABCDEFGH\n");

			for (int i = 63; i >= 0; i--) {
				if (i % 8 == 7) {
					ffw.write(Integer.toString(8 - i / 8) + " ");
				}

				if (((plateauBlanc >>> i) & 1L) != 0) {
					ffw.write("b");
				} else if (((plateauNoir >>> i) & 1L) != 0) {
					ffw.write("n");
				} else {
					ffw.write("-");
				}

				if (i % 8 == 0) {
					ffw.write(" " + Integer.toString(8 - i / 8) + "\n");
				}
			}

			ffw.write("% ABCDEFGH");

			ffw.close();
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	@Override
	public void saveToFile(String fileName) {
		saveToFile(fileName, "0.9", "Tout est presque valide", "");
	}

	@Override
	public boolean estValide(String move, String player) {
		return coupValide(retourneJoueur(player), new CoupFousFous(move));
	}

	@Override
	public String[] mouvementsPossibles(String player) {
		ArrayList<CoupJeu> temp = coupsPossibles(retourneJoueur(player));

		String[] liste = new String[temp.size()];
		for (int i = 0; i < temp.size(); i++) {
			liste[i] = temp.get(i).toString();
		}
		return liste;
	}

	@Override
	public void play(String move, String player) {
		joue(retourneJoueur(player), new CoupFousFous(move));
	}

	@Override
	public boolean finDePartie() {
		return (plateauBlanc == 0 || plateauNoir == 0);
	}

	/************* Méthodes demandée pour la 2eme partie ****************/

	/** Ilustration de l'utilisation de nos méthodes de manière convaincante **/
	public static void main(String[] args) {
		// On initialise les joueurs
		Joueur jBlanc = new Joueur("blanc");
		Joueur jNoir = new Joueur("noir");
		PlateauFousFous.setJoueurs(jBlanc, jNoir);

		// On initialise un plateau de jeu
		PlateauFousFous plateauDeJeu = new PlateauFousFous();
		System.out.println("Plateau de base (initialisation sans parametres) :");
		plateauDeJeu.print();

		// On le met à jour d'après une save sur un txt
		plateauDeJeu.setFromFile("plateauExemple.txt");
		System.out.println("\nPlateau chargé depuis le fichier :");
		plateauDeJeu.print();

		// On calcule tous les coups jouables par les blancs
		String[] jePeuxJouer = plateauDeJeu.mouvementsPossibles("blanc");

		System.out.println("\nCoups jouables par les blancs :");
		System.out.print("[");
		for (String coup : jePeuxJouer)
			System.out.print(coup + ";");
		System.out.print("]\n");

		// On choisi un coup au hasard
		String coupChoisi = jePeuxJouer[ThreadLocalRandom.current().nextInt(0, jePeuxJouer.length)];
		System.out.println("On choisi de jouer " + coupChoisi + "\n");

		// On vérifie que c'est un coups valide
		System.out.println("Coup valide pour le joueur blanc ? " + plateauDeJeu.estValide(coupChoisi, "blanc"));
		System.out.println("Coup valide pour le joueur noir ? " + plateauDeJeu.estValide(coupChoisi, "noir"));

		// On joue le coup
		plateauDeJeu.play(coupChoisi, "blanc");
		System.out.println("\nPlateau une fois le coup " + coupChoisi + " joué :");
		plateauDeJeu.print();

		// On vérifie si la partie est terminée
		System.out.println("\nLa partie est-elle fini ? " + plateauDeJeu.finDePartie());

		// Et on enregistre ce nouveau plateau dans un txt
		System.out.print("\nGénération plateau-->fichier txt...");
		plateauDeJeu.saveToFile("plateauExempleModifie.txt", "0.9", "", "Le coup " + coupChoisi + " a été joué");
		System.out.println("Fait");
		System.out.println("Plateau enregistré sous le nom : plateauExempleModifie.txt");
	}

	/****************** Accesseurs **********************/

	public long getPlateauBlanc() {
		return plateauBlanc;
	}

	public long getPlateauNoir() {
		return plateauNoir;
	}

	public long getPlateauCombinees() {
		return plateauBlanc | Long.reverseBytes(plateauNoir);
	}

	public Joueur getJoueurBlanc() {
		return joueurBlanc;
	}

	public Joueur getJoueurNoir() {
		return joueurNoir;
	}

	/*************** Transformations Symétriques **************/

	public static long symetrieTour180(long plateau) {
		return Long.reverse(plateau);
	}

	public static long symetrieDiagDroite(long plateau) {
		long masqueDiag = masqueDiagGauche & ~1;

		long plateauTmp = 0L;

		plateauTmp |= ((plateau & masqueVert) * masqueDiag) >>> 56;
		for (int i = 1; i < 8; i++) {
			plateau = plateau >>> 1;
			plateauTmp = plateauTmp << 8;
			plateauTmp |= ((plateau & masqueVert) * masqueDiag) >>> 56;
		}

		return Long.reverseBytes(plateauTmp);
	}

	public static long symetrieDiagGauche(long plateau) {
		return symetrieTour180(symetrieDiagDroite(plateau));
	}

	/********************** Heuristiques *********************/

	public int heuristiqueVoisinDirect(Joueur j, boolean simple) {
		long plateauJoueur = retournePlateau(j);
		int voisins = 0;

		// Calcul simple et rapide
		if (simple) {
			voisins += comptePions((plateauJoueur << 7) & plateauJoueur);
			voisins += comptePions((plateauJoueur >>> 7) & plateauJoueur);
			voisins += comptePions((plateauJoueur << 9) & plateauJoueur);
			voisins += comptePions((plateauJoueur >>> 9) & plateauJoueur);
		// Calcul plus complexe donnant moins d'importance aux pions alignés
		} else {
			long plateauJoueurTemp = plateauJoueur;

			voisins += comptePions((plateauJoueurTemp << 7) & plateauJoueur);

			plateauJoueurTemp = plateauJoueur & ~(plateauJoueurTemp >>> 7);
			voisins += comptePions((plateauJoueurTemp >>> 7) & plateauJoueur);

			plateauJoueurTemp = plateauJoueur & ~(plateauJoueurTemp << 7);
			voisins += comptePions((plateauJoueurTemp << 9) & plateauJoueur);

			plateauJoueurTemp = plateauJoueur & ~(plateauJoueurTemp >>> 9);
			voisins += comptePions((plateauJoueurTemp >>> 9) & plateauJoueur);
		}

		return voisins;
	}

	public int heuristiqueVoisinLoins(Joueur j, boolean ennemiBloquant) {
		long plateauJoueur = retournePlateau(j);
		long plateauBloque = ~(retournePlateauAdverse(j) | masquePlateau);
		
		// Si on considère que l'on peut passer à travers les annemis
		if(!ennemiBloquant){
			plateauBloque = ~masquePlateau;
		}
		
		long plateauEtaleFinal = 0L;
		
		long plateauEtale = (plateauJoueur<<7) & plateauBloque;
		
		for(int i=0;i<6;i++){
			plateauEtale |= (plateauEtale<<7) & plateauBloque;
		}
		
		plateauEtaleFinal |= plateauEtale;
		
		plateauEtale = (plateauJoueur>>>7) & plateauBloque;
		
		for(int i=0;i<6;i++){
			plateauEtale |= (plateauEtale>>>7) & plateauBloque;
		}
		
		plateauEtaleFinal |= plateauEtale;

		plateauEtale = (plateauJoueur<<9) & plateauBloque;
		
		for(int i=0;i<6;i++){
			plateauEtale |= (plateauEtale<<9) & plateauBloque;
		}
		
		plateauEtaleFinal |= plateauEtale;
		
		plateauEtale = (plateauJoueur>>>9) & plateauBloque;
		
		for(int i=0;i<6;i++){
			plateauEtale |= (plateauEtale>>>9) & plateauBloque;
		}

		return Long.bitCount(plateauJoueur & plateauEtaleFinal);
	}

	public int heuristiqueNombrePions(Joueur j) {
		return comptePions(retournePlateau(j));
	}

	public int heuristiqueMangeurs(Joueur j) {
		int nb = 0;

		for (PionFousFous pion : listerPions(j)) {
			if (peutManger(j, pion)) {
				nb++;
			}
		}

		return nb;
	}

	public int heuristiqueMenaceurs(Joueur j) {
		int nb = 0;

		for (PionFousFous pion : listerPions(j)) {
			if (!peutManger(j, pion) && peutMenacer(j, pion)) {
				nb++;
			}
		}

		return nb;
	}

	/********************* Autres méthodes *******************/

	/**
	 * Permet de savoir si le chemin de Avant à Apres
	 * est libre de tout obstacles
	 * @param Avant la position du pion au debut
	 * @param Apres la position devant être atteinte
	 * 
	 * Notes : Il faut que Avant et Après soint sur la même diagonale
	 * 
	 * Vitesse : trop rapide
	 */
	public boolean cheminLibre(byte Avant, byte Apres) {
		// Il faut que Avant>Apres
		if (Avant < Apres) {
			byte temp = Avant;
			Avant = Apres;
			Apres = temp;
		}

		// On choisi le masque correspondant à
		// la diagonale entre ces deux points
		long masque;
		if (Avant % 8 > Apres % 8) {
			masque = masqueDiagDroit;
		} else {
			masque = masqueDiagGauche;
		}
		// On place le masque au niveau du pion le plus bas (Apres)
		masque = masque << Apres;

		// On évite que Apres ne soit compté dans le calcul
		Apres++;

		long A = 1L << Avant;
		long B = 1L << Apres;

		return ((A - B) & masque & (plateauNoir | plateauBlanc)) == 0;
	}

	/**
	 * Permet de savoir si le chemin suivit durant le coup c
	 * est libre de tout obstacles
	 * @param c un coup (on le suppose valide)
	 * 
	 * Vitesse : trop rapide
	 */
	public boolean cheminLibre(CoupFousFous c) {
		return cheminLibre(c.getAvant(), c.getApres());
	}


	/**
	 * Permet de savoir si le pion d'un joueur est en position
	 * d'en manger au moins un autre
	 * @param j le joueur concerné
	 * @param p le pion concerné
	 * 
	 * Vitesse : trop rapide
	 */
	public boolean peutManger(Joueur j, PionFousFous p) {
		long plateauNous = retournePlateau(j);
		long plateauEux = retournePlateauAdverse(j);


		byte pion = p.getPion();
		byte antepion = (byte) (63 - p.getPion());


		plateauNous &= ~(1L << pion);

		// plateauEux &= ~(1L << pion); // A enlever une fois les tests fait

		long Nous = plateauNous & (masqueDiagGauche << pion);
		long Eux = plateauEux & (masqueDiagGauche << pion);

		if (Long.numberOfTrailingZeros(Nous) > Long.numberOfTrailingZeros(Eux)) { return true; }

		Nous = plateauNous & (masqueDiagGaucheHaute >>> antepion);
		Eux = plateauEux & (masqueDiagGaucheHaute >>> antepion);

		if (Nous < Eux) { return true; }

		Nous = plateauNous & (masqueDiagDroit << pion);
		Eux = plateauEux & (masqueDiagDroit << pion);

		if (Long.numberOfTrailingZeros(Nous) > Long.numberOfTrailingZeros(Eux)) { return true; }

		Nous = plateauNous & (masqueDiagDroit >>> antepion);
		Eux = plateauEux & (masqueDiagDroit >>> antepion);

		if (Nous < Eux) { return true; }

		return false;
	}

	/**
	 * Permet de savoir si le pion d'un joueur est en position
	 * d'en menacer au moins un autre
	 * @param j le joueur concerné
	 * @param p le pion concerné
	 * 
	 * Vitesse : 50.000.000 fois par secondes
	 */
	public boolean peutMenacer(Joueur j, PionFousFous p) {
		long plateauObstacles = plateauBlanc | plateauNoir | masquePlateau;
		long adverse = retournePlateauAdverse(j);

		long masqueH, masqueB, min, max;

		int curseur;

		curseur = p.getPion();
		plateauObstacles &= ~(1L << curseur);


		for (int incr : new int[]{7, 9}) {
			if (incr == 7) {
				masqueH = masqueDiagDroit;
				masqueB = masqueDiagDroit;
			} else {
				masqueH = masqueDiagGaucheHaute;
				masqueB = masqueDiagGauche;
			}
			curseur = p.getPion();


			while (curseur > 0 && curseur < 64 && ((plateauObstacles >>> curseur) & 1) == 0) {
				curseur -= incr;
			}
			curseur += incr;
			while (curseur > 0 && curseur < 64 && ((plateauObstacles >>> curseur) & 1) == 0) {

				min = Long.highestOneBit(plateauObstacles & (masqueH >>> (63 - curseur))) & adverse;
				max = Long.lowestOneBit(plateauObstacles & (masqueB << curseur)) & adverse;

				if (min != 0 || max != 0) { return true; }

				curseur += incr;
			}
		}

		return false;
	}

	/**
	 * Liste tous les pions que possede un joueur
	 * @param j le joueur concerné
	 * 
	 * Vitesse : 17.000.000 fois par secondes
	 */
	public ArrayList<PionFousFous> listerPions(Joueur j) {
		long plateau = retournePlateau(j);
		ArrayList<PionFousFous> listePion = new ArrayList<PionFousFous>(comptePions(plateau));

//		long pion = Long.lowestOneBit(plateau);
//		while (pion != 0) {
//			listeCoups.add(new PionFousFous(Long.numberOfTrailingZeros(plateau)));
//
//			plateau &= ~pion;
//			pion = Long.lowestOneBit(plateau);
//		}
		
		while (plateau != 0) {
			listePion.add(new PionFousFous(Long.numberOfTrailingZeros(plateau)));

			plateau &= plateau-1;
		}
		
		return listePion;
	}

	/**
	 * Liste tous les coups qui peuvent être joués avec un des pions
	 * @param j le joueur qui veux joueur
	 * @param p le pion qui doit être joué
	 */
	public ArrayList<CoupFousFous> coupsPossibles(Joueur j, PionFousFous p) {
		ArrayList<CoupFousFous> tmp = listerMangeable(j, p);
		if (!tmp.isEmpty()) {
			return tmp;
		} else {
			return listerMenacable(j, p);
		}
	}

	/**
	 * Liste tous les coups qui permettent à un pion d'en manger un autre
	 * @param j le joueur qui veux joueur
	 * @param p le pion qui veux manger les autres
	 * 
	 * Vitesse : 160.000.000 fois par secondes
	 */
	public ArrayList<CoupFousFous> listerMangeable(Joueur j, PionFousFous p) {
		long plateauNous = retournePlateau(j);
		long plateauEux = retournePlateauAdverse(j);

		byte pion = p.getPion();
		byte nonpion = (byte) (63 - pion);

		plateauNous &= ~(1L << pion);

		// plateauEux &= ~(1L << pion); // A enlever une fois les tests fait

		ArrayList<CoupFousFous> listeDesCoups = new ArrayList<CoupFousFous>(4);

		long Nous = plateauNous & (masqueDiagGauche << pion);
		long Eux = plateauEux & (masqueDiagGauche << pion);
		int tmp = Long.numberOfTrailingZeros(Eux);
		if (Long.numberOfTrailingZeros(Nous) > tmp) {
			listeDesCoups.add(new CoupFousFous(pion, (byte) tmp));
		}

		Nous = plateauNous & (masqueDiagDroit << pion);
		Eux = plateauEux & (masqueDiagDroit << pion);
		tmp = Long.numberOfTrailingZeros(Eux);
		if (Long.numberOfTrailingZeros(Nous) > tmp) {
			listeDesCoups.add(new CoupFousFous(pion, (byte) tmp));
		}

		Nous = plateauNous & (masqueDiagGaucheHaute >>> nonpion);
		Eux = plateauEux & (masqueDiagGaucheHaute >>> nonpion);
		if (Nous < Eux) {
			listeDesCoups.add(new CoupFousFous(pion, (byte) (63 - Long.numberOfLeadingZeros(Eux))));
		}

		Nous = plateauNous & (masqueDiagDroit >>> nonpion);
		Eux = plateauEux & (masqueDiagDroit >>> nonpion);
		if (Nous < Eux) {
			listeDesCoups.add(new CoupFousFous(pion, (byte) (63 - Long.numberOfLeadingZeros(Eux))));
		}

		return listeDesCoups;
	}

	/**
	 * Liste tous les pions d'un joueur pouvant manger les autres
	 * @param j le joueur qui veux joueur
	 */
	public ArrayList<PionFousFous> listerMangeur(Joueur j) {
		ArrayList<PionFousFous> listePions = new ArrayList<PionFousFous>();

		for (PionFousFous pion : listerPions(j)) {
			if (peutManger(j, pion)) {
				listePions.add(pion);
			}
		}

		return listePions;
	}

	/**
	 * Liste tous Coups permetant de menacer un pion adverse
	 * @param j le joueur qui veux joueur
	 * @param p le pion qui veux menacer (on suppose qu'il ne peut pas manger)
	 * 
	 * Vitesse : 15.000.000 fois par secondes
	 */
	public ArrayList<CoupFousFous> listerMenacable(Joueur j, PionFousFous p) {
		long plateauObstacles = plateauBlanc | plateauNoir | masquePlateau;
		long adverse = retournePlateauAdverse(j);

		ArrayList<CoupFousFous> listeCoups = new ArrayList<CoupFousFous>();

		long masqueH, masqueB, min, max;

		int curseur;

		curseur = p.getPion();
		plateauObstacles &= ~(1L << curseur);


		for (int incr : new int[]{7, 9}) {
			if (incr == 7) {
				masqueH = masqueDiagDroit;
				masqueB = masqueDiagDroit;
			} else {
				masqueH = masqueDiagGaucheHaute;
				masqueB = masqueDiagGauche;
			}
			curseur = p.getPion();


			while (curseur > 0 && curseur < 64 && ((plateauObstacles >>> curseur) & 1) == 0) {
				curseur -= incr;
			}
			curseur += incr;
			while (curseur > 0 && curseur < 64 && ((plateauObstacles >>> curseur) & 1) == 0) {

				min = Long.highestOneBit(plateauObstacles & (masqueH >>> (63 - curseur))) & adverse;
				max = Long.lowestOneBit(plateauObstacles & (masqueB << curseur)) & adverse;

				if (min != 0 || max != 0) {
					listeCoups.add(new CoupFousFous(p.getPion(), (byte) curseur));
				}

				curseur += incr;
			}
		}

		return listeCoups;
	}

	/**
	 * Liste tous les pions d'un joueur pouvant menacer les autres
	 * (ils ne doivent donc pas pouvoir en manger un autre)
	 * @param j le joueur qui veux joueur
	 */
	public ArrayList<PionFousFous> listerMenaceur(Joueur j) {
		ArrayList<PionFousFous> listePions = new ArrayList<PionFousFous>();

		for (PionFousFous pion : listerPions(j)) {
			if (!peutManger(j, pion) && peutMenacer(j, pion)) {
				listePions.add(pion);
			}
		}

		return listePions;
	}

	/**
	 * Retourne ne nombre de pions encore en jeu d'un joueur
	 * @param j le joueur concerné
	 */
	public int comptePions(Joueur j) {
		return comptePions(retournePlateau(j));
	}

	/**
	 * Retourne ne nombre de pions encore en jeu d'un plateau
	 * @param plateau le plateau concerné
	 */
	public int comptePions(long plateau) {
		if (plateau == 0) { return 0; }

		return Long.bitCount(plateau);
	}


	/********************** Aides et affichage ******************/

	public Joueur retourneAdversaire(Joueur j) {
		if (j.equals(joueurNoir)) {
			return joueurBlanc;
		} else {
			return joueurNoir;
		}
	}

	public long retournePlateau(Joueur j) {
		if (j.equals(joueurNoir)) {
			return plateauNoir;
		} else {
			return plateauBlanc;
		}
	}

	public long retournePlateau(String j) {
		if (j.compareTo("noir") == 0) {
			return plateauNoir;
		} else {
			return plateauBlanc;
		}
	}

	public long retournePlateauAdverse(Joueur j) {
		if (j.equals(joueurNoir)) {
			return plateauBlanc;
		} else {
			return plateauNoir;
		}
	}

	public long retournePlateauAdverse(String j) {
		if (j.compareTo("noir") == 0) {
			return plateauBlanc;
		} else {
			return plateauNoir;
		}
	}

	public Joueur retourneJoueur(String player) {
		if (player.compareTo("noir") == 0) {
			return joueurNoir;
		} else {
			return joueurBlanc;
		}
	}

	public Joueur retourneJoueurAdverse(String player) {
		if (player.compareTo("noir") == 0) {
			return joueurBlanc;
		} else {
			return joueurNoir;
		}
	}

	public String toString() {
		String represente = "";

		for (int i = 63; i >= 0; i--) {

			if (((plateauBlanc >>> i) & 1L) != 0) {
				represente += "b";
			} else if (((plateauNoir >>> i) & 1L) != 0) {
				represente += "n";
			} else {
				represente += " ";
			}

			if (i % 8 == 0) {
				represente += "\n";
			}
		}
		return represente;
	}

	public static String toString(long tableau) {
		String represente = "";

		for (int i = 63; i >= 0; i--) {

			if (((tableau >>> i) & 1L) != 0) {
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
		System.out.println("  A B C D E F G H");
		System.out.println(" ┌─┬─┬─┬─┬─┬─┬─┬─┐");
		for (int i = 63; i >= 0; i--) {

			if (i % 8 == 7) {
				System.out.print((8 - i / 8) + "│");
			}

			if (((plateauBlanc >>> i) & 1L) != 0) {
				System.out.print("b│");
			} else if (((plateauNoir >>> i) & 1L) != 0) {
				System.out.print("n│");
			} else {
				System.out.print(" │");
			}

			if (i % 8 == 0 && i != 0) {
				System.out.print("\n ├─┼─┼─┼─┼─┼─┼─┼─┤\n");
			}
		}
		System.out.print("\n └─┴─┴─┴─┴─┴─┴─┴─┘\n");
	}

}
