package land.majazi.latifiarchitecure.manager;

import android.app.Activity;

import androidx.lifecycle.MutableLiveData;
import land.majazi.latifiarchitecure.R;
import land.majazi.latifiarchitecure.models.ResponseModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepoManager {

    private final Activity activity;


    //______________________________________________________________________________________________ RepoManager
    public RepoManager(Activity activity) {
        this.activity = activity;
    }
    //______________________________________________________________________________________________ RepoManager


    //______________________________________________________________________________________________ validationError
    public MutableLiveData<ResponseModel> validationError() {
        ResponseModel responseModel = new ResponseModel();
        responseModel.setError(true);
        responseModel.setResponseCode(2);
        responseModel.setMessage(getActivity().getResources().getString(R.string.validationError));
        MutableLiveData<ResponseModel> liveData = new MutableLiveData<>();
        liveData.setValue(responseModel);
        return liveData;
    }
    //______________________________________________________________________________________________ validationError



    //______________________________________________________________________________________________ unAuthorization
    public MutableLiveData<ResponseModel> unAuthorization() {
        ResponseModel responseModel = new ResponseModel();
        responseModel.setError(true);
        responseModel.setResponseCode(401);
        responseModel.setMessage(getActivity().getResources().getString(R.string.unAuthorization));
        MutableLiveData<ResponseModel> liveData = new MutableLiveData<>();
        liveData.setValue(responseModel);
        return liveData;
    }
    //______________________________________________________________________________________________ unAuthorization


    //______________________________________________________________________________________________ callRequest
    public MutableLiveData<ResponseModel> callRequest(Call primaryCall, boolean hideKeyboard, boolean isLoginRequest) {

        final MutableLiveData<ResponseModel> mutableLiveData = new MutableLiveData<>();

        if (hideKeyboard) {
            KeyBoardManager keyBoardManager = new KeyBoardManager();
            keyBoardManager.hide(getActivity());
        }

        primaryCall.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                mutableLiveData.setValue(new ResponseModelManager().getResponse(getActivity(), response, isLoginRequest, ""));
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                mutableLiveData.setValue(new ResponseModelManager().getResponse(getActivity(), null, isLoginRequest, t.getMessage()));
            }
        });

        return mutableLiveData;
    }
    //______________________________________________________________________________________________ callRequest


    //______________________________________________________________________________________________ getActivity
    public Activity getActivity() {
        return activity;
    }
    //______________________________________________________________________________________________ getActivity

}
