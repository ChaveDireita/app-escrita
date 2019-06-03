package br.alunos.appescrita.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import br.alunos.appescrita.R;
import br.alunos.appescrita.livro.Capitulo;
import br.alunos.appescrita.livro.Livro;
import br.alunos.appescrita.util.AcessaArquivos;

public class ActivityEditarCapitulo extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, AcessaArquivos
{
    private String livroTitulo,
                   usuario,
                   caminhoArquivo;
    private Livro livro;
    private ArrayList<Capitulo> capitulos;
    private Capitulo capitulo;
    private int itemSelecionado;
    private DrawerLayout drawer;
    private EditText editTextCorpoCapitulo;
    private TextView textViewLivroTitulo;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_capitulo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        Menu menu = navigationView.getMenu();

        editTextCorpoCapitulo = findViewById(R.id.edit_text_corpo_capitulo);

        livroTitulo = getIntent().getStringExtra("livro");
        usuario = getIntent().getStringExtra("usuario");
        caminhoArquivo = usuario + "-" + livroTitulo;

        try
        {
            livro = (Livro) abrirArquivo(caminhoArquivo);
        } catch (IOException | ClassNotFoundException e) {}



        textViewLivroTitulo = navigationView.getHeaderView(0).findViewById(R.id.drawer_titulo_livro);
        textViewLivroTitulo.setText(livro.getTitulo());

        itemSelecionado = getIntent().getIntExtra("itemSelecionado", 0);

        capitulos = livro.getCapitulos();
        capitulo = capitulos.get(itemSelecionado);

        editTextCorpoCapitulo.setText(capitulo.getTexto());

        for (Capitulo c : capitulos)
        {
            int indice = capitulos.indexOf(c);
            menu.add(R.id.editar_capitulo_lista_drawer, indice, indice, c.toString());
        }

        getSupportActionBar().setTitle(capitulo.getTitulo());

        ((TextView) navigationView.getHeaderView(0).findViewById(R.id.drawer_titulo_livro)).setText(livro.getTitulo());
    }

    @Override
    public void onBackPressed()
    {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else
        {
            try
            {
                gravarArquivo(caminhoArquivo, livro);
            } catch (IOException e) {}
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.icone_app_bar, menu);
        return true;
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
    public boolean onNavigationItemSelected(MenuItem item)
    {
        int itemSelecionado = item.getOrder();

        capitulo.setTexto(editTextCorpoCapitulo.getText().toString());
        capitulo = capitulos.get(itemSelecionado);
        editTextCorpoCapitulo.setText(capitulo.getTexto());
        getSupportActionBar().setTitle(capitulo.getTitulo());

        drawer.closeDrawer(GravityCompat.START);
        return true;
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
