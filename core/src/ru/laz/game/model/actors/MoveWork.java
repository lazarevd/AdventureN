package ru.laz.game.model.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import ru.laz.game.model.actors.MainActor.Dir;
import ru.laz.game.model.graph.NodeGame;
import ru.laz.game.model.stages.Level;
import ru.laz.game.model.things.Thing;

public class MoveWork extends Work {
	
	Array<Vector2> targetPos;
	Dir direction;
	Vector2 xy;
	
	public MoveWork (Vector2 xy, Level level) {
		super(level);
		this.xy = xy;
	}

	public MoveWork (Thing toThing, Vector2 defaultPosition, Level level) {
		super(level);
		NodeGame nod = level.getGraph().getNodes().get(toThing.getNodeName());

		if(nod == null || nod.equals("")) {//Если ноду не нашли, то идем по умолчанию
			this.xy = defaultPosition;
		} else {
			this.xy = nod.getXY();
		}
	}



	@Override
	public void init() {
		Gdx.app.log("MoveWork", "inited");
		//gameLevel.getGraph().clearNodes();
		//gameLevel.getGraph().fillNodesGrid(mActor.getX()-50, mActor.getY() - 50, 200, 100, 6, 3);
		
		level.getGraph().addStartFinishNodes(new Vector2(mActor.getX(), mActor.getY()), xy, mActor.getRenderScale());
		Gdx.app.log("Actor xy", mActor.getX() + " " + mActor.getY());
		mActor.genMovePath(xy.x, xy.y);
	setStatus(WorkStatus.PROCESS);
	}
		
	@Override
	public void act(float delta) {
		mActor.moveControl(delta);
		if (!mActor.isMoving()) {
			setStatus(WorkStatus.FINISHED);
			Gdx.app.log("Moved", "ok");
		} 

	}
}
