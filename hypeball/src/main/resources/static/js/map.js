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
        mapOption.center = new kakao.maps.LatLng(37.5076636999999, 127.0405894);
        mapOption.level = 5;
        map = new kakao.maps.Map(mapContainer, mapOption);
    }

    if (region === 'yongsan') {
        mapOption.center = new kakao.maps.LatLng(37.5314, 126.9799); // 용산역
        mapOption.level = 5;
        map = new kakao.maps.Map(mapContainer, mapOption);
    }

    if (region === 'jamsil') {
        mapOption.center = new kakao.maps.LatLng(37.5133, 127.1001); // 잠실역
        mapOption.level = 5;
        map = new kakao.maps.Map(mapContainer, mapOption);
    }

    if (region === 'hongdae') {
        mapOption.center = new kakao.maps.LatLng(37.5575, 126.9245); // 홍대입구역
        mapOption.level = 5;
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
    } else if (url.includes("/map/rank/")) {
        let keyword = url.substring(url.lastIndexOf("/") + 1);

        $.ajax({
            url: '/map/rank/' + keyword,
            type: 'POST',
            success: function (data) {
                const boards = $('#top-boards')

                mapOption.center = new kakao.maps.LatLng(data[0].lat, data[0].lng);
                mapOption.level = 7;
                map = new kakao.maps.Map(mapContainer, mapOption);

                for (var i = 0; i < data.length; i++) {
                    boards.append('' +
                        '<div class="top-board my-3 shadow rounded d-flex p-2"' +
                        ' onclick="moveFocus(' + data[i].lat + ',' +  data[i].lng + ', this' + ')">' +
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





