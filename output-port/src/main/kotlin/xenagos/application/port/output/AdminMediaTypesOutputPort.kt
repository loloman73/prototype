package xenagos.application.port.output

import xenagos.domain.model.MediaType

interface AdminMediaTypesOutputPort {
    fun getAllMediaTypes(): ArrayList<MediaType>
}