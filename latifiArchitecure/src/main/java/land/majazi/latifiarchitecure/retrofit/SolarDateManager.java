package land.majazi.latifiarchitecure.manager;

public class SolarDateManager {


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


}
