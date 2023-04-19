package com.example.lab1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    Button btn, btn2, btn3;
    ImageView add_btn,next_btn,del_btn;
    TextView qtn_view,text_question;
    String qtn,ans1,ans2,ans3;
    int index = 0;
    int y;
    String row = String.valueOf(index);
    String[][] lastQtn = new String[1][4];

    //variable qui contiendra la liste des questions et reponses
    String[][] ListQtn = new String[1][4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btn = findViewById(R.id.btn_answer1);
        btn2 = findViewById(R.id.btn_answer2);
        btn3 = findViewById(R.id.btn_answer3);
        add_btn = findViewById(R.id.ic_add);
        next_btn = findViewById(R.id.ic_next);
        text_question = findViewById(R.id.text_question);
        del_btn = findViewById(R.id.ic_del);

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (view.getId() == R.id.btn_answer2) {

                    Log.i("Condition if", "bouton 2 :" + R.id.btn_answer2);

                    if(view.getId() == R.id.btn_answer2 )
                    {
                        btn2.setBackgroundColor(Color.parseColor("#FF0000"));
                        btn3.setBackgroundColor(Color.parseColor("#FFBB86FC"));

                    }

                }
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Tag", "bouton 3 clicked");

                if(view.getId() == R.id.btn_answer3 )
                {
                    btn3.setBackgroundColor(Color.parseColor("#FF0000"));
                    btn2.setBackgroundColor(Color.parseColor("#FFBB86FC"));
                }
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Tag", "bouton 1 clicked");
                if(view.getId() == R.id.btn_answer1 )
                {
                    btn.setBackgroundColor(Color.parseColor("#00FF00"));
                    btn2.setBackgroundColor(Color.parseColor("#FFBB86FC"));
                    btn3.setBackgroundColor(Color.parseColor("#FFBB86FC"));
                }
            }
        });

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                    startActivity(intent);
            }
        });

        next_btn.setOnClickListener(new View.OnClickListener() {

            Database db = new Database(getApplicationContext(),"flashcard",null,1);
            @Override
            public void onClick(View view) {

                y = db.lengthRow();

                //Condition pour eviter que l'app plante quand il n'y a plus de ligne a lire dans la BD
                if(index < db.lengthRow()){

                    for (int i = 0; i < 1; i++) {
                        for (int j = 0; j < 4; j++) {
                            ListQtn[i][j] = db.callQuestion(index)[0][j];
                            lastQtn[i][j] = db.callQuestion(y)[0][j];

                        }

                    }

                    text_question.setText(ListQtn[0][0]);
                    btn.setText(ListQtn[0][1]);
                    btn2.setText(ListQtn[0][2]);
                    btn3.setText(ListQtn[0][3]);

                }
                else {

                    for (int i = 0; i < 1; i++) {
                        for (int j = 0; j < 4; j++) {
                            ListQtn[i][j] = db.callQuestion(index)[0][j];
                            lastQtn[i][j] = db.callQuestion(y)[0][j];
                        }

                    }

                    //Creation du snackbar
                    Snackbar snackbar = Snackbar.make( btn3,"\t\t\t\t\t\t\t\t Il n'y a plus de question",Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(Color.parseColor("#F11515"));
                    FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) snackbarView.getLayoutParams();
                    params.setMargins(180,0,180,300);
                    snackbarView.setLayoutParams(params);
                    snackbar.show();

                    //Bloquer sur une question qd il n'y plus de question dans la base de donnee
                    text_question.setText("Who is the 44th President of the United States?");
                    btn.setText("Barack Obama");
                    btn2.setText("Bill Clinton");
                    btn3.setText("George H. W. Bush");

                    next_btn.setVisibility(View.INVISIBLE);
                }

                //db.close();
                index++;

            }
        });


        del_btn.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                Database db = new Database(getApplicationContext(),"flashcard",null,1);
                Log.i("a","index "+index);
                db.delete(index);

                y = db.lengthRow();

//                Log.i("","index : "+index);
//                Log.i("","index y : "+y);

                //Condition pour afficher la ligne suivante quand on supprime un ligne
                if(index < y){
                    Log.i("","if1");
                    for (int i = 0; i < 1; i++) {
                        for (int j = 0; j < 4; j++) {
                            ListQtn[i][j] = db.callQuestion(index-1)[0][j];

                        }

                    }

                    text_question.setText(ListQtn[0][0]);
                    btn.setText(ListQtn[0][1]);
                    btn2.setText(ListQtn[0][2]);
                    btn3.setText(ListQtn[0][3]);

                }
                else {
                    Log.i("","if2");
                    for (int i = 0; i < 1; i++) {
                        for (int j = 0; j < 4; j++) {
                            ListQtn[i][j] = db.callQuestion(index+1)[0][j];
                            lastQtn[i][j] = db.callQuestion(y)[0][j];
                        }

                    }

                    //Creation du snackbar
                    Snackbar snackbar = Snackbar.make( btn3,"\t\t\t\t\t\t\t\t Il n'y a plus de question",Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(Color.parseColor("#F11515"));
                    FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) snackbarView.getLayoutParams();
                    params.setMargins(180,0,180,300);
                    snackbarView.setLayoutParams(params);
                    snackbar.show();

                    //Bloquer sur une question qd il n'y plus de question dans la base de donnee
                    text_question.setText("Who is the 44th President of the United States?");
                    btn.setText("Barack Obama");
                    btn2.setText("Bill Clinton");
                    btn3.setText("George H. W. Bush");

                    next_btn.setVisibility(View.INVISIBLE);
                }

                db.close();
                index++;

            }
        });


    }

}