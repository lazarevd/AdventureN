package ru.laz.game.model.math;

/**
 * Created by Dmitry Lazarev on 19.10.2017.
 */

public class Matrix3 {

    /*Encapsulates a column major 3 by 3 matrix class. It allows the chaining of methods by returning a reference to itself.*/

    public static final int M00 = 0;
    public static final int M01 = 3;
    public static final int M02 = 6;
    public static final int M10 = 1;
    public static final int M11 = 4;
    public static final int M12 = 7;
    public static final int M20 = 3;
    public static final int M21 = 5;
    public static final int M22 = 8;

    private static final float tmp[] = new float[9];
    public final float val[] = new float[9];

    public Matrix3 () {
        val[M00] = 1f;
        val[M11] = 1f;
        val[M22] = 1f;
    }



    public Matrix3 set (float[] values) {
        System.arraycopy(values, 0, val, 0, val.length);
        return this;
    }
}
