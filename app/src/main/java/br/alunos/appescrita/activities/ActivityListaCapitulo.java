package br.alunos.appescrita.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import br.alunos.appescrita.dialogfragments.DialogFragmentDeletarCapitulo;
import br.alunos.appescrita.dialogfragments.DialogFragmentDeletarLivro;
import br.alunos.appescrita.livro.Capitulo;
import br.alunos.appescrita.livro.Livro;
import br.alunos.appescrita.R;
import br.alunos.appescrita.util.AcessaArquivos;
import br.alunos.appescrita.util.ConstantesComuns;


public class ActivityListaCapitulo extends AppCompatActivity implements AcessaArquivos
{
    private String livroTitulo,
                   usuario,
                   caminhoArquivo;
    private Livro livro;
    private ArrayList<Capitulo> capitulos;
    private ArrayAdapter<Capitulo> capitulosArrayAdapter;
    private ListView listViewCapitulos;
    int capituloDeletar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_capitulo);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        livroTitulo = getIntent().getStringExtra("livro");
        usuario = getIntent().getStringExtra("usuario");
        caminhoArquivo = usuario + "-" + livroTitulo;
        try
        {
            livro = (Livro) abrirArquivo(caminhoArquivo);
        } catch (IOException | ClassNotFoundException e)
        {
            livro = new Livro(livroTitulo);
        }
        if (livro == null)
            livro = new Livro(livroTitulo);

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
                intent.putExtra("usuario", usuario);
                intent.putExtra("itemSelecionado", position);
                startActivity(intent);
            }
        });

        listViewCapitulos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
            {
                capituloDeletar = position;
                DialogFragmentDeletarCapitulo dialogFragmentDeletarCapitulo = new DialogFragmentDeletarCapitulo();
                dialogFragmentDeletarCapitulo.show(getSupportFragmentManager(), null);
                return true;
            }
        });

        getSupportActionBar().setTitle(livro.getTitulo());

        FloatingActionButton fab = findViewById(R.id.fab_adicionar_capitulo);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                Intent intent = new Intent(ActivityListaCapitulo.this, AdicionarCapitulo.class);
                intent.putExtra("livro", livroTitulo);
                intent.putExtra("usuario", usuario);
                startActivityForResult(intent, ConstantesComuns.REQUEST_OPCAO_SELECIONADA);
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
            gravarArquivo(caminhoArquivo, livro);
        } catch (IOException e) {}
        super.onBackPressed();
    }

    @Override
    protected void onDestroy()
    {
        try
        {
            gravarArquivo(caminhoArquivo, livro);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        try
        {
            livro = (Livro) abrirArquivo(caminhoArquivo);
        } catch (IOException | ClassNotFoundException e)
        {
            livro = new Livro(livroTitulo);
        }

        capitulos = livro.getCapitulos();
        capitulosArrayAdapter = capitulosArrayAdapter = new ArrayAdapter<Capitulo>(this, R.layout.layout_lista, capitulos);
        listViewCapitulos.setAdapter(capitulosArrayAdapter);
        capitulosArrayAdapter.notifyDataSetChanged();
    }

    public void deletarCapitulo ()
    {
        capitulos.remove(capituloDeletar);
        try
        {
            gravarArquivo(caminhoArquivo, livro);
        } catch (IOException e) {}
        capitulosArrayAdapter.notifyDataSetChanged();
    }

}
