package br.alunos.appescrita.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import br.alunos.appescrita.R;

public class SplashScreen extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
    }


    @Override
    protected void onResume()
    {
        super.onResume();
        Intent intent = new Intent(this, ActivityLogin.class);
        startActivity(intent);

        finish();
    }
}
