package computergraphics.datastructures.mesh;

import java.util.ArrayList;

import computergraphics.math.Vector;
import computergraphics.rendering.Texture;

public class TriangleMesh implements ITriangleMesh {
    
    public ArrayList<Vertex> vertices = new ArrayList<>();
    public ArrayList<Triangle> triangles = new ArrayList<>();
    public ArrayList<Vector> tcords = new ArrayList<>();
    public Texture tex = null;
    
    @Override
    public int addVertex(Vector position) {
        vertices.add(new Vertex(position));
        return vertices.size() - 1;
    }
    
    @Override
    public void addVertices(Vector[] positions) {
        for (int i = 0; i < positions.length; i++) {
            addVertex(positions[i]);
        }
    }
    
    @Override
    public Vertex getVertex(int index) {
        return vertices.get(index);
    }
    
    @Override
    public int getNumberOfVertices() {
        return vertices.size();
    }
    
    @Override
    public void addTriangle(int vertexIndex1, int vertexIndex2, int vertexIndex3) {
        triangles.add(new Triangle(vertexIndex1, vertexIndex2, vertexIndex3));
    }
    
    @Override
    public void addTriangle(Triangle t) {
        triangles.add(t);
    }
    
    @Override
    public void addTriangle(int vertexIndex1, int vertexIndex2, int vertexIndex3,
        int texCoordIndex1,
        int texCoordIndex2, int texCoordIndex3) {
        triangles.add(new Triangle(vertexIndex1, vertexIndex2, vertexIndex3, texCoordIndex1,
            texCoordIndex2, texCoordIndex3));
    }
    
    @Override
    public int getNumberOfTriangles() {
        return triangles.size();
    }
    
    @Override
    public Triangle getTriangle(int triangleIndex) {
        return triangles.get(triangleIndex);
    }
    
    @Override
    public void clear() {
        vertices.clear();
        triangles.clear();
    }
    
    @Override
    public void computeTriangleNormals() {
        Triangle triangle;
        Vertex vertex0;
        Vertex vertex1;
        Vertex vertex2;
        Vector normal;
        for (int i = 0; i < triangles.size(); i++) {
            triangle = triangles.get(i);
            vertex0 = vertices.get(triangle.getVertexIndex(0));
            vertex1 = vertices.get(triangle.getVertexIndex(1));
            vertex2 = vertices.get(triangle.getVertexIndex(2));
            
            normal = (vertex1.getPosition().subtract(vertex0.getPosition()))
                .cross((vertex2.getPosition().subtract(vertex0.getPosition())));
            
            triangle.setNormal(normal.getNormalized().multiply(0.025));
        }
    }
    
    @Override
    public Vector getColor() {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public void setColor(Vector color) {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public Vector getTextureCoordinate(int index) {
        return tcords.get(index);
    }
    
    @Override
    public void addTextureCoordinate(Vector t) {
        tcords.add(t);
    }
    
    @Override
    public void setTexture(Texture texture) {
        tex = texture;
        
    }
    
    @Override
    public Texture getTexture() {
        return tex;
    }
    
    @Override
    public void createShadowPolygons(Vector lightPosition, float extend,
        ITriangleMesh shadowPolygonMesh) {
        // TODO Auto-generated method stub
        
    }
    
}
