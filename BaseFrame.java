/**
 * 
 * @Author Abhishek Maheshwari
 * Git : abhimahesh
 * 
 */

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.List;
import java.awt.PopupMenu;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Button;
import java.awt.Color;
import javax.swing.JSlider;
import java.awt.Dimension;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Timer;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JLayeredPane;

public class BaseFrame implements ActionListener, MouseListener {

	public JFrame frame;
//	public static String [] msg = new String[] {"None","None","None","None"};
	public static Vector<Vector<Integer>> msg = new Vector<Vector<Integer>>();
	public static Vector cb_items = new Vector() ;
	public static DefaultComboBoxModel mdl = new DefaultComboBoxModel(cb_items);
	
	private JTextField contentNumberTf;
	public JButton btnNewObject ;
	public JButton btnSAVE ;
	private Resizable res;
	private JButton btnExit ;
	public JButton btnPausePlay;
	public JPanel panel_viewer; 
	private JPanel area ;
	public JLabel imageViewLabel ;
	int width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() -60;
	int height = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight() -60;
	javax.swing.Timer tm ;
	public int numOfFrames = 1;
	String[] imageList;
	
	public arrObjects arrOb = null;
	public reloadArray reloadOb = null;
	JButton btnLoad;	
	JSlider slider ;
	int pos = 0;
	public JLabel lblFrame;
	MainFrame mf=null;
	
	int slider_MIN = 0;
	int slider_MAX = 1;
	int slider_INIT = 0;
	
	public JLabel contentVehLbl ;
	public JLabel contentMakeLbl ;
	public JLabel contentModelLbl ;
	public JButton btnEdit;
	public JButton btnUpdate;
	public rescaleImg rescaleObj = null;
	
	public String loadPath = null;
	public  String xmlPath = null;
	public String savePath = null;
	private JFileChooser jfileChooserXML;
	private JTextField speed;
	private JTextField mcFrmTxtField;
	//public JFileChooser fc = new JFileChooser();
	//public JPanel lBoxPanel;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BaseFrame window = new BaseFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public BaseFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		slider_MIN = 1;
		slider_INIT = 1;
		pos=0;
		frame = new JFrame();
		frame.setResizable(false);
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] gs = ge.getScreenDevices();
		int screen = 0;
		//System.out.println(""+gs.length);
		if(gs.length>screen) {
			gs[screen].setFullScreenWindow(frame);
			height= gs[screen].getDefaultConfiguration().getBounds().height;
			width = gs[screen].getDefaultConfiguration().getBounds().width;
			height = (int)(0.9*height);
			width = (int)(0.9*width);
			//frame.setLocation(gs[screen].getDefaultConfiguration().getBounds().x, gs[screen].getDefaultConfiguration().getBounds().y);
			frame.setLocation(gs[0].getDefaultConfiguration().getBounds().x, gs[0].getDefaultConfiguration().getBounds().y);
			
		}
		else {
			gs[0].setFullScreenWindow(frame);
			height= gs[0].getDefaultConfiguration().getBounds().height;
			width = gs[0].getDefaultConfiguration().getBounds().width;
			height = (int)(0.9*height);
			width = (int)(0.9*width);
			frame.setLocation(gs[0].getDefaultConfiguration().getBounds().x, gs[0].getDefaultConfiguration().getBounds().y);
		}
		
		frame.setBounds(0, 0,width, height);
//		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		panel_viewer = new JPanel();
		panel_viewer.setBounds(12, 12, (int)(width*0.8), (int)(height*0.81));
		panel_viewer.setBackground(Color.LIGHT_GRAY);
		frame.getContentPane().add(panel_viewer);
		panel_viewer.setLayout(null);
		
		imageViewLabel = new JLabel("Image View Label");
		imageViewLabel.setBounds(0,0, (int)(width*0.8), (int)(height*0.81));
		panel_viewer.add(imageViewLabel);
		//showImage(pos);
		
//		lBoxPanel= new JPanel();
//		lBoxPanel.setBounds(0, 0, 1048, 690);
//		panel_viewer.add(lBoxPanel);
//		lBoxPanel.setLayout(null);
		

		//New area to add Bounding box
//		area = new JPanel();
        //area.setBackground(Color.RED);
        
		JPanel panel_control = new JPanel();
		panel_control.setBounds(12, (int)(height*0.81)+25, (int)(width*0.8), (int)(height*0.11));
		panel_control.setBackground(Color.GRAY);
		frame.getContentPane().add(panel_control);
		panel_control.setLayout(null);
		
		slider = new JSlider(JSlider.HORIZONTAL,slider_MIN, slider_MAX, slider_INIT);
		slider.setPreferredSize(new Dimension(200, 5));
		slider.setBounds(12, 12, (int)(width*0.8)-30, 50);
		slider.setBackground(Color.GRAY);
		slider.setMinimum(1);
		Font font = new Font("Serif", Font.ITALIC, 2);
		slider.setFont(font);
		slider.addChangeListener(new ChangeListener() { 
			@Override
			public void stateChanged(ChangeEvent ev) {
				int tmp = slider.getValue()-1;
				if(tmp<numOfFrames)
					pos = tmp;
				if(pos<numOfFrames)
					showImage(pos);	
				lblFrame.setText("Frame : "+(pos+1));
			}
		});
		slider.setMajorTickSpacing(100);
		slider.setMinorTickSpacing(25);
		slider.setPaintTicks(true);
		
		panel_control.add(slider);
		slider.disable();
		
		JPanel panel_subControl = new JPanel();
		panel_subControl.setBounds(12, 47, (int)(width*0.8)-30, 50);
		panel_subControl.setBackground(Color.GRAY);
		panel_control.add(panel_subControl);
		panel_subControl.setLayout(null);
		
		tm = new javax.swing.Timer(500, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
//				System.out.println("abhishekjjjjjjjj"+pos);
				slider.setValue(pos+1);
				if(speed.getText().matches("[0-9]+") && speed.getText().length() >=1)
					pos = pos+Integer.parseInt(speed.getText().toString());
				else {
					pos = pos+5;
					speed.setText("5");
				}
				if(pos>numOfFrames) {
					pos=0;
					tm.stop();
					btnPausePlay.setText("Play");
				}
			}
		});

		btnPausePlay = new JButton("Play");
		btnPausePlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
//				btnPausePlay.setLabel("Pause");
				if(btnPausePlay.getText().equals("Play")) {
					tm.start();
					btnPausePlay.setText("Pause");
				}
				else if(btnPausePlay.getText().equals("Pause")) {
					tm.stop();
					btnPausePlay.setText("Play");
				}
			}
		});
		btnPausePlay.setBounds(92, 12, 98, 25);
		panel_subControl.add(btnPausePlay);
		btnPausePlay.setEnabled(false);
		
		JButton button = new JButton("<<");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(speed.getText().matches("[0-9]+") && speed.getText().length() >=1)
					pos = pos- Integer.parseInt(speed.getText().toString());
				else {
					pos = pos-5;
					speed.setText("5");
				}
				if(pos<=0) {
					pos = 0;
				}

				slider.setValue(pos+1);
			}
		});
		button.setBounds(202, 12, 55, 25);
		panel_subControl.add(button);
		
		JButton button_1 = new JButton("<");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pos = pos-1;
				if(pos<=0) {
					pos =0;
				}
				//showImage(pos);
				slider.setValue(pos+1);
			}
		});
		button_1.setBounds(254, 12, 44, 25);
		panel_subControl.add(button_1);
		
		JButton button_2 = new JButton(">");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pos = pos+1;
				if(pos>=numOfFrames) {
					pos = numOfFrames-1;
				}
				//showImage(pos);
				slider.setValue(pos+1);
			}
		});
		button_2.setBounds(298, 12, 44, 25);
		panel_subControl.add(button_2);
		
		JButton button_3 = new JButton(">>");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(speed.getText().matches("[0-9]+") && speed.getText().length() >=1)
					pos = pos+ Integer.parseInt(speed.getText().toString());
				else {
					pos = pos+5;
					speed.setText("5");
				}
				if(pos>=numOfFrames) {
					pos = numOfFrames-1;
				}
				slider.setValue(pos+1);
				//showImage(pos);
			}
		});
		button_3.setBounds(338, 12, 55, 25);
		panel_subControl.add(button_3);
		
		btnLoad = new JButton("Load");
		btnLoad.addActionListener(this);
		btnLoad.setBounds(12, 12, 77, 25);
		panel_subControl.add(btnLoad);
		
		jfileChooserXML = new JFileChooser();
		jfileChooserXML.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		btnSAVE = new JButton("SAVE");
		btnSAVE.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(xmlPath!=null) {
		    		jfileChooserXML.setCurrentDirectory(new File(xmlPath+"/"));
		    	}
				int result = jfileChooserXML.showOpenDialog(null);
				switch (result) {
			    case JFileChooser.APPROVE_OPTION:
			    	File f = jfileChooserXML.getSelectedFile();
					xmlPath= f.getAbsolutePath();
					arrOb.genrateXML(rescaleObj);
			    	break;
			    case JFileChooser.CANCEL_OPTION:
//			      System.out.println("Cancel or the close-dialog icon was clicked");
			      break;
			    case JFileChooser.ERROR_OPTION:
//			      System.out.println("Error");
			      break;
			    }
			}
		});
		btnSAVE.setBounds(645, 12, 77, 25);
		panel_subControl.add(btnSAVE);
		btnSAVE.setEnabled(false);
		
		btnExit = new JButton("Exit");
		btnExit.addActionListener(this);
		btnExit.setBounds(734, 12, 77, 25);
		panel_subControl.add(btnExit);
		
		lblFrame = new JLabel("Frame");
		lblFrame.setBounds(511, 12, 116, 25);
		lblFrame.setForeground(Color.WHITE);
		panel_subControl.add(lblFrame);
		
		speed = new JTextField();
		speed.setText("5");
		speed.setBounds(405, 12, 38, 25);
		speed.setBackground(Color.yellow);
		panel_subControl.add(speed);
		speed.setColumns(10);

		
//		JSlider slider = new JSlider();
//		slider.setPreferredSize(new Dimension(1000, 16));
//		slider.setMinimumSize(new Dimension(1000, 16));
//		slider.setBackground(Color.GRAY);
//		panel_control.add(slider);
		
		
		JPanel panel_content = new JPanel();
		panel_content.setBounds((int)(width*0.8)+25, 12, (int)(width*0.18)-12, (int)(0.93*height));
		panel_content.setBackground(Color.GRAY);
		frame.getContentPane().add(panel_content);
		panel_content.setLayout(null);
		
		btnNewObject = new JButton("+");
		btnNewObject.addActionListener(this);
		btnNewObject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnNewObject.setBounds(12, 12, 44, 25);
		panel_content.add(btnNewObject);

		cb_items.add("None");
//		cb_items.add("Car 2");
				
		JComboBox comboBox = new JComboBox(mdl);
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				int index = comboBox.getSelectedIndex();
//				System.out.println(index);
				if(index!=0) {
					int f = msg.get(index-1).get(0);
					int b = msg.get(index-1).get(1);
	//				System.out.println("f : "+f+",   b :  "+b);
					contentVehLbl.setText((String)arrOb.properties.get(f).get(b).get(0));
					contentMakeLbl.setText((String)arrOb.properties.get(f).get(b).get(1));
					contentModelLbl.setText((String)arrOb.properties.get(f).get(b).get(2));
					contentNumberTf.setText((String)arrOb.properties.get(f).get(b).get(3));
					btnEdit.enable();
					btnUpdate.enable();
				}
				else {
					contentVehLbl.setText("Vehicle Type");
					contentMakeLbl.setText("Vehicle Make");
					contentModelLbl.setText("Vehicle Model");
					contentNumberTf.setText("Vehicle Number");
					btnEdit.disable();
					btnUpdate.disable();
				}
				contentNumberTf.setEditable(false);
			}
		});
		comboBox.setBounds(68, 12, 136, 24);
		panel_content.add(comboBox);
		
		JPanel panel = new JPanel();
		panel.setBounds(12, 49, 192, 219);
		panel.setBackground(Color.LIGHT_GRAY);
		panel_content.add(panel);
		panel.setLayout(null);
		
		contentVehLbl = new JLabel("Vehicle Type");
		contentVehLbl.setBounds(12, 12, 168, 27);
		panel.add(contentVehLbl);
		
		contentMakeLbl = new JLabel("Vehicle Make");
		contentMakeLbl.setBounds(12, 42, 168, 27);
		panel.add(contentMakeLbl);
		
		contentModelLbl = new JLabel("Vehicle Model");
		contentModelLbl.setBounds(12, 71, 168, 27);
		panel.add(contentModelLbl);
		
		contentNumberTf = new JTextField("Vehicle Number");
		contentNumberTf.setBounds(12, 100, 172, 27);
		contentNumberTf.setBackground(Color.WHITE);
		panel.add(contentNumberTf);
		contentNumberTf.setColumns(10);
		contentNumberTf.setEditable(false);;
		
		JRadioButton rdbtnObjectOccluded = new JRadioButton("Object Occluded");
		rdbtnObjectOccluded.setBackground(Color.LIGHT_GRAY);
		rdbtnObjectOccluded.setBounds(12, 129, 172, 23);
		panel.add(rdbtnObjectOccluded);
		
		JRadioButton rdbtnOutOfViewframe = new JRadioButton("Out of ViewFrame");
		rdbtnOutOfViewframe.setBackground(Color.LIGHT_GRAY);
		rdbtnOutOfViewframe.setBounds(12, 159, 172, 23);
		panel.add(rdbtnOutOfViewframe);
		
		btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int index = comboBox.getSelectedIndex();
				if(index!=0) {
					contentNumberTf.setEditable(true);
					btnUpdate.enable();
				}
				else {
					contentNumberTf.setEditable(false);
					btnUpdate.disable();
				}
			}
		});
		btnEdit.setBounds(12, 190, 77, 17);
		panel.add(btnEdit);
		btnEdit.disable();
		
		btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = comboBox.getSelectedIndex();
				if(cb_items.size()==1) {
					contentVehLbl.setText("Vehicle Type");
					contentMakeLbl.setText("Vehicle Make");
					contentModelLbl.setText("Vehicle Model");
					contentNumberTf.setText("Vehicle Number");
				}
				else if(index!=0) {
					int f = msg.get(index-1).get(0);
					int b = msg.get(index-1).get(1);
	//				System.out.println("f : "+f+",   b :  "+b);
					String new_num = contentNumberTf.getText().toString().toUpperCase();
					arrOb.properties.get(f).get(b).set(3, new_num);
					btnUpdate.disable();
				}else {
					contentVehLbl.setText("Vehicle Type");
					contentMakeLbl.setText("Vehicle Make");
					contentModelLbl.setText("Vehicle Model");
					contentNumberTf.setText("Vehicle Number");
				}
				contentNumberTf.setEditable(false);
				
			}
		});
		btnUpdate.setBounds(90, 190, 90, 17);
		panel.add(btnUpdate);
		
		JLabel lblContactAmaheshiitkacin = new JLabel("maheshwari.nitrr@gmail.com");
		lblContactAmaheshiitkacin.setBounds(12, 866, 206, 15);
		panel_content.add(lblContactAmaheshiitkacin);
		lblContactAmaheshiitkacin.setForeground(Color.WHITE);
		
		JLabel lblAbhishekMaheshwari = new JLabel("Abhishek Maheshwari");
		lblAbhishekMaheshwari.setBounds(37, 854, 167, 15);
		panel_content.add(lblAbhishekMaheshwari);
		lblAbhishekMaheshwari.setForeground(Color.WHITE);
		btnUpdate.setEnabled(false);
		
		JPanel manualPanel = new JPanel();
		manualPanel.setBounds(22, 406, 182, 137);
		panel_content.add(manualPanel);
		manualPanel.setLayout(null);
		
		JLabel lblManualControl = new JLabel("Manual Control");
		lblManualControl.setBounds(33, 12, 117, 15);
		manualPanel.add(lblManualControl);
		
		JLabel lblResetFrame = new JLabel("Clear Frame");
		lblResetFrame.setBounds(12, 39, 93, 15);
		manualPanel.add(lblResetFrame);
		
		mcFrmTxtField = new JTextField();
		mcFrmTxtField.setBounds(105, 37, 33, 19);
		manualPanel.add(mcFrmTxtField);
		mcFrmTxtField.setColumns(10);
		
		JButton btnmcFrmaeClear = new JButton("Go");
		btnmcFrmaeClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int x = 1;
				if(mcFrmTxtField.getText().matches("[0-9]+") && mcFrmTxtField.getText().length() >=1) {
					slider.setValue(x+1);
					x =  Integer.parseInt(mcFrmTxtField.getText().toString());
					arrOb.boundingBoxes.get(x-1).clear();
					arrOb.properties.get(x-1).clear();
					slider.setValue(x);
				}
			}
		});
		btnmcFrmaeClear.setBounds(66, 63, 53, 25);
		manualPanel.add(btnmcFrmaeClear);
	}
	public void getImages() {
		File file = null;
		try {
//			file = new File("/home/amahesh/sample");
			file = new File(loadPath);
			//file = new File(getClass().getResource("/sample").getFile());
		}
		catch (Exception e) {
			System.out.println("Error in getImages()");
		}
		File[] fileList = file.listFiles();
		imageList = new String[fileList.length];
		int n = imageList.length;
		for(int i =0;i<n;++i) {
			imageList[i]=fileList[i].getName();
		//	System.out.println(imageList[i]);
		}
		Arrays.sort(imageList);
		numOfFrames = n;
	}
	public void showImage(int index) {
		String imageName = imageList[index];
//		ImageIcon myImage = new ImageIcon("/home/amahesh/sample/"+imageName);
//		
//		Image img = myImage.getImage();
//		Image newImg = img.getScaledInstance(imageViewLabel.getWidth(), imageViewLabel.getHeight(), Image.SCALE_SMOOTH);
//		ImageIcon icon = new ImageIcon(newImg);
//		imageViewLabel.setIcon(icon);
//		String nameToPass = "/home/amahesh/sample/"+imageName;
		String nameToPass = loadPath+"/"+imageName;
		//System.out.println(""+imageName);
		if(mf==null) {
			try {
				mf=new MainFrame(this);
				mf.addImage(nameToPass);
			}catch(Exception e) {
				System.out.println("Error in initialize()");
			}
		}
		else {
			mf.rect = null;
			mf.oimg=null; mf.setimg=null;
			mf.drawn=false;mf.drag=false;
			mf.addImage(nameToPass);
		}
//		lBoxPanel.setOpaque(true);
//		lBoxPanel.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(btnNewObject )) {
	        if(mf.rect!=null){
				//System.out.println(mf.rect);
				frame.disable();
				NewObjectPopUp newOb = new NewObjectPopUp(this,mf,arrOb);
		        newOb.setLocation((int)(width/3), (int)(height/3));
		        newOb.setVisible(true);
		        // mf.resetImage() is called from OKbutton  ofNewObjectPopUp 
			}
		}
		else if(e.getSource().equals(btnLoad)) {
			if(pos==0) {
				frame.disable();
				ChooseFile chfOb = new ChooseFile(this);
				chfOb.setLocation((int)(width/3), (int)(height/3));
		        chfOb.setVisible(true);
			}
		}
		else if(e.getSource().equals(btnExit)) {
			System.exit(1);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		panel_viewer.requestFocus();
        res.repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
