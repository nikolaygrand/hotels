package ru.antonov.hotels.activity

import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseUser

data class FirebaseAuthResult(
    var user: FirebaseUser? = null,
    var response: IdpResponse? = null,
    var authorized: Boolean = false
)