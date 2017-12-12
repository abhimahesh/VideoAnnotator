import java.awt.Rectangle;
import java.util.Vector;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.Node;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
public class reloadArray{
	Vector < Vector <Rectangle> > boundingBoxes;
	Vector < Vector <Vector <String>> > properties;
	Vector <String> imageName;
	BaseFrame bf = null;
	public reloadArray(int tmp, BaseFrame bff){
		bf = bff;
		boundingBoxes = new Vector <Vector <Rectangle> >();
		properties = new Vector <Vector <Vector <String> > >();
		imageName = new Vector <String>();
		
		imageName.setSize(tmp);
		
		boundingBoxes.setSize(tmp);
		
		for(int i = 0; i< tmp ;++i) {
			boundingBoxes.set(i, new Vector <Rectangle>()) ;
		}
		properties.setSize(tmp);
		for(int i = 0; i< tmp ;++i) {
			properties.set(i, new Vector <Vector <String>>()) ;
		}
		
	}
	public void addImageName(int i , String temp) {
		imageName.set(i , temp);
	}
	public void writeObj(int index ,  Rectangle rect, Vector <String> property) {
		(properties.get(index)).add(property);
		(boundingBoxes.get(index)).add(rect);
	}
	
	public void readXML(rescaleImg obj) {
		 
		for(int i = 0; i < boundingBoxes.size(); ++i) {
			try {
	
//					File fXmlFile = new File("/home/amahesh/sampleXML/"+imageName.get(i) + ".xml");
					File fXmlFile = new File(bf.xmlPath+"/"+imageName.get(i) + ".xml");
					DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
					DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
					Document doc = dBuilder.parse(fXmlFile);
					doc.getDocumentElement().normalize();
	
//					System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
	
					NodeList nList = doc.getElementsByTagName("object");
	
//					System.out.println("----------------------------");
	
					for (int j = 0; j < nList.getLength(); j++) {
	
						org.w3c.dom.Node nNode = nList.item(j);
	
						//System.out.println("\nCurrent Element :" + nNode.getNodeName());
	
						if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	
							Element eElement = (Element) nNode;
							Vector <String>property = new Vector <String>();
							Rectangle rect = new Rectangle();
							//System.out.println("First Name : " + eElement.getElementsByTagName("type").item(0).getTextContent());
//							System.out.println("Type : " + eElement.getElementsByTagName("type").item(0).getTextContent());
//							System.out.println("Make : " + eElement.getElementsByTagName("make").item(0).getTextContent());
//							System.out.println("Model : " + eElement.getElementsByTagName("model").item(0).getTextContent());
//							System.out.println("Number : " + eElement.getElementsByTagName("number").item(0).getTextContent());
							
							property.add(eElement.getElementsByTagName("type").item(0).getTextContent());
							NodeList tempNlist  = eElement.getElementsByTagName("make");
							if(tempNlist.getLength() == 1){
								property.add(eElement.getElementsByTagName("make").item(0).getTextContent());
							}
							tempNlist = eElement.getElementsByTagName("model");
							if(tempNlist.getLength() == 1){
								property.add(eElement.getElementsByTagName("model").item(0).getTextContent());
							}
							//property.add(eElement.getElementsByTagName("model").item(0).getTextContent());
							
							tempNlist = eElement.getElementsByTagName("number");
							if(tempNlist.getLength() == 1){
								property.add(eElement.getElementsByTagName("number").item(0).getTextContent());
							}
							org.w3c.dom.Node tempRect = eElement.getElementsByTagName("bndbox").item(0);
							Element rt = (Element)tempRect;
							
							rect.x =(int) Double.parseDouble( rt.getElementsByTagName("xTop").item(0).getTextContent());
							rect.y = (int)Double.parseDouble( rt.getElementsByTagName("yTop").item(0).getTextContent());
							
							Vector <Double> coord = obj.coorFromXML(rect.x, rect.y);
							
							rect.x = coord.get(0).intValue();;
							
							rect.y = coord.get(1).intValue();;
							coord = obj.coorFromXML((Double)Double.parseDouble( rt.getElementsByTagName("xBottom").item(0).getTextContent()),(Double)Double.parseDouble( rt.getElementsByTagName("yBottom").item(0).getTextContent()) ); 
							rect.width = coord.get(0).intValue() - rect.x;
							rect.height = coord.get(1).intValue() - rect.y;
							writeObj(i , rect , property);
	
						}
					}
				    } catch (Exception e) {
					e.printStackTrace();
				    }
			  }

	}
	
}

