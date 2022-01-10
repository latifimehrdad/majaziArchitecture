package land.majazi.latifiarchitecure.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.BlurMaskFilter;
import android.os.Build;
import android.view.View;
import android.widget.TextView;

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


    //______________________________________________________________________________________________ blurryTextView
    public void blurryTextView(TextView view) {

        view.setText("0000000000");
        view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        float radius = view.getTextSize() / 3;
        BlurMaskFilter filter = new BlurMaskFilter(radius, BlurMaskFilter.Blur.NORMAL);
        view.getPaint().setMaskFilter(filter);

    }
    //______________________________________________________________________________________________ blurryTextView


}
