package main;

import engine.Loader3Dmodel;
import engine.Models.TextureModel;
import engine.RawModel;
import engine.graphics.*;
import engine.io.Input;
import engine.io.Window;
import engine.maths.Vector2f;
import entitete.Camera;
import entitete.Entity;
import org.lwjgl.glfw.GLFW;
import org.newdawn.slick.opengl.Texture;
import org.w3c.dom.Text;
import org.lwjgl.util.vector.Vector3f;

import java.io.IOException;

public class Main implements Runnable {
    public final int WIDTH = 1280, HEIGHT = 760;
    public Thread game;
    public Window window;
    public Renderer renderer;
    public Shader shader;
    public static StaticShader shreder;
    /*public Mesh mesh = new Mesh(new Vertex[]{
            new Vertex(new Vector3f(-0.5f, 0.5f, 0.0f),new Vector3f(1.0f,0.0f,0.0f),new Vector2f(0.0f,0.0f)),
            new Vertex(new Vector3f(-0.5f, -0.5f, 0.0f),new Vector3f(0.0f,1.0f,0.0f),new Vector2f(0.0f,0.0f)),
            new Vertex(new Vector3f(0.5f, -0.5f, 0.0f),new Vector3f(0.0f,0.0f,1.0f),new Vector2f(0.0f,0.0f)),
            new Vertex(new Vector3f(0.5f, 0.5f, 0.0f),new Vector3f(1.0f,1.0f,0.0f),new Vector2f(0.0f,0.0f))
    }, new int[]{
            0, 1, 2,
            0, 3, 2
    },new Material("pot do teksture"));*/

    public static void main(String[] args) {
        new Main().start();
    }

    public void start() {
        game = new Thread(this, "game");
        game.start();
    }
    public void init() throws IOException {
        window = new Window(WIDTH, HEIGHT, "Game");
        shader = new Shader("bin\\shaders\\mainVertex.glsl", "bin\\shaders\\mainFragment.glsl");

        window.setBackgroundColor(1.0f, 0, 0);
        window.create();
        renderer = new Renderer(shader);

      //  mesh.create();
        shader.create();
    }

    public void run() {
        try {
            init();

            Loader3Dmodel loader=new Loader3Dmodel();
            RawModel mode= ObjectLoader.loadObject("10603_slot_car_blue_SG_v1_iterations-2",loader);


            Material mat=new Material("avtomobilcek.png");
            mat.create();
            TextureModel mod=new TextureModel(mode,mat);

            Entity entity =new Entity(mod, new Vector3f(0,0,-1),0,0,0,1);

            Camera camera=new Camera();

            while (!window.shouldClose() && !Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {

                entity.increasePosition(0,0,-0.1f);

                // entity.increaseRotation();

                update();
                camera.move();
                render();
                shreder.loadViewMatrix(camera);

                //primer ko pritisnemo gumb f11 se nam poveca screen na fullscreen
                if (Input.isKeyDown(GLFW.GLFW_KEY_F11)) window.setFullscreen(!window.isFullscreen());
            }
            window.destroy();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void update() {
        window.update();
        if (Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT))
            System.out.println("X: " + Input.getMouseX() + ", Y: " + Input.getMouseY());
    }

    private void render() {
       // renderer.renderMesh(mesh);
        window.swapBuffers();
    }
}