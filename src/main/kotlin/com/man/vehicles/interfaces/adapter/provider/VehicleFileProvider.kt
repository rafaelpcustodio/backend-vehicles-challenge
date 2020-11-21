package com.man.vehicles.interfaces.adapter.provider

import com.man.vehicles.entity.VehicleFileEntity
import com.man.vehicles.interfaces.adapter.repository.VehicleFileRepository
import org.springframework.stereotype.Service
import java.io.BufferedReader

@Service
class VehicleFileProvider(private val vehicleFileRepository: VehicleFileRepository) {

    fun persistProgramData(file: BufferedReader, programType: String) {
        var row : String
        var previousVin: String? = null
        var actualVin: String?
        var vehicleFileEntity : VehicleFileEntity?
        var listOfCodes : MutableList<String> = mutableListOf()
        while (file.readLine().also { row = it } != null) {
            val data: List<String> = row.split(",")
            actualVin = data[0]
            vehicleFileEntity = vehicleFileRepository.findById(actualVin).orElse(null)
            val actualVersion = data[1]
            when (previousVin) {
                null -> {
                    previousVin = data[0]
                    listOfCodes.add(actualVersion)
                }
                actualVin -> {
                    listOfCodes.add(actualVersion)
                }
                else -> {
                    vehicleFileEntity = validateProgramInsertion(
                            actualVin, listOfCodes, programType, vehicleFileEntity
                    )
                    vehicleFileRepository.save(vehicleFileEntity)
                    previousVin = null
                    listOfCodes = mutableListOf()
                }
            }
            file.close()
        }
    }

    private fun validateProgramInsertion(
            actualVin: String,
            listOfCodes: MutableList<String>,
            programType: String,
            vehicleFileEntity: VehicleFileEntity
    ): VehicleFileEntity {
        return if(programType.contains("soft")) {
            listOfCodes.addAll(vehicleFileEntity.softwareAllowedList)
            VehicleFileEntity(actualVin, softwareAllowedList = listOfCodes)
        } else {
            listOfCodes.addAll(vehicleFileEntity.hardwareAllowedList)
            VehicleFileEntity(actualVin, hardwareAllowedList = listOfCodes)
        }
    }
}