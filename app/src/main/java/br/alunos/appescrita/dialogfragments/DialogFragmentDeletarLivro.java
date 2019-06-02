package br.alunos.appescrita.dialogfragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import br.alunos.appescrita.R;
import br.alunos.appescrita.activities.ActivityListaLivro;

public class DialogFragmentDeletarLivro extends DialogFragment
{

    private ActivityListaLivro listaLivro;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.dialog_deletar_livro)
        .setPositiveButton(R.string.context_menu_deletar, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

                listaLivro.deletarLivro();
            }
        })
        .setNegativeButton(R.string.dialog_botao_excluir, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                DialogFragmentDeletarLivro.this.getDialog().cancel();
            }
        });
        return builder.create();
    }

    @Override
    public void onAttach(Context context)
    {
        listaLivro = (ActivityListaLivro) context;
        super.onAttach(context);
    }
}
