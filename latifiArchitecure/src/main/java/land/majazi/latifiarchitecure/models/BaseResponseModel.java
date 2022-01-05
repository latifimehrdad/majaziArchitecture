package land.majazi.latifiarchitecure.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import land.majazi.latifiarchitecure.enums.EnumStatusCode;

public class BaseResponseModel {


    @SerializedName(value = "name1", alternate = {"isSuccess", "IsSuccess"})
    @Expose
    private boolean isSuccess;

    @SerializedName(value = "name2", alternate = {"statusCode", "StatusCode"})
    @Expose
    private EnumStatusCode statusCode;

    @SerializedName(value = "name3", alternate = {"message", "Message"})
    @Expose
    private String message;

    @SerializedName("errors")
    @Expose
    private List<String> errors;


    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public EnumStatusCode getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(EnumStatusCode statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
