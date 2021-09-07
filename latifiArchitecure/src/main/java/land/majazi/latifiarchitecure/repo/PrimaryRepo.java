package land.majazi.latifiarchitecure.repo;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import land.majazi.latifiarchitecure.R;
import land.majazi.latifiarchitecure.models.PrimaryModel;
import land.majazi.latifiarchitecure.models.RP_Primary;
import land.majazi.latifiarchitecure.utility.Utility;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrimaryRepo {

    private Activity activity;

    //______________________________________________________________________________________________ PrimaryRepo
    public PrimaryRepo(Activity activity) {
        this.activity = activity;
    }
    //______________________________________________________________________________________________ PrimaryRepo


    //______________________________________________________________________________________________ unAuthorization
    public MutableLiveData<PrimaryModel> unAuthorization() {
        PrimaryModel primaryModel = new PrimaryModel();
        primaryModel.setError(true);
        primaryModel.setResponseCode(401);
        primaryModel.setMessage(getActivity().getResources().getString(R.string.unAuthorization));
        MutableLiveData<PrimaryModel> liveData = new MutableLiveData<>();
        liveData.setValue(primaryModel);
        return liveData;
    }
    //______________________________________________________________________________________________ unAuthorization


    //______________________________________________________________________________________________ sendRequest
    public MutableLiveData<PrimaryModel> sendRequest(Call primaryCall, boolean hideKeyboard) {

        final MutableLiveData<PrimaryModel> mutableLiveData = new MutableLiveData<>();

        if (hideKeyboard) {
            Utility utility = new Utility();
            utility.hideKeyboard(getActivity());
        }

        primaryCall.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                mutableLiveData.setValue(getErrorMessage(response));
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                mutableLiveData.setValue(getErrorMessage(null));
            }
        });

        return mutableLiveData;
    }
    //______________________________________________________________________________________________ sendRequest


    //______________________________________________________________________________________________ validationError
    public MutableLiveData<PrimaryModel> validationError() {
        PrimaryModel primaryModel = new PrimaryModel();
        primaryModel.setError(true);
        primaryModel.setResponseCode(2);
        primaryModel.setMessage(getActivity().getResources().getString(R.string.validationError));
        MutableLiveData<PrimaryModel> liveData = new MutableLiveData<>();
        liveData.setValue(primaryModel);
        return liveData;
    }
    //______________________________________________________________________________________________ validationError


    //______________________________________________________________________________________________ getErrorMessage
    private PrimaryModel getErrorMessage(Response response) {
        Utility utility = new Utility();
        if (response == null)
            if (utility.isInternetConnected(activity))
                return new PrimaryModel(404, true, activity.getResources().getString(R.string.networkError), null);
            else
                return new PrimaryModel(404, true, activity.getResources().getString(R.string.internetNotAvailable), null);
        else
            return checkResponseIsNotNull(response);
    }
    //______________________________________________________________________________________________ getErrorMessage


    //______________________________________________________________________________________________ checkResponseIsNotNull
    private PrimaryModel checkResponseIsNotNull(Response response) {
        if (!response.isSuccessful() || response.body() == null) {
            return new PrimaryModel(response.code(), true, responseErrorMessage(response), null);
        } else {
            RP_Primary rp_primary = (RP_Primary) response.body();
            if (rp_primary.isSuccess())
                return new PrimaryModel(response.code(), false, null, response.body());
            else
                return new PrimaryModel(1, true, rp_primary.getMessage(), response.body());
        }
    }
    //______________________________________________________________________________________________ checkResponseIsNotNull


    //______________________________________________________________________________________________ responseErrorMessage
    private String responseErrorMessage(Response response) {
        try {
            JSONObject jObjError = new JSONObject(response.errorBody().string());
            String jobErrorString = jObjError.toString();
            String message;
            if (jobErrorString.contains("message"))
                message = jObjError.getString("message");
            else if (jobErrorString.contains("Message"))
                message = jObjError.getString("Message");
            else
                message = activity.getResources().getString(R.string.networkError);
            List<String> errors = new ArrayList<>();
            if (jobErrorString.contains("errors")) {
                JSONArray jsonArray = jObjError.getJSONArray("errors");
                for (int i = 0; i < jsonArray.length(); i++) {
                    String temp = jsonArray.getString(i);
                    errors.add(temp);
                }
            } else {
                return setResponseMessage(message, errors);
            }
            return setResponseMessage(message, errors);
        } catch (Exception ex) {
            return setResponseMessage(activity.getResources().getString(R.string.networkError), null);
        }
    }
    //______________________________________________________________________________________________ responseErrorMessage


    //______________________________________________________________________________________________ setResponseMessage
    private String setResponseMessage(String message, List<String> error) {

        String text = "";
        if (message != null && !message.isEmpty())
            text = message;

        if (error != null && !error.isEmpty())
            for (String e : error) {
                text = text + System.getProperty("line.separator");
                text = text + e;
            }

        return text;
    }
    //______________________________________________________________________________________________ setResponseMessage


    //______________________________________________________________________________________________ getContext
    public Activity getActivity() {
        return activity;
    }
    //______________________________________________________________________________________________ getContext


    //______________________________________________________________________________________________ getSharedPreferences
    public SharedPreferences getSharedPreferences(String appName) {

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(
                "secret_shared_prefs_" + appName,
                0);

        return sharedPreferences;

    }
    //______________________________________________________________________________________________ getSharedPreferences


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


    //______________________________________________________________________________________________ getAndroidVersion
    public String getAndroidVersion() {
        try {
            String release = Build.VERSION.RELEASE;
            int sdkVersion = Build.VERSION.SDK_INT;
            return "Android Version : " + release + " (SDK : " + release + ")";
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
            String languesge = Locale.getDefault().getDisplayLanguage();
            return "Device Language : " + languesge;
        } catch (Exception e) {
            return "Device Language : I could not access the Device Language";
        }
    }
    //______________________________________________________________________________________________ getDeviceLanguage


    //______________________________________________________________________________________________ saveBlurry
    public boolean saveBlurry(boolean blurry, String appName) {

        SharedPreferences shared = getSharedPreferences(appName);
        if (shared == null)
            return false;

        SharedPreferences.Editor editor = shared.edit();
        editor.putBoolean(getActivity().getResources().getString(R.string.sharedPreferencesBlurry), blurry);
        return editor.commit();
    }
    //______________________________________________________________________________________________ saveBlurry


    //______________________________________________________________________________________________ getBlurry
    public boolean getBlurry(String appName) {
        SharedPreferences shared = getSharedPreferences(appName);
        if (shared == null)
            return false;

        return shared.getBoolean(getActivity().getResources().getString(R.string.sharedPreferencesBlurry), false);
    }
    //______________________________________________________________________________________________ getBlurry


    //______________________________________________________________________________________________ saveTheme
    public boolean saveTheme(String theme, String appName) {

        SharedPreferences shared = getSharedPreferences(appName);
        if (shared == null)
            return false;

        SharedPreferences.Editor editor = shared.edit();
        editor.putString(getActivity().getResources().getString(R.string.sharedPreferencesTheme), theme);
        editor.commit();
        return true;
    }
    //______________________________________________________________________________________________ saveTheme


    //______________________________________________________________________________________________ getTheme
    public String getSaveTheme(String appName) {
        String theme = "";
        SharedPreferences shared = getSharedPreferences(appName);
        if (shared == null)
            return theme;

        theme = shared.getString(getActivity().getResources().getString(R.string.sharedPreferencesTheme), "");

        return theme;
    }
    //______________________________________________________________________________________________ getTheme


    //______________________________________________________________________________________________ getAppTheme
    public String getAppTheme(String appName) {
        String theme = getSaveTheme(appName);
        if (theme.isEmpty())
            switch (getActivity().getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) {
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
    public void changeTheme(String appName) {

        switch (getAppTheme(appName)) {
            case "dark":
                saveTheme("light", appName);
                changeDeviceTheme(false);
                break;
            case "light":
                saveTheme("dark", appName);
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


}
