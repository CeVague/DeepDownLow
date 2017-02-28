package jeux.fousfousbitboard;

import java.net.MalformedURLException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import iia.jeux.modele.joueur.Joueur;

public class PartieFFB {
	
	public static void main(String[] args) throws MalformedURLException {
		
		PlateauFFB temp = new PlateauFFB(new int[][]{{0,2,0,2,0,2,0,2},
			{1,0,1,0,1,0,1,0},
			{0,2,0,2,0,2,0,2},
			{1,0,1,0,1,0,1,0},
			{0,2,0,2,0,2,0,2},
			{1,0,1,0,1,0,1,0},
			{0,2,0,2,0,2,0,2},
			{1,0,1,0,1,0,1,0}});


		Joueur jb = new Joueur("blanc");
		Joueur jn = new Joueur("noir");
		PlateauFFB.setJoueurs(jb, jn);
		
		
		System.out.println(temp.getPlateauBlanc());
		System.out.println(temp.getPlateauNoir());
		
		
		long tempInt = temp.getPlateauNoir();

		tempInt = tempInt - ((tempInt >> 1) & 0x5555555555555555L);
		tempInt = (tempInt & 0x3333333333333333L) + ((tempInt >> 2) & 0x3333333333333333L);
	     System.out.println( (((tempInt + (tempInt >> 4)) & 0x0F0F0F0F0F0F0F0FL) * 0x0101010101010101L) >> 56);
	     System.out.println( Long.bitCount(temp.getPlateauNoir()));
		
		System.out.println(temp.toString());
		temp.print();
		temp.joue(new Joueur("gfghf"), new CoupFFB("C2-B1"));
		System.out.println(temp.toString());
		temp.print();
		
		System.out.println(temp.cheminLibre(new CoupFFB("B1-D3")));
		
		System.out.println(new CoupFFB("C2-B1").getAvant());
		System.out.println(new CoupFFB("C2-B1").getApres());
		System.out.println(new CoupFFB("C2-B1").toString());
		
		temp = new PlateauFFB();
		System.out.println(temp.cheminLibre(new CoupFFB("B1-D3")));
		
		System.out.println(temp.getPlateauBlanc());
		System.out.println(temp.getPlateauNoir());
		
		

		// temp.play("C2-B1", "noir");
		// temp.play("A8-B7", "noir");
		
		temp.print();
		
		
		long startTime = System.currentTimeMillis();
		PionFFB pion1 = new PionFFB("B1-D3");
		PionFFB pion2 = new PionFFB("F7-B3");
		CoupFFB coup1 = new CoupFFB("B1-D3");
		CoupFFB coup2 = new CoupFFB("F7-B3");
		
		for(int i=0;i<2000000000;i++){
			temp.listerMangeable(jb, pion1);
			temp.listerMangeable(jn, pion2);
		}
		
		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		System.out.println(elapsedTime);
		

//		temp.print();
//		temp.joue(jb, new CoupFFB("D1-C2"));
//		temp.print();
//
//		System.out.println(temp.listerMangeable(jn, new PionFFB("B1")) + " true false B1");
//		System.out.println(temp.listerMangeable(jn, new PionFFB("A2")) + " true A2");
//		System.out.println(temp.listerMangeable(jn, new PionFFB("D3")) + " true false D3");
//		System.out.println(temp.listerMangeable(jn, new PionFFB("E4")) + " true E4");
//		System.out.println(temp.listerMangeable(jn, new PionFFB("D5")) + " false D5");
//		System.out.println(temp.listerMangeable(jn, new PionFFB("G8")) + " true G8");
//		System.out.println(temp.listerMangeable(jn, new PionFFB("H5")) + " false H5");
//		System.out.println(temp.listerMangeable(jn, new PionFFB("A8")) + " true A8");
//		temp.joue(jn, new CoupFFB("G6-H7"));
//		temp.joue(jn, new CoupFFB("E8-F7"));
//		temp.print();
//		System.out.println(temp.peutManger(jn, new PionFFB("G8")));
		
//		for(PionFFB t : temp.listerPions(jb)){
//			System.out.println(t.toString());
//		}
		// TODO Auto-generated method stub


//        Icon icon = new ImageIcon("DeepDownLow.gif");
//        icon = new ImageIcon("GnaGnaGna.gif");
//        //icon = new ImageIcon("Gneeeeeeee.gif");
//        //icon = new ImageIcon("Lol.gif");
//        JLabel label = new JLabel(icon);
//
//        JFrame f = new JFrame("Animation");
//        f.getContentPane().add(label);
//        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        f.pack();
//        f.setLocationRelativeTo(null);
//        f.setVisible(true);
	}
}
