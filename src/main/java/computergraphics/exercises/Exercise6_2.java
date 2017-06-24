package computergraphics.exercises;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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

	public Exercise6_2() {
		// Timer timeout and shader mode (PHONG, TEXTURE, NO_LIGHTING)
		super(16, Shader.ShaderMode.TEXTURE, RenderMode.REGULAR);
	}

	@Override
	public void setupScenegraph(GL2 gl) {
		// Setup scene after OpenGL is ready
		gl.glDisable(GL2.GL_CULL_FACE); // disable backface-Culling - which
										// doesn't work at all
		getRoot().setLightPosition(new Vector(0, 2, 0));
		try {
			volume = new Volume("engine", gl);
			volumeNode = new VolumeNode(volume);
			createTriangleMeshNodes("x");
			createTriangleMeshNodes("y");
			createTriangleMeshNodes("z");
			volumeNode.setDirection("x", getRoot().getCamera().getEye());
//			volumeNode.setDirection("y", getRoot().getCamera().getEye());
//			volumeNode.setDirection("z", getRoot().getCamera().getEye());
			getRoot().addChild(volumeNode);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * creates triangleNodes and adds them to VolumeNode as children
	 * 
	 * @param axis
	 */
	private void createTriangleMeshNodes(String axis) {
		List<TriangleMesh> triangleMeshes = volume.getTriangleMeshes().get(axis);
		for (TriangleMesh tM : triangleMeshes) {
			volumeNode.addChild(axis, new TriangleMeshNode(tM));
		}
	}

	/**
	 * probably a way to check for camera position?
	 */
	@Override
	public void redraw(GL2 gl) {
		volumeNode.setDirection("x", getRoot().getCamera().getEye());
//		volumeNode.setDirection("y", getRoot().getCamera().getEye());
//		volumeNode.setDirection("z", getRoot().getCamera().getEye());
		super.redraw(gl);
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
