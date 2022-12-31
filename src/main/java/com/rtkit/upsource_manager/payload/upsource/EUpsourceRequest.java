package com.rtkit.upsource_manager.payload.upsource;

/**
 * <a href="https://codereview.ritperm.rt.ru/~api_doc/reference/Service.html#messages.UpsourceRPC">...</a>
 */
public enum EUpsourceRequest {

    /** Информация о проекте */
    GET_PROJECT_INFO("getProjectInfo", "ProjectIdDTO", "ProjectInfoDTO"),
    /** Возвращает список изменений (сумма всех правок) */
    GET_SUM_CHANGES("getReviewSummaryChanges", "ReviewSummaryChangesRequestDTO", "ReviewSummaryChangesResponseDTO"),
    /** Получения ревью */
    GET_REVIEWS("getReviews", "ReviewsRequestDTO", "ReviewListDTO"),
    /** Закрытие ревью */
    CLOSE_REVIEW("closeReview", "CloseReviewRequestDTO", "CloseReviewResponseDTO"),
    /** Получение инфы по конкретном разработчику */
    USER_INFO("getUserInfo", "UserInfoRequestDTO", "UserInfoResponseDTO"),
    /** Получение инфы о всех разработчиках в проекте */
    FIND_USERS("findUsers", "FindUsersRequestDTO", "UserInfoResponseDTO"),
    /** Получение конкретного ревью по ReviewId */
    GET_REVIEW_DETAILS("getReviewDetails", "ReviewIdDTO", "ReviewDescriptorDTO")

    ;
    private final String method;
    private final String request;
    private final String response;

    EUpsourceRequest(String method, String request, String response) {
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
