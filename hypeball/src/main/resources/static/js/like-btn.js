removeLike = (target) => {
    $("#" + target.id).removeClass("heart-active");
}

// 마이페이지 찜 버튼
clickHeart = (target) => {

    let status;
    const selected = $("#" + target.id)
    if (selected.hasClass("heart-active")) {
        status = "hate";
    } else {
        status = "like"
    }

    // 찜
    $.ajax({
        url: '/store/like/' + selected.attr("value"),
        type: 'POST',
        data: {
            status : status
        },
        success: function (data) {
            $("#" + target.id).toggleClass("heart-active");
            let count = parseInt($("#myLikeCount").text());
            if (data.result === "success") {
                count += 1;
            } else {
                count -= 1;
            }
            $("#myLikeCount").text(count);
        },
        error: function (data) {
            loginError(data.responseJSON);
        }
    });
}

// 모달 찜 버튼
modalHeartClick = (target) => {
    let status;

    if ( target.src.endsWith("/image/deadheart.png")) {
        status = "like"
    } else {
        status = "hate";
    }

    // 찜
    $.ajax({
        url: '/store/like/' + $("#storeId").val(),
        type: 'POST',
        data: {
            status : status
        },
        success: function (data) {
            if (data.result === "success") {
                target.src = "/image/aliveheart.png"
            } else {
                target.src = "/image/deadheart.png"
            }
            $("#storeLikeCount").text(data.count);
        },
        error: function (data) {
            loginError(data.responseJSON);
        }
    });
}