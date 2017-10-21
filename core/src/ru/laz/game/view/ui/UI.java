package ru.laz.game.view.ui;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.HashMap;

import ru.laz.game.controller.Controller;
import ru.laz.game.model.things.Trunk;


public class UI {
	
	private TextButtonStyle textButtonStyle;
	private BitmapFont font;
	private Skin skin;
	private TextureAtlas buttonAtlas;
	private static OrthographicCamera uiCam;
	private static OrthographicCamera  scCam;
	private static Texture trunkTex;
	private static Viewport viewportScene;
	private static Viewport viewportUI;
	private static UI ui; //singletone
	private static Trunk trunk;
	//public final static int WIDTH = 2768;
	//public final static int HEIGHT = 334;
	public static boolean GRAPH = true;
	public static boolean BACK = false;

	public static HashMap<String, UIButton> uiButtons;




	private static boolean TRUNK = false;



    /** Пользовательский интерфейс. Управляет отображением всех элементов интерфейса и
	 * управления вьюпортами. В игре есть 2 сцены, stageSc, stageUi.
	 * TODO (перенести их из level в UI)
	 * в них содержатся 2 вьюпорта: viewportScene - для объектов сцены
	 * (сдержит граф движения, главгероя, различные декорации, активные объекты, с которыми можно
	 * взаимодействовать. И viewportUI для интерфейса.
	 * Сделано это только для отрисовки интерфейса не зависимо от камеры сцены. Камеры соотвественно
	 * тоже 2.
	 **/
	
	
	
	
	private UI() {
       // renderShapes.setBackgroundSprite(gameLevel.getBackground());
		uiButtons = new HashMap<String, UIButton>();
        trunk = new Trunk();
        UI.trunkTex = new Texture(Gdx.files.internal("ui/trunk.png"));
		uiCam = new OrthographicCamera();
		uiCam.setToOrtho(false);
		uiCam.position.x = Gdx.graphics.getWidth()/2;
		uiCam.position.y = Gdx.graphics.getHeight()/2;
        scCam = new OrthographicCamera();
        scCam.setToOrtho(false);
		viewportScene = new ScalingViewport(Scaling.fill, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), scCam);
		viewportUI = new ScalingViewport(Scaling.fill, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), uiCam);
		//Gdx.app.log("MATRIX SCENE", scCam.projection.toString());
		//Gdx.app.log("MATRIX UI", uiCam.projection.toString());
		fillUI();
	}

 
	
	public Texture getTrunkTex() {
		return UI.trunkTex;
	}



	public static Viewport getViewportScene() {
		return UI.viewportScene;
	}

	public static Viewport getViewportUI() {
		return UI.viewportUI;
	}

	public static OrthographicCamera getSceneCamera() {
		return UI.scCam;
	}
	
	public static OrthographicCamera getUICamera() {
		return UI.uiCam;
	}
	
	public void act(float delta) {


	}


    public static Trunk getTrunk() {
        return trunk;
    }


	
	private void fillUI() {

		UIButton uib = new UIButton(new Texture(Gdx.files.internal("backpack.png")), 0, Gdx.graphics.getHeight() - 120, 120, 120) {
			@Override
			public void clicked() {
				if (TRUNK) {
					TRUNK = false;
				} else {
					TRUNK = true;
					Controller.setTrunkControls();
				}
			}
		};

		uiButtons.put("mugButton", uib);

	}
	
	


	public static void setTRUNK(boolean TRUNK) {
		UI.TRUNK = TRUNK;
		if(UI.TRUNK)
			Controller.setTrunkControls();
	}


	public static boolean isTRUNK() {
		return TRUNK;
	}
	


	public static UI getUI() {
		if (ui ==null) {
			ui = new UI();
		}
		return ui;
	}

}
