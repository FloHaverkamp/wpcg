/**
* Prof. Philipp Jenke
 * Hochschule für Angewandte Wissenschaften (HAW), Hamburg
 * 
 * Base framework for "WP Computergrafik".
 */
package computergraphics.scenegraph;

import com.jogamp.opengl.GL2;

import computergraphics.math.MathHelpers;
import computergraphics.math.Matrix;
import computergraphics.math.Vector;

/**
 * Scene graph node which scales all its child nodes.
 * 
 * @author Philipp Jenke
 */
public class RotationNode extends InnerNode {

	/**
	 * Scaling factors in x-, y- and z-direction.
	 */
	private Matrix rotationMatrix;
	private Vector axis;

	/**
	 * Constructor.
	 */
	
	public RotationNode(double angle, Vector axis) {
		rotationMatrix = Matrix.createRotationMatrix4GL(axis, MathHelpers.degree2radiens(angle));
		this.axis = axis;
	}

	
	public void calculate(double angle) {
		rotationMatrix = Matrix.createRotationMatrix4GL(axis, MathHelpers.degree2radiens(angle));
	}

	public void traverse(GL2 gl, RenderMode mode, Matrix modelMatrix) {
		super.traverse(gl, mode, rotationMatrix.multiply(modelMatrix));
	}

	public void timerTick(int counter) {
		super.timerTick(counter);
	}
}
