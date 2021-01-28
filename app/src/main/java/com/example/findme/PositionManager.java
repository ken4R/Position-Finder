package com.example.findme;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class PositionManager {
    Context con;
    private SQLiteDatabase mabase;

    public  PositionManager(Context con,String fichier,int version){
        this.con=con;
        ouvrir(fichier,version);
    }



    private void ouvrir(String nom_fichier, int version)
    {

        PositionHelper helper=new PositionHelper(con,nom_fichier,null,version);
        mabase = helper.getWritableDatabase(); //soit create soit upgrate selon la version et le nom de fichier

    }
    public long inserer(String num , String lon , String lat)
    {
        long a=-1;
        ContentValues v=new ContentValues();
        v.put(PositionHelper.col_num,num);
        v.put(PositionHelper.col_long,lon);
        v.put(PositionHelper.col_lat,lat);
        a=mabase.insert(PositionHelper.table_pos,null,v);
        return a;
    }

    public ArrayList<Position> selectionnertout() {
        ArrayList<Position> a=new ArrayList<Position>();
        Cursor cr=mabase.query( PositionHelper.table_pos,
                new String[]{PositionHelper.col_id,
                PositionHelper.col_num,
                PositionHelper.col_long,
                PositionHelper.col_lat},
                null,
                null,
                PositionHelper.col_num,
                null,
                PositionHelper.col_id);
        //rselt set pointeur ypointu 3a rslt ta3 selct colonne par colonne
        cr.moveToFirst();

        while (!cr.isAfterLast())
        {
            int i=cr.getInt(0);
            String num=cr.getString(1);
            String lo=cr.getString(2);
            String la=cr.getString(3);
            Position p= new Position(i,num,la,lo);
            a.add(p);
            cr.moveToNext();
        }

        return a;
    }
    public int supprimer (String num)
    {
        int a=-1;
        a=mabase.delete(PositionHelper.table_pos,
                PositionHelper.col_num+"="+num,
                null);

        return a;
    }
}
