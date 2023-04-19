package com.example.lab1;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Arrays;

public class Database extends SQLiteOpenHelper {
    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory,int version) {
        super(context,name,factory,version);
    }

    @Override
    public void onCreate(SQLiteDatabase sql) {

        String qry1 = "CREATE TABLE flashcard (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "question TEXT NOT NULL,answer TEXT NOT NULL,wrong_answer1 TEXT,wrong_answer2 TEXT)";

        sql.execSQL(qry1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sql, int i, int i1) {

    }

    public int addQuestion(String question,String answer,String wrong_answer1,String wrong_answer2) {
        ContentValues cntV = new ContentValues();
        cntV.put("question",question);
        cntV.put("answer",answer);
        cntV.put("wrong_answer1",wrong_answer1);
        cntV.put("wrong_answer2",wrong_answer2);

        SQLiteDatabase db = getWritableDatabase();
        db.insert("flashcard",null,cntV);
        db.close();

        return 1;
    }

    public int listerFlashcard(String wrong_answer1){
        int result = 0;
        String str[] = new String[1];
        str[0] = "1";
        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM flashcard WHERE id = ?",str);
        if(c.moveToFirst()){
            result =1;
        }
        return  result;
    }

    public  int lengthRow(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT COUNT(*) FROM flashcard ",null);
        int val = 0;

        if(c.moveToFirst()) {
            val = c.getInt(0);
        }

        return  val;

    }

    public int getId(int a) {
        int getid = 0;
        int x = a-1;
        String[] row = new String[1];
        row[0] = "" +x;
        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.rawQuery("SELECT id ,question FROM flashcard  LIMIT 1 OFFSET ? ", row);

        //Saisie de l'ID d'une ligne en question
        int columnIndexId = c.getColumnIndex("id");
        int columnIndexqt = c.getColumnIndex("question");

        String b = null;
        if (c.moveToNext()) {
            getid = c.getInt(columnIndexId);
            b = c.getString(columnIndexqt);
        }

        Log.i("", "ID :" + a);
        Log.i("", "ID :" + b);
        c.close();
        return getid;
    }
    public String[][] callQuestion(int a) {

        MainActivity  main = new MainActivity();
        String[][] quiz = new String[1][4];
        String[] row = new String[1];
        String[] tmp = new String[1];
        row[0] = ""+a;
        TextView question, reponse1, reponse2, reponse3;
        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.rawQuery("SELECT question,answer,wrong_answer1 ,wrong_answer2 FROM flashcard  LIMIT 1 OFFSET ?", row);

        //Index de chaque colonne de la base de donnee
        int columnIndexQtn = c.getColumnIndex("question");
        int columnIndexAnswer = c.getColumnIndex("answer");
        int columnIndexWAns1 = c.getColumnIndex("wrong_answer1");
        int columnIndexWAns2 = c.getColumnIndex("wrong_answer2");

        String wrong_answer1 = null;
        String answer=null;
        String qtn = null;
        String wrong_answer2 = null;

       if (c.moveToFirst()) {
            qtn = c.getString(columnIndexQtn);
            answer = c.getString(columnIndexAnswer);
            wrong_answer1 = c.getString(columnIndexWAns1);
            wrong_answer2 = c.getString(columnIndexWAns2);

            quiz[0][0] = qtn;
            quiz[0][1] = answer;
            quiz[0][2] = wrong_answer1;
            quiz[0][3] = wrong_answer2;


       }

       db.close();
        return quiz;
    }

    public void delete(int a ){
        MainActivity main = new MainActivity();
        int id =getId(a);
        String[] row = new String[1];
        SQLiteDatabase db = this.getWritableDatabase();
        String deleteQuery = "DELETE FROM flashcard WHERE id = "+id;
        db.execSQL(deleteQuery);


       db.close();


    }
}



