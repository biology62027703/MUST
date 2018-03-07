package com.sertek.commons;

import java.io.File;
import java.io.FileReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class XMLHelper {

	private static Logger logger = Logger.getLogger(XMLHelper.class);
	
	public static Document getXmlDoc(String xmldata) throws Exception {
		logger.info("xmldata = \r\n" + xmldata);
		InputSource source = new InputSource(new StringReader(xmldata));
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.parse(source);

		// optional, but recommended
		doc.getDocumentElement().normalize();
		return doc;
	}
	
	public static Document getXmlDoc(File xmlFile) throws Exception {
		logger.info("xmlFile = " + xmlFile);
		InputSource source = new InputSource(new FileReader(xmlFile));
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.parse(source);

		// optional, but recommended
		doc.getDocumentElement().normalize();
		return doc;
	}
	
	public static String getText(Node inputNode, String tagName) throws Exception {
		String text = "";
		if (inputNode.getNodeType() == Node.DOCUMENT_NODE) {
			Document inputDoc = (Document) inputNode;
			NodeList nodeList = inputDoc.getElementsByTagName(tagName);
			if (nodeList.getLength() > 0) {
				text = nodeList.item(0).getTextContent();
			}
		} else if (inputNode.getNodeType() == Node.ELEMENT_NODE) {
			Element inputElement = (Element) inputNode;
			NodeList nodeList = inputElement.getElementsByTagName(tagName);
			if (nodeList.getLength() > 0) {
				text = nodeList.item(0).getTextContent();
			}
		}

		return text;
	}
	
	public static List getTextList(Element element, String tagName) throws Exception {
		List textList = new ArrayList();
		String text = "";
		NodeList nodeList = element.getElementsByTagName(tagName);
		for(int i = 0; i < nodeList.getLength(); i++){
			text = nodeList.item(i).getTextContent();
			textList.add(text);
		}
		
		return textList;
	}
	
	public static List getElementList(Node inputNode, String tagName) throws Exception {
		List elementList = new ArrayList();
		if (inputNode.getNodeType() == Node.DOCUMENT_NODE) {
			Document inputDoc = (Document) inputNode;
			NodeList nodeList = inputDoc.getElementsByTagName(tagName);
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node aNode = nodeList.item(i);
				if (aNode.getNodeType() == Node.ELEMENT_NODE) {
					Element aElement = (Element) aNode;
					elementList.add(aElement);
				}
			}
		} else if (inputNode.getNodeType() == Node.ELEMENT_NODE) {
			Element inputElement = (Element) inputNode;
			NodeList nodeList = inputElement.getElementsByTagName(tagName);
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node aNode = nodeList.item(i);
				if (aNode.getNodeType() == Node.ELEMENT_NODE) {
					Element aElement = (Element) aNode;
					elementList.add(aElement);
				}
			}
		}

		return elementList;
	}
	
	public static Element getElement(Node inputNode, String tagName) throws Exception {
		Element element = null;
		List elementList = XMLHelper.getElementList(inputNode, tagName);
		if (elementList.size() > 0) {
			element = (Element) elementList.get(0);
		}
		return element;
	}
}