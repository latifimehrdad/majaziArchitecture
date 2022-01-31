package land.majazi.latifiarchitecure.manager;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import java.util.Locale;

public class DeviceManager {

    //______________________________________________________________________________________________ getAppVersionCode
    public int getAppVersionCode(Context context) {
        PackageInfo pInfo;
        try {
            pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pInfo.versionCode;
        } catch (PackageManager.NameNotFoundException ignored) {
            return 0;
        }
    }
    //______________________________________________________________________________________________ getAppVersionCode


    //______________________________________________________________________________________________ getAndroidVersion
    public String getAndroidVersion() {
        try {
            String release = Build.VERSION.RELEASE;
            int sdkVersion = Build.VERSION.SDK_INT;
            return "Android Version : " + release + " (SDK : " + sdkVersion + ")";
        } catch (Exception e) {
            return "Android Version : I could not access the Android Version";
        }
    }
    //______________________________________________________________________________________________ getAndroidVersion


    //______________________________________________________________________________________________ getDeviceName
    public String getDeviceName() {
        try {
            String manufacturer = Build.MANUFACTURER;
            String model = Build.MODEL;
            model.replaceAll("-", "_");
            if (model.startsWith(manufacturer)) {
                return capitalize(model);
            } else {
                return capitalize(manufacturer) + " " + model;
            }
        } catch (Exception e) {
            return "DeviceName : I could not access the Device Name";
        }
    }
    //______________________________________________________________________________________________ getDeviceName


    //______________________________________________________________________________________________ getDeviceLanguage
    public String getDeviceLanguage() {
        try {
            String language = Locale.getDefault().getDisplayLanguage();
            return "Device Language : " + language;
        } catch (Exception e) {
            return "Device Language : I could not access the Device Language";
        }
    }
    //______________________________________________________________________________________________ getDeviceLanguage


    //______________________________________________________________________________________________ capitalize
    private String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }
    //______________________________________________________________________________________________ capitalize


}
