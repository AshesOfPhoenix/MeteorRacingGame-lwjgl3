package engine.graphics;

import org.lwjgl.util.vector.Matrix4f;

import java.io.IOException;

public class StaticShader extends Shader {
    private int location_transfMatrix;
    public StaticShader(String vertexFile, String fragmentFile) throws IOException {
        super(vertexFile, fragmentFile);
    }
    protected void getAllUniformLocations() {
        location_transfMatrix = super.getUniformLocation("transformationMatrix");
    }

    public void loadTransMatrix(Matrix4f matrix){
     super.loadMatrix(location_transfMatrix,matrix);
    }

}
