package xenagos.adapter.output.persistence.admin

import org.springframework.stereotype.Repository
import xenagos.adapter.output.persistence.admin.mapper.AdminTopicTagJPAMapper
import xenagos.adapter.output.persistence.model.TopicTagJpaEntity
import xenagos.application.port.output.admin.AdminTopicTagsOutputPort
import xenagos.domain.model.TopicTag

@Repository
open class AdminTopicTagsPersistence(
        repository: AdminTopicTagsRepository,
        mapper: AdminTopicTagJPAMapper) :
    BaseAdminPersistence<TopicTag, TopicTagJpaEntity>(repository, mapper),
    AdminTopicTagsOutputPort