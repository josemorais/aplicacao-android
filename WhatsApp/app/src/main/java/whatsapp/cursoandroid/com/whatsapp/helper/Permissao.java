package whatsapp.cursoandroid.com.whatsapp.helper;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class Permissao {

    public static boolean validaPermissao(int requestCode, Activity activity, String[] permissoes) {

        Log.i("VERSAO: ", "VERSAO: " + Build.VERSION.SDK_INT);
        if (Build.VERSION.SDK_INT >= 23) {
            List<String> listaPermissoes = new ArrayList<>();

            //Percorre as permissoes para saber se está liberada
            for (String permissao : permissoes) {
                boolean validaPermissao = ContextCompat.checkSelfPermission(activity, permissao) == PackageManager.PERMISSION_GRANTED;
                if (!validaPermissao) listaPermissoes.add(permissao);
            }

            if (listaPermissoes.isEmpty()) return true;

            String[] novasPermissoes = new String[listaPermissoes.size()];
            listaPermissoes.toArray(novasPermissoes);

            //Solicita permissao
            ActivityCompat.requestPermissions(activity, novasPermissoes, requestCode);

        }
        return true;
    }
}
