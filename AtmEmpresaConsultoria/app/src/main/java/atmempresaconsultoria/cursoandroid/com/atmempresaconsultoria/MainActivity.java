package atmempresaconsultoria.cursoandroid.com.atmempresaconsultoria;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;


public class MainActivity extends Activity {

    private ImageView imagemEmpresa;
    private ImageView imagemServico;
    private ImageView imagemCliente;
    private ImageView imagemContato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imagemEmpresa = (ImageView) findViewById(R.id.imgEmpresaId);
        imagemServico = (ImageView) findViewById(R.id.imgServicoId);
        imagemCliente = (ImageView) findViewById(R.id.imgClienteId);
        imagemContato = (ImageView) findViewById(R.id.imgContatoId);

        imagemEmpresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this, EmpresaActivity.class));
            }
        });

        imagemServico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this, ServicoActivity.class));
            }
        });

        imagemCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this, ClienteActivity.class));
            }
        });

        imagemContato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this, ContatoActivity.class));
            }
        });
    }

}
