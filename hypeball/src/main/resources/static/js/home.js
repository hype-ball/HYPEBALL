// 홈화면 movingtext
$(document).ready(function() {
        let i = 1;
    setInterval(function() {
        $(".moving-img").removeClass("d-block");
        $(".moving-img").addClass("d-none");
        $("#moving-img-"+ i).removeClass("d-none");
        $("#moving-img-"+ i).addClass("d-block");
        i++;
        if (i > 6) i = 1;
    }, 1600);
});
