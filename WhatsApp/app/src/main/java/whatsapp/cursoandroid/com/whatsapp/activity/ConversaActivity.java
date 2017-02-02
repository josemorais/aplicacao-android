package whatsapp.cursoandroid.com.whatsapp.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import whatsapp.cursoandroid.com.whatsapp.R;
import whatsapp.cursoandroid.com.whatsapp.adapter.MensagemAdapter;
import whatsapp.cursoandroid.com.whatsapp.configuration.ConfiguracaoFirebase;
import whatsapp.cursoandroid.com.whatsapp.helper.Base64Custom;
import whatsapp.cursoandroid.com.whatsapp.helper.Preferencia;
import whatsapp.cursoandroid.com.whatsapp.model.Conversa;
import whatsapp.cursoandroid.com.whatsapp.model.Mensagem;

public class ConversaActivity extends BaseActivity {

    private Toolbar toolbar;
    private EditText textoMensagem;
    private ImageButton botaoMensagem;

    private ListView listView;
    private ArrayAdapter<Mensagem> adapter;
    private ArrayList<Mensagem> mensagens;

    private ValueEventListener valueEventListener;
    private DatabaseReference referenciaMensagem;

    private String idUsuarioLogado;
    private String nomeUsuarioLogado;
    private String idUsuarioDestinatario;
    private String nomeUsuarioDestinatario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversa);

        toolbar = (Toolbar) findViewById(R.id.tb_conversas_id);
        textoMensagem = (EditText) findViewById(R.id.edt_mensagem_id);
        botaoMensagem = (ImageButton) findViewById(R.id.btn_enviar_mensagem_id);
        listView = (ListView) findViewById(R.id.lv_conversas_id);

        //Recuperar as preferencias do usuario logado
        Preferencia preferencia = new Preferencia(this);
        idUsuarioLogado = preferencia.getDadosUsuario().get(Preferencia.CHAVE_IDENTIFICADOR);
        nomeUsuarioLogado = preferencia.getDadosUsuario().get(Preferencia.CHAVE_NOME);

        //Recuperando dados do destinatário
        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            nomeUsuarioDestinatario = extra.getString("nome");
            toolbar.setTitle(nomeUsuarioDestinatario);
            idUsuarioDestinatario = Base64Custom.converteToBase64(extra.getString("email"));
        }

        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left);
        setSupportActionBar(toolbar);

        mensagens = new ArrayList<>();
        adapter = new MensagemAdapter(ConversaActivity.this, mensagens);
        listView.setAdapter(adapter);

        referenciaMensagem = ConfiguracaoFirebase.getDatabase().getReference("mensagens");
        referenciaMensagem = referenciaMensagem.child(idUsuarioLogado).child(idUsuarioDestinatario);
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mensagens.clear();
                for (DataSnapshot dado : dataSnapshot.getChildren()){
                    Mensagem m = dado.getValue(Mensagem.class);
                    mensagens.add(m);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        referenciaMensagem.addValueEventListener(valueEventListener);

        botaoMensagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mensagemDigitada = textoMensagem.getText().toString();
                if (mensagemDigitada.isEmpty()) {
                    Toast.makeText(ConversaActivity.this, "Digite uma mensagem!", Toast.LENGTH_SHORT).show();
                } else {
                    Mensagem mensagem = new Mensagem();
                    mensagem.setIdUsuario(idUsuarioLogado);
                    mensagem.setMensagem(mensagemDigitada);

                    //Salva a mensagem do rementente
                    Boolean retornoRementente = salvarMensagem(idUsuarioLogado, idUsuarioDestinatario, mensagem);
                    if(!retornoRementente){
                        Toast.makeText(ConversaActivity.this, "Problema ao enviar mensagem. Tente novamente!", Toast.LENGTH_SHORT).show();
                    }

                    //Salva a mensagem do destinatário
                    Boolean retornoDestinatario = salvarMensagem(idUsuarioDestinatario, idUsuarioLogado, mensagem);
                    if (!retornoDestinatario){
                        Toast.makeText(ConversaActivity.this, "Problema ao enviar mensagem. Tente novamente!", Toast.LENGTH_SHORT).show();
                    }

                    //Salva a conversa com dos dados do destinatário
                    Conversa conversa = new Conversa();
                    conversa.setIdUsuario(idUsuarioDestinatario);
                    conversa.setNome(nomeUsuarioDestinatario);
                    conversa.setUltimaMensagem(mensagemDigitada);

                    Boolean retornoConversaRemente = salvarConversa(idUsuarioLogado, idUsuarioDestinatario, conversa);
                    if (!retornoConversaRemente){
                        Toast.makeText(ConversaActivity.this, "Problema ao salvar conversa. Tente novamente!", Toast.LENGTH_SHORT).show();
                    }

                    //Salva a conversa com os dados do rementente
                    conversa = new Conversa();
                    conversa.setIdUsuario(idUsuarioLogado);
                    conversa.setNome(nomeUsuarioLogado);
                    conversa.setUltimaMensagem(mensagemDigitada);

                    Boolean retornoConversaDestinatario = salvarConversa(idUsuarioDestinatario, idUsuarioLogado,  conversa);
                    if (!retornoConversaDestinatario){
                        Toast.makeText(ConversaActivity.this, "Problema ao salvar conversa. Tente novamente!", Toast.LENGTH_SHORT).show();
                    }

                    textoMensagem.setText(null);
                }
            }
        });

    }

    private Boolean salvarMensagem(String idRementente, String idDestinatario, Mensagem mensagem) {

        try {
            referenciaMensagem = ConfiguracaoFirebase.getDatabase().getReference();
            referenciaMensagem.child("mensagens")
                    .child(idRementente)
                    .child(idDestinatario)
                    .push()
                    .setValue(mensagem);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private Boolean salvarConversa(String idRementente, String idDestinatario, Conversa conversa) {

        try {
            referenciaMensagem = ConfiguracaoFirebase.getDatabase().getReference();
            referenciaMensagem.child("conversas")
                    .child(idRementente)
                    .child(idDestinatario)
                    .setValue(conversa);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_conversas, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        referenciaMensagem.addValueEventListener(valueEventListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        referenciaMensagem.removeEventListener(valueEventListener);
    }
}
