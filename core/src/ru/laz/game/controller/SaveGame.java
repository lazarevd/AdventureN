package ru.laz.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

import ru.laz.game.AGame;
import ru.laz.game.model.stages.Level;
import ru.laz.game.model.stages.Level_01.Level_01;
import ru.laz.game.model.things.Thing;

/**
 * Created by Dmitry Lazarev on 02.07.2018.
 */
public class SaveGame {

    static Json json = new Json();

    public static String toSave(Object object) {
        return json.toJson(object);
    }

    public static void save(Object object) {
        FileHandle file = Gdx.files.local("savegame.txt");
        String serialized = toSave(object);
        Gdx.app.log("writing to file ", serialized);
        file.writeString(serialized, false);
    }

    public static <T> T fromSave(Class<T> type) {
        FileHandle file = Gdx.files.local("savegame.txt");
        String serialized = file.readString();
        Gdx.app.log("reading file ", serialized);
        json.setSerializer(Thing.class, new Json.Serializer<Thing>() {
            @Override
            public void write(Json json, Thing object, Class knownType) {
                json.writeObjectStart();
                json.writeFields(object);
                json.writeObjectEnd();
            }

            @Override
            public Thing read(Json json, JsonValue jsonData, Class type) {
                Thing thing = new Thing();
                json.readFields(thing,jsonData);
                thing.init();
                return thing;
            }
        });
        return json.fromJson(type, serialized);
    }


    public static void loadAll() {
        Level level = fromSave(Level_01.class);
        level.initSaved();
        level.getMainActor().setLevel(level);
        AGame.getGame().getGameScreen().initSaved(level);
    }

    public static void saveAll() {
        save(AGame.getGame().getGameScreen().getLevel());
    }

}
