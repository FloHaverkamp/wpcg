package computergraphics.scenegraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.jogamp.opengl.GL2;

import computergraphics.datastructures.mesh.TriangleMesh;
import computergraphics.datastructures.mesh.Volume;
import computergraphics.math.Matrix;
import computergraphics.math.Vector;

public class VolumeNode extends InnerNode {
    
    private Volume volume;
    /**
     * List of child nodes.
     */
    private List<INode> childrenX;
    private List<INode> childrenY;
    private List<INode> childrenZ;
    
    public VolumeNode(Volume volume, Vector eye) {
        this.volume = volume;
        childrenX = createLeafNodes("x", eye);
        childrenY = createLeafNodes("y", eye);
        childrenZ = createLeafNodes("z", eye);
    }
    
    /**
     * this method creates the leafNodes with triangleMeshes for traversing
     * 
     * @param axis
     * @param eye
     * @return
     */
    private List<INode> createLeafNodes(String axis, Vector eye) {
        ArrayList<INode> nodes = new ArrayList<>();
        List<TriangleMesh> triangleMeshes = getMeshInOrder(axis, eye);
        
        for (TriangleMesh tM : triangleMeshes) {
            nodes.add(new TriangleMeshNode(tM));
        }
        return nodes;
    }
    
    /**
     * this method returns an ordered list of the textureStack according to the
     * position of the eye.
     * 
     * @param axis
     * @param eye
     * @return
     */
    private List<TriangleMesh> getMeshInOrder(String axis, Vector eye) {
        List<TriangleMesh> orderedTriangleMeshes = new ArrayList<>();
        
        List<Vector> centers = volume.getCenters().get(axis);
        List<TriangleMesh> meshes = Arrays.asList(volume.getTriangleMeshes().get(axis));
        
        // build connection vectors with first and last plane
        Vector first = centers.get(0);
        Vector last = centers.get(centers.size() - 1);
        Vector eyeFirst = first.subtract(eye);
        Vector eyeLast = last.subtract(eye);
        
        // calculate length of both vectors and compare
        // squared root (x^2 + y^2 + z^2)
        double lengthEyeFirst = Math.sqrt(
            Math.pow(eyeFirst.x(), 2) + Math.pow(eyeFirst.y(), 2) + Math.pow(eyeFirst.z(), 2));
        double lengthEyeLast = Math
            .sqrt(Math.pow(eyeLast.x(), 2) + Math.pow(eyeLast.y(), 2) + Math.pow(eyeLast.z(), 2));
        
        if (lengthEyeFirst - lengthEyeLast < 0) {
            // first Plane is nearer to eye than last
            // --> last plane has to be computed first
            Collections.reverse(meshes);
            orderedTriangleMeshes = meshes;
        } else {
            // last Plane is nearer to eye than first
            // --> first has to be computed first
            orderedTriangleMeshes = meshes;
        }
        return orderedTriangleMeshes;
    }
    
    @Override
    public void traverse(GL2 gl, RenderMode mode, Matrix modelMatrix) {
        for (INode child : childrenX) {
            child.traverse(gl, mode, modelMatrix);
        }
        // for (INode child : childrenY) {
        // child.traverse(gl, mode, modelMatrix);
        // }
        // for (INode child : childrenZ) {
        // child.traverse(gl, mode, modelMatrix);
        // }
    }
    
    @Override
    public void timerTick(int counter) {
        for (INode child : childrenX) {
            child.timerTick(counter);
        }
        for (INode child : childrenY) {
            child.timerTick(counter);
        }
        for (INode child : childrenZ) {
            child.timerTick(counter);
        }
    }
    
    /**
     * Add new child node.
     **/
    public void addChild(String axis, INode child) {
        child.setParentNode(this);
        switch (axis) {
        case "x":
            childrenX.add(child);
            break;
        case "y":
            childrenY.add(child);
            break;
        case "z":
            childrenZ.add(child);
            break;
        }
    }
    
    public boolean removeChild(String axis, INode child) {
        switch (axis) {
        case "x":
            return childrenX.remove(child);
        case "y":
            return childrenY.remove(child);
        case "z":
            return childrenZ.remove(child);
        default:
            return false;
        }
    }
    
    public boolean hasChild(String axis, INode child) {
        switch (axis) {
        case "x":
            return childrenX.contains(child);
        case "y":
            return childrenY.contains(child);
        case "z":
            return childrenZ.contains(child);
        default:
            return false;
        }
    }
}
