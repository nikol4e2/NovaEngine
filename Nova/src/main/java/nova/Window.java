package nova;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {

    private int  width, height;

    private String  title;

    private Long glfwWindow; // memory address


    //Singleton patterin
    private static Window window=null;


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

    public void run(){
        System.out.println("Hello LWJGL"+ Version.getVersion());
        init();
        loop();


        //Free memory

        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);


        //Terminate GLFW and free the error callback

        glfwTerminate();
        glfwSetErrorCallback(null).free();


    }


    public void init() {
        //Setup an error callback

        GLFWErrorCallback.createPrint(System.err).set(); // creates printing method to where to print errors


        //Intilize GLWF
        if(!glfwInit())
        {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        //Configure GLWF
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);


        //Create the window
        glfwWindow = glfwCreateWindow(
                width,
                height,
                title,
                NULL,
                NULL
        );
        if(glfwWindow==null)
        {
            throw new IllegalStateException("Unable to create GLFW window");
        }

        glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
        glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);

        glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);


        //Make the OpenGl context current
        glfwMakeContextCurrent(glfwWindow);

        //Enable v-sync
        glfwSwapInterval(1);


        //Make the window visible
        glfwShowWindow(glfwWindow);


        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use
        GL.createCapabilities();
    }

    public void loop() {
        while(!glfwWindowShouldClose(glfwWindow)) {
            //Poll events
            glfwPollEvents();

          

            glClearColor(1.0f,0.0f,0.0f, 1.0f);
            glClear(GL_COLOR_BUFFER_BIT);

            glfwSwapBuffers(glfwWindow);
        }

    }

}
