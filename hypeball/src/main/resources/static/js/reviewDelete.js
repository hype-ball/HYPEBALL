
function deleteReview(reviewId) {
    $.ajax({
        url: '/reviews/delete/' + reviewId,
        type: 'DELETE',
        success: function () {
            myReviewAjax($("select[name=sort]").val());
        },
        error: function () {
        }
    });
}

deleteConfirm = (target) => {
    if(!confirm('삭제한 리뷰는 복구할수 없습니다.\n정말로 삭제하시겠습니까??')){
        return false;
    } else {
        console.log(target.id);
        deleteReview(target.id);
    }

}