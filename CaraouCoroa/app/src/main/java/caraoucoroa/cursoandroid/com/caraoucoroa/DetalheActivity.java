package caraoucoroa.cursoandroid.com.caraoucoroa;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;


public class DetalheActivity extends Activity {

    private ImageView imagemMoeda;
    private ImageView imagemVoltar;

    private static final String MOEDA_CARA = "cara";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe);

        imagemMoeda = (ImageView) findViewById(R.id.imgMoedaId);
        imagemVoltar = (ImageView) findViewById(R.id.imgVoltarId);

        imagemVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
            }
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            String opcaoEscolhida = bundle.getString("opcaoEscolhida");
            if (opcaoEscolhida.equals(MOEDA_CARA)){
                imagemMoeda.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.moeda_cara));
            } else {
                imagemMoeda.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.moeda_coroa));
            }

        }

    }

}
