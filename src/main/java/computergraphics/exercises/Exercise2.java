package computergraphics.exercises;

import java.awt.event.KeyEvent;

import com.jogamp.opengl.GL2;

import computergraphics.datastructures.mesh.ObjReader;
import computergraphics.datastructures.mesh.TriangleMesh;
import computergraphics.math.Vector;
import computergraphics.misc.Scene;
import computergraphics.rendering.Shader;
import computergraphics.scenegraph.INode.RenderMode;
import computergraphics.scenegraph.TranslationNode;
import computergraphics.scenegraph.TriangleMeshNode;


public class Exercise2 extends Scene {
	private static final long serialVersionUID = 8141036480333465137L;

	private TranslationNode translation;
	private TriangleMesh tri = new TriangleMesh();

	public Exercise2() {
		// Timer timeout and shader mode (PHONG, TEXTURE, NO_LIGHTING)
		super(16, Shader.ShaderMode.PHONG, RenderMode.REGULAR);
//		super(16, Shader.ShaderMode.TEXTURE, RenderMode.REGULAR);
	}

	@Override
	public void setupScenegraph(GL2 gl) {
		// Setup scene after OpenGL is ready
		getRoot().setLightPosition(new Vector(0, 2, 0));
		getRoot().setAnimated(true);
		
		
//		tri.addVertex(new Vector(-1, 0, 0));
//		tri.addVertex(new Vector(0, 2, 0));
//		tri.addVertex(new Vector(1, 0, 0));
		
//	    tri.addVertex(new Vector(-0.5, -0.5, -0.5));
//	    tri.addVertex(new Vector(0.5, -0.5, -0.5));
//	    tri.addVertex(new Vector(0.5, 0.5, -0.5));
//	    tri.addVertex(new Vector(-0.5, 0.5, -0.5));
//	    tri.addVertex(new Vector(-0.5, -0.5, 0.5));
//	    tri.addVertex(new Vector(0.5, -0.5, 0.5));
//	    tri.addVertex(new Vector(0.5, 0.5, 0.5));
//	    tri.addVertex(new Vector(-0.5, 0.5, 0.5));
	    
//	    tri.addVertex(new Vector(-0.5, -0.5, -0.5));
//	    tri.addVertex(new Vector(0.5, -0.5, -0.5));
//	    tri.addVertex(new Vector(0.5, -0.5, 0.5));
//	    tri.addVertex(new Vector(-0.5, -0.5, 0.5));
//	    tri.addVertex(new Vector(-0.5, 0.5, -0.5));
//	    tri.addVertex(new Vector(0.5, 0.5, -0.5));
//	    tri.addVertex(new Vector(0.5, 0.5, 0.5));
//	    tri.addVertex(new Vector(-0.5, 0.5, 0.5));
	    
	    
		
//	    tri.addTriangle(0, 2, 1);
//	    tri.addTriangle(0, 3, 2);
//	    tri.addTriangle(1, 2, 5);
//	    tri.addTriangle(5, 2, 6);
//	    tri.addTriangle(5, 6, 4);
//	    tri.addTriangle(4, 6, 7);
//	    tri.addTriangle(4, 7, 0);
//	    tri.addTriangle(0, 7, 3);
//	    tri.addTriangle(3, 7, 2);
//	    tri.addTriangle(2, 7, 6);
//	    tri.addTriangle(1, 4, 0);
//	    tri.addTriangle(1, 5, 4);

		
		
		
//	    tri.addVertex(new Vector(0.75, 0, 0));
//	    tri.addVertex(new Vector(0, 1.5, 0));
//	    tri.addVertex(new Vector(-0.75, 0, 0));
//				
//	    tri.addTriangle(0, 1, 2);
//	    tri.computeTriangleNormals();
	    
//	    System.out.println(tri.getTriangle(0).getNormal());
		

		ObjReader objReader = new ObjReader();
		objReader.read("meshes/cow.obj", tri);
		
		TriangleMeshNode tn = new TriangleMeshNode(tri);
		translation = new TranslationNode(new Vector(0, 0.24, 0));
		translation.addChild(tn);
		getRoot().addChild(translation);


		
		// Floor geometry
//		FloorNode floorNode = new FloorNode(2);
//		getRoot().addChild(floorNode);
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
		new Exercise2();
	}
}
