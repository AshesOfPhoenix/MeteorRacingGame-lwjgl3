package engine.graphics;

import engine.entitete.Camera;
import engine.entitete.Light;
import engine.maths.Maths;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import java.io.IOException;

public class StaticShader extends Shader {
    private String VERTEX_FILE;
    private String FRAGMENT_FILE;
    private int location_transfMatrix;
    private int location_projfMatrix;
    private int location_viewMatrix;
    private int location_lightPosition;
    private int location_lightColor;
    private int location_shineDamper;
    private int location_reflectivity;
    private int location_skyColor;

    public StaticShader(String VertexShaderPath, String FragmentShaderPath) throws IOException {
        super(VertexShaderPath, FragmentShaderPath);
        VERTEX_FILE = VertexShaderPath;
        FRAGMENT_FILE = FragmentShaderPath;
    }

    public void getAllUniformLocations() {
        this.location_transfMatrix = super.getUniformLocation("transMatrix");
        this.location_projfMatrix = super.getUniformLocation("projeMat");
        this.location_viewMatrix = super.getUniformLocation("viewMat");
        this.location_lightPosition = super.getUniformLocation("lightPosition");
        this.location_lightColor = super.getUniformLocation("lightColor");
        this.location_shineDamper = super.getUniformLocation("shineDamper");
        this.location_reflectivity = super.getUniformLocation("reflectivity");
        this.location_skyColor = super.getUniformLocation("skyColor");
    }

    public void loadSkyColor(float r, float g, float b) {
        super.Uniform1v(location_skyColor, new Vector3f(r, g, b));
    }

    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoordinates");
        super.bindAttribute(2, "normal");
    }

    public void UniformShineDamperAndReflectivity(float damper, float reflectivity) {
        super.Uniform1f(location_shineDamper, damper);
        super.Uniform1f(location_reflectivity, reflectivity);
    }

    public void UniformViewMatrix(Camera camera) {
        Matrix4f new_viewMatrix = Maths.createViewMatrix(camera);
        super.Uniform1m(this.location_viewMatrix, new_viewMatrix);
    }

    public void UniformLight(Light light) {
        super.Uniform1v(this.location_lightPosition, light.getPosition());
        super.Uniform1v(this.location_lightColor, light.getColor());
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
