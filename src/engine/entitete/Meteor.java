package engine.entitete;

import engine.Models.TextureModel;
import org.lwjgl.util.vector.Vector3f;

public class Meteor extends Entity {
    private float currentSpeed = -(float) (Math.random() * 0.8 + 0.1);

    public float getCurrentSpeed() {
        return currentSpeed;
    }

    private float currentTS = 0;
    public Vector3f center;

    public Vector3f getCenter() {
        return center;
    }

    private float xSize;
    private float ySize;
    private float zSize;
    private float radius;
    private static Vector3f reset;

    public float getRadius() {
        return radius;
    }

    public void incrementSpeed(float increment) {
        this.currentSpeed += increment;
    }

    public Meteor(TextureModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        super(model, position, rotX, rotY, rotZ, scale);
        this.xSize = model.getRawModel().getxSize() * scale;
        this.ySize = model.getRawModel().getySize() * scale;
        this.zSize = model.getRawModel().getzSize() * scale;
        this.radius = Math.round(this.zSize) / 2;
        this.center = new Vector3f(position.x, position.y, position.z + radius);
        this.reset = position;
        //super.setPosition(this.center);
    }

    public void move(Vector3f positionCar) {
        Vector3f a = super.getPosition();
        if (a.y < -100) {
            restart(positionCar);
        } else {
            super.increasePosition(currentSpeed, currentSpeed, 0);
            Vector3f a2 = super.getPosition();
            this.center = new Vector3f(a2.x, a2.y, a2.z + radius);
            //this.center = super.getPosition();
        }
    }

    public void setCenter(Vector3f center) {
        this.center = center;
    }

    public void restart(Vector3f positionCar) {
        Vector3f a = randomCirclePoint(positionCar);
        super.setPosition(a);
        this.center = new Vector3f(a.x, a.y, a.z + radius);
    }

    public Vector3f randomCirclePoint(Vector3f positionCar) {
        float r = (float) (100 * Math.sqrt(Math.random()));
        float theta = (float) (Math.random() * 2 * Math.PI);

        float x = (float) (positionCar.getX() + r * Math.cos(theta));
        float z = (float) (positionCar.getZ() + r * Math.sin(theta));
        return new Vector3f(x, (float) ((Math.random() * 500) + 350), z);
    }
}


