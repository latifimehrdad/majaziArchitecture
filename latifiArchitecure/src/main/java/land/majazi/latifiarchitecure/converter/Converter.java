package land.majazi.latifiarchitecure.converter;

import java.time.LocalDateTime;

import land.majazi.latifiarchitecure.models.SolarDateModel;

public class Converter {

    //______________________________________________________________________________________________ PersianNumberToEnglishNumber
    public String PersianNumberToEnglishNumber(String persianNumber) {
        return new PersianNumberToEnglishNumber(persianNumber).convert();
    }
    //______________________________________________________________________________________________ PersianNumberToEnglishNumber


    //______________________________________________________________________________________________ GregorianDateToSolarDate
    public SolarDateModel GregorianDateToSolarDate(LocalDateTime gregorianDate) {
        return new GregorianDateToSolarDate(gregorianDate).convert();
    }
    //______________________________________________________________________________________________ GregorianDateToSolarDate


    //______________________________________________________________________________________________ SolarDateToGregorianDate
    public LocalDateTime SolarDateToGregorianDate(String solarDate) {
        return new SolarDateToGregorianDate(solarDate).convert();
    }
    //______________________________________________________________________________________________ SolarDateToGregorianDate

}
