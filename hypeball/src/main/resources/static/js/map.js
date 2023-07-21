var mapContainer = document.getElementById('map'), // 지도를 표시할 div
    mapOption = {
        center: new kakao.maps.LatLng(37.521670, 126.990769), // 지도의 중심좌표
        level: 7 // 지도의 확대 레벨
    };

// 지도 생성
var map = new kakao.maps.Map(mapContainer, mapOption);

// 마커 이미지의 이미지 주소입니다
var imageSrc = '/image/marker_lemon.png', // 마커이미지의 주소
    changeImage = '/image/marker_pink.png',
    imageSize = new kakao.maps.Size(30, 50), // 마커이미지의 크기
    imageOption = {offset: new kakao.maps.Point(17, 69)}; // 마커이미지의 옵션. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정

$(document).ready(function () {

    const url = window.location.href

    // 찜한 가게 지도로 보기
    if (url.endsWith("/member/myLike")) {
        $.ajax({
            url: '/member/myLike',
            type: 'POST',
            success: function (data) {
                for (var i = 0; i < data.length; i++) {
                    createMarker(data[i]);
                }
            },
            error: function () {
            }
        });
    } else if (url.includes("/map/rank/")) {
        let keyword = url.substring(url.lastIndexOf("/") + 1);

        // 통계로 보는 하이볼
        $.ajax({
            url: '/map/rank/' + keyword,
            type: 'POST',
            success: function (data) {

                mapOption.center = new kakao.maps.LatLng(data[0].lat, data[0].lng);
                mapOption.level = 7;
                map = new kakao.maps.Map(mapContainer, mapOption);

                createSideCard(data);

                for (var i = 0; i < data.length; i++) {
                    createMarker(data[i]);
                }

            },
            error: function () {
            }
        });
    } else if (url.includes("/map/home?region")) {

        const i = window.location.href.search('=')

        const address = url.substring(i + 1);

        if (address === 'gangnam') {
            mapOption.center = new kakao.maps.LatLng(37.5076636999999, 127.0405894);
        } else if (address === 'yongsan') {
            mapOption.center = new kakao.maps.LatLng(37.5314, 126.9799); // 용산역
        } else if (address === 'jamsil') {
            mapOption.center = new kakao.maps.LatLng(37.5133, 127.1001); // 잠실역
        } else if (address === 'hongdae') {
            mapOption.center = new kakao.maps.LatLng(37.5575, 126.9245); // 홍대입구역
        }

        mapOption.level = 5;
        map = new kakao.maps.Map(mapContainer, mapOption);

        // 지역별 하이볼 명가
        $.ajax({
            url: '/map/home/' + address,
            type: 'POST',
            success: function (data) {
                for (var i = 0; i < data.length; i++) {
                    createMarker(data[i]);
                }
            },
            error: function () {
            }
        });
    } else {

        document.getElementById("searchBar").innerHTML =
            '<div class="d-flex search-wrap">\n' +
            '  <input type="text" name="search" class="form-control search-input" placeholder=" 검색어를 입력하세요" style="ime-mode:active">\n' +
            '  <button type="submit" class="btn search-btn"><i class="bi bi-search"></i></button>\n' +
            '</div>';

        let search = null;
        if (url.includes("/map/home?search")) {
            const i = window.location.href.search('=')

            search = decodeURI(url.substring(i + 1));
        }

        let searchForm = {
            keyword : search
        }

        // 하이볼 명가 전체보기
        $.ajax({
            url: '/map/home',
            type: 'POST',
            data: searchForm,
            success: function (data) {
                for (var i = 0; i < data.marker.length; i++) {
                    createMarker(data.marker[i]);
                }
                if (data.search != null) {
                    createSideCard(data.search)
                    $('input[name="search"]').val(data.keyword);
                }
            },
            error: function () {
            }
        });
    }
});

var mappingData = {},
    selectedMarker = null;

function createMarker(data) {

    // 마커 이미지를 생성합니다
    var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption);

    // 마커를 생성합니다
    var marker = new kakao.maps.Marker({
        map: map, // 마커를 표시할 지도
        position: new kakao.maps.LatLng(data.lat, data.lng), // 마커를 표시할 위치
        title: data.name, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
        image: markerImage // 마커 이미지
    });

    // 커스텀 오버레이를 생성합니다
    var customOverlay = new kakao.maps.CustomOverlay({
        map: map,
        position: new kakao.maps.LatLng(data.lat, data.lng),
        content: '<div class="customoverlay" onclick="createModal(' + data.storeId + ')" data-bs-toggle="modal" data-bs-target="#store-modal">' +
            '  <p>' +
            '    <span class="title">' + data.name + '</span>' +
            '  </p>' +
            '</div>',
        yAnchor: 1
    });

    mappingData[data.storeId] = {marker, customOverlay};
}

// 카드 선택시 포커스 이동 및 색깔 변경
function moveFocus(storeId, lat, lng, target) {
    var moveLatLon = new kakao.maps.LatLng(lat, lng);

    $('#top-boards').children().removeClass("focused");
    target.className += " focused"

    $('.customoverlay').removeClass("changeColor");
    searchMarker(storeId);

    map.setLevel(6);
    map.panTo(moveLatLon);
}

function searchMarker(storeId) {

    var obj = mappingData[storeId];
    if (!selectedMarker || selectedMarker !== obj.marker) {
        // 클릭된 마커 객체가 null이 아니면
        // 클릭된 마커의 이미지를 기본 이미지로 변경하고
        !!selectedMarker && selectedMarker.marker.setImage(createMarkerImage(imageSrc))
    }
    var changeContent = obj.customOverlay.cc.replace("customoverlay", "customoverlay changeColor");

    selectedMarker = obj;
    obj.marker.setImage(createMarkerImage(changeImage));
    obj.customOverlay.setContent(changeContent);
    return selectedMarker;
}

// MakrerImage 객체를 생성하여 반환하는 함수입니다
function createMarkerImage(changeImageSrc) {
        return new kakao.maps.MarkerImage(changeImageSrc, imageSize, imageOption);
}

// 사이드 카드 생성
function createSideCard(data) {
    const boards = $('#top-boards')
    if (data.length === 0) {
        $('#searchBar').append(
            '<div class="alert alert-warning alert-wrap" role="alert">검색결과가 없습니다.</div>'
        )
        return;
    }
    for (var i = 0; i < data.length; i++) {
        boards.append(
            '<div class="top-board my-3 shadow rounded d-flex p-2"' +
            ' onclick="moveFocus(' + data[i].storeId + ',' + data[i].lat + ',' + data[i].lng + ', this' + ')">' +
            '<div class="p-3 fs-5 text-center rounded">' + (i + 1) + '</div>' +
            '<div class="top-board-content py-3 ps-4 pe-3">' +
            '   <h4>' + data[i].name + '</h4>' +
            '   <p>' + data[i].address + '</p>' +
            '   <div class="d-flex gap-3">' +
            '       <div>⭐ 별점 ' + data[i].starAvg.toFixed(1) + '</div>' +
            '       <div>💖 찜 ' + data[i].likeCount + '</div>' +
            '       <div>💬 리뷰 ' + data[i].reviewCount + '</div>' +
            '   </div>' +
            '</div>' +
            '</div>')
    }
}






