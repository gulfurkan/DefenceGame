package com.example.last;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AnimationView extends View {
    private Paint paint = null;
    public int health,cash;
    public NPC[] npc_list;
    public List<TURRET> turrets;
    boolean pause=false;
    Context context;
    int opened_levels=1;
    SoundPlayer soundPlayer;
    long timer;
    public AnimationView(Context context) {
        super(context);
        initAnimationView();
    }

    public AnimationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAnimationView();
    }

    public AnimationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAnimationView();
    }

    private void initAnimationView(){
        turrets = new ArrayList<>();
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);
        cash=100;
        health=10;
        timer=System.currentTimeMillis();
        setText();
    }
    public void setNpc_list(NPC[] npc_list,Context context){
        this.context=context;
        soundPlayer=new SoundPlayer(context);
        setText();
        this.npc_list=npc_list;
        draw(new Canvas());
    }
    public void addTurret(TURRET t){
        turrets.add(t);
    }
    @Override
    protected void onDraw(Canvas canvas) {

        //PARANIN YETMEDİĞİ KULELERİ KAPATMA
        Game.lock(cash);
        //OYUNU 3 SANİYE GECİKME İLE BAŞLATMA
        if (System.currentTimeMillis()-timer>3000){
            if (pause){
                //DÜŞMANLARIN ÇİZİLMESİ
                if (npc_list!=null){
                    for (NPC npc:npc_list){
                        if (npc.draw){
                            npc.deathtime=System.currentTimeMillis();
                            if (npc.isAlive){
                                canvas.drawBitmap(npc.alive,npc.matrix,null);
                            }else{
                                canvas.drawBitmap(npc.dead,npc.matrix,null);
                            }
                        }
                    }
                }
                //KULELERİN ÇİZİLMESİ
                for (TURRET turret:turrets){
                    if (turret.light){
                        canvas.drawCircle(turret.position.x,turret.position.y,turret.range,paint);
                    }
                    canvas.drawBitmap(turret.bitmap,turret.matrix,null);
                    for (BULLET bullet:turret.bullets){
                        if (bullet.isHit){
                            canvas.drawBitmap(bullet.hit,npc_list[turret.indis].matrix,null);
                        }
                        else {
                            canvas.drawBitmap(bullet.bitmap,bullet.matrix,null);
                        }
                    }
                }
            }
            else{
                if (npc_list!=null){

                    for (int i=0;i<npc_list.length;i++){
                        for (TURRET turret: turrets){
                            //KULE MENZİLİ ÇİZİLMESİ
                            if (turret.light){
                                canvas.drawCircle(turret.position.x,turret.position.y,turret.range,paint);
                            }
                            //KULE ODAĞI DEĞİŞİMİ
                            if (getDistance(npc_list[i].pos,turret.position)<turret.range&&npc_list[i].health>=0){
                                if (i!=0){
                                    float distance=getDistance(npc_list[turret.indis].pos,turret.position);
                                    if (distance>turret.range){
                                        float temp=999;
                                        Point pemp=new Point();
                                        pemp.x= (int) npc_list[turret.indis].pos[0];
                                        pemp.y= (int) npc_list[turret.indis].pos[1];
                                        for (int in=0;in<npc_list.length;in++){
                                            if (temp>getDistance(npc_list[in].pos,pemp)&&in!=turret.indis&&npc_list[in].isAlive&&getDistance(npc_list[in].pos,turret.position)<turret.range){
                                                temp=getDistance(npc_list[in].pos,pemp);
                                                turret.indis=in;
                                            }
                                        }
                                    }

                                }
                                if (npc_list[turret.indis].isAlive){
                                    //MERMİLERİN ÇİZİLMESİ
                                    if (getDistance(npc_list[turret.indis].pos,turret.position)<turret.range){
                                        if (turret.turretID==2){
                                            canvas.drawLine(turret.position.x,turret.position.y,npc_list[turret.indis].pos[0],npc_list[turret.indis].pos[1],turret.paint);
                                        }
                                        for (BULLET bullet:turret.bullets){
                                            if (bullet.distance<bullet.pathLength){
                                                if (turret.turretID==2){

                                                }else{
                                                    canvas.drawPath(bullet.path,turret.paint);
                                                }
                                                bullet.pathMeasure.getPosTan(bullet.distance, bullet.pos, bullet.tan);

                                                bullet.matrix.reset();
                                                float degrees = (float)(Math.atan2(bullet.tan[1], bullet.tan[0])*180.0/Math.PI);
                                                bullet.matrix.postRotate(degrees, bullet.bm_offsetX, bullet.bm_offsetY);
                                                bullet.matrix.postTranslate(bullet.pos[0]-bullet.bm_offsetX, bullet.pos[1]-bullet.bm_offsetY);

                                                canvas.drawBitmap(bullet.bitmap, bullet.matrix, null);

                                                bullet.distance += bullet.speed;
                                            }
                                            else{
                                                if (!bullet.isHit){
                                                    bullet.HitTime =System.currentTimeMillis();
                                                    npc_list[turret.indis].health-=turret.damage;
                                                    bullet.isHit=true;
                                                    // soundPlayer.playHit();

                                                    if (npc_list[turret.indis].health<=0){
                                                        if (Game.sound){
                                                            soundPlayer.playHit();
                                                        }
                                                        npc_list[turret.indis].isAlive=false;
                                                        cash+=npc_list[turret.indis].reward;
                                                        setText();
                                                        npc_list[turret.indis].deathtime=System.currentTimeMillis();
                                                        // npc_list[turret.indis].draw=false;
                                                        next();
                                                    }

                                                }
                                            }
                                        }
                                        if (pause){
                                            break;
                                        }
                                    }

                                    turret.matrix.reset();
                                    turret.matrix.postTranslate(turret.position.x-turret.bitmap.getWidth()/2,turret.position.y-turret.bitmap.getHeight()/2);
                                    float degree=getAngle(npc_list[turret.indis].pos, turret.position);
                                    turret.matrix.postRotate(
                                            degree,
                                            turret.position.x,
                                            turret.position.y);
                                    long last=System.currentTimeMillis();
                                    if (last-turret.time >= turret.firingDuration) {
                                        turret.time=last;
                                        Path path=new Path();
                                        path.moveTo(turret.position.x,turret.position.y);
                                        path.lineTo(npc_list[turret.indis].pos[0],npc_list[turret.indis].pos[1]);
                                        turret.addBullet(path);
                                        if (Game.sound){
                                            soundPlayer.playshot(turret.sound);
                                        }
                                    }
                                }
                                else{
                                    turret.bullets.clear();
                                    float temp=999;
                                    Point pemp=new Point();
                                    pemp.x= (int) npc_list[turret.indis].pos[0];
                                    pemp.y= (int) npc_list[turret.indis].pos[1];
                                    for (int in=0;in<npc_list.length;in++){
                                        if (temp>getDistance(npc_list[in].pos,pemp)&&in!=turret.indis&&npc_list[in].isAlive&&getDistance(npc_list[in].pos,turret.position)<turret.range){
                                            temp=getDistance(npc_list[in].pos,pemp);
                                            turret.indis=in;
                                        }
                                    }
                                }
                            }
                        }
                        if (pause){
                            break;
                        }
                        invalidate();
                    }
                    //DÜŞMAN VE KULE HAREKETİ KONTROLÜ
                    if (npc_list!=null){
                        for (NPC npc : npc_list){
                            if (npc.isAlive){
                                canvas.drawPath(npc.path, npc.paint);
                                if(npc.distance < npc.pathLength){
                                    npc.pathMeasure.getPosTan(npc.distance, npc.pos, npc.tan);

                                    npc.matrix.reset();
                                    float degrees = (float)(Math.atan2(npc.tan[1], npc.tan[0])*180.0/Math.PI);
                                    npc.matrix.postRotate(degrees, npc.bm_offsetX, npc.bm_offsetY);
                                    npc.matrix.postTranslate(npc.pos[0]-npc.bm_offsetX, npc.pos[1]-npc.bm_offsetY);

                                    canvas.drawBitmap(npc.alive, npc.matrix, null);

                                    npc.distance += npc.speed;
                                }
                                else{
                                    if (npc.isAlive){
                                        health-=npc.damage;
                                        if (health<=0){
                                            end();
                                        }
                                        setText();
                                        npc.isAlive=false;
                                        npc.draw=false;
                                        npc.deathtime=System.currentTimeMillis();
                                        // npc.draw=false;
                                        next();
                                    } }
                            }
                            else{
                                next();
                                npc.death.x= (int) npc.pos[0];
                                npc.death.y= (int) npc.pos[1];
                                if (npc.isAlive){
                                    cash+=npc.reward;
                                    setText();
                                    npc.isAlive=false;
                                    npc.deathtime= System.currentTimeMillis();
                                }
                                long timer=System.currentTimeMillis()-npc.deathtime;

                                if (timer<2000){
                                    canvas.drawBitmap(npc.dead,npc.death.x-npc.dead.getWidth()/2,npc.death.y-npc.dead.getHeight()/2,null);
                                }else{
                                    npc.matrix.reset();
                                    npc.draw=false;
                                }
                            }
                        }
                    }


                }
                for (TURRET turret:turrets){
                    canvas.drawBitmap(turret.bitmap,turret.matrix,null);
                    for (BULLET bullet:turret.bullets){
                        if (bullet.isHit){
                            if (System.currentTimeMillis()-bullet.HitTime<100&&npc_list[turret.indis].isAlive){
                                Random random=new Random();
                                int x=random.nextInt(npc_list[turret.indis].alive.getWidth());
                                int y=random.nextInt(npc_list[turret.indis].alive.getHeight());
                                canvas.drawBitmap(bullet.hit,npc_list[turret.indis].pos[0]-npc_list[turret.indis].alive.getWidth()/2+x,npc_list[turret.indis].pos[1]-npc_list[turret.indis].alive.getHeight()/2+y,null);
                            }
                        }
                        else {
                            canvas.drawBitmap(bullet.bitmap,bullet.matrix,null);
                        }
                    }
                }
            }
        }
        else{
            for (TURRET turret:turrets){
                if (turret.light){
                    canvas.drawCircle(turret.position.x,turret.position.y,turret.range,paint);
                }
                canvas.drawBitmap(turret.bitmap,turret.matrix,null);
                for (BULLET bullet:turret.bullets){
                    if (bullet.isHit){
                        canvas.drawBitmap(bullet.hit,npc_list[turret.indis].matrix,null);
                    }
                    else {
                        canvas.drawBitmap(bullet.bitmap,bullet.matrix,null);
                    }
                }
            }
        }


        invalidate();
    }
    private float getDistance(float[] target, Point base) {
        float distance = (float) Math.sqrt(Math.pow(target[0]-base.x,2)+Math.pow(target[1]-base.y,2));
        return distance;
    }
    private float getAngle(float[] target, Point base) {
        float angle = (float) Math.toDegrees(Math.atan2(target[1] - base.y, target[0] - base.x));

        return angle;
    }


    public void setText(){
        if (context !=null){
            TextView bank =  ((Activity) context).findViewById(R.id.cash);
            bank.setText("CASH: "+cash);
            TextView healthbar = ((Activity) context).findViewById(R.id.health);
            healthbar.setText("HEALTH: "+health);
            TextView waves = ((Activity) context).findViewById(R.id.waves);
            waves.setText((Game.current_wave+1)+"/"+Game.level_library.levels[Game.current_level].waves.length);
        }
    }


    private boolean isqueueEmpty() {
        if (npc_list!=null){
            for (NPC npc:npc_list){
                if (npc.isAlive){
                    return false;
                }
            }
        }
        return true;
    }
    private void next(){
        if (isqueueEmpty()){
            cleardata();
            Game.current_wave++;
            if (Game.current_wave>= Game.level_library.levels[Game.current_level].waves.length){
               if (Game.current_level+1>=Game.level_library.levels.length){
                   win();
               }
               else{
                   end();
               }
            }else{
                cleardata();
                setText();
                timer=System.currentTimeMillis();
                npc_list= Game.level_library.levels[Game.current_level].waves[Game.current_wave].npc_list;
            }
        }
    }
    private void end(){

        if (context !=null){
            cleardata();
            turrets=new ArrayList<>();
            pause=true;
            LinearLayout layout =  ((Activity) context).findViewById(R.id.end_menu);
            if (health<=0){
                Button button=((Activity)context).findViewById(R.id.next);
                button.setVisibility(INVISIBLE);
            }
            else{
                if (Game.sound){
                    soundPlayer.playwin();
                }
                opened_levels++;
                Button button=((Activity)context).findViewById(R.id.next);
                button.setVisibility(VISIBLE);
            }
            layout.setVisibility(VISIBLE);
        }
    }
    private void win(){

        if (context !=null){
            cleardata();
            pause=true;
            LinearLayout layout =  ((Activity) context).findViewById(R.id.finish_menu);
            layout.setVisibility(VISIBLE);
        }
    }
    private void cleardata(){
        for (TURRET turret:turrets){
            turret.indis=0;
            npc_list=null;
            turret.bullets=null;
            turret.bullets=new ArrayList<>();
        }
    }
}
