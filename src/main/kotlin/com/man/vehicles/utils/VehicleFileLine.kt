package com.man.vehicles.utils

class VehicleFileLineBuilder(valuesFromFileLine: List<String>, private val type: String) {
    var vin: String
    var code: String
    init {
        if(valuesFromFileLine.size != 2) {
            throw IllegalArgumentException()
        }
        this.vin = valuesFromFileLine[0]
        this.code = valuesFromFileLine[1]
    }
}