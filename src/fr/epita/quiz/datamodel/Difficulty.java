package fr.epita.quiz.datamodel;

public enum Difficulty
{
	VERY_EASY(0),
	EASY(1),
	MEDIUM(2),
	HARD(3),
	VERY_HARD(4),
	EXTREMELY_HARD(5),
	
	;
	
	private int numericDifficulty;
	
	
	private Difficulty(int difficulty)
	{
		this.numericDifficulty = difficulty;
	}
	
	public int getDifficulty()
	{
		return this.numericDifficulty;
	}

}