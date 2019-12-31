package engine.entitete;

import engine.io.Input;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.util.vector.Vector3f;

public class Camera {

    private Vector3f position;
    private static float pitch;
    private static float yaw;
    private float roll;
    private Input input;

    public Camera(Input keys) {
        this.position = new Vector3f(0, 0, 0);
        this.input = keys;
    }

    //!INPUT KEYS FOR CAMERA
    public void move(){
        if (Input.isKeyDown(GLFW.GLFW_KEY_W)) {
            this.position.z -= 0.02f;
        }
        if (Input.isKeyDown(GLFW.GLFW_KEY_D)) {
            this.position.x += 0.02f;
        }
        if (Input.isKeyDown(GLFW.GLFW_KEY_A)) {
            this.position.x -= 0.02f;
        }
        if (Input.isKeyDown(GLFW.GLFW_KEY_S)) {
            this.position.z += 0.02f;
        }

    }
    public Vector3f getPosition() {
        return this.position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public static float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public static float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getRoll() {
        return roll;
    }

    public void setRoll(float roll) {
        this.roll = roll;
    }
}

