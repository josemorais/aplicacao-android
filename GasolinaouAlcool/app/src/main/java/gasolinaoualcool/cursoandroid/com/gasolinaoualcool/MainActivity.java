package gasolinaoualcool.cursoandroid.com.gasolinaoualcool;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private EditText precoAlcool;
    private EditText precoGasolina;
    private Button botaoVerificar;
    private TextView textoResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        precoAlcool = (EditText) findViewById(R.id.edtPrecoAlcoolId);
        precoGasolina = (EditText) findViewById(R.id.edtPrecoGasolinaId);
        botaoVerificar = (Button) findViewById(R.id.btnVerificarId);
        textoResultado = (TextView) findViewById(R.id.txtResultadoId);

        botaoVerificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                esconderTeclado(MainActivity.this);

                String textoPrecoAlcool = precoAlcool.getText().toString();
                String textoPrecoGasolina = precoGasolina.getText().toString();

                if (!textoPrecoAlcool.isEmpty()){
                    if (!textoPrecoGasolina.isEmpty()){

                        Double valorAlcool = Double.valueOf(textoPrecoAlcool);
                        Double valorGasolina = Double.valueOf(textoPrecoGasolina);

                        double resultado = valorAlcool / valorGasolina;

                        if (resultado >= 0.7) {
                            Toast.makeText(getApplicationContext(), "É melhor utilizar Gasolina!", Toast.LENGTH_LONG).show();
                        } else {
                           Toast.makeText(getApplicationContext(), "É melhor utilizar Álcool!", Toast.LENGTH_LONG).show();
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "Digite o preço da gasolina.",Toast.LENGTH_LONG).show();
                    }

                }else {
                    Toast.makeText(getApplicationContext(), "Digite o preço do álcool.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void esconderTeclado(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
