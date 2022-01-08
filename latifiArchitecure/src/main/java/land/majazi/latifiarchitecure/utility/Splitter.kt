package land.majazi.latifiarchitecure.utility

import androidx.core.text.isDigitsOnly
import java.text.DecimalFormat

class Splitter {

    //______________________________________________________________________________________________ split
    fun split(value : String) : String{
        if (value.isNullOrEmpty())
            return ""
        var s = value.replace(",","",false).replace("٬","",false)
        return if (s.isDigitsOnly())
            split(s.toInt())
        else
            ""
    }
    //______________________________________________________________________________________________ split

    //______________________________________________________________________________________________ split
    fun split(value : Int) :String {
        val dec = DecimalFormat("#,###")
        return dec.format(value)
    }
    //______________________________________________________________________________________________ split

}