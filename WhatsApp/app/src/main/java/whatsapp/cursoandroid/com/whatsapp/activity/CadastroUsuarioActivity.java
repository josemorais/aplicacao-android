package whatsapp.cursoandroid.com.whatsapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.google.firebase.auth.FirebaseAuth;

import whatsapp.cursoandroid.com.whatsapp.R;
import whatsapp.cursoandroid.com.whatsapp.dao.UsuarioDAO;
import whatsapp.cursoandroid.com.whatsapp.model.Usuario;

public class CadastroUsuarioActivity extends BaseActivity {

    private FirebaseAuth firebaseAuth;

    private EditText nome;
    private EditText email;
    private EditText senha;
    private Button botaoCadastrar;
    private Usuario usuario;
    private UsuarioDAO usuarioDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        nome = (EditText) findViewById(R.id.edtCadastroNomeId);
        email = (EditText) findViewById(R.id.edtCadastroEmailId);
        senha = (EditText) findViewById(R.id.edt1cadastroSenhaId);
        botaoCadastrar = (Button) findViewById(R.id.btnCadastrarId);

        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                usuario = new Usuario();
                usuario.setNome(nome.getText().toString());
                usuario.setEmail(email.getText().toString());
                usuario.setSenha(senha.getText().toString());

                usuarioDAO = new UsuarioDAO();
                usuarioDAO.salvarUsuarioNaAutenticacao(CadastroUsuarioActivity.this, usuario);

            }
        });

    }

}
