// 첨부파일 미리보기
function setThumbnail(event) {
    for (var image of event.target.files) {
        var reader = new FileReader();

        reader.onload = function (event) {
            var li = document.createElement("li");
            var img = document.createElement("img");
            var xbutton = document.createElement("a");

            img.style.maxWidth = "15vw";
            img.style.maxHeight = "15vw";
            xbutton.innerText = "x";


            li.appendChild(img);
            li.appendChild(xbutton);
            img.setAttribute("src", event.target.result);
            document.querySelector("div#image_container").appendChild(li);
            xbutton.setAttribute("onclick", "delThumbnail(this)");

        };

        console.log(image);
        reader.readAsDataURL(image);

    }
}

// 썸네일 제거
function delThumbnail(target) {
    target.parentElement.remove();
}

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
const drawStar = (target) => {
    document.querySelector(`.star span`).style.width = `${target.value * 10}%`;
    console.log(target.value)
}

$("#review-save").on("click", function () {

    // 첨부파일 저장
    var form = new FormData();

    var inputFile = $("input[name='input-file']");

    var files = inputFile[0].files;

    if (files.length !== 0) {
        // formData 생성
        for (var i = 0; i < files.length; i++) {
            form.append("uploadFile", files[i]);
        }
        $.ajax({
            url: '/reviews/add/file',
            processData: false,
            contentType: false,
            type: 'POST',
            async: false,
            data: form,
            success: function (storeId) {
                console.log("file success")
            },
            error: function () {
                console.log("file error")
            }
        });
    }

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
        storeId: $("#storeId").val(),
        content: $("#review-content").val(),
        star: $('input[name="star"]').val(),
        drink : drinkArr,
        point : pointArr,
    }
    console.log(review)

    $.ajax({
        url: '/reviews/add/' + review.storeId,
        type: 'POST',
        async: false,
        data: review,
        success: function (storeId) {
            console.log("success")
            inputClear();
            reviewLoading(storeId);
        },
        error: function () {
            console.log("error")
        }
    });
});

// input 초기화
function inputClear() {
    $('input[name="tags"]').val(null); // 술태그
    $('input[name="point-tag"]').prop('checked',false); //분위기태그
    console.log($('input[name="star"]').val());
    $('input[name="star"]').val(0); //별점
    console.log($('input[name="star"]').val());
    document.querySelector(`.star span`).style.width = '0%';
    $("#review-content").val(null); //내용
}