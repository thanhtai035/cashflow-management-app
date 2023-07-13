package com.tyme.cashflow_manament_app.app.data.service

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
class JsonHolder (
    @SerialName("username")
    val username: String,

    @SerialName("password")
    val password: String
)
