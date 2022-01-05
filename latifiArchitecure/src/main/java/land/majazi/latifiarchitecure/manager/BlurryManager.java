package land.majazi.latifiarchitecure.manager;

import android.content.Context;
import android.content.SharedPreferences;

public class BlurryManager {

    private final String sharedPreferencesBlurry = "blurry";


    //______________________________________________________________________________________________ saveBlurry
    public boolean saveBlurry(Context context, boolean blurry, String appName) {

        SharedPreferences shared = new SharePreferenceManager().getSharedPreferences(context, appName);
        if (shared == null)
            return false;

        SharedPreferences.Editor editor = shared.edit();
        editor.putBoolean(sharedPreferencesBlurry, blurry);
        return editor.commit();
    }
    //______________________________________________________________________________________________ saveBlurry


    //______________________________________________________________________________________________ getBlurry
    public boolean getBlurry(Context context, String appName) {
        SharedPreferences shared = new SharePreferenceManager().getSharedPreferences(context, appName);
        if (shared == null)
            return false;

        return shared.getBoolean(sharedPreferencesBlurry, false);
    }
    //______________________________________________________________________________________________ getBlurry


}
