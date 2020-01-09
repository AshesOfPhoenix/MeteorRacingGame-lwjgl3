package engine.render;

import engine.GUI.GUITexture;
import engine.Models.Loader3Dmodel;
import engine.Models.RawModel;
import engine.graphics.GUIShader;
import engine.maths.Maths;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import java.io.IOException;

public class GUIRenderer {
    private final RawModel quad;
    private GUIShader shader;

    public GUIRenderer(Loader3Dmodel loader) throws IOException {
        float[] positions = {-1, 1, -1, -1, 1, 1, 1, -1};
        this.quad = loader.loadToVAO(positions);
        shader = new GUIShader();
    }

    public void render(GUITexture gui) {
        this.shader.bind();
        GL30.glBindVertexArray(this.quad.getVaoID());
        GL30.glEnableVertexAttribArray(0);
        //Render
        GL30.glActiveTexture(GL30.GL_TEXTURE0);
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, gui.getTextureID());
        //GL30.glDisable(GL30.GL_DEPTH_TEST);
        Matrix4f matrix = Maths.createTransformationMatrix(gui.getPosition(), gui.getScale());
        this.shader.loadTransformation(matrix);
        GL30.glDrawArrays(GL30.GL_TRIANGLE_STRIP, 0, this.quad.getVertexCount());
        //GL30.glEnable(GL30.GL_DEPTH_TEST);
        GL30.glActiveTexture(0);
        GL30.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
        this.shader.UnBind();
    }

    public void CleanUp() {
        this.shader.destroy();
    }
}
