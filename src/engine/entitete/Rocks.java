package engine.entitete;

import engine.Models.TextureModel;
import org.lwjgl.util.vector.Vector3f;

public class Rocks extends Entity {
    private float xSize;
    private float ySize;
    private float zSize;
    private Vector3f center;
    private float sphereRadius;

    public float getSphereRadius() {
        return sphereRadius;
    }

    public float getxSize() {
        return xSize;
    }


    public float getySize() {
        return ySize;
    }


    public float getzSize() {
        return zSize;
    }


    public Vector3f getCenter() {
        return center;
    }

    public Rocks(TextureModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        super(model, position, rotX, rotY, rotZ, scale);
        this.xSize = model.getRawModel().getxSize() * scale;
        this.ySize = model.getRawModel().getySize() * scale;
        this.zSize = model.getRawModel().getzSize() * scale / 2;
        this.center = new Vector3f(position.x, position.y + this.ySize / 2.3f, position.z + this.zSize);
        this.sphereRadius = this.zSize / 2;
        super.setCenter(this.center);
        super.setxSize(this.xSize);
        super.setySize(this.ySize);
        super.setzSize(this.zSize);
    }
}
