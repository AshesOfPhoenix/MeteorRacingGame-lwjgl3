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

    public void UniformViewMatrix(Camera camera) {
        Matrix4f vieMatrix= Maths.createViewMatrix(camera);
        super.Uniform1m(location_viewMatrix, vieMatrix);
    }

    public void bindAttributes() {
        super.bindAttributes(0, "position");
        super.bindAttributes(1, "textureCoordinates");
    }

    public void UniformProjcMatrix(Matrix4f projection) {
        super.Uniform1m(location_projfMatrix, projection);
    }

    //?Also model matrix
    public void UniformTransMatrix(Matrix4f matrix) {
        super.Uniform1m(location_transfMatrix, matrix);
    }
}
