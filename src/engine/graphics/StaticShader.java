package engine.graphics;

import engine.maths.Maths;
import entitete.Camera;
import org.lwjgl.util.vector.Matrix4f;

import java.io.IOException;

public class StaticShader extends Shader {
    private int location_transfMatrix;
    private int location_projfMatrix;
    private int location_viewMatrix;
    public StaticShader(String vertexFile, String fragmentFile) throws IOException {
        super(vertexFile, fragmentFile);
    }
    protected void getAllUniformLocations() {
        location_transfMatrix = super.getUniformLocation("transformationMatrix");
        location_projfMatrix = super.getUniformLocation("projectionMatrix");
        location_viewMatrix=super.getUniformLocation("location_viewMatrix");
    }

    public void loadTransMatrix(Matrix4f matrix){
     super.loadMatrix(location_transfMatrix,matrix);
    }

    public void loadViewMatrix(Camera camera){
        Matrix4f vieMatrix= Maths.createViewMatrix(camera);
        super.loadMatrix(location_viewMatrix,vieMatrix);
    }
    public void loadProjectionMatrix(Matrix4f projection){
        super.loadMatrix(location_projfMatrix,projection);
    }
}
