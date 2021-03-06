package engine.graphics;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class Vertex {
    private Vector3f position, color;
    private Vector2f texturecord;


    public Vertex(Vector3f position, Vector3f color, Vector2f texturecord) {
        this.position = position;
        this.color = color;
        this.texturecord = texturecord;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getColor() {
        return color;
    }

    public Vector2f getTexturecord() {
        return texturecord;
    }
}