$(document).ready(function() {
var myCookie = document.cookie.replace(/(?:(?:^|.*;\s*)accepted\s*\=\s*([^;]*).*$)|^.*$/, "$1");
  if (myCookie != "yes") {
    $('#cookie-wrap').show();
    $('#accept').click(function() {
      document.cookie = "accepted=yes; expires=Thu, 18 Dec 2025 12:00:00 GMT; path=/";
      $('#cookie-wrap').hide();
    });
  }
});