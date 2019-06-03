package br.alunos.appescrita.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import br.alunos.appescrita.R;

public class UserConfig extends AppCompatActivity implements View.OnClickListener
{

    private String usuario;
    private ImageView userpic;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_config);

        usuario = getIntent().getStringExtra("usuario");
        ((EditText) findViewById(R.id.user_id_edit)).setText(usuario);

        userpic = findViewById(R.id.usereditpic);
        userpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UserConfig.this,"Não é possivel trocar de imagens nessa versão do aplicativo",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.icone_app_bar,menu);
        return true;
    }

    @Override
    public void onClick(View v) {

    }
}

