import java.awt.Rectangle;
import java.util.Vector;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
@SuppressWarnings("unused")
public class arrObjects {
	Vector < Vector <Rectangle> > boundingBoxes;
	Vector < Vector <Vector <String>> > properties;
	
	Vector <String> imageName;
	BaseFrame bf = null;
	public arrObjects(int tmp, BaseFrame baseFrame){
		bf = baseFrame;
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
	
	public void recvObj(int index ,  Rectangle rect, Vector <String> property) {
		
		(properties.get(index)).add(property);
		(boundingBoxes.get(index)).add(rect);
		
	}
	
	public void genrateXML(rescaleImg obj) {
		for(int i = 0; i < boundingBoxes.size(); ++i) {
			try {	 	
					DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
					DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	
					Document doc = docBuilder.newDocument();
					Element rootElement = doc.createElement("annotation");
					doc.appendChild(rootElement);
	
					Element fileName = doc.createElement("filename");
					
					//System.out.println(imageName.get(i));
					
					fileName.appendChild(doc.createTextNode(imageName.get(i)));  				
					rootElement.appendChild(fileName);
					
					for(int j = 0 ; j < boundingBoxes.get(i).size() ; ++j) {
						Element object = doc.createElement("object");
						rootElement.appendChild(object);
		
						Element type = doc.createElement("type");
						type.appendChild(doc.createTextNode(properties.get(i).get(j).get(0)));
						object.appendChild(type);
						
						Element make = doc.createElement("make");
						make.appendChild(doc.createTextNode(properties.get(i).get(j).get(1)));
						object.appendChild(make);
		
						Element model = doc.createElement("model");
						model.appendChild(doc.createTextNode(properties.get(i).get(j).get(2)));
						object.appendChild(model);
						
						Element number = doc.createElement("number");
						number.appendChild(doc.createTextNode(properties.get(i).get(j).get(3)));
						object.appendChild(number);
						
						Element occlusion = doc.createElement("occlusion");
						occlusion.appendChild(doc.createTextNode(properties.get(i).get(j).get(4)));
						object.appendChild(occlusion);
						
						Element objectId = doc.createElement("objectId");
						objectId.appendChild(doc.createTextNode(properties.get(i).get(j).get(5)));
						object.appendChild(objectId);
						
						
						Element bndbox = doc.createElement("bndbox");
						//bndbox.appendChild(doc.createTextNode(boundingBoxes.get(i).get(j).x));
						object.appendChild(bndbox);
						
						Element xTop = doc.createElement("xTop");
						
						
						Vector < Double > coord = obj.coorForXML(boundingBoxes.get(i).get(j).x, boundingBoxes.get(i).get(j).y) ;		
								
						xTop.appendChild(doc.createTextNode("" + coord.get(0)));
						bndbox.appendChild(xTop);
						
						Element yTop = doc.createElement("yTop");
						yTop.appendChild(doc.createTextNode("" + coord.get(1)));
						bndbox.appendChild(yTop);
						
						Element xBottom = doc.createElement("xBottom");
						double tempX = boundingBoxes.get(i).get(j).width + boundingBoxes.get(i).get(j).x;
						
						
						
						Element yBottom = doc.createElement("yBottom");
						double tempY = boundingBoxes.get(i).get(j).height + boundingBoxes.get(i).get(j).y;
						
						coord = obj.coorForXML(tempX, tempY) ;
						
						xBottom.appendChild(doc.createTextNode("" + coord.get(0)));
						bndbox.appendChild(xBottom);
						yBottom.appendChild(doc.createTextNode("" +  coord.get(1)));
						bndbox.appendChild(yBottom);
					}
					
					TransformerFactory transformerFactory = TransformerFactory.newInstance();
					Transformer transformer = transformerFactory.newTransformer();
					DOMSource source = new DOMSource(doc);
					//System.out.println(imageName.get(i));
//					StreamResult result = new StreamResult("/home/amahesh/sampleXML/"+imageName.get(i) + ".xml");
					StreamResult result = new StreamResult(bf.xmlPath+"/"+imageName.get(i) + ".xml");
	
					transformer.transform(source, result);
	
					//System.out.println("File saved!");
	
				  } catch (ParserConfigurationException pce) {
					pce.printStackTrace();
				  } catch (TransformerException tfe) {
					tfe.printStackTrace();
				  }
			}
	}
	
	
}
