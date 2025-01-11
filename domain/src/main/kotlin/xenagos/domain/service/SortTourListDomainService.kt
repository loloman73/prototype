package xenagos.domain.service

import xenagos.domain.model.Tour

class SortTourListDomainService {
    fun sort(myList: Set<Tour>?): List<Tour>?{
        // TODO - apply some business logic
        return myList?.toList()
    }

}