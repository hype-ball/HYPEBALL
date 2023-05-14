var mapContainer = document.getElementById('map'), // 지도를 표시할 div
    mapOption = {
        center: new kakao.maps.LatLng(37.521670, 126.990769), // 지도의 중심좌표
        level: 7 // 지도의 확대 레벨
    };

// 지도를 생성합니다.
var map = new kakao.maps.Map(mapContainer, mapOption);

// 마커 이미지의 이미지 주소입니다
var imageSrc = '/image/Group 3.png', // 마커이미지의 주소입니다
    imageSize = new kakao.maps.Size(30, 50), // 마커이미지의 크기입니다
    imageOption = {offset: new kakao.maps.Point(17, 69)}; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.


$.ajax({
    url: '/map/home',
    type: 'POST',
    success: function (data) {

        for (var i = 0; i < data.length; i++) {

            // 마커 이미지를 생성합니다
            var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption);

            // 마커를 생성합니다
            var marker = new kakao.maps.Marker({
                map: map, // 마커를 표시할 지도
                position: new kakao.maps.LatLng(data[i].lat, data[i].lng), // 마커를 표시할 위치
                title: data[i].name, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
                image: markerImage // 마커 이미지
            });

            // 커스텀 오버레이를 생성합니다
            var customOverlay = new kakao.maps.CustomOverlay({
                map: map,
                position: new kakao.maps.LatLng(data[i].lat, data[i].lng),
                content: '<div class="customoverlay" onclick="createMap(' + data[i].id + ',' + "'"+ data[i].name + "'" +',' + "'"+ data[i].address + "'" +',' + "'"+ data[i].category.name + "'" +',' + "'"+ data[i].menu + "'" +',' + data[i].lat + ',' + data[i].lng + ')" data-bs-toggle="modal" data-bs-target="#store-modal">' +
                    '  <p>' +
                    '    <span class="title">' + data[i].name + '</span>' +
                    '  </p>' +
                    '</div>',
                yAnchor: 1
            });
        }
    },
    error: function () {
    }
});

// 모달의 이미지 지도
const createMap = (storeId, name, address, category, menu, lat, lng) => {
    $("#storeName").text(name);
    $("#storeAddr").text("📍 "+ address);
    $("#storeCategory").text(category);
    $("#storeMenu").text("🐽 " + menu);
    $(".modal-map a").remove();

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

    }, 200);

    $.ajax({
        url: '/reviews/' + storeId,
        type: 'GET',
        success: function (data) {
            console.log("리뷰가져오기");
            console.log(data);

            var review_section = document.getElementById("review_section");
            var reviews = "";

            for (var i = 0; i < data.length; i++) {
                reviews +=
                        "<div><p>" + data[i].member.name + "</p>"
                            + "<p>" + data[i].createdDate + "</p>"
                            + "<p>" + data[i].star + "</p></div>"
                ;
            }

            review_section.innerHTML = reviews;

            },
        error: function () {
        }
    });

}


