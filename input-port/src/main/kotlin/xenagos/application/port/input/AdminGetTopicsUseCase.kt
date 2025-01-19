package xenagos.application.port.input

import xenagos.application.port.input.model.TourDTO

interface AdminGetTopicsUseCase {
    fun getTopics(): List<TourDTO>
}