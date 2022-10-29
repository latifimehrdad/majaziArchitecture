package land.majazi.latifiarchitecure.converter

import java.time.LocalDateTime

class SolarDateToGregorianDate(private val solarDate: String) {

    //______________________________________________________________________________________________ convert
    fun convert(): LocalDateTime? {

        if (solarDate.isNullOrEmpty())
            return null

        if (solarDate.length != 10)
            return null

        var solarYear = solarDate.subSequence(0, 4).toString().toInt()
        val solarMonth = solarDate.subSequence(5, 7).toString().toInt()
        val solarDay = solarDate.subSequence(8, 10).toString().toInt()

        solarYear += 1595
        var d =
            -355668 + (365 * solarYear) + ((solarYear / 33)* 8) + (((solarYear % 33) + 3) / 4) + solarDay
        d += if (solarMonth < 7)
            (solarMonth - 1) * 31
        else
            ((solarMonth - 7 ) * 30) + 186

        var out = arrayOf(0, 0, d)

        out[0] = 400 * (out[2] / 146097)
        out[2] %= 146097
        if (out[2] > 36524) {
            out[0] += 100 * (--out[2] / 36524)
            out[2] %= 36524
            if (out[2] >= 365)
                out[2]++
        }
        out[0] += 4 * (out[2] / 1461)
        out[2] %= 1461
        if (out[2] > 365) {
            out[0] += (out[2] - 1) / 365
            out[2] = (out[2] - 1) % 365
        }
        val sal_a = arrayOf(
            0,
            31,
            if (out[0] % 4 == 0 && out[0] % 100 != 0 || out[0] % 400 == 0) 29 else 28,
            31,
            30,
            31,
            30,
            31,
            31,
            30,
            31,
            30,
            31
        )
        out[2]++
        while (out[1] < 13 && out[2] > sal_a.get(out[1])) {
            out[2] -= sal_a.get(out[1])
            out[1]++
        }
        return LocalDateTime.of(out[0], out[1], out[2], 0, 0)
    }
    //______________________________________________________________________________________________ convert
}