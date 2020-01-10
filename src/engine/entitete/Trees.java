package engine.entitete;

import engine.Models.TextureModel;
import org.lwjgl.util.vector.Vector3f;

public class Trees extends Entity {
    private float xSize;
    private float ySize;
    private float zSize;
    private Vector3f center;
    private float logRadius;

    public Trees(TextureModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        super(model, position, rotX, rotY, rotZ, scale);
        this.xSize = model.getRawModel().getxSize() * scale;
        this.ySize = model.getRawModel().getySize() * scale;
        this.zSize = model.getRawModel().getzSize() * scale;
        this.center = new Vector3f(position.x, position.y, position.z);
        super.setCenter(this.center);
        super.setxSize(this.xSize);
        super.setySize(this.ySize);
        super.setzSize(this.zSize);
        this.logRadius = this.zSize / 8;
    }

    public float getLogRadius() {
        return logRadius;
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
}
