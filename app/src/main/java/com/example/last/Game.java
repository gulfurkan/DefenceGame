package com.example.last;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Game extends AppCompatActivity {
    private Button home,pause,retry,next,menu,turrets_button,finish,lock,yes,no;
    public TextView bank,healthbar,waves;
    private static ImageView[] turret_menu;
    private ConstraintLayout turret_layout;
    public NPC[] npc_list;
    public AnimationView myAnimationView;
    public int display_width,display_height;
    public int turret_choice=0;
    public static int current_level=0,current_wave=0;
    public Point touch;
    public Path_library.Road road;
    public boolean Tmenu=false,locked=false;
    public static Level_Library level_library;
    public LinearLayout end_menu,finish_menu,sure;
    public SharedPreferences sharedPreferences;
    public SoundPlayer soundPlayer;
    static kule[] kules;
    public static boolean sound,diff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        screenAttributes();
        initialize();
        setAnimationThings();
        setText();
        start_game();
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myAnimationView.pause=true;
                sure.setVisibility(View.VISIBLE);
            }
        });
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myAnimationView.pause){
                    pause.setBackgroundResource(R.drawable.ic_baseline_pause_24);
                }
                else{
                    pause.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24);
                }
                myAnimationView.pause=!myAnimationView.pause;
            }
        });
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                current_wave=0;
                myAnimationView.pause=false;
                end_menu.setVisibility(View.INVISIBLE);
                setAnimationThings();
                start_game();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                share();
                myAnimationView.pause=false;
                current_wave=0;
                current_level++;
                if (current_level>=level_library.levels.length){

                }
                else {
                    end_menu.setVisibility(View.INVISIBLE);
                    setAnimationThings();
                    start_game();
                }
            }
        });
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go_menu();
            }
        });
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go_menu();
            }
        });
        turrets_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Tmenu){
                    turrets_button.setBackgroundResource(R.drawable.ic_baseline_arrow_left_24);
                    Animation animation= AnimationUtils.loadAnimation(Game.this,R.anim.turret_menu_closing);
                    turret_layout.setAnimation(animation);
                    turret_layout.setVisibility(View.INVISIBLE);
                    Tmenu=!Tmenu;
                }
                else{
                    turrets_button.setBackgroundResource(R.drawable.ic_baseline_arrow_right_24);
                    Animation animation= AnimationUtils.loadAnimation(Game.this,R.anim.turret_menu_openning);
                    turret_layout.setAnimation(animation);
                    turret_layout.setVisibility(View.VISIBLE);
                    Tmenu=!Tmenu;
                }
            }
        });
        lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (locked){
                    lock.setBackgroundResource(R.drawable.unlocked);
                }
                else{
                    lock.setBackgroundResource(R.drawable.lock24);
                }
                locked=!locked;
            }
        });

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Game.this,Menu.class);
                finish();
                startActivity(intent);
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myAnimationView.pause=false;
                pause.setBackgroundResource(R.drawable.ic_baseline_pause_24);
                sure.setVisibility(View.INVISIBLE);
            }
        });

    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        Point point=new Point();
        point.x = (int)event.getX();
        point.y = (int)event.getY();
        light(point);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
        }

        return false;
    }

    View.OnTouchListener onTouchListener=new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            touch.x= (int) event.getX();
            touch.y= (int) event.getY();
            for (int i=0;i<turret_menu.length;i++){
                if (kules[i].imageView.getId()==view.getId()){
                    turret_choice=i;
                }
            }
            switch (event.getAction()&MotionEvent.ACTION_MASK){
                case MotionEvent.ACTION_DOWN:
                    break;
                case MotionEvent.ACTION_MOVE:
                    ClipData data=ClipData.newPlainText("","");
                    View.DragShadowBuilder shadowBuilder=new View.DragShadowBuilder(view);
                    view.startDrag(data,shadowBuilder,view,0);
                    break;
                case MotionEvent.ACTION_UP:
                    break;
            }

            return true;
        }
    };

    View.OnDragListener dragListener=new View.OnDragListener() {
        @Override
        public boolean onDrag(View view, DragEvent dragEvent) {
            switch (dragEvent.getAction()){
                case DragEvent.ACTION_DRAG_STARTED:
                    if (!locked){
                        turrets_button.setBackgroundResource(R.drawable.ic_baseline_arrow_left_24);
                        Animation animation= AnimationUtils.loadAnimation(Game.this,R.anim.turret_menu_closing);
                        turret_layout.setAnimation(animation);
                        turret_layout.setVisibility(View.INVISIBLE);
                        Tmenu=!Tmenu;
                    }
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    break;
                case DragEvent.ACTION_DROP:
                    touch.x = (int) dragEvent.getX();
                    touch.y= (int) dragEvent.getY();
                    if (!is_in_road(touch)&&!is_on_other(touch)){
                        TURRET turret=new TURRET(turret_choice,touch.x,touch.y,Game.this);
                        if (turret.price<=myAnimationView.cash){
                            myAnimationView.cash-=turret.price;
                            myAnimationView.setText();
                            myAnimationView.addTurret(turret);
                        }
                    }
                    break;
            }

            return true;
        }
    };

    private boolean is_on_other(Point touch){
            for (TURRET turret:myAnimationView.turrets){
                if (getDistance(turret.position,touch)<=turret.surface){
                    return true;
                }
            }
        return false;
    }
    private float getDistance(Point base,Point target){
        float distance = (float) Math.sqrt(Math.pow(target.x-base.x,2)+Math.pow(target.y-base.y,2));
        return distance;
    }
    private boolean is_in_road(Point target){
        road=level_library.levels[current_level].road;
        int left,right,top,end;
        for (int i=0;i<road.upline.length-1;i++){
            if (road.upline[i].x<road.downline[i+1].x){
                left=road.upline[i].x;
                right=road.downline[i+1].x;
            }
            else {
                left=road.downline[i+1].x;
                right=road.upline[i].x;
            }
            if (road.upline[i].y<road.downline[i+1].y){
                top=road.upline[i].y;
                end=road.downline[i+1].y;
            }
            else {
                top=road.downline[i+1].y;
                end=road.upline[i].y;
            }
            if (target.x>left&&target.x<right){
                if (target.y>top&&target.y<end){
                    return true;
                }
            }
        }
        return false;
    }
    private void light(Point point){
        for (TURRET turret:myAnimationView.turrets){
            if (getDistance(point,turret.position)<turret.surface){
                turret.light=true;
            }
            else {
                turret.light=false;
            }
        }
    }


    private void setAnimationThings() {
        level_library=new Level_Library(display_width,display_height, Game.this);
        road=level_library.levels[current_level].road;
        myAnimationView.cash=level_library.levels[current_level].cash;
        myAnimationView.health=level_library.levels[current_level].health;
        npc_list=level_library.levels[current_level].waves[current_wave].npc_list;
    }
    private void setText(){
        bank.setText("CASH: "+myAnimationView.cash);
        healthbar.setText("HEALTH: "+myAnimationView.health);
    }
    private void start_game(){
        setText();
        myAnimationView.setNpc_list(npc_list,Game.this);
    }
    private void go_menu(){
        finish_menu.setVisibility(View.INVISIBLE);
        end_menu.setVisibility(View.INVISIBLE);
        current_level=0;
        current_wave=0;
        Intent intent=new Intent(Game.this,Menu.class);
        share();
        finish();
        startActivity(intent);
    }
    private void share(){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt("opened_level",myAnimationView.opened_levels);
        editor.apply();
    }


    private void initialize(){
        Intent intent=getIntent();
        current_level=intent.getIntExtra("level",0);
        diff=intent.getBooleanExtra("difficulty",true);
        sound=intent.getBooleanExtra("sounds",true);
        touch=new Point();
        turret_menu=new ImageView[3];
        kules=new kule[3];
        for (int i=0;i<kules.length;i++){
            kules[i]=new kule();
            String name="turret"+i;
            int resID=getResources().getIdentifier(name,"id",getPackageName());
            kules[i].imageView=findViewById(resID);
            kules[i].imageView.setOnTouchListener(onTouchListener);
            kules[i].imageView.setOnDragListener(dragListener);
            String open="turret"+(i+1);
            int openID=getResources().getIdentifier(open,"drawable",getPackageName());
            Drawable opened=new BitmapDrawable(BitmapFactory.decodeResource(getResources(),openID));
            kules[i].open=opened;
            String locked="turret"+(i+1)+"_locked";
            int lockedID=getResources().getIdentifier(locked,"drawable",getPackageName());
            Drawable lock=new BitmapDrawable(BitmapFactory.decodeResource(getResources(),lockedID));
            kules[i].locked=lock;
        }
        sharedPreferences=getSharedPreferences("SP_NAME",MODE_PRIVATE);
        myAnimationView=findViewById(R.id.animation_view);
        myAnimationView.opened_levels=intent.getIntExtra("opened_level",1);
        myAnimationView.setOnDragListener(dragListener);
        soundPlayer=new SoundPlayer(this);
        lock=findViewById(R.id.lock);
        home =findViewById(R.id.home);
        pause=findViewById(R.id.pause);
        retry=findViewById(R.id.retry);
        next=findViewById(R.id.next);
        yes=findViewById(R.id.yes);
        no=findViewById(R.id.no);
        sure=findViewById(R.id.sure);
        sure.setVisibility(View.INVISIBLE);
        turrets_button=findViewById(R.id.turret_menu);
        menu=findViewById(R.id.menu);
        bank=findViewById(R.id.cash);
        finish=findViewById(R.id.finish);
        healthbar=findViewById(R.id.health);
        waves=findViewById(R.id.waves);
        //waves.setText("Remaining waves: "+level_library.levels[current_level].waves.length);
        end_menu=findViewById(R.id.end_menu);
        turret_layout=findViewById(R.id.turret_menu_layout);
        finish_menu=findViewById(R.id.finish_menu);
        turret_layout.setVisibility(View.INVISIBLE);
        end_menu.setVisibility(View.INVISIBLE);
        finish_menu.setVisibility(View.INVISIBLE);
    }
    private void screenAttributes() {
        WindowManager windowmanager = (WindowManager)this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics=new DisplayMetrics();
        windowmanager.getDefaultDisplay().getMetrics(displayMetrics);
        display_width = displayMetrics.widthPixels;
        display_height = displayMetrics.heightPixels;
    }

    public static void lock(int cash){
        kules[0].imageView.setBackgroundResource(R.drawable.turret1);
        kules[1].imageView.setBackgroundResource(R.drawable.turret2);
        kules[2].imageView.setBackgroundResource(R.drawable.turret3);
        int a;
        if (cash<50){
          a=0;
        }
        else if (cash<100){
            a=1;
        }
        else if(cash<200){
            a=2;
        }
        else {
            a=3;
        }

        for (int i=a;i<kules.length;i++){
            kules[i].imageView.setBackground(kules[i].locked);
        }
    }

    public class kule{
        ImageView imageView;
        Drawable open;
        Drawable locked;

        public kule() {

        }
    }
}