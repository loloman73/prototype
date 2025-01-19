package xenagos.adapter.output.persistence.model

import jakarta.persistence.*
import java.math.BigDecimal
import java.util.*

@Entity
@Table(name = "tours")
open class Tour {
    @Id
    @Column(name = "tour_id", nullable = false)
    open var id: UUID? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "xenagos_id")
    open var xenagos: Xenagoi? = null

    @Column(name = "title", length = Integer.MAX_VALUE)
    open var title: String? = null

    @Column(name = "description", length = Integer.MAX_VALUE)
    open var description: String? = null

    @Column(name = "price")
    open var price: BigDecimal? = null

    @Column(name = "photo_file_name")
    open var photoFileName: UUID? = null

    @Column(name = "rate_stars")
    open var rateStars: Short? = null

    @Column(name = "rate_reviews")
    open var rateReviews: Int? = null

    @Column(name = "coordinates_avg", columnDefinition = "point")
    open var coordinatesAvg: Any? = null

    @OneToMany(mappedBy = "tour")
    open var tourPoints: MutableSet<TourPoint> = mutableSetOf()
}