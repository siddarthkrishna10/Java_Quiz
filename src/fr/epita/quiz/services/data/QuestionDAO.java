package fr.epita.quiz.services.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.epita.quiz.datamodel.Question;

public class QuestionDAO 
{
	private static final String DB_PASSWORD = "";
	private static final String DB_USERNAME = "test";
	private static final String DB_URL = "jdbc:h2:tcp://localhost/./test2";
	
	public void create(Question question)
	{
		try
		{
			Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
			PreparedStatement statement = connection.prepareStatement("INSERT QUESTION VALUES (?, ?)");
			statement.setString(0, question.getQuestion());
			statement.setInt(1,  question.getDifficulty());
			statement.execute();
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	public List<Question> readByDifficulty(int difficulty)
	{
		List<Question> foundQuestions = new ArrayList<Question>();
		try
		{
			Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
			PreparedStatement statement = connection.prepareStatement("SELECT QUESTION, DIFFICULTY FROM QUESTION WHERE DIFFICULTY = ?");
			statement.setInt(0, difficulty);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				String SQLquestion = resultSet.getString(1);
				int SQLdifficulty = resultSet.getInt(2);
				Question foundQuestion = new Question();
				foundQuestion.setQuestion(SQLquestion);
				foundQuestion.setDifficulty(SQLdifficulty);
			}
		} catch(SQLException e)
		{
			e.printStackTrace();
		}
		return foundQuestions;
	}
	
	public void updateDifficulty(Question question, int newDifficulty)
	{
		try 
		{
			Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
			PreparedStatement statement = connection.prepareStatement("UPDATE QUESTION SET DIFFICULTY = ? WHERE NAME = ?");
			statement.setInt(0, newDifficulty);
			statement.setString(1, question.getQuestion());
			statement.execute();
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void delete(Question question) 
	{
		
	}
}
