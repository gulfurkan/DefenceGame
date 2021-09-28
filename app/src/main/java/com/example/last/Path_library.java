package com.example.last;

import android.graphics.Path;
import android.graphics.Point;

public class Path_library {
    float width,height;
    Road road;
    public class Road{
        Point[] upline,downline,pathline;
        Path    uppath,downpath,path;

        public Road(int sizeofpoints) {
            upline=new Point[sizeofpoints];
            downline=new Point[sizeofpoints];
            pathline=new Point[sizeofpoints];
            for (int i=0;i<sizeofpoints;i++){
                upline[i]=new Point();
                downline[i]=new Point();
                pathline[i]=new Point();
            }
            uppath=new Path();
            downpath=new Path();
            path=new Path();
        }
    }


    public Path_library(int w, int h) {
        this.width = (float)w;
        this.height = (float)h;
    }

    public Path level1_path(float offset_X,float offsetY){
        Path path=new Path();
        path.moveTo(syncX(offset_X),syncY(590));
        path.lineTo(syncX(290),syncY(590));
        path.lineTo(syncX(290),syncY(260));
        path.lineTo(syncX(660),syncY(260));
        path.lineTo(syncX(660),syncY(690));
        path.lineTo(syncX(1140),syncY(690));
        path.lineTo(syncX(1140),syncY(480));
        path.lineTo(syncX(1900),syncY(480));
        return path;
    }
    public Road level1_road(){
        road=new Road(8);
        //pathline
        road.pathline[0].x= (int) syncX(0);
        road.pathline[0].y= (int) syncY(590);

        road.pathline[1].x= (int) syncX(290);
        road.pathline[1].y= (int) syncY(590);

        road.pathline[2].x= (int) syncX(290);
        road.pathline[2].y= (int) syncY(260);

        road.pathline[3].x= (int) syncX(660);
        road.pathline[3].y= (int) syncY(260);

        road.pathline[4].x= (int) syncX(660);
        road.pathline[4].y= (int) syncY(690);

        road.pathline[5].x= (int) syncX(1140);
        road.pathline[5].y= (int) syncY(690);

        road.pathline[6].x= (int) syncX(1140);
        road.pathline[6].y= (int) syncY(480);

        road.pathline[7].x= (int) syncX(1900);
        road.pathline[7].y= (int) syncY(480);
        // path
        road.path=pointToPath(road.pathline);

        //upline
        road.upline[0].x= (int) syncX(0);
        road.upline[0].y= (int) syncY(530);

        road.upline[1].x= (int) syncX(230);
        road.upline[1].y= (int) syncY(530);

        road.upline[2].x= (int) syncX(230);
        road.upline[2].y= (int) syncY(210);

        road.upline[3].x= (int) syncX(725);
        road.upline[3].y= (int) syncY(210);

        road.upline[4].x= (int) syncX(725);
        road.upline[4].y= (int) syncY(640);

        road.upline[5].x= (int) syncX(1070);
        road.upline[5].y= (int) syncY(640);

        road.upline[6].x= (int) syncX(1070);
        road.upline[6].y= (int) syncY(427);

        road.upline[7].x= (int) syncX(1900);
        road.upline[7].y= (int) syncY(427);
        //uppath
        road.uppath=pointToPath(road.upline);
        //downline
        road.downline[0].x= (int) syncX(0);
        road.downline[0].y= (int) syncY(650);
        road.downline[1].x= (int) syncX(350);
        road.downline[1].y= (int) syncY(650);
        road.downline[2].x= (int) syncX(350);
        road.downline[2].y= (int) syncY(320);
        road.downline[3].x= (int) syncX(590);
        road.downline[3].y= (int) syncY(320);
        road.downline[4].x= (int) syncX(590);
        road.downline[4].y= (int) syncY(755);
        road.downline[5].x= (int) syncX(1205);
        road.downline[5].y= (int) syncY(755);
        road.downline[6].x= (int) syncX(1205);
        road.downline[6].y= (int) syncY(550);
        road.downline[7].x= (int) syncX(1900);
        road.downline[7].y= (int) syncY(550);
        //downpath
        road.downpath=pointToPath(road.downline);
        return road;
    }


    private Path pointToPath(Point[] points){
        Path path=new Path();
        path.moveTo(points[0].x,points[0].y);
        for (int i=1;i<points.length;i++){
            path.lineTo(points[i].x,points[i].y);
        }
        return path;
    }
    private float syncX(float corX){
        return (corX/1080)*height;
    }
    private float syncY(float corY){
        return (corY/1794)*width;
    }
}
