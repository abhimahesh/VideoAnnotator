import javax.swing.JFrame;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Hashtable;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class ChooseFile extends JFrame implements ActionListener{
	private JPanel panel; 
	private JTextField txtSelectImageFile;
	private JButton btnLoad1;
	private JTextField txtSelectXmlFile;
	private JButton btnXml;
	private BaseFrame bf = null;
	private String filePath = null;
	private String xmlPath= null;
	private JButton btnOk ;
	private JButton btnCancel;
	private int resultFile = 1;
	private int resultXml = 1;
	private JCheckBox chkbx;
	private boolean selected = true;
	private JLabel lblXmlFilePath ;
	
	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					ChooseFile window = new ChooseFile();
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
	}
	public ChooseFile(BaseFrame bfOb) {
		bf = bfOb;
//		frame = new JFrame();
//		frame.setResizable(false);
//		frame.setBounds(0, 0,500, 400); 
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.getContentPane().setLayout(null);
//		
		setTitle("Select Directories : (Image and/or xml)");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(0,0, 500, 350);
		panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panel);
		panel.setLayout(null);
				
		txtSelectImageFile = new JTextField();
		txtSelectImageFile.setText("Select Image File Path");
		txtSelectImageFile.setBounds(12, 117, 334, 22);
		panel.add(txtSelectImageFile);
		txtSelectImageFile.setColumns(10);
		
		btnLoad1 = new JButton("Load");
		btnLoad1.addActionListener(this);
		btnLoad1.setBounds(358, 115, 117, 25);
		panel.add(btnLoad1);
		
		txtSelectXmlFile = new JTextField();
		txtSelectXmlFile.setText("Select XML file path");
		txtSelectXmlFile.setColumns(10);
		txtSelectXmlFile.setBounds(12, 210, 334, 22);
		panel.add(txtSelectXmlFile);
		
		btnXml = new JButton("XML");
		btnXml.setBounds(358, 208, 117, 25);
		panel.add(btnXml);
		btnXml.addActionListener(this);
		
		chkbx = new JCheckBox("Check this if want to start fresh");
		chkbx.setBounds(12, 167, 334, 23);
		chkbx.setSelected(true);
		chkbx.setBackground(Color.LIGHT_GRAY);
		chkbx.addActionListener(this);
		panel.add(chkbx);
		
		btnOk = new JButton("OK");
		btnOk.setBounds(109, 270, 117, 25);
		panel.add(btnOk);
		btnOk.addActionListener(this);
		
		btnCancel = new JButton("Cancel");
		btnCancel.setBounds(247, 270, 117, 25);
		panel.add(btnCancel);
		btnCancel.addActionListener(this);
		
		JLabel lblImageFilePath = new JLabel("Image File path :");
		lblImageFilePath.setBounds(12, 102, 183, 15);
		panel.add(lblImageFilePath);
		
		lblXmlFilePath = new JLabel("XML File path :");
		lblXmlFilePath.setBounds(12, 194, 183, 15);
		panel.add(lblXmlFilePath);
		
		btnXml.setEnabled(false);
		txtSelectXmlFile.setEnabled(false);
		lblXmlFilePath.setEnabled(false);
}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(btnLoad1)) {
			JFileChooser chooser = new JFileChooser();
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int result = chooser.showOpenDialog(null);
			resultFile = result;
			switch (result) {
		    case JFileChooser.APPROVE_OPTION: 
		    	File f = chooser.getSelectedFile();
				filePath= f.getAbsolutePath();
				txtSelectImageFile.setText(filePath); 
		      break;
		    case JFileChooser.CANCEL_OPTION:
//		    	System.out.println(result);
//		      System.out.println("Cancel or the close-dialog icon was clicked");
		      break;
		    case JFileChooser.ERROR_OPTION:
//		    	System.out.println(result);
//		      System.out.println("Error");
		      break;
		    }
		}
		else if(e.getSource().equals(btnXml)) {
			JFileChooser chooser = new JFileChooser();
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int result = chooser.showOpenDialog(null);
			resultXml= result;
			switch (result) {
		    case JFileChooser.APPROVE_OPTION:
		    	File f = chooser.getSelectedFile();
				xmlPath= f.getAbsolutePath();
				txtSelectXmlFile.setText(xmlPath);
		    	break;
		    case JFileChooser.CANCEL_OPTION:
//		      System.out.println("Cancel or the close-dialog icon was clicked");
		      break;
		    case JFileChooser.ERROR_OPTION:
//		      System.out.println("Error");
		      break;
		    }
		}
		else if(e.getSource().equals(btnOk)) {
			if((resultFile == 0 && resultXml==0) || (selected && resultFile==0)) {
				bf.loadPath = filePath;
				if(!selected)
					bf.xmlPath = xmlPath;
				else 
					bf.xmlPath = null;
				callingpartialBaseFunction();
			}
			else if((resultFile!=0 && resultXml != 0) || (!selected && resultXml!=0)) {
				JOptionPane.showMessageDialog(this,
					    "amahesh says : Either of the directories not selected.",
					    "Try again",
					    JOptionPane.ERROR_MESSAGE);
			}
			dispose();
			bf.frame.enable();	
//			System.out.println("abhishekkkkkkk");
		}
		else if(e.getSource().equals(btnCancel)) {
			bf.frame.enable();
//			callingpartialBaseFunction();
			dispose();
			bf.frame.enable();
		}
		else if(e.getSource().equals(chkbx)) {
			selected = chkbx.isSelected();
//			System.out.println(selected);
			btnXml.setEnabled(!selected);
			txtSelectXmlFile.setEnabled(!selected);
			lblXmlFilePath.setEnabled(!selected);
		}
	}
	private void callingpartialBaseFunction() {
//		System.out.println("abhishekkkkkkk2");
		bf.getImages();
		bf.arrOb = new arrObjects(bf.numOfFrames, bf);
		if(!selected)
			bf.reloadOb = new reloadArray(bf.numOfFrames,bf);
		
		double SH = bf.imageViewLabel.getHeight();
		double SW = bf.imageViewLabel.getWidth();
		String pathName = bf.loadPath+"/"+bf.imageList[0];
		BufferedImage bfimagetmp = null;
		double imgH = 1;
		double imgW = 1;
		try {
		 bfimagetmp= ImageIO.read(new File(pathName));
		 imgH = bfimagetmp.getHeight();
		 imgW = bfimagetmp.getWidth();
		} catch(Exception ev ) {
			System.out.println("Error in Load for sample Image to gret image H/W : "+ev);
		}
		bf.rescaleObj = new rescaleImg(SH,SW,imgH , imgW); //
		for(int i=0;i<bf.numOfFrames;++i) {
			bf.arrOb.addImageName(i , bf.imageList[i].split("\\.")[0]);
			if(!selected)
				bf.reloadOb.addImageName(i, bf.imageList[i].split("\\.")[0]);
		}
		if(!selected) {
			bf.reloadOb.readXML(bf.rescaleObj);
			bf.arrOb.boundingBoxes = bf.reloadOb.boundingBoxes;
			bf.arrOb.properties = bf.reloadOb.properties;
		}
		//showImage(pos);
		bf.slider_MAX = bf.numOfFrames;
		bf.slider.setMaximum(bf.numOfFrames);
		Hashtable labelTable = new Hashtable();
		labelTable.put( new Integer( 1 ), new JLabel("1") );
		for(int i =100;i<bf.slider_MAX;i = i+100) {
			labelTable.put( new Integer( i ), new JLabel(""+i) );
		}
		labelTable.put( new Integer( bf.slider_MAX ), new JLabel(""+bf.slider_MAX) );
		bf.slider.setLabelTable( labelTable );
		bf.slider.setPaintLabels(true);
		bf.slider.enable();
		bf.frame.enable();
		bf.btnSAVE.setEnabled(true);
		bf.btnPausePlay.setEnabled(true);
		
	}
}

