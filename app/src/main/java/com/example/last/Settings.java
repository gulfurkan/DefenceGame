package com.example.last;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Set;

public class Settings extends AppCompatActivity implements View.OnClickListener {
    private Button sound,difficulty,back;
    private boolean sounds=true,easy=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initialize();
    }

    private void initialize(){
        sound=findViewById(R.id.sound);
        sound.setOnClickListener(this);
        difficulty=findViewById(R.id.difficulty);
        difficulty.setOnClickListener(this);
        back=findViewById(R.id.settings_back);
        back.setOnClickListener(this);
        GradientDrawable gradientDrawable = (GradientDrawable) back.getBackground().mutate();
        gradientDrawable.setColor(Color.LTGRAY);
    }

    @Override
    public void onClick(View view) {
            switch (view.getId()){
                case R.id.sound:
                    GradientDrawable gradientDrawable = (GradientDrawable) sound.getBackground().mutate();
                    if (sounds){
                        gradientDrawable.setColor(Color.RED);
                        sound.setText("SOUNDS OFF");
                    }
                    else {
                        gradientDrawable.setColor(Color.GREEN);
                        sound.setText("SOUNDS ON");
                    }
                    sounds=!sounds;
                    break;
                case R.id.difficulty:
                    gradientDrawable = (GradientDrawable) difficulty.getBackground().mutate();
                    if (easy){
                        gradientDrawable.setColor(Color.RED);
                        difficulty.setText("HARD");
                    }
                    else {
                        gradientDrawable.setColor(Color.GREEN);
                        difficulty.setText("EASY");
                    }
                    easy=!easy;
                    break;
                case R.id.settings_back:
                    Intent intent=new Intent(Settings.this,Menu.class);
                    intent.putExtra("sound",sounds);
                    intent.putExtra("diff",easy);
                    finish();
                    startActivity(intent);
                    break;
            }
    }
}