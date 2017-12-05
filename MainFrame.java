import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;


public class MainFrame {	
	JLabel imglbl;
	Rectangle rect;
	BaseFrame bff = null;
	BufferedImage oimg,setimg;
	boolean drawn,drag;
	MyMouseAdapter mma;
	
	public MainFrame(BaseFrame bf) {
		bff = bf;
		mma=new MyMouseAdapter(this,bf);
		imglbl = bf.imageViewLabel;
		addMouseListener();
	}
	public BufferedImage toBufferedImage(Image img,int x,int y){
	    if (img instanceof BufferedImage)
	    	return (BufferedImage) img;	    
	    BufferedImage bimage = new BufferedImage(x, y, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(img,0,0, x, y, null);
	    bGr.dispose();
	    return bimage;
	}	
	public BufferedImage deepCopy(BufferedImage bi) {
		 ColorModel cm = bi.getColorModel();
		 boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		 WritableRaster raster = bi.copyData(null);
		 return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}

	void getImage(String pathName){
		oimg = null;
		try {
		    oimg = ImageIO.read(new File(pathName));
		    int a = imglbl.getWidth(), b = imglbl.getHeight();
		    oimg = toBufferedImage(oimg.getScaledInstance(a, b,Image.SCALE_SMOOTH),a,b);
		    //System.out.println(oimg.getWidth()+" "+oimg.getHeight());
		} catch (IOException e) {
		    e.printStackTrace();
		}
		//return toBufferedImage(oimg.getScaledInstance(a, b,Image.SCALE_SMOOTH),a,b);
	}
	void addImage(String nameToPass){		
		getImage(nameToPass);
//		imglbl.setIcon(icon);	
//		oimg=null;
//		try {
//			oimg = new BufferedImage(nameToPass.getIconWidth(),nameToPass.getIconHeight(),BufferedImage.TYPE_INT_RGB);
//			System.out.println(oimg.getWidth()+" "+oimg.getHeight());
//		}catch(Exception e) {
//			System.out.println("Error in addImage in MainFrame"+e);
//		}
//		setimg=deepCopy(oimg);
//		Graphics g = setimg.createGraphics();
//		// paint the Icon to the BufferedImage.
//		nameToPass.paintIcon(null, g, 0,0);
//		imglbl.setIcon(nameToPass);
//		g.dispose();
//		//ImageIcon ic=new ImageIcon(setimg);
		setimg=deepCopy(oimg);
		//pastiing boxes from xml in that frame
		Graphics2D g = setimg.createGraphics();
		g.drawImage(oimg,0,0,null);
		int s = bff.arrOb.boundingBoxes.get(bff.pos).size();
		Rectangle rect = null;
		Vector <Rectangle> recVec = bff.arrOb.boundingBoxes.get(bff.pos);
		for(int i = 0;i<s;++i) {
			rect = recVec.get(i);
			if(rect!=null){
				g.setColor(Color.RED);
		        g.drawRect(rect.x, rect.y, rect.width,rect.height);        
		        g.setColor(new Color(255,255,255,150));
		        g.fill(rect);
			}
		}
		oimg=deepCopy(setimg);
		ImageIcon ic=new ImageIcon(setimg);		
		imglbl.setIcon(ic);
		g.dispose();
	}

	public void drawBox(){	
		Graphics2D g = setimg.createGraphics();
		g.drawImage(oimg,0,0,null);

		if(rect!=null){
			g.setColor(Color.RED);
	        g.drawRect(rect.x, rect.y, rect.width,rect.height);        
	        g.setColor(new Color(255,255,255,150));
	        g.fill(rect);
		}
        g.dispose();
	}
	public void resizeRect(Point p) {
		int nx=0,ny=0,nw=0,nh=0;
		if(mma.onLeftTop) {
			int dx=0, dy=0;
			dx = p.x-rect.x;
			dy = p.y-rect.y;
			nx = rect.x+dx; ny = rect.y+dy; nw = rect.width-dx; nh = rect.height-dy;
		}
		else if(mma.onTop) {
			int dy = p.y- rect.y;
			nx = rect.x; ny = rect.y+dy; nw = rect.width; nh = rect.height-dy; 
		}
		else if(mma.onTopRight) {
			int dx,dy;
			dx=p.x-rect.x;
			dy=p.y-rect.y;
			nx = rect.x;
			ny= rect.y+dy;
			nw = dx;
			nh = rect.height-dy;
		}
		else if(mma.onRight) {
			nx = rect.x;  ny = rect.y; nw = p.x-rect.x; nh = rect.height;
		}
		else if(mma.onRightBottom) {
			nx = rect.x;  ny = rect.y; nw = p.x-rect.x; nh = p.y-rect.y;
		}
		else if(mma.onBottom) {
			nx = rect.x;  ny = rect.y; nw = rect.width; nh = p.y-rect.y;
		}
		else if(mma.onBottomLeft){
			int dx = p.x - rect.x, dy = p.y-rect.y;
			nx = rect.x+dx;  ny = rect.y; nw = rect.width-dx; nh = dy<0?-1*dy:dy;
		}
		else if(mma.onLeft){
			int dx = rect.x-p.x;
			nx = p.x; ny= rect.y; nw = rect.width+dx; nh = rect.height;
		}
		rect.setBounds(nx,ny,nw,nh);
		Graphics2D g = setimg.createGraphics();
		g.drawImage(oimg,0,0,null);
		g.setColor(Color.RED);
		g.drawRect(rect.x, rect.y, rect.width,rect.height);                
        g.setColor(new Color(255,255,255,150));
        g.fill(rect);
        g.dispose();
		
	}
	public void resetImg(){
		Graphics2D g = setimg.createGraphics();
		g.drawImage(oimg,0,0,null);
		rect=null;
		drawn=false;
		drag=false;
	}
	public void updateBox(Point p,int xx,int yy){
		//if(p.x+x>=0&&p.y+y>=0){
			rect.setLocation(p.x+xx,p.y+yy);
			Graphics2D g = setimg.createGraphics();
			g.drawImage(oimg,0,0,null);
			g.setColor(Color.RED);
	        g.drawRect(rect.x, rect.y, rect.width,rect.height); 

	        //new added @amahesh
	       
	        int pre = 2;
	        int x = rect.x; int y = rect.y;
	        Rectangle r1=new Rectangle(x-pre, y+pre, 2*pre, rect.height-2*pre);
			Rectangle r2=new Rectangle(x+pre, y-pre, rect.width-2*pre, 2*pre);
			Rectangle r3=new Rectangle(x+rect.width-pre, y+pre,  2*pre,rect.height-2*pre);
			Rectangle r4=new Rectangle(x+pre, y+rect.height-pre, rect.width-2*pre ,2*pre);
			
	        g.setColor(Color.CYAN);
	        g.fill(r4);
	        g.fill(r1); g.fill(r2);g.fill(r3);
	        pre = 2;
			//corners rectangles
			Rectangle rc1=new Rectangle(x-pre, y-pre, 2*pre, 2*pre);
			Rectangle rc2=new Rectangle(x+rect.width-pre, y-pre, 2*pre, 2*pre);
			Rectangle rc3=new Rectangle(x+rect.width-pre, y+rect.height-pre,2*pre, 2*pre);
			Rectangle rc4=new Rectangle(x-pre, y+rect.height-pre, 2*pre ,2*pre);
			g.setColor(Color.MAGENTA);
	        g.fill(rc4);
	        g.fill(rc1); g.fill(rc2);g.fill(rc3);
//	        //
	        
	        g.setColor(new Color(255,255,255,150));
	        g.fill(rect);
	        g.dispose();
		//}
	}
	void addMouseListener(){
		imglbl.addMouseListener(mma);
		imglbl.addMouseMotionListener(mma);
	}
	void addButtonListener(){

	}
	public static void main(String[] args) {
//		mf.control();
	}	

}
