package xenagos.application.domain.xenagisi.entities

import java.text.DecimalFormat

data class Coordinates(val latitude: DecimalDegreeLat, val longitude: DecimalDegreeLon)


//precision of 6 digits
private const val PATTERN ="0.000000"
private const val LAT_MAX = 90
private const val LON_MAX = 180

data class DecimalDegreeLat (private val value:Double) {
   val degrees: Double
    init{
        if ((value> LAT_MAX) || (value<-LAT_MAX)) throw IllegalArgumentException("Out of Range")
        degrees = value
        //TODO FIX problem with conversion
        //degrees = DecimalFormat(PATTERN).format(value).toDouble()
    }
}

data class DecimalDegreeLon (var value:Double) {
    init{
        if ((value> LON_MAX) || (value<-LON_MAX)) throw IllegalArgumentException("Out of Range")
    }
    //val degrees = DecimalFormat(PATTERN).format(value).toDouble()
}