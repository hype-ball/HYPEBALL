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
    $(".modal-map a").remove();
}

function createReview(reviews) {
    var review_section = document.getElementById("review_section");
    var rv = "";

    for (var i = 0; i < reviews.length; i++) {
        rv +=
            "<div><p>" + reviews[i].content + "</p>"
            + "<p>" + reviews[i].createdDate + "</p>"
            + "<p>" + reviews[i].star + "</p></div>"
            + "<p>" + reviews[i].writer + "</p></div>"
        ;
    }

    review_section.innerHTML = rv;
}

function createPaging(totalPage, nowPage) {
    var review_paging = document.getElementById("review-paging");
    var pg = "";

    pg +='<nav aria-label="Page navigation example">' +
        '<ul class="pagination">'

    if (nowPage !== 0) {
        pg += '<li class="page-item">' +
            '<a class="page-link" onclick="clickPage('+ (nowPage -1) +')" aria-label="Previous">' +
            '<span aria-hidden="true">&laquo;</span>' +
            '</a></li>'
    }

    for (let i = 0; i < totalPage; i++) {
        pg += '<li class="page-item"><a class="page-link" onclick="clickPage('+ i +')">' + i  + '</a></li>'
    }

    if (totalPage !== 0 && nowPage !== totalPage - 1) {
        pg += '<li class="page-item">' +
            '<a class="page-link" onclick="clickPage('+ (nowPage +1) +')" aria-label="Next">' +
            '<span aria-hidden="true">&raquo;</span>' +
            '</a></li>'
    }

    pg += '</ul></nav>'

    review_paging.innerHTML = pg;
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