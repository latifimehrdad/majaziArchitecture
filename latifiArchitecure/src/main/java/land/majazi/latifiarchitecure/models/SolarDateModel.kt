package land.majazi.latifiarchitecure.models

import java.time.DayOfWeek

class SolarDateModel(private val year: Int,private val month: Int,private val day: Int, private  val dayOfWeek: DayOfWeek) {

    fun getSolarDate(): String {
        return "${getYearString()}/${getMonthString()}/${getDayString()}"
    }

    fun getFullDate() : String {
        return getDayOfWeekTitle() + " " + getDayString() + " " + getMonthTitle() + " " + getYearString()
    }

    fun getMonthTitle() : String {
        return when (month) {
            1 -> "فروردین"
            2 -> "ارديبهشت"
            3 -> "خرداد"
            4 -> "تير"
            5 -> "مرداد"
            6 -> "شهريور"
            7 -> "مهر"
            8 -> "آبان"
            9 -> "آذر"
            10 -> "دي"
            11 -> "بهمن"
            12 -> "اسفند"
            else -> ""
        }
    }


    fun getDayOfWeekTitle() : String {
        return when (dayOfWeek) {
            DayOfWeek.SUNDAY -> "یکشنبه"
            DayOfWeek.MONDAY -> "دوشنبه"
            DayOfWeek.TUESDAY -> "سه شنبه"
            DayOfWeek.THURSDAY -> "چهارشنبه"
            DayOfWeek.WEDNESDAY -> "پنج شنبه"
            DayOfWeek.FRIDAY -> "جمعه"
            DayOfWeek.SATURDAY -> "شنبه"
            else -> ""
        }
    }


    fun getYear() : Int {
        return year
    }

    fun getYearString() : String {
        return year.toString()
    }

    fun getMonth() : Int {
        return month
    }

    fun getMonthString() : String {
        return month.toString().padStart(2, '0')
    }

    fun getDay() : Int {
        return day
    }

    fun getDayString() : String {
        return day.toString().padStart(2, '0')
    }


}