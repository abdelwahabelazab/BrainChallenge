package com.abdelwahabelazab.brainchallenge;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class Splash extends Activity {


    private static long sleep_time=2;
    final Context context=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        IntentLauncher intentLauncher=new IntentLauncher();
        intentLauncher.start();

    }

    class IntentLauncher extends Thread{
        @Override
        public void run() {
            try {
                Thread.sleep(sleep_time*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            // start main activity

            Intent intent = new Intent(Splash.this, MainActivity.class);
            Splash.this.startActivity(intent);
            Splash.this.finish();
        }
    }
}

