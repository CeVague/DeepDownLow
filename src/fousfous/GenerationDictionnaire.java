package fousfous;

import java.util.ArrayList;
import java.util.HashSet;

import iia.jeux.modele.CoupJeu;
import iia.jeux.modele.joueur.Joueur;

public class GenerationDictionnaire {

	public static void main(String[] args) {
		Joueur jBlanc = new Joueur("blanc");
		Joueur jNoir = new Joueur("noir");
		PlateauFousFous.setJoueurs(jBlanc, jNoir);
		Joueur[] lesJoueurs = new Joueur[]{jBlanc, jNoir};
		
		
		HashSet<PlateauMemoizeFousFous> dejaVue = new HashSet<PlateauMemoizeFousFous>();
		
		ArrayList<PlateauFousFous> listeAEvaluer = new ArrayList<PlateauFousFous>();
	
		int profondeurMax = 6;
		int profondeurActuelle = 0;
		
		
		listeAEvaluer.add(new PlateauFousFous());
		dejaVue.add(new PlateauMemoizeFousFous(listeAEvaluer.get(0)));
		
		while(profondeurMax > profondeurActuelle){
			int compte = 0;
			System.out.println("-------------------" + profondeurActuelle + "-------------------");
			ArrayList<PlateauFousFous> nouvelleListeAEvaluer = new ArrayList<PlateauFousFous>();
			
			// Pour chaque plateau à évaluer (ils sont unique)
			for(PlateauFousFous plateau : listeAEvaluer){
				
				if(compte%1000==0){
					System.out.println(compte + "/" + listeAEvaluer.size());
					
				}
				
				// Calcul de tous les coups possibles
				ArrayList<CoupJeu> listeCoups = plateau.coupsPossibles(lesJoueurs[profondeurActuelle%2]);
				
				// Ajout de tout ces nouveaux etats à la futur liste à evaluer
				for(CoupJeu coup : listeCoups){
					PlateauFousFous plateauTemp = (PlateauFousFous) plateau.copy();
					plateauTemp.joue(lesJoueurs[profondeurActuelle%2], coup);
					
					
					PlateauMemoizeFousFous plateauMem = new PlateauMemoizeFousFous(plateauTemp);
					plateauMem.setScore(profondeurActuelle+1);
					
					if(dejaVue.add(plateauMem)){
						if(profondeurActuelle+1 != profondeurMax)
							nouvelleListeAEvaluer.add(plateauTemp);
					}
				}
				
				compte++;
			}
			
			listeAEvaluer.clear();
			listeAEvaluer.addAll(nouvelleListeAEvaluer);
			
			profondeurActuelle++;
		}

		System.out.println("Taille à évaluer : " + listeAEvaluer.size());
		System.out.println("----------------------------------------------");

		System.out.println("Taille déjà vue : " + dejaVue.size());
		
	}
}
