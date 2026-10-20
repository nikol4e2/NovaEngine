package nova;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Camera {

    private Matrix4f projectionMatrix, viewMatrix;
    private Vector2f position;


    public Camera(Vector2f position)
    {
        this.position = position;
        this.projectionMatrix = new Matrix4f();
        this.viewMatrix = new Matrix4f();
        adjustProjection();
    }

    //adjust if screen res changes
    public void adjustProjection() {
        projectionMatrix.identity();
        projectionMatrix.ortho(
                -1.0f, 1.0f,
                -1.0f, 1.0f,
                0.1f, 100.0f
        );
    }



    public Matrix4f getViewMatrix() {
        Vector3f cameraPosition =
                new Vector3f(position.x, position.y, 10.0f);

        Vector3f cameraTarget =
                new Vector3f(position.x, position.y, 0.0f);

        Vector3f cameraUp =
                new Vector3f(0.0f, 1.0f, 0.0f);

        viewMatrix.identity();
        viewMatrix.lookAt(cameraPosition, cameraTarget, cameraUp);

        return viewMatrix;
    }
    public Matrix4f getProjectionMatrix() {
        return this.projectionMatrix;
    }
}
