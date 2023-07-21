const reviewSort = (target) => {
    reviewAjax(0, target.value);
}

const myReviewSort = (target) => {
    myReviewAjax(target.value);
}

const clickPage = (page) => {
    reviewAjax(page, $('select[name="sort"]').val());
}

const reviewAjax = (pageParam, sortParam) => {
    const reviewSortCond = {
        page : pageParam,
        sort : sortParam
    }

    // 모달 리뷰 페이징 및 정렬
    $.ajax({
        url: '/reviews/ps/' + $("#storeId").val(),
        type: 'GET',
        data : reviewSortCond,
        success: function (reviews) {
            createReview(reviews.content);
            createPaging(reviews.totalPages, reviews.number);
        },
        error: function () {
        }
    });
}

const myReviewAjax = (sortParam) => {

    const reviewSortCond = {
        sort : sortParam
    }
    // 마이페이지 리뷰 정렬
    $.ajax({
        url: '/reviews/writer/',
        type: 'GET',
        data : reviewSortCond,
        success: function (myReviews) {
            createMyReview(myReviews);
        },
        error: function () {
        }
    });
}

// 가게 상세 정보 모달
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
            if (data.status === "like") {
                $("#storeHeart").attr("src", "/image/aliveheart.png");
            } else {
                $("#storeHeart").attr("src", "/image/deadheart.png");
            }

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

// 모달의 이미지 지도
function staticMap(lat, lng) {
    setTimeout(function () {
        // 이미지 지도에서 마커가 표시될 위치입니다
        var markerPosition = new kakao.maps.LatLng(lat, lng);
        // 이미지 지도에 표시할 마커입니다
        // 이미지 지도에 표시할 마커는 Object 형태입니다
        var marker = {
            position: markerPosition,
        };

        var staticMapContainer = document.getElementById('staticMap'), // 이미지 지도를 표시할 div
            staticMapOption = {
                center: new kakao.maps.LatLng(lat, lng), // 이미지 지도의 중심좌표
                level: 3, // 이미지 지도의 확대 레벨
                marker: marker
            };

        // 이미지 지도를 표시할 div와 옵션으로 이미지 지도를 생성합니다
        var staticMap = new kakao.maps.StaticMap(staticMapContainer, staticMapOption);
        staticMap.a.lastChild.setAttribute("onclick", "return false;");


    }, 200);
}

// 정적지도 이미지 비우기
function staticMapInit() {
    $("#staticMap").empty();
}

// 가게 정보
function storeInfo(store) {
    $("#storeId").val(store.id);
    $("#storeName").text(store.name);
    $("#storeAddr").text("📍 "+ store.address);
    $("#storeCategory").text(store.category.name);
    $("#storeMenu").text("🐽 " + store.menu);
    $("#storeStarAvg").text(store.starAvg.toFixed(1));
    $("#storeLikeCount").text(store.totalLikeCount);
}

// 모달 리뷰 생성
function createReview(reviews) {
    var review_section = document.getElementById("review_section");
    var rv = "";

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
        rv += '</a>';
    }

    review_section.innerHTML = rv;

}

// 마이페이지 리뷰 생성
function createMyReview(myReviews) {

    var my_review_section = document.getElementById("my_review_section");
    var my_rv = "";

    if (myReviews.length === 0) {
        my_rv = '<a className="list-group-item list-group-item-action my-1 text-center">' +
        '<p>작성한 리뷰가 없습니다.</p>' +
        '</a>'
    }

    for (var i = 0; i < myReviews.length; i++) {
        my_rv += '<a class="list-group-item my-1">' +
            '      <div>' +
            '        <div class="d-flex w-100 justify-content-between">' +
            '           <p class="fs-5 mb-0 myPageStoreName" onclick="' + 'createModal(' + myReviews[i].storeId + ')' + '" data-bs-toggle="modal" data-bs-target="#store-modal">' +
                        myReviews[i].storeName +
            '           </p>' +
        '           <button class="btn btn-danger btn-sm" id="' + myReviews[i].reviewId + '" onclick="deleteConfirm(this)">' + '삭제</button>' +
            '        </div>' +
            '        <hr class="my-1">'  +
            '        <div class="d-flex w-100 justify-content-between">' +
            '           <span class="star rv-star">★★★★★' +
            '               <span style="width: ' + (myReviews[i].star * 20) + '%">★★★★★</span>' +
            '           </span>' +
            '           <small class="text-dark">' + myReviews[i].createdDate + '</small>' +
            '        </div>' +
            '     </div>';

        for (let j = 0; j < myReviews[i].drinks.length; j++) {
            my_rv +=
            '        <small style="background-color: #FF9900" class="badge rounded-pill">' + myReviews[i].drinks[j].drink + '</small>'
        }

        my_rv +=
            '        <p class="my-1 rv-content text-dark">' + myReviews[i].content + '</p>\n';

        if (myReviews[i].attachedFiles != null) {
            my_rv += '<div class="row m-auto gap-1">';
            for (let j = 0; j < myReviews[i].attachedFiles.length; j++) {
                my_rv +=
                    '<div class="img-wrap">' +
                    '   <img class="img-thumbnail review-img" src="/files/' + myReviews[i].attachedFiles[j].storeFileName + '" alt="img" onclick="imgSizeChange(this)">' +
                    '   <p class="img-hover-text">이미지<br>크게 보기</p>' +
                    '   <p class="img-hover-text2">이미지<br>원래대로</p>' +
                    '</div>'
            }
            my_rv += '</div>'
        }
        my_rv += '</a>';
    }

    my_review_section.innerHTML = my_rv;
}

// 첨부파일 크게보기
const imgSizeChange = (target) => {
    $(target).toggleClass("img-size-up");
    $(target).parent('div .img-wrap').toggleClass("img-size-up");
}

// 페이징 바 생성
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

// 술 및 분위기 태그 생성
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
            point += "#FD9595'"
        } else {
            point += "#FCCDCD'"
        }
        point +=
            "class='btn pe-none rounded-pill m-1'>" + points[i].pointName + "</span>"
    }

    drink_tags.innerHTML = drink;
    point_tags.innerHTML = point;
}

