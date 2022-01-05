package land.majazi.latifiarchitecure.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class GregorianDateModel {

    int year;
    int month;
    int day;

    public GregorianDateModel(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }


    public String getDateString(String splitter) {
        Locale loc = new Locale("en_US");
        String date = year + splitter + String.format(loc, "%02d", month) + splitter + String.format(loc, "%02d", day);
        return date;
    }


    public Date getDate() {

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            Date d = sdf.parse(getDateString("/"));
            return d;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }


    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }


}
