package computergraphics.scenegraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.jogamp.opengl.GL2;

import computergraphics.datastructures.mesh.TriangleMesh;
import computergraphics.datastructures.mesh.Volume;
import computergraphics.math.Matrix;
import computergraphics.math.Vector;

public class VolumeNode extends InnerNode {

	/**
	 * List of child nodes. the children-List contains the children of all Axes
	 * in the following Order: x1,y1,z1, x2,y2,z2, x3,...
	 */
	public List<INode> childrenX = new ArrayList<INode>();
	public List<INode> childrenXReversed = new ArrayList<INode>();

	public List<INode> childrenY = new ArrayList<INode>();
	public List<INode> childrenYReversed = new ArrayList<INode>();

	public List<INode> childrenZ = new ArrayList<INode>();
	public List<INode> childrenZReversed = new ArrayList<INode>();

	private Volume volume;

	public VolumeNode(Volume volume) {
		this.volume = volume;
	}

	/**
	 * this method returns an ordered list of the textureStack according to the
	 * position of the eye.
	 * 
	 * @param axis
	 * @param eye
	 * @return true if first plane is nearer to eye than last one
	 */
	private boolean shouldRenderBackToFront(String axis, Vector eye) {
		List<Vector> centers = volume.getCenters().get(axis);
		boolean backToFront = false;
		// build connection vectors with first and last plane
		Vector first = centers.get(0);
		Vector last = centers.get(centers.size() - 1);
		Vector eyeFirst = first.subtract(eye);
		Vector eyeLast = last.subtract(eye);

		// calculate length of both vectors and compare
		// squared root (x^2 + y^2 + z^2)
		double lengthEyeFirst = Math
				.sqrt(Math.pow(eyeFirst.x(), 2) + Math.pow(eyeFirst.y(), 2) + Math.pow(eyeFirst.z(), 2));
		double lengthEyeLast = Math
				.sqrt(Math.pow(eyeLast.x(), 2) + Math.pow(eyeLast.y(), 2) + Math.pow(eyeLast.z(), 2));

		if (lengthEyeFirst - lengthEyeLast < 0) {
			// first Plane is nearer to eye than last
			// --> last plane has to be computed first
			backToFront = true;
		}
		return backToFront;
	}

	@Override
	public void traverse(GL2 gl, RenderMode mode, Matrix modelMatrix) {
		super.traverse(gl, mode, modelMatrix);
	}

	@Override
	public void timerTick(int counter) {
	}

	/**
	 * Add new child node.
	 **/
	public void addChild(String axis, INode child) {
		child.setParentNode(this);
		switch (axis) {
		case "x":
			childrenX.add(child);
			childrenXReversed.add(0, child);
			break;
		case "y":
			childrenY.add(child);
			childrenYReversed.add(0, child);
			break;
		case "z":
			childrenZ.add(child);
			childrenZReversed.add(0, child);
			break;
		}
	}
	
	public void setDirection(String axis, Vector eye){
		if(shouldRenderBackToFront(axis, eye)){
			System.out.println("back to front");
			setBackToFront(axis);
		} else {
			System.out.println("front to Back");
			setFrontToBack(axis);
		}
	}

	private void setBackToFront(String axis) {
		switch(axis) {
		case "x":
			children = childrenXReversed;
			break;
		case "y":
			children = childrenYReversed;
			break;
		case "z":
			children = childrenZReversed;
			break;
		}
	}

	private void setFrontToBack(String axis) {
		switch(axis) {
		case "x":
			children = childrenX;
			break;
		case "y":
			children = childrenY;
			break;
		case "z":
			children = childrenZ;
			break;
		}
	}
}
