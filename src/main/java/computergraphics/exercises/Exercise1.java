/**
 * Prof. Philipp Jenke
 * Hochschule für Angewandte Wissenschaften (HAW), Hamburg
 * 
 * Base framework for "WP Computergrafik".
 */

package computergraphics.exercises;

import java.awt.event.KeyEvent;
import java.util.concurrent.ThreadLocalRandom;

import com.jogamp.opengl.GL2;

import computergraphics.datastructures.mesh.ObjReader;
import computergraphics.datastructures.mesh.TriangleMesh;
import computergraphics.math.Vector;
import computergraphics.misc.Scene;
import computergraphics.rendering.Shader;
import computergraphics.scenegraph.FloorNode;
import computergraphics.scenegraph.INode.RenderMode;
import computergraphics.scenegraph.RectangleNode;
import computergraphics.scenegraph.RotationNode;
import computergraphics.scenegraph.SphereNode;
import computergraphics.scenegraph.TranslationNode;
import computergraphics.scenegraph.TriangleMeshNode;

/**
 * Application for the first exercise.
 * 
 * @author Philipp Jenke
 */
public class Exercise1 extends Scene {
	private static final long serialVersionUID = 8141036480333465137L;

	private TranslationNode translation;
	private RotationNode rotation;
	private RotationNode rotation1;
	private RotationNode rotation2;
	private double angle = 0;
	private double angle2 = 0;
	private double x = 0;
	private double y = 1;
	private double z = 0;
	private boolean running = false;
	private TriangleMesh tri = new TriangleMesh();

	public Exercise1() {
		// Timer timeout and shader mode (PHONG, TEXTURE, NO_LIGHTING)
		super(16, Shader.ShaderMode.PHONG, RenderMode.REGULAR);
	}

	@Override
	public void setupScenegraph(GL2 gl) {
		// Setup scene after OpenGL is ready
		getRoot().setLightPosition(new Vector(0, 2, 0));
		getRoot().setAnimated(true);

		// variables needed to create the scene
		SphereNode sphereNode;
		RectangleNode rec;
		TranslationNode translation1;
		TranslationNode origin;

		// Tree geometry and positioning
		for (int i = 0; i < 100; i++) {

			x = ThreadLocalRandom.current().nextDouble(-1.9, 1.9);
			z = ThreadLocalRandom.current().nextDouble(-1.9, 1.9);

			translation = new TranslationNode(new Vector(x, 0.2, z));
			sphereNode = new SphereNode(0.1, 7);
			translation.addChild(sphereNode);
			getRoot().addChild(translation);

			translation = new TranslationNode(new Vector(x, 0.1, z));
			rec = new RectangleNode(0.03, 0.03, 0.1);
			translation.addChild(rec);
			getRoot().addChild(translation);
		}

		// Heli geometry and positioning
		x = ThreadLocalRandom.current().nextDouble(-1.5, 1.5);
		y = ThreadLocalRandom.current().nextDouble(0.5, 1.0);
		z = ThreadLocalRandom.current().nextDouble(-1.5, 1.5);

		// cockpit
		translation = new TranslationNode(new Vector(this.x, this.y, this.z));
		rotation2 = new RotationNode(angle2, new Vector(0, 1, 0));
		rotation2.addChild(translation);

		translation1 = new TranslationNode(new Vector(0, 0, 0));
		sphereNode = new SphereNode(0.1, 50);
		sphereNode.setColor(new Vector(1, 0, 0, 1));
		translation.addChild(translation1);
		translation1.addChild(sphereNode);

		translation1 = new TranslationNode(new Vector(0, 0, 0.03));
		sphereNode = new SphereNode(0.08, 20);
		sphereNode.setColor(new Vector(1, 1, 1, 1));
		translation.addChild(translation1);
		translation1.addChild(sphereNode);

		translation1 = new TranslationNode(new Vector(0, 0.04, -0.065));
		rec = new RectangleNode(0.04, 0.05, 0.04);
		rec.setColor(new Vector(1, 0, 0, 1));
		translation.addChild(translation1);
		translation1.addChild(rec);

		translation1 = new TranslationNode(new Vector(0, 0.05, -0.13));
		rec = new RectangleNode(0.03, 0.05, 0.03);
		rec.setColor(new Vector(1, 0, 0, 1));
		translation.addChild(translation1);
		translation1.addChild(rec);

		translation1 = new TranslationNode(new Vector(0, 0.06, -0.22));
		rec = new RectangleNode(0.02, 0.07, 0.02);
		rec.setColor(new Vector(1, 0, 0, 1));
		translation.addChild(translation1);
		translation1.addChild(rec);

		// Kufen
		translation1 = new TranslationNode(new Vector(0.05, -0.1, 0));
		rec = new RectangleNode(0.01, 0.10, 0.01);
		rec.setColor(new Vector(1, 1, 1, 1));
		translation.addChild(translation1);
		translation1.addChild(rec);

		translation1 = new TranslationNode(new Vector(-0.05, -0.1, 0));
		rec = new RectangleNode(0.01, 0.10, 0.01);
		rec.setColor(new Vector(1, 1, 1, 1));
		translation.addChild(translation1);
		translation1.addChild(rec);

		translation1 = new TranslationNode(new Vector(0, -0.1, 0));
		rec = new RectangleNode(0.04, 0.02, 0.01);
		rec.setColor(new Vector(1, 1, 1, 1));
		translation.addChild(translation1);
		translation1.addChild(rec);

		// Rotor
		translation1 = new TranslationNode(new Vector(0, 0.11, 0));
		rotation = new RotationNode(angle, new Vector(0, 1, 0));
		origin = new TranslationNode(new Vector(0, 0, 0));
		rec = new RectangleNode(0.015, 0.15, 0.007);
		rec.setColor(new Vector(1, 1, 1, 1));
		translation.addChild(translation1);
		translation1.addChild(rotation);
		rotation.addChild(origin);
		origin.addChild(rec);
		// getRoot().addChild(translation);

		rec = new RectangleNode(0.15, 0.015, 0.007);
		rec.setColor(new Vector(1, 1, 1, 1));
		origin.addChild(rec);

		// Heckrotor

		translation1 = new TranslationNode(new Vector(0.03, 0.06, -0.26));
		rotation1 = new RotationNode(angle, new Vector(1, 0, 0));
		origin = new TranslationNode(new Vector(0, 0, 0));
		rec = new RectangleNode(0.005, 0.01, 0.05);
		rec.setColor(new Vector(1, 1, 1, 1));
		translation.addChild(translation1);
		translation1.addChild(rotation1);
		rotation1.addChild(origin);
		origin.addChild(rec);

		rec = new RectangleNode(0.005, 0.05, 0.01);
		rec.setColor(new Vector(1, 1, 1, 1));
		origin.addChild(rec);

		getRoot().addChild(rotation2);

		
		
//		tri.addVertex(new Vector(-1, 0, 0));
//		tri.addVertex(new Vector(0, 2, 0));
//		tri.addVertex(new Vector(1, 0, 0));
		
//	    tri.addVertex(new Vector(1, 0, 0));
//	    tri.addVertex(new Vector(0, 2, 0));
//	    tri.addVertex(new Vector(-1, 0, 0));
	    
		
		
		
		
//		
//	    tri.addTriangle(0, 1, 2);
//	    tri.computeTriangleNormals();
	    
//	    System.out.println(tri.getTriangle(0).getNormal());
		
		
		
		
		
		
		
		ObjReader objReader = new ObjReader();
		objReader.read("meshes/cow.obj", tri);
		
		
		
		
		
//	    TranslationNode translation5 = new TranslationNode(new Vector(0, 1, 0));
//	    TriangleMeshNode tn = new TriangleMeshNode(tri);
//		translation5.addChild(tn);
//		translation1.addChild(translation5);
	    
		TriangleMeshNode tn = new TriangleMeshNode(tri);
		getRoot().addChild(tn);
		
		
		
		
		
		
		
		// Floor geometry
		FloorNode floorNode = new FloorNode(2);
		getRoot().addChild(floorNode);
	
		
		// TranslationNode cfTranslation = new TranslationNode(new Vector(0, 0,
		// 0));
		// CoordinateFrameNode cfNode = new CoordinateFrameNode();
		// cfTranslation.addChild(cfNode);
		// getRoot().addChild(cfTranslation);

		// Light geometry
		// TranslationNode lightTranslation = new
		// TranslationNode(getRoot().getLightPosition());
		// INode lightSphereNode = new SphereNode(0.1f, 10);
		// lightTranslation.addChild(lightSphereNode);
		// getRoot().addChild(lightTranslation);
	}

	@Override
	public void keyPressed(KeyEvent keyEvent) {
		char keyCode = keyEvent.getKeyChar();
		switch (keyCode) {
		case 's':
			this.running = true;
			break;
		case 'x':
			this.running = false;
			break;
		}
	}

	@Override
	public void timerTick(int counter) {
		rotation.calculate(angle);
		rotation1.calculate(angle);

		angle += 20;
		if (angle >= 360) {
			angle = 0;
		}
		
		if (running) {
			angle2 += 2.5;
			if (angle >= 360) {
				angle = 0;
			}
			rotation2.calculate(angle2);
		}
	}

	/**
	 * Program entry point.
	 */
	public static void main(String[] args) {
		new Exercise1();
	}
}
