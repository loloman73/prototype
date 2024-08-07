package xenagos.application.domain.xenagisi.entity

enum class ContentTag(val description:String) {
    Architecture(""),
    Religion(""),
    War(""),
    Art(""),
    Technology("")
}

enum class AgeTag {
    Kids,
    Teenage,
    Adult,
    AllAges
}

enum class SpecialAbilityTag {
    WheelChair,
    Blind,
    Deaf
}
