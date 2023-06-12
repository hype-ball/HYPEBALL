function showNewPic(event) {
    for (var image of event.target.files) {
        var newPic = event.target.files[0];

        var reader = new FileReader();
        reader.onload = function (e) {
            document.getElementById("picture-temp").setAttribute("src", ""  + e.target.result);

        };
        reader.readAsDataURL(image);

    }
}