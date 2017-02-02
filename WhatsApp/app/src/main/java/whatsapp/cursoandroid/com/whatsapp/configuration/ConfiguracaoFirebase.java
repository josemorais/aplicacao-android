package whatsapp.cursoandroid.com.whatsapp.configuration;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


public final class ConfiguracaoFirebase {

    private static FirebaseAuth auth;
    private static FirebaseDatabase database;

    public static FirebaseAuth getAuth() {

        if (auth == null) {
            auth = FirebaseAuth.getInstance();
        }
        return auth;
    }

    public static FirebaseDatabase getDatabase() {
        if (database == null) {
            database = FirebaseDatabase.getInstance();
        }
        return database;
    }
}
