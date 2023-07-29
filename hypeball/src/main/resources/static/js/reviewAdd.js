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
            xbutton.style.cursor = "pointer";
        };

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

// 별점
const drawStar = (target) => {
    document.querySelector(`#inputStar`).style.width = `${target.value * 10}%`;
}

$("#review-save").on("click", function () {

    validClear();

    // 첨부파일 저장
    var form = new FormData();

    var inputFile = $("input[name='input-file']");
    var files = inputFile[0].files;

    // formData 생성
    for (var i = 0; i < files.length; i++) {
        form.append("file", files[i]);
    }

    // 선택된 분위기 태그 목록 가져오기
    const selectedEls = document.querySelectorAll('input[name="point-tag"]:checked');
    let pointArr = [];
    selectedEls.forEach((el) => {
        pointArr.push(el.value);
    });
    let drinkArr = [];
    tagify.value.forEach((el) => {
        drinkArr.push(el.value);
    })

    let review = {
        content: $("#review-content").val(),
        star: $('input[name="star"]').val(),
        storeId : $("#storeId").val(),
        drinkList : drinkArr,
        pointList : pointArr
    }
    form.append("review", new Blob([JSON.stringify(review)], {type:"application/json"}));

    // 리뷰 저장
    $.ajax({
        url: '/reviews/add',
        type: 'POST',
        async: false,
        data: form,
        contentType: false,
        processData: false,
        enctype: 'multipart/form-data',
        success: function (data) {
            if (data.result === "success") {
                inputClear();
                createModal(review.storeId);
            } else if (data.result === "valid") {
                for (let i in data.valid) {
                    $('#valid-'+data.valid[i].field).text(data.valid[i].message)
                }
                $("#review-content").focus();
            }
        },
        error: function (data) {
            loginError(data.responseJSON);
        }
    });
});

// 모달 전체 input 초기화
function inputClear() {
    $('input[name="tags"]').val(null); // 술태그
    $('input[name="point-tag"]').prop('checked',false); //분위기태그
    $('input[name="star"]').val(0); //별점
    $('input[name="input-file"]').val(null); //이미지
    $('#image_container').children().remove();
    document.querySelector(`#inputStar`).style.width = '0%';
    $("#review-content").val(null); //내용
}

// validation 초기화
function validClear() {
    $('.validation').text(null);
}

function xBtnClick() {
    const url = window.location.href
    if (url.endsWith("/member/myPage")) {
        window.location.reload();
    } else {
        inputClear();
        validClear();
        staticMapInit();
    }
}
