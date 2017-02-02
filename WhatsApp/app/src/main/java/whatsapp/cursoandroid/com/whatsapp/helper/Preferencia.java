package whatsapp.cursoandroid.com.whatsapp.helper;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;


public class Preferencia {

    private Context context;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private static final String NOME_ARQUIVO = "whatsapp.preferences";

    public static final String CHAVE_IDENTIFICADOR = "identificador";
    public static final String CHAVE_NOME = "nome";

    public Preferencia(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(NOME_ARQUIVO, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void salvarPreferenciasUsuario(String identificador, String nome) {
        editor.putString(CHAVE_IDENTIFICADOR, identificador);
        editor.putString(CHAVE_NOME, nome);
        editor.commit();
    }

    public HashMap<String, String> getDadosUsuario() {
        HashMap<String, String> dadosUsuario = new HashMap<>();

        dadosUsuario.put(CHAVE_IDENTIFICADOR, preferences.getString(CHAVE_IDENTIFICADOR, null));
        dadosUsuario.put(CHAVE_NOME, preferences.getString(CHAVE_NOME, null));

        return dadosUsuario;
    }
}
