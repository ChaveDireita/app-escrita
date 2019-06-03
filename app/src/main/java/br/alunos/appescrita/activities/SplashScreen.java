package br.alunos.appescrita.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.alunos.appescrita.R;

public class SplashScreen extends AppCompatActivity implements Runnable
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();

        new Handler().postDelayed(this, 3000);

    }

    @Override
    public void run()
    {
        Intent abrirtelalogin = new Intent(this, ActivityLogin.class);
        startActivity(abrirtelalogin);
        finish();
    }
}
