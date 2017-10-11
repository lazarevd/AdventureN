package ru.laz.game.view.ui;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import ru.laz.game.AGame;
import ru.laz.game.controller.Controller;
import ru.laz.game.model.things.Trunk;


public class UI {
	
	private TextButtonStyle textButtonStyle;
	private BitmapFont font;
	private Skin skin;
	private TextureAtlas buttonAtlas;
	//private static GameLevel gameLevel;
	private Table mainTable;
	private static OrthographicCamera uiCam;
	private static OrthographicCamera  scCam;
	private static Texture trunkTex;
	private static Viewport viewportScene;
	private static Viewport viewportUI;
	private static Stage stageUi;
	private static UI ui; //singletone
	private static Trunk trunk;
	public final static int WIDTH = 1024;
	public final static int HEIGHT = 768;
	public static boolean GRAPH = true;
	public static boolean BACK = false;




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
        trunk = new Trunk();
        UI.trunkTex = new Texture(Gdx.files.internal("ui/trunk.png"));
       // renderShapes.setTrunkSprite(trunkTex);
        mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.left().top();
		uiCam = new OrthographicCamera(WIDTH, HEIGHT);
		uiCam.setToOrtho(false);
        scCam = new OrthographicCamera(WIDTH,HEIGHT);
        scCam.setToOrtho(false);
		viewportScene = new ScalingViewport(Scaling.fill, AGame.W_WIDTH, AGame.W_HEIGHT, scCam);
		viewportUI = new ScalingViewport(Scaling.fill, AGame.W_WIDTH, AGame.W_HEIGHT, uiCam);

		stageUi = new Stage();
		stageUi.setViewport(UI.getViewportUI());
	    stageUi.addActor(mainTable);
		fillUI();
	}

	
	public Texture getTrunkTex() {
		return UI.trunkTex;
	}
	
	public Table getMainTable() {
		return this.mainTable;
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



	public static Stage getUIStage() {		
		return stageUi;
	}
	

	
	private void fillUI() {
		//mainTable.setDebug(true);
		Button but1 = createButton("Graph");
		but1.addListener(new ChangeListener() {
		    public void changed (ChangeEvent event, Actor actor) {
		    	if (GRAPH) {
		    		GRAPH = false;
		        } else {
		        	GRAPH = true;
		        }
		    }
		});
		
		
		Button but2 = createButton("Back");
		but2.addListener(new ChangeListener() {
		    public void changed (ChangeEvent event, Actor actor) {
		    	if (BACK) {
		    		BACK = false;
		        } else {
		        	BACK = true;
		        }
		    }
		});
		


			
		Button trunk = createButton("Trunk");
		trunk.addListener(new ChangeListener() {
		    public void changed (ChangeEvent event, Actor actor) {
		    	if (TRUNK) {
		    		TRUNK = false;
		        } else {
		        	TRUNK = true;
					Controller.setTrunkControls();

		        }
		    }
		});
		


		
        mainTable.add(but1).maxWidth(60);
        mainTable.row();
        mainTable.add(but2).maxWidth(60);
        mainTable.row();
        mainTable.add(trunk).maxWidth(60);
        mainTable.row();
        
	}
	
	
	
	
	public Button createButton(String label) {
        font = new BitmapFont();
        skin = new Skin();
        textButtonStyle = new TextButtonStyle();
        textButtonStyle.font = font;
        buttonAtlas = new TextureAtlas(Gdx.files.internal("ui/astar.pack"));
        skin.addRegions(buttonAtlas);
		textButtonStyle = new TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.up = skin.getDrawable("idle");
        textButtonStyle.down = skin.getDrawable("push");
		Button button = new TextButton(label, textButtonStyle);
		return button;
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
