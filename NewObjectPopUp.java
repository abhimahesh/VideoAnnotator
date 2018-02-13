import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Window.Type;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JRadioButton;

public class NewObjectPopUp extends JFrame implements ActionListener{

	private JPanel contentPane;
	
	public final String[] categories= new String[]{"Person", "Car", "Bus", "Tractor", "MotorCycle","Three Wheeler","Rickshaw", "Bicycle","Cart"};
	public final JComboBox cb_category = new JComboBox(categories);
	public final JComboBox cb_make = new JComboBox();
	public final JComboBox cb_model = new JComboBox();
	
	public ComboBoxModel[] make = new ComboBoxModel[9];
	public ComboBoxModel[][] model = new ComboBoxModel[15][27];
	public JTextField tf_number;
	public JRadioButton rdbtnNewObObjectOcculded ;
	arrObjects arrOb = null;
	BaseFrame bf = null;
	MainFrame mf  = null;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					NewObjectPopUp frame = new NewObjectPopUp();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
	}

	/**
	 * Create the frame.
	 * @param mfLocal 
	 * @param bfLocal 
	 * @param arrOb 
	 */
	public NewObjectPopUp(BaseFrame bfLocal, MainFrame mfLocal, arrObjects arrObLocal) {
		bf = bfLocal;
		mf = mfLocal;
		arrOb = arrObLocal;
		setAlwaysOnTop(true);
		setTitle("New Object");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(10, 10, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		cb_category.setBounds(147, 31, 167, 24);
		cb_make.setBounds(147, 67, 167, 24);
		cb_model.setBounds(147, 103, 167, 24);

		make[0] = new DefaultComboBoxModel(new String[] {"None"});
		make[1] = new DefaultComboBoxModel(new String[] {"None","Maruti", "Hyundai", "Mahindra","Tata Motors","Honda","Toyota","Renault","Ford","Nissan","Volkswagen","Audi","BMW","Cheverolet","Fiat","Jeep","Datsun","Jaguar","Land Rover", "Mercedes-Benz","Mitsubishi","Skoda","Ferrari","Aston Martin","Bugatti","Bentley","Volvo"});
		make[2] = new DefaultComboBoxModel(new String[] {"None"});
		make[3] = new DefaultComboBoxModel(new String[] {"None"});
		make[4] = new DefaultComboBoxModel(new String[] {"None"});
		make[5] = new DefaultComboBoxModel(new String[] {"None"});
		make[6] = new DefaultComboBoxModel(new String[] {"None"});
		make[7] = new DefaultComboBoxModel(new String[] {"None"});
		make[8] = new DefaultComboBoxModel(new String[] {"None"});
		
		model[1][0] = new DefaultComboBoxModel(new String[] {"None"});
		model[1][1] = new DefaultComboBoxModel(new String[] {"None","800","Alto K10", "Alto 800", "WagonR", "Swift", "Swift DZire", "Omni", "Eeco", "Gypsy", "Ertiga", "Celerio", "Ciaz", "Vitara Brezza", "Baleno", "Ignis", "S-Cross"});
		model[1][2] = new DefaultComboBoxModel(new String[] {"None","Eon", "i10", "i20", "Xcent", "Verna", "Elantra", "Creta", "Tucson"});
		model[1][3] = new DefaultComboBoxModel(new String[] {"None","Major", "Bolero", "Scorpio", "Thar", "Xylo", "Quanto", "Verito", "Verito Vibe", "Genio", "XUV500", "e2o", "TUV300", "KUV100", "NuvoSport"});
		model[1][4] = new DefaultComboBoxModel(new String[] {"None","Nano", "Indica", "Vista", "Indigo", "Manza", "Indigo CS", "Sumo", "Movus", "Venture", "Safari", "Xenon", "Aria", "Zest", "Bolt", "Tiago", "Tigor", "Hexa"});
		model[1][5] = new DefaultComboBoxModel(new String[] {"None", "Brio", "Jazz", "Amaze", "BR-V", "City","Accord"});
		model[1][6] = new DefaultComboBoxModel(new String[] {"None","Etios Liva", "Etios", "Corolla Altis", "Innova Crysta", "Fortuner", "Camry"});
		model[1][7] = new DefaultComboBoxModel(new String[] {"None","Pulse", "Duster", "Scala", "Kwid", "Lodgy"});
		model[1][8] = new DefaultComboBoxModel(new String[] {"None","Figo", "Ecosport", "Endeavour", "Figo Aspire"});
		model[1][9] = new DefaultComboBoxModel(new String[] {"None","Micra", "Sunny", "Terrano"});
		model[1][10] = new DefaultComboBoxModel(new String[] {"None","Polo", "Cross Polo", "Vento", "Jetta", "Ameo", "Tiguan"," Beetle"});
		model[1][11] = new DefaultComboBoxModel(new String[] {"None","A3", "A4", "A6", "Q3", "Q5"});
		model[1][12] = new DefaultComboBoxModel(new String[] {"None","1 Series", "3 Series", "3 Series GT", "5 Series", "7 Series", "X1", "X3", "X5"});
		model[1][13] = new DefaultComboBoxModel(new String[] {"None","Spark", "Beat", "Sail", "Cruze", "Tavera", "Enjoy"});
		model[1][14] = new DefaultComboBoxModel(new String[] {"None","Punto", "Linea", "Avventura", "Urban Cross"});
		model[1][15] = new DefaultComboBoxModel(new String[] {"None","Compass"});
		model[1][16] = new DefaultComboBoxModel(new String[] {"None", "Go" , "Go+" , "Redi-Go"});
		model[1][17] = new DefaultComboBoxModel(new String[] {"None","XE", "XF", "XJ"});
		model[1][18] = new DefaultComboBoxModel(new String[] {"None","Freelander", "Range Rover"});
		model[1][19] = new DefaultComboBoxModel(new String[] {"None","C-Class", "E-Class", "M-Class", "GL-Class", "S-Class"});
		model[1][20] = new DefaultComboBoxModel(new String[] {"None","Pajero"});
		model[1][21] = new DefaultComboBoxModel(new String[] {"None","Rapid", "Octavia", "Yeti", "Superb", "Kodiaq"});
		model[1][22] = new DefaultComboBoxModel(new String[] {"None","California", "458 Italia", "F12", "FF"});
		model[1][23] = new DefaultComboBoxModel(new String[] {"None","Vantage", "Vanquish", "Rapide", "Virage", "DB9", "DBS", "One-77"});
		model[1][24] = new DefaultComboBoxModel(new String[] {"None","Veyron"});
		model[1][25] = new DefaultComboBoxModel(new String[] {"None","Arnage", "Azure", "Brooklands", "Continental GT", "Continental Flying Spur", "Mulsanne"});
		model[1][26] = new DefaultComboBoxModel(new String[] {"None","V40", "S60", "S80", "XC60", "S90", "XC90"});
		
		
	
		for(int tmp=0;tmp<categories.length;tmp++) {
			for(int tmp2=0;tmp2<(make[tmp]).getSize();++tmp2) {
				if(tmp!=1) {
					model[tmp][tmp2]= new DefaultComboBoxModel(new String[] {"None"});
				}
			}
		}
		int indexCategory = 0;
		for(int z = 0;z<categories.length;++z) {
			if(categories[z].equals(bf.contentVehLbl.getText().toString())) {
				indexCategory = z;
				break;
			}
		}
		cb_category.setSelectedIndex(indexCategory);
		
		int indexMake = 0;
		for(int z = 0;z<make[indexCategory].getSize();++z) {
			if(make[indexCategory].getElementAt(z).equals(bf.contentMakeLbl.getText().toString())) {
				indexMake = z;
				break;
			}
		}
		cb_make.setModel(make[indexCategory]);
		cb_make.setSelectedIndex(indexMake);
		int indexModel = 0;
		for(int z = 0;z<model[indexCategory][indexMake].getSize();++z) {
			if(model[indexCategory][indexMake].getElementAt(z).equals(bf.contentModelLbl.getText().toString())) {
				indexModel = z;
				break;
			}
		}
		cb_model.setModel(model[indexCategory][indexMake]);
		cb_model.setSelectedIndex(indexModel);
		
		contentPane.add(cb_category);
		contentPane.add(cb_make);
		contentPane.add(cb_model);
		
		tf_number = new JTextField();
		tf_number.setToolTipText("Enter Car Number");
		tf_number.setBounds(147, 139, 167, 19);
		contentPane.add(tf_number);
		tf_number.setColumns(10);
		tf_number.setText(bf.contentNumberTf.getText());
//		if(cb_category.getSelectedIndex()!=1) {
//			tf_number.setText("Not Required");
//			tf_number.setEnabled(false);
//		}
//		else { 
//			tf_number.setEnabled(true);
//		}
				
		JLabel lblCategory = new JLabel("Category :");
		lblCategory.setBounds(29, 36, 89, 15);
		contentPane.add(lblCategory);
		
		JLabel lblMake = new JLabel("Make  :");
		lblMake.setBounds(29, 72, 89, 15);
		contentPane.add(lblMake);
		
		JLabel lblModel = new JLabel("Model  :");
		lblModel.setBounds(29, 108, 89, 15);
		contentPane.add(lblModel);
		
		JLabel lblCarNumber = new JLabel("Car number  :");
		lblCarNumber.setBounds(29, 141, 111, 15);
		contentPane.add(lblCarNumber);
		
		JButton btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mf.oimg=mf.deepCopy(mf.setimg);
				String stmp[] = new String[] {(String)cb_category.getSelectedItem(), (String) cb_make.getSelectedItem(),(String) cb_model.getSelectedItem(),(String) tf_number.getText().toUpperCase(), String.valueOf(rdbtnNewObObjectOcculded.isSelected())};
				Vector tmp = new Vector(Arrays.asList(stmp)); 
				
				Vector<String> input = new Vector<String>();
				bf.uniqueBoxId = bf.uniqueBoxId + 1;
				input.add(stmp[0]);input.add(stmp[1]);input.add(stmp[2]);input.add(stmp[3]);input.add(stmp[4]);
				input.add("" + bf.uniqueBoxId);
				//Sending data of this object to arrObjects class
//				System.out.println(bf.pos+"    :     "+mf.rect);
				arrOb.recvObj(bf.pos, mf.rect, input);
				
				//Setting label on top of rectangle 
				Graphics2D g = mf.setimg.createGraphics();
				g.drawImage(mf.oimg,0,0,null);
				Vector<Vector<String>> tmpProperty = new Vector<Vector<String>>(); tmpProperty.add(input);
				mf.setLabelOnRect(0,tmpProperty, mf.rect,g);
				mf.oimg=mf.deepCopy(mf.setimg);
				ImageIcon ic=new ImageIcon(mf.setimg);		
				mf.imglbl.setIcon(ic);
				g.dispose();
				//End of setting label on rectangle
				
//				int cur_frame = bf.pos;
//				
//				int cur_box_in_cur_frame = arrOb.boundingBoxes.get(cur_frame).size()-1;
//				Vector<Integer>tmp_vec = new Vector<Integer>();
//				tmp_vec.add(cur_frame); tmp_vec.add(cur_box_in_cur_frame);
//				bf.msg.addElement(tmp_vec);
//				
				
				//creating output_java file.txt file to be accessed from python tracker
				//Track object only if user wants to track it otherwise not
				if(bf.chckbxDeleteAndRetrack.isSelected()) {
					tracking trkObjThread = new tracking(bf.loadPath, bf,mf.rect , bf.pos,4, input);
					trkObjThread.start();
				}
				
				//Remaining section of NewObject
				mf.resetImg();						
				dispose();
				bf.frame.setEnabled(true);
				
				bf.contentVehLbl.setText("Vehicle Type");
				bf.contentMakeLbl.setText("Vehicle Make");
				bf.contentModelLbl.setText("Vehicle Model");
				bf.contentNumberTf.setText("");
				bf.rdbtnObjectOccluded.setSelected(false);
				
			}
			
		});
		btnOk.setBounds(203, 202, 81, 25);
		contentPane.add(btnOk);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			   public void actionPerformed(ActionEvent e){
			      dispose();
			      bf.frame.setEnabled(true);
			   }
			});
		btnCancel.setBounds(110, 202, 81, 25);
		contentPane.add(btnCancel);
		
		rdbtnNewObObjectOcculded = new JRadioButton("Object Occulded");
		rdbtnNewObObjectOcculded.setBounds(134, 166, 149, 23);
		contentPane.add(rdbtnNewObObjectOcculded);
		if(bf.rdbtnObjectOccluded.isSelected())
			rdbtnNewObObjectOcculded.setSelected(true);
		
		
		cb_category.addActionListener(this);
		cb_make.addActionListener(this);
		cb_model.addActionListener(this);
		
		addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                e.getWindow().dispose();
                bf.frame.setEnabled(true);
            }
        });
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JComboBox combo = (JComboBox) e.getSource();
		String selectedItem = (String) combo.getSelectedItem();
		
		cb_make.setModel(make[cb_category.getSelectedIndex()]);
		cb_model.setModel(model[cb_category.getSelectedIndex()][cb_make.getSelectedIndex()]);
	
//		if(cb_category.getSelectedIndex()!=1) {
//			tf_number.setText("Not Required");
//			tf_number.setEnabled(false);
//		}
//		else {
//			if(tf_number.getText().equals("Not Required"))
//				tf_number.setText("");
//			tf_number.setEnabled(true);
//		}
		
		if(tf_number.getText().equals("Not Required"))
			tf_number.setText("");

	}
}
