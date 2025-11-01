package xenagos.application.port.output

import xenagos.domain.model.MediaType
import java.util.UUID

interface AdminMediaTypesOutputPort {
    fun getAllMediaTypes(): ArrayList<MediaType>
    fun saveNewMediaType(newEntityToSave: MediaType):MediaType
    fun updateMediaType(entityToUpdate: MediaType):MediaType
    fun deleteMediaType(id: UUID)
}