package land.majazi.latifiarchitecure.manager;

import android.app.Activity;
import android.content.Context;
import androidx.lifecycle.MutableLiveData;
import land.majazi.latifiarchitecure.R;
import land.majazi.latifiarchitecure.manager.KeyBoardManager;
import land.majazi.latifiarchitecure.manager.ResponseModelManager;
import land.majazi.latifiarchitecure.models.ResponseModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepoManager {


    //______________________________________________________________________________________________ unAuthorization
    public MutableLiveData<ResponseModel> unAuthorization(Context context) {
        ResponseModel responseModel = new ResponseModel();
        responseModel.setError(true);
        responseModel.setResponseCode(401);
        responseModel.setMessage(context.getResources().getString(R.string.unAuthorization));
        MutableLiveData<ResponseModel> liveData = new MutableLiveData<>();
        liveData.setValue(responseModel);
        return liveData;
    }
    //______________________________________________________________________________________________ unAuthorization


    //______________________________________________________________________________________________ callRequest
    public MutableLiveData<ResponseModel> callRequest(Context context, Call primaryCall, Activity activityForHideKeyboard, boolean isLoginRequest) {

        final MutableLiveData<ResponseModel> mutableLiveData = new MutableLiveData<>();

        if (activityForHideKeyboard != null) {
            KeyBoardManager keyBoardManager = new KeyBoardManager();
            keyBoardManager.hide(activityForHideKeyboard);
        }

        primaryCall.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                mutableLiveData.setValue(new ResponseModelManager().getResponse(context, response, isLoginRequest));
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                mutableLiveData.setValue(new ResponseModelManager().getResponse(context, null, isLoginRequest));
            }
        });

        return mutableLiveData;
    }
    //______________________________________________________________________________________________ callRequest


}
