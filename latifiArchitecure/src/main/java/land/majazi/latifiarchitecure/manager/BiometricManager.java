package land.majazi.latifiarchitecure.manager;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.ActivityCompat;

import land.majazi.latifiarchitecure.views.activity.MasterActivity;
import land.majazi.latifiarchitecure.views.fragments.FragmentAction;


public class BiometricManager {

    //______________________________________________________________________________________________ checkBiometric
    public void checkBiometric(Activity activity, FragmentAction fragmentAction, int biometricAction) {

        if (activity == null)
            return;

        MasterActivity.biometricAction = biometricAction;
        KeyguardManager keyguardManager = (KeyguardManager) activity.getSystemService(Context.KEYGUARD_SERVICE);
        boolean isSecure = keyguardManager.isKeyguardSecure();

        if (isSecure) {
            Intent intent = keyguardManager.createConfirmDeviceCredentialIntent("رمز ورود به گوشی را وارد نمایید", "از وارد کردن رمز مجازی لند خودداری نمایید");
            ActivityCompat.startActivityForResult(activity,intent, MasterActivity.RESULT_BIOMETRIC_ACTIVITY, null);
        } else {
            fragmentAction.bioMetric(biometricAction);
        }
    }
    //______________________________________________________________________________________________ checkBiometric


}
