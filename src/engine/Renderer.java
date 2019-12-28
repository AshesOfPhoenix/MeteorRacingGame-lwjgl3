package engine;

import engine.Models.TextureModel;
import engine.RawModel;
import engine.graphics.StaticShader;
import engine.maths.Maths;
import entitete.Entity;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjglx.debug.opengl.GL30;
import org.lwjglx.debug.opengl.GL20;
import engine.maths.Vector3f;

public class Renderer {

    //!CleanUp the frame
    public void prepare(){
        GL11.glClearColor(1,0,0,1);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
    }
    //!Bind VAO and draw on the screen
    public void renderer(Entity entity,StaticShader shader){
        TextureModel model =entity.getModel();
        RawModel rawModel =model.getRawModel();
        GL30.glBindVertexArray(rawModel.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);

        Matrix4f transMatrix= Maths.createTransfMatrix(entity.getPosition(),entity.getRotX(),entity.getRotY(),entity.getRotZ(),entity.getScale());
        shader.loadTransMatrix(transMatrix);
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glDrawElements(GL11.GL_TRIANGLES, rawModel.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);


    }
}
