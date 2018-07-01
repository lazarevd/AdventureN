package ru.laz.game.model.things;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;

import ru.laz.game.controller.ThingContainer;
import ru.laz.game.model.graph.Polygon4Game;
import ru.laz.game.model.stages.Level;
import ru.laz.game.view.render.RenderObject;

public class Thing implements RenderObject {//Наследуем от Group т.к. там есть удобные методы для трансформации объектов с помощью матриц

	private float oX;
	private float oY;


	private float xShift;
	private float yShift;
	private float width = 30;
	private float height = 30;
    private float renderWidth = 30;
	private float renderHeight = 30;
	public float zDepth = 0;
	private Matrix4 transformMatrix;
	private boolean canBeTaken = false;
	private float parallaxFactor = 0.0f;
	private String nodeName = "";

	private ThingAction actOnClick = null;
	private ThingActionThing actWithObject = null;
	private String getInteractionThing = "";

    private TextureRegion actorTex;
	public Array<Polygon4Game> bodyPolys;//Coordinates in local system

	private HashMap<String, Animation> animations = new HashMap<String, Animation>();
	private Animation currentAnimation = null;
	private float animStateTime = 0;



	private boolean isDone = false; //for story lifeline

	Level level;
	//private float heigth;


	public Thing(float x, float y, float zDepth, float h, float w, String nodeName, TextureRegion texture,  Level level) {
		this.setX(x);
		this.setY(y);
		this.width = w;
		this.height = h;
		this.nodeName = nodeName;
		this.renderHeight = h;
		this.renderWidth = w;
		this.zDepth = zDepth;
		bodyPolys = new Array<Polygon4Game>();
		this.level = level;
		this.actorTex = texture;
		defineBody();
		actOnClick = new ThingAction() {
			@Override
			public void run(Thing thisThing) {
				Gdx.app.log(thisThing.nodeName, "Click base action");
			}
		};
		actWithObject = new ThingActionThing() {
			@Override
			public void run(Thing thisThing, ThingContainer otherThing) {
				Gdx.app.log(thisThing.nodeName, "Click base action "+otherThing.getThingName());
			}
		};
	}

	public Thing(boolean canBeTaken, float x, float y, float zDepth, float h, float w, String nodeName, TextureRegion texture, Level level) {
		this(x,y,zDepth,h,w,nodeName,texture,level);
		this.canBeTaken = canBeTaken;
	}


	public void defineBody() {//Массив вершина относительно объекта  (ноль координат вершин там, где XY вещи)
		float[] nvertices = new float[]{0,0,this.getWidth(), 0, this.getWidth(), this.getHeight(), 0, this.getHeight()};
		Polygon4Game poly = new Polygon4Game(nvertices);
		bodyPolys.add(poly);
	}


	public void addAnimation(String name, String filename, int columns, int rows, int tileWidth, int tileHeight, float frameDuration) {
		Texture animationSheet;
		Animation animation;
		TextureRegion[] frames;

		animationSheet = new Texture(Gdx.files.internal(filename));
		frames = genTextureRegion(animationSheet, columns,rows, tileWidth, tileHeight);
		animation = new Animation(frameDuration, frames);
		animations.put(name, animation);
	}

    public void setCurrentAnimation(String currentAnimation) {
        this.currentAnimation = animations.get(currentAnimation);
    }

    private TextureRegion[] genTextureRegion(Texture sheet, int cols, int rows, int tileWidth, int tileHeight) {

		TextureRegion[] walkFrames;
		TextureRegion[][] tmp = TextureRegion.split(sheet, tileWidth, tileHeight);

		walkFrames = new TextureRegion[cols * rows];
		int index = 0;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				walkFrames[index++] = tmp[i][j];
			}
		}
		return walkFrames;
	}

    public void setActorTex(TextureRegion actorTex) {
        this.actorTex = actorTex;
    }


	public boolean isHit(Vector2 xy) {
		boolean ret = false;

		for (Polygon4Game poly : bodyPolys) {
			if (getWorldPolygon(poly).isPointInside(xy))
				return true;
		}
		return ret;
	}

	public void setActOnClick(ThingAction actOnClick) {
		this.actOnClick = actOnClick;
	}

	public void setActWithObject(ThingActionThing actWithObject) {
		this.actWithObject = actWithObject;
	}

	public String getInteractionThing() {
		return getInteractionThing;
	}

	public void setInteractionThing(String interactionThing) {
		this.getInteractionThing = interactionThing;
	}

	public boolean isCanBeTaken() {
		return canBeTaken;
	}

	public void setCanBeTaken(boolean canBeTaken) {
		this.canBeTaken = canBeTaken;
	}

	public void act(float delta) {
	    if (this.currentAnimation != null) {
            animStateTime += delta;
            actorTex = (TextureRegion) currentAnimation.getKeyFrame(animStateTime, true);
            if (currentAnimation.isAnimationFinished(animStateTime)) {
                animStateTime=0;
            }

        }
    };//use to define animations or something other on each frame

	public void actOnClick() {
		if (actOnClick != null) {
			actOnClick.run(this);
		}
	};

	public void actWithObject(ThingContainer otherThing) {
		if (actWithObject != null && otherThing != null) {
			actWithObject.run(this, otherThing);
		}
	}

	public TextureRegion getTexture() {
		return this.actorTex;
	}



	public Array<Polygon4Game> getWorldPolygons() {
		Array<Polygon4Game> ret = new Array<Polygon4Game>();

		for (Polygon4Game poly : bodyPolys) {
			ret.add(getWorldPolygon(poly));
		}
		return ret;
	}


	public Polygon4Game getWorldPolygon(Polygon4Game poly) {
		float[] tmpVertices = verticesToGlobal(poly.getVertices());

		return new Polygon4Game(tmpVertices);
	}


	public float[] verticesToGlobal(float[] vertices) {//Чтобы полигон поддерживал все трансформации актера

		Matrix3 translateMatrix2d = new Matrix3().setToTranslation(oX, oY);
		float[] ret = new float[vertices.length];//Создаем новый массив для вывода. Он вдвое больше массива векторов

		int polyPoints = vertices.length/2;
		Vector2[] tmpVectors = new Vector2[polyPoints];

		int j = 0;
		for (int i = 0; i < tmpVectors.length; i++) {//fill initial coords
			tmpVectors[i] = new Vector2(vertices[j], vertices[j+1]);
			j+=2;
		}

		for (int i = 0; i < tmpVectors.length; i++) {
			Vector2 tmpResult = tmpVectors[i].mul(translateMatrix2d);

			ret[i*2] = tmpResult.x;
			ret[i*2+1] = tmpResult.y;
		}
		return ret;
	}



	@Override
	public float getParallaxFactor() {
		return parallaxFactor;
	}


	public void setParallaxFactor(float parallaxFactor) {
		this.parallaxFactor = parallaxFactor;
	}


	public void setWidth(float w) {
		this.width = w;
	}

	public void setHeight(float h) {
		this.height = h;
	}


	public float getWidth() {
		return this.width;
	}

	public float getHeight() {
		return this.height;
	}

	@Override
	public void setPosition(float x, float y) {
		this.oX = x;
		this.oY = y;
	}

	@Override
	public void setPosition(float x, float y, float zDepth) {
		this.oX = x;
		this.oY = y;
	}


	public void setXY(Vector2 xy) {
		this.oX = xy.x;
		this.oY = xy.y;
	}


	public Vector2 getXY() {
		return new Vector2(oX,oY);
	}


	public void setX(float x) {
		this.oX = x;
	}


	public void setY(float y) {
		this.oY = y;
	}


	public float getX() {
		return oX;
	}


	public float getY() {
		return oY;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}


	public float getZDepth() {
		return this.zDepth;
	}

	public void setzDepth(float zDepth) {this.zDepth = zDepth;}

	public float getRenderX() {
		return oX+xShift;
	}


	public float getRenderY() {
		return oY+yShift;
	}


	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public float getxShift() {
		return xShift;
	}

	public float getyShift() {
		return yShift;
	}

	public void setxShift(float xShift) {
		this.xShift = xShift;
	}

	public void setyShift(float yShift) {
		this.yShift = yShift;
	}


	@Override
	public float getRenderHeight() {
		return renderHeight;
	}

	@Override
	public float getRenderWidth() {
		return renderWidth;
	}


    public void setRenderWidth(float renderWidth) {
        this.renderWidth = renderWidth;
    }

    public void setRenderHeight(float renderHeight) {
        this.renderHeight = renderHeight;
    }


    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}