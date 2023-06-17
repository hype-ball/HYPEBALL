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

function editMode() {

    $('#nickname').toggleClass('saved');

    $("input[name='name']").attr("readonly", false);

    $('#profileEditBtn').toggleClass('hide');
    $('#profileEditBtn').toggleClass('show');
    $('#profileSaveBtn').toggleClass('hide');
    $('#profileSaveBtn').toggleClass('show');
    $("input[name='picture']").toggleClass('hide');
    $("input[name='picture']").toggleClass('show');

    $(".btn-upload").toggleClass('hide');
    $(".btn-upload").toggleClass('show');

}

$("#profileSaveBtn").on("click", function () {

    console.log("buttonClickForProfileChange")

    // 첨부파일 저장
    var form = new FormData();

    var inputPicture = $("input[name='picture']");
    var newPic = inputPicture[0].files[0];


    let newNick = {
        name : $("input[name='name']").val()
    }

    form.append("picture", newPic);
    form.append("nickname", new Blob([JSON.stringify(newNick)], {type:"application/json"}));

    $.ajax({
        url: '/member/updateProfile',
        type: 'POST',
        async: false,
        data: form,
        contentType: false,
        processData: false,
        enctype: 'multipart/form-data',
        success: function (data) {

            if (data.result === "success") {
                console.log("success");
                window.location.reload();
            } else if (data.result === "valid") {
                console.log("valid")
                for (let i in data.valid) {
                    console.log(data.valid[i].field);
                    console.log(data.valid[i].message);
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

