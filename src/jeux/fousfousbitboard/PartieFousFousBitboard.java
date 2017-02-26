package jeux.fousfousbitboard;

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
		
		temp = new PlateauFousFousBitboard();
		
		System.out.println(temp.plateauBlanc);
		System.out.println(temp.plateauNoir);
		// TODO Auto-generated method stub
	}
}
