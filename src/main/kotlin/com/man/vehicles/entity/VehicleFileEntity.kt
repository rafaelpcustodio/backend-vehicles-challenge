package com.man.vehicles.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "vehicle")
data class VehicleFileEntity(
        @Id val vin: String,
        val hardwareAllowedList: List<String> = emptyList(),
        var softwareAllowedList: List<String> = emptyList()
)