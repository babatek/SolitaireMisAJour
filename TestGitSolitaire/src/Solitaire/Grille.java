package Solitaire;

import java.nio.channels.AlreadyBoundException;

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
				resultat += plateau.stringCase(i, j);
			}	
			resultat += '\n';
		}
		return resultat;
	}
	
	public Case getCase(Grille plateau, int abs, int ord)
	{
		int valeur = 0;
		String typeCase = plateau.plateau[abs][ord];
		if (typeCase == "   ")
			valeur = -1;
		else
			if (typeCase == "[ ]")
				valeur = 0;
			else
				valeur = 1;
		Case caseCible = new Case(abs, ord, valeur);
		return caseCible;
	}
	
	public boolean autoriserDepart(Case caseD)
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
		if ((plateau.autoriserDepart(caseD)) && (plateau.autoriserArrive(caseD, caseA)))
			if (caseDord > caseAord)
				return plateau.getCase(plateau, caseDabs, caseDord - 1);
			else
				if (caseDabs > caseAabs)
					return plateau.getCase(plateau, caseDabs - 1, caseDord);
				else
					if (caseDabs < caseAabs)
						return plateau.getCase(plateau, caseDabs + 1, caseDord);
					else
						return plateau.getCase(plateau, caseDabs, caseDord + 1);
		else
			return caseInterdite;
	}
	
	public boolean autoriserDeplacement(Grille plateau, Case caseD, Case caseA)
	{
		return plateau.autoriserDepart(caseD) && plateau.autoriserArrive(caseD, caseA) && plateau.caseDuMilieu(plateau, caseD, caseA).getValeur() == 1;
	}
	
	public boolean autoriserArrive(Case caseD, Case caseA)
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
		if (plateau.autoriserDeplacement(plateau, caseD, caseA))
		{
			String[][] plateau1 = plateau.getPlateau();
			Case caseX = new Case(0, 0, 0);
			Case caseM = plateau.caseDuMilieu(plateau, caseD, caseA);
			plateau1[caseX.getAbscisse()][caseX.getOrdonne()] = plateau1[caseD.getAbscisse()][caseD.getOrdonne()];
			plateau1[caseD.getAbscisse()][caseD.getOrdonne()] = plateau1[caseA.getAbscisse()][caseA.getOrdonne()];
			plateau1[caseA.getAbscisse()][caseA.getOrdonne()] = plateau1[caseX.getAbscisse()][caseX.getOrdonne()];
			plateau1[caseM.getAbscisse()][caseM.getOrdonne()] = plateau1[caseD.getAbscisse()][caseD.getOrdonne()];
			plateau.setPlateau(plateau1);
			return plateau;
		}
		return null;
	}
	
	public void unCoup (Grille plateau, int absD, int ordD, int absA, int ordA)
	{
		try
		{
			Case caseD = plateau.getCase(plateau, absD, ordD);
			Case caseA = plateau.getCase(plateau, absA, ordA);
			System.out.println(plateau.deplacement(plateau, caseD, caseA).toString(plateau));
		}
		catch(AlreadyBoundException e)
		{
			System.out.println("deplacement non autorisé");
		} 
	}

	public static void main(String[] args) 
	{
		int ordD, absD, ordA, absA;
		String[][] plateau = null;
		Grille plateau1 = new Grille(plateau);
		
		plateau1.initGrille();
		
		ordD = 4;
		absD = 6;
		ordA = 4;
		absA = 4;
		plateau1.unCoup(plateau1, ordD, absD, ordA, absA);
		
		/*Case caseD = plateau1.getCase(plateau1, 4, 6);
		Case caseA = plateau1.getCase(plateau1, 4, 4);
		System.out.println(plateau1.deplacement(plateau1, caseD, caseA).toString(plateau1));
		
		Case caseDD = plateau1.getCase(plateau1, 6, 5);
		Case caseAA = plateau1.getCase(plateau1, 4, 5);
		System.out.println(plateau1.deplacement(plateau1, caseDD, caseAA).toString(plateau1));
		
		Case caseDD1 = plateau1.getCase(plateau1, 6, 7);
		Case caseAA1 = plateau1.getCase(plateau1, 6, 5);
		System.out.println(plateau1.deplacement(plateau1, caseDD1, caseAA1).toString(plateau1));
		*/
		//System.out.println(plateau1.toString(plateau1));
	}

}
