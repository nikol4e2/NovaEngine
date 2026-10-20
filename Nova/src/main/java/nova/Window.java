
package nova;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import util.Time;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {

    private int width;
    private int height;
    private String title;

    private long glfwWindow;

    private static Window window = null;
    private static Scene currentScene;

    private Window() {
        this.width = 1920;
        this.height = 1080;
        this.title = "Nova";
    }

    public static Window get() {
        if (window == null) {
            window = new Window();
        }
        return window;
    }

    public boolean shouldClose() {
        return glfwWindowShouldClose(glfwWindow);
    }

    public static void changeScene(int newScene) {
        switch (newScene) {
            case 0:
                currentScene = new LevelEditorScene();
                currentScene.init();
                break;

            case 1:
                currentScene = new LevelScene();
                currentScene.init();
                break;

            default:
                throw new IllegalArgumentException(
                        "Unknown scene: " + newScene
                );
        }
    }

    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion());

        init();
        loop();

        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        glfwTerminate();

        GLFWErrorCallback callback = glfwSetErrorCallback(null);
        if (callback != null) {
            callback.free();
        }
    }

    public void init() {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit()) {
            throw new IllegalStateException(
                    "Unable to initialize GLFW"
            );
        }

        glfwDefaultWindowHints();

        // Prepare the window before displaying it
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);

        glfwWindow = glfwCreateWindow(
                width,
                height,
                title,
                NULL,
                NULL
        );

        if (glfwWindow == NULL) {
            throw new IllegalStateException(
                    "Unable to create GLFW window"
            );
        }

        // Input callbacks
        glfwSetCursorPosCallback(
                glfwWindow,
                MouseListener::mousePosCallback
        );

        glfwSetMouseButtonCallback(
                glfwWindow,
                MouseListener::mouseButtonCallback
        );

        glfwSetScrollCallback(
                glfwWindow,
                MouseListener::mouseScrollCallback
        );

        glfwSetKeyCallback(
                glfwWindow,
                KeyListener::keyCallback
        );

        // Initialize OpenGL
        glfwMakeContextCurrent(glfwWindow);

        // Enable V-Sync
        glfwSwapInterval(1);

        GL.createCapabilities();

        // Set viewport
        glViewport(0, 0, width, height);

        // Background color
        glClearColor(0.1f, 0.1f, 0.1f, 1.0f);

        // Initialize the scene before showing the window
        Window.changeScene(0);

        // Display the prepared window
        glfwShowWindow(glfwWindow);
    }

    public void loop() {
        float beginTime = Time.getTime();
        float endTime;
        float dt = 0.0f;

        while (!glfwWindowShouldClose(glfwWindow)) {
            glfwPollEvents();

            // Clear the previous frame
            glClear(GL_COLOR_BUFFER_BIT);

            // Update and render the current scene
            if (currentScene != null) {
                currentScene.update(dt);
            }

            // Display the rendered frame
            glfwSwapBuffers(glfwWindow);

            endTime = Time.getTime();
            dt = endTime - beginTime;
            beginTime = endTime;
        }
    }
}
