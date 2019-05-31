package br.alunos.appescrita.livro;

import java.io.Serializable;
import java.util.ArrayList;

public class Livro implements Serializable
{
    private String titulo;
    private ArrayList<Capitulo> capitulos;

    public String getTitulo()
    {
        return titulo;
    }

    public void setTitulo(String titulo)
    {
        this.titulo = titulo;
    }

    public Capitulo getCapitulo (int capitulo) throws ArrayIndexOutOfBoundsException
    {
        return capitulos.get(capitulo);
    }

    public Livro(String titulo)
    {
        this.titulo = titulo;
        this.capitulos = new ArrayList<Capitulo>();
    }

    public int adicionarCapitulo (String titulo)
    {
        capitulos.add(new Capitulo (titulo));
        return capitulos.size() - 1;
    }

    public void removerCapitulo (int capitulo) throws ArrayIndexOutOfBoundsException
    {
        capitulos.remove(capitulo);
    }

    public ArrayList<Capitulo> getCapitulos()
    {
        return capitulos;
    }

    @Override
    public String toString ()
    {
        return this.titulo;
    }
}
