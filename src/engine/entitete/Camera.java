package engine.entitete;

import engine.io.Input;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.util.vector.Vector3f;

public class Camera {

    private float dolzina_cam_obj = 35;
    private float kot_med_cam_in_obj = 0;
    private float oldMouseX = 550, oldMouseY = 400, newMousex = 0, newMouseY = 0;
    private Vector3f position = new Vector3f(0, 0, 0);
    private float pitch = 20; //!Camera rotation up and down
    private float yaw = 50; //!Camera rotation right and left
    private float roll; //*We dont need this, yet at least
    private Avtomobil avto;

    public Camera(Avtomobil avto) {
        this.avto = avto;
    }

    //!INPUT KEYS FOR CAMERA
    public void move(){
        if (Input.isKeyDown(GLFW.GLFW_KEY_W)) {
            this.position.z -= 0.001f;
        }
        if (Input.isKeyDown(GLFW.GLFW_KEY_D)) {
            this.position.x += 0.001f;
        }
        if (Input.isKeyDown(GLFW.GLFW_KEY_A)) {
            this.position.x -= 0.001f;
        }
        if (Input.isKeyDown(GLFW.GLFW_KEY_S)) {
            this.position.z += 0.001f;
        }
        if (Input.isKeyDown(GLFW.GLFW_KEY_UP)) {
            this.pitch -= 1.0f;
        }
        if (Input.isKeyDown(GLFW.GLFW_KEY_DOWN)) {
            this.pitch += 1.0f;
        }
        if (Input.isKeyDown(GLFW.GLFW_KEY_RIGHT)) {
            this.yaw += 1.0f;
        }
        if (Input.isKeyDown(GLFW.GLFW_KEY_LEFT)) {
            this.yaw -= 1.0f;
        }
        update();
        float hori = calcHorizontal();
        float verti = calcVertical();
        calcCameraPos(hori, verti);
        this.yaw = 180 - (avto.getRotY() + kot_med_cam_in_obj);

    }
    public Vector3f getPosition() {
        return this.position;
    }

    private void calcCameraPos(float horizD, float verticD) {
        float theta = avto.getRotY() + kot_med_cam_in_obj;
        float offsetX = (float) (dolzina_cam_obj * Math.sin(Math.toRadians(theta)));
        float offsetZ = (float) (dolzina_cam_obj * Math.cos(Math.toRadians(theta)));

        position.x = avto.getPosition().x - offsetX;
        position.z = avto.getPosition().z - offsetZ;
        position.y = avto.getPosition().y + verticD;

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

    public float calcHorizontal() {
        return (float) (dolzina_cam_obj * Math.cos(Math.toRadians(pitch)));
    }

    public float calcVertical() {
        return (float) (dolzina_cam_obj * Math.sin(Math.toRadians(pitch)));
    }

    public void update() {
        calculateZoom();
        newMousex = (float) Input.getMouseX();
        newMouseY = (float) Input.getMouseY();


        float dx = newMousex - oldMouseX;
        float dy = newMouseY - oldMouseY;
       /* if (Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_RIGHT)) {

        } else if ((Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT))) {

        }*/
        float pitchch = dy * 0.1f;
        pitch -= pitchch;
        float kot = dx * 0.3f;
        kot_med_cam_in_obj -= kot;
        oldMouseX = newMousex;
        oldMouseY = newMouseY;
    }

    private void calculateZoom() {
        float zoomic = (float) (Input.getScrollY() * 0.4f);
        dolzina_cam_obj -= zoomic;
    }
}