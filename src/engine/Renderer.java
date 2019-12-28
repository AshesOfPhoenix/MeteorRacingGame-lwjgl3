package engine;

import engine.RawModel;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjglx.debug.opengl.GL30;
import org.lwjglx.debug.opengl.GL20;

public class Renderer {

    //!CleanUp the frame
    public void prepare(){
        GL11.glClearColor(1,0,0,1);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
    }
    //!Bind VAO and draw on the screen
    public void renderer(RawModel model){
        GL30.glBindVertexArray(model.getVaoID());
        //GL30.glEnableVertexAttribArray(0);
        GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);


    }
}
