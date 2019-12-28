package main;

import engine.graphics.Mesh;
import engine.graphics.Renderer;
import engine.graphics.Shader;
import engine.graphics.Vertex;
import engine.io.Input;
import engine.io.Window;
import engine.maths.Vector3f;
import org.lwjgl.glfw.GLFW;

import java.io.IOException;

public class Main implements Runnable {
    private final int WIDTH = 1280, HEIGHT = 760;
    private Thread game;
    private Window window;
    private Renderer renderer;
    private Shader shader;
    private Mesh mesh =
            new Mesh(
            new Vertex[]{
                new Vertex(new Vector3f(-0.5f, 0.5f, 0.0f)),
                new Vertex(new Vector3f(-0.5f, -0.5f, 0.0f)),
                new Vertex(new Vector3f(0.5f, -0.5f, 0.0f)),
                new Vertex(new Vector3f(0.5f, 0.5f, 0.0f))
    }, new int[]{
            0, 1, 2,
            0, 3, 2
    });

    //!START GAME
    public static void main(String[] args) {
        new Main().start();
    }

    //!Open new thread and game start
    public void start() {
        game = new Thread(this, "game");
        game.start();
    }

    //!Initialize
    public void init() throws IOException {
        window = new Window(WIDTH, HEIGHT, "Game");
        shader = new Shader("bin\\shaders\\mainVertex.glsl", "bin\\shaders\\mainFragment.glsl");
        renderer = new Renderer(shader);
        window.setBackgroundColor(1.0f, 0, 0);
        window.create();
        mesh.create();
        shader.create();
    }

    //!Run loop
    public void run() {
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (!window.shouldClose() && !Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
            update();
            render();
            if (Input.isKeyDown(GLFW.GLFW_KEY_F11)) window.setFullscreen(!window.isFullscreen());
        }
        window.destroy();
    }

    //!Update every frame
    private void update() {
        window.update();
        if (Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT))
            System.out.println("X: " + Input.getScrollX() + ", Y: " + Input.getScrollY());
    }

    //!Render mesh every frame
    private void render() {
        renderer.renderMesh(mesh);
        window.swapBuffers();
    }
}