package fr.epita.quiz.tests;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import fr.epita.quiz.services.data.QuestionXMLDAO;

public class TestXMLReader 
{
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException 
	{
		testXpath();
	}

	private static void testXpath() throws SAXException, IOException, ParserConfigurationException, XPathExpressionException 
	{
		DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = fact.newDocumentBuilder(); 
		Document doc = builder.parse(new File("student.xml")); 
		
		XPathFactory xpathFactory = XPathFactory.newInstance();
		XPath xpath = xpathFactory.newXPath();
		
		String expression = "//topic"; 
		NodeList elementList = (NodeList) xpath.evaluate(expression, doc, XPathConstants.NODESET);
		for (int i=0; i<elementList.getLength(); i++) 
		{
			System.out.println(elementList.item(i).getTextContent());
		}
		System.out.println("***********************");
		System.out.println("***********************");
		System.out.println("***********************");
				
		expression ="//@id"; 
		NodeList attributeList = (NodeList) xpath.evaluate(expression, doc, XPathConstants.NODESET);
		for (int i=0; i<attributeList.getLength(); i++) 
		{
			System.out.println(attributeList.item(i));
		}
		System.out.println("***********************");
		System.out.println("***********************");
		System.out.println("***********************");

		expression = "//topic | //label"; 
		elementList = (NodeList) xpath.evaluate(expression, doc, XPathConstants.NODESET);
		for (int i=0; i<elementList.getLength(); i++) 
		{
			System.out.println(elementList.item(i).getTextContent());
		}
		
		System.out.println("***********************");
		System.out.println("***********************");
		System.out.println("***********************");

		expression = "/questions/question[last()]"; 
		Element element = (Element) xpath.evaluate(expression, doc, XPathConstants.NODE);
		System.out.println(element.getElementsByTagName("label").item(0).getTextContent());

		System.out.println("***********************");
		System.out.println("***********************");
		System.out.println("***********************");

		QuestionXMLDAO dao = new QuestionXMLDAO();
		List<String> labels = dao.getAllQuestionLabels();
		for (String label : labels) 
		{
			System.out.println(label);
		}
	}

	private static void readAllQuestions() throws ParserConfigurationException, SAXException, IOException 
	{
		DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = fact.newDocumentBuilder();
		Document doc = builder.parse(new File("student.xml")); 
			
		NodeList list = doc.getElementsByTagName("question"); 
		
		for (int i=0; i<list.getLength(); i++) 
		{ 
			Element el  = (Element) list.item(i);  
			String order = el.getAttribute("order"); 
			System.out.println(order);	
			NodeList label = el.getElementsByTagName("label"); 
			System.out.println(label.item(0).getTextContent()); 
		}
	}
}
