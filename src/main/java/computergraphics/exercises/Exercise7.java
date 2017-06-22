package computergraphics.exercises;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import com.jogamp.opengl.GL2;

import computergraphics.datastructures.mesh.ObjReader;
import computergraphics.datastructures.mesh.TriangleMesh;
import computergraphics.math.Vector;
import computergraphics.misc.Scene;
import computergraphics.datastructures.mesh.Volume;
import computergraphics.rendering.Shader;
import computergraphics.rendering.Texture;
import computergraphics.scenegraph.INode.RenderMode;
import computergraphics.scenegraph.TranslationNode;
import computergraphics.scenegraph.TriangleMeshNode;

public class Exercise7 extends Scene {
    private static final long serialVersionUID = 8141036480333465137L;
    
    private TranslationNode translation;
    private Texture tex = new Texture("textures/lego.png");
    private int x = 256;
    private int y = 256;
    private int z = 256;
    private Volume volume;
    
    
	private TranslationNode translation1;
	private TriangleMesh tri = new TriangleMesh();
	private TriangleMesh tri1 = new TriangleMesh();
	private Texture tex1 = new Texture("textures/legofloor.png");
	private Texture tex2 = new Texture("textures/box.jpg");
    
    public Exercise7() {
        // Timer timeout and shader mode (PHONG, TEXTURE, NO_LIGHTING)
        super(16, Shader.ShaderMode.TEXTURE, RenderMode.REGULAR);
    }
    
    
    @Override
    public void setupScenegraph(GL2 gl) {
        // Setup scene after OpenGL is ready
        getRoot().setLightPosition(new Vector(0, 2, 0));
        
			ObjReader objReader = new ObjReader();
			objReader.read("meshes/box.obj", tri);
			tri.setTexture(tex2);
			
			TriangleMeshNode tnn = new TriangleMeshNode(tri);
			translation = new TranslationNode(new Vector(0, 0.5, 0));
			translation.addChild(tnn);
			getRoot().addChild(translation);
			
			
			
			
        
//        try {
//            
//            // Path path =
//            // Paths.get("C:\\Users\\floha\\semester4workspace\\java\\assets\\volumedata\\neghip.vox");
//            // Path path =
//            // Paths.get("C:\\Users\\floha\\semester4workspace\\java\\assets\\volumedata\\engine.raw");
//            Path path = Paths.get("assets\\volumedata\\foot.raw");
//            
//            Volume v1 = new Volume(path, x, y, z);
//            double z = -1.0;
//            
//            int[] intary = new int[256];
//            IntBuffer intbuf = IntBuffer.wrap(intary);
//            gl.glGenTextures(256, intbuf);
//            // v1.calcTextureStacks();
//            byte[][] ts = v1.getTextureStack("x");
//            
//            for (int i = 0; i < 256; i++) {
//                ByteBuffer buffer = ByteBuffer.allocateDirect(256 * 256 * 4);
//                buffer.put(ts[i]);
//                buffer.position(0);
//                
//                gl.glEnable(GL2.GL_TEXTURE_2D);
//                gl.glBindTexture(GL2.GL_TEXTURE_2D, intbuf.get(i));
//                gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);
//                gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);
//                gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
//                gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
//                // gl.glTexParameteri(GL2.GL_TEXTURE_2D,
//                // GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_NEAREST);
//                // gl.glTexParameteri(GL2.GL_TEXTURE_2D,
//                // GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_NEAREST);
//                gl.glTexImage2D(GL2.GL_TEXTURE_2D, 0, GL2.GL_RGBA, 256, 256, 0, GL2.GL_RGBA,
//                    GL2.GL_BYTE, buffer);
//                
//                ObjReader objReader = new ObjReader();
//                TriangleMesh tri = new TriangleMesh();
//                objReader.read("meshes/square.obj", tri);
//                tri.setTexture(new Texture(intbuf.get(i)));
//                // tri.setTexture(tex);
//                System.out.println(intbuf.get(i));
//                
//                TriangleMeshNode tn = new TriangleMeshNode(tri);
//                Vector v = new Vector(0, 0.5, z);
//                System.out.println(v);
//                translation = new TranslationNode(v);
//                translation.addChild(tn);
//                getRoot().addChild(translation);
//                
//                // z += 0.03125;
//                z += 0.0078125;
//            }
//            
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
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
        new Exercise7();
    }
}
