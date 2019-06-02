package br.alunos.appescrita.util;

import java.io.IOException;

public interface AcessaArquivos
{
    Object abrirArquivo (String arquivo) throws IOException, ClassNotFoundException;
    <T> void gravarArquivo (String arquivo, T objeto) throws IOException;
    void deletarArquivo (String arquivo) throws IOException;
}
