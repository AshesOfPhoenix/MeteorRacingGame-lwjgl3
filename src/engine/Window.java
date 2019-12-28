package engine;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;

import java.nio.DoubleBuffer;
import java.security.Key;

public class Window {
    private int width,height;
    private String title;
    private long window;
    private boolean[] keys=new boolean[GLFW.GLFW_KEY_LAST];
    private boolean[] mouseb=new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];

    public Window(int width, int height, String title){
        this.width=width;
        this.height=height;
        this.title=title;
    }


    public void ustvari(){
        if(!GLFW.glfwInit()){
            System.err.println("Error");
            System.exit(-1);
        }
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE,GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE,GLFW.GLFW_FALSE);
        window=GLFW.glfwCreateWindow(width,height,title,0,0);

        if(window==0){
            System.err.println(("Error"));
            System.exit(-1);
        }

        GLFWVidMode videomode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());

        GLFW.glfwSetWindowPos(window,videomode.width() - width / 2,videomode.height() - height / 2);

        GLFW.glfwShowWindow(window);
    }
    public boolean closed(){
        return GLFW.glfwWindowShouldClose(window);
    }
    public void update(){
        for(int i=0;i<GLFW.GLFW_KEY_LAST;i++) keys[i]=KeyDown(i);
        for(int i=0;i<GLFW.GLFW_MOUSE_BUTTON_LAST;i++) mouseb[i]=MouseDown(i);
        GLFW.glfwPollEvents();
    }
    public void swapBuffers(){
        GLFW.glfwSwapBuffers(window);
    }
    public boolean KeyDown(int keycode){
        return GLFW.glfwGetKey(window,keycode)==1;
    }
    public boolean MouseDown(int keycode){
        return GLFW.glfwGetMouseButton(window,keycode)==1;
    }
    public boolean isKeyPressed(int keyCode){
        return KeyDown(keyCode) && !keys[keyCode];
    }
    public boolean isKeyReleased(int keyCode){
        return !KeyDown(keyCode) && keys[keyCode];
    }
    public boolean isMousePressed(int mose){
        return MouseDown(mose) && !mouseb[mose];
    }
    public boolean isMouseReleased(int mose){
        return !MouseDown(mose) && mouseb[mose];
    }
    public double getMousex(){
        DoubleBuffer buffer = BufferUtils.createDoubleBuffer(1);
        GLFW.glfwGetCursorPos(window,buffer,null);
        return buffer.get(0);
    }

    public double getMousey(){
        DoubleBuffer buffer = BufferUtils.createDoubleBuffer(1);
        GLFW.glfwGetCursorPos(window,null,buffer);
        return buffer.get(0);
    }
}
