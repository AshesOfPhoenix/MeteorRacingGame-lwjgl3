package engine.graphics;


import org.lwjgl.opengl.GL13;
;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Material {
    private String filename;
    private Texture texture;
    private float width,height;
    private int textureID;

    public Material(String filename){
        this.filename=filename;
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
            texture=new Texture("objects/textures/"+filename);
            width= texture.getWidth();

            height=texture.getHeight();
            textureID=texture.getTextureID();
            texture.bind();
    }

    public void destroy(){
        texture.unbind();
    }
}
