package xenagos.adapter.output.persistence.model

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "languages")
open class LanguageJpaEntity : BaseJPAEntity  {
    @Id
    @Column(name = "language_id", nullable = false)
    open var id: UUID? = null

    @Column(name = "code", length = 3, nullable = false)
    open var code: String? = null

    @Column(name = "english_name", length = 35, nullable = false)
    open var englishName: String? = null

    @Column(name = "native_name", length = 35, nullable = false)
    open var nativeName: String? = null

    @Column(name = "active", nullable = false)
    open var active: Boolean? = null

    @OneToMany(mappedBy = "language")
    open var mediaGuides: MutableSet<MediaGuideJpaEntity> = mutableSetOf()
}