package engine.entitete;

import engine.Models.TextureModel;
import org.lwjgl.util.vector.Vector3f;

public class Meteor extends Entity {
    private float currentSpeed;

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
    private Moving moving;

    private Vector3f meteorRecorded;
    private Vector3f carRecorded;
    private Vector3f normalizedVector;
    private float razdalja;

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
        this.razdalja = 0;
        this.moving = Moving.NOT_MOVING;
        this.currentSpeed = -(float) (Math.random() * 0.8 + 0.1);
        //super.setPosition(this.center);
    }

    private enum Moving {
        MOVING, NOT_MOVING
    }

    public void move(Vector3f positionCar) {
        Vector3f a = super.getPosition();
        if (a.y < -100) {
            this.moving = Moving.NOT_MOVING;
        }
        if (this.moving == Moving.MOVING) {
            this.razdalja += this.currentSpeed;
            Vector3f new_position = new Vector3f(this.carRecorded.x + this.razdalja * this.normalizedVector.x, this.carRecorded.y + this.razdalja * this.normalizedVector.y, this.carRecorded.z + this.razdalja * this.normalizedVector.z);
            super.setPosition(new_position);
            setCenter(new Vector3f(new_position.x, new_position.y, new_position.z + this.radius));
        } else if (this.moving == Moving.NOT_MOVING) {
            Vector3f lastRecordedCarPosition = getPosition();
            Vector3f distance = new Vector3f(positionCar.x - lastRecordedCarPosition.x, positionCar.y - lastRecordedCarPosition.y, positionCar.z - lastRecordedCarPosition.z);
            Vector3f possibleLocation = new Vector3f(positionCar.x + distance.x, positionCar.y + distance.y, positionCar.z + distance.z);
            restart(possibleLocation);
            this.carRecorded = randomPointINCircle(possibleLocation, 100);
            this.normalizedVector = pointOnLine(this.carRecorded, this.meteorRecorded);
            this.razdalja = euclideanDistance(this.carRecorded);
            moving = Moving.MOVING;
        }
    }

    public void setCenter(Vector3f center) {
        this.center = center;
    }

    public void restart(Vector3f positionCar) {
        Vector3f a = randomPointINCircle(positionCar, 600);
        Vector3f b = new Vector3f(a.x, this.getPosition().y, a.z);
        this.meteorRecorded = b;
        super.setPosition(b);
        setCenter(new Vector3f(b.x, b.y, b.z + radius));
    }

    public Vector3f randomPointINCircle(Vector3f positionCar, float radius) {
        float r = (float) (radius * Math.sqrt(Math.random()));
        float theta = (float) (Math.random() * 2 * Math.PI);

        float x = (float) (positionCar.getX() + r * Math.cos(theta));
        float z = (float) (positionCar.getZ() + r * Math.sin(theta));
        return new Vector3f(x, positionCar.y, z);
    }

    private Vector3f pointOnLine(Vector3f carPosition, Vector3f meteorPosition) {
        Vector3f sub = new Vector3f(meteorPosition.x - carPosition.x, meteorPosition.y - carPosition.y, meteorPosition.z - carPosition.z);
        float down = (float) Math.sqrt(Math.pow(sub.x, 2) + Math.pow(sub.y, 2) + Math.pow(sub.z, 2));
        Vector3f result = new Vector3f(sub.x / down, sub.y / down, sub.z / down);
        return result;
    }
}


