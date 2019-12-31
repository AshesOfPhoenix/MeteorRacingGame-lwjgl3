package engine.entitete;

import engine.io.Input;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.util.vector.Vector3f;

public class Camera {

    private Vector3f position;
    private float pitch; //!Camera rotation up and down
    private float yaw; //!Camera rotation right and left
    private float roll; //*We dont need this, yet at least

    public Camera() {
        this.position = new Vector3f(0, 0, 0);
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
        if (Input.isKeyDown(GLFW.GLFW_KEY_UP)) {
            this.pitch -= 0.2f;
        }
        if (Input.isKeyDown(GLFW.GLFW_KEY_DOWN)) {
            this.pitch += 0.2f;
        }
        if (Input.isKeyDown(GLFW.GLFW_KEY_RIGHT)) {
            this.yaw += 0.2f;
        }
        if (Input.isKeyDown(GLFW.GLFW_KEY_LEFT)) {
            this.yaw -= 0.2f;
        }
    }
    public Vector3f getPosition() {
        return this.position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public float getPitch() {
        return this.pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public float getYaw() {
        return this.yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getRoll() {
        return this.roll;
    }

    public void setRoll(float roll) {
        this.roll = roll;
    }
}

