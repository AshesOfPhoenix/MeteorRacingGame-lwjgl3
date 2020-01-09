package main;

import engine.GUI.GUITexture;
import engine.Models.Loader3Dmodel;
import engine.Models.ObjectLoader;
import engine.Models.RawModel;
import engine.Models.TextureModel;
import engine.PowerUps.Armour;
import engine.PowerUps.PowerUp;
import engine.PowerUps.SpeedBoost;
import engine.entitete.*;
import engine.io.Input;
import engine.io.Window;
import engine.maths.Collision;
import engine.render.GUIRenderer;
import engine.render.MasterRenderer;
import engine.textures.Material;
import engine.textures.TerrainTexture;
import engine.textures.TerrainTexturePack;
import engine.textures.Texture;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

public class Main implements Runnable {
    private final int WIDTH = 1280, HEIGHT = 760;
    private Thread game;
    private Window window;

    private static float lastFrameTime;
    private static float delta;


    public static void main(String[] args) {
        new Main().start();
    }

    private static long getcurrent_time() {
        return Window.frames;
    }

    private static float getFT() {
        return delta;
    }

    private void start() {
        game = new Thread(this, "game");
        game.start();
    }

    private void init() {
        window = new Window(WIDTH, HEIGHT, "Game");
        //!Create and initialize window
        window.create();
        window.setBackgroundColor(1.0f, 1.0f, 1.0f);
        glfwSetInputMode(window.getWindow(), GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
        lastFrameTime = getcurrent_time();
        //?Read the shaders from file and Create shader program for cube
        //?Send shader to renderer for further use and create projection matrix
        //?Add different shader files for different types of objects
    }

    public enum GameStates {
        MENU, GAME, GAME_OVER
    }

    private GameStates state = GameStates.MENU;

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
            //*=================================================================
            //!ROCKS
            RawModel rockModel = ObjectLoader.loadObject("objects\\rocks", loader);
            Material materialRock = new Material(new Texture("objects\\rocks.png"), 10, 1);
            TextureModel textureRock = new TextureModel(rockModel, materialRock);
            Rocks rock;// = new Entity(textureRock, new Vector3f(200, -1f, 100), 0, 0, 0, 0.05f);
            ArrayList<Rocks> rocks = new ArrayList<>();
            for (int i = 0; i <= 500; i++) {
                float randx = (float) (Math.random() * 10000 + (-0));
                float randz = (float) (Math.random() * 5000 + (-0));
                rock = new Rocks(textureRock, new Vector3f(randx, -1f, randz), 0, 0, 0, 0.05f);
                rocks.add(rock);
            }
            //*=================================================================

            //*=================================================================
            //!POWER UPs
            RawModel protection = ObjectLoader.loadObject("objects\\Substance_Painter_Shield_003", loader);
            Material materialProtection = new Material(new Texture("objects\\viking.png"), 30, 15);
            TextureModel texturedProtection = new TextureModel(protection, materialProtection);
            Armour armour;

            RawModel speed = ObjectLoader.loadObject("objects\\10475_Rocket_Ship_v1_L3", loader);
            Material materialSpeed = new Material(new Texture("objects\\10475_Rocket_Ship_v1_Diffuse.png"), 30, 15);
            TextureModel texturedSpeed = new TextureModel(speed, materialSpeed);
            SpeedBoost speedBoost;

            //!PowerUps - HAS TO BE IN ORDER -> SpeedBoost -> Armour -> ...
            List<Entity> powerups = new ArrayList<>();
            for (int i = 0; i <= 80; i++) {
                float randx = (float) (Math.random() * 10000 + (-0));
                float randz = (float) (Math.random() * 5000 + (-0));
                float randX = (float) (Math.random() * 10000 + (-0));
                float randZ = (float) (Math.random() * 5000 + (-0));
                armour = new Armour(texturedProtection, new Vector3f(randx, 4.35f, randz), 0, 0, 0, 0.02f);
                speedBoost = new SpeedBoost(texturedSpeed, new Vector3f(randX, 0.3f, randZ), -90, 0, 0, 0.02f);
                powerups.add(speedBoost);
                powerups.add(armour);
            }

            //*=================================================================
            //!CAR
            //*=================================================================
            RawModel modelCar = ObjectLoader.loadObject("objects\\KiKicar", loader);
            Material materialCar = new Material(new Texture("objects\\demo4.png"), 10, 10);
            TextureModel texturedCar = new TextureModel(modelCar, materialCar);
            Avtomobil Car = new Avtomobil(texturedCar, new Vector3f(5000, 0, 2500), -90, 0, 180, 2.5f);
            //*=================================================================
            //!GUIs
            GUITexture backgroundGui = new GUITexture(Texture.load("mc.png"), new Vector2f(-0.89f, 0.8f), new Vector2f(0.06f, 0.10f));
            GUITexture backgroundGui2 = new GUITexture(Texture.load("mc.png"), new Vector2f(-0.89f, 0.6f), new Vector2f(0.06f, 0.10f));
            GUITexture speedBoostGui = new GUITexture(Texture.load("speedBoostEffect.png"), new Vector2f(-0.891f, 0.8f), new Vector2f(0.045f, 0.077f));
            GUITexture armourGui = new GUITexture(Texture.load("armourEffect.png"), new Vector2f(-0.891f, 0.6f), new Vector2f(0.045f, 0.077f));
            //*=================================================================
            //!TERRAIN
            //*=================================================================
            //Material materialTerrain = new Material(new Texture("asphalt-with-coarse-aggregate-texture.png"), 10, 1);
            // *********TERRAIN TEXTURE STUFF***********
            TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("asphalt.png"));
            TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("dirt.png"));
            TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("tnt.png"));
            TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("grass2.png"));
            TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
            TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blend_map.png"));
            Terrain terrain = new Terrain(0, 0, loader, texturePack, blendMap);
            //*=================================================================
            //!METEOR
            //*=================================================================
            RawModel modelMeteor = ObjectLoader.loadObject("objects\\KrizmanAsteroid", loader);
            Material materialMeteor = new Material(new Texture("objects\\demo5.png"), 10, 1);
            TextureModel texturedMeteor = new TextureModel(modelMeteor, materialMeteor);
            Meteor meteor;
            //Meteor meteor2 = new Meteor(texturedMeteor, new Vector3f(100, 0, 100), 0, 0, 0, 0.02f);

            //!METEOR RANDOMIZER
            ArrayList<Meteor> meteorcki = new ArrayList<>();
            int stevilometeorjev = 3000;
            for (int i = 0; i < stevilometeorjev; i++) {
                float randx = (float) (Math.random() * 10000 + (-0));
                float randz = (float) (Math.random() * 5000 + (-0));
                float randomsize = (float) (Math.random() * 0.05 + 0.01);
                meteor = new Meteor(texturedMeteor, new Vector3f(randx, 200, randz), 0, 0, 0, randomsize);
                meteorcki.add(meteor);
            }

            //!MAIN RENDERER FOR ALL OBJECTS
            MasterRenderer masterRenderer = new MasterRenderer();
            //!GUI RENDERER
            GUIRenderer guiRenderer = new GUIRenderer(loader);
            //!COLLISION DETECTION
            Collision collision = new Collision(Car);
            //!POWER-UPs
            PowerUp UltimatePower = new PowerUp(powerups);

            //!Initialize light source - Light Source and Color output
            Light light = new Light(new Vector3f(0, 500, 0), new Vector3f(1.0f, 0.70f, 0.70f));
            //!Initialize camera class for input readings
            Camera camera = new Camera(Car);
            //!Process Meteors



            while (!window.shouldClose() && !Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
                //!Swap buffer and Clear frame buffer from previous drawCall
                clearFrameBuffer();
                update();
                System.out.println(state);
                switch (state) {
                    case GAME:
                        //?GAME STATE ==============================================================================================
                        masterRenderer.prepareFog();
                        //!Read keyboard input
                        camera.move();
                        Car.move();

                        //!Process PowerUPs - don't render if already active
                        UltimatePower.bouncy_bouncy(); //*This plays up and down animation
                        //*This checks if the car collected any of powerups
                        UltimatePower.collectSpeedBoost(Car.getPosition());
                        UltimatePower.collectArmour(Car.getPosition());
                        //!Check if the speed boost effect is active - if yes activate speed boost and don't render speed entity
                        if (!UltimatePower.checkIfPickedUpSpeed()) {
                            Car.disableSpeedBoost();
                            for (int i = 0; i < powerups.size(); i += 2) {
                                SpeedBoost a = (SpeedBoost) powerups.get(i);
                                if (a.euclideanDistance(Car.getCenter()) < 700) {
                                    masterRenderer.processEntity(a);
                                }
                            }
                        } else {
                            Car.activateSpeedBoost();
                            guiRenderer.render(speedBoostGui);
                        }
                        //!Check if the armour effect is active - if yes activate armour and don't render armour entity
                        if (!UltimatePower.checkIfPickedUpArmour()) {
                            Car.disableArmour();
                            for (int i = 1; i < powerups.size(); i += 2) {
                                Armour a = (Armour) powerups.get(i);
                                if (a.euclideanDistance(Car.getCenter()) < 700) {
                                    masterRenderer.processEntity(a);
                                }
                            }
                        } else {
                            Car.activateArmour();
                            guiRenderer.render(armourGui);
                        }

                        //!METEOR SPAWNER AND MOVEMENT
                        for (Meteor petarda : meteorcki) {
                            if (petarda.euclideanDistance(Car.getCenter()) < 800) {
                                petarda.move();
                                masterRenderer.processEntity(petarda);
                                if (collision.CheckCollisionSphere(petarda)) {
                                    System.out.println("GAME OVER");

                                    state = GameStates.GAME_OVER;
                                }
                            }
                        }

                        //!Rock spawner
                        for (Rocks rockSPawn : rocks) {
                            if (rockSPawn.euclideanDistance(Car.getCenter()) < 700) {
                                masterRenderer.processEntity(rockSPawn);
                                if (collision.CheckCollisionSphere(rockSPawn)) {
                                    System.out.println("GAME OVER");

                                    state = GameStates.GAME_OVER;
                                }
                            }
                        }
                        guiRenderer.render(backgroundGui);
                        guiRenderer.render(backgroundGui2);
                        masterRenderer.processEntity(Car);
                        masterRenderer.processTerrain(terrain);
                        masterRenderer.render(light, camera);
                        //?GAME STATE ==============================================================================================
                        break;

                    case MENU:
                        GL30.glClearColor(0.2f, 0.5f, 0.4f, 0.9f);
                        GL30.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT);

                        break;

                    case GAME_OVER:
                        GL30.glClearColor(1.0f, 0.0f, 0.0f, 0.9f);
                        GL30.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT);
                        //!RESET
                        powerups.clear();
                        meteorcki.clear();
                        for (int i = 0; i <= 80; i++) {
                            float randx = (float) (Math.random() * 10000 + (-0));
                            float randz = (float) (Math.random() * 5000 + (-0));
                            float randX = (float) (Math.random() * 10000 + (-0));
                            float randZ = (float) (Math.random() * 5000 + (-0));
                            armour = new Armour(texturedProtection, new Vector3f(randx, 4.35f, randz), 0, 0, 0, 0.02f);
                            speedBoost = new SpeedBoost(texturedSpeed, new Vector3f(randX, 0.3f, randZ), -90, 0, 0, 0.02f);
                            powerups.add(speedBoost);
                            powerups.add(armour);
                        }
                        for (int i = 0; i < stevilometeorjev; i++) {
                            float randx = (float) (Math.random() * 10000 + (-0));
                            float randz = (float) (Math.random() * 5000 + (-0));
                            float randomsize = (float) (Math.random() * 0.05 + 0.01);
                            meteor = new Meteor(texturedMeteor, new Vector3f(randx, 200, randz), 0, 0, 0, randomsize);
                            meteorcki.add(meteor);
                        }
                        Car.setPosition(new Vector3f(5000f, 0f, 2500f));
                        break;
                }
            }
            //DESTROY
            window.destroy();
            loader.destroy();
            masterRenderer.cleanUp();
            guiRenderer.CleanUp();
            meteorcki.clear();
            powerups.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clearFrameBuffer() {
        window.swapBuffers();
    }

    private void update() {
        window.update();
        if (Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT))
            System.out.println("X: " + Input.getMouseX() + ", Y: " + Input.getMouseY());
        //primer ko pritisnemo gumb f11 se nam poveca screen na fullscreen
        if (Input.isKeyDown(GLFW.GLFW_KEY_F11)) window.setFullscreen(!window.isFullscreen());
        if (Input.isKeyDown(GLFW_KEY_SPACE)) {
            if (state == GameStates.MENU) {
                state = GameStates.GAME;
            } else if (state == GameStates.GAME_OVER) {
                state = GameStates.MENU;
            }
        }
        long currnetFT = getcurrent_time();
        delta = (currnetFT - lastFrameTime) / 100f;
    }
}