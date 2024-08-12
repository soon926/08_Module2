package mobile.wsmb2024.a08_module2.uiState

data class RideUiState(
    var rideId: String = "",
    var driver: UserUiState = UserUiState(),
    var date: String = "",
    var time: String = "",
    var origin: String = "",
    var destination: String = "",
    var fare: String = "",

    var rider: UserUiState = UserUiState(),
    var riderList: ArrayList<UserUiState> = ArrayList<UserUiState>()

)
