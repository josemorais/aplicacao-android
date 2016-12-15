package caraoucoroa.cursoandroid.com.caraoucoroa;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.Random;


public class MainActivity extends Activity {

    private ImageView imagemJogar;
    private String[] opcoes = {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imagemJogar = (ImageView) findViewById(R.id.imgJogarId);
        opcoes = getResources().getStringArray(R.array.opcoes);

        imagemJogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Random randomico = new Random();
                int numeroAleatorio = randomico.nextInt(opcoes.length);

                Intent intent = new Intent(MainActivity.this, DetalheActivity.class);
                intent.putExtra("opcaoEscolhida", opcoes[numeroAleatorio]);
                startActivity(intent);

            }
        });
    }

}
