import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Timer;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import org.omg.CosNaming.IstringHelper;
import org.w3c.dom.css.Rect;


public class MyMouseAdapter implements MouseListener, MouseMotionListener {
	MainFrame mf;
	BaseFrame bff;
	Rectangle area;
	Point start = new Point();
	int prex,prey;
	boolean onLeft=false,onLeftTop=false,onTop=false,onTopRight=false;
	boolean onRight=false,onRightBottom=false,onBottom=false,onBottomLeft=false;
	Timer editboxtm;
	double starttime,endtime,holdtime;
	boolean holding = false;
	int pos_box;
	public MyMouseAdapter(MainFrame m, BaseFrame bf) {
		mf=m;
		bff = bf;
	}
	
	int inLeftRect(Point p) {
		int pre=15;
		int l=0,r=0,t=0,b=0;
		int x=mf.rect.x;
		int y=mf.rect.y;
		/*
		 * r1 = left side, r2 = top, r3 = right, r4 = bottom
		 * rc1 = topLeft, rc2 = topRight, rc3 = bottomRight, rc4 = bottomLeft
		 */
		Rectangle r1=new Rectangle(x-pre, y+pre, 2*pre, mf.rect.height-2*pre);
		Rectangle r2=new Rectangle(x+pre, y-pre, mf.rect.width-2*pre, 2*pre);
		Rectangle r3=new Rectangle(x+mf.rect.width-pre, y+pre,  2*pre,mf.rect.height-2*pre);
		Rectangle r4=new Rectangle(x+pre, y+mf.rect.height-pre, mf.rect.width-2*pre ,2*pre);
		
		//corners rectangles
		Rectangle rc1=new Rectangle(x-pre, y-pre, 2*pre, 2*pre);
		Rectangle rc2=new Rectangle(x+mf.rect.width-pre, y-pre, 2*pre, 2*pre);
		Rectangle rc3=new Rectangle(x+mf.rect.width-pre, y+mf.rect.height-pre,2*pre, 2*pre);
		Rectangle rc4=new Rectangle(x-pre, y+mf.rect.height-pre, 2*pre ,2*pre);

		if(r1.contains(p)) {
			l=1;
		}
		if(r2.contains(p)) {
			t=1; 
		}
		if(r3.contains(p)) {
			r=1;
		}
		if(r4.contains(p)) {
			b=1;
		}
		if(rc1.contains(p))
			return 1;		
		if(rc2.contains(p))
			return 3;
		if(rc3.contains(p))
			return 5;
		if(rc4.contains(p))
			return 7;
		if(t==1) return 2;
		if(r==1) return 4;
		if(b==1) return 6;
		if(l==1) return 8;
		return 0;
	}
	@Override
	public void mouseMoved(MouseEvent me) {
		start=me.getPoint();
		
		int line;
		if(mf.rect!=null) {
			int val = inLeftRect(start);
			switch(val) {
				case 1:
						//mf.setCursor(Cursor.NW_RESIZE_CURSOR);
						onLeftTop=true;
						break;
				case 2:	//mf.setCursor(Cursor.N_RESIZE_CURSOR);
						onTop=true;
						break;
				case 3:	//mf.setCursor(Cursor.NE_RESIZE_CURSOR);
						onTopRight=true;
						break;
				case 4:	//mf.setCursor(Cursor.E_RESIZE_CURSOR);
						onRight=true;
						break;
				case 5:	//mf.setCursor(Cursor.SE_RESIZE_CURSOR);
						onRightBottom=true;
						break;
				case 6:	//mf.setCursor(Cursor.S_RESIZE_CURSOR);
						onBottom=true;
						break;
				case 7:	//mf.setCursor(Cursor.SW_RESIZE_CURSOR);
						onBottomLeft=true;
						break;
				case 8:	//mf.setCursor(Cursor.W_RESIZE_CURSOR);
						onLeft=true;
						break;
				default:
						onLeft=false;onLeftTop=false;onTop=false;onTopRight=false;
						onRight=false;onRightBottom=false;onBottom=false;onBottomLeft=false;
						//mf.setCursor(Cursor.DEFAULT_CURSOR);
						break;

			}
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent me) {
		Point p=me.getPoint();
		if(SwingUtilities.isRightMouseButton(me)&&(mf.rect==null||mf.rect.contains(p))) {
			mf.resetImg();
			bff.contentVehLbl.setText("Vehicle Type");
			bff.contentMakeLbl.setText("Vehicle Make");
			bff.contentModelLbl.setText("Vehicle Model");
			bff.contentNumberTf.setText("Vehicle Number");
			bff.rdbtnObjectOccluded.setSelected(false);
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseDragged(MouseEvent me) {		
		Point end = me.getPoint();
		mf.drag=true;
		//System.out.println(onLeft+" "+onLeftTop);
		if(!mf.drawn||!mf.drag){
//			System.out.println("dragged");
			if(end.x-start.x >0 && end.y-start.y >0)
				mf.rect = new Rectangle(start, new Dimension(Math.abs(end.x-start.x), Math.abs(end.y-start.y)));
			else if(end.x-start.x <0 && end.y-start.y <0)
				mf.rect = new Rectangle(end, new Dimension(Math.abs(end.x-start.x), Math.abs(end.y-start.y)));
			else if(end.x-start.x <0 && end.y-start.y >0)
				mf.rect = new Rectangle(new Point(end.x,start.y), new Dimension(Math.abs(end.x-start.x), Math.abs(end.y-start.y)));
			else if(end.x-start.x >0 && end.y-start.y <0)
				mf.rect = new Rectangle(new Point(start.x,end.y), new Dimension(Math.abs(end.x-start.x), Math.abs(end.y-start.y)));
            mf.drawBox();
            mf.imglbl.repaint();
		}
		else if(onLeft || onLeft || onLeftTop || onTop || onTopRight || onRight || onRightBottom || onBottom || onBottomLeft) {
			mf.resizeRect(end);
			mf.imglbl.repaint();
		}
		else if(mf.rect.contains(end)){
//			System.out.println("moved in dragged");
//			System.out.println(mf.drawn+" :  "+ mf.drag+" : "+holding);
			mf.updateBox(end,prex,prey);
			mf.imglbl.repaint();			
		} 
		holding = false;
	}
	@Override
	public void mousePressed(MouseEvent me) {
		Point end=me.getPoint();	
		if(mf.drawn&&mf.drag&&mf.rect.contains(end)){
//			System.out.println("pressed");
			prex=mf.rect.x-end.x;
			prey=mf.rect.y-end.y;
			mf.updateBox(end,prex,prey);
			mf.imglbl.repaint();
		}	
		else if(mf.drawn && !mf.drag) {
			mf.drawn=false; mf.drag=false;
			if(!holding) {
//				System.out.println("reached here");
				holding = true;
				starttime = System.nanoTime();
			}
		}
		else if(!mf.drawn && !mf.drag) {
			if(!holding) {
//				System.out.println("reached here");
				holding = true;
				starttime = System.nanoTime();
			}
//			System.out.println("pressed");
		}
//		System.out.println("Pressed  "+mf.drawn+"  "+mf.drag+"  "+holding);
	}

	@Override
	public void mouseReleased(MouseEvent me) {		//
		Point end=me.getPoint();
		onLeft=false;
		onLeftTop=false;
		mf.drawn=true;
		if(mf.drawn&&mf.drag&&mf.rect.contains(me.getX(),me.getY())){
//			System.out.println("released");
			prex=mf.rect.x-end.x;
			prey=mf.rect.y-end.y;
			mf.updateBox(end,prex,prey);
			mf.imglbl.repaint();
			//updateloc
		}
		else if(!mf.drag && holding) {
				endtime = System.nanoTime();
				holdtime = (endtime - starttime) / Math.pow(10,9);
				if(holdtime>1) {
					pos_box = getSelected(end);
					//current box ID to be deleted or to be editied in all further frames wherever this object is found
					int currBoxID = Integer.parseInt(bff.arrOb.properties.get(bff.pos).get(pos_box).get(5));

					//Removing that selected rectangle
					Rectangle tmprect = null;
					tmprect= bff.arrOb.boundingBoxes.get(bff.pos).remove(pos_box);
					Vector<String> tmpProperty = bff.arrOb.properties.get(bff.pos).remove(pos_box);
					bff.showImage(bff.pos);
					mf.rect = tmprect;
					Point tmpPoint = new Point(mf.rect.x, mf.rect.y);
					mf.updateBox(tmpPoint,0,0);
			        mf.imglbl.repaint();
			        mf.drawn = true; mf.drag = true; holding = false;
//					System.out.println(holdtime+"  , "+pos_box+"  : "+mf.rect);
			        //Setting Edit control in the content control panel
			        
			        bff.contentVehLbl.setText(tmpProperty.get(0));
			        bff.contentMakeLbl.setText(tmpProperty.get(1));
			        bff.contentModelLbl.setText(tmpProperty.get(2));
			        bff.contentNumberTf.setText(tmpProperty.get(3));
			        if(tmpProperty.get(4).equals("true"))
			        	bff.rdbtnObjectOccluded.setSelected(true);
			        //Delete all further same tracked objects if user wants to (Indicated by bff.chckbxDeleteAndRetrack checkBox)
			        if(bff.chckbxDeleteAndRetrack.isSelected())
			        	deleteNextTracked(bff.pos, currBoxID);

				}
		}
		holding = false;
//		System.out.println("Released  "+mf.drawn+"  "+mf.drag+"  "+holding);
	}
	public void deleteNextTracked(int frameNum, int currBoxID) {
		boolean boxFoundFlag= false;
		for(int i =frameNum+1;i<bff.numOfFrames;i++) {
			Vector<Vector<String>> tmpAllBox = bff.arrOb.properties.get(i);
			for(int j = 0;j<tmpAllBox.size();++j) {
				if(currBoxID == Integer.parseInt(bff.arrOb.properties.get(i).get(j).get(5))) {
					 bff.arrOb.boundingBoxes.get(i).remove(j);
					 bff.arrOb.properties.get(i).remove(j);
					 boxFoundFlag= true;
				}
			}
			if(!boxFoundFlag) break;
			else boxFoundFlag = false;
		}
	}
	private int getSelected(Point end) {
		int nearest_box = 0,xc,yc;
		double dist = Double.MAX_VALUE,tmpdist;
		Rectangle tmprect = null;
		for(int j = 0 ; j < bff.arrOb.boundingBoxes.get(bff.pos).size() ; ++j) {
			tmprect = bff.arrOb.boundingBoxes.get(bff.pos).get(j);
			if(tmprect.contains(end)) {
				xc = tmprect.x + tmprect.width/2;
				yc = tmprect.y + tmprect.height/2;
				tmpdist = Math.pow((xc-end.getX()),2)+Math.pow(yc-end.getY(), 2);
				if(dist>tmpdist) {
					nearest_box = j;
					dist = tmpdist;
				}
			}
		}
		return nearest_box;
	}
//	
}
