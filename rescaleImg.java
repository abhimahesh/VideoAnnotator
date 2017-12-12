import java.util.Vector;

public class rescaleImg {
	double SH;
	double SW;
	double IH;
	double IW;
	double sx,sy;
	public rescaleImg(double a,double b ,double c ,double d) {
		SH = a;
		SW = b;
		IH = c;
		IW = d;
		calScale();
	}
	public void calScale() {
		sx = SW/IW;
		sy = SH/IH;
//		System.out.println(SH);
//		System.out.println(SW);
//		System.out.println(IH);
//		System.out.println(IW);
//		System.out.println(sx);
//		System.out.println(sy);
	}
	public Vector<Double> coorForXML(double x , double y) {
		Vector <Double> tmp = new Vector <Double>();
//		System.out.println(x*sx);
//		System.out.println(y*sy);
		tmp.add(x/sx);
		tmp.add(y/sy);
		return tmp;
	}
	
	public Vector<Double> coorFromXML(double x , double y) {
		Vector <Double> tmp = new Vector <Double>();
//		System.out.println(x/sx);System.out.println(y/sy);
		tmp.add(x*sx);
		tmp.add(y*sy);
		return tmp;
	}
	
}
