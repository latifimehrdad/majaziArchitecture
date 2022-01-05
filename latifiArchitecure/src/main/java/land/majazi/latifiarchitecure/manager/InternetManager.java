package land.majazi.latifiarchitecure.manager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;

import land.majazi.latifiarchitecure.enums.EnumInternetConnection;

public class InternetManager {

    //______________________________________________________________________________________________ connectionType
    public EnumInternetConnection connectionType(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkCapabilities capabilities = cm.getNetworkCapabilities(cm.getActiveNetwork());
        if (capabilities == null)
            return EnumInternetConnection.NONE;
        else {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
                return EnumInternetConnection.CELLULAR;
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI_AWARE))
                return EnumInternetConnection.WIFI;
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET))
                return EnumInternetConnection.WIFI;
            else
                return EnumInternetConnection.NONE;
        }
    }
    //______________________________________________________________________________________________ connectionType
}
