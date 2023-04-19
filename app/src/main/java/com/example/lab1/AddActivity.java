package com.example.lab1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ThemedSpinnerAdapter;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
public class AddActivity extends AppCompatActivity {
    ImageView cancel_btn, edit_btn;
    EditText question, answer1, answer2, answer3;
    String qtn, ans1, ans2, ans3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        cancel_btn = findViewById(R.id.ic_cancel_btn);
        edit_btn = findViewById(R.id.ic_edit_btn);
        question = findViewById(R.id.add_question);
        answer1 = findViewById(R.id.add_answer1);
        answer2 = findViewById(R.id.add_answer2);
        answer3 = findViewById(R.id.add_answer3);
        Database db = new Database(getApplicationContext(),"flashcard",null,1);
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddActivity.this, AddActivity.class);
                finish();
            }
        });

        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentPut = new Intent(AddActivity.this, MainActivity.class);
                qtn = question.getText().toString();
                ans1 = answer1.getText().toString();
                ans2 = answer2.getText().toString();
                ans3 = answer3.getText().toString();

                if (TextUtils.isEmpty(qtn) && TextUtils.isEmpty(ans1) && TextUtils.isEmpty(ans2) && TextUtils.isEmpty(ans3)) {

                    edit_btn.setEnabled(false);
                    Toast.makeText(AddActivity.this, "Fill in empty spaces", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    edit_btn.setEnabled(true);
                    intentPut.putExtra("qtn", qtn);
                    intentPut.putExtra("ans1", ans1);
                    intentPut.putExtra("ans2", ans2);
                    intentPut.putExtra("ans3", ans3);
                    startActivity(intentPut);

                    //Ajout des questions et reponses dans la base de donne

                    if(db.addQuestion(qtn,ans1,ans2,ans3) == 1)
                    {
                        Toast.makeText(AddActivity.this, "Insertion reussie", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(AddActivity.this, "Erreur d'insertion", Toast.LENGTH_SHORT).show();
                    }

//                    if(db.listerFlashcard(ans2) == 1) {
//
//                        int index = 2;
//                        String row = String.valueOf(index);
//                        String[][] ListQtn =  new String[1][4];
//
//                        for (int i = 0; i < 1;i++){
//                            for (int j = 0; j < 4; j++){
//                                ListQtn[i][j] = db.callQuestion(index)[0][j];
//                            }
//                        }
//
//                        question.setText( ListQtn[0][0]);
//                        answer1.setText(ListQtn[0][1]);
//                        answer2.setText(ListQtn[0][2]);
//                        answer3.setText(ListQtn[0][3]);
//
//                    }
//                    else
//                    {
//                        Toast.makeText(AddActivity.this, "La donnee ne se trouve pas dans la base de donnee", Toast.LENGTH_SHORT).show();
//                    }


                }

            }

        });


    }
}