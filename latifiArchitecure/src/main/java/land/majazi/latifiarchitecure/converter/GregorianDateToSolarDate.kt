package land.majazi.latifiarchitecure.converter

import land.majazi.latifiarchitecure.models.SolarDateModel
import java.time.LocalDateTime

class GregorianDateToSolarDate(private val gregorianDate: LocalDateTime) {

    //______________________________________________________________________________________________ convert
    fun convert(): SolarDateModel {

        var solarDay: Int
        var solarMonth: Int
        var solarYear: Int
        var ld: Int

        val gregorianYear = gregorianDate.year
        val gregorianMonth = gregorianDate.monthValue
        val gregorianDay = gregorianDate.dayOfMonth
        val dayOfWeek = gregorianDate.dayOfWeek

        var buf1 = arrayOf(0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334)
        var buf2 = arrayOf(0, 31, 60, 91, 121, 152, 182, 213, 244, 274, 305, 335)

        if (gregorianYear % 4 != 0) {
            solarDay = buf1[gregorianMonth - 1] + gregorianDay
            if (solarDay > 79) {
                solarDay -= 79
                if (solarDay <= 186) {
                    when (solarDay % 31) {
                        0 -> {
                            solarMonth = solarDay / 31
                            solarDay = 31
                        }
                        else -> {
                            solarMonth = (solarDay / 31) + 1
                            solarDay = (solarDay % 31)
                        }
                    }
                    solarYear = gregorianYear - 621
                } else {
                    solarDay -= 186
                    when (solarDay % 30) {
                        0 -> {
                            solarMonth = (solarDay / 30) + 6
                            solarDay = 30
                        }
                        else -> {
                            solarMonth = (solarDay / 30) + 7
                            solarDay = (solarDay % 30)
                        }
                    }
                    solarYear = gregorianYear - 621
                }
            } else {
                ld = if (gregorianYear > 1996 && gregorianYear % 4 == 1)
                    11
                else
                    10
                solarDay += ld
                when (solarDay % 30) {
                    0 -> {
                        solarMonth = (solarDay / 30) + 9
                        solarDay = 30
                    }
                    else -> {
                        solarMonth = (solarDay / 30) + 10
                        solarDay %= 30
                    }
                }
                solarYear = gregorianYear - 622
            }
        }
        else {
            solarDay = buf2[gregorianMonth - 1] + gregorianDay
            ld = if (gregorianYear >= 1996)
                79
            else
                80
            if (solarDay > ld) {
                solarDay -= ld
                if (solarDay <= 186) {
                    when (solarDay % 31) {
                        0 -> {
                            solarMonth = (solarDay / 31)
                            solarDay = 31
                        }
                        else -> {
                            solarMonth = (solarDay / 31) + 1
                            solarDay %= 31
                        }
                    }
                    solarYear = gregorianYear - 621
                } else {
                    solarDay -= 186
                    when (solarDay % 30) {
                        0 -> {
                            solarMonth = (solarDay / 30) + 6
                            solarDay %= 30
                        }
                        else -> {
                            solarMonth = (solarDay / 30) + 7
                            solarDay %= 30
                        }
                    }
                    solarYear = gregorianYear - 621
                }
            }
            else {
                solarDay += 10
                when (solarDay % 30) {
                    0 -> {
                        solarMonth = (solarDay / 30) + 10
                        solarDay %= 30
                    }
                    else -> {
                        solarMonth = (solarDay / 30) + 10
                        solarDay %= 30
                    }
                }
                solarYear = gregorianYear - 622
            }

        }

        return SolarDateModel(solarYear, solarMonth, solarDay, dayOfWeek);
    }
    //______________________________________________________________________________________________ convert

}