package br.alunos.appescrita.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import br.alunos.appescrita.livro.Capitulo;
import br.alunos.appescrita.livro.Livro;
import br.alunos.appescrita.R;
import br.alunos.appescrita.util.AcessaArquivos;


public class ActivityListaCapitulo extends AppCompatActivity implements AcessaArquivos
{
    private String livroTitulo;
    private Livro livro;
    private ArrayList<Capitulo> capitulos;
    private ArrayAdapter<Capitulo> capitulosArrayAdapter;
    private ListView listViewCapitulos;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_capitulo);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        livroTitulo = getIntent().getStringExtra("livro");

        try
        {
            livro = (Livro) abrirArquivo(livroTitulo);
        } catch (IOException | ClassNotFoundException e)
        {
            livro = new Livro(livroTitulo);
        }

        capitulos = livro.getCapitulos();
        capitulosArrayAdapter = new ArrayAdapter<Capitulo>(this, R.layout.layout_lista, capitulos);

        listViewCapitulos = findViewById(R.id.list_view_capitulos);
        listViewCapitulos.setAdapter(capitulosArrayAdapter);
        listViewCapitulos.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = new Intent(ActivityListaCapitulo.this, ActivityEditarCapitulo.class);
                intent.putExtra("livro", livroTitulo);
                intent.putExtra("itemSelecionado", position);
                startActivity(intent);
            }
        });

        toolbar.setTitle(livro.getTitulo());

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

    @Override
    public void onBackPressed()
    {
        try
        {
            gravarArquivo(livroTitulo, livro);
        } catch (IOException e) {}
        super.onBackPressed();
    }

    @Override
    protected void onDestroy()
    {
        try
        {
            gravarArquivo(livroTitulo, livro);
        } catch (IOException e) {}
        super.onDestroy();
    }

    @Override
    public Object abrirArquivo(String arquivo) throws IOException, ClassNotFoundException
    {
        FileInputStream entradaArquivo = openFileInput(arquivo);
        ObjectInputStream entradaObjeto = new ObjectInputStream(entradaArquivo);

        Object o = entradaObjeto.readObject();

        entradaObjeto.close();
        entradaArquivo.close();
        return o;
    }

    @Override
    public <T> void gravarArquivo(String arquivo, T objeto) throws IOException
    {
        FileOutputStream saidaArquivo = openFileOutput(arquivo, Context.MODE_PRIVATE);
        ObjectOutputStream saidaObjeto = new ObjectOutputStream(saidaArquivo);

        saidaObjeto.writeObject(objeto);

        saidaObjeto.close();
        saidaArquivo.close();
    }

    @Override
    public void deletarArquivo(String arquivo) throws IOException
    {
        deleteFile(arquivo);
    }
}