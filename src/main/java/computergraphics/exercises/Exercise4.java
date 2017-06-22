package computergraphics.exercises;

import java.awt.event.KeyEvent;

import com.jogamp.opengl.GL2;

import computergraphics.datastructures.curve.BezierCurve;
import computergraphics.math.Vector;
import computergraphics.misc.Scene;
import computergraphics.rendering.Shader;
import computergraphics.scenegraph.BezierCurveNode;
import computergraphics.scenegraph.BezierCurveNode2;
import computergraphics.scenegraph.INode.RenderMode;


public class Exercise4 extends Scene {
	private static final long serialVersionUID = 8141036480333465137L;

	private BezierCurve bc = new BezierCurve();

	public Exercise4() {
		// Timer timeout and shader mode (PHONG, TEXTURE, NO_LIGHTING)
		super(16, Shader.ShaderMode.PHONG, RenderMode.REGULAR);
	}

	@Override
	public void setupScenegraph(GL2 gl) {
		// Setup scene after OpenGL is ready
		getRoot().setLightPosition(new Vector(0, 2, 0));
		
		bc.addcp(new Vector(-0.5, 0, 0));
		bc.addcp(new Vector(0, 0.5, 0));
		bc.addcp(new Vector(0.5, 0, 0));
		bc.addcp(new Vector(0.75, 0.75, 0));
		
		BezierCurveNode bcn = new BezierCurveNode(bc);
		bcn.comupteTangents();
		getRoot().addChild(bcn);	
		
		BezierCurveNode2 bcn1 = new BezierCurveNode2(bc);
//		bcn.comupteTangents();
		getRoot().addChild(bcn1);
	}

	@Override
	public void keyPressed(KeyEvent keyEvent) {
		
	}

	@Override
	public void timerTick(int counter) {
		
	}

	/**
	 * Program entry point.
	 */
	public static void main(String[] args) {
		new Exercise4();
	}
}
