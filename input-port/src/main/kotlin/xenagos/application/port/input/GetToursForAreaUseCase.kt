package xenagos.application.port.input

import xenagos.application.port.input.model.TourDTO

interface GetToursForAreaUseCase {

    fun whereAreaIs(area:Int): List<TourDTO>?

}