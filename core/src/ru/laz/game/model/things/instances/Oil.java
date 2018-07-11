package ru.laz.game.model.things.instances;


import ru.laz.game.model.things.Thing;

/**
 * Created by Dmitry Lazarev on 06.07.2018.
 */
public class Oil extends Thing {

    public Oil() {
        super();
    }

    public Oil(boolean canBeTaken, float x, float y, float zDepth, float h, float w, String nodeName, String textureName) {
        super(canBeTaken,x,y,zDepth,h,w,nodeName,textureName);
    }

}
