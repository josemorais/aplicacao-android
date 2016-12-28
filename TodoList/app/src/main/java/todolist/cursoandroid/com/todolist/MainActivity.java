package todolist.cursoandroid.com.todolist;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends Activity {

    private EditText texto;
    private Button botaoAdicionar;
    private ListView listView;
    private SQLiteDatabase database;

    private ArrayAdapter<String> itensAdaptador;
    private ArrayList<String> itens;
    private ArrayList<Integer> ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {

            texto = (EditText) findViewById(R.id.edtTextId);
            botaoAdicionar = (Button) findViewById(R.id.btnAdicionarId);
            listView = (ListView) findViewById(R.id.listViewId);
            database = openOrCreateDatabase("apptarefas", MODE_PRIVATE, null);

            database.execSQL("CREATE TABLE IF NOT EXISTS tarefa (id INTEGER PRIMARY KEY AUTOINCREMENT, descricao VARCHAR) ");

            botaoAdicionar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String textoDigitado = texto.getText().toString();
                    salvarTarefa(textoDigitado);
                    recuperarTarefas();
                    texto.setText(null);
                }
            });

            listView.setLongClickable(true);
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Log.i("DELETE", "POSITION: " + i + " INDICE: " + ids.get(i));
                    removerTarefa(ids.get(i));
                    return true;
                }
            });

            recuperarTarefas();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void recuperarTarefas() {
       try{

           itens = new ArrayList<String>();
           ids = new ArrayList<Integer>();

           itensAdaptador = new ArrayAdapter<String>(getApplicationContext(),
                   android.R.layout.simple_list_item_2,
                   android.R.id.text2,
                   itens);
           listView.setAdapter(itensAdaptador);

           Cursor cursor = database.rawQuery("SELECT * FROM tarefa ORDER BY id DESC", null);

           cursor.moveToFirst();
           while (cursor != null) {
               Log.i("Resultado - ", " Id: " + cursor.getString(cursor.getColumnIndex("id")) +" Descricao: " + cursor.getString(cursor.getColumnIndex("descricao")));
               itens.add(cursor.getString(cursor.getColumnIndex("descricao")));
               ids.add(Integer.valueOf(cursor.getString(cursor.getColumnIndex("id"))));
               cursor.moveToNext();
           }

       }catch (Exception e){
           e.printStackTrace();
       }
    }

    private void salvarTarefa(String texto) {
        try{
            if (!texto.isEmpty()){
                database.execSQL("INSERT INTO tarefa (descricao) VALUES ('" + texto + "')");
                Toast.makeText(MainActivity.this, "Tarefa salva com sucesso!",Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Digite uma tarefa!",Toast.LENGTH_SHORT).show();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void removerTarefa(Integer id){
        try{
            database.execSQL("DELETE FROM tarefa WHERE id=" + id);
            recuperarTarefas();
            Toast.makeText(MainActivity.this,"Tarefa removida com sucesso", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}