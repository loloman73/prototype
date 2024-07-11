package xenagos.application.domain.xenagisi.entity

import java.text.DecimalFormat

data class Coordinates(val latitude: DecimalDegreeLat, val longitude: DecimalDegreeLon)


//precision of 6 digits
private const val PATTERN ="0.000000"
private const val LAT_MAX = 90
private const val LON_MAX = 180

data class DecimalDegreeLat (private val degValue:Double) {
   val degrees: Double
    init{
        if ((degValue> LAT_MAX) || (degValue<-LAT_MAX)) throw IllegalArgumentException("Out of Range")
        //degrees = degValue
        //TODO FIX problem with conversion
        degrees = DecimalFormat(PATTERN).format(degValue).toDouble()
    }
}

data class DecimalDegreeLon (private var degValue:Double) {
    val degrees: Double
    init{
        if ((degValue> LON_MAX) || (degValue<-LON_MAX)) throw IllegalArgumentException("Out of Range")
        //degrees = degValue
        degrees = DecimalFormat(PATTERN).format(degValue).toDouble()
    }
}