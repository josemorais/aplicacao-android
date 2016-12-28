package minhasanotacoes.cursoandroid.com.minhasanotecoes;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class MainActivity extends Activity {

    private EditText texto;
    private ImageView botaoSalvar;
    private static final String NOME_ARQUIVO = "arquivo_anotacao.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        texto = (EditText) findViewById(R.id.edtAnotacaoId);
        botaoSalvar = (ImageView) findViewById(R.id.imgSalvarId);

        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textoDigitado = texto.getText().toString();
                gravarNoArquivo(textoDigitado);
                Toast.makeText(MainActivity.this,"Anotação salva com sucesso!",Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        String textoRecuperado = lerArquivo();
        if (textoRecuperado != null){
            texto.setText(textoRecuperado);
        }
    }


    private void gravarNoArquivo(String textoDigitado) {
        try {

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput(NOME_ARQUIVO, Context.MODE_PRIVATE));
            outputStreamWriter.write(textoDigitado);
            outputStreamWriter.close();
        }catch (IOException e){
            Log.v("MaingActivity",e.toString());
        }
    }

    private String lerArquivo(){
        String resultado = "";

        try{
            InputStream arquivo = openFileInput(NOME_ARQUIVO);
            if (arquivo != null){
                InputStreamReader inputStreamReader = new InputStreamReader(arquivo);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String linhaArquivo = "";
                while ((linhaArquivo = bufferedReader.readLine()) != null){
                    resultado += linhaArquivo;
                }
                arquivo.close();
            }


        }catch (IOException e){
            Log.v("MainActivity",e.toString());
        }

        return resultado;
    }
}
