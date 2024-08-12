package mobile.wsmb2024.a08_module2.uiState

data class UserUiState(
    var userId: String="",
    var photo: String = "",
    var ic: String = "",
    var email: String = "",
    var password: String = "",

    var name: String = "",
    var gender: String = "",
    var phone: String = "",
    var address: String = "",

    var isDriver: Boolean = false,
    var model: String = "",
    var capacity: String = "",
    var special: String = ""

)
