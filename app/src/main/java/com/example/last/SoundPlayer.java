package com.example.last;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class SoundPlayer {
    private static SoundPool soundPool;
    private static int gun1,gun2,gun3,roblox,win,explode;

    public SoundPlayer(Context context) {

        soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC,0);

        gun1 =soundPool.load((Activity)context,R.raw.gun2,1);
        gun2 =soundPool.load((Activity)context,R.raw.gun2,1);
        gun3 =soundPool.load((Activity)context,R.raw.gun3,1);
        explode =soundPool.load((Activity)context,R.raw.exp,1);
        roblox=soundPool.load((Activity)context,R.raw.rbds,1);
        win=soundPool.load((Activity)context,R.raw.win,1);
    }
    public void playHit(){
        soundPool.play(roblox,1.0f,1.0f,1,0,1.0f);
    }
    public void playshot(int sound){
        soundPool.play(sound,1.0f,1.0f,1,0,1.0f);
    }
    public void explode(){
        soundPool.play(explode,1.0f,1.0f,1,0,1.0f);
    }
    public void playwin(){
        soundPool.play(win,1.0f,1.0f,1,0,1.0f);
    }

}
