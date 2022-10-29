package land.majazi.latifiarchitecure.utility

import androidx.core.text.isDigitsOnly
import java.math.BigInteger

class Validator {

    fun isText(text: String): Boolean{
        return !text.isNullOrBlank()
    }

    fun isMobile(mobile: String): Boolean {
        val regex = Regex("^(09)\\d{9}")
        return mobile.matches(regex)
    }

    fun isPhone(phone: String): Boolean {
        val regex = Regex("^(0)([1-8])\\d{9}")
        return phone.matches(regex)
    }

    fun isEmail(email: String): Boolean {
        val regex = Regex(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
        )
        return email.matches(regex)
    }

    fun isNationalCode(nationalCode: String): Boolean {
        if (nationalCode.isNullOrEmpty())
            return false

        if (nationalCode.length != 10 || !nationalCode.isDigitsOnly())
            return false

        val revers = nationalCode.reversed()
        val arrayNationalCode: Array<Int> =
            revers.toCharArray().map { it.toString().toInt() }.toTypedArray()

        var sum = 0
        for (i in 9 downTo 1)
            sum += arrayNationalCode[i]!! * (i + 1)
        val temp = sum % 11
        return if (temp < 2)
            arrayNationalCode[0] == temp
        else
            arrayNationalCode[0] == 11 - temp
    }

    fun isStrongPassword(password: String): Boolean {
        val regex = Regex("^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=.*[@#\$%^&+=!]).{6,}\$")
        return password.matches(regex)
    }

    fun isSignsInPassword(password: String): Boolean {
        val regex = Regex("^(?=.*[@#\$%^&+=!]).{1,}\$")
        return password.matches(regex)
    }

    fun isUppercaseInPassword(password: String): Boolean {
        val regex = Regex("^(?=.*[A-Z]).{1,}\$")
        return password.matches(regex)
    }

    fun isNumberInPassword(password: String): Boolean {
        val regex = Regex("^(?=.*[0-9]).{1,}\$")
        return password.matches(regex)
    }

    fun isLowerInPassword(password: String): Boolean {
        val regex = Regex("^(?=.*[a-z]).{1,}\$")
        return password.matches(regex)
    }

    fun isShaba(shaba: String): Boolean {

        if (shaba.isNullOrEmpty())
            return false;

        if (shaba.length != 24)
            return false

        var place1 = "IR" + shaba.subSequence(0,2).toString()
        var place2 = shaba.subSequence(2, 24).toString()
        place1 = place1.replace("IR","1827", true)
        place2+=place1
        var big = place2.toBigInteger()
        return big.mod(BigInteger.valueOf(97)).toInt() == 1
    }

    fun isPriceForGetWay(price: String): Boolean {
        if (price.isNullOrEmpty())
            return false
        val p = price.replace(",","",false).replace("٬","",false)
        if (!p.isDigitsOnly())
            return false
        val priceLong = p.toLong()
        return !(priceLong < 1000 || priceLong > 49900000)
    }

    fun isPostalCode(postalCode: String): Boolean {
        val regex = Regex("\\b(?!(\\d)\\1{3})[13-9]{4}[1346-9][013-9]{5}\\b")
        return postalCode.matches(regex)
    }

    fun isPersianName(name: String): Boolean {
        val regex = Regex("^(?=.*[آ-ی]).{1,}\$")
        return name.matches(regex)
    }

    fun isCardNumber(cardNumber: String): Boolean {
        if (cardNumber.isNullOrEmpty())
            return false

        if (cardNumber.length != 16 || !cardNumber.isDigitsOnly())
            return false

        val arrayCard: Array<Int> =
            cardNumber.toCharArray().map { it.toString().toInt() }.toTypedArray()

        var cardTotal = 0

        for (i in arrayCard.indices)
            cardTotal += if (i % 2 == 0)
                if (arrayCard[i] * 2 > 9) arrayCard[i] * 2 - 9 else arrayCard[i] * 2
             else
                arrayCard[i]
        return cardTotal % 10 == 0
    }
}