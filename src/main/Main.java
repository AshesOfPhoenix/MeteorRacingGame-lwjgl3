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

    //!TEST CUBE
    //*=======================
    //public Renderer renderCube;
    //public static StaticShader CubeShader;
    //*=======================
    //!CAR
    //*=======================
    public Renderer renderCar;
    public static StaticShader CarShader;
    //*=======================
    //!TERRAIN
    //*=================================================================
    //!TODO
    //*=================================================================

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

        //?Read the shaders from file and Create shader program for cube
        //?Send shader to renderer for further use and create projection matrix
        //?Add different shader files for different types of objects
        //!TEST CUBE
        //*=================================================================
        //CubeShader = new StaticShader("resources\\shaders\\mainVertexCube.glsl", "resources\\shaders\\mainFragmentCube.glsl");
        //renderCube = new Renderer(CubeShader);    //<- Projection matrix creation inside
        //*=================================================================
        //!CAR
        //*=================================================================
        CarShader = new StaticShader("resources\\shaders\\mainVertexCar.glsl", "resources\\shaders\\mainFragmentCar.glsl");
        renderCar = new Renderer(CarShader);    //<- Projection matrix creation inside
        //*=================================================================
        //!TERRAIN
        //*=================================================================
        //!TODO
        //*=================================================================
    }

    public void run() {
        try {
            init();
            //?ˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇˇ
            //!Declare loader class for loading 3D objects
            Loader3Dmodel loader = new Loader3Dmodel();

            //?Initialize raw models
            //?Initialize materials and textures
            //?Initialize textured models
            //?Initialize entities, their positions and rotations
            //!TEST CUBE
            //*=================================================================
            //RawModel modelCube = loader.loadToVAO(vertices, textureCoords, indices, normalsArray);
            //Texture textureCube = new Texture("tnt.png");
            //Material materialCube = new Material(textureCube);
            //TextureModel texturedCube = new TextureModel(modelCube, materialCube);
            //Entity Cube = new Entity(texturedCube, new Vector3f(-3, 0, -8), 7, 0, 0, 1.5f);
            //*=================================================================
            //*=================================================================
            //!CAR
            //*=================================================================
            RawModel modelCar = ObjectLoader.loadObject("10603_slot_car_blue_SG_v1_iterations-2", loader);
            Texture textureCar = new Texture("objects\\textures\\avtomobilcek.png");
            Material materialCar = new Material(textureCar);
            TextureModel texturedCar = new TextureModel(modelCar, materialCar);
            Entity Car = new Entity(texturedCar, new Vector3f(0, 0, -8), -60, -10, 200, 1);
            //*=================================================================
            //*=================================================================
            //!TERRAIN
            //*=================================================================
            //!TODO
            //*=================================================================
            //?^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^


            //!Initialize camera class for input readings
            Camera camera = new Camera();

            while (!window.shouldClose() && !Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
                //!Swap buffer and Clear frame buffer from previous drawCall
                clearFrameBuffer();
                update();
                //!Read keyboard input
                camera.move();

                //!MOVE OBJECTS - TRANSFORMATION - MODEL MATRIX
                //Car.increaseRotation(0.0f, 0.0f, 0.5f);
                //Cube.increaseRotation(1.0f, 0.0f, 1.0f);

                //!RENDERING CAR ENTITY
                //*=================================================================
                //?Init OpenGL specifications for rendering CAR
                //renderCube.prepare();
                renderCar.prepare();
                //?RENDER CAR
                CarShader.bind();
                CarShader.UniformViewMatrix(camera);      //<- Send view matrix to the shader
                renderCar.renderEntity(Car, CarShader);  //<- Transformation matrix creation inside
                CarShader.UnBind();
                //?Disable OpenGL specifications for CAR
                renderCar.disable();
                //*=================================================================
                //!TERRAIN
                //*=================================================================
                //!TODO
                //*=================================================================
            }
            //DESTROY
            window.destroy();
            loader.destroy();
            CarShader.destroy();
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