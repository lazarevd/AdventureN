package ru.laz.game.model.actors;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.EnumMap;

import ru.laz.game.model.graph.NodeGame;
import ru.laz.game.model.math.MathGame;
import ru.laz.game.model.stages.Location;
import ru.laz.game.view.render.RenderObject;


public class MainActor extends Actor implements RenderObject {

    private float oX, oY;	//idle position
    private final float RENDER_SHIFT_X = 0;
    private final float RENDER_SHIFT_Y = -0;
	private float renderWidth = 88;
	private float renderHeight = 176;
	private float parallaxFactor = 0;
    private transient float speed;
	private float renderScale = 1.0f;
	private float scaleFactor = 1.0f; //этим параметром можем подкорректировать размер для всей сцены
	private float zDepth;

	private transient Array<Work> works;
    //private Array<Vector2> targetPositions;
    //private Array<Dir> targetDirections;//все направления актера
	private transient Array<WalkData> targetWalkDatas;
    private transient Location location;
	private transient boolean isMoving;
	public transient Array<Vector2> printVec;//DEBUG
	public transient TextureRegion currentFrame;
	private transient EnumMap<Dir, Animation> moveAniSet; //Набор анимаций движения для 8 сторон.
	private transient Dir direction;
	enum Dir {L,FL,F,FR,R,BR,B,BL};
	transient float  stateTime;
	private transient Animation currentAnimation;   //default static texture
	private transient TextureRegion currentTexture; //animation
	private transient TextureRegion renderTexture; //return this to render

	private MainActor() {
		this.zDepth = 1;
		targetWalkDatas = new Array<WalkData>();
		targetWalkDatas.ordered = true;
		this.isMoving = false;
		this.speed = 2;
		this.direction = Dir.L;
		moveAniSet = defineMoveAnimations();//Грузим все анимации походки
		works = new Array<Work>();
		currentFrame = getCurrentFrameTexture();
		printVec = new Array<Vector2>();//DEBUG
	}

    public void setLocation(Location location) {
        this.location = location;
    }

    public MainActor(Location location, float x, float y, float zDepth, float scaleFactor) {
		this();
		this.location = location;
		this.oX = x;
		this.oY = y;
		this.zDepth = zDepth;
		this.scaleFactor = scaleFactor;
	}


	@Override
	public void setPosition(float x, float y, float zDepth) {
		this.oX = x;
		this.oY = y;
		this.zDepth = zDepth;

	}


	@Override
	public TextureRegion getCurrentAnimationTexture() {
		return this.currentFrame;
	}

	@Override
	public void setParallaxFactor(float parallaxFactor) {
	this.parallaxFactor = parallaxFactor;
	}

	@Override
	public float getParallaxFactor() {
		return this.parallaxFactor;
	}


	public TextureRegion getCurrentFrameTexture() {
		return (TextureRegion) moveAniSet.get(direction).getKeyFrame(stateTime*0.3f, true);
	}

	public float getRenderScale() {
		return renderScale;
	}

	public boolean isMoving() {
		return isMoving;
	}

	public void setMoving(boolean moving) {
		isMoving = moving;
	}


	public void genMovePath(float x, float y) {//Fills path (targetPos array)

		location.getGraph().updateStatus();//Update edges, etc..

		ArrayList<String> astarPath = location.getGraph().AStarSearch();// Запускаем расчет AStar
		targetWalkDatas.clear();

		NodeGame curNode = location.getGraph().nodes.get("start");

		for (String nod : astarPath) {
			//Цикл по всем нодам из astar
			NodeGame nexNode = location.getGraph().nodes.get(nod);

			Array<Vector2> moveVectors = MathGame.separateVector(new Vector2(curNode.getX(),curNode.getY()), new Vector2(nexNode.getX(), nexNode.getY()), speed);
			Array<Float> scales = MathGame.getArrayOfFloats(curNode.getRenderScale(), nexNode.getRenderScale(), moveVectors.size);
			Gdx.app.log("renderScale ", renderScale + " " + nexNode.getRenderScale());


           // for (int i = 0; i < (int)k; i++) {						//Fill path array with "speed" vectors by adding it in a cycle
				for(int i = 0; i < moveVectors.size; i++) {
				Vector2 speedVec = moveVectors.get(i);
				Dir dir = Dir.L;
				if(targetWalkDatas.size > 1) {
					dir = detectDirection(this.targetWalkDatas.get(this.targetWalkDatas.size-2).getTargetPosition(), new Vector2(speedVec));
				}
				targetWalkDatas.add(new WalkData(speedVec, dir, scales.get(i)));
			}

			curNode = nexNode;
		}
		if (targetWalkDatas.size > 0) {
			isMoving = true;
		}

	}

	public EnumMap<Dir, Animation> defineMoveAnimations() {

		Texture animationSheet;
		Animation walkAnimation;
		TextureRegion[] walkFrames;

		EnumMap<Dir, Animation> ret = new EnumMap<Dir, Animation>(Dir.class);

		for(Dir dir : Dir.values()) {
			String filename = "mary/left.png";

			switch (dir) {
				case L : filename = "mary/left.png"; break;
				case FL : filename = "mary/fleft.png"; break;
				case F : filename = "mary/front.png"; break;
				case FR : filename = "mary/fright.png"; break;
				case R : filename = "mary/right.png"; break;
				case BR : filename = "mary/bright.png"; break;
				case B : filename = "mary/back.png"; break;
				case BL : filename = "mary/bleft.png"; break;
			}

			animationSheet = new Texture(Gdx.files.internal(filename));
			walkFrames = genTextureRegion(animationSheet, 8,4);
			walkAnimation = new Animation(0.025f, walkFrames);
			ret.put(dir, walkAnimation);
		}



		return ret;
	}


	public void moveControl(float delta) {
		if (isMoving == true) {
			location.QSortRender();//Сортируем слои, чтобы актер перекрывал те что дальше него и перекрывался теми что ближе
			if (targetWalkDatas.size > 0) {
					WalkData twd = targetWalkDatas.first();
					oX = twd.getTargetPosition().x;
					oY = twd.getTargetPosition().y;
					direction = twd.getTargetDirection();
					renderScale = twd.getRenderScale();
				targetWalkDatas.removeValue(twd, true);
				stateTime += delta;
			}
		}

		if (targetWalkDatas.size == 0) {
			isMoving = false;
		}
	}



	public Dir detectDirection(Vector2 curPos, Vector2 dirXY) {
		Dir ret = Dir.L;
		float angle = 0;
		Vector2 res = MathGame.lineToVector(curPos, dirXY);
		angle = res.angle();


		if ((angle >= 337.5 && angle <= 360) || (angle >= 0 && angle < 22.5)) ret = Dir.R;
		else if (angle >= 22.5 && angle < 67.5) ret = Dir.BR;
		else if (angle >= 67.5 && angle < 112.5) ret = Dir.B;
		else if (angle >= 112.5 && angle < 157.5) ret = Dir.BL;
		else if (angle >= 157.5 && angle < 202.5) ret = Dir.L;
		else if (angle >= 202.5 && angle < 247.5) ret = Dir.FL;
		else if (angle >= 247.5 && angle < 292.5) ret = Dir.F;
		else if (angle >= 292.5 && angle < 337.5) ret = Dir.FR;
		return ret;

	}



	@Override
	public void act(float delta) {
		//Здесь получается, что встроенный batch мы не используем, т.к. с ним неудобно работать. Используем один Batch из класса Render.
		currentFrame = getCurrentFrameTexture();
		if(works.size > 0) {
			int i = works.size-1;
			Work work = works.get(i);
			if (WorkProcessor.processWork(work, delta)) {
				works.removeIndex(i);
				work.setActor(null);
			}
		}
	}


	public TextureRegion[] genTextureRegion(Texture sheet, int cols, int rows) {

		TextureRegion[] walkFrames;
		TextureRegion[][] tmp = TextureRegion.split(sheet, 128, 256);

		walkFrames = new TextureRegion[cols * rows];
		int index = 0;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				walkFrames[index++] = tmp[i][j];
			}
		}
		return walkFrames;
	}

	public void clearWorks() {
		works.clear();
	}

	public void addWork (Work work) {
		works.add(work);
		work.setActor(this);
	}


	public void removeWork (Work work) {
		if (works.removeValue(work, true)) work.setActor(null);
	}


	@Override
	public void draw(Batch batch, float alpha) {
		//Метод отрисовки перенесен в act(), т.к. тут есть неявные вызовы batch
		//Это мешает отрисовывать актера с помощью своего batch.
	}


	public float getZDepth() {
		return this.zDepth;
	}



	public void setRenderScale(float sc) {
		this.renderScale = sc;

	}


	public float getRenderHeight() {
        return this.renderHeight * renderScale*scaleFactor;
	}


	public float getRenderWidth() {
        return this.renderWidth * renderScale*scaleFactor;
	}

	@Override
	public void setPosition(float x, float y) {
		this.oX = x;
		this.oY = y;
	}

	@Override
	public float getX(){return this.oX;}//override to use our variables oX, oY

	@Override
	public float getY(){return this.oY;}


	@Override
	public float getRenderX() {
		return this.oX+RENDER_SHIFT_X;
	}

	@Override
	public float getRenderY() {
		return this.oY+RENDER_SHIFT_Y;
	}


	@Override
	public void setRenderWidth(float renderWidth) {
		this.renderWidth = renderWidth;
	}

	@Override
	public void setRenderHeight(float renderHeight) {
		this.renderHeight = renderHeight;
	}

	@Override
	public String getCurrentTextureName() {
		return null;
	}

	@Override
	public String getCurrentAnimationName() {
		return null;
	}

	@Override
	public void setCurrentAnimation(Animation animation) {
		this.currentAnimation = animation;
	}

	@Override
	public void setCurrentTextureName(String textureName) {

	}

	@Override
	public void setCurrentTexture(TextureRegion textureRegion) {
		this.currentTexture = textureRegion;
	}

}
