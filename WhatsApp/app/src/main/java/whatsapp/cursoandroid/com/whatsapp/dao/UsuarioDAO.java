package whatsapp.cursoandroid.com.whatsapp.dao;


import android.app.Activity;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import whatsapp.cursoandroid.com.whatsapp.configuration.ConfiguracaoFirebase;
import whatsapp.cursoandroid.com.whatsapp.helper.Base64Custom;
import whatsapp.cursoandroid.com.whatsapp.model.Usuario;

public class UsuarioDAO {

    private DatabaseReference referencia = ConfiguracaoFirebase.getDatabase().getReference();
    private FirebaseAuth firebaseAuth = ConfiguracaoFirebase.getAuth();


    public void salvar(Activity activity, Usuario usuario) {
        String identificador = Base64Custom.converteToBase64(usuario.getEmail().trim());
        usuario.setId(identificador);
        referencia = referencia.child("usuarios").child(usuario.getId());
        referencia.setValue(usuario);
    }

    public void salvarUsuarioNaAutenticacao(final Activity activity, final Usuario usuario) {

        Log.i("salvarUsuarioNaAutenticacao", "salvarUsuarioNaAutenticacao");
        try {

            firebaseAuth.createUserWithEmailAndPassword(usuario.getEmail(), usuario.getSenha())
                    .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.i("USUARIO:", "USUARIO SALVO COM SUCESSO" + task.getResult().getUser().getUid());
                                Toast.makeText(activity, "Usuário cadastrado com sucesso.", Toast.LENGTH_SHORT).show();
                                salvar(activity, usuario);
                                activity.finish();
                            } else {
                                Log.i("USUARIO:", "PROBLEMA AO SALVAR USUARIO" + task.getException());
                                Toast.makeText(activity, "Problema ao cadastrar usuário. " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logout() {
        this.firebaseAuth.signOut();
    }
}
