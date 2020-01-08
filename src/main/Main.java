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
            RawModel protection = ObjectLoader.loadObject("objects\\Substance_Painter_Shield_003", loader);
            Material materialProtection = new Material(new Texture("objects\\viking.png"), 30, 15);
            TextureModel texturedProtection = new TextureModel(protection, materialProtection);
            Armour armour = new Armour(texturedProtection, new Vector3f(100, 4.35f, 100), 0, 0, 0, 0.02f);

            RawModel speed = ObjectLoader.loadObject("objects\\10475_Rocket_Ship_v1_L3", loader);
            Material materialSpeed = new Material(new Texture("objects\\10475_Rocket_Ship_v1_Diffuse.png"), 30, 15);
            TextureModel texturedSpeed = new TextureModel(speed, materialSpeed);
            SpeedBoost speedBoost = new SpeedBoost(texturedSpeed, new Vector3f(100, 0.3f, 110), -90, 0, 0, 0.02f);
            //*=================================================================
            //!CAR
            //*=================================================================
            RawModel modelCar = ObjectLoader.loadObject("objects\\KiKicar", loader);
            Material materialCar = new Material(new Texture("objects\\demo4.png"), 10, 10);
            TextureModel texturedCar = new TextureModel(modelCar, materialCar);
            Avtomobil Car = new Avtomobil(texturedCar, new Vector3f(4, 0, 4), -90, 0, 180, 2.5f);
            //*=================================================================
            //!GUIs
            GUITexture speedBoostGui = new GUITexture(Texture.load("speedBoostEffect.png"), new Vector2f(-0.89f, 0.8f), new Vector2f(0.06f, 0.10f));
            GUITexture armourGui = new GUITexture(Texture.load("armourEffect.png"), new Vector2f(-0.89f, 0.6f), new Vector2f(0.06f, 0.10f));

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
            Meteor meteor2 = new Meteor(texturedMeteor, new Vector3f(100, 0, 100), 0, 0, 0, 0.02f);

            //!METEOR RANDOMIZER
            ArrayList<Meteor> meteorcki = new ArrayList<>();
            int stevilometeorjev = (int) (Math.random() * 10 * 45);
            for (int i = 0; i < stevilometeorjev; i++) {
                float randx = (float) (Math.random() * 750 + (-200));
                float randz = (float) (Math.random() * 750 + (-200));
                float randomsize = (float) (Math.random() * 0.1 + 0.01);
                meteor = new Meteor(texturedMeteor, new Vector3f(randx, 200, randz), 0, 0, 0, randomsize);
                meteorcki.add(meteor);
            }

            //!MAIN RENDERER FOR ALL OBJECTS
            MasterRenderer masterRenderer = new MasterRenderer();
            //!GUI RENDERER
            GUIRenderer guiRenderer = new GUIRenderer(loader);

            //!PowerUps - HAS TO BE IN ORDER -> SpeedBoost -> Armour -> ...
            List<Entity> powerups = new ArrayList<>();
            powerups.add(speedBoost);
            powerups.add(armour);
            PowerUp UltimatePower = new PowerUp(powerups);

            Collision collision = new Collision(Car);
            //!Initialize light source - Light Source and Color output
            Light light = new Light(new Vector3f(0, 500, 0), new Vector3f(1.0f, 0.70f, 0.70f));
            //!Initialize camera class for input readings
            Camera camera = new Camera(Car);
            int indeks = 0;
            ArrayList<Meteor> gibanje = new ArrayList<>();
            gibanje.add(meteorcki.get(indeks));
            while (!window.shouldClose() && !Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
                //!Swap buffer and Clear frame buffer from previous drawCall
                clearFrameBuffer();
                update();
                masterRenderer.prepareFog();
                //!Read keyboard input
                camera.move();
                Car.move();

                //?METEOR SPAWNER AND MOVEMENT
                for (int i = 0; i < gibanje.size(); i++) {
                    meteor = gibanje.get(i);
                    meteor.move();
                    //*Render meteor
                    masterRenderer.processEntity(meteor);
                    if (gibanje.get(indeks).getPosition().y <= 0 && meteorcki.size() - 1 > indeks) {
                        indeks++;
                        gibanje.add(meteorcki.get(indeks));
                    }
                    Car.colisiondetection(gibanje.get(indeks));
                }

                //!Process PowerUPs - don't render if already active
                UltimatePower.bouncy_bouncy(); //*This plays up and down animation
                //*This checks if the car collected any of powerups
                UltimatePower.collectSpeedBoost(Car.getPosition());
                UltimatePower.collectArmour(Car.getPosition());
                //!Check if the speed boost effect is active - if yes activate speed boost and don't render speed entity
                if (!UltimatePower.checkIfPickedUpSpeed()) {
                    Car.disableSpeedBoost();
                    masterRenderer.processEntity(speedBoost);
                } else {
                    Car.activateSpeedBoost();
                    guiRenderer.render(speedBoostGui);
                }
                //!Check if the armour effect is active - if yes activate armour and don't render armour entity
                if (!UltimatePower.checkIfPickedUpArmour()) {
                    Car.disableArmour();
                    masterRenderer.processEntity(armour);
                } else {
                    Car.activateArmour();
                    guiRenderer.render(armourGui);
                }

                //System.out.println("SpeedBoost -> Active:" + speedBoost.isActive());
                //System.out.println("Armour -> Active:" + armour.isActive());
                System.out.println(collision.CheckCollisionSphere(meteor2));
                masterRenderer.processEntity(meteor2);

                masterRenderer.processEntity(Car);
                masterRenderer.processTerrain(terrain);
                masterRenderer.render(light, camera);

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
        long currnetFT = getcurrent_time();
        delta = (currnetFT - lastFrameTime) / 100f;
    }

}