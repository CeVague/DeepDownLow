package jeux.fousfousbitboard;

import iia.jeux.modele.joueur.Joueur;

public class PartieFousFousBitboard {
	
	public static void main(String[] args) {
		
		PlateauFousFousBitboard temp = new PlateauFousFousBitboard(new int[][]{{0,2,0,2,0,2,0,2},
			{1,0,1,0,1,0,1,0},
			{0,2,0,2,0,2,0,2},
			{1,0,1,0,1,0,1,0},
			{0,2,0,2,0,2,0,2},
			{1,0,1,0,1,0,1,0},
			{0,2,0,2,0,2,0,2},
			{1,0,1,0,1,0,1,0}});
		
		System.out.println(temp.plateauBlanc);
		System.out.println(temp.plateauNoir);
		
		System.out.println(temp.toString());
		temp.joue(new Joueur("gfghf"), new CoupFousFousBitboard("C2-B1"));
		System.out.println(temp.toString());
		
		temp = new PlateauFousFousBitboard();
		
		System.out.println(temp.plateauBlanc);
		System.out.println(temp.plateauNoir);
		
		System.out.println(temp.toString());
		// TODO Auto-generated method stub
	}
}
