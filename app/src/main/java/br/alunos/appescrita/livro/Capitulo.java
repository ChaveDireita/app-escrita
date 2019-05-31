package br.alunos.appescrita.livro;

import java.io.Serializable;

public class Capitulo implements Serializable
{
    private String titulo;
    private String texto;

    public String getTitulo()
    {
        return titulo;
    }

    public void setTitulo(String titulo)
    {
        this.titulo = titulo;
    }

    public String getTexto()
    {
        return texto;
    }

    public void setTexto(String texto)
    {
        this.texto = texto;
    }

    public Capitulo(String titulo)
    {
        this.titulo = titulo;
    }

    @Override
    public String toString ()
    {
        return this.titulo;
    }
}
