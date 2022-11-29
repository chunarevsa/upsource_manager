package com.rtkit.upsource_manager.services

class TrialConnectionFactory : ConnectionFactory {
    override fun getConnection() : Connection{
        return TrialConnection()
    }
}
