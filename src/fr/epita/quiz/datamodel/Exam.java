package fr.epita.quiz.datamodel;

import java.util.List;

public class Exam 
{
	private List<Answer> listAnswers;
	private List<MCQ_Answer> listMCQAnswers;
	
	private long grade;
	private Student student;
	private Quiz quiz;

	public List<Answer> getListAnswers() 
	{
		return listAnswers;
	}

	public void setListAnswers(List<Answer> listAnswers) 
	{
		this.listAnswers = listAnswers;
	}

	public List<MCQ_Answer> getListMCQAnswers() 
	{
		return listMCQAnswers;
	}

	public void setListMCQAnswers(List<MCQ_Answer> listMCQAnswers) 
	{
		this.listMCQAnswers = listMCQAnswers;
	}

	public long getGrade() 
	{
		return grade;
	}

	public void setGrade(long grade) 
	{
		this.grade = grade;
	}

	public Student getStudent() 
	{
		return student;
	}

	public void setStudent(Student student) 
	{
		this.student = student;
	}

	public Quiz getQuiz() 
	{
		return quiz;
	}

	public void setQuiz(Quiz quiz) 
	{
		this.quiz = quiz;
	}

}
