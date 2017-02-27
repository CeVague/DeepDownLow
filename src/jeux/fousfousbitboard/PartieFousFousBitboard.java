package jeux.fousfousbitboard;

import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import iia.jeux.modele.joueur.Joueur;

public class PartieFousFousBitboard {
	
	public static void main(String[] args) throws MalformedURLException {
		
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
		System.out.println(new CoupFousFousBitboard("C2-B1").getAvant());
		System.out.println(new CoupFousFousBitboard("C2-B1").getApres());
		
		temp = new PlateauFousFousBitboard();
		
		System.out.println(temp.plateauBlanc);
		System.out.println(temp.plateauNoir);
		
		System.out.println(temp.toString());
		// TODO Auto-generated method stub


        Icon icon = new ImageIcon("DeepDownLow.gif");
        icon = new ImageIcon("GnaGnaGna.gif");
        //icon = new ImageIcon("Gneeeeeeee.gif");
        //icon = new ImageIcon("Lol.gif");
        JLabel label = new JLabel(icon);

        JFrame f = new JFrame("Animation");
        f.getContentPane().add(label);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
	}
}
