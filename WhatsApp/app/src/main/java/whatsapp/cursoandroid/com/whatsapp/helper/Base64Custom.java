package whatsapp.cursoandroid.com.whatsapp.helper;


import android.util.Base64;

public class Base64Custom {

    public static String converteToBase64(String texto){
        return Base64.encodeToString(texto.trim().getBytes(), Base64.NO_WRAP);
    }

    public static String decodificarBase64(String textoCodificado){
        byte[] byteCodificado = Base64.decode(textoCodificado, Base64.NO_WRAP);
        return new String(byteCodificado);
    }
}
