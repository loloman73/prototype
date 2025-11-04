package xenagos.application.port.input

import xenagos.application.port.input.TourDTO

interface GetToursForAreaUseCase {

    fun whereAreaIs(area:Int): List<TourDTO>?

}