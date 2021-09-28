package com.example.last;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;

public class NPC {
    Bitmap alive,dead;
    int health,speed,npcID,road_selection;
    String name;
    Path path;
    Matrix matrix;
    PathMeasure pathMeasure;
    Context context;
    float distance,pathLength;
    int bm_offsetX, bm_offsetY,reward,damage;
    float[] pos;
    float[] tan;
    Paint paint;
    Point death;
    long deathtime;
    boolean isAlive,draw;
    public NPC() {
    }
    public NPC(int npcID, Context context, Path p) {
        this.context=context;
        if (npcID==0){
            Npc0();
        }else if (npcID==1){
            Npc1();
        }else{
            Npc2();
        }
        this.path=p;
        isAlive=true;
        draw=true;
        pathMeasure = new PathMeasure(this.path, false);
        pathLength = pathMeasure.getLength();
        bm_offsetX = alive.getWidth()/2;
        bm_offsetY = alive.getHeight()/2;
        distance = 0;
        pos = new float[2];
        tan = new float[2];
        death=new Point();
        paint = new Paint();
        paint.setColor(Color.TRANSPARENT);
        paint.setStrokeWidth(1);
        paint.setStyle(Paint.Style.STROKE);
        matrix = new Matrix();
    }
    private void Npc0(){
        this.damage=1;
        this.reward=25;
        this.health = 300;
        this.speed = 2;
        this.npcID = 0;
        this.name = "tank1";
        this.alive =BitmapFactory.decodeResource(this.context.getResources(),R.drawable.solider);
        this.dead = BitmapFactory.decodeResource(this.context.getResources(),R.drawable.dead_solider);
    }
    private void Npc1(){
        this.damage=2;
        this.reward=50;
        this.health = 300;
        this.speed = 5;
        this.npcID = 0;
        this.name = "tank2";
        this.alive =BitmapFactory.decodeResource(this.context.getResources(),R.drawable.tank1);
        this.dead =BitmapFactory.decodeResource(this.context.getResources(),R.drawable.tank1_exploded);
    }
    private void Npc2(){
        this.damage=3;
        this.reward=150;
        this.health = 600;
        this.speed = 2;
        this.npcID = 0;
        this.name = "tank3";
        this.alive =BitmapFactory.decodeResource(this.context.getResources(),R.drawable.boss);
        this.dead =BitmapFactory.decodeResource(this.context.getResources(),R.drawable.boss_exploded);
    }

}
