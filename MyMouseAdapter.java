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

import javax.swing.JLabel;
import javax.swing.SwingUtilities;


public class MyMouseAdapter implements MouseListener, MouseMotionListener {
	MainFrame mf;
	BaseFrame bff;
	Rectangle area;
	Point start = new Point();
	int prex,prey;
	boolean onLeft=false,onLeftTop=false,onTop=false,onTopRight=false;
	boolean onRight=false,onRightBottom=false,onBottom=false,onBottomLeft=false;
	public MyMouseAdapter(MainFrame m, BaseFrame bf) {
		mf=m;
		bff = bf;
	}
	@Override
	public void mouseDragged(MouseEvent me) {		
		Point end = me.getPoint();
		mf.drag=true;
		//System.out.println(onLeft+" "+onLeftTop);
		if(!mf.drawn||!mf.drag){
			mf.rect = new Rectangle(start, new Dimension(end.x-start.x, end.y-start.y));
            mf.drawBox();
            mf.imglbl.repaint();
		}
		else if(onLeft || onLeft || onLeftTop || onTop || onTopRight || onRight || onRightBottom || onBottom || onBottomLeft) {
			mf.resizeRect(end);
			mf.imglbl.repaint();
		}
		else if(mf.rect.contains(end)){
			mf.updateBox(end,prex,prey);
			mf.imglbl.repaint();			
		} 
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
		if(SwingUtilities.isRightMouseButton(me)&&(mf.rect==null||mf.rect.contains(p)))
			mf.resetImg();			
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
	public void mousePressed(MouseEvent me) {
		Point end=me.getPoint();	
		if(mf.drawn&&mf.drag&&mf.rect.contains(end)){
			prex=mf.rect.x-end.x;
			prey=mf.rect.y-end.y;
			mf.updateBox(end,prex,prey);
			mf.imglbl.repaint();
		}		
		//System.out.println("pressed");
	}

	@Override
	public void mouseReleased(MouseEvent me) {		//
		Point end=me.getPoint();
		onLeft=false;
		onLeftTop=false;
		mf.drawn=true;
		if(mf.drawn&&mf.drag&&mf.rect.contains(me.getX(),me.getY())){
			prex=mf.rect.x-end.x;
			prey=mf.rect.y-end.y;
			mf.updateBox(end,prex,prey);
			mf.imglbl.repaint();
			//updateloc
		}
		
	}
	
}
