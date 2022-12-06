package com.rtkit.upsource_manager.services

class TrialConnectionResponse(
    response: String
) : ABaseUpsourceResponse(response) {

    fun isSuccessful(): Boolean {
        return result.isNotEmpty()
    }
}
