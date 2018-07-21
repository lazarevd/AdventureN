package ru.laz.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

import ru.laz.game.AGame;
import ru.laz.game.view.ui.screens.GameScreen;

/**
 * Created by Dmitry Lazarev on 02.07.2018.
 */
public class SaveGame {

    static Json json = new Json();


    public static void save(Object object) {
        FileHandle file = Gdx.files.local("savegame.txt");
        String serialized = json.toJson(object);
        Gdx.app.log("writing to file ", serialized);
        file.writeString(serialized, false);
    }

    public static <T> T fromSave(Class<T> type) {
        FileHandle file = Gdx.files.local("savegame.txt");
        String serialized = file.readString();
        Gdx.app.log("reading file ", serialized);
 /*
        json.setSerializer(Thing.class, new Json.Serializer<Thing>() {
            @Override
            public void write(Json json, Thing object, Class knownType) {
                Gdx.app.log("writing", "write");
                json.writeObjectStart();
                json.writeFields(object);
                json.writeObjectEnd();
            }

            @Override
            public Thing read(Json json, JsonValue jsonData, Class type) {
                Gdx.app.log("initing thing", "initSaved");
                Thing thing = new Thing();
                json.readFields(thing,jsonData);
                thing.init();
                return thing;
            }
        });

        json.setSerializer(Location.class, new Json.Serializer<Location>() {
            @Override
            public void write(Json json, Location object, Class knownType) {
                json.writeObjectStart();
                json.writeFields(object);
                json.writeObjectEnd();
            }

            @Override
            public Location read(Json json, JsonValue jsonData, Class type) {
                Gdx.app.log("initing ", "initSaved");
                Location location = new Location();
                json.readFields(location,jsonData);
                location.initSaved();
                return location;
            }
        });

*/
        return json.fromJson(type, serialized);
    }


    public static void loadAll() {
        GameScreen gameScreen = fromSave(GameScreen.class);
        AGame.getGame().setScreen(gameScreen);
    }
/*
    public static void loadAll() {
        Location location = fromSave(Location_01.class);
        location.getMainActor().setLocation(location);
        AGame.getGame().getGameScreen().setCurrentLocation(location);
        AGame.getGame().getGameScreen().initSaved();
    }
*/
    public static void saveAll() {
        save(AGame.getGame().getGameScreen());
    }

}
