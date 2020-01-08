package engine.entitete;

import engine.Models.TextureModel;
import org.lwjgl.util.vector.Vector3f;

public class Meteor extends Entity {
    private float currentSpeed = -(float) (Math.random() * 0.7 + 0.1);
    private float currentTS = 0;
    private Vector3f center;

    public Vector3f getCenter() {
        return center;
    }

    private float xSize;
    private float ySize;
    private float zSize;
    private float radius;

    public float getRadius() {
        return radius;
    }

    public Meteor(TextureModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        super(model, position, rotX, rotY, rotZ, scale);
        this.xSize = model.getRawModel().getxSize() * scale;
        this.ySize = model.getRawModel().getySize() * scale;
        this.zSize = model.getRawModel().getzSize() * scale;
        this.radius = Math.round(this.zSize) / 2;
        this.center = new Vector3f(position.x, position.y, position.z + radius);
        //super.setPosition(this.center);
    }

    public void move() {
        super.increasePosition(0, currentSpeed, 0);
        Vector3f a = super.getPosition();
        this.center = new Vector3f(a.x, a.y, a.z + radius);
        //this.center = super.getPosition();
    }
}


