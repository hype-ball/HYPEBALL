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