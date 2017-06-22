package computergraphics.exercises;

import java.awt.event.KeyEvent;

import com.jogamp.opengl.GL2;

import computergraphics.datastructures.mesh.ObjReader;
import computergraphics.datastructures.mesh.TriangleMesh;
import computergraphics.math.Vector;
import computergraphics.misc.Scene;
import computergraphics.rendering.Shader;
import computergraphics.rendering.Texture;
import computergraphics.scenegraph.INode.RenderMode;
import computergraphics.scenegraph.TranslationNode;
import computergraphics.scenegraph.TriangleMeshNode;


public class Exercise3 extends Scene {
	private static final long serialVersionUID = 8141036480333465137L;

	private TranslationNode translation;
	private TranslationNode translation1;
	private TriangleMesh tri = new TriangleMesh();
	private TriangleMesh tri1 = new TriangleMesh();
	private Texture tex = new Texture("textures/lego.png");
	private Texture tex1 = new Texture("textures/legofloor.png");
	private Texture tex2 = new Texture("textures/box.jpg");

	public Exercise3() {
		// Timer timeout and shader mode (PHONG, TEXTURE, NO_LIGHTING)
//		super(16, Shader.ShaderMode.PHONG, RenderMode.REGULAR);
		super(16, Shader.ShaderMode.TEXTURE, RenderMode.REGULAR);
	}

	@Override
	public void setupScenegraph(GL2 gl) {
		// Setup scene after OpenGL is ready
		getRoot().setLightPosition(new Vector(0, 2, 0));
		
		
		ObjReader objReader = new ObjReader();
		objReader.read("meshes/square.obj", tri);
		tri.setTexture(tex);
		
		TriangleMeshNode tn = new TriangleMeshNode(tri);
		translation = new TranslationNode(new Vector(0, 0.5, 0));
		translation.addChild(tn);
		getRoot().addChild(translation);
		
		
		ObjReader objReader1 = new ObjReader();
		objReader1.read("meshes/floor.obj", tri1);
		tri1.setTexture(tex1);
		
		TriangleMeshNode tn1 = new TriangleMeshNode(tri1);
		translation1 = new TranslationNode(new Vector(0, 0, 0));
		translation1.addChild(tn1);
		getRoot().addChild(translation1);
		
		System.out.println(getRoot().children);
		
//		ObjReader objReader = new ObjReader();
//		objReader.read("meshes/box.obj", tri);
//		tri.setTexture(tex2);
//		
//		TriangleMeshNode tn = new TriangleMeshNode(tri);
//		translation = new TranslationNode(new Vector(0, 0.5, 0));
//		translation.addChild(tn);
//		getRoot().addChild(translation);
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
		new Exercise3();
	}
}
