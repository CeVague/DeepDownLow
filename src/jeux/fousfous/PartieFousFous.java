package jeux.fousfous;

public class PartieFousFous {
	
	public static void main(String[] args) {
		CoupFousFous temp = new CoupFousFous("A2-B5");
		
		System.out.println(temp.toString());
		System.out.println(temp.getAvant()[0]);
		System.out.println(temp.getAvant()[1]);
		System.out.println(temp.getApres()[0]);
		System.out.println(temp.getApres()[1]);
		// TODO Auto-generated method stub
	}
}
