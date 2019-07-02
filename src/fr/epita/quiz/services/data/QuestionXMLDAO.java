package fr.epita.quiz.services.data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import fr.epita.quiz.datamodel.Answer;
import fr.epita.quiz.datamodel.MCQChoice;
import fr.epita.quiz.datamodel.Question;

public class QuestionXMLDAO 
{
	private static final String XML_FILENAME = "student.xml";

	public List<Question> getAllQuestions() throws SAXException, IOException, ParserConfigurationException 
	{
		Document doc = parseFile();

		List<Question> listQuestions = new ArrayList<>();
		NodeList list = doc.getElementsByTagName("question");
		for (int i = 0; i < list.getLength(); i++) 
		{
			Question question = new Question();
			Element questionXML = (Element) list.item(i);
			int id = Integer.valueOf(questionXML.getAttribute("order")); 
																			
			String label = questionXML.getElementsByTagName("label").item(0).getTextContent(); 
																								
			int difficulty = Integer.valueOf(questionXML.getElementsByTagName("difficulty").item(0).getTextContent()); 
																															
			question.setQuestion(label);
			question.setDifficulty(difficulty);

			String[] topics = getAllTopicsFromQuestion(questionXML);
			question.setTopics(topics);
			question.getAnswer(getAnswerFromQuestion(questionXML));
			question.setChoices(getChoicesFromQuestion(questionXML));

			listQuestions.add(question);
		}

		return listQuestions;
	}

	public List<String> getAllQuestionLabels()
			throws ParserConfigurationException, SAXException, IOException, XPathExpressionException 
	{
		List<String> labelsToReturn = new ArrayList<>();

		Document doc = parseFile();
		
		XPathFactory xpathFactory = XPathFactory.newInstance();
		XPath xpath = xpathFactory.newXPath();
		
		XPathExpression expression = xpath.compile("//label");
		NodeList listElements = (NodeList) expression.evaluate(doc, XPathConstants.NODESET);
		
		for (int i = 0; i < listElements.getLength(); i++) 
		{
			labelsToReturn.add(listElements.item(i).getTextContent());
		}
		return labelsToReturn;
	}

	private String[] getAllTopicsFromQuestion(Element question) 
	{	
		Element topicsElement = (Element) question.getElementsByTagName("topics").item(0);
	
		NodeList topicsList = topicsElement.getElementsByTagName("topic");

		String[] result = new String[topicsList.getLength()];
		
		for (int i = 0; i < topicsList.getLength(); i++) 
		{
			Element topic = (Element) topicsList.item(i);
			result[i] = topic.getTextContent(); 
		}
		return result;
	}

	private Answer getAnswerFromQuestion(Element question) 
	{
		Answer answer = new Answer();
		if(question.getElementsByTagName("answers").getLength()!=0) 
		{
			Element topicsElement = (Element) question.getElementsByTagName("answers").item(0);
			NodeList topicsList = topicsElement.getElementsByTagName("answer");
			answer.setText(topicsList.item(0).getTextContent());
		}
		return answer;
	}

	private List<MCQChoice> getChoicesFromQuestion(Element question) 
	{
		List<MCQChoice> choices = new ArrayList();
		if(question.getElementsByTagName("choices").getLength()!=0) 
		{
			Element topicsElement = (Element) question.getElementsByTagName("choices").item(0);
			NodeList topicsList = topicsElement.getElementsByTagName("choice");
			for (int i = 0; i < topicsList.getLength(); i++) 
			{
				Element topic = (Element) topicsList.item(i);
				choices.add(new MCQChoice(topic.getTextContent(),Boolean.valueOf(topic.getAttribute("valid"))));
			}
		}
		return choices;
	}

	public List<String> getAllTopics() throws ParserConfigurationException, SAXException, IOException 
	{
		List<String> result = new ArrayList();
		Document doc = parseFile();
		NodeList list = doc.getElementsByTagName("topic");
		for (int i = 0; i < list.getLength(); i++) 
		{
			String titre = list.item(i).getTextContent();
			if (!result.contains(titre.trim())) 
			{
				result.add(titre);
			}
		}
		return result;
	}

	public void create(Question question) throws ParserConfigurationException, SAXException, IOException,
			TransformerConfigurationException, TransformerFactoryConfigurationError, TransformerException 
	{	
		Document doc = parseFile();

		Element newQuestion = doc.createElement("question");
		
		Element label = doc.createElement("label");
		label.setTextContent(question.getQuestion());
		newQuestion.appendChild(label);

		Element difficulty = doc.createElement("difficulty");
		difficulty.setTextContent(String.valueOf(question.getDifficulty()));
		newQuestion.appendChild(difficulty);

		Element topics = doc.createElement("topics"); 
		
		for (String topic : question.getTopics()) 
		{ 
			Element xmlTopic = doc.createElement("topic"); 
			xmlTopic.setTextContent(topic); 
			topics.appendChild(xmlTopic); 
		}
		newQuestion.appendChild(topics);
		doc.getDocumentElement().appendChild(newQuestion);
		transformXMLFile(doc);
	}

	private Document parseFile() throws ParserConfigurationException, SAXException, IOException 
	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(new File(XML_FILENAME));
		return doc;
	}

	private void transformXMLFile(Document doc)
			throws TransformerFactoryConfigurationError, TransformerConfigurationException, TransformerException 
	{
		TransformerFactory fact = TransformerFactory.newInstance();
		Transformer transformer = fact.newTransformer();
		transformer.transform(new DOMSource(doc), new StreamResult(XML_FILENAME));
	}

	public void update(Question question)
			throws SAXException, IOException, ParserConfigurationException, TransformerException 
	{	
		Document doc = parseFile();

		NodeList listQuestions = doc.getElementsByTagName("question");
		
		for (int i = 0; i < listQuestions.getLength(); i++) 
		{
			Element questionXML = (Element) listQuestions.item(i);
			int idXML = Integer.valueOf(questionXML.getAttribute("id"));
			
			if (question.getId() == idXML) 
			{ 				
				Element label = (Element) questionXML.getElementsByTagName("label").item(0);
				label.setTextContent(question.getQuestion());
		
				Element difficulty = (Element) questionXML.getElementsByTagName("difficulty").item(0);
				difficulty.setTextContent(String.valueOf(question.getDifficulty()));
			
				Element topics = (Element) questionXML.getElementsByTagName("topics").item(0);
				NodeList topicList = topics.getElementsByTagName("topic");
				
				for (int j = 0; j < topicList.getLength(); j++) 
				{
					topics.removeChild(topicList.item(j));
				}

				for (String topic : question.getTopics()) 
				{
					Element xmlTopic = doc.createElement("topic"); 
					xmlTopic.setTextContent(topic); 
					topics.appendChild(xmlTopic); 
				}
			}
		}
		transformXMLFile(doc);
	}

	public void delete(Question question) throws ParserConfigurationException, SAXException, IOException,
			TransformerConfigurationException, TransformerFactoryConfigurationError, TransformerException 
	{
		Document doc = parseFile();
		
		NodeList listQuestions = doc.getElementsByTagName("question");
	
		for (int i = 0; i < listQuestions.getLength(); i++) 
		{
			Element questionXML = (Element) listQuestions.item(i);
			int idXML = Integer.valueOf(questionXML.getAttribute("id")); 
			if (question.getId() == idXML) 
			{ 
				doc.getDocumentElement().removeChild(questionXML);
			}
		}
		transformXMLFile(doc);
	}

	public List<Question> getAllQuestionsByTopic(String topic)
			throws ParserConfigurationException, SAXException, IOException 
	{
		Document doc = parseFile();
		List<Question> listQuestions = new ArrayList<>();
		NodeList list = doc.getElementsByTagName("question");
		for (int i = 0; i < list.getLength(); i++) 
		{
			Question question = new Question();
			Element questionXML = (Element) list.item(i);
				
			int id = Integer.valueOf(questionXML.getAttribute("order"));
			
			String label = questionXML.getElementsByTagName("label").item(0).getTextContent();
			int difficulty = Integer.valueOf(questionXML.getElementsByTagName("difficulty").item(0).getTextContent()); 
																														
			question.setId(id); 
			question.setQuestion(label);
			question.setDifficulty(difficulty);

			String[] topics = getAllTopicsFromQuestion(questionXML);
			question.setTopics(topics);
			for (String str : question.getTopics()) 
			{
				if (str.equalsIgnoreCase(topic.toLowerCase())) 
				{
					listQuestions.add(question);
				}
			}
			question.getAnswer(getAnswerFromQuestion(questionXML));
			question.setChoices(getChoicesFromQuestion(questionXML));
		}
		return listQuestions;
	}
}