package xenagos.application.domainService

import org.springframework.stereotype.Component
import xenagos.application.port.input.model.TourDTO

@Component
class SortTourListService {
    fun sort(myList: Set<TourDTO>?): List<TourDTO>?{
        // TODO - apply some business logic
        return myList?.toList()
    }

}