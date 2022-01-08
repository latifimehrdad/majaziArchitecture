package land.majazi.latifiarchitecure.models;

public class PrimaryModel {
    int responseCode;
    boolean error;
    String message;
    Object responseBody;

    public PrimaryModel() {
    }

    public PrimaryModel(int responseCode, boolean error, String message, Object responseBody) {
        this.responseCode = responseCode;
        this.error = error;
        this.message = message;
        this.responseBody = responseBody;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(Object responseBody) {
        this.responseBody = responseBody;
    }
}
