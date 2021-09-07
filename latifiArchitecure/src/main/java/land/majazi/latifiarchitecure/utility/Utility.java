package land.majazi.latifiarchitecure.utility;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;
import land.majazi.latifiarchitecure.models.MD_GregorianDate;
import land.majazi.latifiarchitecure.models.MD_SolarDate;

public class Utility {


    private Validations validations;


    //______________________________________________________________________________________________ ApplicationUtility
    public Utility() {
        validations = new Validations();
    }
    //______________________________________________________________________________________________ ApplicationUtility


    //______________________________________________________________________________________________ persianToEnglish
    public String persianToEnglish(String persianStr) {

        if (persianStr == null || persianStr.isEmpty())
            return "";

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < persianStr.length(); i++) {
            char c = persianStr.charAt(i);
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
    //______________________________________________________________________________________________ persianToEnglish


    //______________________________________________________________________________________________ gregorianToSolarDate
    public MD_SolarDate gregorianToSolarDate(Date GregorianDate) {

/*        Type = "FullJalaliNumber = 1367/05/31"
          Type = "YearJalaliNumber = 1367"
          Type = "MonthJalaliNumber = 05"
          Type = "DayJalaliNumber = 31"
          Type = "FullJalaliString = پنجشنبه 31 مرداد 1367"
          Type = "MonthJalaliString = مرداد"
          Type = "DayJalaliString = پنجشنبه"*/

        String strWeekDay = "";
        String strMonth = "";

        int date;
        int month;
        int year;

        int ld;

        int miladiYear = GregorianDate.getYear() + 1900;
        int miladiMonth = GregorianDate.getMonth() + 1;
        int miladiDate = GregorianDate.getDate();
        int WeekDay = GregorianDate.getDay();

        int[] buf1 = new int[12];
        int[] buf2 = new int[12];

        buf1[0] = 0;
        buf1[1] = 31;
        buf1[2] = 59;
        buf1[3] = 90;
        buf1[4] = 120;
        buf1[5] = 151;
        buf1[6] = 181;
        buf1[7] = 212;
        buf1[8] = 243;
        buf1[9] = 273;
        buf1[10] = 304;
        buf1[11] = 334;

        buf2[0] = 0;
        buf2[1] = 31;
        buf2[2] = 60;
        buf2[3] = 91;
        buf2[4] = 121;
        buf2[5] = 152;
        buf2[6] = 182;
        buf2[7] = 213;
        buf2[8] = 244;
        buf2[9] = 274;
        buf2[10] = 305;
        buf2[11] = 335;

        if ((miladiYear % 4) != 0) {
            date = buf1[miladiMonth - 1] + miladiDate;

            if (date > 79) {
                date = date - 79;
                if (date <= 186) {
                    switch (date % 31) {
                        case 0:
                            month = date / 31;
                            date = 31;
                            break;
                        default:
                            month = (date / 31) + 1;
                            date = (date % 31);
                            break;
                    }
                    year = miladiYear - 621;
                } else {
                    date = date - 186;

                    switch (date % 30) {
                        case 0:
                            month = (date / 30) + 6;
                            date = 30;
                            break;
                        default:
                            month = (date / 30) + 7;
                            date = (date % 30);
                            break;
                    }
                    year = miladiYear - 621;
                }
            } else {
                if ((miladiYear > 1996) && (miladiYear % 4) == 1) {
                    ld = 11;
                } else {
                    ld = 10;
                }
                date = date + ld;

                switch (date % 30) {
                    case 0:
                        month = (date / 30) + 9;
                        date = 30;
                        break;
                    default:
                        month = (date / 30) + 10;
                        date = (date % 30);
                        break;
                }
                year = miladiYear - 622;
            }
        } else {
            date = buf2[miladiMonth - 1] + miladiDate;

            if (miladiYear >= 1996) {
                ld = 79;
            } else {
                ld = 80;
            }
            if (date > ld) {
                date = date - ld;

                if (date <= 186) {
                    switch (date % 31) {
                        case 0:
                            month = (date / 31);
                            date = 31;
                            break;
                        default:
                            month = (date / 31) + 1;
                            date = (date % 31);
                            break;
                    }
                    year = miladiYear - 621;
                } else {
                    date = date - 186;

                    switch (date % 30) {
                        case 0:
                            month = (date / 30) + 6;
                            date = 30;
                            break;
                        default:
                            month = (date / 30) + 7;
                            date = (date % 30);
                            break;
                    }
                    year = miladiYear - 621;
                }
            } else {
                date = date + 10;

                switch (date % 30) {
                    case 0:
                        month = (date / 30) + 9;
                        date = 30;
                        break;
                    default:
                        month = (date / 30) + 10;
                        date = (date % 30);
                        break;
                }
                year = miladiYear - 622;
            }

        }


        strMonth = getMonthTitle(month);

        strWeekDay = getDayTitle(WeekDay);

        Locale loc = new Locale("en_US");
        MD_SolarDate gregorianToSun = new MD_SolarDate();
        gregorianToSun.setStringYear(String.valueOf(year));
        gregorianToSun.setIntYear(year);
        gregorianToSun.setIntMonth(month);
        gregorianToSun.setIntDay(date);
        gregorianToSun.setDayOfWeek(strWeekDay);
        gregorianToSun.setMonthOfYear(strMonth);
        gregorianToSun.setStringMonth(String.format(loc, "%02d", month));
        gregorianToSun.setStringDay(String.format(loc, "%02d", date));

        return gregorianToSun;
    }
    //______________________________________________________________________________________________ gregorianToSolarDate


    //______________________________________________________________________________________________ solarDate_to_gregorian
    public MD_GregorianDate solarDateToGregorian(String solarDate) {

        if (solarDate == null)
            return null;

        if (solarDate.length() != 10)
            return null;

        int jy = Integer.parseInt(solarDate.substring(0, 4));
        int jm = Integer.parseInt(solarDate.substring(5, 7));
        int jd = Integer.parseInt(solarDate.substring(8, 10));
        jy += 1595;
        int[] out = {
                0,
                0,
                -355668 + (365 * jy) + (((int) (jy / 33)) * 8) + ((int) (((jy % 33) + 3) / 4)) + jd + ((jm < 7) ? (jm - 1) * 31 : ((jm - 7) * 30) + 186)
        };
        out[0] = 400 * ((int) (out[2] / 146097));
        out[2] %= 146097;
        if (out[2] > 36524) {
            out[0] += 100 * ((int) (--out[2] / 36524));
            out[2] %= 36524;
            if (out[2] >= 365) out[2]++;
        }
        out[0] += 4 * ((int) (out[2] / 1461));
        out[2] %= 1461;
        if (out[2] > 365) {
            out[0] += (int) ((out[2] - 1) / 365);
            out[2] = (out[2] - 1) % 365;
        }
        int[] sal_a = {0, 31, ((out[0] % 4 == 0 && out[0] % 100 != 0) || (out[0] % 400 == 0)) ? 29 : 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        for (out[2]++; out[1] < 13 && out[2] > sal_a[out[1]]; out[1]++) out[2] -= sal_a[out[1]];

        return new MD_GregorianDate(out[0], out[1], out[2]);
    }
    //______________________________________________________________________________________________ solarDate_to_gregorian


    //______________________________________________________________________________________________ getFullSolarDateFromSolarDate
    public String getFullSolarDateFromSolarDate(String solarDate) {

        if (solarDate.length() != 10)
            return solarDate;

        return gregorianToSolarDate(solarDateToGregorian(solarDate).getDate()).getFullStringSolarDate();
    }
    //______________________________________________________________________________________________ getFullSolarDateFromSolarDate


    //______________________________________________________________________________________________ getMonthTitle
    public String getMonthTitle(int month) {

        String strMonth = "";

        switch (month) {
            case 1:
                strMonth = "فروردين";
                break;
            case 2:
                strMonth = "ارديبهشت";
                break;
            case 3:
                strMonth = "خرداد";
                break;
            case 4:
                strMonth = "تير";
                break;
            case 5:
                strMonth = "مرداد";
                break;
            case 6:
                strMonth = "شهريور";
                break;
            case 7:
                strMonth = "مهر";
                break;
            case 8:
                strMonth = "آبان";
                break;
            case 9:
                strMonth = "آذر";
                break;
            case 10:
                strMonth = "دي";
                break;
            case 11:
                strMonth = "بهمن";
                break;
            case 12:
                strMonth = "اسفند";
                break;
        }

        return strMonth;
    }
    //______________________________________________________________________________________________ getMonthTitle


    //______________________________________________________________________________________________ getDayTitle
    public String getDayTitle(int weekDay) {

        String strWeekDay = "";

        switch (weekDay) {

            case 0:
                strWeekDay = "يکشنبه";
                break;
            case 1:
                strWeekDay = "دوشنبه";
                break;
            case 2:
                strWeekDay = "سه شنبه";
                break;
            case 3:
                strWeekDay = "چهارشنبه";
                break;
            case 4:
                strWeekDay = "پنج شنبه";
                break;
            case 5:
                strWeekDay = "جمعه";
                break;
            case 6:
                strWeekDay = "شنبه";
                break;
        }

        return strWeekDay;
    }
    //______________________________________________________________________________________________ getDayTitle


    //______________________________________________________________________________________________ setTextWatcherSplitting
    public TextWatcher setTextWatcherSplitting(final EditText editText, final double amount) {

        final String[] value = new String[2];

        return new TextWatcher() {
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                value[0] = editText.getText().toString();
            }

            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                value[1] = charSequence.toString();
            }

            public void afterTextChanged(Editable editable) {

                if (value[0] == null || value[0].equalsIgnoreCase(""))
                    value[0] = "0";

                if (value[1] == null || value[1].equalsIgnoreCase(""))
                    value[1] = "0";

                value[0] = value[0].replaceAll(",", "");
                value[0] = value[0].replaceAll("٬", "");

                value[1] = value[1].replaceAll(",", "");
                value[1] = value[1].replaceAll("٬", "");


                if (value[1].equalsIgnoreCase("0")) {
                    editText.removeTextChangedListener(this);
                    editText.setText("");
                    editText.addTextChangedListener(this);
                    return;
                }

                if (amount >= Long.valueOf(value[1])) {
                    String m = value[1];
                    m = m.replaceAll(",", "");
                    m = m.replaceAll("٬", "");
                    if (!m.equalsIgnoreCase("")) {
                        editText.removeTextChangedListener(this);
                        editText.setText(splitNumberOfAmount(Long.valueOf(m)));
                        editText.addTextChangedListener(this);
                    }
                } else {
                    editText.removeTextChangedListener(this);
                    editText.setText(splitNumberOfAmount(Long.valueOf(value[0])));
                    editText.addTextChangedListener(this);
                }

                editText.setSelection(editText.getText().length());

            }
        };
    }
    //______________________________________________________________________________________________ setTextWatcherSplitting


    //______________________________________________________________________________________________ splitNumberOfAmount
    public String splitNumberOfAmount(Long amount) {
        NumberFormat formatter = new DecimalFormat("#,###");
        return formatter.format(amount);
    }
    //______________________________________________________________________________________________ splitNumberOfAmount


    //______________________________________________________________________________________________ splitNumberOfString
    public String splitNumberOfString(String value) {
        if (value == null || value.isEmpty())
            return null;

        String temp = value.replaceAll(",", "");
        temp = temp.replaceAll("٬", "");
        if (!getValidations().stringIsDigit(temp))
            return value;

        return splitNumberOfAmount(Long.valueOf(temp));
    }
    //______________________________________________________________________________________________ splitNumberOfString


    //______________________________________________________________________________________________ solarDateBetween
    public Integer solarDateBetween(String Date1, String Date2, Integer intDate1, Integer intDate2) {
        Integer DateStart;
        Integer DateEnd;
        int c1;
        int b2;
        int c2;
        int b1;
        int a1;
        int a2;
        if (intDate1 != null) {
            DateStart = intDate1;
            DateEnd = intDate2;
        } else if (Date1.length() != 10) {
            return 0;
        } else {
            DateStart = Integer.valueOf(Date1.replaceAll("/", ""));
            DateEnd = Integer.valueOf(Date2.replaceAll("/", ""));
        }
        if (DateStart == 0 || DateEnd == 0) {
            return 0;
        }
        String DateStartString = String.valueOf(DateStart);
        String DateEndString = String.valueOf(DateEnd);
        if (DateStart > DateEnd) {
            a1 = Integer.parseInt(DateStartString.substring(0, 4));
            b1 = Integer.parseInt(DateStartString.substring(4, 6));
            c1 = Integer.parseInt(DateStartString.substring(6, 8));
            a2 = Integer.parseInt(DateEndString.substring(0, 4));
            b2 = Integer.parseInt(DateEndString.substring(4, 6));
            c2 = Integer.parseInt(DateEndString.substring(6, 8));
        } else {
            a1 = Integer.parseInt(DateEndString.substring(0, 4));
            b1 = Integer.parseInt(DateEndString.substring(4, 6));
            c1 = Integer.parseInt(DateEndString.substring(6, 8));
            a2 = Integer.parseInt(DateStartString.substring(0, 4));
            b2 = Integer.parseInt(DateStartString.substring(4, 6));
            c2 = Integer.parseInt(DateStartString.substring(6, 8));
        }
        int B = 0;
        int d = b2;
        while (a2 < a1) {
            while (d <= 12) {
                B += numberOfDaysPerMonth(d, a2);
                d++;
            }
            a2++;
            d = 1;
        }
        while (d < b1) {
            B += numberOfDaysPerMonth(d, a1);
            d++;
        }
        return (B + c1) - c2;
    }
    //______________________________________________________________________________________________ solarDateBetween


    //______________________________________________________________________________________________ solarDateAddDay
    public String solarDateAddDay(String Date1, Integer intDate1, int day) {
        Integer DateStart;
        if (intDate1 != null) {
            DateStart = intDate1;
        } else if (Date1.length() != 10) {
            return null;
        } else {
            DateStart = Integer.valueOf(Date1.replaceAll("/", ""));
        }
        if (DateStart == 0) {
            return null;
        }
        int a1 = Integer.parseInt(String.valueOf(DateStart).substring(0, 4));
        int b1 = Integer.parseInt(String.valueOf(DateStart).substring(4, 6));
        int c1 = Integer.parseInt(String.valueOf(DateStart).substring(6, 8));
        int day2 = day + c1;
        while (day2 > 0) {
            int temp = numberOfDaysPerMonth(b1, a1);
            if (day2 >= temp) {
                day2 -= temp;
                b1++;
                c1 = 0;
            } else {
                c1 = day2;
                day2 = 0;
            }
            if (b1 > 12) {
                a1++;
                b1 = 1;
            }
        }

        if (c1 == 0) {
            b1--;
            c1 = numberOfDaysPerMonth(b1, a1);
        }

        StringBuilder sb = new StringBuilder();
        sb.append(String.valueOf(a1));
        sb.append("/");
        String str2 = "%02d";
        sb.append(String.format(str2, new Object[]{Integer.valueOf(b1)}));
        sb.append("/");
        sb.append(String.format(str2, new Object[]{Integer.valueOf(c1)}));
        return sb.toString();
    }
    //______________________________________________________________________________________________ solarDateAddDay


    //______________________________________________________________________________________________ solarDateReduceDay
    public String solarDateReduceDay(String Date1, Integer intDate1, int day) {
        Integer DateStart;
        if (intDate1 != null) {
            DateStart = intDate1;
        } else if (Date1.length() != 10 || Date1.length() != 10) {
            return null;
        } else {
            DateStart = Integer.valueOf(Date1.replaceAll("/", ""));
        }
        if (DateStart.intValue() == 0) {
            return null;
        }
        int a1 = Integer.parseInt(String.valueOf(DateStart).substring(0, 4));
        int b1 = Integer.parseInt(String.valueOf(DateStart).substring(4, 6));
        int day2 = day - Integer.parseInt(String.valueOf(DateStart).substring(6, 8));
        while (day2 > 0) {
            b1--;
            day2 -= numberOfDaysPerMonth(b1, a1);
            if (b1 == 1) {
                b1 = 13;
                a1--;
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append(String.valueOf(a1));
        sb.append("/");
        String str2 = "%02d";
        sb.append(String.format(str2, new Object[]{Integer.valueOf(b1)}));
        sb.append("/");
        if (day2 < 0)
            sb.append(String.format(str2, new Object[]{Integer.valueOf(day2 * -1)}));
        else
            sb.append("01");
        return sb.toString();
    }
    //______________________________________________________________________________________________ solarDateReduceDay


    //______________________________________________________________________________________________ numberOfDaysPerMonth
    private int numberOfDaysPerMonth(int d, int year) {
        switch (d) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                return 31;
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
                return 30;
            case 12:
                if (leapYear(year)) {
                    return 30;
                }
                return 29;
            default:
                return 0;
        }
    }
    //______________________________________________________________________________________________ numberOfDaysPerMonth


    //______________________________________________________________________________________________ leapYear
    private Boolean leapYear(int year) {
        int temp = year % 33;
        if (temp == 1 || temp == 5 || temp == 9 || temp == 13 || temp == 17 || temp == 22 || temp == 26 || temp == 30) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
    //______________________________________________________________________________________________ leapYear



    //______________________________________________________________________________________________ getValidations
    public Validations getValidations() {
        return validations;
    }
    //______________________________________________________________________________________________ getValidations


    //______________________________________________________________________________________________ hideKeyboard
    public void hideKeyboard(Activity activity) {
        if (activity != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            View view = activity.getCurrentFocus();
            if (view == null) {
                view = new View(activity);
            }
            if (imm != null)
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    //______________________________________________________________________________________________ hideKeyboard


    //______________________________________________________________________________________________ isInternetConnected
    @SuppressLint("MissingPermission")
    public boolean isInternetConnected(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork == null)
            return false;

        return activeNetwork.isConnectedOrConnecting();
    }
    //______________________________________________________________________________________________ isInternetConnected


    //______________________________________________________________________________________________ isLocationEnabled
    public boolean isLocationEnabled(Context context) {
        int locationMode;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }
    //______________________________________________________________________________________________ isLocationEnabled


    //______________________________________________________________________________________________ turnOnLocation
    public void turnOnLocation(Activity activity) {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        activity.startActivity(intent);
    }
    //______________________________________________________________________________________________ turnOnLocation


    //______________________________________________________________________________________________ getString
    public String getString(String value) {
        if (value == null)
            return "";
        else
            return value;
    }
    //______________________________________________________________________________________________ getString



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


}
