package xenagos.adapter.output.persistence.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name="TopicTags")
class TopicJpaEntity (
    @Id
    @Column(name="TopicTagID")
    var topicTagId: Byte,
    @Column(name="Name")
    var topicName:String,
    @Column(name = "Description")
    var topicDescription:String
)


