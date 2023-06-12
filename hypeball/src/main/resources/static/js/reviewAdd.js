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

        //console.log(image);
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
    console.log(target.value)
    document.querySelector(`#inputStar`).style.width = `${target.value * 10}%`;
}

$("#review-save").on("click", function () {

    console.log("buttonClickForSaving")

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
                console.log("success")
                inputClear();
                createModal(review.storeId);
            } else if (data.result === "valid") {
                console.log("valid")
                for (let i in data.valid) {
                    $('#valid-'+data.valid[i].field).text(data.valid[i].message)
                }
                $("#review-content").focus();
            }
        },
        error: function () {
            alert("로그인 필수입니다.")
            console.log("error")
        }
    });
});

// input 초기화
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
    inputClear();
    validClear();
    staticMapInit();
}

function staticMapInit() {
    $("#staticMap").empty();
}