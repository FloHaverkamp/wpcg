package computergraphics.exercises;

import java.awt.event.KeyEvent;
import java.io.IOException;

import com.jogamp.opengl.GL2;

import computergraphics.datastructures.mesh.Volume;
import computergraphics.math.Vector;
import computergraphics.misc.Scene;
import computergraphics.rendering.Shader;
import computergraphics.scenegraph.INode.RenderMode;
import computergraphics.scenegraph.VolumeNode;

public class Exercise6 extends Scene {
    private static final long serialVersionUID = 8141036480333465137L;
    private Volume volume;
    private VolumeNode volumeNode;
    
    public Exercise6() {
        // Timer timeout and shader mode (PHONG, TEXTURE, NO_LIGHTING)
        super(16, Shader.ShaderMode.TEXTURE, RenderMode.REGULAR);
    }
    
    @Override
    public void setupScenegraph(GL2 gl) {
        // Setup scene after OpenGL is ready
        getRoot().setLightPosition(new Vector(0, 2, 0));
        try {
            volume = new Volume("engine", gl);
            volumeNode = new VolumeNode(volume, getRoot().getCamera().getEye());
            getRoot().addChild(volumeNode);
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
        new Exercise6();
    }
}
