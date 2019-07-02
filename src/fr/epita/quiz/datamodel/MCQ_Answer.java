package fr.epita.quiz.datamodel;

public class MCQ_Answer 
{
	private Student student;
	private MCQ_Choice choice;
	
	public Student getStudent()
	{
		return student;
	}
	
	public void setStudent(Student student)
	{
		this.student = student;
	}
	
	public MCQ_Choice getChoice()
	{
		return choice;
	}
	
	public void setChoice(MCQ_Choice choice)
	{
		this.choice = choice;
	}

}
