package engine.render;

import engine.Models.RawModel;
import engine.entitete.Terrain;
import engine.graphics.TerrainShader;
import engine.maths.Maths;
import engine.textures.TerrainTexturePack;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import java.util.List;

public class TerrainRenderer {
    private TerrainShader shader;

    public TerrainRenderer(TerrainShader shader, Matrix4f projectionMatrix) {
        this.shader = shader;
        shader.bind();
        shader.UniformProjcMatrix(projectionMatrix);
        shader.connectTextureUnits();
        shader.UnBind();
    }

    public void render(List<Terrain> terrains) {
        for (Terrain terrain : terrains) {
            prepareTerrain(terrain);
            loadModelMatrix(terrain);
            GL30.glDrawElements(GL30.GL_TRIANGLES, terrain.getModel().getVertexCount(),
                    GL30.GL_UNSIGNED_INT, 0);
            unbindTexturedModel();
        }
    }

    private void prepareTerrain(Terrain terrain) {
        RawModel rawModel = terrain.getModel();
        GL30.glBindVertexArray(rawModel.getVaoID());
        GL30.glEnableVertexAttribArray(0);
        GL30.glEnableVertexAttribArray(1);
        GL30.glEnableVertexAttribArray(2);
        bindTextures(terrain);
        shader.UniformShineDamperAndReflectivity(1, 0);

    }

    private void bindTextures(Terrain terrain) {
        TerrainTexturePack texturePack = terrain.getTexturePack();
        GL30.glActiveTexture(GL30.GL_TEXTURE0);
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, texturePack.getBackgroundTexture().getTextureID());
        GL30.glActiveTexture(GL30.GL_TEXTURE1);
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, texturePack.getrTexture().getTextureID());
        GL30.glActiveTexture(GL30.GL_TEXTURE2);
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, texturePack.getgTexture().getTextureID());
        GL30.glActiveTexture(GL30.GL_TEXTURE3);
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, texturePack.getbTexture().getTextureID());
        GL30.glActiveTexture(GL30.GL_TEXTURE4);
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, terrain.getBlendMap().getTextureID());
    }


    private void unbindTexturedModel() {
        GL30.glDisableVertexAttribArray(0);
        GL30.glDisableVertexAttribArray(1);
        GL30.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }

    private void loadModelMatrix(Terrain terrain) {
        Matrix4f transformationMatrix = Maths.createTransfMatrix(
                new Vector3f(terrain.getX(), 0, terrain.getZ()), 0, 0, 0, 1);
        this.shader.UniformTransMatrix(transformationMatrix);
    }
}
