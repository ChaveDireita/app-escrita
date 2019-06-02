package br.alunos.appescrita.dialogfragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import br.alunos.appescrita.R;
import br.alunos.appescrita.activities.ActivityListaLivro;


public class DialogFragmentCriarLivro extends DialogFragment
{
    private View view;
    private ActivityListaLivro listaLivro;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        TextView title =  new TextView(listaLivro);
        title.setText("Due Alert");
        title.setGravity(Gravity.CENTER);
        title.setTextSize(20);
        title.setBackgroundColor(Color.GRAY);
        title.setTextColor(Color.WHITE);

        builder.setView((view = inflater.inflate(R.layout.fragment_dialog_criar_livro, null)))
        .setTitle(R.string.dialog_criar_livro_titulo)
        .setPositiveButton(R.string.dialog_botao_criar, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
                String titulo = ((EditText) view.findViewById(R.id.edit_text_criar_livro)).getText().toString();
                listaLivro.criarLivro(titulo);
            }
        })
        .setNegativeButton(R.string.dialog_botao_cancelar, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                DialogFragmentCriarLivro.this.getDialog().cancel();
            }
        });

        return builder.create();
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.listaLivro = (ActivityListaLivro) context;
    }
}
