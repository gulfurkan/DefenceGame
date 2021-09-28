package com.example.last;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.PathMeasure;

public class BULLET {
    public Bitmap bitmap,hit;
    public int bulletID, speed, bm_offsetX, bm_offsetY;
    public Path path;
    public PathMeasure pathMeasure;
    public Context context;
    public float distance,pathLength;
    public float[] pos;
    public float[] tan;
    public Matrix matrix;
    public long HitTime;
    public boolean isHit;

    public BULLET(int bulletID, Path path, Context context) {
        this.bulletID = bulletID;
        this.context = context;
        this.matrix = new Matrix();
        this.HitTime =0;
        this.isHit=false;
        this.pos=new float[2];
        this.tan=new float[2];
        this.path=path;
        if (bulletID==0){
            bullet0();
        }
        setThings();
    }

    private void bullet0() {
        this.bitmap= BitmapFactory.decodeResource(context.getResources(),R.drawable.bullet);
        this.hit=BitmapFactory.decodeResource(context.getResources(),R.drawable.boom);

    }

    public void setThings() {
        pathMeasure = new PathMeasure(this.path, false);
        pathLength = pathMeasure.getLength();
        bm_offsetX = bitmap.getWidth()/2;
        bm_offsetY = bitmap.getHeight()/2;
        distance = 0;
        speed= 300;
    }
}
