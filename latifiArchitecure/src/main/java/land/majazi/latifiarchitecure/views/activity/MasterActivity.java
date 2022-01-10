package land.majazi.latifiarchitecure.views.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;


import land.majazi.latifiarchitecure.views.fragments.FragmentAction;

public class MasterActivity extends AppCompatActivity {

    public static final int RESULT_BIOMETRIC_ACTIVITY = 11;
    public static int biometricAction;
    public static FragmentAction fragmentAction;

    //______________________________________________________________________________________________ onRequestPermissionsResult
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean accessPermission = true;
        for (String permission : permissions) {
            int check = ContextCompat.checkSelfPermission(this, permission);
            if (check != PackageManager.PERMISSION_GRANTED) {
                accessPermission = false;
                break;
            }
        }

        if (accessPermission)
            fragmentAction.permissionWasGranted();
        else
            this.onBackPressed();

        fragmentAction = null;

    }
    //______________________________________________________________________________________________ onRequestPermissionsResult


    //______________________________________________________________________________________________ onActivityResult
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            if (resultCode == RESULT_OK)
                activityResultOk(requestCode);
            else
                activityResultCancel(requestCode);
    }
    //______________________________________________________________________________________________ onActivityResult



    //______________________________________________________________________________________________ activityResultOk
    private void activityResultOk(int requestCode) {
        if (fragmentAction == null)
            return;

        if (requestCode == RESULT_BIOMETRIC_ACTIVITY)
            fragmentAction.bioMetric(biometricAction);

        fragmentAction = null;
    }
    //______________________________________________________________________________________________ activityResultOk


    //______________________________________________________________________________________________ activityResultCancel
    private void activityResultCancel(int requestCode) {
        if (fragmentAction == null)
            return;

        if (requestCode == RESULT_BIOMETRIC_ACTIVITY)
            fragmentAction.bioMetricFailed(biometricAction);

        fragmentAction = null;
    }
    //______________________________________________________________________________________________ activityResultCancel



}
