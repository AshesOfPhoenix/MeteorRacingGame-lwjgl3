package engine.entitete;

import engine.Models.TextureModel;
import engine.io.Input;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.util.vector.Vector3f;

public class Avtomobil extends Entity {
    private static final float RUN_SPEED = 20;
    private static final float TURN_SPEED = 20;


    private float currentSpeed = 0;
    private float currentTS = 0;

    public Avtomobil(TextureModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        super(model, position, rotX, rotY, rotZ, scale);
    }

    public void move() {
        checkInputs();
        //increase player roatiton in y cordinate in trunspead mnozimo z FTS-jem ker je moramo meriti obrat z casom
        super.increaseRotation(0, 0, currentTS / 15);
        //calculate distance distanceperseccond*current time
        float distance = currentSpeed / 15;

        float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotZ())));
        float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotZ())));
        super.increasePosition(dx, 0, dz);

    }

    //check the keyboard input and set car moving speed
    private void checkInputs() {
        if (Input.isKeyDown(GLFW.GLFW_KEY_W)) {
            this.currentSpeed = -RUN_SPEED;
        } else if (Input.isKeyDown(GLFW.GLFW_KEY_S)) {
            this.currentSpeed = RUN_SPEED;
        } else {
            this.currentSpeed = 0;
        }

        if (Input.isKeyDown(GLFW.GLFW_KEY_D)) {
            this.currentTS = -TURN_SPEED;
        } else if (Input.isKeyDown(GLFW.GLFW_KEY_A)) {
            this.currentTS = TURN_SPEED;
        } else {
            this.currentTS = 0;
        }
    }
}
