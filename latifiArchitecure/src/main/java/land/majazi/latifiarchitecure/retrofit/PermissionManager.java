package land.majazi.latifiarchitecure.manager;

import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class PermissionManager {

    //______________________________________________________________________________________________ isPermissionGranted
    public boolean isPermissionGranted(Activity activity, List<String> permissions) {

        if (activity == null)
            return false;

        List<String> listPermissionsNeeded = new ArrayList<>();

        for (String permission : permissions) {
            int check = ContextCompat.checkSelfPermission(activity, permission);
            if (check != PackageManager.PERMISSION_GRANTED)
                listPermissionsNeeded.add(permission); }

        if (!listPermissionsNeeded.isEmpty()) {
            int REQUEST_PERMISSIONS_CODE_WRITE_STORAGE = 7126;
            ActivityCompat.requestPermissions(activity,listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_PERMISSIONS_CODE_WRITE_STORAGE);
            return false;
        } else
            return true;
    }
    //______________________________________________________________________________________________ isPermissionGranted


}
