package fr.epita.quiz.tests;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import fr.epita.quiz.datamodel.*;
import fr.epita.quiz.services.data.QuestionXMLDAO;

public class TestQuiz 
{
	private static String user = "";
	private static int score = 0;
	private static int type = 0;
	private static Scanner scan;
	private static Document doc;
	private static QuestionXMLDAO dao;

	public static void main(String[] args) 
	{
		scan = new Scanner(System.in);
		dao = new QuestionXMLDAO();
		welcomeMenu();
	}

	public static void welcomeMenu() 
	{
		System.out.println("WELCOME");
		System.out.print("Enter Your Name:");
		user = scan.nextLine();
		System.out.println("Select Quiz");
		System.out.println("1-By Topic");
		System.out.println("2-Normal Quiz");
		try 
		{
			List<Question> questions = new ArrayList<Question>();
			type = scan.nextInt();
			scan.nextLine();
			if (type == 1) 
			{
				questions = buildByTopic();
			} else 
			{
				questions = buildNormal();
			}
			for (int i = 0; i < questions.size(); i++) 
			{
				showQuestion(i, questions.get(i));
				System.out.println("");
			}
			System.out.println("You Have Completed The Quiz");
			System.out.println(user + " You have Got" + score + " over " + questions.size() + " Questions");
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	private static void showQuestion(int index, Question question) 
	{
		System.out.println((index + 1) + "-" + question.getQuestion());
		if (!question.getChoices().isEmpty()) 
		{
			System.out.println("Your choice: ");
			System.out.println("");
			for (int i = 0; i < question.getChoices().size(); i++) 
			{
				System.out.println("-->" + (i + 1) + "-" + question.getChoices().get(i).getChoice());
			}
			int choice = scan.nextInt();
			scan.nextLine();
			if (question.getChoices().get(choice - 1).isValid()) 
			{
				score++;
			}
		} else 
		{
			System.out.print("Enter your answer: ");
			String reponse = scan.nextLine();
			if (question.getAnswer() != null && question.getAnswer().getText().equalsIgnoreCase(reponse)) 
			{
				score++;
			}
		}
	}

	public static void loadFile() throws SAXException 
	{
		try 
		{
			DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder;
			builder = fact.newDocumentBuilder();
			doc = builder.parse(new File("student.xml"));
		} catch (ParserConfigurationException | IOException e) 
		{
			e.printStackTrace();
		}
	}

	public static List<Question> buildNormal()
			throws XPathExpressionException, SAXException, IOException, ParserConfigurationException 
	{
		List<Question> questions = new ArrayList<Question>();
		questions = dao.getAllQuestions();
		return questions;
	}

	public static List<Question> buildByTopic() throws ParserConfigurationException, SAXException, IOException 
	{
		List<Question> questions = new ArrayList<Question>();
		System.out.println("Select Topic:");
		List<String> topics = dao.getAllTopics();
		for (int i = 0; i < topics.size(); i++) 
		{
			System.out.println((i + 1) + "-" + topics.get(i));
		}
		int sout = scan.nextInt();
		questions = dao.getAllQuestionsByTopic(topics.get(sout-1));
		return questions;
	}
}
