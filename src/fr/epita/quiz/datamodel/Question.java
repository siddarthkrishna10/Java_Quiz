package fr.epita.quiz.datamodel;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class Question
{
	private String question;
	private String[] topics;
	private int difficulty;
	private int id;
	
	private Answer answer = null;
	
	private List<MCQ_Choice> choices = new ArrayList();
	
	public String getQuestion() 
	{
		return question;
	}
	
	public void setQuestion(String question)
	{
		this.question = question;
	}
	
	public String[] getTopics()
	{
		return topics;
	}
	
	public void setTopics(String[] topics)
	{
		this.topics = topics;
	}
	
	public int getDifficulty()
	{
		return difficulty;
	}
	
	public void setDifficulty(int difficulty)
	{
		this.difficulty = difficulty;
	}
	
	public int getId()
	{
		return id;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public Answer getAnswer()
	{
		return answer;
	}

	public void getAnswer(Answer answer)
	{
		this.answer = new Answer();
		this.answer = answer;
	}
	
	public List<MCQ_Choice> getChoices()
	{
		return choices;
	}
	
	public void setChoices(List<MCQ_Choice> choices)
	{
		this.choices = choices;
	}
	
	@Override
	public String toString()
	{
		return "Question [id=" + id + ", question=" + question + ", topics=" + Arrays.toString(topics) + ", difficulty="
				+ difficulty + ", answer=" + answer + ", choices=" + choices + "]";
	}
	
}
