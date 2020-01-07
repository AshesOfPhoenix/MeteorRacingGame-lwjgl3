package engine.render;

import engine.Models.RawModel;
import engine.Models.TextureModel;
import engine.entitete.Entity;
import engine.textures.Material;
import engine.graphics.StaticShader;
import engine.maths.Maths;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import java.util.List;
import java.util.Map;

//!Contains a specific shader program
public class EntityRenderer {
    private StaticShader shader;

    public EntityRenderer(StaticShader shader, Matrix4f projectionMatrix) {
        //!Projection Matrix creates only once and it never changes <- GO TO MASTER RENDERER
        this.shader = shader;
        shader.bind();
        shader.UniformProjcMatrix(projectionMatrix);
        shader.UnBind();
    }

    public void render(Map<TextureModel, List<Entity>> entities) {
        for (TextureModel model : entities.keySet()) {
            prepareTextureModel(model);
            List<Entity> batch = entities.get(model);
            for (Entity entity : batch) {
                prepareInstance(entity);
                GL30.glDrawElements(GL30.GL_TRIANGLES, model.getRawModel().getIndexCount(), GL30.GL_UNSIGNED_INT, 0);
            }
            unBindTextureModel();
        }
    }

    public void prepareTextureModel(TextureModel model) {
        RawModel rawModel = model.getRawModel();
        Material material = model.getMaterial();
        GL30.glBindVertexArray(rawModel.getVaoID());
        GL30.glEnableVertexAttribArray(0);
        GL30.glEnableVertexAttribArray(1);
        GL30.glEnableVertexAttribArray(2);
        shader.UniformShineDamperAndReflectivity(material.getShineDamper(), material.getReflectivity());
        GL30.glActiveTexture(GL30.GL_TEXTURE0);
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, material.getTextureID());
    }

    public void unBindTextureModel() {
        GL30.glDisableVertexAttribArray(0);
        GL30.glDisableVertexAttribArray(1);
        GL30.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }

    public void prepareInstance(Entity entity) {
        Matrix4f transformationMatrix = Maths.createTransfMatrix(entity.getPosition(), entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
        shader.UniformTransMatrix(transformationMatrix);
    }

    //!Render entity by binding vao, enabling vertex attributes and drawing the mesh
    public void renderEntity(Entity entity, StaticShader shader) {
        TextureModel model = entity.getModel();
        RawModel rawModel = model.getRawModel();
        Material material = model.getMaterial();

        GL30.glBindVertexArray(rawModel.getVaoID());
        GL30.glEnableVertexAttribArray(0);
        GL30.glEnableVertexAttribArray(1);
        GL30.glEnableVertexAttribArray(2);
        Matrix4f transformationMatrix = Maths.createTransfMatrix(entity.getPosition(), entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
        shader.UniformTransMatrix(transformationMatrix);
        shader.UniformShineDamperAndReflectivity(material.getShineDamper(), material.getReflectivity());

        GL30.glActiveTexture(GL30.GL_TEXTURE0);
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, material.getTextureID());

        //*DRAW CALL
        GL30.glDrawElements(GL30.GL_TRIANGLES, rawModel.getIndexCount(), GL30.GL_UNSIGNED_INT, 0);
        GL30.glDisableVertexAttribArray(0);
        GL30.glDisableVertexAttribArray(1);
        GL30.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }

    public void disable() {
        GL30.glEnable(0);
    }
}