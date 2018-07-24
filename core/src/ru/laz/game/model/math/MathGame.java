package ru.laz.game.model.math;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class MathGame {


	public class Matrix3 {

	}



	public static Vector2 lineToVector(Vector2[] line) {
		Vector2 res = new Vector2(line[1].x-line[0].x, line[1].y - line[0].y);
		
		return res;
	}
	
	public static Vector2 lineToVector(Vector2 xy0, Vector2 xy1) {
		Vector2 res = new Vector2(xy1.x-xy0.x, xy1.y - xy0.y);
		
		return res;
	}


	public static Vector2 getMiddleOfLine(float x1, float y1, float x2, float y2) {

		float x = (x1 + x2) / 2;
		float y = (y1 + y2) / 2;

		Vector2 ret = new Vector2(x, y);
		return ret;
	}



	public static Array<Float> getArrayOfFloats(float startFloat, float endFloat, int divisions) {
		Array<Float> ret = new Array<Float>();
		float step = (endFloat - startFloat)/divisions;
		float tmpAdd = startFloat;
		for (int i = 0; i < divisions; i++) {
			tmpAdd = tmpAdd + step;
			ret.add(tmpAdd);
		}
		return ret;
	}



	public static Array<Vector2> separateVector(Vector2 start, Vector2 finish, float speed) {
        Array<Vector2> retArr = new Array<Vector2>();

        Vector2 vec = new Vector2(finish.x - start.x, finish.y - start.y); 	//Base vector from start of coordinate to finish of path segment
		Vector2 speedVec = new Vector2(vec).nor().scl(speed);//Make normalized vector, then scale it to speed

		int k = (int)(vec.len()/speedVec.len());//Find ratio between base vector and "speed" vector. It`s number of speed vectors in array

		Gdx.app.log("Vec: ", vec.toString());

		Gdx.app.log("SV: ", speedVec.toString());
		Vector2 tmpvec = new Vector2(speedVec.x, speedVec.y).add(start);
        for (int i = 0; i < k;i++) {
			Vector2 addVec = new Vector2(tmpvec.x, tmpvec.y);
            retArr.add(addVec);
			tmpvec = tmpvec.add(speedVec);
        }
        return retArr;
    }

}
