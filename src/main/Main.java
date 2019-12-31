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

        //!Read the shaders from file and Create shader program for cube
        //?Add different shader files for different types of objects
        CubeShader = new StaticShader("resources\\shaders\\mainVertexCube.glsl",
                "resources\\shaders\\mainFragmentCube.glsl");

        //!Send shader to renderCube for further use and create projection matrix
        //?Create different renderer for every shader
        renderCube = new Renderer(CubeShader);    //<- Projection matrix creation inside
    }

    public void run() {
        try {
            init();
            //!ONE OBJECT
            //ˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇ
            //*Declare loader class for loading 3D objects
            Loader3Dmodel loader = new Loader3Dmodel();

            //*Initialize raw models
            RawModel modelCar = ObjectLoader.loadObject("objects\\10608_Tonka_Fire_Truck_SG_v1_L3", loader);
            //RawModel modelCube = loader.loadToVAO(vertices, textureCoords, indices, normalsArray);

            //*Initialize materials and textures
            //?Different for every object
            Texture textureCube = new Texture("objects\\Futuristic_Car_C4.png");
            Material materialCube = new Material(textureCube);

            //*Initialize textured models
            //?Different for every object
            TextureModel texturedCar = new TextureModel(modelCar, materialCube);
            //TextureModel texturedCube = new TextureModel(modelCube, materialCube);

            //*Initialize entities, their positions and rotations
            //?For every entity you want rendered
            Entity Car = new Entity(texturedCar, new Vector3f(2, 0, -8), -60, -10, 200, 1);
            //Entity Cube = new Entity(texturedCube, new Vector3f(-3, 0, -8), 7, 0, 0, 1.5f);
            //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

            //!Initialize camera class for input readings
            Camera camera = new Camera();

            while (!window.shouldClose() && !Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
                //!Swap buffer and Clear frame buffer from previous drawCall
                clearFrameBuffer();
                update();
                //!Read keyboard input
                camera.move();

                //!Init OpenGL specifications
                renderCube.prepare();

                //!MOVE OBJECTS - TRANSFORMATION - MODEL MATRIX
                //Car.increaseRotation(0.0f, 0.0f, 0.5f);
                //Cube.increaseRotation(1.0f, 0.0f, 1.0f);

                //!RENDER OBJECTS
                CubeShader.bind();
                CubeShader.UniformViewMatrix(camera);      //<- Send view matrix to the shader
                renderCube.renderEntity(Car, CubeShader);  //<- Transformation matrix creation inside
                //renderCube.renderEntity(Cube, CubeShader);
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

    private void clearFrameBuffer() {
        window.swapBuffers();
        GL30.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT);
        GL30.glClearColor(0, 0.0f, 0.0f, 1);
    }

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
}