package land.majazi.latifiarchitecure.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import androidx.appcompat.app.AppCompatDelegate;

public class ThemeManager {

    private final String sharedPreferencesTheme = "themeSharedPreferences";

    //______________________________________________________________________________________________ saveTheme
    public boolean saveTheme(Context context, String appName, String theme) {
        SharedPreferences shared = new SharePreferenceManager().getSharedPreferences(context, appName);
        if (shared == null)
            return false;

        SharedPreferences.Editor editor = shared.edit();
        editor.putString(sharedPreferencesTheme, theme);
        editor.apply();
        return true;
    }
    //______________________________________________________________________________________________ saveTheme



    //______________________________________________________________________________________________ getTheme
    public String getSaveTheme(Context context, String appName) {
        String theme = "";
        SharedPreferences shared = new SharePreferenceManager().getSharedPreferences(context, appName);
        if (shared == null)
            return theme;

        theme = shared.getString(sharedPreferencesTheme, "");

        return theme;
    }
    //______________________________________________________________________________________________ getTheme



    //______________________________________________________________________________________________ getAppTheme
    public String getAppTheme(Context context, String appName) {
        String theme = getSaveTheme(context, appName);
        if (theme.isEmpty())
            switch (context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) {
                case Configuration.UI_MODE_NIGHT_YES:
                    theme = "dark";
                    break;
                case Configuration.UI_MODE_NIGHT_NO:
                    theme = "light";
                    break;
            }

        return theme;
    }
    //______________________________________________________________________________________________ getAppTheme


    //______________________________________________________________________________________________ changeTheme
    public void changeTheme(Context context, String appName) {

        switch (getAppTheme(context, appName)) {
            case "dark":
                saveTheme(context,appName,"light");
                changeDeviceTheme(false);
                break;
            case "light":
                saveTheme(context, appName, "dark");
                changeDeviceTheme(true);
                break;
        }

    }
    //______________________________________________________________________________________________ changeTheme


    //______________________________________________________________________________________________ changeDeviceTheme
    public void changeDeviceTheme(boolean dark) {

        if (dark)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

    }
    //______________________________________________________________________________________________ changeDeviceTheme


//    //______________________________________________________________________________________________ getContext
//    public Context getContext() {
//        return context;
//    }
//    //______________________________________________________________________________________________ getContext
}
