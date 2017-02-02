package whatsapp.cursoandroid.com.whatsapp.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import whatsapp.cursoandroid.com.whatsapp.R;
import whatsapp.cursoandroid.com.whatsapp.adapter.TabAdapter;
import whatsapp.cursoandroid.com.whatsapp.configuration.ConfiguracaoFirebase;
import whatsapp.cursoandroid.com.whatsapp.dao.UsuarioDAO;
import whatsapp.cursoandroid.com.whatsapp.helper.Base64Custom;
import whatsapp.cursoandroid.com.whatsapp.helper.Preferencia;
import whatsapp.cursoandroid.com.whatsapp.helper.SlidingTabLayout;
import whatsapp.cursoandroid.com.whatsapp.model.Contato;
import whatsapp.cursoandroid.com.whatsapp.model.Usuario;


public class MainActivity extends BaseActivity {

    private Toolbar toolbar;
    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;

    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);

        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.stl_tabs);
        viewPager = (ViewPager) findViewById(R.id.vp_pagina);

        //Configura o sliding tabs
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setSelectedIndicatorColors(R.color.colorAccente);

        //Configura o adapter
        TabAdapter tabAdapter = new TabAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabAdapter);

        slidingTabLayout.setViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater(); // Exibe os menus na tela
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.item_sair:
                UsuarioDAO dao = new UsuarioDAO();
                dao.logout();
                startActivity(new Intent(this, LoginActivity.class));
                return true;
            case R.id.item_adicionar:
                abrirCadastroContato();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void abrirCadastroContato() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Novo Contato");
        dialog.setMessage("E-mail do usuário");
        dialog.setCancelable(false);

        final EditText editText = new EditText(this);
        dialog.setView(editText);

        dialog.setPositiveButton("Cadastrar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                final String emailDigitado = editText.getText().toString();

                if (emailDigitado.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Preencha o e-mail!", Toast.LENGTH_SHORT).show();
                } else {

                    String identificadorContato = Base64Custom.converteToBase64(emailDigitado);

                    database = ConfiguracaoFirebase.getDatabase();

                    database.getReference("usuarios")
                            .child(identificadorContato)
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    Log.i("dataSnapshot:", String.valueOf(dataSnapshot.getValue()));

                                    if (dataSnapshot.getValue() != null) {

                                        String identificadorContato = Base64Custom.converteToBase64(emailDigitado);

                                        //Recuperando o identificador do usuário logado
                                        Preferencia preferencia = new Preferencia(MainActivity.this);
                                        String identificadorUsuarioLogado = preferencia.getDadosUsuario().get(Preferencia.CHAVE_IDENTIFICADOR);

                                        Log.i("identificadorUsuario:", identificadorUsuarioLogado);

                                        //Recuperar os dados do contato a ser adicionado
                                        Usuario usuarioContato = dataSnapshot.getValue(Usuario.class);

                                        Contato contato = new Contato();
                                        contato.setIdenfificador(identificadorContato);
                                        contato.setNome(usuarioContato.getNome());
                                        contato.setEmail(usuarioContato.getEmail());

                                        database = ConfiguracaoFirebase.getDatabase();
                                        database.getReference("contatos")
                                                .child(identificadorUsuarioLogado)
                                                .child(identificadorContato)
                                                .setValue(contato);

                                    } else {
                                        Toast.makeText(MainActivity.this, "Usuário não cadastrado!", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                }

            }
        });
        dialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog.create();
        dialog.show();
    }
}
