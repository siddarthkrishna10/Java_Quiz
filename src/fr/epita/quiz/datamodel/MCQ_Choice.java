package fr.epita.quiz.datamodel;

public class MCQ_Choice 
{
	private MCQ_Question question;
	private String choice;
	private boolean valid;

	public String getChoice() 
	{
		return choice;
	}

	public MCQ_Choice(MCQ_Question question, String choice, boolean valid) 
	{
		super();
		this.question = question;
		this.choice = choice;
		this.valid = valid;
	}

	public MCQ_Choice(String choice, boolean valid) 
	{
		super();
		this.choice = choice;
		this.valid = valid;
	}

	public void setChoice(String choice) 
	{
		this.choice = choice;
	}

	public boolean isValid() 
	{
		return valid;
	}

	public void setValid(boolean valid) 
	{
		this.valid = valid;
	}

	public MCQ_Question getQuestion() 
	{
		return question;
	}

	public void setQuestion(MCQ_Question question) 
	{
		this.question = question;
	}

}
