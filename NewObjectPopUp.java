import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Frame;

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
	
	private ComboBoxModel[] make = new ComboBoxModel[15];
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
		
		cb_make.setModel(make[0]);
		cb_model.setModel(model[1][0]);
		
		contentPane.add(cb_category);
		contentPane.add(cb_make);
		contentPane.add(cb_model);
		
		tf_number = new JTextField();
		tf_number.setToolTipText("Enter Car Number");
		tf_number.setBounds(147, 139, 167, 19);
		contentPane.add(tf_number);
		tf_number.setColumns(10);
		tf_number.disable();
		
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
				//BaseFrame.msg.addElement(tmp);

				int l = BaseFrame.mdl.getSize();
				int count=0;
				
				for(int i =0;i<l;++i) {
					if(BaseFrame.mdl.getElementAt(i).toString().split("_")[0].equals((String)  cb_category.getSelectedItem()))
						count++;
				}

				BaseFrame.mdl.addElement(""+((String)  cb_category.getSelectedItem())+"_"+(count+1));
				
//		        for(int i =0;i<4;++i)
//		        	System.out.println(stmp[i]);
//				System.out.println("aaaaaaaaaa");
				Vector<String> input = new Vector<String>();
				input.add(stmp[0]);input.add(stmp[1]);input.add(stmp[2]);input.add(stmp[3]);
				
//				System.out.println(bf.pos+"    :     "+mf.rect);
				arrOb.recvObj(bf.pos, mf.rect, input);
				
				int cur_frame = bf.pos;
				
				int cur_box_in_cur_frame = arrOb.boundingBoxes.get(cur_frame).size()-1;
				Vector<Integer>tmp_vec = new Vector<Integer>();
				tmp_vec.add(cur_frame); tmp_vec.add(cur_box_in_cur_frame);
				bf.msg.addElement(tmp_vec);
				
				mf.resetImg();						
				dispose();
				bf.frame.enable();
			}
			
		});
		btnOk.setBounds(203, 202, 81, 25);
		contentPane.add(btnOk);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			   public void actionPerformed(ActionEvent e){
			      dispose();
			      bf.frame.enable();
			   }
			});
		btnCancel.setBounds(110, 202, 81, 25);
		contentPane.add(btnCancel);
		
		
		cb_category.addActionListener(this);
		cb_make.addActionListener(this);
		cb_model.addActionListener(this);
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
			tf_number.disable();
		}
		else {
			tf_number.enable();
			tf_number.setText("");
		}
		
	}
}
