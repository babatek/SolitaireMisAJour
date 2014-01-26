package Solitaire;

public class Case{
	
	private int abscisse, ordonne, valeur;
	public static final int OCCUPE = 1, LIBRE = 0, INTERDIT = -1;
	
	public int getAbscisse()
	{
		return abscisse;
	}
	
	public void setAbscisse (int abscisse)
	{
		this.abscisse = abscisse;
	}
	
	public int getOrdonne()
	{
		return ordonne;
	}
	
	public void setOrdonne (int ordonne)
	{
		this.ordonne = ordonne;
	}
	
	public int getLibre()
	{
		return LIBRE;
	}
	
	public int getOccupe()
	{
		return OCCUPE;
	}
	
	public int getInterdit()
	{
		return INTERDIT;
	}
	
	public int getValeur()
	{
		return valeur;
	}
	
	public void setValeur (int valeur)
	{
		this.valeur = valeur;
	}
	
	public Case (int abscisse, int ordonne, int valeur)
	{
		this.abscisse = abscisse;
		this.ordonne = ordonne;
		this.valeur = valeur;
	}
	
	public String toString()
    {
		if (valeur == INTERDIT)
				return "   ";
		else
			if (valeur == LIBRE)
				return "[ ]";
			else
				return "[X]";
    }	

	public static void main(String[] args) 
	{

	}

}

