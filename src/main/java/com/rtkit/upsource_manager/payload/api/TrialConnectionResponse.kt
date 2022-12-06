package com.rtkit.upsource_manager.payload.api

class TrialConnectionResponse(
    response: String
) : ABaseUpsourceResponse(response) {

    fun isSuccessful(): Boolean {
        return result.isNotEmpty()
    }
}
