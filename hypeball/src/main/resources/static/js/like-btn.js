// $(document).ready(function () {
//     $(".heart").on("click", function () {
//         $(".like").toggleClass("heart-active");
//         $(".heart").toggleClass("heart-active");
//     });
// });

removeLike = (target) => {
    $("#" + target.id).removeClass("heart-active");
}

clickHeart = (target) => {

    let status;
    const selected = $("#" + target.id)
    if (selected.hasClass("heart-active")) {
        status = "hate";
    } else {
        status = "like"
    }

    $.ajax({
        url: '/store/like/' + selected.attr("value"),
        type: 'POST',
        data: {
            status : status
        },
        success: function (data) {
            console.log(status + ', ' + data.result)
            $("#" + target.id).toggleClass("heart-active");
        },
        error: function () {
        }
    });
}

modalHeartClick = (target) => {
    let status;
    console.log(target.src)

    if ( target.src.endsWith("/image/deadheart.png")) {
        status = "like"
    } else {
        status = "hate";
    }

    $.ajax({
        url: '/store/like/' + $("#storeId").val(),
        type: 'POST',
        data: {
            status : status
        },
        success: function (data) {
            console.log(status + ', ' + data.result)
            if (data.result === "success") {
                target.src = "/image/aliveheart.png"
            } else {
                target.src = "/image/deadheart.png"
            }
            $("#storeLikeCount").text(data.count);
        },
        error: function () {
        }
    });
}