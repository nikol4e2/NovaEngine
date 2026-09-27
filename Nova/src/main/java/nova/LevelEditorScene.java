package nova;

import java.awt.event.KeyEvent;

public class LevelEditorScene extends Scene {


    private boolean changingScene = false;
    private float timeToChangeScene = 2;
    public LevelEditorScene(){

    }

    @Override
    public void update(float dt) {
        if(!changingScene && KeyListener.isKeyPressed(KeyEvent.VK_ESCAPE)){
            changingScene = true;


        }

        if(changingScene && timeToChangeScene > 0){
            timeToChangeScene -= dt;

        }else if (changingScene)
        {
            Window.changeScene(1);
        }
    }
}
