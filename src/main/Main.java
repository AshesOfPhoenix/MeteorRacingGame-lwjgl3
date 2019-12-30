package main;

import engine.Models.Loader3Dmodel;
import engine.Models.RawModel;
import engine.Models.TextureModel;
import engine.entitete.Camera;
import engine.entitete.Entity;
import engine.graphics.Material;
import engine.graphics.StaticShader;
import engine.graphics.Texture;
import engine.io.Input;
import engine.io.Window;
import engine.render.Renderer;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.util.vector.Vector3f;

import java.io.IOException;

public class Main implements Runnable {
    public final int WIDTH = 1280, HEIGHT = 760;
    public Thread game;
    public Window window;
    public Renderer renderer;
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
        //!Create and initialize window
        window.create();
        window.setBackgroundColor(1.0f, 0, 1.0f);

        //!Read the shaders from file and Create shader program
        shreder = new StaticShader();

        //!Send shader to renderer for further use and create projection matrix
        renderer = new Renderer(shreder);    //<- Projection matrix creation inside

        //  mesh.create();

    }

    public void run() {
        try {
            init();
            float[] vertices = {
                    -0.5f, 0.5f, 0,
                    -0.5f, -0.5f, 0,
                    0.5f, -0.5f, 0,
                    0.5f, 0.5f, 0,

                    -0.5f, 0.5f, 1,
                    -0.5f, -0.5f, 1,
                    0.5f, -0.5f, 1,
                    0.5f, 0.5f, 1,

                    0.5f, 0.5f, 0,
                    0.5f, -0.5f, 0,
                    0.5f, -0.5f, 1,
                    0.5f, 0.5f, 1,

                    -0.5f, 0.5f, 0,
                    -0.5f, -0.5f, 0,
                    -0.5f, -0.5f, 1,
                    -0.5f, 0.5f, 1,

                    -0.5f, 0.5f, 1,
                    -0.5f, 0.5f, 0,
                    0.5f, 0.5f, 0,
                    0.5f, 0.5f, 1,

                    -0.5f, -0.5f, 1,
                    -0.5f, -0.5f, 0,
                    0.5f, -0.5f, 0,
                    0.5f, -0.5f, 1

            };
            float[] textureCoords = {

                    0, 0,
                    0, 1,
                    1, 1,
                    1, 0,
                    0, 0,
                    0, 1,
                    1, 1,
                    1, 0,
                    0, 0,
                    0, 1,
                    1, 1,
                    1, 0,
                    0, 0,
                    0, 1,
                    1, 1,
                    1, 0,
                    0, 0,
                    0, 1,
                    1, 1,
                    1, 0,
                    0, 0,
                    0, 1,
                    1, 1,
                    1, 0


            };
            int[] indices = {
                    0, 1, 3,
                    3, 1, 2,
                    4, 5, 7,
                    7, 5, 6,
                    8, 9, 11,
                    11, 9, 10,
                    12, 13, 15,
                    15, 13, 14,
                    16, 17, 19,
                    19, 17, 18,
                    20, 21, 23,
                    23, 21, 22

            };

            //!ONE OBJECT
            //ˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇ
            Loader3Dmodel loader = new Loader3Dmodel();
            //RawModel mode= ObjectLoader.loadObject("10603_slot_car_blue_SG_v1_iterations-2",loader);
            RawModel model = loader.loadToVAO(vertices, textureCoords, indices);

            Texture texture = new Texture("tnt.png");
            Material mat = new Material(texture);


            TextureModel mod = new TextureModel(model, mat);
            Entity entity = new Entity(mod, new Vector3f(0, 0, -5), 0, 0, 0, 1);
            //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

            Camera camera = new Camera();

            while (!window.shouldClose() && !Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
                //!Swap buffer
                render();
                update();
                //!Clear frame buffer
                renderer.prepare();

                //!Read mouse input
                camera.move();

                //!MOVE CUBE
                entity.increasePosition(0, 0, -0.1f);
                entity.increaseRotation(0.0f, 3.0f, 3.0f);

                //!RENDER OBJECT
                shreder.bind();
                shreder.UniformViewMatrix(camera);
                renderer.renderEntity(entity, shreder);  //<- Transformation matrix creation inside
                shreder.unbind();
                // entity.increaseRotation();
            }
            window.destroy();
            loader.pocisti();
            shreder.destroy();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void update() {
        window.update();
        if (Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT))
            System.out.println("X: " + Input.getMouseX() + ", Y: " + Input.getMouseY());
        //primer ko pritisnemo gumb f11 se nam poveca screen na fullscreen
        if (Input.isKeyDown(GLFW.GLFW_KEY_F11)) window.setFullscreen(!window.isFullscreen());
    }

    private void render() {
        // renderer.renderMesh(mesh);
        window.swapBuffers();
    }
}