package xenagos.domain.model

import java.util.*

data class AgeGroup(
    val id: UUID,
    val groupName: String,
    val minAge: Byte,
    val maxAge: Byte,
    val active: Boolean
)

//    SmallChild("6-8"),
//    PreTeen("9-12"),
//    Teenage("13-17"),
//    YoungAdult("18-30"),
//    MiddleAgeAdult("31-59"),
//    SeniorAdult("60-99")


//  Little Child	6	9
//  Preteens	10	12
//  Adolescent	13	17
//  Young Adult	18	29
//  Average Adult	30	64
//  Senior	65	-
