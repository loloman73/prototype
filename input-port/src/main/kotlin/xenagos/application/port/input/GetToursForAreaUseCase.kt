package xenagos.application.port.input

interface GetToursForAreaUseCase {

    fun whereAreaIs(area:Int): List<TourDTO>?

}