package whatsapp.cursoandroid.com.whatsapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import whatsapp.cursoandroid.com.whatsapp.R;
import whatsapp.cursoandroid.com.whatsapp.configuration.ConfiguracaoFirebase;
import whatsapp.cursoandroid.com.whatsapp.helper.Base64Custom;
import whatsapp.cursoandroid.com.whatsapp.helper.Preferencia;
import whatsapp.cursoandroid.com.whatsapp.model.Usuario;

public class LoginActivity extends BaseActivity {

    private EditText email;
    private EditText senha;
    private Button botaoLogar;

    private FirebaseAuth auth = ConfiguracaoFirebase.getAuth();
    private DatabaseReference referenciaUsuario;

    private String idenfiticadorUsuarioLogado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.edtLoginEmailId);
        senha = (EditText) findViewById(R.id.edtLoginSenhaId);
        botaoLogar = (Button) findViewById(R.id.btnLogarId);

        if (verificaSeUsuarioEstaLogado()) {
            abrirTelaPrincipal();
        }

        botaoLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Usuario usuario = new Usuario();
                usuario.setEmail(email.getText().toString());
                usuario.setSenha(senha.getText().toString());

                validaLogin(LoginActivity.this, usuario);

            }
        });
    }

    private void validaLogin(final Activity origem, final Usuario usuario) {

        showProgressDialog();
        ConfiguracaoFirebase.getAuth().signInWithEmailAndPassword(usuario.getEmail(), usuario.getSenha())
                .addOnCompleteListener(origem, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            idenfiticadorUsuarioLogado = Base64Custom.converteToBase64(usuario.getEmail());

                            referenciaUsuario = ConfiguracaoFirebase.getDatabase().getReference("usuarios");
                            referenciaUsuario = referenciaUsuario.child(idenfiticadorUsuarioLogado);
                            referenciaUsuario.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    //Recuperar os dados do usuário para salvar nas preferencias
                                    Usuario usuario = dataSnapshot.getValue(Usuario.class);
                                    Preferencia preferencia = new Preferencia(LoginActivity.this);
                                    preferencia.salvarPreferenciasUsuario(usuario.getId(), usuario.getNome());
                                    if (preferencia.getDadosUsuario().get(Preferencia.CHAVE_IDENTIFICADOR) != null){
                                        abrirTelaPrincipal();
                                    } else {
                                        Toast.makeText(origem, "Erro ao salvar preferências do usuário.", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                        } else {
                            Log.i("USUARIO:", "PROBLEMA AO FAZER LOGIN COM O USUARIO" + task.getException().getMessage());
                            Toast.makeText(origem, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                        hideProgressDialog();
                    }
                });

    }

    private boolean verificaSeUsuarioEstaLogado() {

        if (this.auth.getCurrentUser() != null) {
            return true;
        }
        return false;
    }


    private void abrirTelaPrincipal() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void abrirCadastroUsuario(View view) {
        Intent intent = new Intent(this, CadastroUsuarioActivity.class);
        startActivity(intent);
    }

}
