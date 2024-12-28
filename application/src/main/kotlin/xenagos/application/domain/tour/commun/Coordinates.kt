package xenagos.application.domain.tour.commun

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

data class Coordinates(val latitude: DecimalDegreeLat, val longitude: DecimalDegreeLon)

private const val PATTERN ="0.000000"  //precision of 6 digits
private const val LAT_MAX = 90
private const val LON_MAX = 180
private const val DECIMAL_SEPARATOR = '.'

fun formatDoubleToDecimalDegree(degValue:Double):Double{
    val dfs = DecimalFormatSymbols.getInstance()
    dfs.setDecimalSeparator(DECIMAL_SEPARATOR)
    val df = DecimalFormat(PATTERN, dfs)
    return df.format(degValue).toDouble()
}

data class DecimalDegreeLat (private val doubleValue:Double) {
   val degrees: Double
    init{
        if ((doubleValue> LAT_MAX) || (doubleValue<-LAT_MAX)) throw IllegalArgumentException("Out of Range")
        degrees = formatDoubleToDecimalDegree(doubleValue)
    }
}

data class DecimalDegreeLon (private var doubleValue:Double) {
    val degrees: Double
    init{
        if ((doubleValue> LON_MAX) || (doubleValue<-LON_MAX)) throw IllegalArgumentException("Out of Range")
        degrees = formatDoubleToDecimalDegree(doubleValue)
    }
}