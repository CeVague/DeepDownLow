package iia.jeux.modele;


public interface Partie1 {

	/** initialise un plateau à partir d'un fichier texte
	 * @param fileName le nom du fichier à lire
	 */
	public void setFromFile(String fileName);
	
	/** sauve la configuration d'un plateau dans un fichier texte
	 * @param fileName le nom du fichier de sauvegarde
	 * 
	 * Le format doit être compatible avec celui utilisé pour la lecture
	 */
	public void saveToFile(String fileName);
	
	/** indique si le coup <move> est valide pour le joueur <player> sur le plateau courant
	 * @param move le coup à jouer sous la forme "A1-B2"
	 * @param player le joueur qui joue (représenté par "noir" ou "blanc")
	 */
	public boolean estValide(String move, String player);
	
	/** calcule les coups possibles pour le joueur <player> sur le plateau courant
	 * @param player le joueur qui joue (représenté par "noir" ou "blanc")
	 */
	public String[] mouvementsPossibles(String player);
	
	/** modifie le plateau en jouant le coup <move>
	 * @param move le coup à jouer sous la forme "A1-B2"
	 * @param player le joueur qui joue (représenté par "noir" ou "blanc")
	 */
	public void play(String move, String player);
	
	/** vrai lorsque le plateau correspond à une fin de partie
	 */
	public boolean finDePartie();
}
