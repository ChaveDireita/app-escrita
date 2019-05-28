package br.alunos.appescrita.livro;

public class Capitulo
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
}
