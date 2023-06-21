var mapContainer = document.getElementById('map'), // 지도를 표시할 div
    mapOption = {
        center: new kakao.maps.LatLng(37.521670, 126.990769), // 지도의 중심좌표
        level: 7 // 지도의 확대 레벨
    };

console.log("지도만들엉엉")

// 지도를 생성합니다.
var map = new kakao.maps.Map(mapContainer, mapOption);

// 마커 이미지의 이미지 주소입니다
var imageSrc = '/image/Group 3.png', // 마커이미지의 주소입니다
    imageSize = new kakao.maps.Size(30, 50), // 마커이미지의 크기입니다
    imageOption = {offset: new kakao.maps.Point(17, 69)}; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.

$(document).ready(function() {

    const url = window.location.href

    const i = window.location.href.search('=')
    console.log(url.substring(i + 1));

    const region = url.substring(i+1)

    if (region === 'gangnam') {
        // mapOption = {
        //     center: new kakao.maps.LatLng(37.5076636999999, 127.0405894), // 지도의 중심좌표
        //     level: 7// 지도의 확대 레벨
        // };
        mapOption.center = new kakao.maps.LatLng(37.5076636999999, 127.0405894);
        mapOption.level = 6;
        map = new kakao.maps.Map(mapContainer, mapOption);

    }


    if (url.includes("/map/home")) {
        $.ajax({
            url: '/map/home',
            type: 'POST',
            success: function (data) {
                for (var i = 0; i < data.length; i++) {
                    createMarker(data[i]);
                }
            },
            error: function () {
            }
        });
    } else if (url.endsWith("/member/myLike")) {
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
    } else if (url.endsWith("/rank/star")) {
        $.ajax({
            url: '/map/rank/star',
            type: 'POST',
            success: function (data) {
                const boards = $('#top-boards')

                let centerLat = 0;
                let centerLng = 0;

                for (var i = 0; i < data.length; i++) {
                    centerLat += data[0].lat;
                    centerLng += data[0].lng;
                    createMarker(data[i]);
                }

                mapOption.center = new kakao.maps.LatLng(centerLat / data.length, centerLng / data.length)
                mapOption.level = 7;
                map = new kakao.maps.Map(mapContainer, mapOption);

                boards.before('<div class="board-label bg-warning rounded p-2 fixed .fw-2">평균 별점 TOP 10</div>');

                for (var i = 0; i < data.length; i++) {
                    boards.append('<div class="top-board mb-3 mt-3 shadow rounded pt-3 ps-2" onclick="'
                        + 'moveFocus(' + data[i].lat + ',' +  data[i].lng + ',this' + ')"><h4>' + data[i].name + '</h4>' +
                        '<p>' + data[i].address + '</p></div>')
                }

                for (var i = 0; i < data.length; i++) {
                    createMarker(data[i]);
                }


            },
            error: function () {
            }
        });
    }
});

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
        content: '<div class="customoverlay" onclick="createModal('+ data.storeId +')" data-bs-toggle="modal" data-bs-target="#store-modal">' +
            '  <p>' +
            '    <span class="title">' + data.name + '</span>' +
            '  </p>' +
            '</div>',
        yAnchor: 1
    });
}

function moveFocus(lat, lng, target) {
    var moveLatLon = new kakao.maps.LatLng(lat, lng);

    console.log(target)

    $('#top-boards').children().removeClass("focused");
    target.className += " focused"

    map.panTo(moveLatLon);
}





