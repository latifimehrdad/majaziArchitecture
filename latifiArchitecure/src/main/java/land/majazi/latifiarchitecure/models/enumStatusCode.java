package land.majazi.latifiarchitecure.models;

public enum enumStatusCode {
    Success(0),
    ServerError(1),
    BadRequest(2),
    NotFound(3),
    ListEmpty(4),
    LogicError(5),
    UnAuthorized(6);

    private int value;

    enumStatusCode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
