package br.alunos.appescrita.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.ContextMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import br.alunos.appescrita.R;
import br.alunos.appescrita.dialogfragments.DialogFragmentCriarLivro;
import br.alunos.appescrita.dialogfragments.DialogFragmentDeletarLivro;
import br.alunos.appescrita.util.AcessaArquivos;

@SuppressWarnings("unchecked")
public class ActivityListaLivro extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
                   AcessaArquivos

{

    private ArrayList<String> biblioteca;
    private ArrayAdapter<String> bibliotecaArrayAdapter;
    private DrawerLayout drawer;
    private String usuario;
    private String livroDeletar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_livro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fabCriarLivro = (FloatingActionButton) findViewById(R.id.fab_adicionar_livro);
        fabCriarLivro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                DialogFragmentCriarLivro dialogFragmentCriarLivro = new DialogFragmentCriarLivro();
                dialogFragmentCriarLivro.show(getSupportFragmentManager(), null);
            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        usuario = getIntent().getStringExtra("usuario");

        TextView textViewDrawerUsuario = navigationView.getHeaderView(0).findViewById(R.id.drawer_nome_usuario);
        textViewDrawerUsuario.setText(usuario);

        try
        {
            biblioteca = (ArrayList<String>) abrirArquivo(usuario);
        } catch (IOException | ClassNotFoundException e)
        {
            biblioteca = new ArrayList<String>();
        }

        ListView listViewLivros = findViewById(R.id.list_view_livros);
        bibliotecaArrayAdapter = new ArrayAdapter<String>(this, R.layout.layout_lista, biblioteca);
        listViewLivros.setAdapter(bibliotecaArrayAdapter);
        listViewLivros.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int posicao, long id)
            {
                try
                {
                    gravarArquivo(usuario, biblioteca);
                } catch (IOException e) {}
                Intent intent = new Intent(ActivityListaLivro.this, ActivityListaCapitulo.class);
                intent.putExtra("livro", biblioteca.get(posicao));
                intent.putExtra("usuario", usuario);
                startActivity(intent);
            }
        });
        listViewLivros.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
            {
                livroDeletar = biblioteca.get(position);
                DialogFragmentDeletarLivro dialogFragmentDeletarLivro = new DialogFragmentDeletarLivro();
                dialogFragmentDeletarLivro.show(getSupportFragmentManager(), null);
                return true;
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            try
            {
                gravarArquivo(usuario, biblioteca);
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
    public boolean onOptionsItemSelected(MenuItem item)
    {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        Intent intent;

        switch (id)
        {
            case R.id.drawer_opcao_configuracoes:
                intent = new Intent(this, Configs.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
                break;
            case R.id.drawer_opcao_avalie:
                break;
            case R.id.drawer_opcao_sair:
                intent = new Intent(this, ActivityLogin.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
                finish();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStop()
    {
        try
        {
            gravarArquivo(usuario, biblioteca);
        } catch (IOException e) {}
        super.onBackPressed();
        super.onStop();
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

    public void criarLivro (String titulo)
    {
        biblioteca.add(titulo);
        bibliotecaArrayAdapter.notifyDataSetChanged();
    }

    @Override
    public void deletarArquivo(String arquivo)
    {
        deleteFile(arquivo);
    }

    public void deletarLivro ()
    {
        deletarArquivo(usuario + "-" + livroDeletar);
        biblioteca.remove(livroDeletar);
        bibliotecaArrayAdapter.notifyDataSetChanged();
    }

}
