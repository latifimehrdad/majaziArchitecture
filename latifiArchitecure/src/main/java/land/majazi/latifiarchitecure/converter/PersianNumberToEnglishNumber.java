package land.majazi.latifiarchitecure.converter;

public class PersianNumberToEnglishNumber {

    private final String persianNumber;

    //______________________________________________________________________________________________ PersianNumberToEnglishNumber
    public PersianNumberToEnglishNumber(String persianNumber) {
        this.persianNumber = persianNumber;
    }
    //______________________________________________________________________________________________ PersianNumberToEnglishNumber


    //______________________________________________________________________________________________ convert
    public String convert() {

        if (persianNumber == null || persianNumber.isEmpty())
            return "";

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < persianNumber.length(); i++) {
            char c = persianNumber.charAt(i);
            switch (c) {
                case '۰':
                    result.append("0");
                    break;
                case '۱':
                    result.append("1");
                    break;
                case '۲':
                    result.append("2");
                    break;
                case '۳':
                    result.append("3");
                    break;
                case '۴':
                    result.append("4");
                    break;
                case '۵':
                    result.append("5");
                    break;
                case '۶':
                    result.append("6");
                    break;
                case '۷':
                    result.append("7");
                    break;
                case '۸':
                    result.append("8");
                    break;
                case '۹':
                    result.append("9");
                    break;
                default:
                    result.append(c);
                    break;
            }
        }
        return result.toString();

    }
    //______________________________________________________________________________________________ convert

}
