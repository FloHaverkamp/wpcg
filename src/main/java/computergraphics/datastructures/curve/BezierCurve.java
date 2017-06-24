package computergraphics.datastructures.curve;

import java.util.ArrayList;

import computergraphics.math.MathHelpers;
import computergraphics.math.Vector;

public class BezierCurve {
	
	public int value = -1;
	public ArrayList<Vector> controllpoints = new ArrayList<Vector>();
	
	
	public void addcp(Vector cp) {
		controllpoints.add(cp);
		value++;
	}
	
	public void deletecp(int i) {
		controllpoints.remove(i);
		value--;
	}
	
	public Vector comupte(double t) {
		Vector v1 = controllpoints.get(0).multiply((MathHelpers.over(value, 0)) * Math.pow(t, 0) * Math.pow((1 - t), value - 0));
		Vector v2;
		for (int i = 1; i <= value; i++) {
			v2 = controllpoints.get(i).multiply((MathHelpers.over(value, i)) * Math.pow(t, i) * Math.pow((1 - t), value - i));
			v1 = v1.add(v2);
		}
		return v1;
	}

}
