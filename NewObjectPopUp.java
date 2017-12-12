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

public class NewObjectPopUp extends JFrame implements ActionListener{

	private JPanel contentPane;
	
	private final String[] categories= new String[]{"Person", "Car", "Bus", "Tractor", "Bike", "Bicycle"};
	public final JComboBox cb_category = new JComboBox(categories);
	public final JComboBox cb_make = new JComboBox();
	public final JComboBox cb_model = new JComboBox();
	
	private ComboBoxModel[] make = new ComboBoxModel[6];
	private ComboBoxModel[][] model = new ComboBoxModel[15][15];
	public JTextField tf_number;
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
		make[1] = new DefaultComboBoxModel(new String[] {"None","Maruti", "Hyundai", "Mahindra","Tata Motors","Honda","Toyota","Renault","Ford","Nissan","Volkswagen"});
		make[2] = new DefaultComboBoxModel(new String[] {"None","BUS 1", "BUS 2", "BUS 3"});
		make[3] = new DefaultComboBoxModel(new String[] {"None","Tractor 1"});
		make[4] = new DefaultComboBoxModel(new String[] {"None", "Bike 1"});
		make[5] = new DefaultComboBoxModel(new String[] {"None", "Bicycle 1"});
		
		model[1][0] = new DefaultComboBoxModel(new String[] {"None"});
		model[1][1] = new DefaultComboBoxModel(new String[] {"None","Alto 800", "WagonR","Swift","DZire"});
		model[1][2] = new DefaultComboBoxModel(new String[] {"None","Grand i10","Elite i20"});
		model[1][3] = new DefaultComboBoxModel(new String[] {"None","Scorpio","XUV 500"," KUV 100"});
		model[1][4] = new DefaultComboBoxModel(new String[] {"None","Tiago","Hexa","Nexon","Kite V",});
		model[1][5] = new DefaultComboBoxModel(new String[] {"None","Amaze","Brio","Accord"});
		model[1][6] = new DefaultComboBoxModel(new String[] {"None","Innova Crysta","Fortuner","Etios"});
		model[1][7] = new DefaultComboBoxModel(new String[] {"None"});
		model[1][8] = new DefaultComboBoxModel(new String[] {"None"});
		model[1][9] = new DefaultComboBoxModel(new String[] {"None"});
		model[1][10] = new DefaultComboBoxModel(new String[] {"None"});
		
	
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
		if(cb_category.getSelectedIndex()!=1) {
			tf_number.setText("Not Required");
			tf_number.setEnabled(false);
		}
		else { 
			tf_number.setEnabled(true);
		}
				
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
				String stmp[] = new String[] {(String)cb_category.getSelectedItem(), (String) cb_make.getSelectedItem(),(String) cb_model.getSelectedItem(),(String) tf_number.getText().toUpperCase()};
				Vector tmp = new Vector(Arrays.asList(stmp)); 

				Vector<String> input = new Vector<String>();
				input.add(stmp[0]);input.add(stmp[1]);input.add(stmp[2]);input.add(stmp[3]);
				
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
				mf.resetImg();						
				dispose();
				bf.frame.setEnabled(true);
				
				bf.contentVehLbl.setText("Vehicle Type");
				bf.contentMakeLbl.setText("Vehicle Make");
				bf.contentModelLbl.setText("Vehicle Model");
				bf.contentNumberTf.setText("Vehicle Number");
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
	
		if(cb_category.getSelectedIndex()!=1) {
			tf_number.setText("Not Required");
			tf_number.setEnabled(false);
		}
		else {
			if(tf_number.getText().equals("Not Required"))
				tf_number.setText("");
			tf_number.setEnabled(true);
		}
	}
}
