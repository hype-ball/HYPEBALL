$(document).ready(function() {
    console.log(window.location.href.indexOf('#loginModal'))
    if(window.location.href.indexOf('#loginModal') !== -1) {
        $('#loginModal').modal('show');
    }
});

function loginError(response) {
    console.log("error")
    alert(response.message)
    if (response.status === 401) {
        window.location.href="/#loginModal";
    }
}