package xenagos.application.mapper

import org.springframework.stereotype.Component
import xenagos.application.port.input.TourDTO
import xenagos.domain.model.Tour
import java.util.*

@Component
class TourMapper {
    fun entityToDto(entity: Tour): TourDTO {
        val dtoTourId = entity.id
        val dtoTitle = entity.title
        val dtoDescription = entity.description
        val dtoPrice = entity.price
        val dtoTourPhotoFileName = entity.photoFileName
        val dtoTourRate = entity.totalRate
        val dtoTourReviews = entity.totalReviews
        val dtoAvgCoordinates= entity.avgCoordinates

        //TODO query find xenagos UUID
        val dtoTouristGuideId = UUID.randomUUID()
        val dtoTouristGuideName = "John Doe"
        val dtoTouristGuidePhotoFileName = UUID.randomUUID()
        val dtoTourPointsQuantity = entity.tourPoints.size.toShort()

        return TourDTO(
            tourId = dtoTourId,
            title = dtoTitle,
            description = dtoDescription,
            price = dtoPrice,
            tourPhotoFileName = dtoTourPhotoFileName,
            tourRate = dtoTourRate,
            tourReviews=dtoTourReviews,
            avgCoordinates = dtoAvgCoordinates,
            touristGuideId = dtoTouristGuideId,
            touristGuideName = dtoTouristGuideName,
            touristGuidePhotoFileName = dtoTouristGuidePhotoFileName,
            tourPointsQuantity = dtoTourPointsQuantity
        )
    }
}