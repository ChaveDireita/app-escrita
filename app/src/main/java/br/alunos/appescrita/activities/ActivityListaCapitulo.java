package br.alunos.appescrita.activities;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import br.alunos.appescrita.livro.Capitulo;
import br.alunos.appescrita.livro.Livro;
import br.alunos.appescrita.R;


public class ActivityListaCapitulo extends AppCompatActivity
{
    private Livro livro;
    private ArrayAdapter<Capitulo> capitulosArrayAdapter;
    private ListView listViewCapitulos;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_capitulo);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);


        //livro = (Livro) getIntent().getSerializableExtra("livro");
        //toolbar.setTitle(livro.getTitulo());

        //capitulosArrayAdapter = new ArrayAdapter<Capitulo>();

        FloatingActionButton fab = findViewById(R.id.fab_adicionar_capitulo);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Snackbar.make(view, "Não faz nada por equanto", Snackbar.LENGTH_LONG).show();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.icone_app_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
