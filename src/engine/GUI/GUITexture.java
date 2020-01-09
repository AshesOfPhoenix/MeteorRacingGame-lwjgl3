package engine.GUI;

import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector2f;

public class GUITexture {
    private int textureID;
    private Vector2f position;
    private Vector2f scale;

    public GUITexture(int textureID, Vector2f position, Vector2f scale) {
        this.textureID = textureID;
        this.position = position;
        this.scale = scale;
        GL30.glActiveTexture(GL30.GL_TEXTURE0);
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, this.textureID);
        GL30.glEnable(GL30.GL_BLEND);
        GL30.glTexParameteri(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_MIN_FILTER, GL30.GL_LINEAR);
        GL30.glTexParameteri(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_WRAP_S, GL30.GL_CLAMP_TO_EDGE);
        GL30.glTexParameteri(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_WRAP_T, GL30.GL_CLAMP_TO_EDGE);
        GL30.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
        GL30.glActiveTexture(0);
        GL30.glDisable(GL30.GL_BLEND);
    }

    public int getTextureID() {
        return textureID;
    }

    public Vector2f getPosition() {
        return position;
    }

    public Vector2f getScale() {
        return scale;
    }
}
