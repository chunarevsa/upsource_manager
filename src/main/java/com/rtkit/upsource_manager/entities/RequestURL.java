package com.rtkit.upsource_manager.entities;

/** https://codereview.ritperm.rt.ru/~api_doc/reference/Service.html#messages.UpsourceRPC */
public enum RequestURL {
	/** Информация о проекте*/
	GET_PROJECT_INFO("https://codereview.ritperm.rt.ru/~rpc/getProjectInfo"),
	GET_SUM_CHANGES("https://codereview.ritperm.rt.ru/~rpc/getReviewSummaryChanges"),
	GET_REVIEWS("https://codereview.ritperm.rt.ru/~rpc/getReviews"),
	CLOSE_REVIEW("https://codereview.ritperm.rt.ru/~rpc/closeReview"),
	USER_INFO("https://codereview.ritperm.rt.ru/~rpc/getUserInfo"),
	FIND_USERS("https://codereview.ritperm.rt.ru/~rpc/findUsers");


	private final String requestURL;

	RequestURL(String requestURL) {
		this.requestURL = requestURL;
	}

	@Override
	public String toString() {
		return requestURL;
	}
}
