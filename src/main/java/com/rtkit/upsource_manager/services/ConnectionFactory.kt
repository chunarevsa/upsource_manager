package com.rtkit.upsource_manager.services

interface ConnectionFactory {
    fun getConnection(): Connection
}