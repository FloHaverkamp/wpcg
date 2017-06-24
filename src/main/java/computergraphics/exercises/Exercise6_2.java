package computergraphics.exercises;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.jogamp.opengl.GL2;

import computergraphics.datastructures.mesh.TriangleMesh;
import computergraphics.datastructures.mesh.Volume;
import computergraphics.math.Matrix;
import computergraphics.math.Vector;
import computergraphics.misc.Scene;
import computergraphics.rendering.Shader;
import computergraphics.scenegraph.INode.RenderMode;
import computergraphics.scenegraph.TriangleMeshNode;
import computergraphics.scenegraph.VolumeNode;

public class Exercise6_2 extends Scene {
	private static final long serialVersionUID = 8141036480333465137L;
	private Volume volume;
	private VolumeNode volumeNode;
	private Vector eye;

	public Exercise6_2() {
		// Timer timeout and shader mode (PHONG, TEXTURE, NO_LIGHTING)
		super(16, Shader.ShaderMode.TEXTURE, RenderMode.REGULAR);
	}

	@Override
	public void setupScenegraph(GL2 gl) {
		// Setup scene after OpenGL is ready
		getRoot().setLightPosition(new Vector(0, 2, 0));
		try {
			eye = getRoot().getCamera().getEye();
			volume = new Volume("engine", gl);
			volumeNode = new VolumeNode(volume, eye);
			createTriangleMeshNodes("x");
			getRoot().addChild(volumeNode);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//	private List<TriangleMeshNode> createChildrenList() {
//		List<TriangleMeshNode> children = new ArrayList<>();
//		List<TriangleMeshNode> childrenX = createTriangleMeshNodes("x");
//		List<TriangleMeshNode> childrenY = createTriangleMeshNodes("y");
//		List<TriangleMeshNode> childrenZ = createTriangleMeshNodes("z");
//		
//		return children;
//	}

	/**
	 * creates triangleNodes and adds them to VolumeNode as children
	 * 
	 * @param axis
	 * @return list of TriangleMeshNodes for given axis
	 */
	private List<TriangleMeshNode> createTriangleMeshNodes(String axis) {
		List<TriangleMeshNode> nodes = new ArrayList<>();
		List<TriangleMesh> triangleMeshes = volume.getTriangleMeshes().get(axis);
		for (TriangleMesh tM : triangleMeshes) {
			TriangleMeshNode node = new TriangleMeshNode(tM);
			volumeNode.addChild(axis, node);
			volumeNode.addChild(node);
			nodes.add(node);
		}
		return nodes;
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
		new Exercise6_2();
	}
}
