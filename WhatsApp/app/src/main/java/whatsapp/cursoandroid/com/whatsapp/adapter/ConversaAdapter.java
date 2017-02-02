package whatsapp.cursoandroid.com.whatsapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import whatsapp.cursoandroid.com.whatsapp.R;
import whatsapp.cursoandroid.com.whatsapp.model.Conversa;


public class ConversaAdapter extends ArrayAdapter<Conversa> {

    private Context context;
    private ArrayList<Conversa> conversas;
    private  Conversa conversa;

    public ConversaAdapter(Context context, ArrayList<Conversa> objects) {
        super(context, 0, objects);
        this.context = context;
        this.conversas = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        if (conversas != null){

            //Inicializa o objeto para montagem do layout
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            //Monta a view a partir do layout
            view = layoutInflater.inflate(R.layout.lista_conversas, parent, false);

            //Recupera os elementos da tela
            TextView nome = (TextView) view.findViewById(R.id.tv_nome_id);
            TextView ultimaMensagem = (TextView) view.findViewById(R.id.tv_ultima_mensagem_id);

            //Setar os valores nos componentes da tela
            conversa = conversas.get(position);
            nome.setText(conversa.getNome());
            ultimaMensagem.setText(conversa.getUltimaMensagem());
        }

        return view;
    }
}
