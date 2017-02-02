package whatsapp.cursoandroid.com.whatsapp.helper;

import android.widget.EditText;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;


public class Mascara {

    public static void formatar(EditText textView, String formato) {

        //criar mascara para a formatacao de campos
        SimpleMaskFormatter mascara = new SimpleMaskFormatter(formato);
        MaskTextWatcher maskTextWatcher = new MaskTextWatcher(textView, mascara);
        textView.addTextChangedListener(maskTextWatcher);
    }
}
