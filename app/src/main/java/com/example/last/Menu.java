package com.example.last;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class Menu extends AppCompatActivity implements View.OnClickListener{
    Button play,settings,market,exit,marback;
    LinearLayout level_menu,settings_menu,market_menu;
    ImageButton[] levels;
    int opened_levels,sizeoflevels=2;
    SharedPreferences sharedPreferences;
    boolean diff,sounds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        initialize();
    }
    private void initialize() {
        sharedPreferences=getSharedPreferences("SP_NAME",MODE_PRIVATE);
        opened_levels=sharedPreferences.getInt("opened_level",1);
        Intent intent=getIntent();
        diff=intent.getBooleanExtra("diff",true);
        sounds=intent.getBooleanExtra("sound",true);
        play =findViewById(R.id.home);
        play.setOnClickListener(this);
        settings=findViewById(R.id.settings);
        settings.setOnClickListener(this);
        market=findViewById(R.id.market);
        market.setOnClickListener(this);
        exit=findViewById(R.id.exit);
        exit.setOnClickListener(this);
        marback=findViewById(R.id.market_back);
        marback.setOnClickListener(this);
        GradientDrawable gradientDrawable = (GradientDrawable) marback.getBackground().mutate();
        gradientDrawable.setColor(Color.LTGRAY);
        level_menu =findViewById(R.id.levels);
        level_menu.setVisibility(View.INVISIBLE);
        market_menu=findViewById(R.id.market_menu);
        market_menu.setVisibility(View.INVISIBLE);
        levels=new ImageButton[9];
        for (int i=0;i<levels.length;i++){
            String name="level"+i;
            int resID=getResources().getIdentifier(name,"id",getPackageName());
            levels[i]=findViewById(resID);
            if (i<opened_levels){
                String B_name="level"+(i+1);
                int B_resID=getResources().getIdentifier(B_name,"drawable",getPackageName());
                levels[i].setImageResource(B_resID);
                levels[i].setOnClickListener(this);
            }
            else if (i<sizeoflevels){
                levels[i].setImageResource(R.drawable.lock180);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.home:
                level_menu.setVisibility(View.VISIBLE);
                break;
            case R.id.settings:
                Intent set=new Intent(Menu.this,Settings.class);
                finish();
                startActivity(set);
                break;
            case R.id.market:
                market_menu.setVisibility(View.VISIBLE);
                break;
            case R.id.exit:
                finish();
                break;
            case R.id.market_back:
                market_menu.setVisibility(View.INVISIBLE);
                break;
            case R.id.settings_back:
                settings_menu.setVisibility(View.INVISIBLE);
                break;
            default:
                for (int i=0;i<levels.length;i++){
                    if (levels[i].getId()==view.getId()){
                        level_menu.setVisibility(View.INVISIBLE);
                        Intent intent=new Intent(Menu.this,Game.class);
                        intent.putExtra("level",i);
                        intent.putExtra("opened_levels",opened_levels);
                        intent.putExtra("difficulty",diff);
                        intent.putExtra("sounds",sounds);
                        finish();
                        startActivity(intent);
                        break;
                    }
                }
                break;
        }
    }
}