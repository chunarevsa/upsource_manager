<div class="mb-3 pl-3"><h4>Ревью для удаления</h4></div>
<div class="d-flex mb-3 pl-3 justify-content-between align-items-center">
    <div class="btn-group">
        <form action="/main/review/closeAll" method="POST">
            <button class="btn btn-warning " type="submit">Закрыть все</button>
        </form>
    </div>
</div>
<div class="mb-3 pl-3"><h4>Пустые ревью</h4></div>
<div class="card-columns" id="review-list">
    <#list reviewsWithEmptyRevision as reviewWithEmptyRevision>
        <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
            <div class="col">
                <div class="card shadow-sm">

                    <div class="card-body w-100 p-6">
                        <a href="${reviewWithEmptyRevision.getUpsourceLink(reviewWithEmptyRevision.reviewId.reviewId)}">
                            <h4
                                    class="card-text mt-2">${reviewWithEmptyRevision.reviewId.reviewId}</h4></a>
                        <a href="${reviewWithEmptyRevision.getJiraLink(reviewWithEmptyRevision.title)}">
                            <h6 class="card-text mt-2">${reviewWithEmptyRevision.getClearTitle(reviewWithEmptyRevision.title)}</h6>
                        </a>
                        <h6 class="mt-2"> Ревьюверы: </h6>
                        <#list reviewWithEmptyRevision.participants as participant>
                            <div class="mt-2"> ${participant.name} </div>
                        <#else>
                            Нет ревьюверов
                        </#list>
                        <div class="d-flex justify-content-between align-items-center mt-2">
                            <div class="btn-group">
                                <form ${reviewWithEmptyRevision.createdBy}" method="POST">
                                <button class="btn btn-warning " type="submit">Закрыть</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    <#else>
        <div class="mb-3 pl-5">Haven't bad reviews</div>
    </#list>
</div>

<div class="mb-3 mt-3 pl-3"><h4>Старые ревью</h4></div>
<div class="form-row mb-3 pl-4">
    <div class="form-group col-md-6">
        <form method="get" action="/main/review" class="form-inline">
            <input type="text" name="filter" class="form-control" value="${filter?ifExists}" placeholder="От скольки дней считать просроченным">
            <button type="submit" class="btn btn-primary ml-2">Применить</button>
        </form>
    </div>
</div>
<div class="card-columns" id="review-list">
    <#list expiredReviews as expiredReview>
        <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
            <div class="col">
                <div class="card shadow-sm">

                    <div class="card-body w-100 p-6">
                        <a href="${expiredReview.getUpsourceLink(expiredReview.reviewId.reviewId)}"><h4
                                    class="card-text mt-2">${expiredReview.reviewId.reviewId}</h4></a>
                        <a href="${expiredReview.getJiraLink(expiredReview.title)}">
                            <h6 class="card-text mt-2">${expiredReview.getClearTitle(expiredReview.title)}</h6></a>
                        <h6 class="mt-2"> Ревьюверы: </h6>
                        <#list expiredReview.participants as participant>
                            <div class="mt-2"> ${participant.name} </div>
                        <#else>
                            Нет ревьюверов
                        </#list>
                        <div class="d-flex justify-content-between align-items-center mt-2">
                            <div class="btn-group">
                                <form action="/main/review/close/${expiredReview.reviewId.reviewId}" method="POST">
                                <button class="btn btn-warning " type="submit">Закрыть</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    <#else>
        <div class="mb-3 pl-5">Haven't bad reviews></div>
    </#list>
</div>
<div class="mb-5 pl-5"><h1></h1></div>
<div class="mb-5 pl-5"><h1></h1></div>
