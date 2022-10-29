package land.majazi.latifiarchitecure.utility

import androidx.core.text.isDigitsOnly
import java.lang.Exception
import java.text.DecimalFormat

class Splitter {

    //______________________________________________________________________________________________ split
    fun split(value: String): String {
        if (value.isEmpty())
            return ""
        val s = value.replace(",", "", false).replace("٬", "", false)
        return if (s.isDigitsOnly())
            split(s.toLong())
        else
            try {
                val temp = s.toDouble()
                split(temp)
            } catch (e: Exception) {
                ""
            }
    }
    //______________________________________________________________________________________________ split

    //______________________________________________________________________________________________ split
    fun split(value: Long): String {
        val dec = DecimalFormat("#,###")
        return dec.format(value)
    }
    //______________________________________________________________________________________________ split


    //______________________________________________________________________________________________ split
    fun split(value: Double): String {
        val dec = DecimalFormat("#,###.########")
        return dec.format(value)
    }
    //______________________________________________________________________________________________ split

}