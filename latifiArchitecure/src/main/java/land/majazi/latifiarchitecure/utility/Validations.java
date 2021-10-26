package land.majazi.latifiarchitecure.utility;

import android.os.Build;
import android.util.Patterns;

import androidx.annotation.RequiresApi;

import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Validations {


    //______________________________________________________________________________________________ Validations
    public Validations() {
    }
    //______________________________________________________________________________________________ Validations


    //______________________________________________________________________________________________ mobileValidation
    public boolean mobileValidation(String mobile) {

        if (mobile == null)
            return false;

        if (mobile.isEmpty())
            return false;

        String zeroNine = mobile.subSequence(0, 2).toString();
        if (!zeroNine.equalsIgnoreCase("09"))
            return false;

        return mobile.length() == 11;

    }
    //______________________________________________________________________________________________ mobileValidation


    //______________________________________________________________________________________________ phoneValidation
    public boolean phoneValidation(String phone) {

        if (phone == null)
            return false;

        if (phone.isEmpty())
            return false;

        String zeroNine = phone.subSequence(0, 2).toString();
        if (zeroNine.equalsIgnoreCase("09"))
            return false;


        return phone.length() > 4;

    }
    //______________________________________________________________________________________________ phoneValidation


    //______________________________________________________________________________________________ mobileValidation
    public boolean mobileValidationWithoutAreaCode(String mobile) {

        if (mobile == null)
            return false;

        return mobile.length() == 9;
    }
    //______________________________________________________________________________________________ mobileValidation


    //______________________________________________________________________________________________ textValidation
    public boolean textValidation(String text) {

        if (text == null)
            return false;

        return !text.isEmpty();
    }
    //______________________________________________________________________________________________ textValidation


    //______________________________________________________________________________________________ emailValidation
    public boolean emailValidation(String email) {

        if (email == null)
            return false;

        if (email.isEmpty())
            return false;

        return Patterns.EMAIL_ADDRESS.matcher(email).matches();

    }
    //______________________________________________________________________________________________ emailValidation


    //______________________________________________________________________________________________ nationalValidation
    public boolean nationalValidation(String national) {

        if (national == null)
            return false;

        if (national.length() != 10)
            return false;

        long nationalCode = Long.parseLong(national);
        byte[] arrayNationalCode = new byte[10];

        //extract digits from number
        for (int i = 0; i < 10; i++) {
            arrayNationalCode[i] = (byte) (nationalCode % 10);
            nationalCode = nationalCode / 10;
        }

        //Checking the control digit
        int sum = 0;
        for (int i = 9; i > 0; i--)
            sum += arrayNationalCode[i] * (i + 1);
        int temp = sum % 11;
        if (temp < 2)
            return arrayNationalCode[0] == temp;
        else
            return arrayNationalCode[0] == 11 - temp;

    }
    //______________________________________________________________________________________________ nationalValidation


    //______________________________________________________________________________________________ passwordValidation
    public boolean passwordValidation(String password) {

        if (password == null)
            return false;

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=.*[@#$%^&+=!]).{6,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();
    }
    //______________________________________________________________________________________________ passwordValidation


    //______________________________________________________________________________________________ signsPasswordValidation
    public boolean signsPasswordValidation(String password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[@#$%^&+=!]).{1,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();
    }
    //______________________________________________________________________________________________ signsPasswordValidation


    //______________________________________________________________________________________________ uppercasePasswordValidation
    public boolean uppercasePasswordValidation(String password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[A-Z]).{1,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();
    }
    //______________________________________________________________________________________________ uppercasePasswordValidation


    //______________________________________________________________________________________________ numberInPasswordValidation
    public boolean numberInPasswordValidation(String password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9]).{1,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();
    }
    //______________________________________________________________________________________________ numberInPasswordValidation



    //______________________________________________________________________________________________ UppercasePasswordValidation
    public boolean lowercasePasswordValidation(String password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[a-z]).{1,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();
    }
    //______________________________________________________________________________________________ UppercasePasswordValidation



    //______________________________________________________________________________________________ stringIsDigit
    public boolean stringIsDigit(String value) {

        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    //______________________________________________________________________________________________ stringIsDigit


    //______________________________________________________________________________________________ checkValues
    public boolean checkValues(Object value) {
        if (value == null)
            return false;
        else
            return true;
    }
    //______________________________________________________________________________________________ checkValues


/*
    //______________________________________________________________________________________________ decimalValidation
    public boolean decimalValidation(String count, int decimalCont) {
        if (count == null || count.isEmpty())
            return false;
        else {
            try {
                Double d = new Double(count);
                long l = d.longValue();
                Double decimalPart = d - l;
                decimalCont = decimalCont + 2;
                String d_s = String.valueOf(decimalPart);
                if (d_s.length() > decimalCont)
                    return false;
                else
                    return true;
            } catch (NumberFormatException ex) {
                return false;
            }
        }
    }
    //______________________________________________________________________________________________ decimalValidation


    //______________________________________________________________________________________________ integerCountValidation
    public boolean integerCountValidation(String count) {
        if (count == null || count.isEmpty())
            return false;
        else {
            try {
                long l = new Long(count);
                return true;
            } catch (NumberFormatException ex) {
                return false;
            }
        }
    }
    //______________________________________________________________________________________________ integerCountValidation
*/



    //______________________________________________________________________________________________ shabaValidation
    public boolean shabaValidation(String shaba) {
        if (shaba == null || shaba.isEmpty())
            return false;


        shaba = "IR" + shaba;

        if (shaba.length() != 26)
            return false;


        String fourNumber = shaba.substring(0, 4);
        shaba = shaba.substring(4, 26);
        fourNumber = fourNumber.replace("IR", "1827");
        shaba = shaba + fourNumber;
        BigInteger big = new BigInteger(shaba);
        BigInteger bigMode = new BigInteger("97");
        BigInteger divide = big.mod(bigMode);
        if (divide.intValue() == 1)
            return true;
        else
            return false;
    }
    //______________________________________________________________________________________________ shabaValidation


    //______________________________________________________________________________________________ priceValidation
    public boolean priceValidation(String price) {
        if (price == null || price.isEmpty())
            return false;

        price = price.replaceAll(",", "");
        price = price.replaceAll("٬", "");

        if (!stringIsDigit(price))
            return false;

        long priceLong = Long.valueOf(price);

        if (priceLong < 1000 || priceLong > 49900000)
            return false;

        return true;
    }
    //______________________________________________________________________________________________ priceValidation


    //______________________________________________________________________________________________ numberValidation
    public boolean numberValidation(String number) {

        Utility utility = new Utility();
        number = utility.persianToEnglish(number);

        if (number == null || number.isEmpty())
            return false;

        if (!stringIsDigit(number))
            return false;

        if (number.equalsIgnoreCase("0"))
            return false;

        return true;
    }
    //______________________________________________________________________________________________ numberValidation

    //______________________________________________________________________________________________ numberIsIntegerValidation
    public boolean numberIsIntegerValidation(String number) {

        if (!numberValidation(number))
            return false;

        Utility utility = new Utility();
        number = utility.persianToEnglish(number);

        double v = Double.valueOf(number);
        if (v != (long) v)
            return false;

        return true;
    }
    //______________________________________________________________________________________________ numberIsIntegerValidation


    //______________________________________________________________________________________________ postalCode
    public boolean postalCode(String postalCode) {

        if (!numberIsIntegerValidation(postalCode))
            return false;

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "\\b(?!(\\d)\\1{3})[13-9]{4}[1346-9][013-9]{5}\\b";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(postalCode);

        return matcher.matches();
    }
    //______________________________________________________________________________________________ postalCode



    //______________________________________________________________________________________________ CurdNumber
    public boolean cardNumber(String cardNumber) {

        if (!numberIsIntegerValidation(cardNumber))
            return false;


        if (cardNumber.length() != 16)
            return false;

        int cardTotal = 0;
        for (int i = 0; i < 16; i += 1) {
            int c = Character.getNumericValue(cardNumber.charAt(i));

            if (i % 2 == 0) {
                cardTotal += ((c * 2 > 9) ? (c * 2) - 9 : (c * 2));
            } else {
                cardTotal += c;
            }
        }

        return (cardTotal % 10 == 0);
    }
    //______________________________________________________________________________________________ CurdNumber


    //______________________________________________________________________________________________ persianNameValidation
    public boolean persianNameValidation(String text) {

        if (text == null)
            return false;

        if (text.isEmpty())
            return false;

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[آ-ی]).{1,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(text);

        return matcher.matches();
    }
    //______________________________________________________________________________________________ persianNameValidation


}
