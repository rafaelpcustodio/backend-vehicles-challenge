package com.man.vehicles.interfaces.adapter.repository

import com.man.vehicles.entity.VehicleFileEntity
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface VehicleFileRepository : MongoRepository<VehicleFileEntity, String>