package com.example.findme;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class PositionHelper extends SQLiteOpenHelper {
    public static String file_name="mespositions.db";
    public static int version_base=1;
    public static final String table_pos="position";
    public static final String col_id="ID";
    public static final String col_num="Numero";
    public static final String col_long="longitude";
    public static final String col_lat="latitude";


    public PositionHelper(@Nullable Context context,
                          @Nullable String name, //nom fichier de bd *.db ou ilay le stockage dans tlf
                          @Nullable SQLiteDatabase.CursorFactory factory, //type de curseur generalement null
                          int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //lors de creation de fichier donc bd il sera appeler ==> creation de table
        db.execSQL(" create table  "+table_pos+"(" +
                col_id      +" integer primary Key autoincrement ," +
                col_num     +" Text not null," +
                col_long    +" Text not null," +
                col_lat     +" Text not null)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //en cas de maj de la version
        db.execSQL("drop table "+table_pos);
        onCreate(db);

    }
}
