package land.majazi.latifiarchitecure.manager;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import land.majazi.latifiarchitecure.R;
import land.majazi.latifiarchitecure.enums.EnumInternetConnection;
import land.majazi.latifiarchitecure.models.BaseResponseModel;
import land.majazi.latifiarchitecure.models.ResponseModel;
import retrofit2.Response;

public class ResponseModelManager {



    //______________________________________________________________________________________________ getErrorMessage
    public ResponseModel getResponse(Context context, Response response, boolean isLoginRequest, String throwable) {
        if (response == null)
            return checkResponseISNull(context, throwable);
        else
            return checkResponseIsNotNull(context, response, isLoginRequest);
    }
    //______________________________________________________________________________________________ getErrorMessage


    //______________________________________________________________________________________________ checkResponseISNull
    private ResponseModel checkResponseISNull(Context context, String throwable) {
        InternetManager internetManager = new InternetManager();
        if (internetManager.connectionType(context) == EnumInternetConnection.NONE)
            return new ResponseModel(404, true, context.getResources().getString(R.string.internetNotAvailable), null);
        else
            return new ResponseModel(404, true, context.getResources().getString(R.string.networkError) + System.getProperty("line.separator") + throwable, null);
    }
    //______________________________________________________________________________________________ checkResponseISNull



    //______________________________________________________________________________________________ checkResponseIsNotNull
    private ResponseModel checkResponseIsNotNull(Context context, Response response, boolean isLoginRequest) {
        if (!response.isSuccessful() || response.body() == null) {
            return new ResponseModel(response.code(), true, responseErrorMessage(context, response), null);
        } else {
            BaseResponseModel baseResponseModel = (BaseResponseModel) response.body();
            if (baseResponseModel.isSuccess() || isLoginRequest)
                return new ResponseModel(response.code(), false, null, response.body());
            else
                return new ResponseModel(1, true, baseResponseModel.getMessage(), response.body());
        }
    }
    //______________________________________________________________________________________________ checkResponseIsNotNull


    //______________________________________________________________________________________________ responseErrorMessage
    private String responseErrorMessage(Context context, Response response) {
        try {
            JSONObject jObjError = new JSONObject(response.errorBody().string());
            String jobErrorString = jObjError.toString();
            String message;
            if (jobErrorString.contains("message"))
                message = jObjError.getString("message");
            else if (jobErrorString.contains("Message"))
                message = jObjError.getString("Message");
            else
                message = context.getResources().getString(R.string.networkError);
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
            return setResponseMessage(context.getResources().getString(R.string.networkError), null);
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

}
