package br.alunos.appescrita.activities;

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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import br.alunos.appescrita.R;
import br.alunos.appescrita.livro.Capitulo;
import br.alunos.appescrita.livro.Livro;

public class ActivityEditarCapitulo extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{

    private Livro livro;
    private ArrayList<Capitulo> capitulos;
    private Capitulo capitulo;
    private ArrayAdapter<Capitulo> capitulosArrayAdapter;
    private int itemSelecionado;
    private DrawerLayout drawer;
    private EditText editTextCorpoCapitulo;
    private View viewLivroTitulo;
    private TextView textViewLivroTitulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        textViewLivroTitulo = navigationView.getHeaderView(0).findViewById(R.id.text_view_titulo_livro_drawer);
        textViewLivroTitulo.setText("Titulo do Livro");

        Menu menu = navigationView.getMenu();

        editTextCorpoCapitulo = findViewById(R.id.edit_text_corpo_capitulo);

        livro = (Livro) getIntent().getSerializableExtra("livro");
        itemSelecionado = getIntent().getIntExtra("itemSelecionado", 0);
        capitulos = livro.getCapitulos();


        capitulo = capitulos.get(itemSelecionado);

        capitulosArrayAdapter = new ArrayAdapter<Capitulo>(this, R.layout.layout_lista, capitulos);

        editTextCorpoCapitulo.setText(capitulo.getTexto());

        for (Capitulo c : capitulos)
        {
            int indice = capitulos.indexOf(c);
            menu.add(R.id.editar_capitulo_lista_drawer, indice, indice, c.toString());
        }
    }

    @Override
    public void onBackPressed()
    {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else
        {
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
    public boolean onNavigationItemSelected(MenuItem item)
    {
        int itemSelecionado = item.getOrder();

        capitulo.setTexto(editTextCorpoCapitulo.getText().toString());
        capitulo = capitulos.get(itemSelecionado);
        editTextCorpoCapitulo.setText(capitulo.getTexto());

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
