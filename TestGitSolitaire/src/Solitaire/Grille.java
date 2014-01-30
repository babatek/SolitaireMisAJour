package Solitaire;

import java.util.Scanner;

public class Grille {
	
	private String[][] plateau;
	public static final int DIMENSION = 9;
	Case case1;
	
	public String[][] getPlateau()
	{
		return plateau;
	}
	
	public void setPlateau(String[][] plateau)
	{
		this.plateau = plateau;
	}
	
	public Grille (String[][] plateau)
	{
		this.plateau = plateau;
	}
	
	public String toString(Grille plateau)
	{
		String resultat = "";
		for (int i = 0; i < DIMENSION; i++) {
			for (int j = 0; j < DIMENSION; j++){
				resultat += this.stringCase(i, j);
			}	
			resultat += '\n';
		}
		return resultat;
	}
	
	public Case getCase(Grille plateau, int ord, int abs)
	{
		int valeur = 0;
		String typeCase = this.plateau[ord][abs];
		if (typeCase == "   ")
			valeur = -1;
		else
			if (typeCase == "[ ]")
				valeur = 0;
			else
				valeur = 1;
		Case caseCible = new Case(ord, abs, valeur);
		return caseCible;
	}
	
	private boolean autoriserDepart(Case caseD)
	{
		return caseD.getValeur() == 1;
	}
	
	public Case caseDuMilieu(Grille plateau, Case caseD, Case caseA)
	{
		Case caseInterdite = new Case(0, 0, -1);
		int caseDord = caseD.getOrdonne();
		int caseDabs = caseD.getAbscisse();
		int caseAord = caseA.getOrdonne();
		int caseAabs = caseA.getAbscisse();
		if ((this.autoriserDepart(caseD)) && (this.autoriserArrive(caseD, caseA)))
			if (caseDord > caseAord)
				return this.getCase(this, caseDabs, caseDord - 1);
			else
				if (caseDabs > caseAabs)
					return this.getCase(this, caseDabs - 1, caseDord);
				else
					if (caseDabs < caseAabs)
						return this.getCase(this, caseDabs + 1, caseDord);
					else
						return this.getCase(this, caseDabs, caseDord + 1);
		else
			return caseInterdite;
	}
	
	private boolean autoriserArrive(Case caseD, Case caseA)
	{
		boolean libre = (caseA.getValeur() == 0);
		boolean memeAbs = (caseD.getAbscisse() == caseA.getAbscisse());
		boolean memeOrd = (caseD.getOrdonne() == caseA.getOrdonne());
		boolean ordDif1 = caseD.getOrdonne() == caseA.getOrdonne() - 2;
		boolean ordDif2 = caseD.getOrdonne() == caseA.getOrdonne() + 2;
		boolean absDif1 = (caseD.getAbscisse() == caseA.getAbscisse() - 2);
		boolean absDif2 = (caseD.getAbscisse() == caseA.getAbscisse() + 2);
		return libre && ((memeAbs && (ordDif1 || ordDif2)) || (memeOrd && (absDif1 || absDif2)));
	}
	
	private boolean autoriserDeplacement(Grille plateau, Case caseD, Case caseA)
	{
		return plateau.autoriserDepart(caseD) && plateau.autoriserArrive(caseD, caseA) && plateau.caseDuMilieu(plateau, caseD, caseA).getValeur() == 1;
	}
	
	public String stringCase(int i, int j)
	{
		return plateau[i][j];
	}
	
	public void initGrille()
	{
		plateau = new String [DIMENSION][DIMENSION];
		for (int i = 0; i < DIMENSION; i++) {
			for (int j = 0; j < DIMENSION; j++){
				if (i == DIMENSION/2 && j == DIMENSION/2)
				{
					case1 = new Case(i, j, 0);
					plateau[i][j] = case1.toString();
				}
				else
				{
					case1 = new Case(i, j, 1);
					plateau[i][j] = case1.toString();
				}
			}	
		}
	}
	
	public Grille deplacement(Grille plateau, Case caseD, Case caseA)
	{
		String[][] plateau2 = null;
		Grille erreurPlateau = new Grille(plateau2);
		if (this.autoriserDeplacement(this, caseD, caseA))
		{
			String[][] plateau1 = this.getPlateau();
			Case caseX = new Case(0, 0, 0);
			Case caseM = plateau.caseDuMilieu(plateau, caseD, caseA);
			plateau1[caseX.getAbscisse()][caseX.getOrdonne()] = plateau1[caseD.getAbscisse()][caseD.getOrdonne()];
			plateau1[caseD.getAbscisse()][caseD.getOrdonne()] = plateau1[caseA.getAbscisse()][caseA.getOrdonne()];
			plateau1[caseA.getAbscisse()][caseA.getOrdonne()] = plateau1[caseX.getAbscisse()][caseX.getOrdonne()];
			plateau1[caseM.getAbscisse()][caseM.getOrdonne()] = plateau1[caseD.getAbscisse()][caseD.getOrdonne()];
			this.setPlateau(plateau1);
			return plateau;
		}
		else
			return erreurPlateau;
	}
	
	/*public boolean comparable(Grille plateau1, Grille plateau2)
	{
		try{
			
		boolean compare = true;
		String[][] plateauA = plateau1.getPlateau();
		String[][] plateauB = plateau2.getPlateau();
		for (int i = 0; i < DIMENSION - 1; i++) {
			for (int j = 0; j < DIMENSION - 1; j++){
				if (plateauA[i][j] != plateauB[i][j])
						compare = false;
			}
		}
		return compare;
		}
		catch (NullPointerException e)
		{
			return false;
		}
	}*/
	
	public void unCoup (Grille plateau, int ordD, int absD, int ordA, int absA)
	{
		try
		{
		Case caseD = this.getCase(plateau, ordD, absD);
		Case caseA = this.getCase(plateau, ordA, absA);
		if (!autoriserDepart(caseD))
			System.out.println("Choissisez une nouvelle case de départ.");
		else
			if (!autoriserArrive(caseD, caseA))
				System.out.println("Choississez une autre case d'arrivée");
			else
				if (caseDuMilieu(plateau, caseD, caseA).getValeur() != 1)
					System.out.println("Vous devez sauter une bille.");
					else
						System.out.println(plateau.deplacement(plateau, caseD, caseA).toString(plateau));
		}
		catch (ArrayIndexOutOfBoundsException e)
		{
			System.out.println("Restez à l'intérieur du plateau.");
		}
	}
	
	public void jouer(Grille plateau)
	{
		@SuppressWarnings("resource")
		Scanner s = new Scanner(System.in);
		int rejouer = 0;
		this.initGrille();
		System.out.println(this.toString(plateau));
		System.out.println("voulez vous rejouer? (1 = oui / 2 = non)");
		rejouer = s.nextInt();
		while (rejouer == 1)
		{
			System.out.println("entrez l'ordonné de départ : ");
			int ordD = s.nextInt();
		
			System.out.println("entrez l'abscisse de départ : ");
			int absD = s.nextInt();
					
			System.out.println("entrez l'ordonné d'arrivé : ");
			int ordA = s.nextInt();
					
			System.out.println("entrez l'abscisse d'arrivé : ");
			int absA = s.nextInt();
		
			this.unCoup(this, ordD, absD, ordA, absA);
					
			System.out.println("voulez vous rejouer? (1 = oui / 2 = non)");
			rejouer = s.nextInt();
		}
		System.out.println("Game over");
	}

	public static void main(String[] args) 
	{
		/* Pour jour au solitaire, il suffit de mettre une bille : X dans une case vide : [ ] 
		 * en passant par dessus une autre bille.
		 * 
		 * Pour ce faire, choisissez :
		 * 
		 * - l'ordonne et l'abscisse de la bille de départ : ordD et absD
		 * - l'ordonne et l'abscisse de la bille d'arrivee : ordA et absA*/
		
		String[][] plateau = null;
		Grille plateau1 = new Grille(plateau);

		plateau1.jouer(plateau1);
	}

}
