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
import whatsapp.cursoandroid.com.whatsapp.adapter.ContatoAdapter;
import whatsapp.cursoandroid.com.whatsapp.configuration.ConfiguracaoFirebase;
import whatsapp.cursoandroid.com.whatsapp.helper.Preferencia;
import whatsapp.cursoandroid.com.whatsapp.model.Contato;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContatosFragment extends Fragment {

    private ListView listView;
    private ArrayAdapter adapter;
    private ArrayList<Contato> contatos;

    private DatabaseReference referenciaContato;
    private ValueEventListener valueEventListener;

    public ContatosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contatos, container, false);

        contatos = new ArrayList<>();

        listView = (ListView) view.findViewById(R.id.listViewContatos);
        adapter = new ContatoAdapter(getActivity(), contatos);
        listView.setAdapter(adapter);

        Preferencia preferencia = new Preferencia(getActivity());
        String identificadorUsuarioLogado = preferencia.getDadosUsuario().get(Preferencia.CHAVE_IDENTIFICADOR);

        referenciaContato = ConfiguracaoFirebase.getDatabase().getReference("contatos");
        if (identificadorUsuarioLogado != null){
            referenciaContato = referenciaContato.child(identificadorUsuarioLogado);
        }

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                contatos.clear();

                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                    Contato contato = dados.getValue(Contato.class);
                    contatos.add(contato);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), ConversaActivity.class);

                Contato contato = contatos.get(i);
                intent.putExtra("nome", contato.getNome());
                intent.putExtra("email", contato.getEmail());
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        referenciaContato.addValueEventListener(valueEventListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        referenciaContato.removeEventListener(valueEventListener);
    }

}
