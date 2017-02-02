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
import whatsapp.cursoandroid.com.whatsapp.helper.Preferencia;
import whatsapp.cursoandroid.com.whatsapp.model.Mensagem;

public class MensagemAdapter extends ArrayAdapter<Mensagem> {

    private Context context;
    private ArrayList<Mensagem> mensagens;

    public MensagemAdapter(Context context, ArrayList<Mensagem> objects) {
        super(context, 0, objects);
        this.context = context;
        this.mensagens = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        if (mensagens != null){

            Mensagem mensagem = mensagens.get(position);

            Preferencia preferencia = new Preferencia(context);
            String idUsuarioLogado = preferencia.getDadosUsuario().get(Preferencia.CHAVE_IDENTIFICADOR);

            //Inicializa o objeto para montagem do layout
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            TextView textView = null;
            if (idUsuarioLogado.equals(mensagem.getIdUsuario())){
                //Monta a view a partir do xml
                view = layoutInflater.inflate(R.layout.item_mensagem_direita, parent, false);
                textView = (TextView) view.findViewById(R.id.tv_mensagem_direita_id);

            } else {
                view = layoutInflater.inflate(R.layout.item_mensagem_esquerda, parent, false);
                textView = (TextView) view.findViewById(R.id.tv_mensagem_esquerda_id);
            }

            textView.setText(mensagem.getMensagem());
        }
        return view;
    }
}
