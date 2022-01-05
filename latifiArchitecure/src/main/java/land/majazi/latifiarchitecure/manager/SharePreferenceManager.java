package land.majazi.latifiarchitecure.manager;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePreferenceManager {


    //______________________________________________________________________________________________ getSharedPreferences
    public SharedPreferences getSharedPreferences(Context context, String appName) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(
                "secret_shared_prefs_" + appName,
                Context.MODE_PRIVATE);

        return sharedPreferences;

    }
    //______________________________________________________________________________________________ getSharedPreferences

}
