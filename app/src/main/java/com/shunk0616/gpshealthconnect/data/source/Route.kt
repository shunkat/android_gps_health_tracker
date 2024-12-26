package com.shunk0616.gpshealthconnect.data.source

import kotlinx.serialization.Serializable

class Route {
    @Serializable
    data object Authenticatioin

    @Serializable
    data object Setting

    @Serializable
    data object Home
}