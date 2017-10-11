package ru.laz.game.model.actors;

import com.badlogic.gdx.math.Vector2;
import ru.laz.game.model.actors.MainActor.Dir;

/**
 * Created by Dmitry Lazarev on 20.07.2017.
 */

public class WalkData {

    private Vector2 targetPosition;
    private Dir targetDirection;
    private float renderScale;

    public WalkData(Vector2 tPos, Dir tDir, float rendScale){
        targetPosition = tPos;
        targetDirection = tDir;
        renderScale = rendScale;
    }

    public Vector2 getTargetPosition() {
        return targetPosition;
    }

    public Dir getTargetDirection() {
        return targetDirection;
    }

    public float getRenderScale() {
        return renderScale;
    }


    
}
