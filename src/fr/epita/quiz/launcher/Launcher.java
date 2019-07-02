package fr.epita.quiz.launcher;

import java.util.Scanner;

public class Launcher
{

	private static final String LOGIN = "ADM";
	private static final String PWD = "ADM";

	public static void main(String[] args)
	{

		Scanner scanner = new Scanner(System.in);
		boolean authenticated = authenticate(scanner);
		if (!authenticated) 
		{
			scanner.close();
			return;
		}
		
		System.out.println("You're authenticated");
		String answer = "";
		while (!answer.equals("q")) 
		{

			answer = displayMenu(scanner);

			switch (answer) 
			{
				case "1":
					quizCreation();
					break;
				case "2":
					questionCreation();
					break;
				case "q":
					System.out.println("Good bye!");
					break;

				default:
					System.out.println("Option not recognized, please enter an other option");
					break;
			}
		}

		scanner.close();

	}

	
	private static void quizCreation()
	{
		System.out.println("Quiz Creation ...");
	}
	
	private static void questionCreation()
	{
		System.out.println("Question creation ...");
	}

	private static String displayMenu(Scanner scanner)
	{
		String answer;
		System.out.println("-- Menu --");
		System.out.println("1. Create Quiz");
		System.out.println("2. Create Question");
		System.out.println("q. Quit the application");
		System.out.println("What is your choice ? (1|2|q) :");
		answer = scanner.nextLine();
		return answer;
	}

	private static boolean authenticate(Scanner scanner)
	{
		System.out.println("Please enter your login : ");
		String login = scanner.nextLine();
		System.out.println("Please enter your password : ");
		String password = scanner.nextLine();

	
		return LOGIN.equals(login) && PWD.equals(password);
	}

}
