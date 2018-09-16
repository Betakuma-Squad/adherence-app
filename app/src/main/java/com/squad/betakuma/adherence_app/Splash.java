package com.squad.betakuma.adherence_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Splash extends Activity {
    private static final int WAIT = 1000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), PrescriptionListActivity.class);
                getApplicationContext().startActivity(intent);
                Splash.this.finish();
            }
        }, WAIT);
    }
}
