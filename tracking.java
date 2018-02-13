import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Vector;


public class tracking extends Thread{
	private String dir;
	private Vector <String> property;
	private Rectangle rect ;
	private int frameNumber;
	private int trackerid;
	private BaseFrame bfObj = null;
	public tracking(String tempDir, BaseFrame bftmp,Rectangle rectTmp , int frameNumberTmp , int trackeridTmp,  Vector<String> PropTmp) {
		bfObj = bftmp;
		dir = tempDir;
		property = PropTmp;
		rect = rectTmp;
		frameNumber = frameNumberTmp;
		trackerid = trackeridTmp;
	}
	public void run() {
		try
        {
            // Displaying the thread that is running
//            System.out.println ("Thread " +
//                  Thread.currentThread().getId() +
//                  " is running");
            String arguments = getArguments();
            executePythonThread(arguments);
 
        }
        catch (Exception e)
        {
            // Throwing an exception
            System.out.println ("Exception is caught");
        }
	}
	public String getArguments() {
		String arguments = null;
		int xTop = rect.x;
		int yTop = rect.y;
		int xBottom = rect.x + rect.width;
		int yBottom = rect.y + rect.height;
		
		// Scaling the coordinates of bounding box as per original image;
		Vector <Double> topCoord = bfObj.rescaleObj.coorForXML(xTop, yTop);
		Vector <Double> bottomCoord = bfObj.rescaleObj.coorForXML(xBottom, yBottom);
		
		Rectangle newRect = new Rectangle();
		
		newRect.x = topCoord.get(0).intValue();
		newRect.y = topCoord.get(1).intValue();
		
		newRect.width = bottomCoord.get(0).intValue() - topCoord.get(0).intValue();
		newRect.height = bottomCoord.get(1).intValue() - topCoord.get(1).intValue();
		
		// newRect have scaled coordinates
		
		arguments = dir + " " + frameNumber + " " + trackerid + " " + newRect.x + " " + newRect.y + " " + newRect.width + " " + newRect.height;
		return arguments;
	}
	public void executePythonThread(String arguments) {
		String[] cmd = {
				bfObj.logObj.pythonPath,
				bfObj.logObj.pwd+"/.vat/pyScripts"+"/util_thread.py",
				bfObj.logObj.pwd,
				arguments
		};
//		System.out.println(bfObj.logObj.pwd+"/.vat/pyScripts"+"/util.py");
		try {
			Process p = Runtime.getRuntime().exec(cmd);
			BufferedReader p2bfr = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = "";
//			System.out.println("---------start   pyhton   process ----------");
			while((line = p2bfr.readLine())!= null) {
//				System.out.println("Python output: "+ line);
				reloadTrackedBoxThread(line);
			}
//			System.out.println("---------end   pyhton   process ----------");
		}
		catch(Exception e) {
			System.err.println(e+"Error in ProcessBuilder");
		}
	}
	public void reloadTrackedBoxThread(String line) {
		String[] temp = line.split(" ");
		int index = Integer.parseInt((temp[0].split("\\."))[0]);
		int x = Integer.parseInt((temp[1].split("\\."))[0]);
		int y = Integer.parseInt((temp[2].split("\\."))[0]);
		int width = Integer.parseInt((temp[3].split("\\."))[0]);
		int height = Integer.parseInt((temp[4].split("\\."))[0]);
		
		Rectangle rect = new Rectangle();
		// rect now contain the scaled coordinates
		Vector <Double> coord = bfObj.rescaleObj.coorFromXML(x,y);
		rect.x = coord.get(0).intValue();
		rect.y = coord.get(1).intValue();
		
		Vector <Double> dimensions= bfObj.rescaleObj.coorFromXML(width, height);
		rect.width = dimensions.get(0).intValue();
		rect.height = dimensions.get(1).intValue();
		
		bfObj.arrOb.recvObj(index-1, rect, property);
	}
	public void deleteMetaDataFiles(){
		File folder = new File(dir);
		File fList[] = folder.listFiles();
		for (int i = 0; i < fList.length; i++) {
		    String pes = fList[i].toString();
		    if (pes.endsWith(".txt")) {
		        // and deletes
		        boolean success = (new File(bfObj.logObj.pwd+"/.vat/bufferedFiles/"+"/"+pes).delete());
		    }
		}
	}
}
