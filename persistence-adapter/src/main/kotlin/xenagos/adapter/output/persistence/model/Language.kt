package xenagos.adapter.output.persistence.model

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "languages")
open class Language {
    @Id
    @Column(name = "language_id", nullable = false)
    open var id: UUID? = null

    @Column(name = "language", length = Integer.MAX_VALUE)
    open var language: String? = null

    @OneToMany(mappedBy = "language")
    open var mediaGuides: MutableSet<MediaGuide> = mutableSetOf()
}