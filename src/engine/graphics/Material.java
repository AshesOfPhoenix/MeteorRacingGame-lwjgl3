package engine.graphics;


import engine.utils.FileUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import java.io.IOException;

public class Material {
    private String path;
    private Texture texture;
    private float width,height;
    private int textureID;

    public Material(String path){
        this.path=path;
    }

    public Texture getTexture() {
        return texture;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public int getTextureID() {
        return textureID;

    }

    public void create(){
        try {
            texture=TextureLoader.getTexture(path.split(".")[1],FileUtils.class.getResourceAsStream(path),GL11.GL_LINEAR);

            width= texture.getWidth();

            height=texture.getHeight();
            textureID=texture.getTextureID();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void destroy(){
        GL13.glDeleteTextures(textureID);
    }
}
