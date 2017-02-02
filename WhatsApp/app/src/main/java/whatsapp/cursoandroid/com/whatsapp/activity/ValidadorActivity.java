package whatsapp.cursoandroid.com.whatsapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

import whatsapp.cursoandroid.com.whatsapp.R;
import whatsapp.cursoandroid.com.whatsapp.helper.Mascara;
import whatsapp.cursoandroid.com.whatsapp.helper.Preferencia;

public class ValidadorActivity extends BaseActivity {

    private EditText codigoValidacao;
    private Button botaoValidacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validador);

        codigoValidacao = (EditText) findViewById(R.id.edtCodigoValidacaoId);
        botaoValidacao = (Button) findViewById(R.id.btnValidacaoId);

        //Formatacao do codigo de validacao
        Mascara.formatar(codigoValidacao, "NNNN");

        botaoValidacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Recuperar as preferencias do usuario
                Preferencia preferencia = new Preferencia(ValidadorActivity.this);
                HashMap<String, String> usuario = preferencia.getDadosUsuario();

                String tokenSalvo = usuario.get(Preferencia.CHAVE_IDENTIFICADOR);
                String tokenDigitado = codigoValidacao.getText().toString();

                if (tokenSalvo.equals(tokenDigitado)){
                    Toast.makeText(ValidadorActivity.this, "Código VALIDADO!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ValidadorActivity.this, "Código não VALIDADO!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

}
