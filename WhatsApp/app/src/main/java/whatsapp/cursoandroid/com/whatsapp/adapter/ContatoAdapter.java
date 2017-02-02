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
import whatsapp.cursoandroid.com.whatsapp.model.Contato;

public class ContatoAdapter extends ArrayAdapter<Contato>{

    private Context context;
    private ArrayList<Contato> contatos;

    public ContatoAdapter(Context context, ArrayList<Contato> objects) {
        super(context, 0, objects);
        this.context = context;
        this.contatos = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        if (contatos != null){

            //Inicializa o objeto para a montagem do layout
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            //Monta a view a partir do xml
            view = inflater.inflate(R.layout.layout_contatos, parent, false);
            TextView textView = (TextView) view.findViewById(R.id.textViewContatosId);

            Contato contato = contatos.get(position);
            textView.setText(contato.getNome());

        }

        return view;
    }
}
