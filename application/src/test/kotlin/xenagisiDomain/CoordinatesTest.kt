package xenagisiDomain

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import xenagos.application.entity.commun.DecimalDegreeLat
import xenagos.application.entity.commun.DecimalDegreeLon
import kotlin.test.assertEquals

class CoordinatesTest {


    //LATITUDE - DecimalDegreeLat class test

    @Test
    fun `Lat enter in-range value Should return same number`(){
        val ddLat = DecimalDegreeLat(63.0)
        assertEquals(63.0,ddLat.degrees,0.0)
    }
    @Test
    fun `Lat enter range limit value 90 Should return same number`(){
        val ddLat = DecimalDegreeLat(90.0)
        assertEquals(90.0,ddLat.degrees,0.0)
    }
    @Test
    fun `Lat enter range limit value -90 Should return same number`(){
        val ddLat = DecimalDegreeLat(-90.0)
        assertEquals(-90.0,ddLat.degrees,0.0)
    }
    @Test
    fun `Lat enter out of range value 90,0001 Should throw exception`(){
        assertThrows<IllegalArgumentException> { DecimalDegreeLat(90.0001) }
    }
    @Test
    fun `Lat enter number with 7 decimal digits Should convert it to 6 decimal digits with rounding`(){
        val ddLatDown = DecimalDegreeLat(45.1234561)
        val ddLatUp = DecimalDegreeLat(45.1234568)
        assertEquals(45.12345600,ddLatDown.degrees,0.0)
        assertEquals(45.12345700,ddLatUp.degrees,0.0)
    }



    //LONGITUDE - DecimalDegreeLon class test

    @Test
    fun `Lon enter in-range value Should return same number`(){
    val ddLon = DecimalDegreeLon(163.0)
    assertEquals(163.0,ddLon.degrees,0.0)
}
    @Test
    fun `Lon enter range limit value 180 Should return same number`(){
        val ddLon = DecimalDegreeLon(180.0)
        assertEquals(180.0,ddLon.degrees,0.0)
    }
    @Test
    fun `Lon enter range limit value -180 Should return same number`(){
        val ddLon = DecimalDegreeLon(-180.0)
        assertEquals(-180.0,ddLon.degrees,0.0)
    }
    @Test
    fun `Lon enter out of range value 180,0001 Should throw exception`(){
        assertThrows<IllegalArgumentException> { DecimalDegreeLon(180.0001) }
    }
    @Test
    fun `Lon enter number with 7 decimal digits Should convert it to 6 decimal digits with rounding`(){
        val ddLonDown = DecimalDegreeLon(145.1234561)
        val ddLonUp = DecimalDegreeLon(145.1234568)
        assertEquals(145.12345600,ddLonDown.degrees,0.0)
        assertEquals(145.12345700,ddLonUp.degrees,0.0)
    }


}