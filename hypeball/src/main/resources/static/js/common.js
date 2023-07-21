// 로그인 모달로 이동
$(document).ready(function() {
    if(window.location.href.indexOf('#loginModal') !== -1) {
        $('#loginModal').modal('show');
    }
});

function loginError(response) {
    alert(response.message)
    if (response.status === 401) {
        window.location.href="/#loginModal";
    }
}