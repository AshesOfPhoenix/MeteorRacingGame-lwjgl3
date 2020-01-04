package engine.entitete;

import engine.Models.TextureModel;
import org.lwjgl.util.vector.Vector3f;

public class Meteor extends Entity {
    private float currentSpeed = 0;
    private float currentTS = 0;

    public Meteor(TextureModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        super(model, position, rotX, rotY, rotZ, scale);
    }

    public void move() {
        //increase player roatiton in y cordinate in trunspead mnozimo z FTS-jem ker je moramo meriti obrat z casom
        super.increaseRotation(0, 0, currentTS / 15);
        //calculate distance distanceperseccond*current time
        float distance = currentSpeed / 15;

        float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotZ())));
        float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotZ())));
        super.increasePosition(dx, 0, dz);

    }
}
