package com.man.vehicles.utils

import com.man.vehicles.entity.VehicleFileEntity

class VehicleEntitiesBuilder(private val vehicleLinesList: List<VehicleFileLine>) {
    fun build(): List<VehicleFileEntity> {
        val vehicleFileEntities: MutableList<VehicleFileEntity> = ArrayList()
        vehicleLinesList.forEach {
            vehicleFileEntities.add(VehicleFileEntity(it, ArrayList(), ArrayList()))
        }
        return vehicleFileEntities
    }
}