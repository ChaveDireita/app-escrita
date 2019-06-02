package br.alunos.appescrita.activities;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

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

public class AdicionarCapitulo extends AppCompatActivity implements AcessaArquivos
{
    private String livroTitulo,
            usuario,
            caminhoArquivo;
    private Livro livro;
    private ArrayList<Capitulo> capitulos;
    private Capitulo capitulo;
    private int numeroCapitulo;
    private EditText editTextCorpoCapitulo,
                     editTextNumeroCapitulo,
                     editTextTituloCapitulo;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_capitulo);

        livroTitulo = getIntent().getStringExtra("livro");
        usuario = getIntent().getStringExtra("usuario");
        caminhoArquivo = usuario + "-" + livroTitulo;

        getSupportActionBar().setTitle(livroTitulo);

        try
        {
            livro = (Livro) abrirArquivo(caminhoArquivo);
        } catch (IOException | ClassNotFoundException e) {}

        capitulos = livro.getCapitulos();
        numeroCapitulo = capitulos.size();

        editTextTituloCapitulo = findViewById(R.id.edit_text_capitulo_titulo);
        editTextNumeroCapitulo = findViewById(R.id.edit_text_capitulo_numero);
        editTextCorpoCapitulo = findViewById(R.id.edit_text_corpo_capitulo);

        editTextNumeroCapitulo.setText("" + (numeroCapitulo + 1));

    }

    @Override
    public void onBackPressed()
    {
        capitulo = new Capitulo(editTextTituloCapitulo.getText().toString());
        capitulo.setTexto(editTextCorpoCapitulo.getText().toString());
        String s = capitulo.getTitulo();
        Log.d( "onBackPressed: ", s);
        if (capitulo.getTitulo() != null && !capitulo.getTitulo().isEmpty()) {
            try {
                numeroCapitulo = Integer.parseInt(editTextNumeroCapitulo.getText().toString());
            } catch (NumberFormatException e) {
                numeroCapitulo = capitulos.size();
            }

            numeroCapitulo--;
            capitulos.add(Math.max(Math.min(numeroCapitulo, capitulos.size() - 1), 0), capitulo);

            try {
                gravarArquivo(caminhoArquivo, livro);
            } catch (IOException e) {
            }
        }
        super.onBackPressed();
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
