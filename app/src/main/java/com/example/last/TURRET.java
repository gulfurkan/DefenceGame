package com.example.last;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.SoundPool;

import java.util.ArrayList;
import java.util.List;

public class TURRET {
    public Bitmap bitmap,locked;
    public int turretID,damage;
    public long firingDuration;
    public Point position;
    public Matrix matrix;
    public Context context;
    public int indis,range,price;
    public float surface;
    public boolean light;
    public long time;
    public List<BULLET> bullets;
    public int sound;
    public SoundPool soundPool;
    public Paint paint;


    public TURRET(int turretID, int x, int y, Context context) {
        this.soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC,0);
        this.context=context;
        this.turretID=turretID;
        this.matrix=new Matrix();
        this.position=new Point();
        this.position.x=x;
        this.position.y=y;
        this.indis=0;
        this.light=false;
        this.time=0;
        this.bullets=new ArrayList<>();
        this.paint = new Paint();
        this.paint.setStyle(Paint.Style.STROKE);
        if (turretID==0){
            turret0();
        }else if (turretID==1){
            turret1();
        }else if (turretID==2){
            turret2();
        }else{
            turret0();
        }
        this.matrix.postTranslate(this.position.x-this.bitmap.getWidth()/2,this.position.y-this.bitmap.getHeight()/2);

    }
    private void turret0() {
        this.bitmap=BitmapFactory.decodeResource(context.getResources(),R.drawable.turret1);
        this.locked=BitmapFactory.decodeResource(context.getResources(),R.drawable.turret1_locked);
        this.firingDuration=250;
        this.damage=40;
        this.range=300;
        this.surface=50;
        this.price=50;
        this.sound=soundPool.load((Activity)context,R.raw.gun3,1);
        this.paint.setColor(Color.BLUE);
        this.paint.setStrokeWidth(2);
    }
    private void turret1() {
        this.bitmap=BitmapFactory.decodeResource(context.getResources(),R.drawable.turret2);
        this.locked=BitmapFactory.decodeResource(context.getResources(),R.drawable.turret2_locked);
        this.firingDuration=500;
        this.damage=100;
        this.range=400;
        this.surface=20;
        this.price=100;
        this.sound=soundPool.load((Activity)context,R.raw.gun2,1);
        this.paint.setColor(Color.GREEN);
        this.paint.setStrokeWidth(3);
    }
    private void turret2() {
        this.bitmap=BitmapFactory.decodeResource(context.getResources(),R.drawable.turret3);
        this.locked=BitmapFactory.decodeResource(context.getResources(),R.drawable.turret3_locked);
        this.firingDuration=1000;
        this.damage=60;
        this.range=500;
        this.surface=25;
        this.price=200;
        this.sound=soundPool.load((Activity)context,R.raw.gun3,1);
        this.paint.setColor(Color.RED);
        this.paint.setStrokeWidth(5);
    }

    public void addBullet(Path path) {
        this.bullets.add(new BULLET(0, path, context));
    }
}
