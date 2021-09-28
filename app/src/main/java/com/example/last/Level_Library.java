package com.example.last;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

public class Level_Library {
    Level[] levels=new Level[2];
    Path_library path_library;
    Context context;
    public static class Level {
        int levelID,sizeofwaves,sizeofroads;
        Path_library.Road road;
        Bitmap background,base;
        Waves[] waves;
        int cash,health;

        public Level(int levelID, int sizeofwaves, int sizeofroads, Path_library.Road road, int cash, int health) {
            this.levelID = levelID;
            this.sizeofwaves = sizeofwaves;
            this.sizeofroads = sizeofroads;
            this.road=road;
            waves=new Waves[sizeofwaves];
            this.cash = cash;
            this.health = health;
        }

        public Level() {

        }

        public static class Waves {
            int sizeofnpc;
            NPC[] npc_list;

            public Waves(int sizeofnpc) {
                this.sizeofnpc = sizeofnpc;
                this.npc_list=new NPC[sizeofnpc];
                int x=0;
            }
        }

    }

    public Level_Library(int display_width, int display_height,Context context) {
        path_library=new Path_library(display_width,display_height);
        this.context=context;
        set_levels();
    }

    private void set_levels() {
        levels[0]=new Level(1,2,1,path_library.level1_road(),100,10);
        levels[0].base=BitmapFactory.decodeResource(context.getResources(),R.drawable.base);
        levels[0].background= BitmapFactory.decodeResource(context.getResources(),R.drawable.background);
        levels[0].levelID=0;
        levels[0].waves[0]=new Level.Waves(10);
        for (int i=0;i<levels[0].waves[0].sizeofnpc;i++){
            levels[0].waves[0].npc_list[i]=new NPC(0,context,path_library.level1_path((i+1)*-200,550));
            levels[0].waves[0].npc_list[i].road_selection=0;
        }
        levels[0].waves[1]=new Level.Waves(10);
        for (int i=0;i<levels[0].waves[1].sizeofnpc;i++){
            levels[0].waves[1].npc_list[i]=new NPC(1,context,path_library.level1_path((i+1)*-200,550));
            levels[0].waves[1].npc_list[i].road_selection=0;
        }


        levels[1]=new Level(1,3,1,path_library.level1_road(),200,10);
        levels[1].background= BitmapFactory.decodeResource(Resources.getSystem(),R.drawable.background);
        levels[1].levelID=1;
        levels[1].waves[0]=new Level.Waves(10);
        for (int i=0;i<levels[1].waves[0].sizeofnpc;i++){
            levels[1].waves[0].npc_list[i]=new NPC(1,context,path_library.level1_path((i+1)*-200,550));
            levels[1].waves[0].npc_list[i].road_selection=0;
        }
        levels[1].waves[1]=new Level.Waves(15);
        for (int i=0;i<levels[1].waves[1].sizeofnpc;i++){

            levels[1].waves[1].npc_list[i]=new NPC(2,context,path_library.level1_path((i+1)*-200,550));
            levels[1].waves[1].npc_list[i].road_selection=0;
        }
        levels[1].waves[2]=new Level.Waves(20);
        for (int i=0;i<levels[1].waves[2].sizeofnpc;i++){
            levels[1].waves[2].npc_list[i]=new NPC(2,context,path_library.level1_path((i+1)*-200,550));
            levels[1].waves[2].npc_list[i].road_selection=0;
        }

        levels[1]=new Level(1,3,1,path_library.level1_road(),200,10);
        levels[1].background= BitmapFactory.decodeResource(Resources.getSystem(),R.drawable.background);
        levels[1].levelID=1;
        levels[1].waves[0]=new Level.Waves(10);
        for (int i=0;i<levels[1].waves[0].sizeofnpc;i++){
            levels[1].waves[0].npc_list[i]=new NPC(1,context,path_library.level1_path((i+1)*-200,550));
            levels[1].waves[0].npc_list[i].road_selection=0;
        }
        levels[1].waves[1]=new Level.Waves(15);
        for (int i=0;i<levels[1].waves[1].sizeofnpc;i++){

            levels[1].waves[1].npc_list[i]=new NPC(2,context,path_library.level1_path((i+1)*-200,550));
            levels[1].waves[1].npc_list[i].road_selection=0;
        }
        levels[1].waves[2]=new Level.Waves(20);
        for (int i=0;i<levels[1].waves[2].sizeofnpc;i++){
            levels[1].waves[2].npc_list[i]=new NPC(2,context,path_library.level1_path((i+1)*-200,550));
            levels[1].waves[2].npc_list[i].road_selection=0;
        }
    }
}
