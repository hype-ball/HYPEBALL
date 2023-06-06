const reviewSort = (target) => {
    reviewAjax(0, target.value);
}

const clickPage = (page) => {
    reviewAjax(page, $('select[name="sort"]').val());
}

const reviewAjax = (pageParam, sortParam) => {
    const reviewSortCond = {
        page : pageParam,
        sort : sortParam
    }
    $.ajax({
        url: '/reviews/ps/' + $("#storeId").val(),
        type: 'GET',
        data : reviewSortCond,
        success: function (reviews) {
            console.log(this.url)
            console.log(reviews)
            createReview(reviews.content);
            createPaging(reviews.totalPages, reviews.number);
        },
        error: function () {
        }
    });
}

const createModal = (storeId) => {
    const reviewSortCond = {
        page : 0,
        sort : "default"
    }
    $.ajax({
        url: '/reviews/' + storeId,
        type: 'GET',
        data : reviewSortCond,
        success: function (data) {
            staticMap(data.store.lat, data.store.lng);
            storeInfo(data.store);
            createTag(data.points, data.drinks);
            createReview(data.reviews.content);
            createPaging(data.reviews.totalPages, data.reviews.number);
        },
        error: function () {
        }
    });
}

function storeInfo(store) {
    $("#storeId").val(store.id);
    $("#storeName").text(store.name);
    $("#storeAddr").text("📍 "+ store.address);
    $("#storeCategory").text(store.category.name);
    $("#storeMenu").text("🐽 " + store.menu);
}

function createReview(reviews) {
    var review_section = document.getElementById("review_section");
    var rv = "";

    console.log(reviews);

    if (reviews.length === 0) {
        rv = "<div class='rv-none'>" +
            "   <p>등록된 리뷰가 없습니다.</p>" +
            "</div>"
    }

    for (var i = 0; i < reviews.length; i++) {
        rv += '<a class="list-group-item list-group-item-action">' +
            '        <div class="d-flex w-100 justify-content-between">' +
            '            <h5 class="mb-1 me-2">' + reviews[i].writer +
            '                <span class="star rv-star">★★★★★' +
            '                    <span style="width: ' + (reviews[i].star * 20) + '%">★★★★★</span>' +
            '                </span>\n' +
            '            </h5>\n' +
            '            <small class="text-body-secondary">' + reviews[i].createdDate + '</small>' +
            '        </div>\n';
        for (let j = 0; j < reviews[i].drinks.length; j++) {
            rv += '        <small style="background-color: #FF9900" class="badge rounded-pill">' + reviews[i].drinks[j].drink + '</small>'
        }
        rv +=
            '        <p class="my-1 rv-content">' + reviews[i].content + '</p>\n';

        if (reviews[i].attachedFiles != null) {
            rv += '<div class="row m-auto gap-1">';
            for (let j = 0; j < reviews[i].attachedFiles.length; j++) {
                rv +=
                    '<div class="img-wrap">' +
                    '   <img class="img-thumbnail review-img" src="/files/' + reviews[i].attachedFiles[j].storeFileName + '" alt="img" onclick="imgSizeChange(this)">' +
                    '   <p class="img-hover-text">이미지<br>크게 보기</p>' +
                    '   <p class="img-hover-text2">이미지<br>원래대로</p>' +
                    '</div>'
            }
            rv += '</div>'
        }
        // if (reviews[i].attachedFiles !== null) {
        //     rv +=
        //     '<div id="carouselExampleControls'
        //         + reviews[i].id +
        //     '" class="carousel slide" data-ride="carousel">' +
        //     '  <div class="carousel-inner">';
        //
        //     for (var j = 0; j < reviews[i].attachedFiles.length; j++) {
        //         if (j === 0) {
        //             rv += '    <div class="carousel-item active" style="height: 130px; width: 130px;>';
        //         } else {
        //             rv += '    <div class="carousel-item" style="height: 130px;>';
        //         }
        //
        //         rv +=
        //     '      <img class="img-thumbnail" src="/files/' +
        //             reviews[i].attachedFiles[j].storeFileName
        //            + '" alt="slide">' +
        //     '    </div>';
        //     }
        //
        //     rv +=
        //     '  </div>';
        //     rv +=
        //        '<button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleControls' +
        //         reviews[i].id +
        //         '" data-bs-slide="prev">' +
        //        '     <span class="carousel-control-prev-icon" aria-hidden="true"></span>' +
        //        '    <span class="visually-hidden">Previous</span>' +
        //        '</button>' +
        //         '<button class="carousel-control-next" type="button" data-bs-target="#carouselExampleControls' +
        //         reviews[i].id +
        //         '" data-bs-slide="next">' +
        //               '<span class="carousel-control-next-icon" aria-hidden="true"></span>' +
        //               '<span class="visually-hidden">Next</span>' +
        //         '</button>' +
        //         '</div>';
        // }
        rv += '</a>';
    }

    review_section.innerHTML = rv;

}

const imgSizeChange = (target) => {
    $(target).toggleClass("img-size-up");
    $(target).parent('div .img-wrap').toggleClass("img-size-up");
}

function createPaging(totalPage, nowPage) {
    var review_paging = document.getElementById("review-paging");
    var pg = "";

    pg +='<nav aria-label="Page navigation example">' +
        '<ul class="pagination pagination-sm justify-content-center mt-2">'

    if (nowPage !== 0) {
        pg += '<li class="page-item">' +
            '<a class="page-link" onclick="clickPage('+ (nowPage -1) +')" aria-label="Previous">' +
            '<span aria-hidden="true">&laquo;</span>' +
            '</a></li>'
    }

    for (let i = 0; i < totalPage; i++) {
        pg += '<li id="page'+i+'" class="page-item"><a class="page-link" onclick="clickPage('+ i +')">' + (i+1)  + '</a></li>'
    }

    if (totalPage !== 0 && nowPage !== totalPage - 1) {
        pg += '<li class="page-item">' +
            '<a class="page-link" onclick="clickPage('+ (nowPage +1) +')" aria-label="Next">' +
            '<span aria-hidden="true">&raquo;</span>' +
            '</a></li>'
    }

    pg += '</ul></nav>'

    review_paging.innerHTML = pg;

    $('#page'+nowPage).addClass("active");
}

function createTag(points, drinks) {
    var point_tags = document.getElementById("point-tags");
    var drink_tags = document.getElementById("drink-tags");
    var point = "";
    var drink = "";

    for (var i = 0; i < drinks.length; i++) {

        drink += "<span style='background-color: ";

        if (drinks[i].count >= 3) {
            drink += "#FF9900'"
        } else {
            drink += "#FFCC33'"
        }
        drink += "class='btn pe-none rounded-pill m-1'>" + drinks[i].drinkName + "</span>"
    }

    for (var i = 0; i < points.length; i++) {

        point += "<span style='background-color: ";

        if (points[i].count >= 3) {
            point += "#00CC66'"
        } else {
            point += "#66FF99'"
        }
        point +=
            "class='btn pe-none rounded-pill m-1'>" + points[i].pointName + "</span>"
    }

    drink_tags.innerHTML = drink;
    point_tags.innerHTML = point;
}