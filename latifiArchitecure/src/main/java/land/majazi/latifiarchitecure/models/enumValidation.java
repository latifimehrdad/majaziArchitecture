package land.majazi.latifiarchitecure.models;

public enum enumValidation {
    none(0),
    mobile(1),
    text(2),
    email(3),
    national(4),
    password(5),
    mobileWithoutAreaCode(6),
    phone(7),
    wallet(10),
    shaba(11),
    price(12),
    numberInteger(13),
    numberDecimal(14),
    postalCode(15),
    cardNumber(16);

    private int value;

    enumValidation(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
