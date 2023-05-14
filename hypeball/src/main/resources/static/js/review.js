// 태그
var input = document.querySelector('input[name=tags]');
var tagify = new Tagify(input, {maxTags: 3});

// // 태그가 추가되면 이벤트 발생
// tagify.on('add', function () {
//     console.log(tagify.value); // 입력된 태그 정보 객체
// })
//
// tagify.on('remove', function () {
//     console.log("remove"); // 입력된 태그 정보 객체
// })

// 별점
let starValue = 0;

const drawStar = (target) => {
    starValue = target.value;
    document.querySelector(`.star span`).style.width = `${target.value * 10}%`;
    console.log(target.value)
}

$("#review-save").on("click", function () {

    // 선택된 분위기 태그 목록 가져오기
    const query = 'input[name="point-tag"]:checked';
    const selectedEls = document.querySelectorAll(query);
    let pointArr = [];
    selectedEls.forEach((el) => {
        pointArr.push(el.value);
    });
    let drinkArr = [];
    tagify.value.forEach((el) => {
        drinkArr.push(el.value);
    })

    let review = {
        storeId : $("#storeId").val(),
        content : $("#review-content").val(),
        star : starValue,
        drink : drinkArr,
        point : pointArr
    }
    console.log(review)

    $.ajax({
        url: '/reviews/add/' + review.storeId,
        type: 'POST',
        async : false,
        data: review,
        success: function (data) {
            if (data === "success") {
                console.log("success")
            } else {
                console.log("fail")
            }
        },
        error: function () {
            console.log("error")
        }
    });
});