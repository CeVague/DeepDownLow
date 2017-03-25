package affrontements;


/**
 * Voici l'interface abstraite qu'il suffit d'implanter pour jouer. Ensuite, vous devez utiliser
 * ClientJeu en lui donnant le nom de votre classe pour qu'il la charge et se connecte au serveur.
 * 
 * @author L. Simon (Univ. Paris-Sud)- 2006-2013
 * 
 */

public interface IJoueur {

    // Mais pas lors de la conversation avec l'arbitre (màthodes initJoueur et getNumJoueur)
    // Vous pouvez changer cela en interne si vous le souhaitez
    static final int BLANC = -1;
    static final int NOIR = 1;

    /**
     * L'arbitre vient de lancer votre joueur. Il lui informe par cette màthode que vous devez jouer
     * dans cette couleur. Vous pouvez utiliser cette màthode abstraite, ou la màthode constructeur
     * de votre classe, pour initialiser vos structures.
     * 
     * @param mycolour
     *            La couleur dans laquelle vous allez jouer (-1=BLANC, 1=NOIR)
     */
    public void initJoueur(int mycolour);

    // Doit retourner l'argument passà par la fonction ci-dessus (constantes BLANC ou NOIR)
    public int getNumJoueur();

    /**
     * C'est ici que vous devez faire appel à votre IA pour trouver le meilleur coup à jouer sur le
     * plateau courant.
     * 
     * @return une chaine dàcrivant le mouvement. Cette chaine doit ?tre dàcrite exactement comme
     *         sur l'exemple : String msg = "" + positionInitiale + "-" +positionFinale + ""; ou "PASSE";
     *          Chaque position contient une lettre et un numàro, par exemple:A1,B2 (coup "A1-B2")
     */
    public String choixMouvement();

    /**
     * Màthode appelàe par l'arbitre pour dàsigner le vainqueur. Vous pouvez en profiter pour
     * imprimer une banni?re de joie... Si vous gagnez...
     * 
     * @param colour
     *            La couleur du gagnant (BLANC=-1, NOIR=1).
     */
    public void declareLeVainqueur(int colour);

    /**
     * On suppose que l'arbitre a vàrifià que le mouvement ennemi àtait bien làgal. Il vous informe
     * du mouvement ennemi. A vous de ràpercuter ce mouvement dans vos structures. Comme par exemple
     * àliminer les pions que ennemi vient de vous prendre par ce mouvement. Il n'est pas nàcessaire
     * de ràflàchir dàjà à votre prochain coup à jouer : pour cela l'arbitre appelera ensuite
     * choixMouvement().
     * 
     * @param coup
     * 			une chaine dàcrivant le mouvement:  par exemple: "A1-B2"
     */
    public void mouvementEnnemi(String coup);

    public String binoName();

}
