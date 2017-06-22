/**
 * Florian Haverkamp
 * Hochschule für Angewandte Wissenschaften (HAW), Hamburg
 * 
 * Base framework for "WP Computergrafik".
 */
package computergraphics.scenegraph;

import java.util.ArrayList;
import java.util.List;

import com.jogamp.opengl.GL2;

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
public class TreeTrunkNode extends LeafNode {

  /**
   * Cube side length
   */
  private double sideLength;
  private double height;
  private Vector color = new Vector(1.11, 0.6, 0.6, 1);

  /**
   * VBO.
   */
  private VertexBufferObject vbo = new VertexBufferObject();

  /**
   * Constructor.
   */
  public TreeTrunkNode(double sideLength, double height) {
    this.sideLength = sideLength;
    this.height = height;
    createVbo();
  }

  private void createVbo() {
    List<RenderVertex> renderVertices = new ArrayList<RenderVertex>();

    Vector p0 = new Vector(-sideLength, 0, -sideLength);
    Vector p1 = new Vector(sideLength, 0, -sideLength);
    Vector p2 = new Vector(sideLength, 2*height, -sideLength);
    Vector p3 = new Vector(-sideLength, 2*height, -sideLength);
    Vector p4 = new Vector(-sideLength, 0, sideLength);
    Vector p5 = new Vector(sideLength, 0, sideLength);
    Vector p6 = new Vector(sideLength, 2*height, sideLength);
    Vector p7 = new Vector(-sideLength,2*height, sideLength);
    Vector n0 = new Vector(0, 0, -1);
    Vector n1 = new Vector(1, 0, 0);
    Vector n2 = new Vector(0, 0, 1);
    Vector n3 = new Vector(-1, 0, 0);
    Vector n4 = new Vector(0, 1, 0);
    Vector n5 = new Vector(0, -1, 0);
    
    AddSideVertices(renderVertices, p0, p1, p2, p3, n0, color);
    AddSideVertices(renderVertices, p1, p5, p6, p2, n1, color);
    AddSideVertices(renderVertices, p4, p7, p6, p5, n2, color);
    AddSideVertices(renderVertices, p0, p3, p7, p4, n3, color);
    AddSideVertices(renderVertices, p2, p6, p7, p3, n4, color);
    AddSideVertices(renderVertices, p5, p1, p0, p4, n5, color);

    vbo.Setup(renderVertices, GL2.GL_QUADS);
  }

  /**
   * Add 4 vertices to the array
   */
  private void AddSideVertices(List<RenderVertex> renderVertices, Vector p0,
      Vector p1, Vector p2, Vector p3, Vector normal, Vector color) {
    renderVertices.add(new RenderVertex(p3, normal, color));
    renderVertices.add(new RenderVertex(p2, normal, color));
    renderVertices.add(new RenderVertex(p1, normal, color));
    renderVertices.add(new RenderVertex(p0, normal, color));
  }

  @Override
  public void drawGL(GL2 gl, RenderMode mode, Matrix modelMatrix) {
    if (mode == RenderMode.REGULAR) {
      vbo.draw(gl);
    }
  }
  
  public void setColor(Vector color) {
	    this.color = color;
	    createVbo();
	  }
}
