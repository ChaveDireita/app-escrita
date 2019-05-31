package br.alunos.appescrita.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import br.alunos.appescrita.R;

public class ActivityLogin extends AppCompatActivity
{

    EditText editTextLogin;
    EditText editTextSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);



        EditText editText = findViewById(R.id.campo_senha);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
            {
                if (actionId == EditorInfo.IME_ACTION_DONE)
                {
                    //Log.d("Debug", "oeal");
                    iniciarActivity(ActivityListaCapitulo.class);
                    return true;
                }
                return false;
            }
        });
    }

    public void iniciarActivity (Class activityClass)
    {
        Intent intent = new Intent(this, activityClass);
        startActivity (intent);
        finish();
    }
}
