package whatsapp.cursoandroid.com.whatsapp.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import whatsapp.cursoandroid.com.whatsapp.R;
import whatsapp.cursoandroid.com.whatsapp.activity.ConversaActivity;
import whatsapp.cursoandroid.com.whatsapp.adapter.ConversaAdapter;
import whatsapp.cursoandroid.com.whatsapp.configuration.ConfiguracaoFirebase;
import whatsapp.cursoandroid.com.whatsapp.helper.Base64Custom;
import whatsapp.cursoandroid.com.whatsapp.helper.Preferencia;
import whatsapp.cursoandroid.com.whatsapp.model.Conversa;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConversasFragment extends Fragment {

    private ListView listView;
    private ArrayAdapter<Conversa> adapter;
    private ArrayList<Conversa> conversas;

    private DatabaseReference referenciaConversa;
    private ValueEventListener valueEventListenerConversas;


    public ConversasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_conversas, container, false);

        listView = (ListView) view.findViewById(R.id.lv_conversas_frag_id);
        conversas = new ArrayList<>();
        adapter = new ConversaAdapter(getActivity(), conversas);
        listView.setAdapter(adapter);

        //Recuperar as preferencias do usuario logado
        Preferencia preferencia = new Preferencia(getActivity());
        String idUsuarioLogado = preferencia.getDadosUsuario().get(Preferencia.CHAVE_IDENTIFICADOR);

        referenciaConversa = ConfiguracaoFirebase.getDatabase().getReference("conversas");
        referenciaConversa = referenciaConversa.child(idUsuarioLogado);
        valueEventListenerConversas = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                conversas.clear();
                for(DataSnapshot dado : dataSnapshot.getChildren()){
                    Conversa c = dado.getValue(Conversa.class);
                    conversas.add(c);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        referenciaConversa.addValueEventListener(valueEventListenerConversas);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), ConversaActivity.class);

                Conversa conversa = conversas.get(i);
                String email = Base64Custom.decodificarBase64(conversa.getIdUsuario());
                intent.putExtra("nome", conversa.getNome());
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        referenciaConversa.addValueEventListener(valueEventListenerConversas);
    }

    @Override
    public void onStop() {
        super.onStop();
        referenciaConversa.removeEventListener(valueEventListenerConversas);
    }
}
