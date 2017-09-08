package com.abdelwahabelazab.brainchallenge;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;
public class QuestionActivity extends AppCompatActivity {

    Random random=new Random();
    int correctAnswerLocation;
    AlertDialog.Builder builder;
    Context context;
    String buTag;
    int tag;
    boolean isEnabled;
    boolean activityActive;
    String timeText;
    String finalScore;
    int currentScore;
    int fullScore;
    String [] scoreS;
    int allSeconds;
    TextView txtTime;
    TextView txtScore;
    TextView txtQues;
    Button buAns1;
    Button buAns2;
    Button buAns3;
    Button buAns4;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        activityActive=true;
        isEnabled=true;
        linearLayout=(LinearLayout) findViewById(R.id.linear);
        linearLayout.setEnabled(true);
        txtTime=(TextView) findViewById(R.id.second);
        txtScore=(TextView) findViewById(R.id.score_inc);
        txtQues=(TextView) findViewById(R.id.question);
        buAns1=(Button) findViewById(R.id.ans1);
        buAns2=(Button) findViewById(R.id.ans2);
        buAns3=(Button) findViewById(R.id.ans3);
        buAns4=(Button) findViewById(R.id.ans4);
        Intent intent=this.getIntent();
        buTag=intent.getStringExtra("tag");
        switch (buTag){
            case "0":timeText="50";allSeconds=50;txtTime.setText(timeText);break;
            case "1":timeText="40";allSeconds=40;txtTime.setText(timeText);break;
            case "2":timeText="30";allSeconds=30;txtTime.setText(timeText);break;
            case "3":timeText="20";allSeconds=20;txtTime.setText(timeText);break;
            case "4":timeText="11";allSeconds=11;txtTime.setText(timeText);break;
        }
        timeDown();


       newQues();
    }

    public void timeDown(){
        new CountDownTimer(allSeconds*1000,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                int second=(int) millisUntilFinished/1000;
                String timeText;
                switch (buTag){
                    case "0":
                      timeDown2();if(second%5==0) {newQues();};break;
                    case "1":
                        timeDown2();if(second%4==0) {newQues();}break;
                    case "2":
                        timeDown2();if(second%3==0) {newQues();};break;
                    case "3":
                        timeDown2();if(second%2==0){newQues();};break;

                    default:
                        timeDown2();{newQues();};break;

                }



            }

            @Override
            public void onFinish() {
                if (activityActive) {
                    String score = scoreS[0];
                    String message = getResources().getString(R.string.score)+" : " + score + "/" + "10";
                    txtScore.setText(message);
                    builder = new AlertDialog.Builder(QuestionActivity.this);
                    builder.setIcon(R.drawable.dialog_icon)
                            .setTitle(getResources().getString(R.string.over))
                            .setMessage(message)
                            .setPositiveButton(getResources().getString(R.string.again), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    linearLayout.setEnabled(true);
                                    String restartSecond = Integer.toString(allSeconds);
                                    txtTime.setText(restartSecond);
                                    String restartScore = "0/0";
                                    txtScore.setText(restartScore);
                                    timeDown();
                                    newQues();
                                }
                            })
                            .setNegativeButton(getResources().getString(R.string.exit), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent i = new Intent(QuestionActivity.this, MainActivity.class);
                                    startActivity(i);
                                }
                            })
                            .show();

                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                    mediaPlayer.start();
                    linearLayout.setEnabled(false);
                    buAns1.setEnabled(false);
                    buAns2.setEnabled(false);
                    buAns3.setEnabled(false);
                    buAns4.setEnabled(false);
                }
            }
        }.start();

    }
    public void timeDown2(){
        String ss=txtTime.getText().toString();
        String [] SplitS=ss.split("s");
        int seconds=Integer.parseInt(SplitS[0]);
        seconds--;
        String rSeconds=Integer.toString(seconds);
        String finalSeconds=rSeconds+"s";
        txtTime.setText(finalSeconds);
    }
    public void newQues(){

        if(buAns1.isEnabled()) {
            incScore();
        }

            buAns1.setEnabled(true);
            buAns2.setEnabled(true);
            buAns3.setEnabled(true);
            buAns4.setEnabled(true);

        if(buAns1.isEnabled()){

            buAns1.setBackgroundColor(getResources().getColor(R.color.btn_score_back_color));
            buAns2.setBackgroundColor(getResources().getColor(R.color.btn_score_back_color));
            buAns3.setBackgroundColor(getResources().getColor(R.color.btn_score_back_color));
            buAns4.setBackgroundColor(getResources().getColor(R.color.btn_score_back_color));
        }
        int number1=random.nextInt(20);
        int number2=random.nextInt(20);
        int incorrectAnswer=random.nextInt(40);
        correctAnswerLocation=random.nextInt(4);
        String question=txtQues.getText().toString();
        String [] questionS=question.split("\\+");
        questionS[0]=Integer.toString(number1);
        questionS[1]=Integer.toString(number2);
        String finalQues=questionS[0]+" + "+questionS[1];
        txtQues.setText(finalQues);

        int correctAnswer=Integer.parseInt(questionS[0])+Integer.parseInt(questionS[1]);
        int [] locations=new int[4];
        for (int i=0;i<4;i++){
            if (i == correctAnswerLocation){
                locations[i]=correctAnswer;
            }
            else {
                incorrectAnswer=random.nextInt(40);
                while (incorrectAnswer == correctAnswer){
                    incorrectAnswer=random.nextInt(40);
                }
                locations[i]=incorrectAnswer;
            }
        }
        String ans1=Integer.toString(locations[0]);
        String ans2=Integer.toString(locations[1]);
        String ans3=Integer.toString(locations[2]);
        String ans4=Integer.toString(locations[3]);

        buAns1.setText(ans1);
        buAns2.setText(ans2);
        buAns3.setText(ans3);
        buAns4.setText(ans4);


    }

    public void buChoose(View view){


        String score=txtScore.getText().toString();
        scoreS=score.split("/");
        currentScore=Integer.parseInt(scoreS[0]);
        fullScore=Integer.parseInt(scoreS[1]);
        tag=Integer.parseInt(view.getTag().toString());
        fullScore++;
        scoreS[1]=Integer.toString(fullScore);
        if (correctAnswerLocation == tag){
             currentScore++;
            scoreS[0]=Integer.toString(currentScore);
            finalScore=scoreS[0]+"/"+scoreS[1];
            txtScore.setText(finalScore);

                view.setBackgroundColor(Color.GREEN);

        }
        else{
            scoreS[0]=Integer.toString(currentScore);
            finalScore=scoreS[0]+"/"+scoreS[1];
            txtScore.setText(finalScore);

                view.setBackgroundColor(Color.RED);

        }

        buAns1.setEnabled(false);
        buAns2.setEnabled(false);
        buAns3.setEnabled(false);
        buAns4.setEnabled(false);

    }
    public void incScore(){
        String score=txtScore.getText().toString();
        scoreS=score.split("/");
        currentScore=Integer.parseInt(scoreS[0]);
        fullScore=Integer.parseInt(scoreS[1]);
        if(txtTime.getText().toString() != timeText) {
            fullScore++;
        }

        scoreS[1]=Integer.toString(fullScore);

        scoreS[0]=Integer.toString(currentScore);
        finalScore=scoreS[0]+"/"+scoreS[1];
        txtScore.setText(finalScore);
    }

    @Override
    protected void onPause() {
        super.onPause();
        activityActive=false;
    }

}
