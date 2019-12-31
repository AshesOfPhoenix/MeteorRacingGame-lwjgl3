package engine.render;

import engine.Models.RawModel;
import engine.Models.TextureModel;
import engine.entitete.Entity;
import engine.graphics.Material;
import engine.graphics.Mesh;
import engine.graphics.StaticShader;
import engine.io.Window;
import engine.maths.Maths;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

//!Contains a specific shader program
public class Renderer {
    private StaticShader shader;
    private static final float FOV = 70;
    private static final float NEAR_PLANE = 0.1f;
    private static final float FAR_PlANE = 1000;
    private Matrix4f projectionMatrix;

    public Renderer(StaticShader shader) {
        this.shader = shader;
        //!Projection Matrix creates only one and it never changes
        createProjectionMatrix();
        shader.bind();
        shader.UniformProjcMatrix(this.projectionMatrix);
        shader.UnBind();
    }

    //!Render mesh by binding vao, enabling vertex attributes and drawing the mesh
    public void renderMesh(Mesh mesh) {
        GL30.glBindVertexArray(mesh.getVAO());
        GL30.glEnableVertexAttribArray(0);
        GL30.glEnableVertexAttribArray(1);
        //GL30.glEnableVertexAttribArray(2);
        GL30.glBindBuffer(GL30.GL_ELEMENT_ARRAY_BUFFER, mesh.getIBO());
        GL30.glActiveTexture(GL30.GL_TEXTURE0);
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, mesh.getMaterial().getTextureID());
        this.shader.bind();
        GL30.glDrawElements(GL30.GL_TRIANGLES, mesh.getIndices().length, GL30.GL_UNSIGNED_INT, 0);
        this.shader.UnBind();
        GL30.glBindBuffer(GL30.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL30.glDisableVertexAttribArray(0);
        GL30.glDisableVertexAttribArray(1);
        //GL30.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }

    public void renderEntity(Entity entity, StaticShader shader) {
        TextureModel model = entity.getModel();
        RawModel rawModel = model.getRawModel();
        Material material = model.getMaterial();

        GL30.glBindVertexArray(rawModel.getVaoID());
        GL30.glEnableVertexAttribArray(0);
        GL30.glEnableVertexAttribArray(1);
        //GL30.glEnableVertexAttribArray(2);
        Matrix4f transformationMatrix = Maths.createTransfMatrix(entity.getPosition(), entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
        shader.UniformTransMatrix(transformationMatrix);

        //shader.UniformProjcMatrix(this.projectionMatrix);
        GL30.glActiveTexture(GL30.GL_TEXTURE0);
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, material.getTextureID());
        GL30.glDrawElements(GL30.GL_TRIANGLES, rawModel.getIndexCount(), GL30.GL_UNSIGNED_INT, 0);
        GL30.glDisableVertexAttribArray(0);
        GL30.glDisableVertexAttribArray(1);
        GL30.glBindVertexArray(0);
    }

    private void createProjectionMatrix() {
        float aspectRatio = (float) Window.getWidth() / (float) Window.getHeight();
        float yscale = (float) ((1f / Math.tan(Math.toRadians((FOV / 2f)))) * aspectRatio);
        float xscale = yscale / aspectRatio;
        float lenght = FAR_PlANE - NEAR_PLANE;

        this.projectionMatrix = new Matrix4f();
        this.projectionMatrix.m00 = xscale;
        this.projectionMatrix.m11 = yscale;

        this.projectionMatrix.m22 = -((FAR_PlANE + NEAR_PLANE) / lenght);
        this.projectionMatrix.m23 = -1;
        this.projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PlANE) / lenght);
        this.projectionMatrix.m33 = 0;
    }

    //!Clean the frame
    public void prepare() {
        GL30.glEnable(GL30.GL_DEPTH_TEST);
    }
}