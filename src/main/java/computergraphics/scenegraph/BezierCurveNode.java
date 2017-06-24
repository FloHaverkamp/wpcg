package computergraphics.scenegraph;

import java.util.ArrayList;
import java.util.List;

import com.jogamp.opengl.GL2;

import computergraphics.datastructures.curve.BezierCurve;
import computergraphics.math.Matrix;
import computergraphics.math.Vector;
import computergraphics.rendering.RenderVertex;
import computergraphics.rendering.VertexBufferObject;

/**
 * Representation of a coordinate frame with lines for the three axis in RBG.
 */
public class BezierCurveNode extends LeafNode {
	private VertexBufferObject vbo = new VertexBufferObject();
	private BezierCurve bcurve;
	private double h = 0.000001;

	public BezierCurveNode(BezierCurve bc) {
		this(bc, 0.1);
	}

	public BezierCurveNode(BezierCurve bc, double resolution) {
		List<RenderVertex> renderVertices = new ArrayList<RenderVertex>();
		bcurve = bc;

		for (double i = 0; i <= 1; i += resolution) {
			renderVertices.add(new RenderVertex(bc.comupte(i), new Vector(0, 1, 0), new Vector(1, 0, 0, 1)));
		}
		vbo.Setup(renderVertices, GL2.GL_LINE_STRIP);
	}

	public void comupteTangents() {
		List<RenderVertex> renderVertices1 = new ArrayList<RenderVertex>();
		Vector v;
		for (double i = 0; i <= 1; i += 0.2) {
			renderVertices1.add(new RenderVertex(bcurve.comupte(i), new Vector(0, 1, 0), new Vector(0, 0, 0, 1)));
			v = (bcurve.comupte(i + h).subtract(bcurve.comupte(i))).multiply((1/h));
			System.out.println(v);
			renderVertices1.add(new RenderVertex(bcurve.comupte(i).add(v), new Vector(0, 1, 0), new Vector(0, 0, 0, 1)));
			
			
		}
		vbo.Setup(renderVertices1, GL2.GL_LINES);
	}

	@Override
	public void drawGL(GL2 gl, RenderMode mode, Matrix modelMatrix) {
		gl.glLineWidth(3);
		vbo.draw(gl);
	}

	@Override
	public void timerTick(int counter) {
	}
}
