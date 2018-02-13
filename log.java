import java.awt.Dialog;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import javax.swing.JOptionPane;

public class log {
	private BaseFrame bfObj = null;
	public String pwd = null;
	public String pythonPath = null;
	private PrintWriter logWriter = null;
	private String dir = null;
	public log(BaseFrame bfObjtmp) {
		bfObj = bfObjtmp;
	}
	public void createLogFile() {
		//Getting present working directory
		try {
			Process p1 = Runtime.getRuntime().exec("pwd");
			p1.waitFor();
			BufferedReader p1bfr = new BufferedReader(new InputStreamReader(p1.getInputStream()));
			pwd = "";
			String line = "";
			while((line = p1bfr.readLine())!= null) {
				pwd = line;
//				System.out.println("pwd is: "+ pwd);
			}
			//Creating metatdata folder for the software run
			File file = new File(pwd+"/.vat/log");
			file.mkdirs();
			file = new File(pwd+"/.vat/bufferFiles");
			file.mkdirs();
			file = new File(pwd+"/.vat/pyScripts");
			file.mkdirs();
			logWriter = new PrintWriter(pwd+"/.vat/log/log.txt","UTF-8");
			logWriter.println("log File created : "+pwd+"/.vat/log/log.txt");
			logWriter.println("-----------------------------------------------------------------");
			logWriter.flush();
			if(pythonPath!=null) {
				bfObj.chfOb.callingpartialBaseFunction();
			}
			while(pythonPath==null) {
				pythonPath = JOptionPane.showInputDialog(bfObj.frame, "Please add your Python scripts to the folder : \n"+pwd +"/.vat/pyScripts\n\n"+"Enter your Python Path :","/home/abhi/anaconda3/envs/condaEnv/bin/python3.6");
				if(pythonPath!=null) {
					try {
						Process p = Runtime.getRuntime().exec(pythonPath +" "+pwd+"/.vat/pyScripts/checkPython.py");
						int exitcode = p.waitFor();
						if(exitcode!=0) {
							pythonPath=null;
							JOptionPane.showMessageDialog(bfObj.frame,"Please enter correct Python Path","Wrong Python Path",JOptionPane.ERROR_MESSAGE);;
						}
						else {
							bfObj.chfOb.callingpartialBaseFunction();
						}
					}
					catch(Exception e) {
						pythonPath=null;
						JOptionPane.showMessageDialog(bfObj.frame,"Please enter correct Python Path","Wrong Python Path",JOptionPane.ERROR_MESSAGE);;
					}
					
				}
				else {
					break;
				}
			}
//			JOptionPane.showMessageDialog(bfObj.frame,"Please add your Python scripts to the folder : \n"+pwd +"/.vat/pyScripts");
		}
		catch(Exception e){
			System.err.println("Error in log.java/createLogFile() function"+e);
		}
	}
	public void createMetaDataFile(String loadPath){
		dir = loadPath;
		File file = null;
		try {
//			file = new File("/home/amahesh/sample");
			file = new File(dir);
			//file = new File(getClass().getResource("/sample").getFile());
		}
		catch (Exception e) {
			System.out.println("Error in getImages()");
		}
		File[] fileList = file.listFiles();
		String[] imageList = new String[fileList.length];
		int n = imageList.length;
		for(int i =0;i<n;++i) {
			imageList[i]=fileList[i].getName();
		//	System.out.println(imageList[i]);
		}
		Arrays.sort(imageList);
		int numOfFrames = n;
		
		//Writing to a file for python script usage
		String tmp = "";
		for(int i =0;i<n;++i) {
			tmp = tmp + imageList[i] + " ";
		}
		try {
			PrintWriter writer = new PrintWriter(bfObj.logObj.pwd+"/.vat/bufferFiles/"+"metadata.txt","UTF-8");
			writer.println(tmp);
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void enterLog(String logline) throws IOException {
		logWriter.println(logline);
		logWriter.println("-----------------------------------------------------------------");
		logWriter.flush();
	}
}
