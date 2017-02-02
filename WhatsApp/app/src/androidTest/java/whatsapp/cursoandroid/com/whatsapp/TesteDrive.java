package whatsapp.cursoandroid.com.whatsapp;

import whatsapp.cursoandroid.com.whatsapp.helper.Base64Custom;

/**
 * Created by junior on 16/01/2017.
 */

public class TesteDrive {

    public static void main(String[] args) {

        String[] textos = {"junior.moraigti@gmail.com", "jr@gmail.com", "ana@hotmail.com", "jose@mail.com", "testando@google.com"};

        for (int i=0; i <textos.length; i++){
            String resultado = Base64Custom.converteToBase64(textos[i]);
            System.out.println(resultado);
            resultado = null;
        }

    }
}
