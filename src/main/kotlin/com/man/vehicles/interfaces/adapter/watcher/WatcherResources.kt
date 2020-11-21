package com.man.vehicles.interfaces.adapter.watcher

import com.man.vehicles.interfaces.adapter.provider.VehicleFileProvider
import org.springframework.stereotype.Component
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.nio.file.*


@Component
class WatcherResources(private val vehicleFileProvider: VehicleFileProvider) {

    val watchService: WatchService = FileSystems.getDefault().newWatchService()

    val pathFromWatchedFile: Path =
            Paths.get(System.getProperty("user.dir").plus("/resources"))

    fun onChange() {
        pathFromWatchedFile.register(watchService, StandardWatchEventKinds.ENTRY_CREATE)
        processDetectedFileInsertion(watchService)
    }

    fun processDetectedFileInsertion(watchService: WatchService) {
        var key: WatchKey
        while (watchService.take().also { key = it } != null) {
            for (event: WatchEvent<*> in key.pollEvents()) {
                val csvReader = BufferedReader(
                        FileReader(pathFromWatchedFile
                                .toString().plus("/" + event.context()))
                )
                vehicleFileProvider.persistProgramData(csvReader, event.context().toString())
            }
            key.reset()
        }
    }
}
