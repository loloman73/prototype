package xenagos.application.service

import org.springframework.stereotype.Service
import xenagos.application.port.input.AdminGetTopicsUseCase
import xenagos.application.port.input.model.TourDTO

@Service
class AdminGetTopicsService(): AdminGetTopicsUseCase {
    override fun getTopics(): List<TourDTO> {
        TODO("Not yet implemented")
    }


}