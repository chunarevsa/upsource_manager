package com.rtkit.upsource_manager.entities;

/**
 * <a href="https://codereview.ritperm.rt.ru/~api_doc/reference/Service.html#messages.UpsourceRPC">...</a>
 */
public enum ERequest {

    /**
     * Информация о проекте
     */
    GET_PROJECT_INFO("getProjectInfo", "", "ProjectInfoDTO"),
    GET_SUM_CHANGES("getReviewSummaryChanges", "", ""),
    GET_REVIEWS("getReviews", "", "ReviewListDTO"),
    CLOSE_REVIEW("closeReview", "", ""),
    USER_INFO("getUserInfo", "UserInfoRequestDTO", "UserInfoResponseDTO"),
    FIND_USERS("/findUsers", "", "");

    private final String method;
    private final String request;
    private final String response;

    ERequest(String method, String request, String response) {
        this.method = method;
        this.request = request;
        this.response = response;
    }

    public String getRequestUrl() {
        return "https://codereview.ritperm.rt.ru/~rpc/" + method;
    }

    public String getRequest() {
        return request;
    }

    public String getResponse() {
        return response;
    }
}
