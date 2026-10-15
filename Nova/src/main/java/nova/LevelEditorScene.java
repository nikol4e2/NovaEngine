package nova;

import org.lwjgl.BufferUtils;

import java.awt.event.KeyEvent;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.ARBVertexArrayObject.glBindVertexArray;
import static org.lwjgl.opengl.ARBVertexArrayObject.glGenVertexArrays;
import static org.lwjgl.opengl.GL20.*;

public class LevelEditorScene extends Scene {


    private String vertexShaderSrc = "#version 330 core\n" +
            "layout (location=0) in vec3 aPos;\n" +
            "layout (location=1) in vec4 aColor;\n" +
            "\n" +
            "out vec4 fColor;\n" +
            "\n" +
            "void main(){\n" +
            "    fColor= aColor;\n" +
            "    gl_Position=vec4(aPos,1.0);\n" +
            "}\n";

    private String fragmentShaderSrc = "#version 330 core\n" +
            "in vec4 fColor;\n" +
            "\n" +
            "out vec4 color;\n" +
            "\n" +
            "void main(){\n" +
            "\n" +
            "    color=fColor;\n" +
            "\n" +
            "}";



    private int vertexId , fragmentId, shaderProgram;


    private float [] vertexArray={
      //position     //color
            0.5f, -0.5f, 0.0f,      1.0f, 0.0f, 0.0f, 1.0f, //bottom right
            -0.5f, -0.5f, 0.0f,     0.0f, 1.0f, 0.0f, 1.0f, //top left
            0.5f, 0.5f, 0.0f,       0.0f, 0.0f, 1.0f, 1.0f, // top right
            -0.5f, -0.5f, 0.0f,     1.0f, 1.0f, 0.0f, 1.0f, //bottom left
    };

    //IMPORTANT: Must be in counter-clockwise order
    private int [] elementArray={
            2,1,0, //Top right triangle
            0,1,3 //bottom left triangle
    };

    private int vaoId, vboId, eboId;




    public LevelEditorScene(){

    }


    @Override
    public void init() {
        //Compile and link shader
        //First load and compile the vertex shader

        vertexId = glCreateShader(GL_VERTEX_SHADER);


        //Pass the shader source to the GPU
        glShaderSource(vertexId, vertexShaderSrc);
        glCompileShader(vertexId);

        //Check for errors in compilation

        int success = glGetShaderi(vertexId, GL_COMPILE_STATUS);
        if( success == GL_FALSE)
        {
            int len = glGetShaderi(vertexId, GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: 'defaultShader.glsl'\n\tVertex shader compilation failed");
            System.out.println(glGetShaderInfoLog(vertexId, len));
            assert false : ""; // just exits the app


        }




        fragmentId = glCreateShader(GL_FRAGMENT_SHADER);


        //Pass the shader source to the GPU
        glShaderSource(fragmentId, fragmentShaderSrc);
        glCompileShader(fragmentId);

        //Check for errors in compilation

         success = glGetShaderi(fragmentId, GL_COMPILE_STATUS);
        if( success == GL_FALSE)
        {
            int len = glGetShaderi(fragmentId, GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: 'defaultShader.glsl'\n\tFragment shader compilation failed");
            System.out.println(glGetShaderInfoLog(fragmentId, len));
            assert false : "";


        }


        //Link shaders and check for errors
        shaderProgram = glCreateProgram();
        glAttachShader(shaderProgram, vertexId);
        glAttachShader(shaderProgram, fragmentId);
        glLinkProgram(shaderProgram);

        //Check for linking programs
        success = glGetProgrami(shaderProgram, GL_LINK_STATUS);
        if( success == GL_FALSE)
        {
            int len = glGetProgrami(shaderProgram, GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: 'defaultShader.glsl'\n\tLinking shaders failed");
            System.out.println(glGetProgramInfoLog(shaderProgram, len));
            assert false : "";
        }


        vaoId=glGenVertexArrays();
        glBindVertexArray(vaoId);


        //Create a float buffer of verticies

        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertexArray.length);
        vertexBuffer.put(vertexArray).flip();


        //Create VBO upload the vertex buffer
        vboId=glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboId);



    }

    @Override
    public void update(float dt) {

    }
}
