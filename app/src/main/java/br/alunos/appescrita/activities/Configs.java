package br.alunos.appescrita.activities;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import br.alunos.appescrita.R;

public class Configs extends AppCompatActivity {
    TextView userid;
    ImageView editbutton;
    Spinner themeset,fontset;

    private boolean showMsgTheme = false,
                    showMsgFont = false;

    private String usuario;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_config);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        usuario = getIntent().getStringExtra("usuario");


        editbutton = (ImageView) findViewById(R.id.userpic);

        themeset = findViewById(R.id.theme_set);
        ArrayAdapter<CharSequence> themes = ArrayAdapter.createFromResource(this,R.array.themes,android.R.layout.simple_spinner_item);
        themes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        themeset.setAdapter(themes);
        themeset.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!showMsgTheme)
                {
                    showMsgTheme = true;
                    return;
                }
                String themealert = getString(R.string.infoabouttheme);
                Toast.makeText(Configs.this,themealert, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fontset = findViewById(R.id.font_set);
        ArrayAdapter<CharSequence> fonts = ArrayAdapter.createFromResource(this,R.array.fonts,android.R.layout.simple_spinner_item);
        fonts.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fontset.setAdapter(fonts);
        fontset.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!showMsgFont)
                {
                    showMsgFont = true;
                    return;
                }
                String fontalert = getString(R.string.infoaboutfont);
                Toast.makeText(Configs.this,fontalert, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        userid = findViewById(R.id.userid);
        userid.setText(usuario);

        FloatingActionButton fab = findViewById(R.id.fab_editar_usuario);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Configs.this, UserConfig.class);
                intent.putExtra ("usuario", usuario);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.icone_app_bar,menu);
        return true;
    }
}


