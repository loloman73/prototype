package xenagos.adapter.output.persistence

import org.springframework.stereotype.Service
import xenagos.adapter.output.persistence.mapper.toDomainEntity
import xenagos.adapter.output.persistence.mapper.toJpaEntity
import xenagos.application.port.output.AdminMediaTypesOutputPort
import xenagos.domain.model.MediaType
import java.util.UUID

@Service
class AdminMediaTypesPersistence(private val repository: AdminMediaTypesRepository): AdminMediaTypesOutputPort {

    override fun getAllMediaTypes() = arrayListOf<MediaType>().apply {
        repository.findAll().forEach { add(it.toDomainEntity()) }
    }
    override fun saveNewMediaType(newEntityToSave: MediaType): MediaType =
        repository.save(newEntityToSave.toJpaEntity()).toDomainEntity()

    override fun updateMediaType(entityToUpdate: MediaType): MediaType =
        repository.save(entityToUpdate.toJpaEntity()).toDomainEntity()

    override fun deleteMediaType(id: UUID) = repository.deleteById(id)
}