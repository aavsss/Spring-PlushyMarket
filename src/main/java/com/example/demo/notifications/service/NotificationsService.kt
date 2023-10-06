package com.example.demo.notifications.service

import com.example.demo.crud.repository.models.PlushyInDB
import org.springframework.stereotype.Service

@Service
open class NotificationsService {
    open fun sendNotification(plushyInDB: PlushyInDB) {
        println("sending notifications to ${plushyInDB.ownerEmail}")
    }
}