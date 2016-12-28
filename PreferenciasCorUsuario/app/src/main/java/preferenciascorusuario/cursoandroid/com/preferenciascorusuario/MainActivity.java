package preferenciascorusuario.cursoandroid.com.preferenciascorusuario;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;


public class MainActivity extends Activity {

    private RadioGroup radioGroup;
    private RadioButton radioButtonSelecionado;
    private Button botaoSalvar;
    private RelativeLayout layout;
    private static final String ARQUIVO_PREFERENCIAS = "ArqPreferencias";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroupCorId);
        botaoSalvar = (Button) findViewById(R.id.btnSalvarId);
        layout  = (RelativeLayout) findViewById(R.id.layoutId);

        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int idRadioButtonEscolhido = radioGroup.getCheckedRadioButtonId();
                if (idRadioButtonEscolhido > 0){
                    radioButtonSelecionado = (RadioButton) findViewById(idRadioButtonEscolhido);

                    SharedPreferences sharedPreferences = getSharedPreferences(ARQUIVO_PREFERENCIAS, 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    String corEscolhida = radioButtonSelecionado.getText().toString();
                    editor.putString("corEscolhida", corEscolhida);
                    editor.putInt("radioEscolhido", idRadioButtonEscolhido);
                    editor.commit();

                    setBackGround(corEscolhida);

                }
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences(ARQUIVO_PREFERENCIAS, 0);
        if (sharedPreferences.contains("corEscolhida")){
            String corRecuperada = sharedPreferences.getString("corEscolhida", "Laranja");
            setBackGround(corRecuperada);
        }
        if (sharedPreferences.contains("radioEscolhido")){
            int idRadioSelecionado = sharedPreferences.getInt("radioEscolhido", 0);
            radioGroup.check(idRadioSelecionado);
        }
    }

    private void setBackGround(String cor) {
        if (cor.equals("Azul")){
            layout.setBackgroundColor(Color.parseColor("#495b7c"));
        } else if (cor.equals("Laranja")){
            layout.setBackgroundColor(Color.parseColor("#ffce26"));
        } else if (cor.equals("Verde")){
            layout.setBackgroundColor(Color.parseColor("#009670"));
        }
    }


}
