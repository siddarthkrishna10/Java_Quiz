package fr.epita.quiz.tests;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import fr.epita.quiz.datamodel.Question;
import fr.epita.quiz.services.data.QuestionXMLDAO;

public class TestXMLWriter 
{
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, TransformerException 
	{
		
	}

	private static void delete() throws SAXException, IOException, ParserConfigurationException, TransformerConfigurationException, TransformerFactoryConfigurationError, TransformerException 
	{
		QuestionXMLDAO dao = new QuestionXMLDAO();
		List<Question> listQuestions = dao.getAllQuestions();
		Question question = listQuestions.get(0);
		dao.delete(question);
	}

	private static void modify() throws SAXException, IOException, ParserConfigurationException, TransformerException 
	{
		QuestionXMLDAO dao = new QuestionXMLDAO();
		List<Question> listQuestions = dao.getAllQuestions();
		Question question = listQuestions.get(0);
		question.setDifficulty(1);
		dao.update(question);
	}

	private static void create() throws ParserConfigurationException, SAXException, IOException,
		TransformerFactoryConfigurationError, TransformerConfigurationException, TransformerException 
	{
		DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = fact.newDocumentBuilder(); 
		Document doc = builder.parse(new File("student.xml")); 

		Element newQuestion = doc.createElement("question"); 
		newQuestion.setAttribute("order", "3");
		Element rootElement = doc.getDocumentElement(); 
		rootElement.appendChild(newQuestion); 
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer(); 
		transformer.setOutputProperty(OutputKeys.INDENT, "yes"); 
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
		transformer.transform(new DOMSource(doc), new StreamResult("student.xml")); 
	}
}