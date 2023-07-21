// 업로드한 프로필 사진 미리보기
function showNewPic(event) {
    for (var image of event.target.files) {
        var newPic = event.target.files[0];

        var reader = new FileReader();
        reader.onload = function (e) {
            document.getElementById("picture").setAttribute("src", ""  + e.target.result);

        };
        reader.readAsDataURL(image);
    }
}

// 편집 버튼 클릭
function editMode() {

    $('#nickname').toggleClass('saved');

    $("input[name='name']").attr("readonly", false);

    $('#profileEditBtn').toggleClass('hide');
    $('#profileEditBtn').toggleClass('show');
    $('#profileSaveBtn').toggleClass('hide');
    $('#profileSaveBtn').toggleClass('show');

    $(".btn-upload").toggleClass('hide');
    $(".btn-upload").toggleClass('show');

}

// 변경된 프로필 저장
$("#profileSaveBtn").on("click", function () {

    // 첨부파일 저장
    var form = new FormData();
    var inputPicture = $("input[name='picture']");
    var newPic = inputPicture[0].files[0];

    let newNick = {
        name : $("input[name='name']").val()
    }

    if (newPic != null) {
        form.append("picture", newPic);
    }

    form.append("nickname", new Blob([JSON.stringify(newNick)], {type:"application/json"}));

    $.ajax({
        url: '/member/updateProfile',
        type: 'POST',
        // async: false,
        data: form,
        contentType: false,
        processData: false,
        enctype: 'multipart/form-data',
        success: function (data) {

            if (data.result === "success") {
                window.location.href='/member/myPage';

            } else if (data.result === "valid") {
                for (let i in data.valid) {
                    $('#valid-'+data.valid[i].field).text(data.valid[i].message)
                }
                $("input[name='name']").focus();
            }

        },
        error: function (data) {
            loginError(data.responseJSON);
        }
    });
});

