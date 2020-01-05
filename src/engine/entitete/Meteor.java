package engine.entitete;

import engine.Models.TextureModel;
import org.lwjgl.util.vector.Vector3f;

public class Meteor extends Entity {
    private float currentSpeed = -(float) (Math.random() * 0.7 + 0.1);
    private float currentTS = 0;
    private Vector3f position;

    public Meteor(TextureModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        super(model, position, rotX, rotY, rotZ, scale);
        this.position = position;
    }

    public void move() {
        super.increasePosition(0, currentSpeed, 0);


    }
}


