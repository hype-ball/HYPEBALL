$(document).ready(function() {
    console.log(window.location.href.indexOf('#loginModal'))
    if(window.location.href.indexOf('#loginModal') !== -1) {
        $('#loginModal').modal('show');
    }
});