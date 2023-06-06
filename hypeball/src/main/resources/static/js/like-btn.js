$(document).ready(function () {
    $(".heart").on("click", function () {
        $(".like").toggleClass("heart-active");
        $(".heart").toggleClass("heart-active");
    });
});