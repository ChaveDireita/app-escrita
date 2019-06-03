package br.alunos.appescrita.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import br.alunos.appescrita.R;
import br.alunos.appescrita.util.ConstantesComuns;

public class ActivityLogin extends AppCompatActivity
{
    private EditText editTextLogin;
    private EditText editTextSenha;
    private TextView textViewCadastrar;
    private TextView textViewEsqueciSenha;
    private TextView textViewLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

        editTextLogin = findViewById(R.id.campo_usuario);
        editTextSenha = findViewById(R.id.campo_senha);
        try
        {
            String usuario = getIntent().getStringExtra("usuario");
            editTextLogin.setText(usuario);
        } catch (NullPointerException e) {}

        textViewCadastrar = findViewById(R.id.texto_cadastro);
        textViewEsqueciSenha = findViewById(R.id.texto_esqueci);
        textViewLogin = findViewById(R.id.texto_login);

        editTextSenha.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
            {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String usuario = editTextLogin.getText().toString();
                    String senha = editTextSenha.getText().toString();

                    logar(testeLogin(usuario, senha), v, usuario);
                }
                return false;
            }
        });

        textViewLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String usuario = editTextLogin.getText().toString();
                String senha = editTextSenha.getText().toString();

                logar(testeLogin(usuario, senha), v, usuario);
            }
        });

        textViewCadastrar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String usuario = editTextLogin.getText().toString();
                String senha = editTextSenha.getText().toString();
                switch (cadastrar(usuario, senha))
                {
                    case ConstantesComuns.LOGIN_SEM_USUARIO:
                        Snackbar.make(v, R.string.snackbar_insira_usuario, Snackbar.LENGTH_SHORT)
                                .show();
                        break;
                    case ConstantesComuns.LOGIN_SEM_SENHA:
                        Snackbar.make(v, R.string.snackbar_insira_senha, Snackbar.LENGTH_SHORT)
                                .show();
                        break;
                    case ConstantesComuns.LOGIN_USUARIO_EXISTENTE:
                        Snackbar.make(v, R.string.snackbar_usuario_cadastrado, Snackbar.LENGTH_SHORT)
                                .show();
                        break;
                    case ConstantesComuns.LOGIN_BEM_SUCEDIDO:
                        Snackbar.make(v, R.string.snackbar_bem_sucedido, Snackbar.LENGTH_SHORT)
                                .show();
                        break;
                }
            }
        });

        textViewEsqueciSenha.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String usuario = editTextLogin.getText().toString();
                String senha;
                SharedPreferences preferencias = getSharedPreferences("login", MODE_PRIVATE);

                if ((senha = preferencias.getString(usuario, null)) == null)
                    Snackbar.make(v, R.string.snackbar_usuario_inexistente, Snackbar.LENGTH_SHORT)
                            .show();
                else
                    Snackbar.make(v, getString(R.string.senha) + ": " + senha, Snackbar.LENGTH_SHORT)
                            .show();
            }
        });
    }

    public void logar (int operacao, View v, String usuario)
    {
        switch (operacao)
        {
            case ConstantesComuns.LOGIN_BEM_SUCEDIDO:
                Intent intent = new Intent(ActivityLogin.this, ActivityListaLivro.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
                finish();
                break;
            case ConstantesComuns.LOGIN_SEM_USUARIO:
                Snackbar.make(v, R.string.snackbar_insira_usuario, Snackbar.LENGTH_SHORT)
                        .show();
                break;
            case ConstantesComuns.LOGIN_SEM_SENHA:
                Snackbar.make(v, R.string.snackbar_insira_senha, Snackbar.LENGTH_SHORT)
                        .show();
                break;
            case ConstantesComuns.LOGIN_USUARIO_INEXISTENTE:
                Snackbar.make(v, R.string.snackbar_usuario_inexistente, Snackbar.LENGTH_SHORT)
                        .show();
                break;
            case ConstantesComuns.LOGIN_SENHA_ERRADA:
                Snackbar.make(v, R.string.snackbar_senha_incorreta, Snackbar.LENGTH_SHORT)
                        .show();
                break;
        }
    }

    public int testeLogin(String usuario, String senha)
    {
        SharedPreferences preferencias = getSharedPreferences("login", MODE_PRIVATE);

        String senhaVerdadeira = preferencias.getString(usuario, null);

        if (usuario.isEmpty())
            return ConstantesComuns.LOGIN_SEM_USUARIO;
        else if (senha.isEmpty())
            return ConstantesComuns.LOGIN_SEM_SENHA;
        else if (senhaVerdadeira == null)
            return ConstantesComuns.LOGIN_USUARIO_INEXISTENTE;
        else if (!senha.equals(senhaVerdadeira))
            return ConstantesComuns.LOGIN_SENHA_ERRADA;
        return ConstantesComuns.LOGIN_BEM_SUCEDIDO;
    }

    public int cadastrar (String usuario, String senha)
    {
        SharedPreferences preferencias = getSharedPreferences("login", MODE_PRIVATE);
        String senhaExistente = preferencias.getString(usuario, null);

        if (usuario.isEmpty())
            return ConstantesComuns.LOGIN_SEM_USUARIO;
        else if (senha.isEmpty())
            return ConstantesComuns.LOGIN_SEM_SENHA;
        else if (senhaExistente != null)
            return ConstantesComuns.LOGIN_USUARIO_EXISTENTE;

        SharedPreferences.Editor editorPreferencias = preferencias.edit();
        editorPreferencias.putString(usuario, senha);
        editorPreferencias.commit();

        return ConstantesComuns.LOGIN_BEM_SUCEDIDO;
    }
}
