package main;

import engine.Models.Loader3Dmodel;
import engine.Models.ObjectLoader;
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
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector3f;

import java.io.IOException;

public class Main implements Runnable {
    public final int WIDTH = 1280, HEIGHT = 760;
    public Thread game;
    public Window window;
    public Renderer renderCube;
    public static StaticShader CubeShader;
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
        window.setBackgroundColor(1.0f, 1.0f, 1.0f);

        //!Read the shaders from file and Create shader program
        CubeShader = new StaticShader();

        //!Send shader to renderCube for further use and create projection matrix
        renderCube = new Renderer(CubeShader);    //<- Projection matrix creation inside

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
            //*Declare loader class for loading 3D objects
            Loader3Dmodel loader = new Loader3Dmodel();

            //*Initialize raw models
            RawModel modelCar = ObjectLoader.loadObject("10603_slot_car_blue_SG_v1_iterations-2", loader);
            RawModel modelCube = loader.loadToVAO(vertices, textureCoords, indices);

            //*Initialize materials and textures
            Texture textureCube = new Texture("tnt.png");
            Material materialCube = new Material(textureCube);

            //*Initialize textured models
            TextureModel texturedCar = new TextureModel(modelCar, materialCube);
            TextureModel texturedCube = new TextureModel(modelCube, materialCube);

            //*Initialize entities
            Entity Car = new Entity(texturedCar, new Vector3f(-3, 0, -8), 7, 0, 0, 1.5f);
            //Entity Cube = new Entity(texturedCube, new Vector3f(-3, 0, -8), 7, 0, 0, 1.5f);
            //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
            Input vhodi = new Input();
            Camera camera = new Camera(vhodi);

            while (!window.shouldClose() && !vhodi.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
                //!Swap buffer and Clear frame buffer from previous drawcall
                render();
                update();

                //!Init OpenGL specifications
                renderCube.prepare();

                //!Read mouse input
                vhodi = new Input();
                if (vhodi.isKeyDown(GLFW.GLFW_KEY_W)) {
                    System.out.println("Matr");
                }
                camera.move();

                //!MOVE OBJECTS - TRANSFORMATION - MODEL MATRIX
                Car.increaseRotation(0.0f, 0.0f, 0.5f);
                // Cube.increaseRotation(5.0f, 0.0f, 5.0f);

                //!RENDER OBJECTS
                CubeShader.bind();
                CubeShader.UniformViewMatrix(camera);      //<- Send view matrix to the shader
                renderCube.renderEntity(Car, CubeShader);  //<- Transformation matrix creation inside
                //   renderCube.renderEntity(Cube, CubeShader);
                CubeShader.UnBind();
            }
            window.destroy();
            loader.pocisti();
            CubeShader.destroy();
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
        // renderCube.renderMesh(mesh);
        window.swapBuffers();
        GL30.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT);
        GL30.glClearColor(0, 0.0f, 0.0f, 1);
    }
}