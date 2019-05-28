package br.alunos.appescrita.livro;

import java.util.ArrayList;

public class Livro
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
}
