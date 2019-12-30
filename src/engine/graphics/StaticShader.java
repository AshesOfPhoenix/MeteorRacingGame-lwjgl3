package engine.graphics;

import engine.entitete.Camera;
import engine.maths.Maths;
import org.lwjgl.util.vector.Matrix4f;

import java.io.IOException;

public class StaticShader extends Shader {
    private static final String VERTEX_FILE = "resources\\shaders\\mainVertex.glsl";
    private static final String FRAGMENT_FILE = "resources\\shaders\\mainFragment.glsl";
    private int location_transfMatrix;
    private int location_projfMatrix;
    private int location_viewMatrix;

    public StaticShader() throws IOException {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    public void getAllUniformLocations() {
        this.location_transfMatrix = super.getUniformLocation("transMatrix");
        if (this.location_transfMatrix == -1) {
            System.err.println("Error in Shader.addUniform(): uniform [" + "transMatrix" + "] does not exist");
            System.exit(1);
        }
        this.location_projfMatrix = super.getUniformLocation("projeMat");
        if (this.location_projfMatrix == -1) {
            System.err.println("Error in Shader.addUniform(): uniform [" + "projeMat" + "] does not exist");
            System.exit(1);
        }
        this.location_viewMatrix = super.getUniformLocation("viewMat");
        if (this.location_viewMatrix == -1) {
            System.err.println("Error in Shader.addUniform(): uniform [" + "viewMat" + "] does not exist");
            System.exit(1);
        }
    }

    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoordinates");
    }


    public void UniformViewMatrix(Camera camera) {
        Matrix4f new_viewMatrix = Maths.createViewMatrix(camera);
        super.Uniform1m(this.location_viewMatrix, new_viewMatrix);
    }

    public void UniformProjcMatrix(Matrix4f projection) {
        super.Uniform1m(this.location_projfMatrix, projection);
    }

    //?Also model matrix
    public void UniformTransMatrix(Matrix4f matrix) {
        super.Uniform1m(this.location_transfMatrix, matrix);
    }

    public int getLocation_transfMatrix() {
        return this.location_transfMatrix;
    }

    public int getLocation_projfMatrix() {
        return this.location_projfMatrix;
    }

    public int getLocation_viewMatrix() {
        return this.location_viewMatrix;
    }

}
