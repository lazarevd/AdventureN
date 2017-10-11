package ru.laz.game.model.things;

import com.badlogic.gdx.math.Vector2;

import java.util.HashMap;
import java.util.Map.Entry;

public class Trunk {


    private int COLUMNS = 6;
    private int ROWS = 5;
    private int COLUMN_SHIFT = 100;
    private int ROWS_SHIFT = 100;

    private HashMap<String, Thing> things  = new HashMap<String, Thing>();

    public Trunk() {

    }

    public void addToTrunk(String name, Thing thing) {
        things.put(name, thing);
        arrangeThings();
    }


    public HashMap<String, Thing> getThings(){
        return things;
    }


    public void arrangeThings() {



        int tmpRShift = ROWS_SHIFT;
        int tmpCShift = COLUMN_SHIFT;
        int tmpRowCount = 0;
        int tmpColCount = 0;

        for(Entry<String, Thing> entry : things.entrySet()) {
                    entry.getValue().setXY(new Vector2(tmpCShift, tmpRShift));
                    tmpCShift+=COLUMN_SHIFT;
                    tmpColCount+=1;
                    if (tmpColCount >= COLUMNS) {
                        tmpRShift += ROWS_SHIFT;
                        tmpCShift = COLUMN_SHIFT;
                    }

  }
    }

}
