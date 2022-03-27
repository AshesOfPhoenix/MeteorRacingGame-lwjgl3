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
import engine.maths.Timmer;
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

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

public class StartGame implements Runnable {
    private final int WIDTH = 1280, HEIGHT = 760;
    private Thread game;
    private Window window;

    private static long delta;

    public static void setDelta(long delta) {
        StartGame.delta = delta;
    }

    public static void main(String[] args) {
        new StartGame().start();
    }

    private static long getcurrent_time() {
        return System.currentTimeMillis();
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
        glfwSetInputMode(window.getWindow(), GLFW_CURSOR, GLFW_CURSOR_DISABLED);

        //?Read the shaders from file and Create shader program for cube
        //?Send shader to renderer for further use and create projection matrix
        //?Add different shader files for different types of objects
    }

    public enum GameStates {
        MENU, GAME, GAME_OVER
    }

    private GameStates state = GameStates.MENU;

    private boolean chechNear(boolean[][] grid, int X, int Z) {
        boolean check = false;
        if (X < 9995 && X > 5 && Z < 4995 && Z > 5) {
            for (int i = X - 4; i < X + 4; i++) {
                for (int j = Z - 4; j < Z + 4; j++) {
                    if (grid[i][j]) {
                        check = true;
                    }
                }
            }
        }
        return check;
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
            boolean[][] grid = new boolean[10000][5000]; // X Z
            //*=================================================================
            //!ROCKS
            RawModel rockModel = ObjectLoader.loadObject("files\\objects\\rocks", loader);
            Material materialRock = new Material(new Texture("files\\textures\\rocks.png"), 10, 1);
            TextureModel textureRock = new TextureModel(rockModel, materialRock);
            Rocks rock;
            ArrayList<Rocks> rocks = new ArrayList<>();
            for (int i = 0; i <= 500; i++) {
                float randx = (float) (Math.random() * 10000);
                float randz = (float) (Math.random() * 5000);
                if (!chechNear(grid, (int) randx, (int) randz)) {
                    grid[(int) randx][(int) randz] = true;
                    rock = new Rocks(textureRock, new Vector3f(randx, -1f, randz), 0, 0, 0, 0.05f);
                    rocks.add(rock);
                }
            }
            //*=================================================================
            //!TREES
            RawModel treeModel = ObjectLoader.loadObject("files\\objects\\10460_Yellow_Poplar_Tree_v1_L3", loader);
            Material materialTree = new Material(new Texture("files\\textures\\10460_Yellow_Poplar_Tree_v1_Diffuse.png"), 10, 1);
            TextureModel textureTree = new TextureModel(treeModel, materialTree);
            Trees tree;// = new Trees(textureTree, new Vector3f(5100, -1f, 2500), -90, 0, 0, 0.020f);
            ArrayList<Trees> trees = new ArrayList<>();
            for (int i = 0; i <= 500; i++) {
                float randx = (float) (Math.random() * 10000);
                float randz = (float) (Math.random() * 5000);
                if (!chechNear(grid, (int) randx, (int) randz)) {
                    grid[(int) randx][(int) randz] = true;
                    tree = new Trees(textureTree, new Vector3f(randx, -1f, randz), -90, 0, 0, 0.020f);
                    trees.add(tree);
                }
            }
            //*=================================================================
            //!POWER UPs
            RawModel protection = ObjectLoader.loadObject("files\\objects\\armour", loader);
            Material materialProtection = new Material(new Texture("files\\textures\\armour.png"), 20, 10);
            TextureModel texturedProtection = new TextureModel(protection, materialProtection);
            Armour armour;

            RawModel speed = ObjectLoader.loadObject("files\\objects\\10475_Rocket_Ship_v1_L3", loader);
            Material materialSpeed = new Material(new Texture("files\\textures\\10475_Rocket_Ship_v1_Diffuse.png"), 20, 10);
            TextureModel texturedSpeed = new TextureModel(speed, materialSpeed);
            SpeedBoost speedBoost;

            //!PowerUps - HAS TO BE IN ORDER -> SpeedBoost -> Armour -> ...
            List<Entity> powerups = new ArrayList<>();
            for (int i = 0; i <= 80; i++) {
                float randx = (float) (Math.random() * 10000 + (-0));
                float randz = (float) (Math.random() * 5000 + (-0));
                float randX = (float) (Math.random() * 10000 + (-0));
                float randZ = (float) (Math.random() * 5000 + (-0));
                if (!chechNear(grid, (int) randx, (int) randz) && !chechNear(grid, (int) randX, (int) randZ)) {
                    grid[(int) randX][(int) randZ] = true;
                    speedBoost = new SpeedBoost(texturedSpeed, new Vector3f(randX, 0.3f, randZ), -90, 0, 0, 0.02f);
                    powerups.add(speedBoost);
                    grid[(int) randx][(int) randz] = true;
                    armour = new Armour(texturedProtection, new Vector3f(randx, 4.4f, randz), 0, 0, 0, 0.02f);
                    powerups.add(armour);
                }
            }
            //*=================================================================
            //!CAR
            RawModel modelCar = ObjectLoader.loadObject("files\\objects\\car", loader);
            Material materialCar = new Material(new Texture("files\\textures\\carTexture.png"), 10, 10);
            TextureModel texturedCar = new TextureModel(modelCar, materialCar);
            Avtomobil Car = new Avtomobil(texturedCar, new Vector3f(5000, 0, 2500), -90, 0, 180, 2.5f);

            //*=================================================================
            //!GUIs
            GUITexture backgroundGui = new GUITexture(Texture.load("files\\textures\\mc.png"), new Vector2f(-0.89f, 0.8f), new Vector2f(0.06f, 0.10f));
            GUITexture backgroundGui2 = new GUITexture(Texture.load("files\\textures\\mc.png"), new Vector2f(-0.89f, 0.6f), new Vector2f(0.06f, 0.10f));
            GUITexture speedBoostGui = new GUITexture(Texture.load("files\\textures\\speedBoostEffect.png"), new Vector2f(-0.891f, 0.8f), new Vector2f(0.045f, 0.077f));
            GUITexture armourGui = new GUITexture(Texture.load("files\\textures\\armourEffect.png"), new Vector2f(-0.891f, 0.6f), new Vector2f(0.045f, 0.077f));
            GUITexture menu = new GUITexture(Texture.load("files\\textures\\menu.png"), new Vector2f(0f, 0.0f), new Vector2f(1f, 1f));
            GUITexture over = new GUITexture(Texture.load("files\\textures\\over.png"), new Vector2f(0f, 0.0f), new Vector2f(1f, 1f));
            //*=================================================================
            //!TERRAIN
            // *********TERRAIN TEXTURE STUFF***********
            TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("files\\textures\\asphalt.png"));
            TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("files\\textures\\dirt.png"));
            TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("files\\textures\\sand.png"));
            TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("files\\textures\\grass.png"));
            TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
            TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("files\\textures\\blend_map.png"));
            Terrain terrain = new Terrain(0, 0, loader, texturePack, blendMap);
            //*=================================================================
            //!METEOR
            //*=================================================================
            RawModel modelMeteor = ObjectLoader.loadObject("files\\objects\\asteroid", loader);
            Material materialMeteor = new Material(new Texture("files\\textures\\asteroid.png"), 10, 1);
            TextureModel texturedMeteor = new TextureModel(modelMeteor, materialMeteor);
            Meteor meteor;

            //!METEOR RANDOMIZER
            ArrayList<Meteor> meteorcki = new ArrayList<>();
            int stevilometeorjev = 3100;
            for (int i = 0; i < stevilometeorjev; i++) {
                float randx = (float) (Math.random() * 10000);
                float randz = (float) (Math.random() * 5000);
                float randy = (float) ((Math.random() * 500) + 350);
                float randomsize = (float) (Math.random() * 0.05 + 0.01);
                meteor = new Meteor(texturedMeteor, new Vector3f(randx, randy, randz), 0, 0, 0, randomsize);
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
            Light light = new Light(new Vector3f(0, 500, 0), new Vector3f(0.7f, 0.50f, 0.50f));
            //!Initialize camera class for input readings
            Camera camera = new Camera(Car);
            //!Process Meteors
            Timmer.start();
            boolean levelUP = false;
            int timeBetweenLevels = 0;
            float meteorSpeed = 0.8f;
            VelikoMeteorjev UltimateDestruction = new VelikoMeteorjev(meteorcki, texturedMeteor);

            //!MAIN GAME LOOP
            while (!window.shouldClose() && !Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
                //!Swap buffer and Clear frame buffer from previous drawCall
                clearFrameBuffer();
                update();
                switch (state) {
                    case GAME:
                        //?GAME STATE ==============================================================================================
                        masterRenderer.prepareFog();
                        //!Read keyboard input
                        camera.move();
                        Car.move();
                        if (Car.isGAME_OVER()) {
                            state = GameStates.GAME_OVER;
                            Car.setGAME_OVER(false);
                        }
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
                        if (Timmer.getTime() == 30 + timeBetweenLevels) {
                            timeBetweenLevels += 26;
                            stevilometeorjev += 1000;
                            meteorSpeed += 0.8;
                            UltimateDestruction.updateCount(stevilometeorjev, meteorSpeed);
                        } else {
                            UltimateDestruction.setLevelUp(false);
                        }
                        //!METEOR SPAWNER AND MOVEMENT
                        for (Meteor petarda : UltimateDestruction.meteorji) {
                            if (petarda.euclideanDistance(Car.getCenter()) < 1100) {
                                petarda.move(Car.getPosition());
                                if (petarda.euclideanDistance(Car.getCenter()) < 800) {
                                    masterRenderer.processEntity(petarda);
                                    if (collision.CheckCollisionSphere(petarda)) {
                                        if (!Car.isArmour()) {
                                            System.out.println("GAME OVER");
                                            setDelta(getcurrent_time());
                                            state = GameStates.GAME_OVER;
                                        }
                                    }
                                }
                            }
                        }
                        //!Rock spawner
                        for (Rocks rockSPawn : rocks) {
                            if (rockSPawn.euclideanDistance(Car.getCenter()) < 600) {
                                masterRenderer.processEntity(rockSPawn);
                                if (collision.CheckCollisionSphere(rockSPawn)) {
                                    if (!Car.isArmour()) {
                                        System.out.println("GAME OVER");
                                        setDelta(getcurrent_time());
                                        state = GameStates.GAME_OVER;
                                    }
                                }
                            }
                        }
                        //!TREE SPAWNER
                        for (Trees treeSpawn : trees) {
                            if (treeSpawn.euclideanDistance(Car.getCenter()) < 700) {
                                masterRenderer.processEntity(treeSpawn);
                                if (collision.CheckCollision(treeSpawn)) {
                                    treeSpawn.setRotX(-180);
                                }
                            }
                        }
                        guiRenderer.render(backgroundGui);
                        guiRenderer.render(backgroundGui2);
                        masterRenderer.processEntity(Car);
                        masterRenderer.processTerrain(terrain);
                        masterRenderer.render(light, camera);
                        //?GAME STATE ==============================================================================================
                        setDelta(getcurrent_time());
                        break;
                    case MENU:
                        GL30.glClearColor(0.2f, 0.5f, 0.4f, 0.9f);
                        GL30.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT);
                        if ((getcurrent_time() - delta) > 1000) {
                            if (Input.isKeyDown(GLFW_KEY_SPACE)) {
                                state = GameStates.GAME;
                            }
                        }
                        guiRenderer.render(menu);
                        break;

                    case GAME_OVER:
                        GL30.glClearColor(1.0f, 0.0f, 0.0f, 0.9f);
                        GL30.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT);
                        guiRenderer.render(over);
                        //!RESET
                        timeBetweenLevels = 0;
                        meteorSpeed = 0.8f;
                        stevilometeorjev = 3200;
                        UltimatePower.cleanUp();
                        powerups.clear();
                        meteorcki.clear();
                        Car.resetCarSpeed();
                        UltimateDestruction.setLevelUp(false);
                        for (int i = 0; i <= 80; i++) {
                            armour = new Armour(texturedProtection, UltimatePower.resetArmourLocation(), 0, 0, 0, 0.02f);
                            speedBoost = new SpeedBoost(texturedSpeed, UltimatePower.resetSpeedLocation(), -90, 0, 0, 0.02f);
                            powerups.add(speedBoost);
                            powerups.add(armour);
                        }
                        Car.setPosition(new Vector3f(5000f, 0f, 2500f));
                        UltimatePower.resetInterpolation();
                        UltimatePower.update(powerups);
                        UltimateDestruction.update(stevilometeorjev);
                        if ((getcurrent_time() - delta) > 1000) {
                            if (Input.isKeyDown(GLFW_KEY_SPACE)) {
                                state = GameStates.MENU;
                                setDelta(getcurrent_time());
                            }
                        }
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
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
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
        if (Input.isKeyDown(GLFW.GLFW_KEY_ENTER)) glfwSetInputMode(window.getWindow(), GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
    }
}