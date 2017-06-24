package computergraphics.exercises;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;

import com.jogamp.opengl.GL2;

import computergraphics.datastructures.mesh.TriangleMesh;
import computergraphics.math.Vector;
import computergraphics.misc.Scene;
import computergraphics.datastructures.mesh.Volume;
import computergraphics.rendering.Shader;
import computergraphics.scenegraph.INode.RenderMode;
import computergraphics.scenegraph.TriangleMeshNode;

public class Exercise6_1 extends Scene {
	private static final long serialVersionUID = 8141036480333465137L;

	private Volume volume;
	private Vector eye;

	public Exercise6_1() {
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
			List<TriangleMesh> tma = volume.getOrderedByEye("x", eye);
			
			for (int i = 0; i < tma.size(); i++) {
				TriangleMeshNode tn = new TriangleMeshNode(tma.get(i));
				getRoot().addChild(tn);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		new Exercise6_1();
	}
}
