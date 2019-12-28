package main;

import engine.graphics.Mesh;
import engine.graphics.Renderer;
import engine.graphics.Shader;
import engine.graphics.Vertex;
import engine.io.Input;
import engine.io.Window;
import engine.maths.Vector3f;
import org.lwjgl.glfw.GLFW;

public class Main implements Runnable {
    public final int WIDTH = 1280, HEIGHT = 760;
    public Thread game;
    public Window window;
    public Renderer renderer;
    public Shader shader;
    public Mesh mesh = new Mesh(new Vertex[]{
            new Vertex(new Vector3f(-0.5f, 0.5f, 0.0f)),
            new Vertex(new Vector3f(-0.5f, -0.5f, 0.0f)),
            new Vertex(new Vector3f(0.5f, -0.5f, 0.0f)),
            new Vertex(new Vector3f(0.5f, 0.5f, 0.0f))
    }, new int[]{
            0, 1, 2,
            0, 3, 2
    });

    public static void main(String[] args) {
        new Main().start();
    }

    public void start() {
        game = new Thread(this, "game");
        game.start();
    }

    public void init() {
        window = new Window(WIDTH, HEIGHT, "Game");
        shader = new Shader("D:/java/MeteorRacingGame-lwjgl3/resources/mainVertex.glsl", "D:/java/MeteorRacingGame-lwjgl3/resources/mainFragment.glsl");
        renderer = new Renderer(shader);
        window.setBackgroundColor(1.0f, 0, 0);
        window.create();
        mesh.create();
        shader.create();
    }

    public void run() {
        init();
        while (!window.shouldClose() && !Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
            update();
            render();
            if (Input.isKeyDown(GLFW.GLFW_KEY_F11)) window.setFullscreen(!window.isFullscreen());
        }
        window.destroy();
    }

    private void update() {
        window.update();
        if (Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT))
            System.out.println("X: " + Input.getScrollX() + ", Y: " + Input.getScrollY());
    }

    private void render() {
        renderer.renderMesh(mesh);
        window.swapBuffers();
    }
}