package computergraphics.scenegraph;

import java.util.ArrayList;
import java.util.List;

import com.jogamp.opengl.GL2;

import computergraphics.datastructures.mesh.Triangle;
import computergraphics.datastructures.mesh.TriangleMesh;
import computergraphics.math.Matrix;
import computergraphics.math.Vector;
import computergraphics.rendering.RenderVertex;
import computergraphics.rendering.VertexBufferObject;

/**
 * Representation of a cuboid with different dimensions in x-, y- and
 * z-direction.
 *
 * @author Philipp Jenke
 */
public class TriangleMeshNode extends LeafNode {

	/**
	 * Cube side length
	 */
	private TriangleMesh tri;
	private Vector v0;
	private Vector v1;
	private Vector v2;
	private Vector t0;
	private Vector t1;
	private Vector t2;
	private Vector m;

	/**
	 * VBO.
	 */
	private VertexBufferObject vbo = new VertexBufferObject();
	private VertexBufferObject vbo1 = new VertexBufferObject();

	/**
	 * Constructor.
	 */
	public TriangleMeshNode(TriangleMesh mesh) {
		this.tri = mesh;
		tri.computeTriangleNormals();
		createVbo();
		 displayNormals();
	}

	private void createVbo() {
		List<RenderVertex> renderVertices = new ArrayList<RenderVertex>();

		Vector color = new Vector(1, 0, 0, 1);

		for (int i = 0; i < tri.triangles.size(); i++) {
//			System.out.println(tri.getTriangle(i).toString());
			v0 = tri.getVertex(tri.getTriangle(i).getVertexIndex(0)).getPosition();
			v1 = tri.getVertex(tri.getTriangle(i).getVertexIndex(1)).getPosition();
			v2 = tri.getVertex(tri.getTriangle(i).getVertexIndex(2)).getPosition();

//			System.out.println(tri.getTriangle(i).getTexCoordIndex(0));
			t0 = tri.getTextureCoordinate(tri.getTriangle(i).getTexCoordIndex(0));
			t1 = tri.getTextureCoordinate(tri.getTriangle(i).getTexCoordIndex(1));
			t2 = tri.getTextureCoordinate(tri.getTriangle(i).getTexCoordIndex(2));

			AddVertices(renderVertices, tri.getTriangle(i).getNormal(), color);
		}
		vbo.Setup(renderVertices, GL2.GL_TRIANGLES);
	}

	public void displayNormals() {
		List<RenderVertex> renderVertices1 = new ArrayList<RenderVertex>();
		Vector color = new Vector(0, 0, 1, 1);
		for (int i = 0; i < tri.triangles.size(); i++) {
			v0 = tri.getVertex(tri.getTriangle(i).getVertexIndex(0)).getPosition();
			v1 = tri.getVertex(tri.getTriangle(i).getVertexIndex(1)).getPosition();
			v2 = tri.getVertex(tri.getTriangle(i).getVertexIndex(2)).getPosition();
			m = (v0.add(v1).add(v2)).multiply(0.33333);

			renderVertices1.add(new RenderVertex(m, new Vector(0, 1, 0), color));
			renderVertices1.add(new RenderVertex(m.add(tri.getTriangle(i).getNormal()), new Vector(0, 1, 0), color));
		}
		vbo1.Setup(renderVertices1, GL2.GL_LINES);
	}

	/**
	 * Add 4 vertices to the array
	 */
	private void AddVertices(List<RenderVertex> renderVertices, Vector normal, Vector color) {
		renderVertices.add(new RenderVertex(v0, normal, color, t0));
		renderVertices.add(new RenderVertex(v1, normal, color, t1));
		renderVertices.add(new RenderVertex(v2, normal, color, t2));
		renderVertices.add(new RenderVertex(v0, normal, color));
		renderVertices.add(new RenderVertex(v1, normal, color));
		renderVertices.add(new RenderVertex(v2, normal, color));
	}

	@Override
	public void drawGL(GL2 gl, RenderMode mode, Matrix modelMatrix) {
		if (mode == RenderMode.REGULAR) {
			vbo.draw(gl);
		}
		if (tri.tex != null) {
			if (!tri.tex.isLoaded()) {
				tri.tex.load(gl);
			}
		}
		tri.tex.bind(gl);
		 vbo1.draw(gl);
	}
}
