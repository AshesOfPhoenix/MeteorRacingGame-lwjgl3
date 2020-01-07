package engine.graphics;

import org.lwjgl.util.vector.Matrix4f;

import java.io.IOException;

public class GUIShader extends Shader {
    private static final String VERTEX_FILE = "resources/shaders/GuiVertexShader.glsl";
    private static final String FRAGMENT_FILE = "resources/shaders/GuiFragmentShader.glsl";

    private int location_transformationMatrix;

    public GUIShader() throws IOException {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    public void loadTransformation(Matrix4f matrix) {
        super.Uniform1m(location_transformationMatrix, matrix);
    }

    @Override
    protected void getAllUniformLocations() {
        location_transformationMatrix = super.getUniformLocation("transformationMatrixGui");
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
    }
}
